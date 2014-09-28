package com.karniyarik.controller.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.db.DatabaseTableCreator;
import com.karniyarik.controller.SiteControllerThread;

public class SchedulerAdmin
{
	private static SchedulerAdmin	mInstance	= null;

	private SiteScheduler			mScheduler	= null;
	private final Logger			logger;

	private SchedulerAdmin()
	{
		logger = Logger.getLogger(this.getClass().getName());

		try
		{
			new DatabaseTableCreator(KarniyarikRepository.getInstance()
					.getConfig().getConfigurationBundle().getDbConfig())
					.createScheduleInfoTable();
		}
		catch (Throwable e)
		{
			logger.error("Can not create scheduler tables", e);
			throw new RuntimeException("Can not create scheduler tables", e);
		}

		mScheduler = new SiteScheduler();
	}

	public static SchedulerAdmin getInstance()
	{
		if (mInstance == null)
		{
			synchronized (SchedulerAdmin.class)
			{
				if (mInstance == null)
				{
					mInstance = new SchedulerAdmin();
				}
			}
		}

		return mInstance;
	}

	public void begin()
	{
		mScheduler.begin();
	}

	public void stop()
	{
		mScheduler.pause();
	}

	public void stopAfterCurrent()
	{
		mScheduler.pauseAfterCurrent();
	}

	public void stop(String aSiteName)
	{
		mScheduler.pause(aSiteName);
	}

	public void start(String aSiteName)
	{
		mScheduler.start(aSiteName);
	}

	public List<SiteControllerThread> getExecutingOperations()
	{
		List<SiteControllerThread> executingOperationsList = new ArrayList<SiteControllerThread>();
		executingOperationsList.addAll(mScheduler.getExecutingOperations());
		return executingOperationsList;
	}

	public List<SiteScheduleInfo> getQueuedOperations()
	{
		List<SiteScheduleInfo> scheduledOperationsList = new ArrayList<SiteScheduleInfo>();
		scheduledOperationsList.addAll(mScheduler.getQueuedOperations());
		return scheduledOperationsList;
	}
	
	public static void main(String[] args)
	{
		SchedulerAdmin.getInstance().start("dermaderm");
	}
}
