package com.karniyarik.jobscheduler;

import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.util.QuartzSchedulerFactory;

public class SiteConfigSchedulerFactory {
	public static final String DAILY = "daily";
	public static final String DEFAULT_GROUP_NAME 		= "scheduler";
	public static final String SITE_GROUP_NAME 			=	"site";

	private SiteConfig config = null;
	private JobDetail jobDetail = null;
	private Trigger trigger = null;
	private Scheduler scheduler = null;
	
	public SiteConfigSchedulerFactory(SiteConfig config) {
		this.config = config;
		
		try {
			scheduler = QuartzSchedulerFactory.getInstance().getSchedFact().getScheduler();
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void scheduleSite() {
		
		try {
			String jobDetailName = getJobDetailName();
			
			jobDetail = scheduler.getJobDetail(jobDetailName, SITE_GROUP_NAME);
			
			if (StringUtils.isNotBlank(config.getCron()) && jobDetail == null)
			{
				jobDetail = new JobDetail(jobDetailName, SITE_GROUP_NAME, SiteScheduleJob.class);
				jobDetail.getJobDataMap().put(SiteScheduleJob.siteNameKey, config.getSiteName());
				
				trigger = createTrigger();
				scheduler.scheduleJob(jobDetail, trigger);
			}
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public void rescheduleSite()
	{
		if (config != null && !config.hasDatafeed())
		{
			try
			{
				Scheduler scheduler = QuartzSchedulerFactory.getInstance().getSchedFact().getScheduler();
				Trigger trigger = scheduler.getTrigger(getTriggerName(), SITE_GROUP_NAME);
				trigger = createTrigger(trigger);
				scheduler.rescheduleJob(trigger.getName(), SITE_GROUP_NAME, trigger);
			}
			catch (Throwable e)
			{
				throw new RuntimeException(e);
			}
		}	
	}
	
	public Trigger getScheduledTrigger()
	{
		try {
			Trigger trigger = scheduler.getTrigger(getTriggerName(), SITE_GROUP_NAME);
			return trigger;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Trigger createTrigger()
	{
		return createTrigger(null);
	}
	
	private Trigger createTrigger(Trigger oldTrigger)
	{
		Trigger result = null;
		
		try {
			String triggerName = getTriggerName();
			Random random = new Random(config.getSiteName().hashCode());
			
			String triggerConfig = config.getCron();
			String[] split = triggerConfig.trim().split("=");
			SiteConfigTriggerType triggerType = SiteConfigTriggerType.valueOf(split[0]);
			
			int count = 1;
			switch(triggerType)
			{
				case hourly:
					count = Integer.parseInt(split[1]);
					result = getHourlyTrigger(count, triggerName, random, 2, oldTrigger);
					break;
				case daily:
					count = Integer.parseInt(split[1]);
					int hourCount = count * 24;
					result = getHourlyTrigger(hourCount, triggerName, random, 6, oldTrigger);
					break;
				case weekly:
					count = Integer.parseInt(split[1]);
					hourCount = count * 24 * 7;				
					result = getHourlyTrigger(hourCount, triggerName, random, 24, oldTrigger);
					break;
				case cron: 
					String cronStr = split[1];
					if(oldTrigger != null)
					{
						result = oldTrigger;
					}
					else
					{
						result = new CronTrigger(triggerName, SITE_GROUP_NAME, cronStr); 
					}
					break;
				default: 
					throw new RuntimeException();
			}
		} catch (Throwable e) {
			throw new RuntimeException(config.getSiteName() + " / " + config.getCron() + " / Unknown cron type. Job scheduling configuration shall be (cron|daily|hourly|weekly)=(count|cronstr)");
		} 
		return result;
	}
	
	private Trigger getHourlyTrigger(int hourCount, String triggerName, Random random, int randomHourDelayConstant, Trigger oldTrigger)
	{
		Trigger result = null;
		if(oldTrigger != null)
		{
			result = oldTrigger;
		}
		else
		{
			result = TriggerUtils.makeHourlyTrigger(hourCount);
			result.setName(triggerName);
			result.setGroup(SITE_GROUP_NAME);
		}
		
		int randomStartMinuteDelay = random.nextInt(randomHourDelayConstant*60);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, randomStartMinuteDelay);
		
		if(oldTrigger != null)
		{
			//this call is only made with reschedule, therefore I have to add the default hour count as the start date
			cal.add(Calendar.HOUR, hourCount);
		}
		result.setStartTime(cal.getTime());
		return result;
	}
	
	private String getTriggerName()
	{
		return config.getSiteName() + "JobTrigger";
	}

	private String getJobDetailName()
	{
		return config.getSiteName() + "Job";
	}
	
	public Trigger getTrigger() {
		return trigger;
	}
	
	public JobDetail getJobDetail() {
		return jobDetail;
	}
}
