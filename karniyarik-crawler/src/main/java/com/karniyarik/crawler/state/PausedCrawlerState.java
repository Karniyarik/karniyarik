package com.karniyarik.crawler.state;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.crawler.Crawler;

public class PausedCrawlerState extends AbstractCrawlerState
{

	public PausedCrawlerState(Crawler crawler)
	{
		super(crawler);
	}

	@Override
	public void start()
	{
		try
		{
			getCrawler().preStart();

			// in any case restarted/started/resumed
			// load the visited link cache
			// getCrawler().getVisitedLinks().loadCache();

			// TODO: Load links to visit queue from database
			// getCrawler().getLinksToVisitQueue().loadCache();

			// added by km.
			getCrawler().restoreLastState();

			getCrawler().getLogger().info("Paused state crawler reconstruction completed...Begining crawling...");

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
			getCrawler().saveStateThenFreeResources();

			if (getCrawler().checkSuccessiveExceptions())
			{
				getCrawler().getCrawlerStatistics().crawlingFailed();
				getCrawler().sendFailureNotification("Too many successive exceptions occured.");
			}
			else if (getCrawler().checkSuccessiveWaitTryFails())
			{
				getCrawler().getCrawlerStatistics().crawlingFailed();
				getCrawler().sendFailureNotification("Too many wait and try exceptions occured.");
			}
			else
			{
				getCrawler().getCrawlerStatistics().crawlingPaused();
			}

		}
		catch (Throwable e)
		{
			getCrawler().getLogger().error("Error occured while trying to flush links to visit and visited link caches.", e);

			getCrawler().getCrawlerStatistics().crawlingFailed();

			getCrawler().sendFailureNotification("Error occured while trying to flush links to visit and visited link caches." + ExceptionUtils.getStackTrace(e));

			getCrawler().setState(getCrawler().getEndedCrawlerState());
		}
		finally
		{
			JobExecutorServiceUtil.sendJobExecutionStatJobToScheduler(getCrawler().getCrawlerStatistics());
		}

	}

}
