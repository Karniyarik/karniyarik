package com.karniyarik.pagerank.normalizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.pagerank.histogram.HistogramEqualizator;
import com.karniyarik.pagerank.histogram.HistogramValueHolder;

public class HistogramNormalizer implements INormalizer
{
	private Map<Integer, Integer>				mLookupTable	= null;
	private final List<HistogramValueHolder>	distinctRanks;
	private final int							siteRowCount;

	public HistogramNormalizer(int siteRowCount,
			List<HistogramValueHolder> distinctRanks)
	{
		this.siteRowCount = siteRowCount;
		this.distinctRanks = distinctRanks;
	}

	@Override
	public void initialize()
	{
		HistogramEqualizator anEqualizator = new HistogramEqualizator(
				distinctRanks, siteRowCount, 100);

		List<HistogramValueHolder> aList = anEqualizator.getCDF();

		mLookupTable = new HashMap<Integer, Integer>();

		for (HistogramValueHolder tmpHolder : aList)
		{
			mLookupTable.put(tmpHolder.getValue(), tmpHolder.getCount());
		}
	}

	@Override
	public int calculateNewRank(int aPH)
	{
		return mLookupTable.get(aPH);
	}
}