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
 * Generic interface used to load {@link Resource}.
 * 
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 * 
 * @version $Id$
 * @since 1.0
 */
public interface ResourceLoader {

	/**
	 * Load a resource using the given id.
	 * 
	 * @param id
	 *            The id of loading resource.
	 * @return The resource loaded.
	 * 
	 * @throws ResourceNotFoundException
	 *             if the resource with given id is not found.
	 */
	Resource loadResource(String id);
}
