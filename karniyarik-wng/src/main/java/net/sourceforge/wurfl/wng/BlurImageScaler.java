/**
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;


/**
 * Blur image to enhanche image scaling quality
 * @author Tommaso Teofili
 *
 */
public class BlurImageScaler implements ImageScaler {

	/** pre blurring image scaling via Java2d */
	public void scaleImage(BufferedImage input, BufferedImage output) {
		Validate.notNull(input);
		
		BufferedImage image = this.blurImage(input);
		
		Graphics2D g = output.createGraphics();
		g.drawImage(image, 0, 0, output.getWidth(), output.getHeight(), null);
		g.dispose();
		
	}
	
	
	
	/** blur selected image */
	private BufferedImage blurImage(BufferedImage image) {
		float value = 1.0f/9.0f;
		float[] blurMatrix = {
				value, value, value,
				value, value, value,
				value, value, value
		};

		Map map = new HashMap();
		map.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RenderingHints hints = new RenderingHints(map);
		
		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurMatrix), ConvolveOp.EDGE_NO_OP, hints);
		try {
			image = op.filter(image, null);
			return image;
		}
		catch (Exception e) { //handle BUG http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4957775
			BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
			op.filter(image, imageCopy);
			return imageCopy;
		}
	}

}
