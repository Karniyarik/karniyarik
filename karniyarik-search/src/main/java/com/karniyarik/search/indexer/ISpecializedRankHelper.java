package com.karniyarik.search.indexer;

import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.vo.Product;

public interface ISpecializedRankHelper
{
	public float getRank(Product product, CategoryConfig categoryConfig);
}
