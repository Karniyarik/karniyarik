package com.karniyarik.common.group;

import javax.ws.rs.core.Response;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionContext;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionStats;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;

public interface IExecutorService {
	public boolean sendJobExecutionStatJobToScheduler(JobExecutionStat stat);
	public void start(JobExecutionContext context);
	public void pause(String siteName);
	public void end(String siteName);
	public Response pause();
	public JobExecutionStats getRunningStats();
	public void updateBrandKB(String content);
	public AlexaSiteInfo getAlexaInfo(String sitename);
	public SiteConfig getSiteConfig(String sitename);
	public boolean reduceBoost(SiteConfig siteConfig);
}
