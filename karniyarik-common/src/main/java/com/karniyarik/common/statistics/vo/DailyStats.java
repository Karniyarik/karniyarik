package com.karniyarik.common.statistics.vo;

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
@XmlType(name = "dailyStats", propOrder = { "dailyStatList" })
public class DailyStats
{

	@XmlElement(name = "dailyStat")
	@XmlElementWrapper(name = "dailyStatList")
	private List<DailyStat> dailyStatList= new ArrayList<DailyStat>();

	public List<DailyStat> getDailyStatList()
	{
		return dailyStatList;
	}

	public void setDailyStatList(List<DailyStat> dailyStatList)
	{
		this.dailyStatList = dailyStatList;
	}

}
