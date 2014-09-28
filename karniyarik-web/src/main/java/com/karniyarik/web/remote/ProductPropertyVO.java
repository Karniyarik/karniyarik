package com.karniyarik.web.remote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.common.vo.ProductProperty;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "prop", propOrder = { "name", "value"})
public class ProductPropertyVO
{
	@XmlElement(name = "name")
	private String	name	= null;
	
	@XmlElement(name = "val")
	private String	value	= null;

	public ProductPropertyVO()
	{
		
	}
	
	public ProductPropertyVO(ProductProperty property)
	{
		this.name = property.getName();
		this.value = property.getValue();
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

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
