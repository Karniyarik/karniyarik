package com.karniyarik.jobexecutor.init;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.recognizer.hepsiburada.Collector;

public class HepsiburadaXMLJob implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		//new Collector().start();
	}

}
