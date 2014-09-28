package com.karniyarik.common.util;

import java.util.Calendar;
import java.util.Date;


public class StopWatch extends org.apache.commons.lang.time.StopWatch
{
	private Date mStartTime = null;
	private Date mEndTime = null;
	
	public StopWatch()
	{
		super();
	}
	
	@Override
	public void start()
	{
		mStartTime = Calendar.getInstance().getTime();
		super.start();
	}
	
	@Override
	public void stop()
	{
		mEndTime = Calendar.getInstance().getTime();
		super.stop();
	}
		
	public long getTimeInMilis()
	{
		return getTime();
	}
	
	public long getTimeInSeconds()
	{
		return getTime() / 1000;
	}
	
	public long getTimeInMinutes()
	{
		return getTime() / (1000 * 60);
	}
	
	public long getTimeInHours()
	{
		return getTime() / (1000 * 60 * 60);
	}

	public long getStartTime()
	{
		return mStartTime.getTime();
	}

	public Date getEndTime()
	{
		return mEndTime;
	}
}
