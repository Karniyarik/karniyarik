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
@XmlType(name = "lastSearches", propOrder = { "category", "lastSearchList" })
public class LastSearches
{

	@XmlElement(name="category")
	private int category;
	
	@XmlElement(name = "lastSearch")
	@XmlElementWrapper(name = "lastSearchList")
	private List<String> lastSearchList;

	public int getCategory()
	{
		return category;
	}

	public void setCategory(int category)
	{
		this.category = category;
	}

	public List<String> getLastSearchList()
	{
		return lastSearchList;
	}

	public void setLastSearchList(List<String> lastSearchList)
	{
		this.lastSearchList = lastSearchList;
	}

}
