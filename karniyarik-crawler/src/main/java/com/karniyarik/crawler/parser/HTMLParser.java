package com.karniyarik.crawler.parser;

import java.util.Set;

public interface HTMLParser
{
	public Set<String> getLinks(String content, String currentURL);
}
