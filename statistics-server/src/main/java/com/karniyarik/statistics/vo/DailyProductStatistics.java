package com.karniyarik.statistics.vo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;


public class DailyProductStatistics
{
	private final String url;
	private final String name;
	private final String siteName;
	private final long date;
	private String productCategory;
	private Integer listingViews;
	private Integer listingClicks;
	private Integer sponsorViews;
	private Integer sponsorClicks;
	private Map<String, QueryStatisticLog> queryMap;
	
	public DailyProductStatistics(String url, String name, String siteName, long date)
	{
		this.url = url;
		this.name = name;
		this.siteName = siteName;
		this.productCategory = "";
		this.date = date;
		listingClicks = 0;
		listingViews = 0;
		sponsorClicks = 0;
		sponsorViews = 0;
		queryMap = new HashMap<String, QueryStatisticLog>();
	}

	public long getDate()
	{
		return date;
	}

	public Integer getListingViews()
	{
		return listingViews;
	}

	public void setListingViews(Integer listingViews)
	{
		this.listingViews = listingViews;
	}

	public Integer getListingClicks()
	{
		return listingClicks;
	}

	public void setListingClicks(Integer listingClicks)
	{
		this.listingClicks = listingClicks;
	}

	public Integer getSponsorViews()
	{
		return sponsorViews;
	}

	public void setSponsorViews(Integer sponsorViews)
	{
		this.sponsorViews = sponsorViews;
	}

	public Integer getSponsorClicks()
	{
		return sponsorClicks;
	}

	public void setSponsorClicks(Integer sponsorClicks)
	{
		this.sponsorClicks = sponsorClicks;
	}

	public String getUrl()
	{
		return url;
	}

	public String getName()
	{
		return name;
	}

	public String getSiteName()
	{
		return siteName;
	}
	
	public String getProductCategory()
	{
		return productCategory;
	}
	
	public void setProductCategory(String productCategory)
	{
		this.productCategory = productCategory;
	}

	public QueryStatisticLog getQueryStatisticLog(String query)
	{
		QueryStatisticLog log = queryMap.get(query);
		if (log == null) {
			log = new QueryStatisticLog(query);
			queryMap.put(query, log);
		}
		return log;
	}
	
	public String getQueryStatisticString() {
		JSONArray obj = new JSONArray();
		for (QueryStatisticLog log : queryMap.values())
		{
			obj.put(log.toJSON());
		}
		return obj.toString();
	}
	
	public void setQueryStatisticMap(String json) {
		queryMap = new HashMap<String, QueryStatisticLog>();
		try
		{
			JSONArray jsonArray = new JSONArray(json);
			QueryStatisticLog log = null;
			
			for (int i = 0; i < jsonArray.length(); i++)
			{
				log = QueryStatisticLog.create(jsonArray.getJSONObject(i));
				queryMap.put(log.getQuery(), log);
			}
		}
		catch (JSONException e)
		{
			// will not occur
		}
	}
}
