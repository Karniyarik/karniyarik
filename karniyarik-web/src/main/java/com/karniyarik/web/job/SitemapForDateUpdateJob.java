package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.web.sitemap.SitemapDateUpdater;

public class SitemapForDateUpdateJob implements Job
{
	public static String PATH = "PATH";
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException
	{
		SitemapDateUpdater updater = new SitemapDateUpdater();
		updater.update();
	}
}
