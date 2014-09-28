package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.web.site.FunnyStatisticsProvider;

public class GeneralSiteStatisticsUpdateJob implements Job{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FunnyStatisticsProvider.getInstance().update();
	}
}
