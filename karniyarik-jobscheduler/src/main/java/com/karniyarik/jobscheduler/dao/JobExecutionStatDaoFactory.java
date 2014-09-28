package com.karniyarik.jobscheduler.dao;

import java.io.File;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;

public class JobExecutionStatDaoFactory
{
	public JobExecutionStatDao create(String siteName)
	{
		File file = new File(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSchedulerConfig().getJobStatisticsDirectory() + File.separator + siteName + ".txt");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (Throwable e)
			{
				getLogger().error("Can not create job statistics file " + file.getAbsolutePath(), e);
				throw new RuntimeException("Can not create job statistics file " + file.getAbsolutePath(), e);
			}
		}

		return new JobExecutionStatDao(siteName, file);
	}

	private Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}
}
