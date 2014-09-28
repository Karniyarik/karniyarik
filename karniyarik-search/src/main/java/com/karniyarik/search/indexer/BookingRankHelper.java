package com.karniyarik.search.indexer;

import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;

public class BookingRankHelper implements ISpecializedRankHelper {
	@Override
	public float getRank(Product product, CategoryConfig categoryConfig) {
		
		float rank = 0;
		
			for(ProductProperty productProperty: product.getProperties())
			{
				try {
					if(productProperty.getName().equals("rating"))
					{
						Double value = Double.parseDouble(productProperty.getValue());
						rank += value / 150000d;
					}
					
					if(productProperty.getName().equals("star")){
						Double value = Double.parseDouble(productProperty.getValue());
						rank += value/5; 
					}
				} catch (Throwable e) {
				}
			}
			
			rank = rank / 2;
		
		return rank;
	}
}
