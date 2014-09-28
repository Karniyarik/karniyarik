package com.karniyarik.common.config.site;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author meralan
 * 
 */
@SuppressWarnings("serial")
public class SitesConfig implements Serializable
{
	private Map<String, SiteConfig>	sitesMap	= null;

	public void setSitesMap(Map<String, SiteConfig> sitesMap)
	{
		this.sitesMap = sitesMap;
	}
	
	public void updateSiteConfig(SiteConfig config)
	{
		sitesMap.put(config.getSiteName(), config);
	}

	public void deleteSiteConfig(String sitename)
	{
		sitesMap.remove(sitename);
	}

	public SiteConfig getSiteConfig(String siteName)
	{
		return sitesMap.get(siteName);
	}

	public Collection<SiteConfig> getSiteConfigList()
	{
		return sitesMap.values();
	}

	public Collection<SiteConfig> getSiteConfigListOrderByName()
	{
		List<SiteConfig> siteConfigList = new ArrayList<SiteConfig>();
		siteConfigList.addAll(sitesMap.values());
		Collections.sort(siteConfigList, new SiteConfigNameComparator());
		return siteConfigList;
	}

	public int getSiteCount()
	{
		return sitesMap.values().size();
	}

}
