package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.search.solr.SOLRSearchProxy;
import com.karniyarik.search.solr.SOLRSearcherFactory;

public class SOLRIndexOptimizeJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
	
		for(CategoryConfig cateConfig: categorizerConfig.getCategoryConfigList())
		{
			try
			{
				int catType = CategorizerConfig.getCategoryType(cateConfig.getName());
				SOLRSearchProxy searcher = SOLRSearcherFactory.getSearcher(catType,600000);
				searcher.optimize();
				System.out.println("Called optimize for " + cateConfig.getName());
			}
			catch (Exception e)
			{
				System.out.println("CANNOT call optimize for " + cateConfig.getName());
			}
		}
	
		int a = 5;
	}
}