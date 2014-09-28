package com.karniyarik.common.statistics.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "siteStat", propOrder = { "siteName", "productCount", "date", "datafeed" })
public class SiteStat
{
	@XmlElement(name = "siteName")
	private String	siteName;

	@XmlElement(name = "productCount")
	private int		productCount;

	@XmlElement(name = "date")
	private long	date;

	@XmlElement(name = "datafeed")
	private boolean	datafeed;
	
	public SiteStat() {
		this.siteName = "";
		this.productCount = -1;
		this.date = -1;
		this.datafeed = false;
	}
	
	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public int getProductCount()
	{
		return productCount;
	}

	public void setProductCount(int productCount)
	{
		this.productCount = productCount;
	}

	public long getDate()
	{
		return date;
	}

	public void setDate(long date)
	{
		this.date = date;
	}

	public boolean isDatafeed()
	{
		return datafeed;
	}

	public void setDatafeed(boolean datafeed)
	{
		this.datafeed = datafeed;
	}

}
