package com.karniyarik.datafeed;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CrawlerConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionState;
import com.karniyarik.crawler.HttpClientContentFetcher;
import com.karniyarik.crawler.IContentFetcher;
import com.karniyarik.crawler.util.URLManager;
import com.karniyarik.parser.pojo.Product;

public class DatafeedProductCollectorFactory
{
	Logger log = Logger.getLogger(DatafeedProductCollectorFactory.class);
	
	public DatafeedProductCollector create(SiteConfig siteConfig, JobExecutionStat stat)
	{
		CrawlerConfig crawlerConf = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig();
		IContentFetcher contentFetcher = new HttpClientContentFetcher(crawlerConf.getBotName(), 600000, siteConfig.getSiteEncoding());
		//IContentFetcher contentFetcher = new SimpleContentFetcher(crawlerConf.getBotName(), crawlerConf.getURLFetchTimeout(), siteConfig.getSiteEncoding());
		DatafeedParser datafeedParser = createDatafeedParser(siteConfig, contentFetcher);
		stat.setSiteName(siteConfig.getSiteName());
		return new DatafeedProductCollector(siteConfig, contentFetcher, datafeedParser, stat);
	}

	private DatafeedParser createDatafeedParser(SiteConfig siteConfig, IContentFetcher contentFetcher)
	{
		DatafeedParser parser = null;
		
		URLManager urlManager = new URLManager(siteConfig.getUrl(), siteConfig.getSiteName());
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		CategoryConfig categoryConfig = categorizerConfig.getCategoryConfig(siteConfig.getCategory());

		if (siteConfig.isTabSeperatedDatafeed())
		{
			parser = new TabSeparatedDatafeedParser(siteConfig, categoryConfig, urlManager);
		}
		else if (siteConfig.isWebServiceDataFeed())
		{
			String classname = siteConfig.getAffiliateClass();
			try {
				Class<WebServiceDatafeedParser> clazz = (Class<WebServiceDatafeedParser>) Class.forName(classname);
				WebServiceDatafeedParser webServiceParser = (WebServiceDatafeedParser) clazz.newInstance(); 
				webServiceParser.setCategoryConfig(categoryConfig);
				webServiceParser.setSiteConfig(siteConfig);
				webServiceParser.setUrlManager(urlManager);
				webServiceParser.setContentFetcher(contentFetcher);
				parser = webServiceParser;
			} catch (Throwable e) {
				log.error("Cannot instantiate web service datafeed client: " + classname, e);
			} 
		}
		else
		{
			parser = new XMLDatafeedParser(siteConfig, categoryConfig, urlManager);
		}

		return parser;
	}

	public static void main(String[] args) {
		System.getProperties().put("http.proxySet", "true");
		System.getProperties().put("http.proxyHost", "");
		System.getProperties().put("http.proxyPort", "");
		System.getProperties().put("http.proxyUser", "");
		System.getProperties().put("http.proxyPassword", "");
		Authenticator.setDefault(new Authenticator(){@Override
			protected Object clone() throws CloneNotSupportedException
			{
				return super.clone();
			}
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("", "".toCharArray());
			}
		});
		
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		SiteConfig siteConfig = sitesConfig.getSiteConfig("inventus");
		JobExecutionStat stat = new JobExecutionStat();
		stat.setStatus(JobExecutionState.IDLE);
		stat.setSiteName(siteConfig.getSiteName());
		List<Product> collectProducts = new DatafeedProductCollectorFactory().create(siteConfig, stat).collectProducts();
		int a = collectProducts.size();
//		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
//		for(SiteConfig siteConfig: sitesConfig.getSiteConfigList())
//		{
//			if(siteConfig.isDatafeed())
//			{
//				try {
//					System.out.println("working on " + siteConfig.getSiteName());
//					JobExecutionStat stat = new JobExecutionStat();
//					stat.setStatus(JobExecutionState.IDLE);
//					stat.setSiteName(siteConfig.getSiteName());
//					List<Product> collectProducts = new DatafeedProductCollectorFactory().create(siteConfig, stat).collectProducts();
//				} catch (Exception e) {
//					System.out.println("fetch for " + siteConfig.getSiteName() + " failed");
//				}				
//			}
//		}
//		
//		BrandLogger.getInstance().write();
		
	}
}
