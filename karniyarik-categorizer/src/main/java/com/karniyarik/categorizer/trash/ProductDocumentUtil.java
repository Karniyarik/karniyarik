package com.karniyarik.categorizer.trash;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;

import com.karniyarik.common.config.ConfigAttributeType;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.ir.DoubleField;
import com.karniyarik.ir.SearchConstants;

public class ProductDocumentUtil
{
	private static DecimalFormat	mDecimalFormat	= new DecimalFormat(
															"###,###,###.00");

	public static Product prepareProduct(Document doc, CategoryConfig categoryConfig)
	{
		Product product = null;

		if (doc != null)
		{
			product = new Product();

			product.setLink(doc.get(SearchConstants.PRODUCT_URL));
			product.setName(doc.get(SearchConstants.NAME));
			product.setBrand(doc.get(SearchConstants.BRAND));
			product.setPrice(DoubleField.getDouble(doc
					.get(SearchConstants.PRICE)));
			product.setPriceCurrency(doc.get(SearchConstants.CURRENCY));
			product.setBreadcrumb(doc.get(SearchConstants.BREADCRUMB));
			product.setImageURL(doc.get(SearchConstants.IMAGE_URL));
			product.setCategory(categoryConfig.getName());

			try
			{
				product.setLastFetchDate(DateTools.stringToDate(doc
						.get(SearchConstants.LAST_FETCH_DATE)));
			}
			catch (ParseException e)
			{
				// do nothing
				// ProductResult performs null check on this field
			}

			product.setSourceURL(doc.get(SearchConstants.STORE_URL));
			product.setSourceName(doc.get(SearchConstants.STORE));

			String propertyName;
			String propertyValue;
			List<ProductProperty> propertyList = new ArrayList<ProductProperty>();
			for (CategoryPropertyConfig categoryPropertyConfig : categoryConfig
					.getPropertyMap().values())
			{
				propertyName = categoryPropertyConfig.getName();
				propertyValue = doc.get(propertyName);

				if (StringUtils.isNotBlank(propertyValue))
				{
					propertyList.add(new ProductProperty(propertyName,
							getCategoryPropertyValueStr(propertyName, propertyValue, categoryConfig)));
				}
			}
			product.setProperties(propertyList);
		}

		return product;
	}

	public static String getCategoryPropertyString(String name, String value,
			CategoryConfig categoryConfig)
	{
		CategoryPropertyConfig propertyConfig = categoryConfig.getPropertyMap()
				.get(name);

		if (propertyConfig.getType() == ConfigAttributeType.Double)
		{
			try
			{
				return DoubleField.getString(Double.parseDouble(value));
			}
			catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				return DoubleField.getString(0);
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Integer)
		{
			try
			{
				return DoubleField.getString(Integer.parseInt(value));
			}
			catch (NumberFormatException e)
			{
				return DoubleField.getString(0);
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Date)
		{
			throw new NotImplementedException(
					"Date filter typew is not implemented!!!");
		}
		else
		{
			return (String) value;
		}
	}

	private static String getCategoryPropertyValueStr(String name,
			String value, CategoryConfig categoryConfig)
	{
		CategoryPropertyConfig propertyConfig = categoryConfig.getPropertyMap()
				.get(name);

		if (propertyConfig.getType() == ConfigAttributeType.Double)
		{
			try
			{
				return mDecimalFormat.format(Double.parseDouble(value));
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
				return Integer.toString(((Double) Double.parseDouble(value))
						.intValue());
			}
			catch (NumberFormatException e)
			{
				return "";
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Date)
		{
			throw new NotImplementedException(
					"Date filter typew is not implemented!!!");
		}
		else
		{
			return (String) value;
		}
	}
	
	public static String preapreProductKeywords(Product product) {

		StringBuilder sb = new StringBuilder();

		sb.append(product.getName() + " ");
		sb.append(product.getBrand() + " ");
		sb.append(product.getBreadcrumb() + " ");
		sb.append(Product.getPropertyKeywords(product.getPropertyString()));

		return sb.toString();
	}


}
