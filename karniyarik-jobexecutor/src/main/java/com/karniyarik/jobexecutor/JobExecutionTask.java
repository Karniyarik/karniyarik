package com.karniyarik.jobexecutor;

import org.apache.log4j.Logger;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionState;
import com.karniyarik.indexer.SiteIndexer;
import com.karniyarik.indexer.SiteIndexerFactory;

public abstract class JobExecutionTask implements Runnable
{

	private final SiteConfig		siteConfig;
	private final JobExecutionStat	stat;
	private final boolean			publishIndex;

	public JobExecutionTask(SiteConfig siteConfig, JobExecutionStat stat, boolean publishIndex)
	{
		this.siteConfig = siteConfig;
		this.stat = stat;
		this.publishIndex = publishIndex;
	}

	@Override
	public void run()
	{
		try
		{
			//this statement occurs for new commands
			boolean continueProcess = true;
			
//			if(stat.getStatus().hasPaused() || stat.getStatus().hasFailed())
//			{
//				continueProcess = true;
//			}
				
			while (stat.getStatus().isRunning() || stat.getStatus().isIdle() || continueProcess == true)
			{
				continueProcess = false;
				
				if (stat.getStatus() == JobExecutionState.IDLE || stat.getStatus() == JobExecutionState.CRAWLING_PAUSED)
				{
					crawl();
				}
				else if (stat.getStatus() == JobExecutionState.CRAWLING_ENDED)
				{
					rank();
				}
				else if (stat.getStatus() == JobExecutionState.RANKING_ENDED)
				{
					index();
				}
				else if (stat.getStatus() == JobExecutionState.INDEXING_ENDED)
				{
					publish();
				}
				else if (stat.getStatus() == JobExecutionState.PUBLISHING_ENDED)
				{
					merge();
				}
				else if (stat.getStatus() == JobExecutionState.MERGE_CALLS_ENDED)
				{
					stat.end();
				}
			}
		}
		catch (Throwable e)
		{
			getLogger().error("One of site operations failed for " + stat.getSiteName() + ", current state is " + stat.getStatus(), e);
		}
		finally
		{
			JobExecutorServiceUtil.sendJobExecutionStatJobToScheduler(stat);
			JobExecutorAdmin.getInstance().taskEnded(stat.getSiteName());
		}
	}

	protected abstract void crawl();

	protected abstract void rank();

	public abstract void pause();

	public abstract void end();

	private void index()
	{
		SiteIndexer indexer = new SiteIndexerFactory().create(siteConfig, stat);
		indexer.index();
	}

	private void publish()
	{
		stat.startPublishing();
		stat.publishingEnded();
//		if (publishIndex)
//		{
//			SiteIndexPublisher publisher = new SiteIndexPublisher(stat.getSiteName(), new SiteIndexUtil(stat.getSiteName()));
//			try
//			{
//				publisher.publishToWebServer();
//				stat.publishingEnded();
//			}
//			catch (Throwable e)
//			{
//				stat.publishingFailed("Could not publish index to web server, " + e.getMessage());
//				getLogger().error("Could not publish site index to web server " + stat.getSiteName(), e);
//			}
//		}
//		else
//		{
//			stat.publishingEnded();
//		}
	}

	private void merge()
	{
		try
		{
			stat.startCallingMergeServlets();
//			IndexMergeUtil.callMergeSiteIndex(stat.getSiteName());
			stat.mergeCallsEnded();
		}
		catch (Throwable e)
		{
			stat.mergeServletCallsFailed("Index merge call failed " + e.getMessage());
			getLogger().error("Index merge call failed", e);
		}
	}

	public SiteConfig getSiteConfig()
	{
		return siteConfig;
	}

	public JobExecutionStat getStat()
	{
		return stat;
	}

	public boolean isPublishIndex()
	{
		return publishIndex;
	}

	public Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
