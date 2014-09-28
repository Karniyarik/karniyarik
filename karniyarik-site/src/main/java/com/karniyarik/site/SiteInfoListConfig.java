package com.karniyarik.site;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.URLUtil;

@SuppressWarnings("serial")
public class SiteInfoListConfig extends XMLConfiguration 
{
	private Map<String, SiteInfoConfig> list = new HashMap<String, SiteInfoConfig>();
	
	public SiteInfoListConfig(URL url) throws Exception
	{
		super(url);
		init();
	}
	
	private void init()  throws Exception
	{
		URLUtil urlUtil = new URLUtil();
		List aList = getList("firma.firmaadi");

		String aPath = null;
		SiteInfoConfig config = null;
		String siteName = null;
		for (int anIndex = 0; anIndex < aList.size(); anIndex++)
		{
			aPath = "firma(" + anIndex + ")";
			config = new SiteInfoConfig(configurationAt(aPath), aList.size());
			siteName = urlUtil.extractDomainName(config.getSiteURL());
			
			//siteName = siteName.replaceAll("\\s", "");
			//siteName = StringUtil.convertTurkishCharacter(siteName).toLowerCase();
			
			if (StringUtils.isNotBlank(siteName)) {
				list.put(siteName, config);
			}
		}
	}
	
	public SiteInfoConfig getSiteInfoConfig(String siteName)
	{
		return list.get(siteName.toLowerCase());
	}

	public Collection<SiteInfoConfig> getSiteInfoConfigList()
	{
		return list.values();
	}
	
}
