package com.karniyarik.common.statistics.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "topSearch", propOrder = { "query", "count" })
public class TopSearch
{

	@XmlElement(name = "query")
	private String	query;

	@XmlElement(name = "count")
	private int		count;

	public TopSearch()
	{
	}

	public TopSearch(String query, int count)
	{
		this.query = query;
		this.count = count;
	}

	public String getQuery()
	{
		return query;
	}

	public int getCount()
	{
		return count;
	}
}
