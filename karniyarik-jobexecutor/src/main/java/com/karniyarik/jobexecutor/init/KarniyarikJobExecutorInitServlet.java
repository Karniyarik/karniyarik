package com.karniyarik.jobexecutor.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.karniyarik.common.group.GroupMember;
import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.util.QuartzSchedulerFactory;
import com.karniyarik.jobexecutor.ExecutorGroupServer;
import com.karniyarik.jobexecutor.JobExecutorAdmin;

public class KarniyarikJobExecutorInitServlet implements ServletContextListener
{
	@Override
	public void contextInitialized(ServletContextEvent aSce)
	{
		new KarniyarikLogger().initLogger();
		
		ExecutorGroupServer.getInstance();
		JobExecutorServiceUtil.executorService = ExecutorGroupServer.getInstance();
		JobExecutorAdmin.getInstance();
		
		
		try
		{
			Scheduler scheduler = QuartzSchedulerFactory.getInstance().getSchedFact().getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail("CurencyUpdateJob", "currency");
			if(jobDetail == null)
			{
				jobDetail = new JobDetail("CurrencyUpdateJob", "currency", CurrencyUpdateJob.class);
				Trigger trigger = TriggerUtils.makeDailyTrigger("CurrencyUpdateTrigger", 16, 0);
				scheduler.scheduleJob(jobDetail, trigger);	
			}
			
			GroupMember memberDetails = ExecutorGroupServer.getInstance().getMemberDetails();
			
			jobDetail = scheduler.getJobDetail("HepsiburadaDataFetchJob", "hb");
			
			if(memberDetails.isGelirotaklariCapable() && jobDetail == null)
			{
				jobDetail = new JobDetail("HepsiburadaDataFetchJob", "hb", HepsiburadaXMLJob.class);
				Trigger trigger = new CronTrigger("HepsiburadaDataFetchTrigger", "hb", "0 5 7,13,19 * * ? *");
				scheduler.scheduleJob(jobDetail, trigger);	
			}
			else if(!memberDetails.isGelirotaklariCapable() && jobDetail != null)
			{
				scheduler.deleteJob("HepsiburadaDataFetchJob", "hb");
			}				
			
			jobDetail = scheduler.getJobDetail("ModelTrainerJob", "model");
			
			if(jobDetail == null)
			{
				jobDetail = new JobDetail("ModelTrainerJob", "model", ModelTrainerJob.class);
				Trigger trigger = new CronTrigger("ModelTrainerTrigger", "model", "0 5 11 1/5 * ? *");
				scheduler.scheduleJob(jobDetail, trigger);	
				
				//instant
//				jobDetail = new JobDetail("ModelTrainerInstantJob", "model", ModelTrainerJob.class);
//				trigger = TriggerUtils.makeImmediateTrigger(1, 1);
//				trigger.setName("ModelTrainerInstantTrigger");
//				trigger.setGroup("model");
//				scheduler.scheduleJob(jobDetail, trigger);
			}
		}
		catch (Exception e)
		{
			Logger.getLogger(this.getClass()).error("Can not initialize currency update quartz job", e);
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent aSce)
	{
		JobExecutorAdmin.getInstance().close();
		ExecutorGroupServer.getInstance().shutDown();
	}
}
