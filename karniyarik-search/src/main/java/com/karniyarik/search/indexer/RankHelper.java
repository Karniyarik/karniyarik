package com.karniyarik.search.indexer;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.group.JobExecutorServiceUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;

public class RankHelper
{
	private int mMaxRank = 0;
	private CategoryConfig categoryConfig;
	private SiteConfig siteConfig;
	private ISpecializedRankHelper specializedRankHandler;
	private AlexaSiteInfo siteAlexaRank;
	
	public RankHelper(CategoryConfig categoryConfig, SiteConfig siteConfig)
	{
		mMaxRank = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getMaxRank();
		this.categoryConfig = categoryConfig;
		this.siteConfig = siteConfig;
		this.siteAlexaRank = JobExecutorServiceUtil.getAlexaInfo(siteConfig.getSiteName());
		
		String className = siteConfig.getRankHelper();
		
		if(StringUtils.isNotBlank(className))
		{
			try {
				Class clazz = Class.forName(className);
				specializedRankHandler = (ISpecializedRankHelper) clazz.newInstance();				
			} catch (Throwable e) {
			}
		}
	}
	
	public float getRank(Product product)
	{
		//page rank is between 0-100
		int aRank = product.getHit();
		
		//page rank is normalized between 1-2 
		float pageRank = Double.valueOf(1 + aRank * 1.0 / mMaxRank).floatValue();
		
		float specialRank = 0;
		
		if(specializedRankHandler != null)
		{
			specialRank = specializedRankHandler.getRank(product, categoryConfig);
		}
		//special rank is between 0-1 and moved between 1-2
		specialRank = specialRank+1;
		
		siteAlexaRank = JobExecutorServiceUtil.getAlexaInfo(siteConfig.getSiteName());
		
		float alexaRank = 0;
		//alexa rank is from 1-2
		if(siteAlexaRank != null)
		{
			alexaRank = normalizeAlexa(siteAlexaRank.getRank());	
		}

		//siteConfig Rank is between 0-100 and moved to 1-2
		float siteConfigRank = 1 + siteConfig.getSiteRank()/100f;
		
		float dataFeedBoost = siteConfig.isDatafeed() ? 1 : 0;
		
		float rank = (1f*pageRank + 1f*specialRank + 1f*alexaRank + 2*dataFeedBoost + 5f*siteConfigRank) / 5;
		
		//final rank is between 1-2
		return rank;
	}
	
	private float normalizeAlexa(long rank)
	{
		long maxRank = 1000000; //one million
		if(rank > maxRank)
		{
			rank = maxRank;
		}
		
		float result = 1 + ((maxRank - rank)*1f/maxRank);
		
		return result;
	}
}
