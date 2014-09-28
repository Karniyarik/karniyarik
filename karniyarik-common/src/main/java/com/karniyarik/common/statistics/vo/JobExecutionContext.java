package com.karniyarik.common.statistics.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.common.config.site.SiteConfig;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "jobExecutionContext", propOrder = { "jobExecutionStat", "siteConfig" })
public class JobExecutionContext implements Serializable
{
	private static final long serialVersionUID = 6099799357973247789L;

	@XmlElement(name = "jobExecutionStat")
	private JobExecutionStat	jobExecutionStat;
	
	@XmlElement(name = "siteConfig")
	private SiteConfig			siteConfig;
	
	public JobExecutionStat getJobExecutionStat()
	{
		return jobExecutionStat;
	}

	public void setJobExecutionStat(JobExecutionStat jobExecutionStat)
	{
		this.jobExecutionStat = jobExecutionStat;
	}

	public SiteConfig getSiteConfig()
	{
		return siteConfig;
	}

	public void setSiteConfig(SiteConfig siteConfig)
	{
		this.siteConfig = siteConfig;
	}
	

}
