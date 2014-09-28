package com.karniyarik.crawler.state;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.crawler.Crawler;

public class IdleCrawlerState extends AbstractCrawlerState
{
	public IdleCrawlerState(Crawler crawler)
	{
		super(crawler);
	}

	@Override
	public void start()
	{
		try
		{
			getCrawler().preStart();

			// put the home page to the links to visit queue
			getCrawler().getLinksToVisitQueue().push(getCrawler().getSiteAnalyzer().getHomeURL());

			// get the starting urls
			List<String> startURLs = getCrawler().getSiteAnalyzer().getStartingURLs();

			// crawl the starting urls (add the new links)
			for (String url : startURLs)
			{
				getCrawler().getLinksToVisitQueue().push(url);
			}

			getCrawler().getLogger().info("Idle state crawler initialization completed...Begining crawling...");

			// state transition to crawling
			getCrawler().setState(getCrawler().getCrawlingCrawlerState());
			
			getCrawler().crawl();
		}
		catch (Throwable e)
		{
			// do nothing
			KarniyarikLogger.logException("Exception occured for "
					+ getCrawler().getSiteConfig().getSiteName(), e, getCrawler().getLogger());

			getCrawler().getLogger().error(
					"The crawler for " + getCrawler().getSiteConfig().getSiteName() + " is dead anymore.");

			getCrawler().getCrawlerStatistics().exceptionOccured(e);

			getCrawler().getCrawlerStatistics().crawlingFailed();
			getCrawler().sendFailureNotification("Fail during startup: " + ExceptionUtils.getStackTrace(e));
			getCrawler().setState(getCrawler().getEndedCrawlerState());
		}
		
	}
	
}
