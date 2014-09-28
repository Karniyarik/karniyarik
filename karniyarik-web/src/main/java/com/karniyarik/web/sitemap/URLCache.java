package com.karniyarik.web.sitemap;

import java.util.ArrayList;
import java.util.List;

public class URLCache
{
	public List<String> urls = new ArrayList<String>();
	
	public boolean exists(String query)
	{
		return urls.contains(query);
	}
	
	public void add(String query)
	{
		urls.add(query);
	}
}
