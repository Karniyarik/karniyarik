package com.karniyarik.common.statistics.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "productStat", propOrder = { "url", "name", "totalViews", "totalClicks", "listingViews", "listingClicks", "sponsorViews", "sponsorClicks" })
public class ProductStat
{

	@XmlElement(name = "url")
	private String	url;

	@XmlElement(name = "name")
	private String	name;

	@XmlElement(name = "totalViews")
	private Integer	totalViews;

	@XmlElement(name = "totalClicks")
	private Integer	totalClicks;

	@XmlElement(name = "listingViews")
	private Integer	listingViews;

	@XmlElement(name = "listingClicks")
	private Integer	listingClicks;

	@XmlElement(name = "sponsorViews")
	private Integer	sponsorViews;

	@XmlElement(name = "sponsorClicks")
	private Integer	sponsorClicks;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getTotalViews()
	{
		return totalViews;
	}

	public void setTotalViews(Integer totalViews)
	{
		this.totalViews = totalViews;
	}

	public Integer getTotalClicks()
	{
		return totalClicks;
	}

	public void setTotalClicks(Integer totalClicks)
	{
		this.totalClicks = totalClicks;
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

}
