package com.karniyarik.web.category;

import java.util.HashMap;
import java.util.Map;

import com.karniyarik.common.config.system.CategorizerConfig;

public class UtilProvider
{
	public static Map<Integer, BaseCategoryUtil> utils = new HashMap<Integer, BaseCategoryUtil>();
	
	static {
		utils.put(CategorizerConfig.CAR_TYPE, new CarUtil());
		utils.put(CategorizerConfig.PRODUCT_TYPE, new ProductUtil());
		utils.put(CategorizerConfig.HOTEL_TYPE, new OtelUtil());
		utils.put(CategorizerConfig.ESTATE_TYPE, new EstateUtil());
		//utils.put(CategorizerConfig.DAILY_OPPORTUNITY_TYPE, new DailyOpportunityUtil());
	}
	
	public static BaseCategoryUtil getDomainUtil(int cat)
	{
		return utils.get(cat);
	}
}
