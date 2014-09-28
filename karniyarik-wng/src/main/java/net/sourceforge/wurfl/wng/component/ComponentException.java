/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.component;

/**
 * This is an Exception thrown by a component.
 *  
 * @author Filippo De Luca
 * @version $Id$
 */
public abstract class ComponentException extends Exception {
	
	private static final long serialVersionUID = 10L;
	
	private Component source;
	
	public ComponentException(Component source) {
		this.source = source;
	}

	public ComponentException(Component source, String message) {
		super(message);
		this.source = source;
	}
	
	public ComponentException(Component source, String message, Throwable cause) {
		super(message, cause);
		this.source = source;
	}

	public ComponentException(Component source, Throwable cause) {
		super(cause);
		this.source = source;
	}

	public Component getSource() {
		return source;
	}

}
