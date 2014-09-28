/*
 * This file is released under the GNU General Public License.
 * Refer to the COPYING file distributed with this package.
 *
 * Copyright (c) 2008-2009 WURFL-Pro srl
 */
package net.sourceforge.wurfl.wng;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;


/**
 * 
 * @author fanta
 * @testng.test groups = 'func'
 */
public class ImageScalerTest {

	private final static String INPUT_IMG_FILE = "images/palin.JPG";
	
	public void shoudScaleImage() throws IOException {
		
		URL inputImgURL = getClass().getResource(INPUT_IMG_FILE);
				
		BufferedImage input = ImageIO.read(inputImgURL);
		BufferedImage output = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		
		SimpleImageScaler scaler = new SimpleImageScaler();
		scaler.scaleImage(input, output);
		
		String resizedImgFullPath = buildFileName(inputImgURL);		
		ImageIO.write(output, "png", new File(resizedImgFullPath));

		BufferedImage scaledImage = ImageIO.read(new File(resizedImgFullPath));
		Assert.assertEquals(128, scaledImage.getWidth());
		Assert.assertEquals(128, scaledImage.getHeight());
	}

	private String buildFileName(URL inputImgURL) {
		String baseDir = StringUtils.substringBeforeLast(inputImgURL.getPath(), "/");
		return new StrBuilder().append(baseDir).append("/palin-resized.png").toString();

	}
	
	public void shouldCreateANewJPGScaledImage() throws IOException {

		BufferedImage scaledImage;
		URL url = getClass().getResource(INPUT_IMG_FILE);
		BufferedImage input = ImageIO.read(url);
		ImageScaler imageScaler = new BlurImageScaler();
		//scale the image to 100x100px
		scaledImage = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR); //3 bytes for JPGs, 4 byte for PNGs
		imageScaler.scaleImage(input, scaledImage);
		

		assert scaledImage != null : "image data is null";

	}
	
	
}
