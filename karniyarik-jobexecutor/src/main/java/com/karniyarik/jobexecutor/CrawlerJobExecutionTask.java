package com.karniyarik.jobexecutor;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.db.DatabaseTableCreator;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionState;
import com.karniyarik.common.util.TableNameUtil;
import com.karniyarik.crawler.Crawler;
import com.karniyarik.crawler.CrawlerFactory;
import com.karniyarik.pagerank.PageRankCalculator;

public class CrawlerJobExecutionTask extends JobExecutionTask
{

	private Crawler	crawler;

	public CrawlerJobExecutionTask(SiteConfig siteConfig, JobExecutionStat stat, boolean publishIndex)
	{
		super(siteConfig, stat, publishIndex);
		this.crawler = null;
	}

	@Override
	protected void crawl()
	{

		try
		{
			if (getStat().getStatus() != JobExecutionState.CRAWLING_PAUSED)
			{
				DatabaseTableCreator tableCreator = new DatabaseTableCreator(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDbConfig());
				tableCreator.dropAndCreateVisitedLinksTable(TableNameUtil.createLinksVisitedTableName(getSiteConfig().getSiteName()));
			}

			crawler = new CrawlerFactory().createCrawler(getSiteConfig(), getStat());

			if (crawler != null)
			{
				crawler.start();
			}
		}
		catch (Throwable e)
		{
			getLogger().error("JobExecution task failed during crawling " + getStat().getSiteName(), e);
			getStat().setStatusMessage(e.getMessage());
			getStat().crawlingFailed();
		}
		finally
		{
			crawler = null;
		}
	}

	@Override
	protected void rank()
	{
		try
		{
			getStat().rankingStarted();
			new PageRankCalculator(getStat().getSiteName(), TableNameUtil.createLinksVisitedTableName(getStat().getSiteName())).calculate();
			getStat().rankingEnded();
		}
		catch (Throwable e)
		{
			getLogger().error("Ranking failed " + getSiteConfig().getSiteName(), e);
			getStat().rankingFailed(e.getMessage());
		}
	}

	@Override
	public void pause()
	{
		if (crawler != null)
		{
			crawler.pause();
		}
	}

	@Override
	public void end()
	{
		if (crawler != null)
		{
			crawler.stop();
		}
	}

}
