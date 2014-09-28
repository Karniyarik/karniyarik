package com.karniyarik.web.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.karniyarik.ir.filter.KarniyarikFilterr;

public class EstateProvider extends AbstractCategoryHandler
{
	public List<KarniyarikFilterr> getFilters(Map requestParameters)
	{
		List<KarniyarikFilterr> result = new ArrayList<KarniyarikFilterr>();

		appendDoubleRangeFilter("price", "price1", "price2", requestParameters, result);
		//appendSingleTermFilter("brand", requestParameters, result);
		appendSingleTermFilter("city", requestParameters, result);
		appendSingleTermFilter("district", requestParameters, result);
		appendSingleTermFilter("roomcount", requestParameters, result);
		appendSingleTermFilter("salooncount", requestParameters, result);
		appendSingleTermFilter("floorcount", requestParameters, result);
		appendSingleTermFilter("warmingtype", requestParameters, result);
		appendSingleTermFilter("buildingtype", requestParameters, result);
		appendIntegerRangeFilter("area", "area1", "area2", requestParameters, result);
		
		return result;
	}
}
