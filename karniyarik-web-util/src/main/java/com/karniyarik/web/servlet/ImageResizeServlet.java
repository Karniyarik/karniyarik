package com.karniyarik.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * Servlet implementation class HitCounterServlet
 */
@SuppressWarnings("serial")
public class ImageResizeServlet extends HttpServlet {

//	private static SummaryStatistics resizeStat = new SummaryStatistics();
//	private static SummaryStatistics downloadStat = new SummaryStatistics();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String imageURL = request.getParameter("v");

		BufferedImage bufferedImage = null;
		
		try {
			try
			{
				if (StringUtils.isNotBlank(imageURL)) {
					imageURL = ImageUtil.encodeImage(imageURL);
					bufferedImage = readImage(imageURL);
				}
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
			if(bufferedImage == null)
			{
				InputStream stream = StreamUtil.getStream("nophoto.png");
				bufferedImage = ImageIO.read(stream);
				stream.close();
			}
			
			if(bufferedImage != null)
			{
				String widthStr = request.getParameter("w");
				String heightStr = request.getParameter("h");
				String typeStr = request.getParameter("t");
				String cropRatioStr = request.getParameter("cr");

				if(StringUtils.isNotBlank(widthStr) && StringUtils.isNotBlank(heightStr))
				{
					int type = ImageResizer.CROP_CENTERED;
					if(StringUtils.isNotBlank(typeStr) && typeStr.trim().equalsIgnoreCase("scale"))
					{
						type=ImageResizer.FULL_SCALE;
					}
					
					double cropRatio = 1.25;						
					if(StringUtils.isNotBlank(cropRatioStr))
					{
						try{
							cropRatio = Double.parseDouble(cropRatioStr);
						}
						catch(Throwable t){}
					}
					
					int width = Integer.parseInt(widthStr);
					int height = Integer.parseInt(heightStr);
					
					bufferedImage = ImageResizer.resize(bufferedImage, width, height, type, cropRatio);
				}
			}
		} catch (Throwable e) {
			int a = 9;
		}

		if(bufferedImage == null)
		{
			InputStream stream = StreamUtil.getStream("nophoto.png");
			bufferedImage = ImageIO.read(stream);
			stream.close();
		}
		
		response.setContentType("image/png");
		
		HttpCacheUtil.setResponseCacheAttributes(response, request, 168);
		ImageIO.write(bufferedImage, "PNG", response.getOutputStream());
	}
	
	private BufferedImage readImage(String aURL) throws MalformedURLException,
			IOException {
		BufferedImage bufferedImage = null;		
		HttpClient client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 4000);
		HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Windows; U; Windows NT 6.1; ru; rv:1.9.2.3) Gecko/20100401 Firefox/4.0 (.NET CLR 3.5.30729)");
		HttpClientParams.setRedirecting(params, false);
		HttpGet get = new HttpGet(aURL);
		HttpResponse execute = client.execute(get);
		int anHttpStatus = execute.getStatusLine().getStatusCode();
		
		
		if (anHttpStatus == 200) {
			bufferedImage = ImageIO.read(execute.getEntity().getContent());
			execute.getEntity().consumeContent();
		}
		
		return bufferedImage;
	}
}