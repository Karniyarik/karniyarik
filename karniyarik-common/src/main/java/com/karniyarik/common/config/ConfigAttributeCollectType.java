package com.karniyarik.common.config;

import org.apache.commons.lang.StringUtils;

public enum ConfigAttributeCollectType
{
	Cluster(0), Occurence(1), Provided(2), None(3);

	private int value = 0;
	
	private ConfigAttributeCollectType(int value)
	{
		this.value = value;
	}
	
	public int intValue()
	{
		return value;
	}
	
	public static ConfigAttributeCollectType getType(String type)
	{
		if(!StringUtils.isBlank(type))
		{
			if(type.equalsIgnoreCase("cluster"))
			{
				return Cluster;
			}
			else if(type.equalsIgnoreCase("provided"))
			{
				return Provided;
			}
			else if(type.equalsIgnoreCase("occurrence"))
			{
				return Occurence;
			}
			else
			{
				return None;
			}
		}
		else
		{
			return None;	
		}
	}
}
