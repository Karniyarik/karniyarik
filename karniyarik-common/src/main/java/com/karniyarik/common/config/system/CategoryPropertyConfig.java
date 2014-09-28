package com.karniyarik.common.config.system;

import org.apache.commons.configuration.HierarchicalConfiguration;

import com.karniyarik.common.config.ConfigAttributeCollectType;
import com.karniyarik.common.config.ConfigAttributeType;

public class CategoryPropertyConfig
{
	private HierarchicalConfiguration configNode = null;

	public CategoryPropertyConfig(HierarchicalConfiguration configNode)
	{
		this.configNode = configNode; 
	}

	public String getName()
	{
		return configNode.getString("[@name]");
	}

	public ConfigAttributeType getType()
	{
		return ConfigAttributeType.getType(configNode.getString("[@type]"));
	}
	
	public ConfigAttributeCollectType getCollectType()
	{
		return ConfigAttributeCollectType.getType(configNode.getString("[@collect]"));
	}
}
