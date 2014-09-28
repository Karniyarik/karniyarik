package com.karniyarik.search.searcher.autocomplete;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.common.util.StatisticsWebServiceUtil;

public class AutoCompleteEngine {
	
	private final Map<Integer, CategoryAutoCompleter> indexSearcherMap = new HashMap<Integer, CategoryAutoCompleter>();
	
	private static AutoCompleteEngine instance = null;
	
	private AutoCompleteEngine() {
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		String category;
		int catType;
		for (CategoryConfig config : categorizerConfig.getCategoryConfigList())
		{
			category = config.getName();
			catType = CategorizerConfig.getCategoryType(category);
			indexSearcherMap.put(catType, new CategoryAutoCompleter(category));
		}
	}
	
	public static AutoCompleteEngine getInstance() {
		if(instance == null)
		{
			instance = new AutoCompleteEngine();
		}
		return instance;
	}
	
	public void refresh()
	{
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		String category;
		int catType;
		for (CategoryConfig config : categorizerConfig.getCategoryConfigList())
		{
			category = config.getName();
			catType = CategorizerConfig.getCategoryType(category);
			TopSearches weeklySearches = StatisticsWebServiceUtil.getWeeklySearches(catType, 10000, new Date().getTime(), 1200000);
			indexSearches(weeklySearches);
		}
	}
	public void indexSearches(TopSearches topSearches)
	{
		if(topSearches != null)
		{
			indexSearcherMap.get(topSearches.getCategory()).indexQueries(topSearches);	
		}
	}
	
	public List<String> autocomplete(String term, int catType)
	{
		return indexSearcherMap.get(catType).autocomplete(term);
	}
}
