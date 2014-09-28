package com.karniyarik.common.site;

import java.util.HashMap;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.cache.CacheProvider;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.util.JerseyUtil;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;
import com.karniyarik.externalrank.alexa.AlexaSiteInfoList;

public class SiteRegistry {
	
	private static Logger log = Logger.getLogger(SiteRegistry.class);
	
	private static SiteRegistry instance = new SiteRegistry();

	private Cache featuredCache = null;
	private Cache siteCache = null;
	private Cache alexaCache = null;

	public SiteRegistry() {
		featuredCache = CacheProvider.getInstance().getCache("featured");
		siteCache = CacheProvider.getInstance().getCache("siteinfo");
		alexaCache = CacheProvider.getInstance().getCache("alexainfo");
	}

	public static SiteRegistry getInstance() {
		return instance;
	}

	public SiteInfo getSiteInfo(String id) {
		Element element = siteCache.get(id);

		if (element != null) {
			return (SiteInfo) element.getValue();
		} else {
			SiteInfo info = new SiteInfo();
			info.setName(id);
			info.setFeatured(false);
			info.setSponsored(false);
			siteCache.put(new Element(info.getName(), info));
			return info;
		}
	}

	public AlexaSiteInfo getAlexaInfo(String sitename)
	{
		Element element = alexaCache.get(sitename);

		if (element != null) {
			return (AlexaSiteInfo) element.getValue();
		} 
		return null;
	}
	
	public void refreshSiteCache() {
		synchronized (this) {
			DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
			String enterpriseDeploymentURL = deploymentConfig.getEnterpriseDeploymentURL();
			
			String jsonStr = JerseyUtil.getObject(enterpriseDeploymentURL
					+ "/api/sites/list", new HashMap<String, String>(),
					String.class);
			
			List<SiteInfo> siteInfoList = SiteInfoFactory.create(jsonStr);
			
			if(siteInfoList != null && siteInfoList.size() > 0)
			{
				siteCache.removeAll();

				for (SiteInfo info : siteInfoList) {
					siteCache.put(new Element(info.getName(), info));
				}				
			}
			else
			{
				log.debug("Cannot get site data from enteprise");
			}
		}
	}
	
	public void refreshAlexaCache() {
		synchronized (this) {
			DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
			String adminDeploymentURL = deploymentConfig.getJobSchedulerDeploymentURL();
			
			AlexaSiteInfoList list = JerseyUtil.getObject(adminDeploymentURL + "/rest/getalexainfolist", new HashMap<String, String>(),AlexaSiteInfoList.class);
			
			if(list != null && list.getSites() != null && list.getSites().size() > 0)
			{
				alexaCache.removeAll();
				for(AlexaSiteInfo info: list.getSites())
				{
					alexaCache.put(new Element(info.getSitename(), info));
				}
			}			
		}
	}

	public void refreshFeaturedCache() {
		synchronized (this) {
			DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();			
			String enterpriseDeploymentURL = deploymentConfig.getEnterpriseDeploymentURL();
			String jsonStr = JerseyUtil.getObject(enterpriseDeploymentURL
					+ "/api/featured/list", new HashMap<String, String>(),
					String.class);
			
			List<FeaturedMerchantVO> featuredList = FeaturedMerchantFactory
					.createList(jsonStr);
			
			if(featuredList != null && featuredList.size() > 0)
			{
				featuredCache.removeAll();

				for (FeaturedMerchantVO info : featuredList) {
					featuredCache.put(new Element(info.getSitename(), info));
				}
			}
			else
			{
				log.debug("Cannot get Featured data from enteprise");
			}
		}
	}

	public FeaturedMerchantVO getFeaturedMerchant(String id) {
		Element element = featuredCache.get(id);

		if (element != null) {
			return (FeaturedMerchantVO) element.getValue();
		} else {
			return new FeaturedMerchantVO();
		}
	}

	public void sendSiteInfo() {
		SitesConfig sitesConfig = KarniyarikRepository.getInstance()
				.getConfig().getConfigurationBundle().getSitesConfig();
		
		SiteVOList siteList = new SiteVOList();

		for (SiteConfig siteConfig : sitesConfig.getSiteConfigList()) {
			SiteVO siteVO = new SiteVO(siteConfig);
			siteList.getSitelist().add(siteVO);
		}

		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String enterpriseDeploymentURL = deploymentConfig.getEnterpriseDeploymentURL();
		
		JerseyUtil.sendJSONPut(enterpriseDeploymentURL + "/api/sites/set", siteList, false, 20000);
	}
}
