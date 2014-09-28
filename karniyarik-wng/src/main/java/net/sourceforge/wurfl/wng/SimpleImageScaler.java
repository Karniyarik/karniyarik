/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * @author Filippo De Luca
 * @author Tommaso Teofili
 * 
 * @version $Id$
 */
public class SimpleImageScaler implements ImageScaler {

	/**
	 * Scales the image using simple java2d API.
	 */
	public void scaleImage(BufferedImage input, BufferedImage output) {

		Graphics2D g = output.createGraphics();
	
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		//float scale = 1f; //output.getWidth() / input.getWidth();
		//RescaleOp op = new RescaleOp(scale, 0, null);

		g.drawImage(input, 0, 0, output.getWidth(), output.getHeight(), null);
		g.dispose();

		input.flush();
	}

}
