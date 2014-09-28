/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link ResourceLoader} uses commons-chain to load resource from several
 * loaders. It is usefull when you have more place to load your resources. It
 * iterate on the registered loaders until a resource is loaded. If no loader
 * can load the {@link Resource} with the given id, then it throw a
 * {@link ResourceNotFoundException}.
 * 
 * @author Filippo De Luca
 * 
 * @version $Id$
 * @since 1.0
 */
public class ResourceLoaderChain implements ResourceLoader {

	private static final String CTX_ID = "id";
	private static final String CTX_LOADED = "loaded";

	private static final Logger logger = LoggerFactory
			.getLogger(ResourceLoaderChain.class);

	private Chain loadersChain = new ChainBase();

	public ResourceLoaderChain() {
		// Empty
	}

	/**
	 * Create ResourceLoaderChain with the given loaders as chain.
	 * 
	 * @param loaders
	 *            The loaders chain.
	 * @throws IllegalArgumentException
	 *             if the loaders is null.
	 */
	public ResourceLoaderChain(ResourceLoader[] loaders) {

		Validate.notNull(loaders, "The loaders must be not null");
		
		addResourceLoaders(Arrays.asList(loaders));
	}

	/**
	 * Create ResourceLoaderChain with the given loaders as chain.
	 * 
	 * @param loaders
	 *            The loaders chain.
	 * @throws IllegalArgumentException
	 *             if the loaders is null.
	 * @throws IllegalArgumentException
	 *             if the loaders contains null element.
	 * @throws IllegalArgumentException
	 *             if the loaders contains no {@link ResourceLoader} instance.
	 */
	public ResourceLoaderChain(List/* ResourceLoader */loaders) {

		Validate.notNull(loaders);
		Validate.noNullElements(loaders);
		Validate.allElementsOfType(loaders, ResourceLoader.class);

		addResourceLoaders(loaders);
	}

	/**
	 * Load the resource with given id, iterating over loaders chain.
	 */
	public Resource loadResource(String id) {

		Validate.notEmpty(id, "The resource id must be not empty");

		Context context = new ContextBase();
		context.put(CTX_ID, id);

		boolean executed = Command.CONTINUE_PROCESSING;

		try {
			executed = loadersChain.execute(context);
		} 
		catch (ResourceException e) {
			throw e;
		}
		catch (Exception e) {
			logger.error("Error loading resource: {}.", id);
			throw new RuntimeException(e.getLocalizedMessage(), e);
		}

		if (executed == Command.PROCESSING_COMPLETE) {

			assert context.get(CTX_LOADED) != null;
			assert context.get(CTX_LOADED) instanceof Resource;

			Resource loaded = (Resource) context.get(CTX_LOADED);
			return loaded;
		} else {
			throw new ResourceNotFoundException(id);
		}

	}

	/**
	 * Add a {@link ResourceLoader} to the loaders chain.
	 * 
	 * @param loader The loader to add
	 * @throws IllegalArgumentException if the given loader is null.
	 */
	public void addResourceLoader(ResourceLoader loader) {

		Validate.notNull(loader);

		loadersChain.addCommand(new LoadResourceCommand(loader));
	}
	
	
	public void addResourceLoaders(List loaders) {
		for (Iterator it = IteratorUtils.getIterator(loaders); it.hasNext();) {
			addResourceLoader((ResourceLoader) it.next());
		}
	}

	// ResourceLoader Command static class
	static class LoadResourceCommand implements Command {

		private final ResourceLoader loader;

		public LoadResourceCommand(ResourceLoader loader) {
			this.loader = loader;
		}

		public boolean execute(Context context) throws Exception {

			String id = (String) context.get(CTX_ID);

			assert StringUtils.isNotBlank(id) : "The id in context is blank";

			try {
				Resource loaded = loader.loadResource(id);
				context.put(CTX_LOADED, loaded);
				return PROCESSING_COMPLETE;
			} catch (Exception e) {
				return CONTINUE_PROCESSING;
			}
		}

	}

}
