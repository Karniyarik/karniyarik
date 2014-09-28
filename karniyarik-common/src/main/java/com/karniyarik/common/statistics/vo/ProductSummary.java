package com.karniyarik.common.statistics.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "productSummary", propOrder = { "url", "siteName", "name", "sponsor"})
public class ProductSummary
{

	@XmlElement(name = "url")
	private String url;
	
	@XmlElement(name = "siteName")
	private String siteName;
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "sponsor")
	private boolean sponsor;
	
	/**
	 * For JSON APIs
	 */
	public ProductSummary()
	{
	}
	
	public ProductSummary(String url, String siteName, String name, boolean sponsor) {
		this.url = url;
		this.siteName = siteName;
		this.name = name;
		this.sponsor = sponsor;
	}
	
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isSponsor()
	{
		return sponsor;
	}

	public void setSponsor(boolean sponsor)
	{
		this.sponsor = sponsor;
	}
	
}
