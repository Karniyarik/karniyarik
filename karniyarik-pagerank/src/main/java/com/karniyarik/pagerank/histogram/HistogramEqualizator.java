package com.karniyarik.pagerank.histogram;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class HistogramEqualizator
{
	private int							mMinCDFValue	= -1;
	private int							mMaxRank		= -1;
	private int							mSiteRowCount	= 0;
	private List<HistogramValueHolder>	mHistogramList	= null;
	private List<HistogramValueHolder>	mCDF			= null;

	public HistogramEqualizator(List<HistogramValueHolder> aHistogramList,
			int aRowCount, int aMaxRank)
	{
		mSiteRowCount = aRowCount;
		mHistogramList = aHistogramList;
		mMaxRank = aMaxRank;
		constructCDF();

		normalizeHistogram();
	}

	private void constructCDF()
	{
		mCDF = new ArrayList<HistogramValueHolder>();

		int previousValue = 0;

		for (HistogramValueHolder tmpValueHolder : mHistogramList)
		{
			previousValue = previousValue + tmpValueHolder.getCount();
			tmpValueHolder.setCount(previousValue);
			mCDF.add(tmpValueHolder);
		}

		mMinCDFValue = mCDF.get(0).getCount();
	}

	private int cdfCalculator(int originalCdfValue)
	{
		Double tmpDouble = new Double(50);
		try
		{
			BigDecimal bd = new BigDecimal(new Double(
					(originalCdfValue - mMinCDFValue))
					/ (mSiteRowCount - mMinCDFValue) * (mMaxRank - 1));
			bd.setScale(1, BigDecimal.ROUND_HALF_EVEN);
			tmpDouble = bd.doubleValue();
		}
		catch (Throwable e)
		{
			getLogger().error(
					"Could not calculate cdf, originalCdf: " + originalCdfValue
							+ " minCdfValue: " + mMinCDFValue
							+ " siteRowCount: " + mSiteRowCount + " maxRank: "
							+ mMaxRank, e);
		}

		return tmpDouble.intValue();
	}

	private void normalizeHistogram()
	{
		for (HistogramValueHolder tmpHolder : mCDF)
		{
			tmpHolder.setCount(cdfCalculator(tmpHolder.getCount()));
		}
	}

	public List<HistogramValueHolder> getCDF()
	{
		return mCDF;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
}
