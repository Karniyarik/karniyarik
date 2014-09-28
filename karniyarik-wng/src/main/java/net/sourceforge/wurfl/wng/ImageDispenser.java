/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import net.sourceforge.wurfl.wng.component.Image;

/**
 *
 * @author Asres Gizaw Fantayeneh
 * @version $Id: ImageDispenser.java 1046 2009-03-09 15:57:13Z filippo.deluca $
 */
public interface ImageDispenser {

	/**
	 * Get the image
	 * @return Image
	 */
	Image getImage();
}
