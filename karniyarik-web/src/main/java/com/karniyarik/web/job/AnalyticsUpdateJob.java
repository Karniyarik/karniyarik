package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.common.ga.GoogleAnalyticsDataFetcher;

public class AnalyticsUpdateJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		GoogleAnalyticsDataFetcher.getInstance(false).refreshData();
	}
}
