package com.karniyarik.crawler;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CrawlerConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionState;
import com.karniyarik.common.util.TableNameUtil;
import com.karniyarik.crawler.linkgraph.VisitedLinkCache;
import com.karniyarik.crawler.linkgraph.VisitedLinksDAO;
import com.karniyarik.crawler.linkgraph.VisitedLinksList;
import com.karniyarik.crawler.parser.XQueryHtmlParser;
import com.karniyarik.crawler.queue.LinksToVisitFileDAO;
import com.karniyarik.crawler.queue.LinksToVisitQueue;
import com.karniyarik.crawler.site.SiteLinkAnalyzer;
import com.karniyarik.crawler.state.CrawlerState;
import com.karniyarik.crawler.util.CrawlerLoggerProvider;
import com.karniyarik.crawler.util.RobotsTxtAnalyzer;
import com.karniyarik.crawler.util.URLAnalyzer;
import com.karniyarik.crawler.util.URLManager;
import com.karniyarik.parser.core.ParserSiteTool;
import com.karniyarik.parser.core.ParserSiteToolFactory;

public class CrawlerFactory
{

	private final ParserSiteToolFactory	parserFactory;

	public CrawlerFactory()
	{
		this.parserFactory = new ParserSiteToolFactory();
	}

	public Crawler createCrawler(SiteConfig siteConfig, JobExecutionStat crawlerStatistics)
	{
		Crawler crawler = null;
//		if (crawlerStatistics.getStatus().ordinal() >= JobExecutionState.IDLE.ordinal() && crawlerStatistics.getStatus().ordinal() <= JobExecutionState.CRAWLING_FAILED.ordinal())
//		{
			Logger logger = new CrawlerLoggerProvider(siteConfig.getSiteName()).getLogger();

			CrawlerConfig crawlerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig();
			URLAnalyzer urlAnalyzer = new URLAnalyzer();
			URLManager urlManager = new URLManager(siteConfig.getUrl(), siteConfig.getSiteName());

			ParserSiteTool parserSiteTool = parserFactory.createParserSiteTool(siteConfig, crawlerStatistics);

			RobotsTxtAnalyzer robotsTxtAnalyzer = new RobotsTxtAnalyzer();
			SiteLinkAnalyzer siteLinkAnalyzer = new SiteLinkAnalyzer(siteConfig.getUrl(), siteConfig.getIgnoreList(), siteConfig.getPagingRule(), siteConfig.getRuleClassName(), crawlerConfig
					.getCommonIgnoreRuleList(), robotsTxtAnalyzer.getDisallowedUrlList(), logger);

			XQueryHtmlParser htmlParser = new XQueryHtmlParser();

			ContentFetcherFactory contentFetcherFactory = new ContentFetcherFactory(crawlerConfig.getBotName(), crawlerConfig.getURLFetchTimeout(), siteConfig.getSiteEncoding(), siteConfig.getUrl());

			SimpleContentFetcher simpleContenFetcher = contentFetcherFactory.createSimpleContentFetcher();

			SeleniumContentFetcher seleniumContentFetcher = null;
			if (StringUtils.isNotBlank(siteConfig.getPagingRule()))
			{
				seleniumContentFetcher = contentFetcherFactory.createSeleniumContentFetcher();
			}

			LinksToVisitFileDAO linksToVisitDao = new LinksToVisitFileDAO(getLinksToVisitDirectory(crawlerConfig.getLinksToVisitDirectory(), siteConfig.getSiteName()), logger);

			LinksToVisitQueue linksToVisitQueue = new LinksToVisitQueue(crawlerConfig.getQueueSize(), crawlerConfig.getLinksToVisitBlockSize(), linksToVisitDao, crawlerStatistics, urlManager, logger);

			VisitedLinksDAO visitedLinksDao = new VisitedLinksDAO(siteConfig.getSiteName(), TableNameUtil.createLinksVisitedTableName(siteConfig.getSiteName()), crawlerStatistics, logger);

			VisitedLinkCache visitedLinkCache = new VisitedLinkCache(crawlerConfig.getVisitedCacheSize(), crawlerStatistics, logger, visitedLinksDao);

			VisitedLinksList visitedLinksList = new VisitedLinksList(visitedLinkCache, urlManager, crawlerStatistics, logger);

			crawler = new Crawler(logger, crawlerConfig, siteConfig, urlAnalyzer, crawlerStatistics, urlManager, parserSiteTool, siteLinkAnalyzer, htmlParser, linksToVisitQueue, visitedLinksList,
					simpleContenFetcher, seleniumContentFetcher);

			setCurrentState(crawlerStatistics.getStatus(), crawler, siteConfig);
//		}

		return crawler;
	}

	private void setCurrentState(JobExecutionState crawlerState, Crawler crawler, SiteConfig siteConfig)
	{
		CrawlerState state = null;
		switch (crawlerState)
		{
		case IDLE:
			state = crawler.getIdleCrawlerState();
			break;
		case CRAWLING:
			state = crawler.getCrawlingCrawlerState();
			break;
		case CRAWLING_PAUSED:
			state = crawler.getPausedCrawlerState();
			break;
		case CRAWLING_ENDED:
			state = crawler.getEndedCrawlerState();
			break;
		default:
			state = crawler.getIdleCrawlerState();
			break;
		}

		crawler.setState(state);
	}

	private File getLinksToVisitDirectory(String linksToVisitDirectory, String siteName)
	{
		File dir = new File(linksToVisitDirectory + siteName);

		if (!dir.exists())
		{
			try
			{
				FileUtils.forceMkdir(dir);
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Can not create links to visit directory " + dir.getName(), e);
			}
		}

		return dir;
	}

}
