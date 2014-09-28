package com.karniyarik.parser.core;

import java.io.File;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.URLUtil;
import com.karniyarik.parser.base.XQueryParser;
import com.karniyarik.parser.dao.ParserProductFileDAO;
import com.karniyarik.parser.dao.ProductRegisteryFileDAO;
import com.karniyarik.parser.logger.ParserBrandsLogger;

public class ParserSiteToolFactory
{

	public ParserSiteTool createParserSiteTool(SiteConfig siteConfig, JobExecutionStat crawlerStatistics)
	{
		// ParserBrandsLogger brandsLogger = new ParserBrandsLogger(siteConfig
		// .getSiteName());
		// Brands logger set to null to prevent too many open files exception
		// on servers
		ParserBrandsLogger brandsLogger = null;
		XQueryParser parser = new XQueryParser(siteConfig, brandsLogger, new URLUtil());

		File productsDir = new File(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getProductsDirectory());
		if (!productsDir.exists())
		{
			try
			{
				FileUtils.forceMkdir(productsDir);
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Can not create products directory " + productsDir.getAbsolutePath(), e);
			}
		}

		File productsFile = new File(productsDir, siteConfig.getSiteName() + ".txt");
		if (!productsFile.exists())
		{
			try
			{
				productsFile.createNewFile();
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Can not create products file " + productsFile.getAbsolutePath(), e);
			}
		}
		else if(!crawlerStatistics.getStatus().hasPaused())
		{
			try
			{
				productsFile.delete();
				productsFile.createNewFile();
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Can not create products file " + productsFile.getAbsolutePath(), e);
			}
		}

		ProductRegisteryFileDAO productRegisteryDao = new ProductRegisteryFileDAO(productsFile);
		TreeSet<Integer> registeryData = productRegisteryDao.loadRegistery();
		SiteProductRegistery productRegistery = new SiteProductRegistery(registeryData);

		ParserProductFileDAO parserProductDao = new ParserProductFileDAO(productsFile);
		SiteProductBuffer productBuffer = new SiteProductBuffer(100, parserProductDao);

		return new ParserSiteTool(parser, productRegistery, productBuffer, crawlerStatistics);
	}
}
