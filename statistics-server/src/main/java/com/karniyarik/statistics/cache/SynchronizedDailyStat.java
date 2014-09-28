package com.karniyarik.statistics.cache;

import com.karniyarik.common.statistics.vo.DailyStat;


public class SynchronizedDailyStat {
	private DailyStat stat = null;
	
	public SynchronizedDailyStat(long date) {
		stat = new DailyStat();
		stat.setDate(date);
		stat.setListingClicks(0);
		stat.setListingViews(0);
		stat.setSponsorClicks(0);
		stat.setSponsorViews(0);
		stat.setTotalClicks(0);
		stat.setTotalViews(0);
	}
	
	public long getDate()
	{
		return stat.getDate();
	}

	public DailyStat getStat() {
		stat.setTotalClicks(stat.getSponsorClicks()+stat.getListingClicks());
		stat.setTotalViews(stat.getSponsorViews()+stat.getListingViews());
		return stat;
	}
	
	public void increaseListingClicksByOne()
	{
		synchronized(stat)
		{
			stat.setListingClicks(stat.getListingClicks()+1);	
		}
	}
	
	public void increaseListingViewsByOne()
	{
		synchronized(stat)
		{
			stat.setListingViews(stat.getListingViews()+1);
		}
	}

	public void increaseSponsoredClicksByOne()
	{
		synchronized(stat)
		{
			stat.setSponsorClicks(stat.getSponsorClicks()+1);
		}
	}

	public void increaseSponsorViewsByOne()
	{
		synchronized(stat)
		{
			stat.setSponsorViews(stat.getSponsorViews()+1);
		}
	}

}

