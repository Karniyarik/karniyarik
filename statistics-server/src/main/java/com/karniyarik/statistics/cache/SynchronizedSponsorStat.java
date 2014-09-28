package com.karniyarik.statistics.cache;

import com.karniyarik.common.statistics.vo.SponsorStat;


public class SynchronizedSponsorStat {
	private SponsorStat stat = null;
	
	public SynchronizedSponsorStat(String url) {
		stat = new SponsorStat();
		stat.setUrl(url);
		stat.setClicks(0);
		stat.setViews(0);
	}
	
	public SponsorStat getStat() {
		return stat;
	}
	
	public void increaseClicksByOne()
	{
		synchronized(stat)
		{
			stat.setClicks(stat.getClicks()+1);
		}
	}
	
	public void increaseViewsByOne()
	{
		synchronized(stat)
		{
			stat.setViews(stat.getViews()+1);
		}
	}
}
