package com.karniyarik.web.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.karniyarik.ir.filter.KarniyarikFilterr;

public class ProductProvider extends AbstractCategoryHandler
{
	public List<KarniyarikFilterr> getFilters(Map requestParameters)
	{
		List<KarniyarikFilterr> result = new ArrayList<KarniyarikFilterr>();

		appendDoubleRangeFilter("price", "price1", "price2", requestParameters, result);
		appendSingleTermFilter("source", requestParameters, result);
		appendSingleTermFilter("brand", requestParameters, result);
		appendSingleTermFilter("tags", requestParameters, result);
		appendSingleTermFilter("class", requestParameters, result);
		
		return result;
	}
}
