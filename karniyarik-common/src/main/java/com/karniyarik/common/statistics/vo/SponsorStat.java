package com.karniyarik.common.statistics.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "sponsorStat", propOrder = { "url", "views", "clicks" })
public class SponsorStat
{

	@XmlElement(name = "url")
	private String url;
	
	@XmlElement(name = "views")
	private Integer views;
	
	@XmlElement(name = "clicks")
	private Integer clicks;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Integer getViews()
	{
		return views;
	}

	public void setViews(Integer views)
	{
		this.views = views;
	}

	public Integer getClicks()
	{
		return clicks;
	}

	public void setClicks(Integer clicks)
	{
		this.clicks = clicks;
	}
	
}
