package com.karniyarik.web.json;

import com.karniyarik.web.util.RequestWrapper;

public class PagerCalculator
{
	public static int		MAX_PAGER			= 5;

	private int		mPageNumber					= 1;
	private int		mPageCount					= 0;
	private int		mPageStart					= 1;
	private int		mPageEnd					= 1;
	private boolean	mFirstButtonRequired		= false;
	private boolean	mLastButtonRequired			= false;
	private boolean	mNextButtonOnLeftRequired	= false;
	private boolean	mNextButtonOnRightRequired	= false;
	private int		mFastPreviousButtonPage		= 1;
	private int		mFastNextButtonPage			= 1;
	private int		mPreviousButtonPage			= 1;
	private int		mNextButtonPage				= 1;
	private int		mFirstButtonPage			= 1;
	private int		mLastButtonPage				= 1;

	public PagerCalculator(RequestWrapper aWrapper)
	{
		if (aWrapper.getQueryResult().getQuery() != null)
		{
			long aTotalHits = aWrapper.getQueryResult().getTotalHits();
			int aPageSize = aWrapper.getQueryResult().getQuery().getPageSize();

			mPageNumber = aWrapper.getPageNumber();
			mPageCount = (int) Math.ceil(aTotalHits * 1.0 / aPageSize);
			mLastButtonPage = mPageCount;
			calculatePageStartAndEnd();

			calculateNextAndPreviousButtonPages();
		}
	}

	private void calculateNextAndPreviousButtonPages()
	{

		int aHalfOfTheMaxPage = MAX_PAGER / 2;

		if (mPageStart > 1)
		{
			mNextButtonOnLeftRequired = true;
			mFirstButtonRequired = true;
			mFastPreviousButtonPage = mPageStart - aHalfOfTheMaxPage;
			mFastPreviousButtonPage = mFastPreviousButtonPage < 1 ? 1 : mFastPreviousButtonPage;
			
		}

		if ((mPageCount - mPageEnd) > 0)
		{
			mNextButtonOnRightRequired = true;
			mFastNextButtonPage = mPageEnd + aHalfOfTheMaxPage;
			mFastNextButtonPage = mFastNextButtonPage > mPageCount - 1 ? mPageCount - 1 : mFastNextButtonPage;
		}
		
		if(mPageEnd < mLastButtonPage)
		{
			mLastButtonRequired = true;
		}
		
		mPreviousButtonPage = mPageNumber - 1;
		
		mPreviousButtonPage = mPreviousButtonPage < 0 ? 0 : mPreviousButtonPage;
		
		mNextButtonPage = mPageNumber+ 1;

		//mNextButtonPage = mNextButtonPage > mPageCount - 1 ? mPageCount - 1 : mNextButtonPage;
	}

	private void calculatePageStartAndEnd()
	{
		int aHalfPageCount = MAX_PAGER / 2;

		mPageStart = mPageNumber - aHalfPageCount;
		mPageEnd = mPageNumber + aHalfPageCount;

		int aDeltaStart = mPageStart;
		int aDeltaEnd = mPageCount - mPageEnd;

		if (aDeltaStart <= 0 && aDeltaEnd < 0)
		{
			mPageStart = 1;
			mPageEnd = mPageCount;
		}
		
		else if (aDeltaStart <= 0 && aDeltaEnd >= 0)
		{
			mPageStart = 1;
			mPageEnd = mPageEnd + (-1 * aDeltaStart);
			mPageEnd = mPageCount < mPageEnd ? mPageCount : mPageEnd;
		}
		else if (aDeltaStart > 0 && aDeltaEnd < 0)
		{
			mPageEnd = mPageCount;
			mPageStart = mPageStart + aDeltaEnd;
			mPageStart = mPageStart < 0 ? 1 : mPageStart;
		}
		else
		// if(aDeltaStart > 0 && aDeltaEnd > 0)
		{
			// do nothing
		}
	}

	public int getPageCount()
	{
		return mPageCount;
	}

	public int getPageStart()
	{
		return mPageStart;
	}

	public int getPageEnd()
	{
		return mPageEnd;
	}

	public int getPageNumber()
	{
		return mPageNumber;
	}

	public boolean isNextButtonOnLeftRequired()
	{
		return mNextButtonOnLeftRequired;
	}

	public boolean isNextButtonOnRightRequired()
	{
		return mNextButtonOnRightRequired;
	}

	public int getPreviousButtonPage()
	{
		return mPreviousButtonPage;
	}

	public int getNextButtonPage()
	{
		return mNextButtonPage;
	}
	
	public int getFastPreviousButtonPage()
	{
		return mFastPreviousButtonPage;
	}

	public int getFastNextButtonPage()
	{
		return mFastNextButtonPage;
	}
	
	public int getFirstButtonPage()
	{
		return mFirstButtonPage;
	}

	public int getLastButtonPage()
	{
		return mLastButtonPage;
	}
	
	public boolean isFirstButtonRequired() {
		return mFirstButtonRequired;
	}
	
	public boolean isLastButtonRequired() {
		return mLastButtonRequired;
	}

}
