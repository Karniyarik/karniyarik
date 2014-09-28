package com.karniyarik.pagerank.dao;

public class ProductHit
{
	private final String url;
	private final int hitCount;
	
	public ProductHit(String url, int hitCount) {
		this.url = url;
		this.hitCount = hitCount;
	}
	
	public String getUrl()
	{
		return url;
	}

	public int getHitCount()
	{
		return hitCount;
	}

}
