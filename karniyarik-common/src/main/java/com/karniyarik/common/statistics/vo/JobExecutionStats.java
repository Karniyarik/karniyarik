package com.karniyarik.common.statistics.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "jobExecutionStats", propOrder = { "jobExecutionStatList" })
public class JobExecutionStats implements Serializable
{
	private static final long serialVersionUID = 5394394414195881557L;
	
	@XmlElement(name = "jobExecutionStat")
	@XmlElementWrapper(name = "jobExecutionStatList")
	private List<JobExecutionStat>	jobExecutionStatList	= new ArrayList<JobExecutionStat>();

	public List<JobExecutionStat> getJobExecutionStatList()
	{
		return jobExecutionStatList;
	}

	public void setJobExecutionStatList(List<JobExecutionStat> jobExecutionStatList)
	{
		this.jobExecutionStatList = jobExecutionStatList;
	}

}
