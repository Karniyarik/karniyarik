/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

/**
 * This exception is thrown when a resource is found but it is not accessible.
 * 
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 * @since 1.0
 */
public class ResourceAccessException extends ResourceException {

	private static final long serialVersionUID = 10L;

	public ResourceAccessException(String id) {
		super(id);
	}

	public ResourceAccessException(String id, String message, Throwable cause) {
		super(id, message, cause);
	}

	public ResourceAccessException(String id, String message) {
		super(id, message);
	}

	public ResourceAccessException(String id, Throwable cause) {
		super(id, cause);
	}

}
