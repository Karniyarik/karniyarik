package com.karniyarik.web.citydeals;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.filters.StringInputStream;

import com.karniyarik.crawler.HttpClientContentFetcher;

public class IPGeoLookup {
	public IPGeoLookup() {
		
	}
	
	public String getCity(HttpServletRequest request)
	{
		String result = null;
		try {
			String ip = request.getRemoteAddr();
			//ip = "195.175.224.215";
			HttpClientContentFetcher client = new HttpClientContentFetcher("karniyarik", 2000, "UTF-8");
			String content = client.getContent("http://api.hostip.info/get_html.php?ip=" + ip);
			Properties prop = new Properties();
			prop.load(new StringInputStream(content));
			result = prop.getProperty("City");
			//http://api.hostip.info/get_html.php?ip=195.175.224.215
		} catch (Throwable e) {
			int a =5;
		}
		return result;
	}
}