package com.karniyarik.jobscheduler;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SiteScheduleJob implements Job
{
	public final static String	siteNameKey	= "sitename";

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException
	{
		String siteName = jobContext.getJobDetail().getJobDataMap().getString(siteNameKey);

		if (StringUtils.isNotBlank(siteName))
		{
			JobSchedulerAdmin.getInstance().schedule(siteName);
		}
	}

}
