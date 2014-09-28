package com.karniyarik.affiliate;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;

public class AffiliateProvider {

	private Map<String, IAffiliateSite> classMap = new HashMap<String, IAffiliateSite>();
	
	private static AffiliateProvider instance = new AffiliateProvider();
	
	private AffiliateProvider() {
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		for(SiteConfig siteConfig: sitesConfig.getSiteConfigList())
		{
			String className = siteConfig.getAffiliateClass();
			if(StringUtils.isNotBlank(className))
			{
				try {
					Class clazz = Class.forName(className);
					IAffiliateSite affiliateHandler = (IAffiliateSite) clazz.newInstance();
					classMap.put(siteConfig.getSiteName(), affiliateHandler);
				} catch (Throwable e) {
				}
			}
		}
	}
	
	public static AffiliateProvider getInstance() {
		return instance;
	}
	
	public boolean isAffiliate(String sitename)
	{
		return classMap.containsKey(sitename);
	}
	public String correctUrl(String url, String sitename)
	{
		IAffiliateSite affiliateHandler = classMap.get(sitename);
		
		if(affiliateHandler != null)
		{
			url = affiliateHandler.correctUrl(url);
		}
		
		return url;
	}
	
}
