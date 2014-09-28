package com.karniyarik.statistics.vo;

public class DailySiteClickStatistics
{

	private final String	siteName;
	private final long		date;
	private Integer			listingViews;
	private Integer			listingClicks;
	private Integer			sponsorViews;
	private Integer			sponsorClicks;

	public DailySiteClickStatistics(String siteName, long date)
	{
		this.siteName = siteName;
		this.date = date;
		this.listingClicks = 0;
		this.listingViews = 0;
		this.sponsorClicks = 0;
		this.sponsorViews = 0;
	}

	public void addDailyProductStatistics(DailyProductStatistics productStat)
	{
		listingClicks += productStat.getListingClicks();
		listingViews += productStat.getListingViews();
		sponsorClicks += productStat.getSponsorClicks();
		sponsorViews += productStat.getSponsorViews();
	}

	public Integer getListingViews()
	{
		return listingViews;
	}

	public void setListingViews(Integer listingViews)
	{
		this.listingViews = listingViews;
	}

	public Integer getListingClicks()
	{
		return listingClicks;
	}

	public void setListingClicks(Integer listingClicks)
	{
		this.listingClicks = listingClicks;
	}

	public Integer getSponsorViews()
	{
		return sponsorViews;
	}

	public void setSponsorViews(Integer sponsorViews)
	{
		this.sponsorViews = sponsorViews;
	}

	public Integer getSponsorClicks()
	{
		return sponsorClicks;
	}

	public void setSponsorClicks(Integer sponsorClicks)
	{
		this.sponsorClicks = sponsorClicks;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public long getDate()
	{
		return date;
	}

}
