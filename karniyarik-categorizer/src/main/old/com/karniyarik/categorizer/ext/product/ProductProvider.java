package com.karniyarik.categorizer.ext.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.karniyarik.categorizer.AbstractCategoryHandler;
import com.karniyarik.ir.filter.KarniyarikFilterr;

public class ProductProvider extends AbstractCategoryHandler
{
	public List<KarniyarikFilterr> getFilters(Map requestParameters)
	{
		List<KarniyarikFilterr> result = new ArrayList<KarniyarikFilterr>();

		appendIntegerRangeFilter("price", "price1", "price2", requestParameters, result);
		appendSingleTermFilter("source", requestParameters, result);
		appendSingleTermFilter("brand", requestParameters, result);
		
		return result;
	}
}
