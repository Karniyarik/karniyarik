package com.karniyarik.search.sponsored;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.site.SiteInfo;
import com.karniyarik.common.site.SiteRegistry;
import com.karniyarik.common.statistics.vo.SiteSponsorStat;
import com.karniyarik.common.statistics.vo.SponsorStat;
import com.karniyarik.common.statistics.vo.SponsorStats;
import com.karniyarik.common.util.StatisticsWebServiceUtil;

public class SponsoredMerchantServiceFactory
{

	public SponsoredMerchantService create(String category, String statisticsMachineUrl)
	{

		List<String> sponsoredMerchants = new ArrayList<String>();

		Collection<SiteConfig> siteConfigList = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfigList();

		for (SiteConfig siteConfig : siteConfigList)
		{
			SiteInfo siteInfo = SiteRegistry.getInstance().getSiteInfo(siteConfig.getSiteName());
			if (siteInfo.isSponsored()/*siteConfig.isSponsored()*/ && siteConfig.getCategory().equals(category))
			{
				sponsoredMerchants.add(siteConfig.getSiteName());
			}
		}

		Date restDateParam = DateUtils.addMonths(new Date(), -1);
		
		Map<String, Map<String, ViewHitStatistics>> sponsorMap = initializeSponsorMap(sponsoredMerchants);
		SponsorStats sponsorStats = StatisticsWebServiceUtil.getSponsorStats(sponsoredMerchants, restDateParam.getTime());
		createUrlMaps(sponsorMap, sponsorStats);
		SponsoredMerchantService smService = new SponsoredMerchantService(sponsoredMerchants, sponsorMap);

		return smService;
	}
	
	private Map<String, Map<String, ViewHitStatistics>> initializeSponsorMap(List<String> siteList) {
		HashMap<String, Map<String, ViewHitStatistics>> result = new HashMap<String, Map<String, ViewHitStatistics>>();
		for (String siteName : siteList)
		{
			result.put(siteName, new HashMap<String, ViewHitStatistics>());
		}
		return result;
	} 

	private void createUrlMaps(Map<String, Map<String, ViewHitStatistics>> sponsorMap, SponsorStats sponsorStats)
	{
		Map<String, ViewHitStatistics> temp = null;

		if (sponsorStats != null && sponsorStats.getSiteSponsorStatList() != null)
		{

			for (SiteSponsorStat s : sponsorStats.getSiteSponsorStatList())
			{

				temp = new HashMap<String, ViewHitStatistics>();

				if (s.getSponsorStatList() != null)
				{
					for (SponsorStat ss : s.getSponsorStatList())
					{
						temp.put(ss.getUrl(), new ViewHitStatistics(ss.getClicks(), ss.getViews()));
					}
				}

				sponsorMap.put(s.getSiteName(), temp);
			}
		}
	}

}
