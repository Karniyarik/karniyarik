package com.karniyarik.common.config;

import org.apache.commons.lang.StringUtils;

public enum ConfigAttributeType
{
	Integer(0), String(1), Double(2), Date(3);

	private int value = 0;
	
	private ConfigAttributeType(int value)
	{
		this.value = value;
	}
	
	public int intValue()
	{
		return value;
	}
	
	public static ConfigAttributeType getType(String type)
	{
		if(StringUtils.isBlank(type))
		{
			return null;
		}
		
		if(type.equalsIgnoreCase("string"))
		{
			return String;
		}
		else if(type.equalsIgnoreCase("integer"))
		{
			return Integer;
		}
		else if(type.equalsIgnoreCase("double"))
		{
			return Double;
		}
		else if(type.equalsIgnoreCase("date"))
		{
			return Date;
		}
		
		return null;
	}
}
