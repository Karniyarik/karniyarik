package com.karniyarik.statistics;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.statistics.cache.DailyCache;

public class DailyCacheClearJob implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		DailyCache.getInstance().reset();
	}	
}
