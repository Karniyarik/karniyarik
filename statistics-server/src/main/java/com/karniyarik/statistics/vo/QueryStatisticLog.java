package com.karniyarik.statistics.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class QueryStatisticLog
{
	
	private final String query;
	private Integer listingViewCount;
	private Integer listingClickCount;
	private Integer sponsorViewCount;
	private Integer sponsorClickCount;
	
	public QueryStatisticLog(String query)
	{
		this.query = query;
		this.listingViewCount = 0;
		this.listingClickCount = 0;
		this.sponsorViewCount = 0;
		this.sponsorClickCount = 0;
	}
	
	public void incrementListingViewCount() {
		listingViewCount++;
	}
	
	public void incrementListingClickCount() {
		listingClickCount++;
	}
	
	public void incrementSponsorViewCount() {
		sponsorViewCount++;
	}
	
	public void incrementSponsorClickCount() {
		sponsorClickCount++;
	}
	
	public Integer getListingViewCount()
	{
		return listingViewCount;
	}

	public void setListingViewCount(Integer listingViewCount)
	{
		this.listingViewCount = listingViewCount;
	}

	public Integer getListingClickCount()
	{
		return listingClickCount;
	}

	public void setListingClickCount(Integer listingClickCount)
	{
		this.listingClickCount = listingClickCount;
	}

	public Integer getSponsorViewCount()
	{
		return sponsorViewCount;
	}

	public void setSponsorViewCount(Integer sponsorViewCount)
	{
		this.sponsorViewCount = sponsorViewCount;
	}

	public Integer getSponsorClickCount()
	{
		return sponsorClickCount;
	}

	public void setSponsorClickCount(Integer sponsorClickCount)
	{
		this.sponsorClickCount = sponsorClickCount;
	}

	public String getQuery()
	{
		return query;
	}
	
	public String toJSON() {
		JSONObject obj = new JSONObject();
		try
		{
			obj.put("q", query);
			obj.put("lw", listingViewCount);
			obj.put("lc", listingClickCount);
			obj.put("sw", sponsorViewCount);
			obj.put("sc", sponsorClickCount);
		}
		catch (JSONException e)
		{
			// will not happen
		}
		return obj.toString();
	}

	public static QueryStatisticLog create(JSONObject json) {
		QueryStatisticLog log = null;
		try
		{
			log = new QueryStatisticLog(json.getString("q"));
			log.setListingClickCount(json.getInt("lc"));
			log.setListingViewCount(json.getInt("lw"));
			log.setSponsorClickCount(json.getInt("sc"));
			log.setSponsorViewCount(json.getInt("sw"));
		}
		catch (JSONException e)
		{
			// will not happen
		}
		return log;
	}
	
}
