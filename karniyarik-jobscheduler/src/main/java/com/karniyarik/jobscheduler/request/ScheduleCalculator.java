package com.karniyarik.jobscheduler.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Trigger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.jobscheduler.JobSchedulerAdmin;
import com.karniyarik.jobscheduler.util.DateFormatter;
import com.karniyarik.jobscheduler.vo.ScheduleInformation;

import edu.emory.mathcs.backport.java.util.Collections;

public class ScheduleCalculator
{
	public static List<ScheduleInformation> getSchedules(List<JobExecutionStat> stats)
	{
		List<ScheduleInformation> result = new ArrayList<ScheduleInformation>();
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		
		Date currentDate = new Date();
		for(JobExecutionStat stat: stats) {
			
			ScheduleInformation info = new ScheduleInformation();
			info.setSitename(stat.getSiteName());
			Trigger trigger = JobSchedulerAdmin.getInstance().getScheduleInformation(info.getSitename());
			SiteConfig siteConfig = sitesConfig.getSiteConfig(info.getSitename());
			
			boolean isDataFeed = false;
			if(siteConfig != null)
			{
				isDataFeed = siteConfig.hasDatafeed();
			}
			
			info.setDatafeed(isDataFeed);
			if(stat.getStartDate() > 0) 
			{
				info.setBeginDate(new Date(stat.getStartDate()));	
			}
			
			if(stat.getEndDate() > 0) 
			{
				info.setEndDate(new Date(stat.getEndDate()));	
			}
			
			info.setStartDate(trigger.getStartTime());
			info.setPreviousFireTime(trigger.getPreviousFireTime());
			info.setNextStartDate(trigger.getNextFireTime());
			info.setFinalFireTime(trigger.getFinalFireTime());
			info.setEndTime(trigger.getEndTime());
			info.setLastExecutionDuration(DateFormatter.getDuration(info.getStartDate(), info.getEndDate()));
			info.setDuration(DateFormatter.getDuration(currentDate, trigger.getNextFireTime()));
			info.setLastExecutionDurationStr(DateFormatter.getDurationStr(info.getStartDate(), info.getEndDate()));
			info.setDurationStr(DateFormatter.getDurationStr(currentDate, trigger.getNextFireTime()));
			result.add(info);
		}
		
		Collections.sort(result, new ScheduleInfoTimeLeftComparator());
		
		return result;
	}
}
