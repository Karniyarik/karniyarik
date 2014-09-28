package com.karniyarik.web.sitemap.providers;

import java.util.LinkedList;
import java.util.List;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.search.textcluster.SitemapClusterConstuctor;
import com.karniyarik.web.sitemap.BaseURLProvider;
import com.karniyarik.web.sitemap.SiteMapGenerationConstants;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;

public class CarBrandProvider extends BaseURLProvider {
	
	private String[]	brands	= new String[] { "Acura", "Aleko",
			"Alfa Romeo", "AMC", "ARO", "Aston Martin", "Audi", "Austin",
			"Bentley", "BMC", "BMW", "Buick", "Cadillac", "Chery", "Chevrolet",
			"Chrysler", "Citroen", "Dacia", "Daewoo", "DAF", "Daihatsu", "DFM",
			"Diğer", "Dodge", "Eagle", "Excalibur", "Ferrari", "Fiat - Tofaş",
			"Ford", "GAZ", "Geely", "GMC", "HFKanuni", "Hino", "Honda",
			"Hummer", "Hyundai", "Infiniti", "Isuzu", "Iveco", "Jaguar",
			"Jeep", "Karsan", "Kia", "Lada", "Lamborghini", "Lancia",
			"Land Rover", "Lexus", "Lincoln", "Lotus", "Mahindra", "MAN",
			"Maserati", "Mazda", "Mega", "Mercedes", "Mercury", "MG", "Mini",
			"Mitsubishi", "Morgen", "Nissan", "Oldsmobile", "Opel", "Otokar",
			"Otoyol", "Peugeot", "Piaggio", "Plymouth", "Pontiac", "Porsche",
			"Proton", "Renault", "Rolls Royce", "Rover", "Saab", "Samand",
			"Samsung", "Scania", "Seat", "Skoda", "Smart", "SsangYong",
			"Subaru", "Suzuki", "Talbot", "Tata", "Temsa", "Toyota", "Trabant",
			"Volkswagen", "Volvo"};

	int index = 0;
	
	private LinkedList<String> clusterCache = new LinkedList<String>();
	
	@Override
	public String getFilename() {
		return "car_brand";
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
		String url = generateURL(label, SiteMapGenerationConstants.CAR_ROOT);		
		TUrl urlType = createTUrl(url, SiteMapGenerationConstants.CAR_BRAND_PRIORITY, SiteMapGenerationConstants.CAR_BRAND_CHANGE_FREQ);
		
		return urlType;
	}

	private void fillClusterCache() {
		
		while(index < brands.length && clusterCache.size() < 1)
		{
			String brand = brands[index];
			index++;
			SitemapClusterConstuctor constructor = new SitemapClusterConstuctor();
			List<String> labels = constructor.getClusters(brand, CategorizerConfig.CAR, 
					SiteMapGenerationConstants.CAR_BRAND_MAX_CLUSTER, 
					SiteMapGenerationConstants.CAR_BRAND_MAX_SEARCH_RESULT);
			
			clusterCache.addAll(labels);
		}
	}
}