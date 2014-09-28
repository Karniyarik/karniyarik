package com.karniyarik.pagerank.normalizer;

import org.apache.log4j.Logger;

import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.TableNameUtil;
import com.karniyarik.pagerank.dao.PageRankDAO;

public class SiteRankNormalizer implements INormalizer
{
	private JobExecutionStat	mCrawlerStatistics	= null;
	private double				mPageToLinkRatio	= 0;
	private int					mMaxRank			= 0;

	public SiteRankNormalizer(String siteName, JobExecutionStat stat)
	{
		this.mCrawlerStatistics = stat;
	}

	@Override
	public void initialize()
	{

		try
		{
			mMaxRank = new PageRankDAO(mCrawlerStatistics.getSiteName(), TableNameUtil.createLinksVisitedTableName(mCrawlerStatistics.getSiteName())).findMaxRank();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not initialize site rank normalizer for " + mCrawlerStatistics.getSiteName(), e);
		}

		mPageToLinkRatio = mCrawlerStatistics.getTotalVisitedLinks() * 1.0 / mCrawlerStatistics.getTotalLinkCount();
	}

	@Override
	public int calculateNewRank(int aPH)
	{
		return (int) mPageToLinkRatio * aPH / mMaxRank;
	}

	public Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
}