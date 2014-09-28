package com.karniyarik.web.remote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.web.citydeals.CityResult;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "city")
public class CityVO
{
	@XmlElement(name = "name")
	public String name;
	@XmlElement(name = "value")
	public String value;
	
	@XmlElement(name = "selected")
	public boolean selected = false;
	
	@XmlElement(name = "dealcount")
	private int dealCount;

	public CityVO()
	{
	}

	public CityVO(CityResult city)
	{
		setName(city.getName());
		setValue(city.getValue());
		setSelected(city.isSelected());
		setDealCount(city.getDealCount());
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public int getDealCount()
	{
		return dealCount;
	}

	public void setDealCount(int dealCount)
	{
		this.dealCount = dealCount;
	}
}