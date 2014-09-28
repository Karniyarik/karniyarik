package com.karniyarik.common.config.base;

import org.apache.log4j.Logger;

import com.karniyarik.common.config.apikey.APIKeyConfig;
import com.karniyarik.common.config.news.NewsList;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.config.site.SitesConfigFactory;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CrawlerConfig;
import com.karniyarik.common.config.system.DBConfig;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.config.system.MailConfig;
import com.karniyarik.common.config.system.SchedulerConfig;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.common.config.system.StatisticsConfig;
import com.karniyarik.common.config.system.WebConfig;

/**
 * TODO: what this class does will be documented by meralan.
 * 
 * @author meralan
 * 
 */
public class ConfigurationBundle
{

	private DBConfig			dbConfig			= null;
	private CrawlerConfig		crawlerConfig		= null;
	private SearchConfig		searchConfig		= null;
	private StatisticsConfig	statiticsConfig		= null;
	private SitesConfig			sitesConfig			= null;
	private WebConfig			webConfig			= null;
	private SchedulerConfig		schedulerConfig		= null;
	private CategorizerConfig	categorizerConfig	= null;
	private MailConfig			mailConfig			= null;
	private APIKeyConfig		apiKeyConfig		= null;
	private NewsList			newsList			= null;
	private DeploymentConfig 	deploymentConfig	= null;

	public ConfigurationBundle()
	{
		try
		{
			dbConfig = new DBConfig();
			crawlerConfig = new CrawlerConfig();
			searchConfig = new SearchConfig();
			statiticsConfig = new StatisticsConfig();
			categorizerConfig = new CategorizerConfig();
			sitesConfig = new SitesConfigFactory().create(categorizerConfig);
			webConfig = new WebConfig();
			schedulerConfig = new SchedulerConfig();
			mailConfig = new MailConfig();
			apiKeyConfig = new APIKeyConfig();
			newsList = new NewsList();
			deploymentConfig = new DeploymentConfig();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Exception occured during config init.", e);
		}
	}

	public DBConfig getDbConfig()
	{
		return dbConfig;
	}

	public void setDbConfig(DBConfig dbConfig)
	{
		this.dbConfig = dbConfig;
	}

	public CrawlerConfig getCrawlerConfig()
	{
		return crawlerConfig;
	}

	public void setCrawlerConfig(CrawlerConfig crawlerConfig)
	{
		this.crawlerConfig = crawlerConfig;
	}

	public SearchConfig getSearchConfig()
	{
		return searchConfig;
	}

	public void setSearchConfig(SearchConfig searchConfig)
	{
		this.searchConfig = searchConfig;
	}

	public StatisticsConfig getStatiticsConfig()
	{
		return statiticsConfig;
	}

	public void setStatiticsConfig(StatisticsConfig statiticsConfig)
	{
		this.statiticsConfig = statiticsConfig;
	}

	public SitesConfig getSitesConfig()
	{
		return sitesConfig;
	}

	public void setSitesConfig(SitesConfig sitesConfig)
	{
		this.sitesConfig = sitesConfig;
	}

	public WebConfig getWebConfig()
	{
		return webConfig;
	}

	public void setWebConfig(WebConfig webConfig)
	{
		this.webConfig = webConfig;
	}

	public SchedulerConfig getSchedulerConfig()
	{
		return schedulerConfig;
	}

	public CategorizerConfig getCategorizerConfig()
	{
		return categorizerConfig;
	}

	public MailConfig getMailConfig()
	{
		return mailConfig;
	}

	public APIKeyConfig getApiKeyConfig()
	{
		return apiKeyConfig;
	}

	public NewsList getNewsList()
	{
		return newsList;
	}

	public DeploymentConfig getDeploymentConfig()
	{
		return deploymentConfig;
	}
	
	public synchronized void refreshSitesConfig()
	{
		SitesConfig newSitesConfig = null;
		try
		{
			newSitesConfig = new SitesConfigFactory().create(categorizerConfig);
			this.sitesConfig = newSitesConfig;
		}
		catch (Throwable e)
		{
			getLogger().error("Can not update sites conf", e);
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
}
