package com.karniyarik.datafeed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.crawler.util.URLManager;
import com.karniyarik.parser.pojo.Product;
import com.karniyarik.parser.util.BrandUtil;
import com.karniyarik.parser.util.PriceUtil;
import com.karniyarik.recognizer.RecognizerImpl;

public class TabSeparatedDatafeedParser implements DatafeedParser
{

	private final SiteConfig		siteConfig;
	private final URLManager		urlManager;
	private final CategoryConfig	categoryConfig;

	private Map<String, Integer>	indexMap;

	public TabSeparatedDatafeedParser(SiteConfig siteConfig, CategoryConfig categoryConfig, URLManager urlManager)
	{
		this.siteConfig = siteConfig;
		this.categoryConfig = categoryConfig;
		this.urlManager = urlManager;
		this.indexMap = new HashMap<String, Integer>();
	}

	@Override
	public List<Product> parse(String content)
	{
		List<Product> productList = new ArrayList<Product>();
		if (StringUtils.isNotBlank(content))
		{
			Date fetchDate = new Date();
			String[] lines = content.split("\n");
			Product product;

			for (String line : lines)
			{
				product = parseLine(line);
				product.setFetchDate(fetchDate);
				productList.add(product);
			}
		}

		return productList;
	}

	private Product parseLine(String line)
	{
		String[] element = line.split("\t");
		Product product = new Product();
		String url = getValue(element, siteConfig.getDatafeedProductUrlXPath());
		if (StringUtils.isNotBlank(url))
		{
			url = urlManager.breakURL(url);
		}
		product.setUrl(url);

		product.setName(getValue(element, siteConfig.getDatafeedNameXPath()));

		String priceText = getValue(element, siteConfig.getDatafeedPriceXPath());
		String currencyText = getValue(element, siteConfig.getDatafeedCurrencyXPath());
		PriceUtil.setPrice(priceText, currencyText, product);

		product.setBreadcrumb(getValue(element, siteConfig.getDatafeedBreadcrumbXPath()));
		product.setImageUrl(getValue(element, siteConfig.getDatafeedImageElementName()));

		String brandText = getValue(element, siteConfig.getDatafeedBrandXPath());
		product.setBrand(BrandUtil.getBrand(siteConfig.getSingleBrand(), brandText, product.getName(), product.getBreadcrumb(), null));

		List<ProductProperty> properties = new ArrayList<ProductProperty>();
		String propertyValue;
		for (CategoryPropertyConfig propertyConfig : categoryConfig.getPropertyMap().values())
		{
			propertyValue = getValue(element, siteConfig.getDatafeedPropertyXQuery(propertyConfig.getName()));
			
			if(propertyConfig.getName().equalsIgnoreCase("country"))
			{
				propertyValue = RecognizerImpl.getInstance().recognizeCountry(propertyValue);
			}
			
			if(propertyConfig.getName().equalsIgnoreCase("city"))
			{
				propertyValue = RecognizerImpl.getInstance().recognizeCity(propertyValue);				
			}
			
			if (StringUtils.isNotBlank(propertyValue))
			{
				properties.add(new ProductProperty(propertyConfig.getName(), propertyValue));
			}
		}
		product.setProperties(properties);

		return product;
	}

	private String getValue(String[] tokens, String indexStr)
	{
		String value = "";
		if (StringUtils.isNotBlank(indexStr))
		{
			try
			{
				value = tokens[getIndex(indexStr)];
			}
			catch (Throwable e)
			{

			}
		}
		return value;
	}

	private Integer getIndex(String indexStr)
	{
		Integer index = indexMap.get(indexStr);

		if (index == null)
		{
			try
			{
				index = Integer.parseInt(indexStr);
				indexMap.put(indexStr, index);
			}
			catch (Throwable e)
			{
				index = -1;
			}
		}

		return index;
	}

}
