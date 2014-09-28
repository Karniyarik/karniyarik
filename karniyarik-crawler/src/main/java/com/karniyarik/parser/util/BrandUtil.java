package com.karniyarik.parser.util;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.brands.BrandService;
import com.karniyarik.brands.BrandServiceImpl;
import com.karniyarik.parser.logger.ParserBrandsLogger;

public class BrandUtil
{

	public static String getBrand(String singleBrand, String brandText, String productName, String breadcrumb, ParserBrandsLogger brandsLogger)
	{
		String brand = "";
		if (StringUtils.isNotBlank(singleBrand))
		{
			// Some sites like pasabahce and dermaderm
			// provide products from only a single brand.
			// Such sites have a single brand defined by
			// the configuration. So if all products from
			// the site has a single brand set it directly
			// and skip parsing, resolving steps.
			brand = singleBrand;
		}
		else
		{
			BrandService aInstance = BrandServiceImpl.getInstance();

			// if brand is parsed from web page
			// get actual brand for it from brand service
			if (StringUtils.isNotBlank(brandText))
			{
				brand = aInstance.getActualBrand(brandText);

				// before changing the parser brand name
				// with actual brand log brand resolving results
				if (aInstance.isBrandRecognized(brand) && brandsLogger != null)
				{
					brandsLogger.logMatchedBrand(brandText, brand);
				}
				else
				{
					// if can not directly find actual brand
					// try to resolve a brand from brand text
					brand = aInstance.resolveBrand(brandText);

					if (brandsLogger != null)
					{
						brandsLogger.logNotMatchedBrand(brandText);

						if (aInstance.isBrandRecognized(brand))
						{
							brandsLogger.logResolvedFromBrand(brandText, brand);
						}
						else
						{
							brandsLogger.logNotResolvedFromBrand(brandText, brand);
						}
					}
				}
			}
			else
			{
				// if brand can not be parsed from web page
				// try to resolve brand from product name and
				// breadcrumb
				String brandString = productName;

				if (StringUtils.isNotBlank(breadcrumb))
				{
					brandString = brandString + " " + breadcrumb;
				}

				brand = aInstance.resolveBrand(brandString);

				if (brandsLogger != null)
				{
					if (aInstance.isBrandRecognized(brand))
					{
						brandsLogger.logResolvedFromProductName(brandString, brand);
					}
					else
					{
						brandsLogger.logNotResolvedFromProductName(brandString);
					}
				}
			}
		}

		return brand;
	}

}