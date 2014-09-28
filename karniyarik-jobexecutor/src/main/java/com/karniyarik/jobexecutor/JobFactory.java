package com.karniyarik.jobexecutor;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;

public class JobFactory
{

	public JobExecutionTask create(SiteConfig siteConfig, JobExecutionStat stat)
	{
		JobExecutionTask task = null;
		boolean publishIndex = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getPublishIndex();

		if (siteConfig.hasDatafeed())
		{
			task = new DatafeedJobExecutionTask(siteConfig, stat, publishIndex);
		}
		else
		{
			task = new CrawlerJobExecutionTask(siteConfig, stat, publishIndex);
		}

		return task;
	}

}
