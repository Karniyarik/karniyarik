package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.web.citydeals.CityDealConverter;

public class CityDealJob implements Job
{
	public CityDealJob()
	{
	}
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException
	{
		update();
	}

	public void update()
	{
		new CityDealConverter(Integer.MAX_VALUE).updateData();
		//new CityDealRSSGenerator().generate(rootPath);
	}
}
