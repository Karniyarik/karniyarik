/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import net.sourceforge.wurfl.wng.component.Image;

public class DefaultImageDispenser implements ImageDispenser {
	
	private final Image image;
	
	public DefaultImageDispenser(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return this.image;
	}

}
