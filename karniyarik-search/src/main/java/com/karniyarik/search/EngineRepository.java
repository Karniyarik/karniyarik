package com.karniyarik.search;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.util.ExecutorUtil;
import com.karniyarik.search.searcher.ISearcher;
import com.karniyarik.search.searcher.SearcherFactory;
import com.karniyarik.search.sponsored.SponsoredMerchantService;
import com.karniyarik.search.sponsored.SponsoredMerchantServiceFactory;

public class EngineRepository
{
	private final Map<String, ISearcher>	categorySearchToolMap;
	private static EngineRepository			INSTANCE	= null;
	private final ExecutorService			logExecutor;

	private EngineRepository()
	{
		categorySearchToolMap = new HashMap<String, ISearcher>();

		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();

		SearcherFactory fac = new SearcherFactory();
		for (CategoryConfig categoryConfig : categorizerConfig.getCategoryConfigList())
		{
			categorySearchToolMap.put(categoryConfig.getName(), fac.create(categoryConfig.getName()));
		}
		
		logExecutor = Executors.newSingleThreadExecutor(); 
	}

	public static EngineRepository getInstance()
	{
		if (INSTANCE == null)
		{
			synchronized (EngineRepository.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = new EngineRepository();
				}
			}
		}

		return INSTANCE;
	}

	public ISearcher getCategorySearcher(String category)
	{
		return categorySearchToolMap.get(category);
	}

	public void shutDownLogger() {
		ExecutorUtil.shutDown(logExecutor, "Search Log executor");
	}

	public void refreshSponsoredLinksData() {

		SponsoredMerchantService newService = null;
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String statisticsMachineUrl = deploymentConfig.getStatisticsDeploymentURL();

		for (String cat : categorySearchToolMap.keySet()) {
			newService = new SponsoredMerchantServiceFactory().create(cat, statisticsMachineUrl);
			categorySearchToolMap.get(cat).refreshSponsoredMerchantService(newService);
		}
	}
}