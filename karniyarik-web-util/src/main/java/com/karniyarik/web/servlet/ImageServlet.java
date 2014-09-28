package com.karniyarik.web.servlet;

import java.io.IOException;

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

@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String image = request.getParameter("v");
		
		String nophoto = request.getContextPath() + "/images/nophoto.gif";
		
		String result = nophoto; 
		
		if(StringUtils.isNotBlank(image))
		{
			try
			{
				image = ImageUtil.encodeImage(image);
				
				HttpClient client = new DefaultHttpClient();
				HttpParams params = client.getParams();
				HttpConnectionParams.setConnectionTimeout(params, 7500);
				HttpProtocolParams.setUserAgent(params, "Mozilla/5.0 (Windows; U; Windows NT 6.1; ru; rv:1.9.2.3) Gecko/20100401 Firefox/4.0 (.NET CLR 3.5.30729)");
				HttpClientParams.setRedirecting(params, false);
				HttpGet get = new HttpGet(image);
				HttpResponse execute = client.execute(get);
				
				int anHttpStatus = execute.getStatusLine().getStatusCode();
				
				if(anHttpStatus == 200)
				{
					result = image;
				}
			}
			catch (Throwable e)
			{
				//e.printStackTrace();
//				getLogger().error("Image servlet can not check image: " + image, e);
			}
		}
		
		response.sendRedirect(result);
	}
	
//	public static String getImageUrl(HttpServletRequest request, ProductResult result)
//	{
//  	  	return getImageUrl(request, result.getImageURL(), result.getImageName());
//	}
	
	public static String getImageUrl(HttpServletRequest request, String url, String imageName)
	{
	  StringBuffer imgBuff = new StringBuffer();
  	  imgBuff.append(request.getContextPath());
  	  imgBuff.append("/img/");
  	  imgBuff.append(imageName);
  	  imgBuff.append(".png");
  	  imgBuff.append("?v=");
  	  imgBuff.append(url);
  	  String imgUrl = imgBuff.toString();
  	  
  	  return imgUrl;
	}

	public static String getImageRszUrl(HttpServletRequest request, String url, String imageName, int w, int h)
	{
	  return getImageRszUrl(request.getContextPath(), url, imageName, w, h);
	}
	
	public static String getImageRszUrl(String rootPath, String url, String imageName, int w, int h)
	{
	  StringBuffer imgBuff = new StringBuffer();
	  
	  imgBuff.append(rootPath);
  	  imgBuff.append("/imgrsz/");
  	  imgBuff.append(imageName);
  	  imgBuff.append(".png");
  	  imgBuff.append("?w=");
  	  imgBuff.append(w);
  	  imgBuff.append("&h=");
  	  imgBuff.append(h);
  	  imgBuff.append("&v=");
  	  imgBuff.append(url);

  	  String imgUrl = imgBuff.toString();
  	  
  	  return imgUrl;
	}
}