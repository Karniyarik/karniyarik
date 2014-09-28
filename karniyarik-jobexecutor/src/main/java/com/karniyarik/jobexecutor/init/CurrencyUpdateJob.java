package com.karniyarik.jobexecutor.init;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.parser.util.CurrencyManager;

public class CurrencyUpdateJob implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		CurrencyManager.getInstance().refreshCurrencyMap();
	}

}
