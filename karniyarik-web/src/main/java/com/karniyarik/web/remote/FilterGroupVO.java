package com.karniyarik.web.remote;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.web.json.LinkedLabel;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="filtergroup", propOrder={"name", "values"} )
public class FilterGroupVO
{
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "value")
	@XmlElementWrapper( name="values" )
	private List<FilterVO> values = new ArrayList<FilterVO>();
	
	public FilterGroupVO()
	{
	}
	
	public FilterGroupVO(String name, List<LinkedLabel> valueLabels)
	{
		setName(name);

		for (LinkedLabel label : valueLabels)
		{
			values.add(new FilterVO(label));
		}
	}
	
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setValues(List<FilterVO> values)
	{
		this.values = values;
	}
	
	public List<FilterVO> getValues()
	{
		return values;
	}	
}
