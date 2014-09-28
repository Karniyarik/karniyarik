/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

import net.sourceforge.wurfl.wng.component.Component;

import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang.Validate;
import org.slf4j.LoggerFactory;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class DefaultRendererGroup implements RendererGroup {
	
	private static final String CTX_RENDERER = "renderer";
	
	private static final String CTX_COMPONENT = "component";

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(DefaultRendererGroup.class);

	private String name;

	private Chain chain = new ChainBase();

	private ComponentRenderer defaultRenderer;

	public DefaultRendererGroup() {
		// Empty
	}

	public DefaultRendererGroup(String name) {
		this.name = name;
	}

	public DefaultRendererGroup(String name, ComponentRenderer defaultRenderer) {
		this.name = name;
		this.defaultRenderer = defaultRenderer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ComponentRenderer getDefaultRenderer() {
		return defaultRenderer;
	}

	public void setDefaultRenderer(ComponentRenderer defaultRenderer) {
		this.defaultRenderer = defaultRenderer;
	}

	public void registerRenderer(Class/* Component */componentClass,
			ComponentRenderer renderer) {
		
		Validate.notNull(componentClass, "The component class is null");
		Validate.notNull(renderer, "The component renderer is null");
		
		Command command = new RendererCommand(componentClass, renderer);
		chain.addCommand(command);
		
		logger.debug("Added renderer for class: {} to group: {}", componentClass, name);
	}

	public ComponentRenderer getRenderer(Component component) {

		Validate.notNull(component, "The component must be not null");

		if(defaultRenderer == null) {
			throw new IllegalStateException("The default renderer is null");
		}
		
		ComponentRenderer renderer = defaultRenderer;

		Context context = new ContextBase();
		context.put(CTX_COMPONENT, component);

		try {
			if (chain.execute(context) == Command.PROCESSING_COMPLETE) {
				renderer = (ComponentRenderer) context.get(CTX_RENDERER);
				assert renderer != null;
				logger.debug("Render for component: {} found in chain: {}", component.getClass(), renderer);
			}
			else{
				logger.debug("Render for component: {} not found in chain", component.getClass());
			}
		} catch (Exception e) {
			logger.error("Error resolving component renderer: {}", e.getLocalizedMessage());
		}
		
		assert renderer != null;
		return renderer;
	}

	private static class RendererCommand implements Command {

		
		private final Class handledClass;
		private final ComponentRenderer renderer;

		public RendererCommand(Class handledClass, ComponentRenderer renderer) {
			
			assert handledClass!=null: "The component class must be not null";
			assert renderer!=null: "The component renderer must be not null";
			
			this.handledClass = handledClass;
			this.renderer = renderer;
		}

		public boolean execute(Context context) throws Exception {

			Component component = (Component) context.get(CTX_COMPONENT);
			assert component != null;

			if (handledClass.isInstance(component)) {

				context.put(CTX_RENDERER, renderer);
				return PROCESSING_COMPLETE;
			}

			return CONTINUE_PROCESSING;
		}

	}

}
