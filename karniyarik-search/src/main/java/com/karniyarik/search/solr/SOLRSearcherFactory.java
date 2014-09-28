package com.karniyarik.search.solr;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;

public class SOLRSearcherFactory {

	public static SOLRSearchProxy getSearcher(int category)
	{
		return getSearcher(category, 5000);
	}

	public static SOLRSearchProxy getSearcher(int category, int timeOut)
	{
		String categoryString = CategorizerConfig.getCategoryString(category);
		CategoryConfig categoryConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig().getCategoryConfig(categoryString);
		return new SOLRSearchProxy(categoryConfig, timeOut);
	}

	public static SOLRQuerySearchProxy getQuerySearcher() {
		return new SOLRQuerySearchProxy();
	}
}
