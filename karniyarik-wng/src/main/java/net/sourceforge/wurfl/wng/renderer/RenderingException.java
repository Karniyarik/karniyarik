/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.renderer;

/**
 * @author Asres Gizaw Fantayeneh
 * @version $Id: RenderingException.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public class RenderingException extends RuntimeException{

	private static final long serialVersionUID = 10L;
	
	public RenderingException() {
		// Empty
	}

	public RenderingException(String arg0) {
		super(arg0);
	}

	public RenderingException(Throwable arg0) {
		super(arg0);
	}

	public RenderingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
