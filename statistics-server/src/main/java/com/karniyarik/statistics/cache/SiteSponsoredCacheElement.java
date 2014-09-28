package com.karniyarik.statistics.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.statistics.vo.SponsorStat;

public class SiteSponsoredCacheElement {
	private Map<String, SynchronizedSponsorStat> urls = new HashMap<String, SynchronizedSponsorStat>();
	
	private String sitename; 
	public SiteSponsoredCacheElement(String sitename) {
		this.sitename = sitename;
	}
	
	public SynchronizedSponsorStat getSponsorStat(String url)
	{
		synchronized (urls) {
			SynchronizedSponsorStat sponsorStat = urls.get(url);
			if(sponsorStat == null)
			{
				sponsorStat = new SynchronizedSponsorStat(url);
				urls.put(url, sponsorStat);
			}
			
			return sponsorStat;
		}
	}
	
	public String getSitename() {
		return sitename;
	}
	
	public List<SponsorStat> getSponsorStat() {
		List<SponsorStat> list = new ArrayList<SponsorStat>();
		for(SynchronizedSponsorStat stat: urls.values())
		{
			SponsorStat orgStat = stat.getStat();
			list.add(orgStat);
		}
		
		return list;
	}
}
