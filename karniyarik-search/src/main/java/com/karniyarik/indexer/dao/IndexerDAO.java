package com.karniyarik.indexer.dao;

import com.karniyarik.common.vo.Product;

public interface IndexerDAO
{

	public Product getNextProduct();

	public int getProductCount();

	public boolean hasNext();
	
	public void close();
}
