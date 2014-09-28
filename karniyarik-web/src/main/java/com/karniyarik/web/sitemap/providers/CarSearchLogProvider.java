package com.karniyarik.web.sitemap.providers;

import java.util.Calendar;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.statistics.vo.TopSearch;
import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.web.sitemap.BaseURLProvider;
import com.karniyarik.web.sitemap.Generator;
import com.karniyarik.web.sitemap.SearchLogGenerator;
import com.karniyarik.web.sitemap.SiteMapGenerationConstants;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;

public class CarSearchLogProvider extends BaseURLProvider {
	
	int index = 0;

	private TopSearches weeklySearches = null;

	public CarSearchLogProvider(String rootPath, int type) 
	{
		if(type == SearchLogGenerator.WEEKLY)
		{
			weeklySearches = StatisticsWebServiceUtil.getWeeklySearches(CategorizerConfig.CAR_TYPE, 
					SiteMapGenerationConstants.MAX_TOP_SEARCHES, 
					Calendar.getInstance().getTimeInMillis(), 600000);
		}
		else
		{
			weeklySearches = StatisticsWebServiceUtil.getMonthlySearches(CategorizerConfig.CAR_TYPE, 
					SiteMapGenerationConstants.MAX_TOP_SEARCHES,
					Calendar.getInstance().getTimeInMillis(), 600000);
		}
		
		int maxFileIndex = Generator.findMaxIndex(rootPath, getFilename());
		setFileStartIndex(maxFileIndex+1);
	}

	@Override
	public String getFilename() {
		return "car_search";
	}

	@Override
	public boolean hasNext() {
		return weeklySearches != null && weeklySearches.getTopSearchList() != null && index < weeklySearches.getTopSearchList().size();
	}

	@Override
	public TUrl next() 
	{
		TopSearch topSearch = weeklySearches.getTopSearchList().get(index);
		index++;
		String label = topSearch.getQuery();
		String url = generateURL(label.trim(), SiteMapGenerationConstants.CAR_ROOT);
		TUrl urlType = createTUrl(url, SiteMapGenerationConstants.TOP_SEARCHES_PRIORITY, SiteMapGenerationConstants.TOP_SEARCHES_FREQ);	
		return urlType;
	}
}