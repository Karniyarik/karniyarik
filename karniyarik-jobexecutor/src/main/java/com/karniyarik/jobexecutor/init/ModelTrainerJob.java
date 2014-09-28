package com.karniyarik.jobexecutor.init;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.recognizer.model.ModelTrainer;

public class ModelTrainerJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		System.out.println("Getting site confs");
		
		List<SiteConfig> siteConfigList = new ArrayList<SiteConfig>();
		addSite("teknosa", siteConfigList);
		addSite("darty", siteConfigList);
		
		System.out.println("SiteConfig list size: " + siteConfigList.size());
		
		if(siteConfigList.size() > 0 )
		{
			new ModelTrainer().train(siteConfigList);	
		}
		
	}
	
	public void addSite(String name, List<SiteConfig> siteConfigList)
	{
		SiteConfig siteConfig = JobExecutorServiceUtil.getSiteConfig(name);
		if(siteConfig != null)
		{
			siteConfigList.add(siteConfig);	
		}
	}
}
