package com.karniyarik.jobscheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.group.GroupMember;
import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionState;
import com.karniyarik.common.statistics.vo.JobExecutionStats;
import com.karniyarik.common.util.ConfigurationURLUtil;
import com.karniyarik.common.util.JerseyUtil;
import com.karniyarik.common.util.QuartzSchedulerFactory;
import com.karniyarik.jobscheduler.dao.JobExecutionStatCache;
import com.karniyarik.jobscheduler.dao.JobExecutionStatDao;
import com.karniyarik.jobscheduler.dao.JobExecutionStatDaoFactory;
import com.karniyarik.jobscheduler.util.JobExecutionStatComparator;
import com.karniyarik.jobscheduler.util.JobHealthComparator;
import com.karniyarik.jobscheduler.util.JobStatisticsSummary;
import com.karniyarik.jobscheduler.util.JobStatisticsSummaryCalculator;

import edu.emory.mathcs.backport.java.util.Collections;

public class JobSchedulerAdmin
{
	private static JobSchedulerAdmin			INSTANCE		= null;
	
	private Map<String, JobExecutionStat>		statisticsMap;
	private Map<String, JobStatisticsSummary>	statisticsSummaryMap;
	private Map<GroupMember, JobExecutionStats> memberStatMap 	= new HashMap<GroupMember, JobExecutionStats>();
	
	private JobExecutionStatDaoFactory			daoFactory;
	private int									maxCrawlersPerServer;
	private Queue<String>						waitingQueue;
	private Boolean								scheduleLock	= false;
	private Map<String, Integer>				missingStatInSynchCount = new HashMap<String, Integer>();
	private Timer timer = new Timer();
	private BoostReduceCallTracker 				boostReduceTracker = null;
	
	private JobSchedulerAdmin()
	{
		statisticsMap = new HashMap<String, JobExecutionStat>();
		statisticsSummaryMap = new HashMap<String, JobStatisticsSummary>();
		daoFactory = new JobExecutionStatDaoFactory();
		
		maxCrawlersPerServer = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSchedulerConfig().getMaxRunningCrawlers();
//		maxCrawlersPerServer = 2; 
		waitingQueue = new LinkedList<String>();

		boostReduceTracker = new BoostReduceCallTracker();
		
		refreshStatisticsMap();
		synchWithExecutors();
		scheduleQuartzJobs();

		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				synchWithExecutors();
			}
		}, 120000, 30000);
	}

	public static synchronized JobSchedulerAdmin getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new JobSchedulerAdmin();
		}
		return INSTANCE;
	}

	public void refreshStatisticsMap()
	{
		synchronized (scheduleLock)
		{
			SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();

			JobExecutionStatDao dao;
			for (SiteConfig siteConfig : sitesConfig.getSiteConfigList())
			{
				String siteName = siteConfig.getSiteName();
				if (!statisticsMap.containsKey(siteName))
				{
					JobExecutionStat currentStat = JobExecutionStatCache.getInstance().getCurrentStat(siteName);
					statisticsMap.put(siteConfig.getSiteName(), currentStat);
				}
			}

			List<String> removeList = new ArrayList<String>();
			for (String siteName : statisticsMap.keySet())
			{
				if (sitesConfig.getSiteConfig(siteName) == null)
				{
					// this site has been removed externally
					// while scheduler admin is running
					// so we should remove it from the map
					removeList.add(siteName);
				}
			}

			// remove deleted sites from map
			// and waiting queue
			for (String siteName : removeList)
			{
				statisticsMap.remove(siteName);
				if (waitingQueue.contains(siteName))
				{
					waitingQueue.remove(siteName);
				}
			}
		}
	}

	private void synchWithExecutors()
	{
		List<JobExecutionStat> newStats = new ArrayList<JobExecutionStat>();
		
		synchronized (scheduleLock)
		{
			JobExecutionStats stats;
			
			Map<GroupMember, JobExecutionStats> runningStats = JobExecutorServiceUtil.getRunningStats();
			
			memberStatMap.clear();
			//statisticsMap.clear();
			
			List<String> oldSites = new ArrayList<String>();
			oldSites.addAll(statisticsMap.keySet());
			
			for (GroupMember member: runningStats.keySet())
			{
				stats = runningStats.get(member);
				
				if (stats != null)
				{
					memberStatMap.put(member, stats);
					for (JobExecutionStat stat : stats.getJobExecutionStatList())
					{
						statisticsMap.put(stat.getSiteName(), stat);
						oldSites.remove(stat.getSiteName());
					}
				}
			}
			
			
			for(String sitename: oldSites)
			{
				int count = 0; 
				JobExecutionStat jobExecutionStat = statisticsMap.get(sitename);
				if(jobExecutionStat != null && !jobExecutionStat.getStatus().isRunning())
				{
					continue;
				}
				
				if(missingStatInSynchCount.get(sitename) != null)
				{
					count = missingStatInSynchCount.get(sitename);
				}
				
				if(count > 3) //constant is fully trash. just want to give an end to missing stats
				{
					JobExecutionStat stat = statisticsMap.get(sitename);					
					JobExecutionStatDao dao = daoFactory.create(sitename);
					
					if(stat == null)
					{
						stat = new JobExecutionStat();
						stat.setSiteName(sitename);
						stat.setStatus(JobExecutionState.CRAWLING_FAILED);
						dao.insertStat(stat);	
					}
					else
					{
						stat.setStatus(JobExecutionState.CRAWLING_FAILED);
						stat.setStatusMessage("No update to executing state");
						dao.updateCurrentStat(stat);						
					}
					
					newStats.add(stat);
					statisticsMap.put(stat.getSiteName(), stat);
					missingStatInSynchCount.remove(sitename);
				}
				else
				{
					count++;
					missingStatInSynchCount.put(sitename, count);
				}
			}
		}
		
		if(newStats.size() > 0)
		{
			for(JobExecutionStat stat: newStats)
			{
				saveStat(stat);
			}
		}
	}

	private void scheduleQuartzJobs()
	{
		try
		{
			Scheduler scheduler = QuartzSchedulerFactory.getInstance().getSchedFact().getScheduler();

			JobDetail jobDetail = scheduler.getJobDetail("SchedulerWaitingSitesJob", SiteConfigSchedulerFactory.DEFAULT_GROUP_NAME); 
			
			if(jobDetail == null)
			{
				jobDetail = new JobDetail("SchedulerWaitingSitesJob", SiteConfigSchedulerFactory.DEFAULT_GROUP_NAME, WaitingSiteScheduleJob.class);
				String queueCheckCronConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSchedulerConfig().getQueueCheckCronJob();
				Trigger trigger = new CronTrigger("JobSchedulerWaitingSitesJobTrigger", SiteConfigSchedulerFactory.DEFAULT_GROUP_NAME, queueCheckCronConfig);
				scheduler.scheduleJob(jobDetail, trigger);
			}
			
			SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
			for (SiteConfig config : sitesConfig.getSiteConfigList())
			{
				scheduleSiteQuartzJob(config);
			}
		}
		catch (Exception e)
		{
			getLogger().error("Cannot initialize job scheduler quartz jobs", e);
			throw new RuntimeException("Cannot initialize job scheduler quartz jobs", e);
		}
	}

	public void scheduleSiteQuartzJob(SiteConfig config) {
		SiteConfigSchedulerFactory factory = new SiteConfigSchedulerFactory(config);
		
		factory.scheduleSite();
		Trigger trigger = factory.getTrigger();
		
		JobExecutionStat statistics = getSiteStatistics(config.getSiteName());
		if(statistics != null && trigger != null)
		{
			statistics.setNextExecutionDate(trigger.getNextFireTime().getTime());
		}
	}
	
	public Trigger getScheduleInformation(String sitename)
	{
		SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
		SiteConfigSchedulerFactory factory = new SiteConfigSchedulerFactory(siteConfig);
		return factory.getScheduledTrigger();
	}
	
	public void scheduleAll()
	{
		synchronized (scheduleLock)
		{
			for (String siteName : statisticsMap.keySet())
			{
				schedule(siteName);
			}
		}
	}

	public void schedule(String siteName)
	{
		SiteConfig config = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(siteName);
		if(config == null)
		{
			return;
		}
		synchronized (scheduleLock)
		{
			if (!isRunning(siteName))
			{
				GroupMember server = null;
				
				JobExecutionStat stat = statisticsMap.get(siteName);
				
				if(stat == null)
				{
					//cannot schedule it
					return;
				}
				synchWithExecutors();
				
				if (stat.getStatus() == JobExecutionState.CRAWLING_PAUSED)
				{
					server = SchedulerGroupServer.getInstance().getMemberByName(stat.getRunningServer());
				}
				else
				{
					server = getLeastBusyServer(siteName);
				}

				if (server != null)
				{
					stat.setRunningServer(server.getUuid());
					
					if (stat.getStatus().hasEnded() || stat.getStatus().hasFailed())
					{
						JobExecutionStatDao dao = daoFactory.create(siteName);
						JobExecutionStat newStat = new JobExecutionStat();
						newStat.setSiteName(siteName);
						newStat.setStatus(JobExecutionState.IDLE);
						newStat.setRunningServer(server.getUuid());
						dao.insertStat(newStat);
						stat = newStat;
						statisticsMap.put(siteName, newStat);
					}
					
					SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(siteName);
					JobExecutorServiceUtil.startExecuting(server, siteConfig, stat);
					if (waitingQueue.contains(siteName))
					{
						waitingQueue.remove(siteName);
					}
				}
				else
				{
					if (!waitingQueue.contains(siteName))
					{
						waitingQueue.add(siteName);
					}
				}
			}
		}
	}

	public void pause(String siteName)
	{
		if (StringUtils.isNotBlank(siteName))
		{
			JobExecutorServiceUtil.pause(siteName);
		}
	}

	public void pauseAll()
	{
		JobExecutorServiceUtil.pauseAll();
	}

	public void end(String siteName)
	{
		if (StringUtils.isNotBlank(siteName))
		{
			JobExecutorServiceUtil.end(siteName);
		}
	}

	public void scheduleWaitingSites()
	{
		synchronized (scheduleLock)
		{
			while (waitingQueue.size() > 0)
			{
				String siteName = waitingQueue.peek();
				GroupMember leastBusyServer = getLeastBusyServer(siteName);
				if(leastBusyServer != null)
				{
					waitingQueue.poll();
					schedule(siteName);
				}
				else
				{
					break;
				}
			}				
		}
	}

	public void saveStat(JobExecutionStat stat)
	{
		synchronized (scheduleLock)
		{
			statisticsMap.put(stat.getSiteName(), stat);
			daoFactory.create(stat.getSiteName()).updateCurrentStat(stat);

			if (stat.getStatus().hasEnded())
			{
				rescheduleQuartzJob(stat.getSiteName());
			}
			
			if(stat.getStatus().hasFailed())
			{
				List<JobExecutionStat> statHistory = getStatHistory(stat.getSiteName(), 3);
				int failedCount = 0;
				for(JobExecutionStat oldStat: statHistory)
				{
					if(oldStat.getStatus().hasFailed())
					{
						failedCount++;
					}
				}
				//in order to prevent reducing the sites index contniously for a site
				//that fails more than 3 times (say 10 times)
				//the prevention code is written in IndexMergeTask (BoostReduceTracker)
				if(failedCount > 7)
				{
					try {
						String sitename = stat.getSiteName();
						SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
						//?? WTF?
						if(!boostReduceTracker.isReduceCalled(sitename))
						{
							JobExecutorServiceUtil.sendReduceBoostCommand(siteConfig);
							boostReduceTracker.mergeCalled(stat.getSiteName(), true);
						}
					} catch (Throwable e) {
						//do nothing
						int i = 5;
						int b = i;
					}
				}
			}
		}
	}

	/**
	 * 
	 * If a crawler has ended it should be rescheduled. Crawlers have a 3 day
	 * waiting period. After a crawler has ended a new quartz job should start
	 * to wait 3 days.
	 * 
	 * Data feeds are ignored inside this function, since their quartz job does
	 * not need to be rescheduled.
	 * 
	 * @param siteName
	 */
	private void rescheduleQuartzJob(String siteName)
	{
		SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(siteName);
		SiteConfigSchedulerFactory factory = new SiteConfigSchedulerFactory(siteConfig);
		factory.rescheduleSite();
	}

	private boolean isRunning(String siteName)
	{
		synchronized (scheduleLock)
		{
			JobExecutionStat jobExecutionStat = statisticsMap.get(siteName);
			
			if(jobExecutionStat != null)
			{
				return jobExecutionStat.getStatus().isRunning();
			}
			
			return false;
		}
	}

	public boolean isWaiting(String siteName)
	{
		synchronized (scheduleLock)
		{
			return waitingQueue.contains(siteName);
		}
	}

	private GroupMember getLeastBusyServer(String siteName)
	{
		if(StringUtils.isNotBlank(siteName))
		{
			SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
			SiteConfig siteConfig = sitesConfig.getSiteConfig(siteName);
			boolean isSelenium = siteConfig.isSelenium();
			boolean isclass = StringUtils.isNotBlank(siteConfig.getAffiliateClass()) || StringUtils.isNotBlank(siteConfig.getRankHelper()) || StringUtils.isNotBlank(siteConfig.getRuleClassName());
			String ip = siteConfig.getExecutingip();
			
			synchronized (scheduleLock)
			{
				SchedulerGroupServer.getInstance().getActiveMembers();
				
				int minCount = Integer.MAX_VALUE;
				GroupMember server = null;
				List<GroupMember> servers = null;
				
				if (isSelenium)
				{
					servers = SchedulerGroupServer.getInstance().getSeleniumCapable();
				}
				else if(isclass)
				{
					servers = SchedulerGroupServer.getInstance().getJobsWithCassCapable();
					if(servers == null || servers.size() < 1)
					{
						servers = SchedulerGroupServer.getInstance().getExecutors();
					}
				}
				else
				{
					servers = SchedulerGroupServer.getInstance().getExecutors();
				}
				
				
				for (GroupMember member: servers)
				{
					if(StringUtils.isNotBlank(ip) && member.getIp().trim().equals(ip.trim()))
					{
						JobExecutionStats jobExecutionStats = memberStatMap.get(member);
						if(jobExecutionStats != null)
						{
							int runningCount = jobExecutionStats.getJobExecutionStatList().size();
							
							if(runningCount < maxCrawlersPerServer)
							{
								server = member;
							}
						}
						break;
					}
					else
					{
						JobExecutionStats jobExecutionStats = memberStatMap.get(member);
						
						if(jobExecutionStats != null)
						{
							int runningCount = jobExecutionStats.getJobExecutionStatList().size();
							
							if(runningCount < minCount && runningCount < maxCrawlersPerServer)
							{
								minCount = runningCount;
								server = member;
							}
						}						
					}
				}
				
				return server;
			}			
		}
		return null;
	}
	
	public JobStatisticsSummary getStatisticsSummary(String sitename)
	{
		return statisticsSummaryMap.get(sitename);
	}
	
	public List<JobStatisticsSummary> getStatisticsSummary(List<JobAdminStateFilter> filters)
	{
		List<JobStatisticsSummary> resultList = new ArrayList<JobStatisticsSummary>();
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		List<JobExecutionStat> statList = getAllStatistics();
		
		for(JobExecutionStat stat: statList)
		{
			SiteConfig siteConfig = sitesConfig.getSiteConfig(stat.getSiteName());
			
			JobStatisticsSummary summ = statisticsSummaryMap.get(stat.getSiteName());

			boolean filterok = true;
			
			if(filters!= null)
			{
				for(JobAdminStateFilter filter: filters)
				{
					if(!filter.isSameState(stat, siteConfig, summ))
					{
						filterok = false;
					}
				}
			}
			
			if(filterok)
			{
				resultList.add(summ);
			}
		}
		
		Collections.sort(resultList, new JobHealthComparator());
		
		return resultList;		
	}
	
	public GroupMember getMember(String id)
	{
		return SchedulerGroupServer.getInstance().getMemberByName(id);
	}

	public JobExecutionStat getSiteStatistics(String sitename)
	{
		JobExecutionStat jobExecutionStat = statisticsMap.get(sitename);
		return jobExecutionStat;
	}

	public List<JobExecutionStat> getStatistics(){
		return getStatistics(new ArrayList<JobAdminStateFilter>());
	}

	public List<JobExecutionStat> getStatistics(JobAdminStateFilter[] filters)
	{
		return getStatistics(Arrays.asList(filters));
	}

	public List<JobExecutionStat> getStatistics(List<JobAdminStateFilter> filters){
		List<JobExecutionStat> statList = getAllStatistics();
		return getStatistics(filters, statList);
	}
	
	public List<JobExecutionStat> getStatistics(JobAdminStateFilter[] filters, List<JobExecutionStat> statList)
	{
		return getStatistics(Arrays.asList(filters), statList);
	}
	
	public List<JobExecutionStat> getStatistics(List<JobAdminStateFilter> filters, List<JobExecutionStat> statList)
	{
		List<JobExecutionStat> resultList = new ArrayList<JobExecutionStat>();
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		
		
		for(JobExecutionStat stat: statList)
		{
			SiteConfig siteConfig = sitesConfig.getSiteConfig(stat.getSiteName());
			
			JobStatisticsSummary summ = statisticsSummaryMap.get(stat.getSiteName());
			
			boolean filterok = true;

			if(filters!= null)
			{
				for(JobAdminStateFilter filter: filters)
				{
					if(!filter.isSameState(stat, siteConfig, summ))
					{
						filterok = false;
					}
				}
			}
			
			if(filterok)
			{
				resultList.add(stat);	
			}
		}
		
		return resultList;	
	}
	
	private List<JobExecutionStat> getAllStatistics()
	{
		List<JobExecutionStat> statList = new ArrayList<JobExecutionStat>();
		List<String> waitingList = new ArrayList<String>();
		
		Map<GroupMember, JobExecutionStats> runningStats = JobExecutorServiceUtil.getRunningStats();
		
		for(GroupMember member: runningStats.keySet())
		{
			JobExecutionStats jobExecutionStats = runningStats.get(member);
			statList.addAll(jobExecutionStats.getJobExecutionStatList());
		}
		
		synchronized (scheduleLock)
		{
			for (JobExecutionStat jobExecutionStat : statList)
			{
				statisticsMap.put(jobExecutionStat.getSiteName(), jobExecutionStat);
			}

			statList.clear();
			statList.addAll(statisticsMap.values());
			waitingList.addAll(waitingQueue);
			
			statisticsSummaryMap.clear();
			
			for (JobExecutionStat jobExecutionStat : statList)
			{
				JobStatisticsSummary summ = JobStatisticsSummaryCalculator.calculateSummary(jobExecutionStat, 300, 100);
				statisticsSummaryMap.put(jobExecutionStat.getSiteName(), summ);
			}
		}

		Collections.sort(statList, new JobExecutionStatComparator(waitingList));
		
		return statList;
	}

	public List<JobExecutionStat> getStatHistory(String sitename)	
	{
		return getStatHistory(sitename, -1);
	}
	
	public List<JobExecutionStat> getStatHistory(String sitename, int count)
	{
		List<JobExecutionStat> stats = JobExecutionStatCache.getInstance().getStatHistory(sitename, count);
		return stats;
	}
	
	public void setCapability(GroupMember member, Boolean selenium, Boolean jobclass)
	{
		SchedulerGroupServer.getInstance().setCapability(member, selenium, jobclass);
	}
	
	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

	public void deleteSite(String sitename)
	{
		end(sitename);
		File siteConfigDir = ConfigurationURLUtil.loadSiteConfDir();
		File configFile = new File(siteConfigDir.getAbsolutePath() + "/" + sitename + ".xml");
		if(configFile.exists())
		{
			configFile.delete();
		}
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		sitesConfig.deleteSiteConfig(sitename);
		refreshStatisticsMap();
		
		//inform web
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String webEndPoint = deploymentConfig.getMasterWebUrl() + "/rest/event/siteconf/delete";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("sitename", sitename);
		JerseyUtil.getObject(webEndPoint, parameters, String.class);
	}
	
	public static void main(String[] args)
	{
		List<JobExecutionStat> list = new ArrayList<JobExecutionStat>();

		JobExecutionStat stat;
		for (JobExecutionState state : JobExecutionState.values())
		{
			stat = new JobExecutionStat();
			stat.setStatus(state);
			list.add(stat);
		}

		Collections.sort(list, new JobExecutionStatComparator(new ArrayList<String>()));

		for (JobExecutionStat jobExecutionStat : list)
		{
			System.out.println(jobExecutionStat.getStatus().toString());
		}

	}

	public void shutDown() {
		timer.cancel();
	}

	public Boolean isBoostReduceCalled(String sitename) {
		return boostReduceTracker.isReduceCalled(sitename);
	}

	public void setBoostReduceCalled(String sitename, Boolean reduceBoost) {
		boostReduceTracker.mergeCalled(sitename, reduceBoost);
	}

}
