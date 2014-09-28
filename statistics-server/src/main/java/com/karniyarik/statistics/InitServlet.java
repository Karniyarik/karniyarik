package com.karniyarik.statistics;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.db.DatabaseTableCreator;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.util.QuartzSchedulerFactory;

public class InitServlet implements ServletContextListener
{
	Logger	log	= Logger.getLogger(InitServlet.class);

	@Override
	public void contextInitialized(ServletContextEvent ctx)
	{
		new KarniyarikLogger().initLogger();

		DatabaseTableCreator tableCreator = new DatabaseTableCreator(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDbConfig());
		try
		{
			tableCreator.createSiteStatTable();
			tableCreator.createSearchLogTable();
			tableCreator.createSiteClickStatTable();
		}
		catch (Throwable e)
		{
			log.error("Can not create site stat or search log table", e);
			throw new RuntimeException("Can not create site stat or search log table", e);
		}

		QuartzSchedulerFactory.getInstance().getSchedFact();
		// initialize statistics collector
		// and top searches to web server
		StatisticsCollector.getInstance();
		
		scheduleJobs(ctx);
		
	}
	
	private void scheduleJobs(ServletContextEvent aSce)
	{
		SchedulerFactory factory = QuartzSchedulerFactory.getInstance().getSchedFact();
		
		try
		{
			Scheduler scheduler = factory.getScheduler();

			JobDetail jobDetail = new JobDetail("collectStatisticsJob", "gr1", SaveStatisticsJob.class);
			Trigger trigger = TriggerUtils.makeDailyTrigger("collectStatisticsTrigger", 1, 0);
			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("dailyCacheClearJob", "gr1", DailyCacheClearJob.class);
			trigger = TriggerUtils.makeDailyTrigger("dailyCacheClearTrigger", 23, 58);
			scheduler.scheduleJob(jobDetail, trigger);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			KarniyarikLogger.logExceptionSummary("Cannot initialize scheduler",e, log);
		}
	}


	@Override
	public void contextDestroyed(ServletContextEvent aSce)
	{
		QuartzSchedulerFactory.getInstance().shutdown();
		StatisticsCollector.getInstance().close();
	}
}
