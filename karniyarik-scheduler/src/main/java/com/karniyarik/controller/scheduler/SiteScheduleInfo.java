package com.karniyarik.controller.scheduler;

import java.util.Date;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.controller.SiteControllerState;

public class SiteScheduleInfo
{
	//the site configuration
	private SiteConfig mSiteConfig = null;
	
	//crawling period (in days)
	private int mCrawlingPeriod = 4;
	
	private long mExecutionTime = 0; //hours
	
	private int mAverageLinkCount = 0;
	
	private long mAverageFetchTime = 0; //ms
	
	private Date mNextExecutionDate = null;
	
	private SiteControllerState	mState = SiteControllerState.IDLE;
	
	public SiteScheduleInfo()
	{
		
	}

	public SiteConfig getSiteConfig()
	{
		return mSiteConfig;
	}

	public void setSiteConfig(SiteConfig aSiteConfig)
	{
		mSiteConfig = aSiteConfig;
	}

	/**
	 * The crawling period in days
	 * @return
	 */
	public int getCrawlingPeriod()
	{
		return mCrawlingPeriod;
	}

	public void setCrawlingPeriod(int aCrawlingPeriod)
	{
		mCrawlingPeriod = aCrawlingPeriod;
	}

	public Date getNextExecutionDate()
	{
		return mNextExecutionDate;
	}

	public void setNextExecutionDate(Date aNextExecutionDate)
	{
		mNextExecutionDate = aNextExecutionDate;
	}

	public long getExecutionTime()
	{
		return mExecutionTime;
	}

	public void setExecutionTime(long aExecutionTime)
	{
		mExecutionTime = aExecutionTime;
	}

	public int getAverageLinkCount()
	{
		return mAverageLinkCount;
	}

	public void setAverageLinkCount(int aAverageLinkCount)
	{
		mAverageLinkCount = aAverageLinkCount;
	}

	public long getAverageFetchTime()
	{
		return mAverageFetchTime;
	}

	public void setAverageFetchTime(long aAverageFetchTime)
	{
		mAverageFetchTime = aAverageFetchTime;
	}
	
	public SiteControllerState getState()
	{
		return mState;
	}

	public void setState(SiteControllerState aState)
	{
		mState = aState;
	}
	
	@Override
	public String toString()
	{
		StringBuffer aResult = new StringBuffer();
		
		if(getSiteConfig() != null)
		{
			aResult.append("Sitename: ");
			aResult.append(getSiteConfig().getSiteName());
			aResult.append(" - ");
		}
		aResult.append("NED: ");
		aResult.append(getNextExecutionDate());
		aResult.append(" - State: ");
		aResult.append(getState());
		aResult.append(" - Period:");
		aResult.append(getCrawlingPeriod());
		
		return aResult.toString();
	}
}
