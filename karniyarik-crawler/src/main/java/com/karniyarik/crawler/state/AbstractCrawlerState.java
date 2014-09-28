package com.karniyarik.crawler.state;

import com.karniyarik.crawler.Crawler;

public abstract class AbstractCrawlerState implements CrawlerState
{
	private final Crawler crawler;
	
	public AbstractCrawlerState(Crawler crawler) {
		this.crawler = crawler;
	}
	
	@Override
	public void pause()
	{
//		throw new UnsupportedOperationException("Current state " + crawler.getState() + " requested operation pause");
	}

	@Override
	public void start()
	{
//		throw new UnsupportedOperationException("Current state " + crawler.getState() + " requested operation start");
	}

	@Override
	public void stop()
	{
//		throw new UnsupportedOperationException("Current state " + crawler.getState() + " requested operation stop");
	}

	@Override
	public String popLinkToVisit()
	{
//		throw new UnsupportedOperationException("Current state " + crawler.getState() + " requested operation popLinkToVisit");
		return null;
	}

	@Override
	public void handleExcessiveExceptions(String message)
	{
		
	}
	
	public final Crawler getCrawler() {
		return crawler;
	}
	
}
