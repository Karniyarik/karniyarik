package com.karniyarik.jobscheduler.ga;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.common.ga.GoogleAnalyticsDataFetcher;

public class AnalyticsUpdateJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		Thread update = new Thread(){
			public void run() {
				GoogleAnalyticsDataFetcher.getInstance(true).refreshData();
			};
		};
		
		update.start();
	}
}
