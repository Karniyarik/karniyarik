/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Filippo De Luca
 * @version $Id$
 */
public class BufferedHttpResponseWrapper extends HttpServletResponseWrapper implements BufferedHttpServletResponse {
	
	private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(new OutputStreamWriter(buffer));
	
	private ServletOutputStream outputStream = new ServletOutputStream(){
		public void write(int b) throws IOException {
			buffer.write(b);
		}
	};
	
	public BufferedHttpResponseWrapper(HttpServletResponse response) {
		super(response);
	}
	
	public PrintWriter getWriter() throws IOException {
		return writer;
	}
	
	public ServletOutputStream getOutputStream() throws IOException {
		return outputStream;
	}
	
	public void writeTo(OutputStream out) throws IOException {
		buffer.writeTo(out);
	}
	
}
