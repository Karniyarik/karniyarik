package com.karniyarik.crawler.state;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.crawler.Crawler;

public class EndedCrawlerState extends AbstractCrawlerState
{

	public EndedCrawlerState(Crawler crawler)
	{
		super(crawler);
	}

	@Override
	public void start()
	{
		try
		{
			// getCrawler().setCrawlerStatistics(StatisticsDAO.resetStatistics(getCrawler().getSiteConfig()));

			getCrawler().preStart();

			// clear data remaining from previous crawls
			getCrawler().getVisitedLinks().clearAll();
			getCrawler().getLinksToVisitQueue().clearAll();

			// put the home page to the links to visit queue
			getCrawler().getLinksToVisitQueue().push(getCrawler().getSiteAnalyzer().getHomeURL());

			// get the starting urls
			List<String> startURLs = getCrawler().getSiteAnalyzer().getStartingURLs();

			// crawl the starting urls (add the new links)
			for (String url : startURLs)
			{
				getCrawler().getLinksToVisitQueue().push(url);
			}

			getCrawler().getLogger().info("Ended state crawler initialization completed...Begining crawling...");

			// state transition to crawling
			getCrawler().setState(getCrawler().getCrawlingCrawlerState());
			
			getCrawler().crawl();
		}
		catch (Throwable e)
		{
			// do nothing
			KarniyarikLogger.logException("Exception occured for " + getCrawler().getSiteConfig().getSiteName(), e, getCrawler().getLogger());

			getCrawler().getLogger().error("The crawler for " + getCrawler().getSiteConfig().getSiteName() + " is dead anymore.");

			getCrawler().getCrawlerStatistics().exceptionOccured(e);

			getCrawler().getCrawlerStatistics().crawlingFailed();
			getCrawler().sendFailureNotification("Fail during startup: " + ExceptionUtils.getStackTrace(e));
			getCrawler().setState(getCrawler().getEndedCrawlerState());
		}

	}

	@Override
	public void stop()
	{
		try
		{
			// getCrawler().flushCaches();
			// getCrawler().freeResources();
			getCrawler().saveStateThenFreeResources();

			getCrawler().getCrawlerStatistics().crawlingEnded();

		}
		catch (Throwable e)
		{
			getCrawler().getLogger().error("Error occured while trying to flush links to visit and visited link caches.", e);

			getCrawler().getCrawlerStatistics().crawlingFailed();

			getCrawler().sendFailureNotification("Error occured while trying to flush links to visit and visited link caches." + ExceptionUtils.getStackTrace(e));
		}
		finally
		{
			JobExecutorServiceUtil.sendJobExecutionStatJobToScheduler(getCrawler().getCrawlerStatistics());
		}

	}

}
