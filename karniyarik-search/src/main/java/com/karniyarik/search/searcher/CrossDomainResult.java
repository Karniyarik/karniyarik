package com.karniyarik.search.searcher;

import java.util.ArrayList;
import java.util.List;

import com.karniyarik.common.vo.Product;

public class CrossDomainResult {
	private List<Product> results = new ArrayList<Product>();
	private long totalHits = 0;
	private String categoryName;
	
	public CrossDomainResult() {
		// TODO Auto-generated constructor stub
	}

	public List<Product> getResults() {
		return results;
	}

	public void setResults(List<Product> results) {
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
}
