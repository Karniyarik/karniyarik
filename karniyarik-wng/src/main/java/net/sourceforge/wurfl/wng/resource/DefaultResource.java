/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;

/**
 * This is the default {@link Resource} implementation. It contains info string
 * and a InputStream.
 * 
 * @author Filippo De Luca
 * 
 * @version $id$
 * @since 1.0
 */
public class DefaultResource implements Resource {

	private final String info;

	private final InputStream inputStream;

	/**
	 * Create {@link DefaultResource} using info string and inputStream.
	 * 
	 * @param info
	 * @param inputStream
	 * 
	 * @throws IllegalArgumentException
	 *             if the given {@link InputStream} is null.
	 */
	public DefaultResource(String info, InputStream inputStream) {

		Validate.notNull(inputStream, "The inputStream must be not null");

		this.info = info;
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public String getInfo() {
		return info;
	}

	/**
	 * Close the delegate inputStream. It don't throw excetion on error.
	 */
	public void close() {
		IOUtils.closeQuietly(inputStream);
	}

}
