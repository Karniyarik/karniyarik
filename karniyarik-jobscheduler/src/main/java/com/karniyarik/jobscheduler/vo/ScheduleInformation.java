package com.karniyarik.jobscheduler.vo;

import java.util.Date;

public class ScheduleInformation
{
	String sitename;
	boolean isDatafeed = false;
	Date beginDate;
	Date endDate;
	Date startDate;
	Date previousFireTime;
	Date nextStartDate;
	Date finalFireTime;
	Date endTime;
	long lastExecutionDuration;
	String lastExecutionDurationStr;
	long duration;
	String durationStr;
	
	public String getSitename()
	{
		return sitename;
	}
	public void setSitename(String sitename)
	{
		this.sitename = sitename;
	}
	public boolean isDatafeed()
	{
		return isDatafeed;
	}
	public void setDatafeed(boolean isDatafeed)
	{
		this.isDatafeed = isDatafeed;
	}
	public Date getBeginDate()
	{
		return beginDate;
	}
	public void setBeginDate(Date beginDate)
	{
		this.beginDate = beginDate;
	}
	public Date getEndDate()
	{
		return endDate;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public Date getPreviousFireTime()
	{
		return previousFireTime;
	}
	public void setPreviousFireTime(Date previousFireTime)
	{
		this.previousFireTime = previousFireTime;
	}
	public Date getNextStartDate()
	{
		return nextStartDate;
	}
	public void setNextStartDate(Date nextStartDate)
	{
		this.nextStartDate = nextStartDate;
	}
	public Date getFinalFireTime()
	{
		return finalFireTime;
	}
	public void setFinalFireTime(Date finalFireTime)
	{
		this.finalFireTime = finalFireTime;
	}
	public Date getEndTime()
	{
		return endTime;
	}
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	public long getDuration()
	{
		return duration;
	}
	public void setDuration(long duration)
	{
		this.duration = duration;
	}
	public String getDurationStr()
	{
		return durationStr;
	}
	public void setDurationStr(String durationStr)
	{
		this.durationStr = durationStr;
	}
	
	public long getLastExecutionDuration()
	{
		return lastExecutionDuration;
	}
	
	public void setLastExecutionDuration(long lastExecutionDuration)
	{
		this.lastExecutionDuration = lastExecutionDuration;
	}
	
	public String getLastExecutionDurationStr()
	{
		return lastExecutionDurationStr;
	}
	
	public void setLastExecutionDurationStr(String lastExecutionDurationStr)
	{
		this.lastExecutionDurationStr = lastExecutionDurationStr;
	}
}
