/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 * @since 1.0
 */
public abstract class ResourceException extends RuntimeException {

	private static final long serialVersionUID = 10L;
	
	private final String id;

	public ResourceException(String id) {
		this.id = id;
	}

	public ResourceException(String id, String message, Throwable cause) {
		super(message, cause);
		this.id = id;
	}

	public ResourceException(String id, String message) {
		super(message);
		this.id = id;
	}

	public ResourceException(String id, Throwable cause) {
		super(cause);
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
