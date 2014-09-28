package com.karniyarik.jobexecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionState;
import com.karniyarik.common.util.ExecutorUtil;
import com.karniyarik.indexer.SiteIndexer;
import com.karniyarik.indexer.SiteIndexerFactory;

public class JobExecutorAdmin
{

	private static JobExecutorAdmin				INSTANCE	= null;
	private final Map<String, JobExecutionTask>	taskMap;
	private final ExecutorService				executor;
	private final JobFactory					factory;

	private JobExecutorAdmin()
	{
		taskMap = new HashMap<String, JobExecutionTask>();
		executor = Executors.newCachedThreadPool();
		factory = new JobFactory();
	}

	public static synchronized JobExecutorAdmin getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new JobExecutorAdmin();
		}
		return INSTANCE;
	}

	public void start(SiteConfig siteConfig, JobExecutionStat stat)
	{
		JobExecutionTask jobExecutionTask = taskMap.get(siteConfig.getSiteName());
		if(jobExecutionTask == null)
		{
			JobExecutionTask task = factory.create(siteConfig, stat);
			taskMap.put(siteConfig.getSiteName(), task);
			executor.submit(task);			
		}
	}
	
	public JobExecutionTask getJobExecutionTask(String sitename)
	{
		return taskMap.get(sitename);
	}

	public void pause(String siteName)
	{
		JobExecutionTask task = taskMap.get(siteName);
		if (task != null)
		{
			task.pause();
		}
	}

	public void end(String siteName)
	{
		JobExecutionTask task = taskMap.get(siteName);
		if (task != null)
		{
			task.end();
		}
	}

	public void pauseAll()
	{
		for (JobExecutionTask task : taskMap.values())
		{
			task.pause();
		}
	}

	public void taskEnded(String siteName)
	{
		taskMap.remove(siteName);
	}
	
	public void reduceBoost(SiteConfig siteConfig)
	{
		SiteIndexer indexer = new SiteIndexerFactory().create(siteConfig, new JobExecutionStat());
		indexer.reduceBoost();
	}

	public List<JobExecutionStat> getAllStatistics()
	{
		List<JobExecutionStat> statList = new ArrayList<JobExecutionStat>();

		for (JobExecutionTask task : taskMap.values())
		{
			statList.add(task.getStat());
		}

		return statList;
	}

	public JobExecutionStat getStat(String siteName)
	{
		JobExecutionStat stat = null;

		if (taskMap.containsKey(siteName))
		{
			stat = taskMap.get(siteName).getStat();
		}

		return stat;
	}

	public void close()
	{
		for (JobExecutionTask task : taskMap.values())
		{
			task.pause();
		}

		ExecutorUtil.shutDown(executor, "Job Executor");
	}

	public static void main(String[] args)
	{
		
		JobExecutionStat stat = new JobExecutionStat();
		SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig("hepsiburada");
		stat.setSiteName(siteConfig.getSiteName());
		stat.setStatus(JobExecutionState.IDLE);
		JobExecutionTask task = new JobFactory().create(siteConfig, stat);
		task.run();
	}

}
