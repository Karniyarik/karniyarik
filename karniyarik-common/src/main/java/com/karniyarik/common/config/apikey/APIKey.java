package com.karniyarik.common.config.apikey;

import org.apache.commons.configuration.HierarchicalConfiguration;

public class APIKey
{
	private HierarchicalConfiguration configNode = null;
	
	public APIKey(HierarchicalConfiguration configNode)
	{
		this.configNode = configNode; 
	}
	
	public String getValue()
	{
		return configNode.getString("[@value]");
	}
	
	public String getName()
	{
		return configNode.getString("[@name]");
	}
	
	public int getLimit()
	{
		return configNode.getInt("[@limit]");
	}

}
