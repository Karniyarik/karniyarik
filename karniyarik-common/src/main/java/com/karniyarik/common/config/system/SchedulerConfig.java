package com.karniyarik.common.config.system;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

/**
 * Configuration object for the scheduler.
 * 
 * @author siyamed
 * 
 */

@SuppressWarnings("serial")
public class SchedulerConfig extends ConfigurationBase
{
	private static final String	QUEUE_CHECK_CRON_JOB	= "";
	private static final int	MAX_RUNNING_CRAWLERS	= 10;
	private final String		JOBSTATISTICS_DIR		= "C:/krnyrk/jobstatistics";

	public SchedulerConfig() throws Exception
	{
		super(ConfigurationURLUtil.getSchedulerConf());
	}

	public String getQueueCheckCronJob()
	{
		return getString("scheduler.queuecheckcronjob", QUEUE_CHECK_CRON_JOB);
	}

	public void setQueueCheckCronJob(String value)
	{
		setProperty("scheduler.queuecheckcronjob", value);
	}

	public int getMaxRunningCrawlers()
	{
		return getInt("scheduler.maxrunningcrawlers", MAX_RUNNING_CRAWLERS);
	}

	public void setMaxRunningCrawlers(int value)
	{
		setProperty("scheduler.maxrunningcrawlers", value);
	}

	public String getJobStatisticsDirectory()
	{
		return getString("scheduler.jobstatisticsdir", JOBSTATISTICS_DIR);
	}
}