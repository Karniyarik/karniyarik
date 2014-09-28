package com.karniyarik.web.sitemap;

import com.karniyarik.web.sitemap.providers.CarBrandProvider;
import com.karniyarik.web.sitemap.providers.CarSearchLogProvider;
import com.karniyarik.web.sitemap.providers.InsightProvider;
import com.karniyarik.web.sitemap.providers.ProductBrandProvider;
import com.karniyarik.web.sitemap.providers.ProductCategoryProvider;
import com.karniyarik.web.sitemap.providers.ProductSearchLogProvider;
import com.karniyarik.web.sitemap.providers.StaticPagesProvider;

public class URLProviderFactory {
	
	public static URLProviderIterator createInsightProvider(String rootPath)
	{
		return new InsightProvider(rootPath);
	}

	public static URLProviderIterator createInsightProvider(String rootPath, String insightFileName)
	{
		return new InsightProvider(rootPath, insightFileName);
	}
	
	public static URLProviderIterator createProductBrandProvider()
	{
		return new ProductBrandProvider();
	}

	public static URLProviderIterator createCarBrandProvider()
	{
		return new CarBrandProvider();
	}

	public static URLProviderIterator createCarSearchLogProvider(String rootPath, int type)
	{
		return new CarSearchLogProvider(rootPath, type);
	}

	public static URLProviderIterator createProductSearchLogProvider(String rootPath, int type)
	{
		return new ProductSearchLogProvider(rootPath, type);
	}

	public static URLProviderIterator createStaticPagesProvider()
	{
		return new StaticPagesProvider();
	}

	public static URLProviderIterator createProductCategoryProvider()
	{
		return new ProductCategoryProvider();
	}

}
