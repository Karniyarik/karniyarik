package com.karniyarik.statistics.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.statistics.vo.DailyStat;
import com.karniyarik.common.statistics.vo.ProductClickLog;
import com.karniyarik.common.statistics.vo.ProductSummary;
import com.karniyarik.common.statistics.vo.SiteSponsorStat;
import com.karniyarik.common.statistics.vo.SponsorStat;

public class DailyCache {
	
	private Map<String, SynchronizedDailyStat> dailyStatCache = new HashMap<String, SynchronizedDailyStat>();
	private Map<String, SiteSponsoredCacheElement> sponsoredCache = new HashMap<String, SiteSponsoredCacheElement>();
	
	private static DailyCache instance = new DailyCache();
	
	private DailyCache() {
	}
	
	public static DailyCache getInstance() {
		return instance;
	}
	
	public void productClicked(ProductClickLog log)
	{
		String name = null;
		
		if(log.apiClick())
		{
			name = log.getApiKey();
		}
		else
		{
			name = log.getSiteName(); 
		}
		
		if(StringUtils.isNotBlank(name))
		{
			SynchronizedDailyStat dailyStat = getDailyStatFromCache(name);
			
			if(log.isSponsor())
			{
				dailyStat.increaseSponsoredClicksByOne();
				
				SynchronizedSponsorStat sponsorStat = getSponsorStatFromCache(log.getSiteName(), log.getUrl());
				sponsorStat.increaseClicksByOne();
			}
			else
			{
				dailyStat.increaseListingClicksByOne();
			}
			
		}
	}

	public void productViewed(List<ProductSummary>	products)
	{
		for(ProductSummary productSummary: products)
		{
			String sitename = productSummary.getSiteName();
			if(StringUtils.isNotBlank(sitename))
			{
				SynchronizedDailyStat dailyStat = getDailyStatFromCache(sitename);
				if(productSummary.isSponsor())
				{
					dailyStat.increaseSponsorViewsByOne();
					
					SynchronizedSponsorStat sponsorStat = getSponsorStatFromCache(productSummary.getSiteName(), productSummary.getUrl());
					sponsorStat.increaseViewsByOne();
				}
				else
				{
					dailyStat.increaseListingViewsByOne();
				}
			}
		}
	}
	
	private SynchronizedDailyStat getDailyStatFromCache(String name) {
		synchronized (dailyStatCache) {
			SynchronizedDailyStat dailyStat = dailyStatCache.get(name);
			if(dailyStat == null)
			{
				dailyStat = new SynchronizedDailyStat(System.currentTimeMillis());
				dailyStatCache.put(name, dailyStat);
			}
			return dailyStat;			
		}
	}

	private SynchronizedSponsorStat getSponsorStatFromCache(String sitename, String url) {
		synchronized (sponsoredCache) {
			SiteSponsoredCacheElement site = sponsoredCache.get(sitename);
			if(site == null)
			{
				site = new SiteSponsoredCacheElement(sitename);
				sponsoredCache.put(sitename, site);
			}
			
			return site.getSponsorStat(url);
		}
	}
	
	public void resetSiteSponsorStat(String sitename, SiteSponsorStat siteSponsorStat)
	{
		SiteSponsoredCacheElement site = sponsoredCache.get(sitename);
		
		if(site != null)
		{
			for(SponsorStat sponsorStat: siteSponsorStat.getSponsorStatList())
			{
				SynchronizedSponsorStat dailySponsorStat = site.getSponsorStat(sponsorStat.getUrl());
				if(dailySponsorStat != null)
				{
					sponsorStat.setClicks(sponsorStat.getClicks() + dailySponsorStat.getStat().getClicks());
					sponsorStat.setViews(sponsorStat.getViews() + dailySponsorStat.getStat().getViews());
				}
			}
		}
	}
	
	public DailyStat getDailyStat(String sitename)
	{
		SynchronizedDailyStat synchronizedDailyStat = dailyStatCache.get(sitename);
		if(synchronizedDailyStat != null)
		{
			return synchronizedDailyStat.getStat();
		}
		
		return null;
	}

	public void reset() {
		synchronized (dailyStatCache) {
			dailyStatCache.clear();	
		}
		
		synchronized (sponsoredCache) {
			sponsoredCache.clear();	
		}
	}
}
