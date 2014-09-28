/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng.resource;

import java.io.InputStream;

/**
 * @author Asres Gizaw Fantayeneh
 * @author Filippo De Luca
 *
 * @version $Id$
 * @since 1.0
 */
public interface Resource {
	
	String getInfo();

	InputStream getInputStream();
	
	void close();
}
