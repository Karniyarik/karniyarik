package com.karniyarik.crawler.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class SiteRule
{
	private SiteLinkAnalyzer	mSiteLinkAnalyzer	= null;

	public SiteLinkAnalyzer getSiteLinkAnalyzer()
	{
		return mSiteLinkAnalyzer;
	}

	public void setSiteLinkAnalyzer(SiteLinkAnalyzer aSiteLinkAnalyzer)
	{
		mSiteLinkAnalyzer = aSiteLinkAnalyzer;
	}

	public String correctURL(String aURL, String aParentURL)
	{
		return aURL;
	}

	public List<String> populateStartingURLs()
	{
		// to prevent returning null value
		// return empty list by default
		return new ArrayList<String>(0);
	}

	public boolean isIgnored(String aURL, boolean aFoundResult)
	{
		return aFoundResult;
	}

	public Set<String> generateUrls(String aURL) {

		return new TreeSet<String>();
	}
}