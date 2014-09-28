package com.karniyarik.search.sponsored;

import com.karniyarik.common.vo.Product;

public class SponsoredProductWithScore implements Comparable<SponsoredProductWithScore> {

	private Product product;
	private Double score;

	public SponsoredProductWithScore(Product product, Double score) {
		this.product = product;
		this.score = score;
	}

	@Override
	public int compareTo(SponsoredProductWithScore o) {
		return o.getScore().compareTo(score);
	}

	public Double getScore() {
		return score;
	}

	public Product getProduct() {
		return product;
	}
}
