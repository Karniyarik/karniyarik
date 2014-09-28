package com.karniyarik.web;

import java.util.Calendar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.karniyarik.citydeal.CityDealProvider;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.util.QuartzSchedulerFactory;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;
import com.karniyarik.search.EngineRepository;
import com.karniyarik.search.searcher.logger.SearchLogIndexer;
import com.karniyarik.web.bendeistiyorum.DailyOpportunityLoader;
import com.karniyarik.web.job.AutoCompleteRefreshJob;
import com.karniyarik.web.job.CityDealJob;
import com.karniyarik.web.job.DailyOpportunityRefreshJob;
import com.karniyarik.web.job.EnterpriseAndStatSyncJob;
import com.karniyarik.web.job.GeneralSiteStatisticsUpdateJob;
import com.karniyarik.web.job.SOLRIndexCommitJob;
import com.karniyarik.web.job.SOLRIndexOptimizeJob;
import com.karniyarik.web.job.SiteInfoJob;
import com.karniyarik.web.job.SitemapForDateUpdateJob;
import com.karniyarik.web.job.SitemapForSearchLogJob;
import com.karniyarik.web.job.StatusUpdateJob;
import com.karniyarik.web.job.TwitterMessageFetchJob;
import com.karniyarik.web.util.IndexPublishUtil;
import com.karniyarik.web.util.WebApplicationDataHolder;

public class KarniyarikWebInitServlet implements ServletContextListener
{
	Logger	log	= Logger.getLogger(KarniyarikWebInitServlet.class);

	@Override
	public void contextInitialized(ServletContextEvent aSce)
	{
		new KarniyarikLogger().initLogger();

//		new DatabaseTableCreator(KarniyarikRepository.getInstance()
//				.getConfig().getConfigurationBundle().getDbConfig())
//				.createCityDealEmailTable();
		
		WebApplicationDataHolder.getInstance().refreshSiteData();
		
		QuartzSchedulerFactory.getInstance().getSchedFact();
		
		new IndexPublishUtil().createPublishDirectory();

		scheduleJobs(aSce);

		// initialize searcher implementations
		EngineRepository.getInstance();

		// initialize search log indexer
		SearchLogIndexer.getInstance();

		// initialize turkish analyzer pool
		TurkishAnalyzerPool.getInstance();

		// initialize ben de istiyorum products
		DailyOpportunityLoader.getInstance().refreshData();
		
		CityDealProvider.getInstance();
	}

	private void scheduleJobs(ServletContextEvent aSce)
	{
		SchedulerFactory factory = QuartzSchedulerFactory.getInstance().getSchedFact();
		
		try
		{
			Scheduler scheduler = factory.getScheduler();

			JobDetail jobDetail = new JobDetail("siteInfoJob", "gr1", SiteInfoJob.class);
			Trigger trigger = TriggerUtils.makeHourlyTrigger("siteInfoJobTrigger");
			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("statusFetchJob", "gr1", TwitterMessageFetchJob.class);
			trigger = TriggerUtils.makeHourlyTrigger(2);
			trigger.setName("statusFetchTrigger");
			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("sitemapForSearchLogJob", "gr1", SitemapForSearchLogJob.class);
			trigger = TriggerUtils.makeWeeklyTrigger("sitemapForSearchLogJobTrigger", 1, 1, 0);
			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("sitemapDateUpdateJob", "gr1", SitemapForDateUpdateJob.class);
			trigger = TriggerUtils.makeMonthlyTrigger("sitemapDateUpdateJobTrigger", 1, 3, 0);
			scheduler.scheduleJob(jobDetail, trigger);

			if (KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getStatusUpdatesEnabled())
			{
				jobDetail = new JobDetail("statusUpdateJob", "gr1", StatusUpdateJob.class);
				trigger = TriggerUtils.makeWeeklyTrigger("statusUpdateTrigger", 1, 7, 0);
				trigger.setName("statusUpdateTrigger");
				scheduler.scheduleJob(jobDetail, trigger);
				//log.info("Status update is scheduled");
			}

//			jobDetail = new JobDetail("indexBackupJob", "gr1", IndexBackupJob.class);
//			trigger = TriggerUtils.makeDailyTrigger("indexBackupJobTrigger", 5, 0);
//			jobDetail.getJobDataMap().put(SitemapForDateUpdateJob.PATH, aSce.getServletContext().getRealPath("sitemap"));
//			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("benDeIstiyorumRefreshJob", "gr1", DailyOpportunityRefreshJob.class);
			trigger = TriggerUtils.makeDailyTrigger("benDeIstiyorumRefreshJobTrigger", 12, 30);
			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("enterpriseAndStatSynchJob", "gr1", EnterpriseAndStatSyncJob.class);
			trigger = TriggerUtils.makeHourlyTrigger(3);
			trigger.setName("enterpriseDataUpdateJobTrigger");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 1);
			trigger.setStartTime(cal.getTime());
			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("CityDealRefreshJob", "gr1", CityDealJob.class);
			trigger = new CronTrigger("CityDealRefreshJobTrigger", "gr1", "0 5 0,3,6,7,8,9,12,14,15,17,18,20,21,23 * * ? *");
			scheduler.scheduleJob(jobDetail, trigger);

			jobDetail = new JobDetail("CityDealRefreshJobInstant", "gr1", CityDealJob.class);
			trigger = TriggerUtils.makeMinutelyTrigger(5, 1);
			trigger.setName("CityDealRefreshJobTriggerInstant");
			cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 2);
			trigger.setStartTime(cal.getTime());
			scheduler.scheduleJob(jobDetail, trigger);
			
//			jobDetail = new JobDetail("IndexMergeCheckJob", "gr1", IndexMergerCheckJob.class);
//			trigger = TriggerUtils.makeHourlyTrigger(4);
//			trigger.setName("IndexMergeCheckJobTriggerInstant");
//			cal = Calendar.getInstance();
//			cal.add(Calendar.MINUTE, 10);
//			trigger.setStartTime(cal.getTime());
//			scheduler.scheduleJob(jobDetail, trigger);
			
			jobDetail = new JobDetail("GeneralStatUpdateJob", "gr1", GeneralSiteStatisticsUpdateJob.class);
			trigger = new CronTrigger("GeneralStatUpdateTrigger", "gr1", "0 30 1 * * ? *");
			scheduler.scheduleJob(jobDetail, trigger);
			
			jobDetail = new JobDetail("AnalyticsUpdateJob", "gr1", GeneralSiteStatisticsUpdateJob.class);
			trigger = TriggerUtils.makeWeeklyTrigger("AnalyticsUpdateTrigger", 1, 0, 10);
			scheduler.scheduleJob(jobDetail, trigger);
			
			jobDetail = new JobDetail("AutoCompleteRefreshJob", "gr1", AutoCompleteRefreshJob.class);
			trigger = TriggerUtils.makeWeeklyTrigger("AutoCompleteRefreshTrigger", 2, 7, 10);
			scheduler.scheduleJob(jobDetail, trigger);
			
			jobDetail = new JobDetail("SOLROptimizeJob", "gr1", SOLRIndexOptimizeJob.class);
			trigger = TriggerUtils.makeDailyTrigger("SOLROptimizeTrigger", 6, 0);
			scheduler.scheduleJob(jobDetail, trigger);
			jobDetail = new JobDetail("SOLRCommitJob", "gr1", SOLRIndexCommitJob.class);
			trigger = new CronTrigger("SOLRCommitTrigger", "gr1", "0 5 9,16,23 * * ? *");
			scheduler.scheduleJob(jobDetail, trigger);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			KarniyarikLogger.logExceptionSummary("Cannot initialize scheduler", e, log);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent aSce)
	{
		EngineRepository.getInstance().shutDownLogger();
		SearchLogIndexer.getInstance().close();
		QuartzSchedulerFactory.getInstance().shutdown();
	}
}
