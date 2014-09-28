package com.karniyarik.jobscheduler.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.common.statistics.vo.JobExecutionStat;

@XmlRootElement(name = "statlist", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statlist")
public class JobExecutionStatList
{
	@XmlElement(name = "stat")
	@XmlElementWrapper(name = "stats")
	private List<JobExecutionStat> stats = new ArrayList<JobExecutionStat>();
	
	public JobExecutionStatList()
	{
		// TODO Auto-generated constructor stub
	}
	
	public List<JobExecutionStat> getStats()
	{
		return stats;
	}
	
	public void setStats(List<JobExecutionStat> stats)
	{
		this.stats = stats;
	}
}
