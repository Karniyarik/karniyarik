package com.karniyarik.web.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunnyStatistics
{
	private int totalSiteCount = 0;
	private Map<String, Integer> domainKeywords = new HashMap<String, Integer>();
	private Map<String, Integer> extensionKeywords = new HashMap<String, Integer>();
	private List<SiteInfoBean> topProductCountSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> topClickedProductCountSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> topCarCountSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> topClickedCarCountSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> topClickedCityDealSites = new ArrayList<SiteInfoBean>();
	private Date analyticsLastDate = null;
	
	public FunnyStatistics()
	{
	}
	
	public Map<String, Integer> getDomainKeywords()
	{
		return domainKeywords;
	}
	
	public void setDomainKeywords(Map<String, Integer> domainKeywords)
	{
		this.domainKeywords = domainKeywords;
	}
	
	public Map<String, Integer> getExtensionKeywords()
	{
		return extensionKeywords;
	}
	
	public void setExtensionKeywords(Map<String, Integer> extensionKeywords)
	{
		this.extensionKeywords = extensionKeywords;
	}
	
	public int getTotalSiteCount()
	{
		return totalSiteCount;
	}
	
	public void setTotalSiteCount(int totalSiteCount)
	{
		this.totalSiteCount = totalSiteCount;
	}
	
	public List<SiteInfoBean> getTopProductCountSites() {
		return topProductCountSites;
	}
	
	public void setTopProductCountSites(List<SiteInfoBean> topProductCountSites) {
		this.topProductCountSites = topProductCountSites;
	}
	
	public List<SiteInfoBean> getTopClickedProductCountSites() {
		return topClickedProductCountSites;
	}
	
	public void setTopClickedProductCountSites(
			List<SiteInfoBean> topClickedProductCountSites) {
		this.topClickedProductCountSites = topClickedProductCountSites;
	}
	
	public List<SiteInfoBean> getTopCarCountSites() {
		return topCarCountSites;
	}
	public List<SiteInfoBean> getTopClickedCarCountSites() {
		return topClickedCarCountSites;
	}
	
	public void setTopCarCountSites(List<SiteInfoBean> topCarCountSites) {
		this.topCarCountSites = topCarCountSites;
	}
	
	public void setTopClickedCarCountSites(
			List<SiteInfoBean> topClickedCarCountSites) {
		this.topClickedCarCountSites = topClickedCarCountSites;
	}
	
	public List<SiteInfoBean> getTopClickedCityDealSites() {
		return topClickedCityDealSites;
	}
	
	public void setTopClickedCityDealSites(
			List<SiteInfoBean> topClickedCityDealSites) {
		this.topClickedCityDealSites = topClickedCityDealSites;
	}
	
	public Date getAnalyticsLastDate() {
		return analyticsLastDate;
	}
	
	public void setAnalyticsLastDate(Date analyticsLastDate) {
		this.analyticsLastDate = analyticsLastDate;
	}
}
