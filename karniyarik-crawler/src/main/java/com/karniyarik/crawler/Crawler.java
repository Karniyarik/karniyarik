package com.karniyarik.crawler;

import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CrawlerConfig;
import com.karniyarik.common.exception.ExceptionVO;
import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.notifier.MailNotifier;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.DateUtil;
import com.karniyarik.common.util.JerseyUtil;
import com.karniyarik.crawler.exception.KarniyarikCrawlerURLFetchException;
import com.karniyarik.crawler.linkgraph.Link;
import com.karniyarik.crawler.linkgraph.VisitedLinksList;
import com.karniyarik.crawler.parser.HTMLParser;
import com.karniyarik.crawler.parser.XQueryHtmlParser;
import com.karniyarik.crawler.queue.LinksToVisitQueue;
import com.karniyarik.crawler.site.SiteLinkAnalyzer;
import com.karniyarik.crawler.state.CrawlerState;
import com.karniyarik.crawler.state.CrawlingCrawlerState;
import com.karniyarik.crawler.state.EndedCrawlerState;
import com.karniyarik.crawler.state.IdleCrawlerState;
import com.karniyarik.crawler.state.PausedCrawlerState;
import com.karniyarik.crawler.util.URLAnalyzer;
import com.karniyarik.crawler.util.URLManager;
import com.karniyarik.parser.core.ParserSiteTool;

public class Crawler
{
	private final SiteLinkAnalyzer			siteAnalyzer;
	private final HTMLParser				htmlParser;
	private final SimpleContentFetcher		contentFetcher;
	private final CrawlerConfig				crawlerConfig;
	private final SeleniumContentFetcher	seleniumContentFetcher;
	private final LinksToVisitQueue			linksToVisitQueue;
	private final VisitedLinksList			visitedLinks;
	private final ParserSiteTool			parserSiteTool;
	private final SiteConfig				siteConfig;
	private final URLAnalyzer				urlAnalyzer;
	private final Logger					logger;
	private final URLManager				urlManager;
	private final JobExecutionStat			crawlerStatistics;

	private final Object					crawlerWaitLock;
	private final CrawlerState				idleCrawlerState;
	private final CrawlerState				pausedCrawlerState;
	private final CrawlerState				crawlingCrawlerState;
	private final CrawlerState				endedCrawlerState;

	private CrawlerState					state;
	private int								successiveExceptionCount	= 0;
	private int								successiveWaitTryFail		= 0;
	private boolean							crawlingErrorneous			= false;
	private boolean							flushTimerRequest			= false;
	private Timer							stateSaverTimer				= null;

	public Crawler(Logger logger, CrawlerConfig crawlerConfig, SiteConfig siteConfig, URLAnalyzer urlAnalyzer, JobExecutionStat crawlerStatistics, URLManager urlManager,
			ParserSiteTool parserSiteTool, SiteLinkAnalyzer siteLinkAnalyzer, XQueryHtmlParser htmlParser, LinksToVisitQueue linksToVisitQueue, VisitedLinksList visitedLinksList,
			SimpleContentFetcher simpleContentFetcher, SeleniumContentFetcher seleniumContentFetcher)
	{
		this.idleCrawlerState = new IdleCrawlerState(this);
		this.pausedCrawlerState = new PausedCrawlerState(this);
		this.crawlingCrawlerState = new CrawlingCrawlerState(this);
		this.endedCrawlerState = new EndedCrawlerState(this);

		this.logger = logger;
		this.urlManager = urlManager;
		this.crawlerConfig = crawlerConfig;
		this.siteConfig = siteConfig;
		this.urlAnalyzer = urlAnalyzer;
		this.crawlerStatistics = crawlerStatistics;
		this.parserSiteTool = parserSiteTool;
		this.siteAnalyzer = siteLinkAnalyzer;
		this.htmlParser = htmlParser;
		this.linksToVisitQueue = linksToVisitQueue;
		this.visitedLinks = visitedLinksList;
		this.contentFetcher = simpleContentFetcher;
		this.seleniumContentFetcher = seleniumContentFetcher;

		this.crawlerWaitLock = new Object();

	}

	public final void preStart()
	{

		// links to visit queue, and visited links are nulled out
		// at the end of crawl method. Each time crawler begins initialize
		// them.
		getLogger().info("Crawler for " + getSiteConfig().getSiteName() + " initializing...");

		setCrawlingErrorneous(false);
	}

	public final void start()
	{
		state.start();
	}

	public final void pause()
	{
		state.pause();
	}

	public final void stop()
	{
		state.stop();
	}

	private void initStateSaver()
	{
		stateSaverTimer = new Timer();

		// introduce a random delay between 0-15 (minutes);
		int randomDelay = new Random(getSiteConfig().getSiteName().hashCode()).nextInt(20);

		long aDelayTime = crawlerConfig.getFlushPeriod() * 60 * 1000 + randomDelay * 60 * 1000;

		stateSaverTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if (state.equals(crawlingCrawlerState))
				{
					if (requestPermissionToFlush())
					{
						try
						{
							linksToVisitQueue.saveState();
							visitedLinks.saveState();
							JobExecutorServiceUtil.sendJobExecutionStatJobToScheduler(getCrawlerStatistics());
						}
						catch (Exception e)
						{
							// do nothing you will try again
						}

						synchronized (crawlerWaitLock)
						{
							crawlerWaitLock.notifyAll();
						}
					}
				}
			}

			public boolean requestPermissionToFlush()
			{
				synchronized (crawlerWaitLock)
				{
					flushTimerRequest = true;

					try
					{
						crawlerWaitLock.wait();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					flushTimerRequest = false;
				}

				return true;
			}

		}, aDelayTime, aDelayTime);
	}

	public final void crawl()
	{
		getCrawlerStatistics().crawlerStarted();

		initStateSaver();

		String aURL = null;
		String aContent = null;
		Set<String> extractedLinkSet = null;

		long aDelay = getSiteConfig().getCrawlDelay();

		while (getState().equals(getCrawlingCrawlerState()))
		{
			try
			{
				// pop a link to visit, if there is not the state will change
				aURL = state.popLinkToVisit();

				if (StringUtils.isBlank(aURL))
				{
					continue;
				}

				// check if link is visited
				Link aLink = visitedLinks.isVisited(aURL);

				if (aLink.isVisited())
				{
					continue;
				}

				// wait a little before each visit operation
				Thread.sleep(aDelay);

				if (checkPermissionToFlush())
				{
					triggerFlushPermission();
				}

				// fetch content
				aContent = fetchContent(aLink);

				if (aContent != null)
				{
					parserSiteTool.parse(aURL, aLink.getURL(), aContent, new Date(), visitedLinks);
					// get link
					visitedLinks.visit(aLink);

					aDelay = calculateDelay();

					if (seleniumContentFetcher != null && siteAnalyzer.isConstructedPagingUrl(aURL))
					{
						aURL = siteAnalyzer.extractPagingUrlParent(aURL);
					}

					// get new links and add to the queue
					extractedLinkSet = htmlParser.getLinks(aContent, aURL);

					extractedLinkSet = getUrlAnalyzer().cleanUrlSet(extractedLinkSet);

					extractedLinkSet = getUrlAnalyzer().removeTraps(extractedLinkSet);

					extractedLinkSet.addAll(siteAnalyzer.generateUrls(aURL));

					crawlChildren(extractedLinkSet, aURL);

					successiveExceptionCount = 0;
				}
			}
			catch (Throwable e)
			{
				handleException(e, null, true, true);
			}
		}

		parserSiteTool.shutDownAndSave();

		stateSaverTimer.cancel();

		state.stop();

		System.gc();
	}

	public boolean checkSuccessiveWaitTryFails()
	{
		return successiveWaitTryFail > 10;
	}

	public boolean checkSuccessiveExceptions()
	{
		return successiveExceptionCount > crawlerConfig.getMaxSuccessiveExceptionCount();
	}

	private boolean checkPermissionToFlush()
	{
		synchronized (crawlerWaitLock)
		{
			return flushTimerRequest;
		}
	}

	private void triggerFlushPermission()
	{
		synchronized (crawlerWaitLock)
		{
			flushTimerRequest = false;

			crawlerWaitLock.notifyAll();

			try
			{
				crawlerWaitLock.wait();
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	private String fetchContent(Link aLink)
	{
		String aContent = null;

		boolean aForLoopBreak = false;

		int tryCountForWait = 3;
		for (int anIndex = 0; anIndex < tryCountForWait && !aForLoopBreak; anIndex++)
		{
			try
			{
				long aFetchStartTime = System.currentTimeMillis();

				if (seleniumContentFetcher != null && siteAnalyzer.isConstructedPagingUrl(aLink.getURL()))
				{
					aContent = seleniumContentFetcher.getContent(getUrlManager().constructURL(siteAnalyzer.extractPagingUrlParent(aLink.getURL())), siteAnalyzer.extractPagingUrl(aLink.getURL()));
				}
				else
				{

					aContent = contentFetcher.getContent(getUrlManager().constructURL(aLink.getURL()));
				}

				long aFetchEndTime = System.currentTimeMillis();

				aLink.setFetchDate(DateUtil.getCurrentLocalDate());

				aLink.setFetchTime(aFetchEndTime - aFetchStartTime);

				getCrawlerStatistics().fetchTimeGathered(aLink.getFetchTime());

				aForLoopBreak = true;

				successiveWaitTryFail = 0;

				break;
			}
			catch (KarniyarikCrawlerURLFetchException e)
			{
				aForLoopBreak = handleURLFetchException(aLink, tryCountForWait, anIndex, e);
			}
			catch (Throwable e)
			{
				// cannot handle i dont know the exceptin
				handleException(e, "Cannot fetch content, have no idea about the reason", true, true);
				visitedLinks.visit(aLink);
				aForLoopBreak = true;
			}
		}

		return aContent;
	}

	private long calculateDelay()
	{
		int aConfigDelay = getSiteConfig().getCrawlDelay();

		long anAvgFetch = getCrawlerStatistics().getWindowedAvgFetchTime();

		long aDelay = aConfigDelay;

		if ((anAvgFetch / 3) > aConfigDelay)
		{
			aDelay = anAvgFetch / 3;
		}

		return aDelay;
	}

	private void crawlChildren(Set<String> childrenLinkSet, String parentUrl)
	{
		for (String aURL : childrenLinkSet)
		{
			if (aURL != null && !siteAnalyzer.isIgnored(aURL))
			{
				aURL = siteAnalyzer.correctURL(aURL, parentUrl);

				aURL = getUrlAnalyzer().clean(aURL);

				if (seleniumContentFetcher != null && siteAnalyzer.isPagingUrl(aURL))
				{
					aURL = siteAnalyzer.generateSpecialPagingUrl(parentUrl, aURL);
				}

				if (getUrlAnalyzer().isHostsEqual(aURL, siteAnalyzer.getHomeURL()))
				{
					// getCrawlerStatistics().linksFound(1);

					Link aLink = visitedLinks.isVisited(aURL);

					if (!aLink.isVisited())
					{
						linksToVisitQueue.push(aURL);
					}
				}
			}
		}
	}

	public void saveState()
	{

		visitedLinks.saveState();
		linksToVisitQueue.saveState();
	}

	public void restoreLastState()
	{
		visitedLinks.restoreLastState();
		linksToVisitQueue.restoreLastState();
	}

	public void freeResources()
	{
		visitedLinks.freeResources();
		linksToVisitQueue.freeResources();

		if (seleniumContentFetcher != null)
		{
			seleniumContentFetcher.stop();
		}
	}

	public void saveStateThenFreeResources()
	{

		saveState();
		freeResources();
	}

	public void handleException(Throwable e, String aMessage, boolean isLog, boolean increaseCount)
	{
		crawlerStatistics.exceptionOccured(e);

		if (isLog)
		{
			String aLogMessage = siteConfig.getSiteName() + ": ";

			if (aMessage != null)
			{
				aLogMessage += aMessage;
			}

			KarniyarikLogger.logExceptionSummary(aLogMessage, e, logger);
		}

		if (increaseCount)
		{
			successiveExceptionCount++;

			if (checkSuccessiveExceptions())
			{
				state.handleExcessiveExceptions("Too many successive exceptions occured, pausing the crawler for " + siteConfig.getSiteName());
			}
		}
	}

	private boolean handleURLFetchException(Link aLink, int tryCountForWait, int anIndex, KarniyarikCrawlerURLFetchException e)
	{
		boolean aForLoopBreak = false;

		switch (e.getErrorCode())
		{
		case KarniyarikCrawlerURLFetchException.PASS:
			handleException(e, " Passing url" + e.getExceptionSummary(), true, true);
			visitedLinks.visit(aLink);
			aForLoopBreak = true;
			break;

		case KarniyarikCrawlerURLFetchException.TRY_LATER:
			// mCrawlingErrorneous is set when a errorneous link is popped from
			// links to visit queue
			// after pop the system calls fetchContent function
			// therefore this variable represents the same link for both pop and
			// fetch
			// if it has failed before and failed again, i have to pass it.
			if (crawlingErrorneous)
			{
				handleException(e, " Errorneous link failed again. passing." + e.getExceptionSummary(), true, false);
				// mark as visited so i do not visit it again.
				visitedLinks.visit(aLink);
			}
			else
			{
				// push to the erroneous queue for later investigation
				linksToVisitQueue.pushErrorneous(aLink.getURL());

				// increase exception count but do not log it.
				handleException(e, " Errorneous link failed, pushing to errorneous." + e.getExceptionSummary(), false, true);
			}

			// required since this type of exception does not need a for loop
			// (multiple tries)
			aForLoopBreak = true;
			break;
		case KarniyarikCrawlerURLFetchException.WAIT_AND_TRY_LATER:

			if (anIndex < tryCountForWait - 1)
			{
				long aWaitTime = (anIndex + 1) * (getCrawlerConfig().getSleepTime() * 60 * 1000);

				// log the exception, and inform about sleep
				handleException(e, "Server is down, will sleep for (" + ((anIndex + 1) * getCrawlerConfig().getSleepTime()) + " minutes) and try again, try no:" + anIndex + e.getExceptionSummary(),
						true, true);

				try
				{
					getLogger().info("Crawler for " + getSiteConfig().getSiteName() + " taking a nap for " + aWaitTime + " milliseconds.");
					Thread.sleep(aWaitTime); // wait 20x for each turn
					getLogger().info("Crawler for " + getSiteConfig().getSiteName() + " waking up from " + aWaitTime + " sweet sleep.");
				}
				catch (InterruptedException e1)
				{
					aForLoopBreak = true;
					getLogger().info("Thread sleep in fetch content wait and try is " + "interrupted!!!");
				}

			}
			else
			{
				// mark it as visited so i do not visit it again
				visitedLinks.visit(aLink);

				successiveWaitTryFail++;

				if (checkSuccessiveWaitTryFails())
				{
					state.handleExcessiveExceptions("Too many wait and try fails occured, pausing the crawler for " + siteConfig.getSiteName());
				}
				else
				{
					handleException(e, "Server is down, tried 3 times, couldnt fetch content, " + "passing this content" + e.getExceptionSummary(), true, true);
				}

				// exit the loop
				aForLoopBreak = true;
			}

			break;
		}

		return aForLoopBreak;
	}

	public final void sendFailureNotification(String failReason)
	{
		StringBuffer summary = new StringBuffer();
		summary.append("Fail Reason : ");
		summary.append(failReason);
		summary.append("\nSummary:");
		summary.append("\nTotal exception count: ");
		summary.append(getCrawlerStatistics().getTotalExceptionCount());
		summary.append("\nTotal links to visit : ");
		summary.append(getCrawlerStatistics().getTotalLinksToVisit());
		summary.append("\nTotal visited links : ");
		summary.append(getCrawlerStatistics().getTotalVisitedLinks());
		summary.append("\nAverage fetch time : ");
		summary.append(getCrawlerStatistics().calculateAvgFetchTime());
		summary.append("\nWindowed average fetch time : ");
		summary.append(getCrawlerStatistics().getWindowedAvgFetchTime());
		summary.append("\nTotal Fetch time: ");
		summary.append(getCrawlerStatistics().getTotalFetchTime());
		summary.append("\nStart date : ");
		summary.append(getCrawlerStatistics().getStartDate());
		summary.append("\nEnd date : ");
		summary.append(getCrawlerStatistics().getEndDate());
		
		ExceptionVO vo = new ExceptionVO();
		vo.setDate(new Date());
		vo.setIdentifier("crawler-failed");
		vo.setTitle("Crawler for " + getSiteConfig().getSiteName() + " failed.");
		vo.setMessage(summary.toString());
		ExceptionNotifier.sendException(vo);
		//new MailNotifier().sendTextMail(, summary.toString());
	}

	public final CrawlerState getState()
	{
		return state;
	}

	public final void setState(CrawlerState state)
	{
		this.state = state;
	}

	public final CrawlerState getIdleCrawlerState()
	{
		return idleCrawlerState;
	}

	public final CrawlerState getPausedCrawlerState()
	{
		return pausedCrawlerState;
	}

	public final CrawlerState getCrawlingCrawlerState()
	{
		return crawlingCrawlerState;
	}

	public final CrawlerState getEndedCrawlerState()
	{
		return endedCrawlerState;
	}

	public final void setCrawlingErrorneous(boolean crawlingErrorneous)
	{
		this.crawlingErrorneous = crawlingErrorneous;
	}

	public final LinksToVisitQueue getLinksToVisitQueue()
	{
		return linksToVisitQueue;
	}

	public final VisitedLinksList getVisitedLinks()
	{
		return visitedLinks;
	}

	public final SiteLinkAnalyzer getSiteAnalyzer()
	{
		return siteAnalyzer;
	}

	public int getSuccessiveExceptionCount()
	{
		return successiveExceptionCount;
	}

	public SiteConfig getSiteConfig()
	{
		return siteConfig;
	}

	public URLAnalyzer getUrlAnalyzer()
	{
		return urlAnalyzer;
	}

	public JobExecutionStat getCrawlerStatistics()
	{
		return crawlerStatistics;
	}

	public Logger getLogger()
	{
		return logger;
	}

	public URLManager getUrlManager()
	{
		return urlManager;
	}

	public CrawlerConfig getCrawlerConfig()
	{
		return crawlerConfig;
	}
}
