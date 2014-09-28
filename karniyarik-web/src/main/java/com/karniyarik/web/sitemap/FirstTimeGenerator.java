package com.karniyarik.web.sitemap;

import com.karniyarik.common.KarniyarikRepository;

public class FirstTimeGenerator {
	
	URLProviderIterator[] providers = null;
	
	private String rootPath = null;
	
	public FirstTimeGenerator() {
		this.rootPath = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getSitemapRootPath();
		
		providers = new URLProviderIterator[] {
				URLProviderFactory.createCarBrandProvider(),
				URLProviderFactory.createInsightProvider(rootPath),
				URLProviderFactory.createProductBrandProvider(),
				URLProviderFactory.createProductCategoryProvider(),
				URLProviderFactory.createStaticPagesProvider(),
				URLProviderFactory.createProductSearchLogProvider(rootPath, SearchLogGenerator.MONTHLY),
				URLProviderFactory.createCarSearchLogProvider(rootPath, SearchLogGenerator.MONTHLY)
			};		
	}
	
	public void generate()
	{
		new SitemapGenerator(rootPath).generate(providers);
	}
	
	public static void main(String[] args) {
		
	}
	
}
