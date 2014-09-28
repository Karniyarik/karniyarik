package com.karniyarik.web.mobile;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.wurfl.wng.ImageDispenser;
import net.sourceforge.wurfl.wng.WNGDevice;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class MobileUtil
{
	public static String DEVICEID = "kdeviceid";
	public static String DEVICEBRAND = "kdevicebrand";
	public static String DEVICEOS = "kdeviceos";
	public static String TOUCHSCREEN = "ktouch";
	public static String CONTEXT = "kcontextpath";
	
	public static String getURL(HttpServletRequest request)
	{
		String uri = request.getRequestURI();
		if(StringUtils.isNotBlank(request.getQueryString()))
		{
			uri = uri + "?" + request.getQueryString();
		}
		
		return StringEscapeUtils.escapeHtml(uri);
	}
	
	public static String replaceServlet(HttpServletRequest request, String source, String target)
	{
		String uri= getURL(request);
		uri = uri.replace(source,target);
		return uri;
	}
	
	public static String appendParam(String uri, String name, String value)
	{
		if(!uri.contains("?"))
		{
			uri += "?";
		}
		else
		{
			uri += "&amp;";
		}
		uri += name + "=" + value; 
		return uri;
	}
	
	public static String removeParam(String uri, String name, String value)
	{
		String match = name+"="+value;
		if(uri.contains(match))
		{
			uri = uri.replace(match, "");
			uri = uri.replaceAll("&amp;&amp;", "&amp;");
			if(uri.endsWith("&amp;"))
			{
				uri = uri.substring(0,uri.length()-5);
			}
		}
		return uri;
	}
	
	public static void setDispensers(HttpServletRequest request)
	{
		WNGDevice device = (WNGDevice) request.getAttribute("device");
		ImageDispenser logo = new LogoDispenser(device);
		request.setAttribute("logo", logo);
		logo = new SearchLogoDispenser(device);
		request.setAttribute("slogo", logo);
	}
	
	public static boolean isIPodOrDerivative(String string)
	{
		if(string != null && (string.contains("iphone") || string.contains("ipod") || string.contains("ipad")))
		{
			return true;
		}
		
		return false;
	}
	
	public static boolean isAndroid(String string)
	{
		if(string != null && (string.contains("android")))
		{
			return true;
		}
		
		return false;
	}
}
