package com.karniyarik.crawler.util;

import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.karniyarik.common.log.KarniyarikLogger;

public class URLManager {

	private int splitIndex = -1;
	private String linkPrefix = null;

	//will be used for logging purposes
	private String siteName = null;
	private Set<String> exceptionalUrlSet;

	public URLManager(String linkPrefix, String siteName) {

		this.siteName = siteName;

		if (linkPrefix.endsWith("/")) {
			
			this.linkPrefix = linkPrefix.substring(0, linkPrefix.length() - 1);

		}
		else {

			this.linkPrefix = linkPrefix;

		}

		this.splitIndex = this.linkPrefix.length();

		this.exceptionalUrlSet = new TreeSet<String>();
	}

	public String constructURL(String url) {
		
		if(!url.startsWith("http"))
		{
			return linkPrefix + url;	
		}
		return url;
	}

	//every url is broke into two pieces, such as:
	// http://www.hepsiburada.com and /products.asp?productID=12
	public String breakURL(String url) {

		String result = null;
		
		try {
			result = url.substring(splitIndex);

			//if a url starts with https we ignore and log it
			if (!result.isEmpty() && !result.startsWith("/")) {
				throw new RuntimeException();
			}
		}
		catch (Exception e){

			result = "/";

			if (exceptionalUrlSet.add(url)) {
				KarniyarikLogger.logException("can't split url " + url + " for split index = " + splitIndex, e, Logger.getLogger(siteName));
			}
		}
		return result;
	}
}
