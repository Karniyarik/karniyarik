package com.karniyarik.web.sitemap.providers;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.categorizer.io.CategoryIO;
import com.karniyarik.categorizer.xml.category.CategoryType;
import com.karniyarik.categorizer.xml.category.RootType;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.search.textcluster.SitemapClusterConstuctor;
import com.karniyarik.web.sitemap.BaseURLProvider;
import com.karniyarik.web.sitemap.SiteMapGenerationConstants;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;

public class ProductCategoryProvider extends BaseURLProvider {
	
	int index = 0;
	
	private LinkedList<String> clusterCache = new LinkedList<String>();
	private LinkedList<String> categoryList = new LinkedList<String>();

	public ProductCategoryProvider() {
		RootType categoryRoot = new CategoryIO().read("karniyarik");
		fillCategoryLabels(categoryRoot.getCategory());
	}
	
	private void fillCategoryLabels(CategoryType category)
	{
		String label = clean(category.getName());
		if(StringUtils.isNotBlank(label))
		{
			categoryList.add(label);	
		}

		for (Serializable content : category.getContent())
		{
			if (content instanceof JAXBElement<?>)
			{
				fillCategoryLabels((((JAXBElement<CategoryType>) content).getValue()));
			}
		}
	}

	public String clean(String value)
	{
		if (value != null)
		{
			value = value.replaceAll(",", " ");
			value = value.replaceAll("\\s+", "");
			value.trim();
		}
		return value;
	}

	
	@Override
	public String getFilename() {
		return "product_category";
	}

	@Override
	public boolean hasNext() {
		fillClusterCache();
		return clusterCache.size() > 0;
	}

	@Override
	public TUrl next() 
	{
		String label = clusterCache.poll();
		String url = generateURL(label, SiteMapGenerationConstants.PRODUCT_ROOT);		 
		TUrl urlType = createTUrl(url, 
				SiteMapGenerationConstants.PRODUCT_CATEGORY_PRIORITY, 
				SiteMapGenerationConstants.PRODUCT_CATEGORY_CHANGE_FREQ);
		
		return urlType;
	}

	private void fillClusterCache() {
		while(index < categoryList.size() && clusterCache.size() < 1)
		{
			String label = categoryList.get(index);
			index++;
			SitemapClusterConstuctor constructor = new SitemapClusterConstuctor();
			List<String> labels = constructor.getClusters(label, 
					CategorizerConfig.PRODUCT, 
					SiteMapGenerationConstants.PRODUCT_CATEGORY_MAX_CLUSTER, 
					SiteMapGenerationConstants.PRODUCT_CATEGORY_MAX_SEARCH_RESULT);
			clusterCache.addAll(labels);
		}
	}
}