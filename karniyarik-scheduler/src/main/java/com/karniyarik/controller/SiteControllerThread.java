package com.karniyarik.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.karniyarik.common.util.DateUtil;
import com.karniyarik.controller.exception.KarniyarikSiteControllerException;
import com.karniyarik.crawler.admin.CrawlerAdmin;
import com.karniyarik.crawler.state.CrawlerState;
import com.karniyarik.pagerank.PageRankCalculator;
import com.karniyarik.search.indexer.admin.IndexAdmin;

public class SiteControllerThread extends Thread
{
	private SiteControllerState					mSiteState		= null;
	private KarniyarikSiteControllerException	mFailReason		= null;
	private List<ISiteControllerEventListener>	mEventListener	= null;
	private Date								mStartTime		= null;
	private Date								mEndTime		= null;
	private boolean								mIsPause		= false;

	private final String						siteName;
	private final String						visitedLinksTableName;

	public SiteControllerThread(String siteName, String visitedLinksTableName)
	{
		super(siteName + "SiteController");
		this.siteName = siteName;
		this.visitedLinksTableName = visitedLinksTableName;
		mEventListener = new ArrayList<ISiteControllerEventListener>();
	}

	@Override
	public void run()
	{
		try
		{
			startScheduler();

			while (!mIsPause && mSiteState != SiteControllerState.ENDED && mSiteState != SiteControllerState.FAILED)
			{
				if (mSiteState == SiteControllerState.CRAWLING)
				{
					crawl();
				}
				else if (mSiteState == SiteControllerState.RANKING)
				{
					rank();
				}
				else if (mSiteState == SiteControllerState.INDEXING)
				{
					index();
				}
				else if (mSiteState == SiteControllerState.CLEANING)
				{
					clean();
				}
			}
		}
		catch (Throwable e)
		{
			getLogger().error("One of site operations failed for " + siteName + ", current state is " + mSiteState, e);
			mSiteState = SiteControllerState.FAILED;
			mFailReason = new KarniyarikSiteControllerException("One of site operations failed for " + siteName + ", current state is " + mSiteState, e);
		}

		endScheduler();
	}

	public void pause()
	{
		mIsPause = true;

		if (mSiteState == SiteControllerState.CRAWLING)
		{
			CrawlerAdmin.getInstance().pauseCrawler(getSiteName());
		}
	}

	private void startScheduler()
	{
		if (mSiteState == null || mSiteState == SiteControllerState.IDLE || mSiteState == SiteControllerState.ENDED || mSiteState == SiteControllerState.FAILED)
		{
			mSiteState = SiteControllerState.CRAWLING;
			mStartTime = DateUtil.getCurrentLocalDate();
		}
	}

	private void endScheduler()
	{
		if (!mIsPause)
		{
			mSiteState = SiteControllerState.ENDED;

			mEndTime = DateUtil.getCurrentLocalDate();
		}

		fireSiteControllerEndedEvent();
	}

	private void clean()
	{
		// clean database from the old links, products
		jumpToNewState(false);
	}

	@SuppressWarnings("unchecked")
	private void index() throws InterruptedException, ExecutionException
	{
		Future anIndexerFuture = IndexAdmin.getInstance().indexProducts(siteName);

		if (anIndexerFuture != null)
		{
			anIndexerFuture.get();
		}

		jumpToNewState(false);
	}

	private void rank()
	{
		new PageRankCalculator(siteName, visitedLinksTableName).calculate();
		jumpToNewState(false);
	}

	private void crawl() throws InterruptedException
	{
		Future<Integer> future = CrawlerAdmin.getInstance().startCrawler(siteName);

		Integer crawlerState = -1;
		if (future != null)
		{
			try
			{
				crawlerState = future.get();
			}
			catch (ExecutionException e)
			{
				getLogger().error("Crawler task exception occured for " + getSiteName(), e);
			}
		}

		if (crawlerState.equals(CrawlerState.ENDED_STATE_ID))
		{
			jumpToNewState(false);
		}
		else
		{
			// otherwise the crawler has been stopped, so i will die without
			// doing anything.
			jumpToNewState(true);
		}

	}

	private void jumpToNewState(boolean isJumpToEnd)
	{
		if (!isJumpToEnd)
		{
			mSiteState = SiteControllerState.values()[(mSiteState.ordinal() + 1)];
		}
		else
		{
			mIsPause = true;
		}

	}

	public SiteControllerState getSiteState()
	{
		return mSiteState;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public KarniyarikSiteControllerException getFailReason()
	{
		return mFailReason;
	}

	public void fireSiteControllerEndedEvent()
	{
		for (ISiteControllerEventListener aListener : mEventListener)
		{
			aListener.operationEnded(this);
		}
	}

	public void addEventListener(ISiteControllerEventListener aListener)
	{
		mEventListener.add(aListener);
	}

	public Date getStartTime()
	{
		return mStartTime;
	}

	public void setStartTime(Date aStartTime)
	{
		mStartTime = aStartTime;
	}

	public Date getEndTime()
	{
		return mEndTime;
	}

	public void setEndTime(Date aEndTime)
	{
		mEndTime = aEndTime;
	}

	public void setSiteState(SiteControllerState aSiteState)
	{
		mSiteState = aSiteState;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
	
}
