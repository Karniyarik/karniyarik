package com.karniyarik.web.util;

import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpCacheUtil
{
	public static void setStaticResponseCacheAttributes(HttpServletResponse response, HttpServletRequest request)
	{
		setResponseCacheAttributes(response, request, 7*24);
	}
	
	public static void setSearchResponseCacheAttributes(HttpServletResponse response, HttpServletRequest request)
	{
		setResponseCacheAttributes(response, request, 12);
	}
	
	public static void setIndexResponseCacheAttributes(HttpServletResponse response, HttpServletRequest request)
	{
		setResponseCacheAttributes(response, request, 1);
	}

	public static void setNoCacheAttributes(HttpServletResponse response, HttpServletRequest request)
	{
		setResponseCacheAttributes(response, request, 0);
	}

	public static void setResponseCacheAttributes(HttpServletResponse response, HttpServletRequest request, int hours)
	{
		if(hours > 0)
		{
			TimeZone timeZone = TimeZone.getTimeZone("GMT-0");
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(timeZone);
			cal.add(Calendar.HOUR_OF_DAY, hours);

			String gmtDate = cal.getTime().toGMTString();
			long maxage = hours*60L*60;
			String cacheControlStr = "must-revalidate, public, max-age=" + maxage;
			
			response.setHeader("Cache-Control",cacheControlStr); //HTTP 1.1
			response.setDateHeader("Expires", cal.getTimeInMillis()); //prevents caching at the proxy server

			request.setAttribute("k-cache-control", cacheControlStr);
			request.setAttribute("k-cache-expires", gmtDate);			
		}
		else
		{
			String cacheControlStr = "no-cache, must-revalidate, public, max-age=0";
			
			response.setHeader("Cache-Control",cacheControlStr); //HTTP 1.1
			response.setDateHeader("Expires", 0); //prevents caching at the proxy server

			request.setAttribute("k-cache-control", cacheControlStr);
			request.setAttribute("k-cache-expires", 0);
		}
	}

}
