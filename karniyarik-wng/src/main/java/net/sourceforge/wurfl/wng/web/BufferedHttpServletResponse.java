/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public interface BufferedHttpServletResponse extends HttpServletResponse {

	public void writeTo(OutputStream out) throws IOException;

}