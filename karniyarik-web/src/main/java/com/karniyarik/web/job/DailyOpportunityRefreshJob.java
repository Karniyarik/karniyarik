package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.web.bendeistiyorum.DailyOpportunityLoader;


public class DailyOpportunityRefreshJob implements Job
{

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException
	{
		DailyOpportunityLoader.getInstance().refreshData();
	}

}
