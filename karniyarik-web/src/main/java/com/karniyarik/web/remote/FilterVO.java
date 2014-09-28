package com.karniyarik.web.remote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringEscapeUtils;

import com.karniyarik.web.json.LinkedLabel;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType( name="filter", propOrder={"name", "count"} )
public class FilterVO
{
	@XmlElement(name = "name")
	private String	name;

	@XmlElement(name = "count")
	private int		count;

	public FilterVO()
	{
	}

	public FilterVO(LinkedLabel label)
	{
		setName(StringEscapeUtils.unescapeHtml(label.getLabel()));
		setCount(label.getCount());
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getCount()
	{
		return count;
	}
}
