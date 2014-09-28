package com.karniyarik.common.vo;

public class ProductProperty implements Cloneable
{
	private String	name	= null;
	private String	value	= null;

	public ProductProperty()
	{
		// TODO Auto-generated constructor stub
	}
	
	public ProductProperty(String name, String value)
	{
		this.name = name;
		this.value = value;
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
		return new ProductProperty(this.getName(), this.getValue());
	}
}
