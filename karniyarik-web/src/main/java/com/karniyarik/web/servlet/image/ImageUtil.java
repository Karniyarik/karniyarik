package com.karniyarik.web.servlet.image;

import java.net.URLDecoder;

import org.apache.commons.httpclient.util.URIUtil;

public class ImageUtil {
	public static String DEFAULT_ENCODING = "UTF-8";
	
	public static String encodeImage(String url)
	{
		try {
			url = URLDecoder.decode(url, DEFAULT_ENCODING);
			return URIUtil.encodeQuery(url, DEFAULT_ENCODING);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} 
	}
}
