package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.web.sitemap.SearchLogGenerator;

public class SitemapForSearchLogJob implements Job
{
	public static String PATH = "PATH";
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException
	{
		SearchLogGenerator generator = new SearchLogGenerator(SearchLogGenerator.WEEKLY);
		generator.generate();
	}
}
