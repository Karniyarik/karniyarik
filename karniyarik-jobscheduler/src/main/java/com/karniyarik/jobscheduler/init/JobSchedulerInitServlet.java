package com.karniyarik.jobscheduler.init;

import java.io.File;
import java.sql.Connection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.util.QuartzSchedulerFactory;
import com.karniyarik.common.util.SiteConfUpdateUtil;
import com.karniyarik.common.util.sftp.SFTPUtil;
import com.karniyarik.externalrank.alexa.AlexaRankRegistry;
import com.karniyarik.jobscheduler.AlexaInfoUpdateJob;
import com.karniyarik.jobscheduler.JobSchedulerAdmin;
import com.karniyarik.jobscheduler.SchedulerGroupServer;
import com.karniyarik.jobscheduler.ga.AnalyticsUpdateJob;
import com.karniyarik.jobscheduler.warning.WeeklyWarningNotifier;

public class JobSchedulerInitServlet implements ServletContextListener
{
	private Logger log = Logger.getLogger(JobSchedulerInitServlet.class);

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		new KarniyarikLogger().initLogger();
		
		log.info("Initializiong db connection");
		Connection connection = DBConnectionProvider.getConnection(true, true, Connection.TRANSACTION_READ_COMMITTED);
		log.info("DB Connection initialized");
		
		log.info("Creating stat directory");
		File jobStatisticsDir = new File(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSchedulerConfig().getJobStatisticsDirectory());
		if (!jobStatisticsDir.exists())
		{
			try
			{
				FileUtils.forceMkdir(jobStatisticsDir);
			}
			catch (Throwable e)
			{
				getLogger().error("Can not create job statistics directory", e);
				throw new RuntimeException("Can not create job statistics directory", e);
			}
		}
		
		log.info("initializing group server");
		SchedulerGroupServer.getInstance();
		
		JobExecutorServiceUtil.schedulerService = SchedulerGroupServer.getInstance();
		
		log.info("Initializing quartz scheduler");
		QuartzSchedulerFactory.getInstance().getSchedFact();
		
		log.info("Initializing admin");
		JobSchedulerAdmin.getInstance();

		SiteConfUpdateUtil siteConfUpdateUtil = new SiteConfUpdateUtil();
		File archive = siteConfUpdateUtil.createSiteConfArchive();
		if (KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getPublishIndex())
		{
			SFTPUtil.publishToWebServer(archive);
		}
		
		try {
			siteConfUpdateUtil.callSiteConfRefreshServlets(archive.getName());
			siteConfUpdateUtil.callDeleteSiteConfArchiveServlet(archive.getName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			Scheduler scheduler = QuartzSchedulerFactory.getInstance().getSchedFact().getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail("AnalyticsUpdateJob", "analytics");
			if(jobDetail == null)
			{
				jobDetail = new JobDetail("AnalyticsUpdateJob", "analytics", AnalyticsUpdateJob.class);
				Trigger trigger = TriggerUtils.makeHourlyTrigger(2);
				trigger.setName("AnalyticsUpdateTrigger");
				scheduler.scheduleJob(jobDetail, trigger);	
			}
			
			jobDetail = scheduler.getJobDetail("WeeklyNotifier", "notifier");
			if(jobDetail == null)
			{
				jobDetail = new JobDetail("WeeklyNotifier", "notifier", WeeklyWarningNotifier.class);
				Trigger trigger = TriggerUtils.makeWeeklyTrigger("WeeklyNotifierTrigger", TriggerUtils.SUNDAY, 23, 1);
				scheduler.scheduleJob(jobDetail, trigger);	
			}
			
			jobDetail = scheduler.getJobDetail("AlexaUpdate", "update");
			
			if(jobDetail == null)
			{
				jobDetail = new JobDetail("AlexaUpdate", "update", AlexaInfoUpdateJob.class);
				Trigger trigger = TriggerUtils.makeWeeklyTrigger("AlexaUpdateTrigger", TriggerUtils.SUNDAY, 23, 30);
				scheduler.scheduleJob(jobDetail, trigger);
			}
			
			//load alexa info
			AlexaRankRegistry.getInstance();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		JobSchedulerAdmin.getInstance().shutDown();
		SchedulerGroupServer.getInstance().shutDown();
		QuartzSchedulerFactory.getInstance().shutdown();
	}
	
	private Logger getLogger() {
		return log;
	}

}
