package com.karniyarik.web.servlet;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

public class ImageResizer {
	
	public static int FULL_SCALE = 0;
	public static int CROP_CENTERED = 1;
	
	public static void main(String[] args) {
		try {
			BufferedImage image = ImageIO.read(new File("H:/1/deneme.png"));
			ImageIO.write(resize(image, 60, 60, CROP_CENTERED, 1.25d), "png", new FileOutputStream("H:/1/deneme1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage resize(BufferedImage image, int width, int height, int type, double cropRatio) {
		int orgWidth = image.getWidth();
		int orgHeight = image.getHeight();
		
		int[] values = null;
		if(type == CROP_CENTERED)
		{
			values = intelligentCrop(width, height, orgWidth, orgHeight, cropRatio);	
		}
		else if (type == FULL_SCALE)
		{
			values = fill(width, height, orgWidth, orgHeight);
		}
		
		int offX = values[0];
		int offY = values[1];		
		int resizeWidth = values[2];
		int resizeHeight = values[3];

		//image = createCompatibleImage(image);
		//image = resizeInternal(image, resizeWidth, resizeHeight, width, height);
		image = resizeInternalUsingJavaImageResizingAPI(image, resizeWidth, resizeHeight, width, height);
		image = crop(image, offX, offY, width, height);
		//image = blurImage(image);
		return image;
	}
	
	public static int[] fill(int width, int height, int orgWidth, int orgHeight)
	{
		int offX = 0;
		int offY = 0;
		int resizeHeight = height;
		int resizeWidth = width;
		
		double wRatio = width*1d/orgWidth;
		double hRatio = height*1d/orgHeight;

		if(wRatio > hRatio)
		{
			resizeWidth = new Double(orgWidth * hRatio).intValue();
			offX = (resizeWidth - width) / 2;
		}
		else
		{
			resizeHeight = new Double(orgHeight * wRatio).intValue();
			offY = (resizeHeight - height) / 2;
		}
		
		int[] values = new int[4];
		values[0] = offX;
		values[1] = offY;
		values[2] = resizeWidth;
		values[3] = resizeHeight;
		
		return values;
	}

	public static int[] intelligentCrop(int width, int height, int orgWidth, int orgHeight, double cropRatio)
	{
		int offX = 0;
		int offY = 0;
		int resizeHeight = height;
		int resizeWidth = width;
		
		double wRatio = width*1d/orgWidth;
		double hRatio = height*1d/orgHeight;
		
		double fRatio = (wRatio + hRatio)/2*cropRatio;
		fRatio = fRatio > 1 ? 1 : fRatio;
		
		resizeWidth = new Double(orgWidth * fRatio).intValue();
		offX = (resizeWidth- width) / 2;	
		
		resizeHeight = new Double(orgHeight * fRatio).intValue();
		offY = (resizeHeight -height) / 2;	
		
		int[] values = new int[4];
		values[0] = offX;
		values[1] = offY;
		values[2] = resizeWidth;
		values[3] = resizeHeight;
		
		return values;
	}


	private static BufferedImage resizeInternal(BufferedImage image, int rWidth, int rHeight, int width, int height) {
		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(rWidth, rHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, rWidth, rHeight);
		g.drawImage(image, 0, 0, rWidth, rHeight, Color.WHITE, null);
		g.dispose();
		return resizedImage;
	}

	private static BufferedImage resizeInternalUsingJavaImageResizingAPI(BufferedImage image, int rWidth, int rHeight, int width, int height) {
		ResampleOp  resampleOp = new ResampleOp(rWidth,rHeight);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
		BufferedImage resizedImage = resampleOp.filter(image, null);
		return resizedImage;
	}

	
	private static BufferedImage crop(BufferedImage image, int x, int y, int w, int h) {
		int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage finalImage = new BufferedImage(w, h, type);
		Graphics2D g = finalImage.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, w, h);
		
		int cropW = w > image.getWidth() ? image.getWidth(): w; 
		int cropH = h > image.getHeight() ? image.getHeight() : h; 
		int cropX = x > 0 ? x : 0;
		int cropY = y > 0 ? y : 0;
		Image croppedImage = image.getSubimage(cropX, cropY, cropW, cropH);
		
		int dimX = x > 0 ? 0 : Math.abs(x);
		int dimY = y > 0 ? 0 : Math.abs(y);
		g.drawImage(croppedImage, dimX, dimY, cropW, cropH, null);
		g.dispose();
		return finalImage;
	}

//	public static BufferedImage blurImage(BufferedImage image) {
//		float ninth = 1.0f/9.0f;
//		float[] blurKernel = {
//				ninth, ninth, ninth,
//				ninth, ninth, ninth,
//				ninth, ninth, ninth
//		};
//
//		Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
//		map.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		map.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
//		map.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//		RenderingHints hints = new RenderingHints(map);
//		BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
//		return op.filter(image, null);
//	}

//	private static BufferedImage createCompatibleImage(BufferedImage image) {
//		GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
//		int w = image.getWidth();
//		int h = image.getHeight();
//		BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
//		Graphics2D g2 = result.createGraphics();
//		g2.drawRenderedImage(image, null);
//		g2.dispose();
//		return result;
//	}
}