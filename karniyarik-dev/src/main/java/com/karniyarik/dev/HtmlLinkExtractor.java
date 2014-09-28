package com.karniyarik.dev;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CrawlerConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionState;
import com.karniyarik.crawler.ContentFetcherFactory;
import com.karniyarik.crawler.SeleniumContentFetcher;
import com.karniyarik.crawler.SimpleContentFetcher;
import com.karniyarik.crawler.parser.XQueryHtmlParser;
import com.karniyarik.crawler.site.SiteLinkAnalyzer;
import com.karniyarik.crawler.util.CrawlerLoggerProvider;
import com.karniyarik.crawler.util.RobotsTxtAnalyzer;
import com.karniyarik.crawler.util.URLAnalyzer;
import com.karniyarik.crawler.util.URLManager;

public class HtmlLinkExtractor
{
	URLAnalyzer urlAnalyzer = null; 
	URLManager urlManager = null;
	SeleniumContentFetcher seleniumContentFetcher = null;
	SiteLinkAnalyzer siteAnalyzer = null;
	XQueryHtmlParser htmlParser = null; 
	SimpleContentFetcher simpleContenFetcher = null; 
		
	public HtmlLinkExtractor(String sitename)
	{
		JobExecutionStat stat = new JobExecutionStat();
		SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
		stat.setSiteName(siteConfig.getSiteName());
		stat.setStatus(JobExecutionState.IDLE);

		Logger logger = new CrawlerLoggerProvider(siteConfig.getSiteName()).getLogger();

		CrawlerConfig crawlerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig();

		urlAnalyzer = new URLAnalyzer();
		urlManager = new URLManager(siteConfig.getUrl(), siteConfig.getSiteName());
		RobotsTxtAnalyzer robotsTxtAnalyzer = new RobotsTxtAnalyzer();
		siteAnalyzer = new SiteLinkAnalyzer(siteConfig.getUrl(), siteConfig.getIgnoreList(), siteConfig.getPagingRule(), siteConfig.getRuleClassName(), crawlerConfig
				.getCommonIgnoreRuleList(), robotsTxtAnalyzer.getDisallowedUrlList(), logger);
		
		ContentFetcherFactory contentFetcherFactory = new ContentFetcherFactory(crawlerConfig.getBotName(), crawlerConfig.getURLFetchTimeout(), siteConfig.getSiteEncoding(), siteConfig.getUrl());

		htmlParser = new XQueryHtmlParser();
		
		simpleContenFetcher = contentFetcherFactory.createSimpleContentFetcher();

		seleniumContentFetcher = null;
		if (StringUtils.isNotBlank(siteConfig.getPagingRule()))
		{
			seleniumContentFetcher = contentFetcherFactory.createSeleniumContentFetcher();
		}

	}
	
	public void getLinks(String aURL)
	{
		String content = simpleContenFetcher.getContent(aURL);
		
		Set<String> extractedLinkSet = null;
		
		if (seleniumContentFetcher != null && siteAnalyzer.isConstructedPagingUrl(aURL))
		{
			aURL = siteAnalyzer.extractPagingUrlParent(aURL);
		}

		// get new links and add to the queue
		extractedLinkSet = htmlParser.getLinks(content, aURL);

		extractedLinkSet = urlAnalyzer.cleanUrlSet(extractedLinkSet);

		extractedLinkSet = urlAnalyzer.removeTraps(extractedLinkSet);

		extractedLinkSet.addAll(siteAnalyzer.generateUrls(aURL));

		crawlChildren(extractedLinkSet, aURL);
	}
	
	private void crawlChildren(Set<String> childrenLinkSet, String parentUrl)
	{
		for (String aURL : childrenLinkSet)
		{
			if (aURL != null && !siteAnalyzer.isIgnored(aURL))
			{
				aURL = siteAnalyzer.correctURL(aURL, parentUrl);

				aURL = urlAnalyzer.clean(aURL);

				if (seleniumContentFetcher != null && siteAnalyzer.isPagingUrl(aURL))
				{
					aURL = siteAnalyzer.generateSpecialPagingUrl(parentUrl, aURL);
				}

				if (urlAnalyzer.isHostsEqual(aURL, siteAnalyzer.getHomeURL()))
				{
					// getCrawlerStatistics().linksFound(1);
					System.out.println(aURL);
				}
			}
		}
	}

	
	public static void main(String[] args)
	{
		new HtmlLinkExtractor("milliyetemlak").getLinks("http://www.milliyetemlak.com/kiralik/konut_ev_daire/sahibinden");
	}
}
