package com.karniyarik.jobscheduler;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.jobscheduler.util.JobStatisticsSummary;

public enum JobAdminStateFilter
{
	ALL,
	EXECUTING,
	FAILED, 
	DATAFEED,
	BROKEN,
	PAUSED,
	CRAWLER,
	SELENIUM,
	IDEASOFT,
	HEMENAL,
	KOBIMASTER,
	PRESTASHOP,
	PROJE,
	NETICARET; 
	
	private JobAdminStateFilter()
	{
	}
	
	public boolean isSameState(JobExecutionStat stat, SiteConfig siteConfig, JobStatisticsSummary summ)
	{
		boolean result = true;
		switch (this)
		{
			case ALL:
				break;
			case EXECUTING:
				result = stat.getStatus().isRunning();
				break;
			case FAILED:
				result = stat.getStatus().hasFailed();
				break;
			case DATAFEED:
				result = siteConfig.isDatafeed();
				break;
			case PAUSED:
				result = stat.getStatus().hasPaused();
				break;
			case SELENIUM:
				if(siteConfig != null)
					result = siteConfig.isSelenium();
				break;				
			case CRAWLER:
				if(siteConfig != null)
					result = siteConfig.isCrawler();
				break;								
			case BROKEN:
				if(siteConfig != null && siteConfig.isDatafeed() && stat.getStatus() != null)
				{
					result = stat.getStatus().hasFailed() && summ.getOverallHealth() < 20;					
				}
				else
				{
					result = summ.getOverallHealth() != 0 && summ.getOverallHealth() < 40;	
				}
				break;
			case IDEASOFT:
				if(siteConfig != null)
					result = siteConfig.getECommerceName().equalsIgnoreCase("ideasoft");
				break;
			case HEMENAL:
				if(siteConfig != null)
					result = siteConfig.getECommerceName().equalsIgnoreCase("hemenal");
				break;
			case KOBIMASTER:
				if(siteConfig != null)
					result = siteConfig.getECommerceName().equalsIgnoreCase("kobimaster");
				break;
			case PRESTASHOP:
				if(siteConfig != null)
					result = siteConfig.getECommerceName().equalsIgnoreCase("prestashop");
				break;
			case PROJE:				
				if(siteConfig != null)	
					result = siteConfig.getECommerceName().equalsIgnoreCase("proj-e");
				break;
			case NETICARET:				
				if(siteConfig != null)	
					result = siteConfig.getECommerceName().equalsIgnoreCase("neticaret");
				break;
		}
		return result;
	}
}
