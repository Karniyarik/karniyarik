package com.karniyarik.jobexecutor;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.datafeed.DatafeedProductCollector;
import com.karniyarik.datafeed.DatafeedProductCollectorFactory;
import com.karniyarik.parser.pojo.Product;

public class DatafeedJobExecutionTask extends JobExecutionTask
{

	private List<Product>	productList;

	public DatafeedJobExecutionTask(SiteConfig siteConfig, JobExecutionStat stat, boolean publishIndex)
	{
		super(siteConfig, stat, publishIndex);
	}

	@Override
	protected void crawl()
	{
		getStat().crawlerStarted();
		try
		{
			DatafeedProductCollectorFactory factory = new DatafeedProductCollectorFactory();
			DatafeedProductCollector collector = factory.create(getSiteConfig(), getStat());
			productList = collector.collectProducts();
			getStat().crawlingEnded();
		}
		catch (Throwable e)
		{
			getStat().crawlingFailed();
			getLogger().error("Can not collect products for " + getSiteConfig(), e);
		}
	}

	@Override
	public void pause()
	{
		// datafeed job can not be paused
	}

	@Override
	public void end()
	{
		// datafeed job can not be ended
	}

	@Override
	protected void rank()
	{
		File productsFile = new File(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getProductsDirectory() + File.separator + getStat().getSiteName()
				+ ".txt");
		int defaultRank = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getMaxRank() / 2;

		try
		{
			getStat().rankingStarted();
			if (!productsFile.getParentFile().exists())
			{
				FileUtils.forceMkdir(productsFile.getParentFile());
			}

			FileWriter writer = new FileWriter(productsFile);
			for (Product product : productList)
			{
				product.setRank(defaultRank);
				writer.write(JSONUtil.getJSON(product) + "\n");
			}
			writer.flush();
			writer.close();
			getStat().rankingEnded();
		}
		catch (Throwable e)
		{
			Logger.getLogger(this.getClass()).error("Can not rank products " + getSiteConfig().getSiteName(), e);
			getStat().rankingFailed(e.getMessage());
		}

		productList.clear();
		productList = null;
	}

}
