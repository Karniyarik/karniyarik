package com.karniyarik.web.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.search.EngineRepository;
import com.karniyarik.search.searcher.ISearcher;

public class WebApplicationDataHolder {
	private Integer siteCount = 0;
	private Integer productCount = 0;

	private Map<String, Integer> siteProductCountMap = null;

	private static WebApplicationDataHolder instance = null;

	private WebApplicationDataHolder() {
		siteProductCountMap = new HashMap<String, Integer>();
	}

	public static WebApplicationDataHolder getInstance() {
		if (instance == null) {
			instance = new WebApplicationDataHolder();
		}
		return instance;
	}

	public void setSiteProductCountMap(Map<String, Integer> aSiteProductCountMap) {
		siteProductCountMap = aSiteProductCountMap;
	}

	public Integer getSiteCount() {
		return siteCount;
	}

	public void setSiteCount(Integer siteCount) {
		this.siteCount = siteCount;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getSiteProductCount(String siteName) {
		if (siteProductCountMap.containsKey(siteName))
			return siteProductCountMap.get(siteName);
		return 0;
	}

	public List<String> getSiteList() {
		List<String> siteList = new ArrayList<String>();
		siteList.addAll(siteProductCountMap.keySet());
		return siteList;
	}

	public void refreshSiteData() {
		int productCount = 0;
		Map<String, Integer> siteProductCountMap = new HashMap<String, Integer>();

		Collection<CategoryConfig> list = KarniyarikRepository.getInstance()
				.getConfig().getConfigurationBundle().getCategorizerConfig()
				.getCategoryConfigList();

		ISearcher searcher = null;
		Map<String, Integer> tempMap = null;

		for (CategoryConfig categoryConfig : list) {
			searcher = EngineRepository.getInstance().getCategorySearcher(categoryConfig.getName());
			productCount += searcher.getTotalProductCountInSystem();
			tempMap = searcher.getSiteProductCounts();
			for (String key : tempMap.keySet()) {
				siteProductCountMap.put(key, tempMap.get(key));
			}
		}

		setProductCount(productCount);
		setSiteCount(KarniyarikRepository.getInstance().getConfig()
				.getConfigurationBundle().getSitesConfig().getSiteCount());
		setSiteProductCountMap(siteProductCountMap);
	}
}
