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
@XmlType(name = "topSearches", propOrder = { "category", "topSearchList" })
public class TopSearches
{
	@XmlElement(name="category")
	private int category;
	
	@XmlElement(name = "topSearch")
	@XmlElementWrapper(name = "topSearchList")
	private List<TopSearch>	topSearchList;

	public int getCategory()
	{
		return category;
	}

	public void setCategory(int category)
	{
		this.category = category;
	}

	public List<TopSearch> getTopSearchList()
	{
		return topSearchList;
	}

	public void setTopSearchList(List<TopSearch> topSearchList)
	{
		this.topSearchList = topSearchList;
	}

}
