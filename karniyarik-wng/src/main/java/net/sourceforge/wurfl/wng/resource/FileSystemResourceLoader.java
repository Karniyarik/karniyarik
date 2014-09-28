/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link ResourceLoader} implementation load resource from the filesystem,
 * using a base directory.
 * 
 * 
 * @author Filippo De Luca
 * 
 * @version $Id$
 * @since 1.0
 */
public class FileSystemResourceLoader implements ResourceLoader {

	private static final Logger logger = LoggerFactory
			.getLogger(FileSystemResourceLoader.class);

	private File baseDirectory;

	/**
	 * Create the {@link FileSystemResourceLoader} using given {@link File} as
	 * base directory. The base directory can be null if it is intended to use
	 * ROOT directory.
	 * 
	 * @param baseDirectory
	 *            The base directory to load resource from.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given base directory is null.
	 * @throws IllegalArgumentException
	 *             if the given base directory does not exist.
	 * @throws IllegalArgumentException
	 *             if the given base directory is not a directory.
	 * @throws IllegalArgumentException
	 *             if the given base directory is not readable.
	 */
	public FileSystemResourceLoader(File baseDirectory) {

		Validate.notNull(baseDirectory, "The baseDirectory can't be null");
		Validate.isTrue(baseDirectory.exists(), "The baseDirectory must exist");
		Validate.isTrue(baseDirectory.isDirectory(),
				"The baseDirectory must be a directory");
		Validate.isTrue(baseDirectory.canRead(),
				"The baseDirectory must be readable");

		this.baseDirectory = baseDirectory;
	}

	/**
	 * Load the resource from filesystem using baseDirectory. The resource name
	 * used will be <code>new File(baseDirectory, id)</code>.
	 * 
	 * @throws ResourceNotFoundException
	 *             if the resource file is not a file, does not exist or it is
	 *             not readable.
	 */
	public Resource loadResource(String name) {

		Resource resource = loadFromFileSystem(name);

		if (logger.isDebugEnabled() && resource != null) {
			logger.debug("Loaded {} resource", name);
		}

		return resource;
	}

	private Resource loadFromFileSystem(String id) {
		Resource resource = null;

		File file = new File(baseDirectory, id);

		if (file.exists() && file.isFile() && file.canRead()) {

			try {
				InputStream stream = FileUtils.openInputStream(file);
				resource = new DefaultResource(file.getCanonicalPath(), stream);
			} catch (IOException e) {
				logger.error("Error: {} loading file: {}", e, file);
				throw new ResourceAccessException(id, e.getLocalizedMessage(), e);
			}

		} else if (!file.exists()) {
			throw new ResourceNotFoundException(id, "File " + file.getPath()
					+ " does not exist.");
		} else if (!file.isFile()) {
			throw new ResourceNotFoundException(id, "File " + file.getPath()
					+ " is not a regular file.");
		} else if (!file.canRead()) {
			throw new ResourceAccessException(id, "File " + file.getPath()
					+ " can not be read.");
		}

		return resource;
	}

}
