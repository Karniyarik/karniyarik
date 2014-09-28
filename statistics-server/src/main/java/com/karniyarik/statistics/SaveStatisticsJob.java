package com.karniyarik.statistics;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SaveStatisticsJob implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		StatisticsCollector.getInstance().saveCurrentStatistics();
	}
	
}
