package com.karniyarik.web.servlet;

import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpCacheUtil
{
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
		}
		else
		{
			String cacheControlStr = "no-cache, must-revalidate, public, max-age=0";
			
			response.setHeader("Cache-Control",cacheControlStr); //HTTP 1.1
			response.setDateHeader("Expires", 0); //prevents caching at the proxy server
		}
	}

}
