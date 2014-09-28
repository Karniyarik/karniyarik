/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link ResourceLoader} implementation, load resource using
 * {@link ClassLoader}. The resource will be loaded using baseClassPath and
 * given id.
 * 
 * @author Filippo De Luca
 * 
 * @version $Id$
 * @since 1.0
 */
public class ClassPathResourceLoader implements ResourceLoader {

	private final String baseClassPath;

	private final ClassLoader classLoader = getClass().getClassLoader();

	private static final Logger logger = LoggerFactory
			.getLogger(ClassPathResourceLoader.class);

	/**
	 * Create a {@link ClassPathResourceLoader} using the given baseClassPath.
	 * The baseClassPath can be null or empty if the resource will be loaded
	 * from Root classpath.
	 * 
	 * @param baseClassPath
	 */
	public ClassPathResourceLoader(String baseClassPath) {
		
		
		String workingBaseClassPath= StringUtils.trimToEmpty(baseClassPath);
		workingBaseClassPath = StringUtils.stripStart(workingBaseClassPath, "/");
		//System.out.println("Classpath resource loader : " + baseClassPath + " - "  + workingBaseClassPath);
		// The baseClassPath can be empty (ROOT classPath)
		this.baseClassPath = workingBaseClassPath;
	}

	/**
	 * Load resource from baseClassPath using the given id as resource name. The
	 * loaded resource will be: baseClassPath + "/" + id;
	 * 
	 * @throws ResourceNotFoundException
	 *             if the resource does not exist in classpath.
	 */
	public Resource loadResource(String id) {
		
		Resource resource = findFromClasspath(id);

		if (resource != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Loaded {} resource", id);
			}
			return resource;
		}

		throw new ResourceNotFoundException(id);
	}

	private Resource findFromClasspath(String fileName) {

		Resource resource;

		String path = createResourcePath(fileName);
		//System.out.println("Looking for file: " + path + " --> " + classLoader.getResource(path));
		InputStream stream = classLoader.getResourceAsStream(path);
		if (stream != null) {
			logger.debug("Found valid resource at path {}", path);
			resource = new DefaultResource(path, stream);
		} else {
			logger.debug("Do not found valid resource at path {}", path);
			resource = null;
		}

		return resource;

	}

	private String createResourcePath(String fileName) {
		StrBuilder pathBuilder = new StrBuilder(baseClassPath);
		if (!pathBuilder.endsWith("/")) {
			pathBuilder.append("/");
		}
		pathBuilder.append(fileName);

		String path = pathBuilder.toString();
		
		logger.debug("Created resource path: {}", path);
		
		return path;
	}
}
