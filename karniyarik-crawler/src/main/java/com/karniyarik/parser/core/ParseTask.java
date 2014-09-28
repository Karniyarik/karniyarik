package com.karniyarik.parser.core;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.brands.BrandServiceImpl;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.crawler.linkgraph.VisitedLinksList;
import com.karniyarik.parser.base.XQueryParser;
import com.karniyarik.parser.pojo.Product;

public class ParseTask implements Runnable
{

	private final String				content;
	private final String				url;
	private final String				shortUrl;
	private final Date					fetchDate;
	private final XQueryParser			parser;
	private final SiteProductRegistery	registery;
	private final SiteProductBuffer		buffer;
	private final JobExecutionStat		statistics;
	private final VisitedLinksList		visitedLinksList;

	public ParseTask(String url, String shortUrl, String content, Date fetchDate, XQueryParser parser, SiteProductRegistery productRegistery, SiteProductBuffer buffer, JobExecutionStat statistics,
			VisitedLinksList visitedLinksList)
	{
		this.content = content;
		this.url = url;
		this.shortUrl = shortUrl;
		this.fetchDate = fetchDate;
		this.parser = parser;
		this.registery = productRegistery;
		this.buffer = buffer;
		this.statistics = statistics;
		this.visitedLinksList = visitedLinksList;
	}

	@Override
	public void run()
	{
		Product product = parser.parse(url, shortUrl, content, fetchDate);

		if (product != null)
		{
			boolean registered = registery.isProductRegistered(product.hashCode());

			if (!registered)
			{
				buffer.addProduct(product);
				updateStatistics(product);
				visitedLinksList.productFound(shortUrl);
//				System.out.println("Found: " + url);
			}
			else
			{
				statistics.duplicateProductFound();
			}
		}
		else
		{
//			System.out.println("Miss: " + url);
			statistics.productMissed();
		}
	}

	private void updateStatistics(Product product)
	{
		statistics.productFound();

		if (BrandServiceImpl.getInstance().isBrandRecognized(product.getBrand()))
		{
			statistics.brandParsed();
		}
		else
		{
			statistics.brandMissed();
		}

		if (StringUtils.isNotBlank(product.getBreadcrumb()))
		{
			statistics.breadcrumbParsed();
		}
		else
		{
			statistics.breadcrumbMissed();
		}

		if (StringUtils.isNotBlank(product.getImageUrl()))
		{
			statistics.imageParsed();
		}
		else
		{
			statistics.imageMissed();
		}
	}

}
