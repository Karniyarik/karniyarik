package com.karniyarik.parser.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.karniyarik.common.vo.CurrencyType;
import com.karniyarik.common.vo.ProductProperty;

public class Product implements Cloneable
{
	private String					name			= "";
	private float					price			= 0f;
	private float					priceAlternate	= 0f;
	private String					breadcrumb		= "";
	private String					brand			= "";
	private String					model			= "";
	private String					imageUrl		= "";
	private String					priceCurrency	= CurrencyType.TL.getSymbol();

	private String					url				= "";
	private Date					fetchDate;

	private List<ProductProperty>	properties		= new ArrayList<ProductProperty>();
//	private String					tags 			= "";
	private String					category = "";

	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * At crawling time rank variable is left 0. After all crawling ends ranker
	 * updates products and sets this value.
	 */
	private int						rank			= 0;

	public Product()
	{

	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		name = trim(name, 512);
		this.name = name;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

	public float getPriceAlternate()
	{
		return priceAlternate;
	}

	public void setPriceAlternate(float priceAlternate)
	{
		this.priceAlternate = priceAlternate;
	}

	public String getBreadcrumb()
	{
		return breadcrumb;
	}

	public void setBreadcrumb(String breadcrumb)
	{
		breadcrumb = trim(breadcrumb, 512);
		this.breadcrumb = breadcrumb;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		brand = trim(brand, 256);
		this.brand = brand;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		imageUrl = trim(imageUrl, 256);
		this.imageUrl = imageUrl;
	}

	public List<ProductProperty> getProperties()
	{
		return properties;
	}

	public void setProperties(List<ProductProperty> properties)
	{
		this.properties = properties;
	}

	/**
	 * Do not name this getPropertyString for JSON serialization
	 * 
	 * @return
	 */
	public String createPropertyString()
	{
		StringBuffer buffer = new StringBuffer("");
		int index = 0;
		for (ProductProperty property : getProperties())
		{
			buffer.append(property.getName());
			buffer.append(":");
			buffer.append(property.getValue());
			if (index < (getProperties().size() - 1))
			{
				buffer.append(",");
			}
			index++;
		}

		return trim(buffer.toString(), 1024);
	}

	public String getPriceCurrency()
	{
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency)
	{
		priceCurrency = trim(priceCurrency, 32);
		this.priceCurrency = priceCurrency;
	}

	private String trim(String string, int length)
	{
		if (string != null && string.length() > length)
		{
			string = string.substring(0, length - 1);
		}

		return string;
	}

	public Date getFetchDate()
	{
		return fetchDate;
	}

	public void setFetchDate(Date fetchDate)
	{
		this.fetchDate = fetchDate;
	}

	public int getRank()
	{
		return rank;
	}

	public void setRank(int rank)
	{
		this.rank = rank;
	}
	
//	public String getTags() {
//		return tags;
//	}
//	
//	public void setTags(String tags) {
//		this.tags = tags;
//	}

	/**
	 * Do not use ranks value when calculating hash value. Since it is set after
	 * crawling.
	 */
	@Override
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(getName());
		builder.append(getBrand());
		builder.append(getImageUrl());
		builder.append(getPrice());
//		builder.append(getPriceAlternate());
		builder.append(getPriceCurrency());

		if (getProperties() != null)
		{
			for (ProductProperty property : getProperties())
			{
				builder.append(property.getName());
				builder.append(property.getValue());
			}
		}

		return builder.toHashCode();
	}
	
	public String getModel()
	{
		return model;
	}
	
	public void setModel(String model)
	{
		this.model = model;
	}

}
