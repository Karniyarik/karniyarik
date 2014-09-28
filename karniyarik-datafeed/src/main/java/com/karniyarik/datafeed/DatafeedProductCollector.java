package com.karniyarik.datafeed;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.crawler.IContentFetcher;
import com.karniyarik.parser.core.SiteProductRegistery;
import com.karniyarik.parser.pojo.Product;

public class DatafeedProductCollector
{

	Logger							log	= Logger.getLogger(DatafeedProductCollector.class);

	private final SiteConfig		siteConfig;
	private final IContentFetcher	contentFetcher;
	private final DatafeedParser	datafeedParser;
	private final JobExecutionStat	stat;

	public DatafeedProductCollector(SiteConfig siteConfig, IContentFetcher contentFetcher, DatafeedParser datafeedParser, JobExecutionStat stat)
	{
		this.siteConfig = siteConfig;
		this.contentFetcher = contentFetcher;
		this.datafeedParser = datafeedParser;
		this.stat = stat;
	}

	public List<Product> collectProducts()
	{
		String content;
		stat.crawlerStarted();
		List<Product> productList = new ArrayList<Product>();
		List<Product> parsedProductList = new ArrayList<Product>();
		SiteProductRegistery registery = new SiteProductRegistery(new TreeSet<Integer>());
		Exception exception = null;
		
		if (siteConfig.isWebServiceDataFeed())
		{
			try
			{
				parsedProductList.addAll(datafeedParser.parse(null));
			}
			catch (Exception e)
			{
				stat.crawlingFailed();
				stat.setStatusMessage("Error occured during data fetch. Message: " + e.getMessage());
				exception = e;
				KarniyarikLogger.logException("Error occured during data fetch. Message: ", e, log);
			}
		}
		else
		{
			try 
			{
				for (String datafeedUrl : siteConfig.getDatafeedUrlList())
				{
					try {
						content = contentFetcher.getContent(datafeedUrl);
						parsedProductList.addAll(datafeedParser.parse(content));
						stat.linkVisited(datafeedUrl);
					} 
					catch (Exception e) {
						KarniyarikLogger.logException("Error occured during data fetch. Message: ", e, log);
					}
				}
			}
			catch (Exception e)
			{
				stat.crawlingFailed();
				stat.setStatusMessage("Error occured during data fetch. Message: " + e.getMessage());
				exception = e;
				KarniyarikLogger.logException("Error occured during data fetch. Message: ", e, log);
			}
		}

		for (Product product : parsedProductList)
		{
			if (StringUtils.isNotBlank(product.getName()) && StringUtils.isNotBlank(product.getUrl()) && product.getPrice() > 0f)
			{
				if (!registery.isProductRegistered(product.hashCode()))
				{
					productList.add(product);
					stat.productFound();
				}
				else
				{
					stat.duplicateProductFound();
				}
			}
			else
			{
				stat.productMissed();
			}
		}

		if (productList.size() > 0)
		{
			stat.crawlingEnded();
		}
		else
		{
			stat.crawlingFailed();
			ExceptionNotifier.sendException("datafeed-failed", siteConfig.getSiteName() + " datafeed failed!", 
					"No products found in " + siteConfig.getDatafeedUrlList().toString(), exception);
			//new MailNotifier().sendTextMail(siteConfig.getSiteName() + " datafeed failed!", );
			stat.setStatusMessage("Zero products!");
		}

		return productList;
	}

}
