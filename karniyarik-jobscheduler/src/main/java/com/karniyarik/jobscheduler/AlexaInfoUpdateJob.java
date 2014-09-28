package com.karniyarik.jobscheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.externalrank.alexa.AlexaRankRegistry;

public class AlexaInfoUpdateJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		Thread update = new Thread(){
			public void run() {
				AlexaRankRegistry.getInstance().update();
			};
		};
		
		update.start();
	}
}