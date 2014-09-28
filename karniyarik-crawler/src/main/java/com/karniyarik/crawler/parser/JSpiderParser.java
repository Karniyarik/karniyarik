package com.karniyarik.crawler.parser;

import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class JSpiderParser implements HTMLParser
{
	private URLFinder	mURLFinder	= null;

	public JSpiderParser()
	{
		mURLFinder = new URLFinder();
	}

	@Override
	public Set<String> getLinks(String aContent, String aCurrentURL)
	{
		Set<String> links = null;
		try
		{
			links = mURLFinder.findURLs(aContent, aCurrentURL);
		}
		catch (Exception e)
		{
			links = new TreeSet<String>();
			getLogger().error("Could not get links content " + aContent + " current url " + aCurrentURL, e);
		}
		
		return links;
	}
	
	private Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}
}
