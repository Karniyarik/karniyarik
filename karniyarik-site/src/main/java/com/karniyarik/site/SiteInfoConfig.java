package com.karniyarik.site;

import org.apache.commons.configuration.HierarchicalConfiguration;

public class SiteInfoConfig  
{
	private HierarchicalConfiguration configNode = null;
	private int siteRank = 0;
	private float siteRankOver10 = 0;
	
	public SiteInfoConfig(HierarchicalConfiguration configNode, int totalSize) throws Exception
	{
		this.configNode = configNode;
		int rank = configNode.getInt("siralama");
		int diff = totalSize/10;
		siteRank = (int) Math.round((totalSize-rank)/(1.0 * diff));
		siteRankOver10 = siteRank * 1f / 2;
	}
	
	public String getSiteName()
	{
		return configNode.getString("firmaadi");
	}
	
	public String getSiteURL()
	{
		String url = configNode.getString("firmaadresi");
		if(!url.startsWith("http://"))
		{
			url = "http://" + url;
		}
		return url;
	}

	public String getSiteReviewURL()
	{
		return configNode.getString("link");
	}
	
	public Integer getCalculatedRank()
	{
		return siteRank;
	}

	public Integer getRank()
	{
		return configNode.getInt("siralama");
	}

	public String getImageURL()
	{
		return configNode.getString("kucukfoto");
	}
	
	public String getSmallImageURL()
	{
		return configNode.getString("buyukfoto");
	}
	
	public float getSiteRankOver10()
	{
		return siteRankOver10;
	}
}
