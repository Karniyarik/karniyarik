package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.common.site.SiteRegistry;
import com.karniyarik.search.EngineRepository;

public class EnterpriseAndStatSyncJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		SiteRegistry.getInstance().sendSiteInfo();
		SiteRegistry.getInstance().refreshSiteCache();
		SiteRegistry.getInstance().refreshFeaturedCache();
		EngineRepository.getInstance().refreshSponsoredLinksData();
		SiteRegistry.getInstance().refreshAlexaCache();
	}
}
