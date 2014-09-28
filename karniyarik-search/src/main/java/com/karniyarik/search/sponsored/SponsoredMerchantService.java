package com.karniyarik.search.sponsored;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.vo.Product;

public class SponsoredMerchantService {

	private Map<String, Map<String, ViewHitStatistics>> container;
	private List<String> sponsoredMerchants;

	public SponsoredMerchantService(Map<String, Map<String, ViewHitStatistics>> container) {
		this.sponsoredMerchants = new ArrayList<String>();
		this.container = container;
	}

	public SponsoredMerchantService(List<String> sms, Map<String, Map<String, ViewHitStatistics>> container) {
		this.sponsoredMerchants = sms;
		this.container = container;
	}

	public boolean isSponsored(String merchantName) {
		return sponsoredMerchants.contains(merchantName);
	}
	
	public ViewHitStatistics getStatistics(String site, String link) {
		return container.get(site).get(link) != null ?  container.get(site).get(link) : new ViewHitStatistics();
	}

	// will be implemented -- km
	public List<Product> sortSponsoredProducts(List<Product> productList) {

		List<SponsoredProductWithScore> list = new ArrayList<SponsoredProductWithScore>(productList.size());
		List<Product> result = new ArrayList<Product>(productList.size());

		for (Product product : productList) {
			ViewHitStatistics statistics = getStatistics(product.getSourceName(), product.getLink());
			Double score = calculateScore(statistics, product);
			list.add(new SponsoredProductWithScore(product, score));
		}

		Collections.sort(list);

		for (SponsoredProductWithScore sp : list) {
			result.add(sp.getProduct());
		}

		return result;
	}
	
	public List<String> getSponsoredMerchants() {
		return sponsoredMerchants;
	}

	private Double calculateScore(ViewHitStatistics statistics, Product product) {
		// will be definitely changed
		Double result = (statistics.getHit()+1 * 1.0)/(statistics.getView()+1);
		//result += product.getScore()/10;
		return result;
	}
}