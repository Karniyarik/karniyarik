/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
/**
 * 
 */
package net.sourceforge.wurfl.wng.resource;

/**
 * @author Asres Gizaw Fantayeneh
 *
 * @version $Id$
 */
public class ResourceNotFoundException extends ResourceException {

	private static final long serialVersionUID = 10L;

	public ResourceNotFoundException(String id, String message, Throwable cause) {
		super(id, message, cause);
	}

	public ResourceNotFoundException(String id, String message) {
		super(id, message);
	}

	public ResourceNotFoundException(String id, Throwable cause) {
		super(id, cause);
	}

	public ResourceNotFoundException(String id) {
		super(id);
	}

}
