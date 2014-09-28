package com.karniyarik.common.config.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.HierarchicalConfiguration;

public class CategoryConfig
{
	private Map<String, CategoryPropertyConfig>	propertyList	= new HashMap<String, CategoryPropertyConfig>();
	private HierarchicalConfiguration configNode = null;
	
	public CategoryConfig(HierarchicalConfiguration configNode)
	{
		this.configNode = configNode; 
		init();
	}

	@SuppressWarnings("unchecked")
	private void init()
	{
		List<String> propList = configNode.getList("properties.property[@name]");
		String aPath = null;
		CategoryPropertyConfig propConfig = null;

		for (int anIndex = 0; anIndex < propList.size(); anIndex++)
		{
			aPath = "properties.property(" + anIndex + ")";
			propConfig = new CategoryPropertyConfig(configNode.configurationAt(aPath));
			
			getPropertyMap().put(propConfig.getName(), propConfig);
		}
	}
	
	public String getName()
	{
		return configNode.getString("[@name]", "");
	}

	public String getCoreName()
	{
		return configNode.getString("corename", "");
	}

	public String getGuiPath()
	{
		return configNode.getString("[@guipath]", "");
	}

	public Map<String, CategoryPropertyConfig> getPropertyMap()
	{
		return propertyList;
	}

	public void setPropertyList(Map<String, CategoryPropertyConfig> propertyList)
	{
		this.propertyList = propertyList;
	}
	
}