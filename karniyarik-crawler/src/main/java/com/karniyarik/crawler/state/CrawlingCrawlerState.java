package com.karniyarik.crawler.state;

import com.karniyarik.crawler.Crawler;

public class CrawlingCrawlerState extends AbstractCrawlerState
{

	public CrawlingCrawlerState(Crawler crawler)
	{
		super(crawler);
	}

	@Override
	public void pause()
	{
		getCrawler().getCrawlerStatistics().crawlingPausing();
		getCrawler().setState(getCrawler().getPausedCrawlerState());
	}

	@Override
	public void stop()
	{
		getCrawler().getCrawlerStatistics().crawlingEnding();
		getCrawler().setState(getCrawler().getEndedCrawlerState());
	}

	@Override
	public String popLinkToVisit()
	{
		// get next link to visit
		String url = getCrawler().getLinksToVisitQueue().pop();

		// termination condition: no links to analyze remained
		if (url == null)
		{
			url = getCrawler().getLinksToVisitQueue().popErrorneous();

			getCrawler().setCrawlingErrorneous(Boolean.TRUE);

			if (url == null)
			{
				getCrawler().getCrawlerStatistics().crawlingEnding();
				getCrawler().setState(getCrawler().getEndedCrawlerState());
			}
		}

		return url;
	}

	@Override
	public void handleExcessiveExceptions(String message)
	{
		getCrawler().getLogger().error(message);
		getCrawler().setState(getCrawler().getPausedCrawlerState());
	}
}