package com.karniyarik.web.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.karniyarik.ir.filter.KarniyarikFilterr;

public class CarProvider extends AbstractCategoryHandler
{
	public List<KarniyarikFilterr> getFilters(Map requestParameters)
	{
		List<KarniyarikFilterr> result = new ArrayList<KarniyarikFilterr>();

		appendDoubleRangeFilter("price", "price1", "price2", requestParameters, result);
		appendSingleTermFilter("brand", requestParameters, result);
		appendSingleTermFilter("city", requestParameters, result);
		appendSingleTermFilter("color", requestParameters, result);
		
		appendIntegerRangeFilter("enginepower", "enginepower1", "enginepower2", requestParameters, result);
		appendIntegerRangeFilter("enginevolume", "enginevolume1", "enginevolume2", requestParameters, result);
		appendIntegerRangeFilter("km", "km1", "km2", requestParameters, result);
		appendIntegerRangeFilter("modelyear", "modelyear1", "modelyear2", requestParameters, result);
		appendSingleTermFilter("gear", requestParameters, result);
		appendSingleTermFilter("fuel", requestParameters, result);
		
		return result;
	}
}
