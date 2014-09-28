package com.karniyarik.common.statistics.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "siteSponsorStat", propOrder = { "siteName", "sponsorStatList" })
public class SiteSponsorStat
{
	
	@XmlElement(name = "siteName")
	private String siteName;
	
	@XmlElement(name = "sponsorStat")
	@XmlElementWrapper(name = "sponsorStatList")
	private List<SponsorStat> sponsorStatList;

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public List<SponsorStat> getSponsorStatList()
	{
		return sponsorStatList;
	}

	public void setSponsorStatList(List<SponsorStat> sponsorStatList)
	{
		this.sponsorStatList = sponsorStatList;
	}
	
}
