package com.karniyarik.common.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;

public class SponsoredProduct extends Product
{
	private Date startDate = null;
	private Date endDate = null;
	private Date orderDate = null;

	public SponsoredProduct()
	{
		super();
	}

	public SponsoredProduct(String json)
	{
		super();
		unmarshall(json);
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public Date getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(Date orderDate)
	{
		this.orderDate = orderDate;
	}
	
	public void unmarshall(JSONObject jsonObj)
	{
		try
		{
			SiteConfig siteConf = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(jsonObj.getString("site"));
			
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			
			setName(jsonObj.getString("productName"));
			setBrand(jsonObj.getString("brand"));
			setBreadcrumb(jsonObj.getString("keywords"));
			setPrice(jsonObj.getDouble("price"));
			setPriceCurrency(jsonObj.getString("kurrency"));
			//setSourceURL(jsonObj.getString("sourceURL"));
			setLink(jsonObj.getString("productURL"));
			setSourceName(siteConf.getSiteName());
			setSourceURL(siteConf.getUrl());
			setImageURL(jsonObj.getString("imageURL"));
			//properties(new ArrayList<ProductProperty>();
			//category(jsonObj.getString("");
			setStartDate(df.parse(jsonObj.getString("startDate"))); 
			setEndDate(df.parse(jsonObj.getString("endDate")));
			setLastFetchDate(df.parse(jsonObj.getString("orderDate")));
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot unmarshall product", e);	
		}				
	}
	
	public void unmarshall(String jsonString)
	{
		try
		{
			JSONObject obj = new JSONObject(jsonString);
			unmarshall(obj);
		}
		catch (JSONException e)
		{
			throw new RuntimeException("Cannot unmarshall product", e);
		}
	}

}
