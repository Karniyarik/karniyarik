package com.karniyarik.search.searcher.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.lucene.index.IndexReader;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.util.ExecutorUtil;
import com.karniyarik.ir.index.IndexSearcherFactory;

public class SearchLogIndexer
{
	private static SearchLogIndexer							instance	= null;
	private final Map<Integer, CategorySearchLogIndexer>	indexerMap;
	private final Map<Integer, ExecutorService>				executorMap;

	private SearchLogIndexer()
	{
		indexerMap = new HashMap<Integer, CategorySearchLogIndexer>();
		executorMap = new HashMap<Integer, ExecutorService>();

		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		IndexSearcherFactory fac = new IndexSearcherFactory();
		String category;
		IndexReader indexReader;
		int catType;
		int cacheSize = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getSearchLogCacheSize();
		for (CategoryConfig config : categorizerConfig.getCategoryConfigList())
		{
			category = config.getName();
			catType = CategorizerConfig.getCategoryType(category);
			indexReader = fac.createSearchLogIndexReader(category);
			indexerMap.put(catType, new CategorySearchLogIndexer(category, indexReader, cacheSize));
			executorMap.put(catType, Executors.newSingleThreadExecutor());
		}
	}

	public synchronized static SearchLogIndexer getInstance()
	{
		if (instance == null)
		{
			instance = new SearchLogIndexer();
		}
		return instance;
	}

	public void log(String query, int catType)
	{
		ExecutorService executor = executorMap.get(catType);
		CategorySearchLogIndexer indexer = indexerMap.get(catType);
		SearchLogTask task = new SearchLogTask(indexer, query);
		executor.execute(task);
	}

	public double docFreq(String term, int catType)
	{
		return indexerMap.get(catType).docFreq(term);
	}

	public void close()
	{
		for (ExecutorService executor : executorMap.values())
		{
			ExecutorUtil.shutDown(executor, "Search Log Index executor");
		}
	}

}
