package com.karniyarik.controller.scheduler;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.SchedulerConfig;
import com.karniyarik.common.util.DateUtil;
import com.karniyarik.common.util.TableNameUtil;
import com.karniyarik.controller.ISiteControllerEventListener;
import com.karniyarik.controller.SiteControllerState;
import com.karniyarik.controller.SiteControllerThread;
import com.karniyarik.crawler.admin.CrawlerAdmin;

public class SiteScheduler implements ISiteControllerEventListener
{
	private int										mMaxRunningOps			= 0;

	private Map<String, SiteScheduleInfo>			mSiteScheduleInfoMap	= null;

	private ConcurrentLinkedQueue<SiteScheduleInfo>	mWaitingSiteQueue		= null;

	private Map<String, SiteControllerThread>		mRunningOperationsMap	= null;

	private boolean									mStarted				= false;

	private SchedulerConfig							mSchedulerConfig		= null;

	private boolean									mIsStop					= false;

	private SchedulerDAO							mDAO					= null;

	public SiteScheduler()
	{
		mSiteScheduleInfoMap = new HashMap<String, SiteScheduleInfo>();

		mWaitingSiteQueue = new ConcurrentLinkedQueue<SiteScheduleInfo>();

		mRunningOperationsMap = new HashMap<String, SiteControllerThread>();

		mSchedulerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSchedulerConfig();

		mDAO = new SchedulerDAO();

		prepareSiteScheduleInfoMap();

		loadSchedulersFromDB();

		initQueue();

		mMaxRunningOps = mSchedulerConfig.getMaxRunningCrawlers();
	}

	public void begin()
	{
		if (mStarted == false)
		{
			mIsStop = false;

			initTimer();

			mStarted = true;
		}
	}

	private void initTimer()
	{
		Timer aTimer = new Timer();

		aTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				cleanFinishedThreadsFromRunningQueue();
				createJobsWaitingInQueue();
			}
		}, new Date(), new Double(mSchedulerConfig.getQueueCheckPeriod() * 60 * 60 * 1000).intValue() /* hours */);
	}

	private void initQueue()
	{
		List<SiteScheduleInfo> aList = new ArrayList<SiteScheduleInfo>(mSiteScheduleInfoMap.values());

		Collections.sort(aList, new SiteScheduleInfoComparator(false));

		SiteScheduleInfo aMinDateInfo = aList.get(0);

		Collections.sort(aList, new SiteScheduleInfoComparator(true));

		int aMinIndex = aList.indexOf(aMinDateInfo);

		int anIndex = 0;
		for (SiteScheduleInfo anInfo : aList)
		{
			if (anIndex < aMinIndex)
			{
				anInfo.setNextExecutionDate(aMinDateInfo.getNextExecutionDate());
			}

			mWaitingSiteQueue.add(anInfo);

			anIndex++;
		}
	}

	private void prepareSiteScheduleInfoMap()
	{
		Collection<SiteConfig> aSiteConfigList = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfigList();

		Date aCurrentDate = DateUtil.getCurrentLocalDate();

		for (SiteConfig aSiteConfig : aSiteConfigList)
		{
			SiteScheduleInfo anInfo = new SiteScheduleInfo();
			anInfo.setSiteConfig(aSiteConfig);
			anInfo.setNextExecutionDate(aCurrentDate);
			anInfo.setCrawlingPeriod(aSiteConfig.getCrawlPeriod());
			anInfo.setState(SiteControllerState.IDLE);
			mSiteScheduleInfoMap.put(aSiteConfig.getSiteName(), anInfo);
		}
	}

	private void loadSchedulersFromDB()
	{
		List<SiteScheduleInfo> aSchedulerList = mDAO.getSchedulerStates();

		String aSiteName = null;

		SiteScheduleInfo anInfo = null;

		for (SiteScheduleInfo aDBInfo : aSchedulerList)
		{
			if (aDBInfo.getSiteConfig() != null)
			{
				aSiteName = aDBInfo.getSiteConfig().getSiteName();

				if (mSiteScheduleInfoMap.containsKey(aSiteName))
				{
					anInfo = mSiteScheduleInfoMap.get(aSiteName);
					anInfo.setCrawlingPeriod(aDBInfo.getCrawlingPeriod());
					anInfo.setNextExecutionDate(aDBInfo.getNextExecutionDate());
					anInfo.setState(aDBInfo.getState());
				}
			}
		}
	}

	public void pause()
	{
		mIsStop = true;

		// for (SiteControllerThread aThread : mRunningOperationsMap.values())
		// {
		// aThread.pause();
		// }
		//
		while (mRunningOperationsMap.size() > 0)
		{
			SiteControllerThread aThread = mRunningOperationsMap.values().iterator().next();
			aThread.pause();
			try
			{
				aThread.join();
				// Thread.sleep(3000);
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}

		storeState();
	}

	public void pauseAfterCurrent()
	{
		mIsStop = true;
	}

	public void pause(String aSiteName)
	{
		if (mRunningOperationsMap.containsKey(aSiteName))
		{
			mRunningOperationsMap.get(aSiteName).pause();
		}
	}

	private void storeState()
	{
		List<SiteScheduleInfo> anInfoList = new ArrayList<SiteScheduleInfo>();

		for (SiteScheduleInfo aScheduleInfo : mWaitingSiteQueue)
		{
			anInfoList.add(aScheduleInfo);
		}

		SiteControllerThread aThread = null;
		SiteScheduleInfo anInfo = null;
		for (String aSiteName : mRunningOperationsMap.keySet())
		{
			anInfo = mSiteScheduleInfoMap.get(aSiteName);
			aThread = mRunningOperationsMap.get(aSiteName);
			anInfo.setState(aThread.getSiteState());
			anInfoList.add(anInfo);
		}

		mDAO.storeSchedulerState(anInfoList);
	}

	private void createJobsWaitingInQueue()
	{
		if (mIsStop)
		{
			return;
		}

		try
		{
			Date aCurrentDate = DateUtil.getCurrentLocalDate();

			int aCurrentOpsSize = mRunningOperationsMap.values().size();

			SiteScheduleInfo anInfo = null;

			for (int anIndex = aCurrentOpsSize; anIndex <= mMaxRunningOps; anIndex++)
			{
				anInfo = mWaitingSiteQueue.peek();

				if ((anInfo != null) && (aCurrentDate.compareTo(anInfo.getNextExecutionDate()) >= 0))
				{
					createSiteControllerThread(anInfo);
				}
				else
				{
					break;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private SiteControllerThread createSiteControllerThread(SiteScheduleInfo anInfo)
	{
		SiteControllerThread aThread = new SiteControllerThread(anInfo.getSiteConfig().getSiteName(), TableNameUtil.createLinksVisitedTableName(anInfo.getSiteConfig().getSiteName()));
		aThread.setSiteState(anInfo.getState());
		aThread.addEventListener(this);
		aThread.start();
		mRunningOperationsMap.put(aThread.getSiteName(), aThread);
		mWaitingSiteQueue.remove(anInfo);
		return aThread;
	}

	private void cleanFinishedThreadsFromRunningQueue()
	{
		SiteControllerThread aThread = null;

		for (String aName : mRunningOperationsMap.keySet())
		{
			aThread = mRunningOperationsMap.get(aName);

			if (aThread.getState() == State.TERMINATED)
			{
				mRunningOperationsMap.remove(aName);
			}
		}

		if (mRunningOperationsMap.size() == 0)
		{
			try
			{
				this.notify();
			}
			catch (Exception e)
			{
			}
		}
	}

	@Override
	public void operationEnded(SiteControllerThread aControllerThread)
	{
		SiteScheduleInfo anInfo = mSiteScheduleInfoMap.get(aControllerThread.getSiteName());

		anInfo.setState(aControllerThread.getSiteState());

		mWaitingSiteQueue.add(anInfo);
		mRunningOperationsMap.remove(aControllerThread.getSiteName());

		Date aCurrentDate = DateUtil.getCurrentLocalDate();

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(aCurrentDate);

		aCalendar.add(Calendar.DAY_OF_MONTH, anInfo.getCrawlingPeriod());

		anInfo.setNextExecutionDate(aCalendar.getTime());

		cleanFinishedThreadsFromRunningQueue();

		if (aControllerThread.getSiteState() == SiteControllerState.ENDED)
		{
			if (aControllerThread.getEndTime() != null && aControllerThread.getStartTime() != null)
			{
				long actualOpTime = aControllerThread.getEndTime().getTime() - aControllerThread.getStartTime().getTime(); // doublee

				long actualOpTimeInHours = actualOpTime / (10000 * 60 * 60);

				anInfo.setAverageLinkCount(CrawlerAdmin.getInstance().getCrawlerStatistics(aControllerThread.getSiteName()).getTotalVisitedLinks());

				anInfo.setAverageFetchTime(CrawlerAdmin.getInstance().getCrawlerStatistics(aControllerThread.getSiteName()).getAvgFetchTime());

				anInfo.setExecutionTime(actualOpTimeInHours);
			}

		}

		createJobsWaitingInQueue();
	}

	public Collection<SiteControllerThread> getExecutingOperations()
	{
		return mRunningOperationsMap.values();
	}

	public Collection<SiteScheduleInfo> getQueuedOperations()
	{
		return mWaitingSiteQueue;
	}

	public void start(String aSiteName)
	{
		if (!mRunningOperationsMap.containsKey(aSiteName) && mSiteScheduleInfoMap.containsKey(aSiteName))
		{
			createSiteControllerThread(mSiteScheduleInfoMap.get(aSiteName));
		}
	}
}