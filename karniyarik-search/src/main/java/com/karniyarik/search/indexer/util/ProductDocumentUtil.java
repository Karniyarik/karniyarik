package com.karniyarik.search.indexer.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.NotImplementedException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import com.karniyarik.common.config.ConfigAttributeType;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.crawler.util.URLManager;
import com.karniyarik.ir.SearchConstants;

public class ProductDocumentUtil
{
	private static DecimalFormat	mDecimalFormat	= new DecimalFormat("###,###,###.00");

	public static Product prepareProduct(SolrDocument solrDocument, CategoryConfig categoryConfig)
	{
		Product product = null;

		if (solrDocument != null)
		{
			product = new Product();
			product.setLink((String)solrDocument.get(SearchConstants.PRODUCT_URL));
			product.setName((String)solrDocument.get(SearchConstants.NAME));
			product.setHighlightedName(product.getName());
			product.setBrand((String)solrDocument.get(SearchConstants.BRAND));
			product.setPrice((Double)solrDocument.get(SearchConstants.PRICE));
			product.setPriceAlternate((Double)solrDocument.get(SearchConstants.PRICE_ALTERNATE));
			product.setPriceCurrency((String)solrDocument.get(SearchConstants.CURRENCY));
			product.setBreadcrumb((String)solrDocument.get(SearchConstants.BREADCRUMB));
			product.setImageURL((String)solrDocument.get(SearchConstants.IMAGE_URL));
			product.setCategory((String)categoryConfig.getName());
			product.setLastFetchDate((Date)solrDocument.get(SearchConstants.LAST_FETCH_DATE));
			product.setSourceURL((String)solrDocument.get(SearchConstants.STORE_URL));
			product.setSourceName((String)solrDocument.get(SearchConstants.STORE));
			product.setId((String)solrDocument.get(SearchConstants.ID));
			product.setUuid((String)solrDocument.get(SearchConstants.UUID));
			
			String propertyName;
			Object propertyValue;
			List<ProductProperty> propertyList = new ArrayList<ProductProperty>();
			for (CategoryPropertyConfig categoryPropertyConfig : categoryConfig.getPropertyMap().values())
			{
				propertyName = categoryPropertyConfig.getName();
				propertyValue = solrDocument.get(propertyName);

				if (propertyValue != null)
				{
					propertyList.add(new ProductProperty(propertyName, getCategoryPropertyValueStr(propertyName, propertyValue, categoryConfig)));
				}
			}
			product.setProperties(propertyList);
		}

		return product;
	}

	public static SolrInputDocument prepareDocument(Product product, CategoryConfig categoryConfig)
	{
		SolrInputDocument aDoc = new SolrInputDocument();
		
		aDoc.addField(SearchConstants.NAME, product.getName());
		aDoc.addField(SearchConstants.BRAND, product.getBrand());

		aDoc.addField(SearchConstants.STORE, product.getSourceName());
		aDoc.addField(SearchConstants.BREADCRUMB, product.getBreadcrumb());
		aDoc.addField(SearchConstants.PRICE, product.getPrice());
		aDoc.addField(SearchConstants.PRICE_ALTERNATE, product.getPriceAlternate());
		aDoc.addField(SearchConstants.CURRENCY, product.getPriceCurrency());
		aDoc.addField(SearchConstants.STORE_URL, product.getSourceURL());
		URLManager urlManager = new URLManager(product.getSourceURL(), product.getSourceName());
		aDoc.addField(SearchConstants.PRODUCT_URL, urlManager.constructURL(product.getLink()));
		urlManager = null;
		aDoc.addField(SearchConstants.LAST_FETCH_DATE, product.getLastFetchDate());
		aDoc.addField(SearchConstants.IMAGE_URL, product.getImageURL());
		
		aDoc.addField(SearchConstants.ID, UUID.randomUUID().toString());
		aDoc.addField(SearchConstants.VERTICAL, categoryConfig.getName());
		
		for (ProductProperty productProperty : product.getProperties())
		{
			aDoc.addField(productProperty.getName(), getCategoryPropertyValue(productProperty.getName(), productProperty.getValue(), categoryConfig));	
		}

		return aDoc;
	}

	public static Object getCategoryPropertyValue(String name, String value, CategoryConfig categoryConfig)
	{
		CategoryPropertyConfig propertyConfig = categoryConfig.getPropertyMap().get(name);

		if (propertyConfig.getType() == ConfigAttributeType.Double)
		{
			try
			{
				return Double.parseDouble(value);
			}
			catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				return 0d;
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Integer)
		{
			try
			{
				value = StringUtil.removePunctiations(value);
				if(value.indexOf('–') != -1)
				{
					value = value.substring(0, value.indexOf('–'));
				}
				return Integer.parseInt(value);
			}
			catch (NumberFormatException e)
			{
				return 0;
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Date)
		{
			throw new NotImplementedException("Date filter typew is not implemented!!!");
		}
		else
		{
			return (String) value;
		}
	}

	private static String getCategoryPropertyValueStr(String name, Object value, CategoryConfig categoryConfig)
	{
		CategoryPropertyConfig propertyConfig = categoryConfig.getPropertyMap().get(name);

		if (propertyConfig.getType() == ConfigAttributeType.Double)
		{
			try
			{
				return mDecimalFormat.format((Double) value);
			}
			catch (NumberFormatException e)
			{
				return "";
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Integer)
		{
			try
			{
				return Integer.toString((Integer) value);
			}
			catch (NumberFormatException e)
			{
				return "";
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Date)
		{
			throw new NotImplementedException("Date filter typew is not implemented!!!");
		}
		else
		{
			return (String) value;
		}
	}
}
