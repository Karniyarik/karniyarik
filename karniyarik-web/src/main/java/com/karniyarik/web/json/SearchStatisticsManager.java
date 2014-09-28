package com.karniyarik.web.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;


public class SearchStatisticsManager
{
	private static SearchStatisticsManager		INSTANCE	= null;
	private final Map<Integer, ExecutorService>	executorMap;
	private final Map<Integer, SearchStatistics>		searchLogMap;
	
	public SearchStatisticsManager()
	{
		executorMap = new HashMap<Integer, ExecutorService>();
		searchLogMap = new HashMap<Integer, SearchStatistics>();
		SearchStatisticsFactory logFactory = new SearchStatisticsFactory();
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		for(String category: categorizerConfig.getCategoryConfigMap().keySet())
		{
			int catType = CategorizerConfig.getCategoryType(category);
			//executors must be single threaded
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executorMap.put(catType, executor);
			searchLogMap.put(catType, logFactory.create(catType));
		}
		
	}

	public static SearchStatisticsManager getInstance()
	{
		if (INSTANCE == null)
		{
			synchronized (SearchStatisticsManager.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = new SearchStatisticsManager();
				}
			}
		}
		return INSTANCE;
	}

	public void searchOcurred(int catType, String query)
	{
		ExecutorService executor = executorMap.get(catType);
		SearchStatistics searchLog = searchLogMap.get(catType);
		SearchStatisticsTask task = new SearchStatisticsTask(query, searchLog);
		executor.execute(task);
	}
	
	public List<LinkedLabel> getLastSearches(int catType) {
		return searchLogMap.get(catType).getLastSearches();
	}
	
	public List<LinkedLabel> getTopSearches(int catType) {
		return searchLogMap.get(catType).getTopSearches();
	}
	
	public void setTopSearches(int catType, List<LinkedLabel> topSearches) {
		searchLogMap.get(catType).setTopSearches(topSearches);
	}
	
}
