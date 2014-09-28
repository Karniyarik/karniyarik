/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Asres Gizaw Fantayeneh
 * @version $Id$
 */
public interface ImageScaler {
	void scaleImage(BufferedImage input, BufferedImage output);
}
