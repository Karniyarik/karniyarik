package com.karniyarik.indexer;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.indexer.dao.IndexerDAO;
import com.karniyarik.indexer.dao.IndexerDAOFactory;

public class SiteIndexerFactory
{

	public SiteIndexer create(SiteConfig siteConfig, JobExecutionStat stat)
	{
		CategoryConfig categoryConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig().getCategoryConfig(siteConfig.getCategory());
		SiteIndexer indexer = null;

		IndexerDAO dao = new IndexerDAOFactory().create(siteConfig);
		
		try
		{
			indexer = new SiteIndexer(stat, dao, categoryConfig, siteConfig);
		}
		catch (Throwable e)
		{
			getLogger().error("Can not create site indexer for " + stat.getSiteName(), e);
			stat.indexingFailed(e.getMessage());
			throw new RuntimeException("Can not create site indexer for " + stat.getSiteName(), e);
		}

		return indexer;
	}
	
	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
