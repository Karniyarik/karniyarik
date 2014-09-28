package com.karniyarik.search.indexer;

import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;

public class CarRankHelper implements ISpecializedRankHelper {
	@Override
	public float getRank(Product product, CategoryConfig categoryConfig) {
		
		float rank = 0;
		
		for(ProductProperty productProperty: product.getProperties())
		{
			try {
				if(productProperty.getName().equals("km"))
				{
					Integer value = Integer.parseInt(productProperty.getValue());
					rank += (1-value/300000f);
				}
			} catch (Throwable e) {
			}
			
			try {
				
				if(productProperty.getName().equals("modelyear")){
					Integer value = Integer.parseInt(productProperty.getValue());
					rank += (1-value/3000f); 
				}
			} catch (Throwable e) {
			}
		}
		
		rank = rank / 2;
		
		return rank;
	}
}
