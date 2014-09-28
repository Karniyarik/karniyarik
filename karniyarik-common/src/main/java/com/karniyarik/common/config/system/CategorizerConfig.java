package com.karniyarik.common.config.system;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;
import com.karniyarik.common.util.StringUtil;

@SuppressWarnings("serial")
public class CategorizerConfig extends ConfigurationBase
{
	public static final String				CAR					= "Araba";
	public static final String				PRODUCT				= "Urun";
	public static final String				HOTEL				= "Otel";
	public static final String				DAILY_OPPORTUNITY	= "GunlukFirsat";
	public static final String				CITY_DEAL			= "Sehir-Firsati";
	public static final String				ESTATE				= "Emlak";
	public static final int					NONE				= 0;
	public static final int					PRODUCT_TYPE		= 1;
	public static final int					CAR_TYPE			= 2;
	public static final int					ESTATE_TYPE			= 3;
	public static final int					HOTEL_TYPE			= 4;	
	public static final int					CITY_DEAL_TYPE		= 9;
	public static final int					DAILY_OPPORTUNITY_TYPE	= 10;

	private Map<String, CategoryConfig>	categoryConfigMap	= new HashMap<String, CategoryConfig>();

	public CategorizerConfig() throws Exception
	{
		super(ConfigurationURLUtil.getCategorizerConfig());
		loadCategoryConfigList();
	}

	@SuppressWarnings("unchecked")
	private void loadCategoryConfigList()
	{
		CategoryConfig aConfig = null;

		List aList = getList("categories.category[@name]");

		String aPath = null;
		for (int anIndex = 0; anIndex < aList.size(); anIndex++)
		{
			aPath = "categories.category(" + anIndex + ")";

			aConfig = new CategoryConfig(configurationAt(aPath));

			categoryConfigMap.put(aConfig.getName(), aConfig);
		}
	}

	public Map<String, CategoryConfig> getCategoryConfigMap()
	{
		return categoryConfigMap;
	}

	public Collection<CategoryConfig> getCategoryConfigList()
	{
		return categoryConfigMap.values();
	}

	public CategoryConfig getCategoryConfig(String name)
	{
		return categoryConfigMap.get(name);
	}

	public void setCategoryConfigMap(Map<String, CategoryConfig> categoryConfigMap)
	{
		this.categoryConfigMap = categoryConfigMap;
	}

	public Float getCategoryNameBoost()
	{
		return getFloat("categorizer.category[@name]", 1);
	}

	public Float getCategoryKeywordDefaultBoost()
	{
		return getFloat("categorizer.category[@keyword]", 1);
	}

	public Float getCategoryBrandBoost()
	{
		return getFloat("categorizer.category[@brand]", 1);
	}

	public Float getProductNameBoost()
	{
		return getFloat("categorizer.product[@name]", 1);
	}

	public Float getProductBreadCrumbBoost()
	{
		return getFloat("categorizer.product[@breadcrumb]", 1);
	}

	public Float getProductBrandBoost()
	{
		return getFloat("categorizer.product[@brand]", 1);
	}

	public String getModelPath()
	{
		return getString("categorizer.model[@path]", "");
	}

	public Float getNegativeKeywordBoost()
	{
		return getFloat("categorizer.category[@negative]", 1);
	}

	public static int getCategoryType(String categoryStr)
	{
		int type = PRODUCT_TYPE;
		if (StringUtils.isNotBlank(categoryStr))
		{
			if (categoryStr.equalsIgnoreCase(CAR))
			{
				type = CAR_TYPE;
			}
			else if (categoryStr.equalsIgnoreCase(HOTEL))
			{
				type = HOTEL_TYPE;
			}
			else if (categoryStr.equalsIgnoreCase(DAILY_OPPORTUNITY))
			{
				type = DAILY_OPPORTUNITY_TYPE;
			}
			else if (categoryStr.equalsIgnoreCase(ESTATE))
			{
				type = ESTATE_TYPE;
			}
			else if (categoryStr.equalsIgnoreCase(CITY_DEAL))
			{
				type = CITY_DEAL_TYPE;
			}

		}
		return type;
	}

	public static String getCategoryString(int catType)
	{
		String type = PRODUCT;
		if (catType == CAR_TYPE)
		{
			type = CAR;
		}
		else if (catType == HOTEL_TYPE)
		{
			type = HOTEL;
		}
		else if (catType == DAILY_OPPORTUNITY_TYPE)
		{
			type = DAILY_OPPORTUNITY;
		}
		else if (catType == ESTATE_TYPE)
		{
			type = ESTATE;
		}
		else if (catType == CITY_DEAL_TYPE)
		{
			type = CITY_DEAL;
		}

		return type;
	}

	public static String lowercaseCategoryString(String categoryStr)
	{
		String type = "urun";
		if (StringUtils.isNotBlank(categoryStr))
		{
			categoryStr = StringUtil.convertTurkishCharacter(categoryStr);

			if (categoryStr.equalsIgnoreCase(CAR))
			{
				return CAR.toLowerCase();
			}
			else if (categoryStr.equalsIgnoreCase(HOTEL))
			{
				return HOTEL.toLowerCase();
			}
			else if (categoryStr.equalsIgnoreCase(DAILY_OPPORTUNITY))
			{
				return DAILY_OPPORTUNITY.toLowerCase();
			}			
			else if (categoryStr.equalsIgnoreCase(ESTATE))
			{
				return ESTATE.toLowerCase();
			}
			else if (categoryStr.equalsIgnoreCase(CITY_DEAL))
			{
				return CITY_DEAL.toLowerCase();
			}
		}
		return type;
	}
}