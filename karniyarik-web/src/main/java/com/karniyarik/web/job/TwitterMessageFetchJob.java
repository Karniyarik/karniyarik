package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.web.statusmsg.TwitterMessages;

public class TwitterMessageFetchJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		TwitterMessages.getInstance().update();
	}
}