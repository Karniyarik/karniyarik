package com.karniyarik.web.sitemap;

import com.karniyarik.common.KarniyarikRepository;

public class InsightGenerator {
	
	URLProviderIterator[] providers = null;
	
	private String rootPath = null;
	
	public InsightGenerator(String insightFilename) {
		this.rootPath = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getSitemapRootPath();
		providers = new URLProviderIterator[] {
				URLProviderFactory.createInsightProvider(rootPath, insightFilename)
			};
		
	}
	
	public void generate()
	{
		new SitemapGenerator(rootPath).generate(providers);
	}
	
	public static void main(String[] args) {
		new InsightGenerator("insight1.txt").generate();
	}
	
}
