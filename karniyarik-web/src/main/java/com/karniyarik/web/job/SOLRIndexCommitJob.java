package com.karniyarik.web.job;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.search.solr.SOLRSearchProxy;
import com.karniyarik.search.solr.SOLRSearcherFactory;

public class SOLRIndexCommitJob implements Job
{
	private String catName = null;
	
	public SOLRIndexCommitJob()
	{
		// TODO Auto-generated constructor stub
	}

	public SOLRIndexCommitJob(String catName)
	{
		this.catName = catName;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		execute();
	}

	public void execute() {
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		if(StringUtils.isBlank(catName))
		{
			for(CategoryConfig cateConfig: categorizerConfig.getCategoryConfigList())
			{
				callCommit(cateConfig);
			}			
		}
		else
		{
			CategoryConfig cateConfig = categorizerConfig.getCategoryConfig(catName);
			callCommit(cateConfig);
		}
		int a = 5;		
	}

	private void callCommit(CategoryConfig cateConfig)
	{
		try{
			int catType = CategorizerConfig.getCategoryType(cateConfig.getName());
			SOLRSearchProxy searcher = SOLRSearcherFactory.getSearcher(catType,120000);
			searcher.commit();
			System.out.println("Called commit for " + cateConfig.getName());
		}
		catch (Exception e)
		{
			System.out.println("CANNOT call commit for " + cateConfig.getName());
		}
	}
}
