package com.karniyarik.common.util;

import java.util.LinkedList;

public class FetchTimeCalculator
{
	private final static int	WINDOW_SIZE				= 100;

	private LinkedList<Long>	mFetchTimes				= null;
	private int					mCurrentSize			= 0;
	private long				mWindowedTotalFetchTime	= 0;

	public FetchTimeCalculator()
	{
		mFetchTimes = new LinkedList<Long>();
	}

	public void fetchTimeGathered(long aFetchTime)
	{
		mFetchTimes.addFirst(aFetchTime);

		mWindowedTotalFetchTime = mWindowedTotalFetchTime + aFetchTime;

		if (mCurrentSize < WINDOW_SIZE)
		{
			mCurrentSize++;
		}
		else
		{
			mWindowedTotalFetchTime = mWindowedTotalFetchTime - mFetchTimes.removeLast();
		}
	}

	public long getWindowedAvgFetchTime()
	{
		if (mCurrentSize == 0)
		{
			return 0;
		}

		return mWindowedTotalFetchTime / mCurrentSize;
	}
	
	public void clear() {
		mCurrentSize = 0;
		mWindowedTotalFetchTime = 0;
		mFetchTimes.clear();
	}
}
