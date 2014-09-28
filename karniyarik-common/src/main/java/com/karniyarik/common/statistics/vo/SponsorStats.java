package com.karniyarik.common.statistics.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "sponsorStats", propOrder = { "siteSponsorStatList" })
public class SponsorStats
{
	@XmlElement(name = "siteSponsorStat")
	@XmlElementWrapper(name = "siteSponsorStatList")
	private List<SiteSponsorStat> siteSponsorStatList;

	public List<SiteSponsorStat> getSiteSponsorStatList()
	{
		return siteSponsorStatList;
	}

	public void setSiteSponsorStatList(List<SiteSponsorStat> siteSponsorStatList)
	{
		this.siteSponsorStatList = siteSponsorStatList;
	}
	
}
