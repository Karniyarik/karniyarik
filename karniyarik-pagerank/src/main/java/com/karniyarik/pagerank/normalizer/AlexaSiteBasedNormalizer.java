package com.karniyarik.pagerank.normalizer;

import com.karniyarik.pagerank.alexa.AlexaRankFetcher;

public class AlexaSiteBasedNormalizer implements INormalizer
{
	private AlexaRankFetcher	mFetcher	= null;
	private int					mSiteRank	= 0;
	private final String		siteName;

	public AlexaSiteBasedNormalizer(String siteName)
	{
		this.siteName = siteName;
		mFetcher = new AlexaRankFetcher();
		mFetcher.calculate();
	}

	@Override
	public void initialize()
	{
		mSiteRank = mFetcher.getRank(siteName);
	}

	@Override
	public int calculateNewRank(int aPH)
	{
		return aPH + mSiteRank / 4;
	}
}
