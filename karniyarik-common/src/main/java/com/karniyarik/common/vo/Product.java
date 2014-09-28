package com.karniyarik.common.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.solr.client.solrj.beans.Field;

public class Product
{
	private String					uuid			= null;
	
	private String					id				= null;
	
	private String					name			= null;
	
	private String					highlightedName	= null;

	private String					brand			= null;
	
	private String					breadcrumb		= null;
	
	private Double					price			= null;
	
	private Double					priceAlternate	= null;
	
	private String					priceCurrency	= null;
	
	private String					sourceURL		= null;
	
	private String					link			= null;
	
	private String					sourceName		= null;
	
	private Date					lastFetchDate	= null;
	
	private String					imageURL		= null;
	
	private String					category		= null;
	
	private List<ProductProperty>	properties		= new ArrayList<ProductProperty>();
//	start_date
//    end_date
//	keywords
//	tags
//	city
//	inStock
//	modelyear
//	enginepower
//	enginevolume
//	km
//	fuel
//	color
//	gear
//	address
//	district
//	warmingtype
//	buildingtype
//	roomcount
//	floorcount
//	salooncount
//	area
//	buildingage
//	district
	
	private float					score			= 0;
	private int						hit				= 0;
	
	private boolean 				sponsored		= false;	

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public float getScore()
	{
		return score;
	}

	public void setScore(float score)
	{
		this.score = score;
	}

	public int getHit()
	{
		return hit;
	}

	public void setHit(int hit)
	{
		this.hit = hit;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getHighlightedName()
	{
		return highlightedName;
	}

	public void setHighlightedName(String highlightedName)
	{
		this.highlightedName = highlightedName;
	}

	public String getBreadcrumb()
	{
		return breadcrumb;
	}

	public void setBreadcrumb(String breadcrumb)
	{
		this.breadcrumb = breadcrumb;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}
	
	public Double getPriceAlternate()
	{
		return priceAlternate;
	}

	public void setPriceAlternate(Double priceAlternate)
	{
		this.priceAlternate = priceAlternate;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getSourceURL()
	{
		return sourceURL;
	}

	public void setSourceURL(String sourceURL)
	{
		this.sourceURL = sourceURL;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getSourceName()
	{
		return sourceName;
	}

	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}

	public Date getLastFetchDate()
	{
		return lastFetchDate;
	}

	public void setLastFetchDate(Date lastFetchDate)
	{
		this.lastFetchDate = lastFetchDate;
	}

	public String getImageURL()
	{
		return imageURL;
	}

	public void setImageURL(String aImageURL)
	{
		imageURL = aImageURL;
	}

	public String getPriceCurrency()
	{
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency)
	{
		this.priceCurrency = priceCurrency;
	}

	public List<ProductProperty> getProperties()
	{
		return properties;
	}

	public void setProperties(List<ProductProperty> properties)
	{
		this.properties = properties;
	}

	public String getPropertyString()
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

		return buffer.toString();
	}
	
	public boolean isSponsored() {
		return sponsored;
	}
	
	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}
	
	public static String getPropertyKeywords(String propertiesStr)
	{
		StringBuffer buffer = new StringBuffer();

		if (StringUtils.isNotBlank(propertiesStr))
		{
			String[] propertyArray = propertiesStr.split(",");
			String[] propertyValues = null;

			for (String propertyStr : propertyArray)
			{
				propertyValues = propertyStr.split(":");
				if (propertyValues != null && propertyValues.length > 1)
				{
					buffer.append(propertyValues[1]);
					buffer.append(" ");
				}
			}
		}

		return buffer.toString().trim();
	}

//	public String getTags() {
//		return tags;
//	}
//	
//	public void setTags(String tags) {
//		this.tags = tags;
//	}
	
	@Override
	public int hashCode()
	{
		HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(getName());
		builder.append(getBrand());
		builder.append(getImageURL());
		builder.append(getPrice());
		builder.append(getPriceAlternate());
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

}
