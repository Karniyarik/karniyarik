package com.karniyarik.common.group;

import java.util.Map;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionStats;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;

public interface ISchedulerService {
	public boolean sendStartCommand(GroupMember member, SiteConfig siteConfig,JobExecutionStat stat);
	public Map<GroupMember, JobExecutionStats> getRunningStats();
	//public JobExecutionStats getRunningStats(GroupMember member);
	public boolean sendPauseCommand(String siteName);
	public boolean sendEndCommand(String siteName);
	public boolean sendPauseAllCommand();
	public boolean sendNewBrandFileContent(String content);
	public boolean sendReduceBoostCommand(SiteConfig siteConfig);
	//SCHEDULER SERVICE FUNCTIONS
	public void saveStat(JobExecutionStat stat);
	public AlexaSiteInfo getAlexaInfo(String sitename);
	public SiteConfig getSiteConfig(String sitename);
}
