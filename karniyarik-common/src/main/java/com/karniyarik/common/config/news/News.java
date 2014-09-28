package com.karniyarik.common.config.news;

import org.apache.commons.configuration.HierarchicalConfiguration;

public class News {
	private HierarchicalConfiguration configNode = null;
	
	public News(HierarchicalConfiguration configNode)
	{
		this.configNode = configNode; 
	}
	
	public String getDate() {
		return configNode.getString("[@date]");
	}
	
	public String getTitle() {
		return configNode.getString("title");
	}
	
	public String getAbstractText() {
		return configNode.getString("abstract");
	}
	
	public String getFullText() {
		return configNode.getString("full");	
	}
}