package com.karniyarik.pagerank.alexa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.pagerank.AlexaRankCalculator;
import com.karniyarik.pagerank.histogram.HistogramEqualizator;
import com.karniyarik.pagerank.histogram.HistogramValueHolder;

public class AlexaRankFetcher
{
	private Map<String, Integer>	mRankMap		= null;
	private Map<Integer, String>	mInverseRankMap	= null;
	private int						mMaxRank		= 0;

	public AlexaRankFetcher()
	{
		mRankMap = new HashMap<String, Integer>();
		mInverseRankMap = new HashMap<Integer, String>();
		mMaxRank = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getMaxRank();
	}

	public void calculate()
	{
		Collection<SiteConfig> aSiteConfigList = KarniyarikRepository
				.getInstance().getConfig().getConfigurationBundle()
				.getSitesConfig().getSiteConfigList();

		fetchSiteRanks(aSiteConfigList);

		normalizeRanks(aSiteConfigList);

		// for(String aSiteName: mRankMap.keySet())
		// {
		// System.out.println(aSiteName + " - " +
		// mRankMap.get(aSiteName)*1.0/MAX_RANK);
		// }
	}

	private void normalizeRanks(Collection<SiteConfig> aSiteConfigList)
	{
		List<HistogramValueHolder> aHistogramList = new ArrayList<HistogramValueHolder>();

		for (Integer aTmpRank : mRankMap.values())
		{
			aHistogramList.add(new HistogramValueHolder(aTmpRank, 1));
		}

		Collections.sort(aHistogramList);

		HistogramEqualizator anEqualizator = new HistogramEqualizator(
				aHistogramList, aSiteConfigList.size(),
				mMaxRank);

		aHistogramList = anEqualizator.getCDF();

		for (HistogramValueHolder aTmpHolder : aHistogramList)
		{
			mRankMap.put(mInverseRankMap.get(aTmpHolder.getValue()),
					mMaxRank - aTmpHolder.getCount());
		}
	}

	private void fetchSiteRanks(Collection<SiteConfig> aSiteConfigList)
	{
		int aRank = 0;

		for (SiteConfig aSiteConfig : aSiteConfigList)
		{
			try
			{
				aRank = AlexaRankCalculator.fetch(aSiteConfig.getUrl());
			}
			catch (Throwable aT)
			{
				aRank = 1000000;
			}
			mRankMap.put(aSiteConfig.getSiteName(), aRank);
			mInverseRankMap.put(aRank, aSiteConfig.getSiteName());
		}
	}

	public int getRank(String aSiteName)
	{
		return mRankMap.get(aSiteName);
	}

	public static void main(String[] args)
	{
		new AlexaRankFetcher().calculate();
	}
}
