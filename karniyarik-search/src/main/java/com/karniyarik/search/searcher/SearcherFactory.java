package com.karniyarik.search.searcher;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.search.searcher.query.QueryParserFactory;
import com.karniyarik.search.solr.SOLRSearcherImpl;
import com.karniyarik.search.sponsored.SponsoredMerchantService;
import com.karniyarik.search.sponsored.SponsoredMerchantServiceFactory;

public class SearcherFactory
{

	public ISearcher create(String category)
	{
		SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();

//		SpellChecker spellChecker = new SpellCheckerFactory().create(category);
//		IndexSearcherFactory indexSearcherFactory = new IndexSearcherFactory();
//		IndexSearcher searcher = indexSearcherFactory.createProductIndexSearcher(category);

		QueryParserFactory qpf = new QueryParserFactory();

		CategorizerConfig categorizer = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		CategoryConfig categoryConfig = categorizer.getCategoryConfig(category);
		int categoryType = CategorizerConfig.getCategoryType(category);
		int clusterSize = searchConfig.getClusterSize();
		
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String statisticsMachineUrl = deploymentConfig.getStatisticsDeploymentURL();

		SponsoredMerchantService smService = null;
		
		try 
		{
			smService = new SponsoredMerchantServiceFactory().create(category, statisticsMachineUrl);
		} catch (Exception e) {
			//e.printStackTrace();
		}

		//return new SOLRSearcherImpl(searcher, spellChecker, categoryConfig, qpf, categoryType, clusterSize, smService);
		return new SOLRSearcherImpl(categoryConfig, qpf, categoryType, smService);
	}
}
