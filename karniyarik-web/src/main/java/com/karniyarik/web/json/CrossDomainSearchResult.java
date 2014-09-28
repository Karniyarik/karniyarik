package com.karniyarik.web.json;

import java.util.ArrayList;
import java.util.List;

public class CrossDomainSearchResult {
	private List<ProductResult> results = new ArrayList<ProductResult>();
	private long totalHits = 0;
	private String categoryName;
	private String url;
	
	public CrossDomainSearchResult() {
		// TODO Auto-generated constructor stub
	}

	public List<ProductResult> getResults() {
		return results;
	}

	public void setResults(List<ProductResult> results) {
		this.results = results;
	}

	public long getTotalHits() {
		return totalHits;
	}

	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
