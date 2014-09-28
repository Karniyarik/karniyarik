package com.karniyarik.web.sitemap.providers;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.brands.BrandHolder;
import com.karniyarik.brands.BrandServiceImpl;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.search.textcluster.SitemapClusterConstuctor;
import com.karniyarik.web.sitemap.BaseURLProvider;
import com.karniyarik.web.sitemap.SiteMapGenerationConstants;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;

public class ProductBrandProvider extends BaseURLProvider {
	
	int index = 0;
	
	private LinkedList<String> clusterCache = new LinkedList<String>();
	private List<BrandHolder> brandHolderList = null;
	
	public ProductBrandProvider() {
		brandHolderList = BrandServiceImpl.getInstance().getAllBrands();
	}
	
	@Override
	public String getFilename() {
		return "product_brand";
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
		TUrl urlType = createTUrl(url, SiteMapGenerationConstants.PRODUCT_BRAND_PRIORITY, 
				SiteMapGenerationConstants.PRODUCT_BRAND_CHANGE_FREQ);
		
		return urlType;
	}

	private void fillClusterCache() {
		while(index < brandHolderList.size() && clusterCache.size() < 1)
		{
			BrandHolder brand = brandHolderList.get(index);
			index++;
			String label = brand.getActualBrand();
			if(StringUtils.isNotBlank(label)){
				SitemapClusterConstuctor constructor = new SitemapClusterConstuctor();
				List<String> labels = constructor.getClusters(label.trim(), CategorizerConfig.PRODUCT, 
						SiteMapGenerationConstants.PRODUCT_BRAND_MAX_CLUSTER, 
						SiteMapGenerationConstants.PRODUCT_BRAND_MAX_SEARCH_RESULT);
				
				clusterCache.addAll(labels);
			}
		}
	}
}