package com.karniyarik.web.sitemap;

import com.karniyarik.common.KarniyarikRepository;

public class SearchLogGenerator {

	public static int WEEKLY = 0;
	public static int MONTHLY = 1;

	URLProviderIterator[] providers = new URLProviderIterator[] {};
	private String rootPath = null;
	
	public SearchLogGenerator(int type) {
		this.rootPath = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getSitemapRootPath();
		
		providers = new URLProviderIterator[] {
				URLProviderFactory.createProductSearchLogProvider(rootPath, type),
				URLProviderFactory.createCarSearchLogProvider(rootPath, type)
			};
	}
	
	public void generate()
	{
		new SitemapGenerator(rootPath).generate(providers);
	}
	
	public static void main(String[] args) throws Exception
	{
		SearchLogGenerator generator = new SearchLogGenerator(SearchLogGenerator.WEEKLY);
		generator.generate();
	}
}
