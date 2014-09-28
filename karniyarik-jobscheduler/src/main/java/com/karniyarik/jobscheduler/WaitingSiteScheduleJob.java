package com.karniyarik.jobscheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WaitingSiteScheduleJob implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		JobSchedulerAdmin.getInstance().scheduleWaitingSites();
	}

}
