package com.karniyarik.common.group;

import java.util.Map;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionStats;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;

public class JobExecutorServiceUtil
{
	
	public static ISchedulerService schedulerService = null;
	public static IExecutorService executorService = null;
	
	public static void sendJobExecutionStatJobToScheduler(JobExecutionStat stat)
	{
		executorService.sendJobExecutionStatJobToScheduler(stat);
	}
	
	public static AlexaSiteInfo getAlexaInfo(String sitename)
	{
		return executorService.getAlexaInfo(sitename);
	}

	public static SiteConfig getSiteConfig(String sitename)
	{
		return executorService.getSiteConfig(sitename);
	}

	public static void startExecuting(GroupMember member, SiteConfig siteConfig, JobExecutionStat stat)
	{
		schedulerService.sendStartCommand(member, siteConfig, stat);
	}

	public static void pause(String siteName)
	{
		schedulerService.sendPauseCommand(siteName);
	}

	public static void end(String siteName)
	{
		schedulerService.sendEndCommand(siteName);
	}
	
	public static void pauseAll()
	{
		schedulerService.sendPauseAllCommand();
	}

	public static Map<GroupMember, JobExecutionStats> getRunningStats()
	{
		return schedulerService.getRunningStats();
	}
	
	public static Boolean sendReduceBoostCommand(SiteConfig siteConfig)
	{
		return schedulerService.sendReduceBoostCommand(siteConfig);
	}

}
