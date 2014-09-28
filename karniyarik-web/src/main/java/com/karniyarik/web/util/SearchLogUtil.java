package com.karniyarik.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.karniyarik.common.statistics.vo.LastSearches;
import com.karniyarik.common.statistics.vo.TopSearch;
import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.web.json.LinkedLabel;

public class SearchLogUtil
{

	public List<LinkedLabel> getLastSearches(int catType)
	{
		List<LinkedLabel> list = new ArrayList<LinkedLabel>();
		
		LastSearches lastSearches = null;
		
		try 
		{
			lastSearches = StatisticsWebServiceUtil.getLastSearches(catType, StatisticsWebServiceUtil.MAX_LAST_SEARCHES);
		} 
		catch (Throwable ex) 
		{
			ex.printStackTrace();
		}

		if (lastSearches != null && lastSearches.getLastSearchList() != null)
		{
			try
			{
				for (String query : lastSearches.getLastSearchList())
				{
					list.add(new LinkedLabel(query, URLEncoder.encode(query, StringUtil.DEFAULT_ENCODING)));
				}
			}
			catch (UnsupportedEncodingException e)
			{
				// this exception will not occur
			}
		}

		return list;
	}

	public List<LinkedLabel> getTopSearches(int catType)
	{
		TopSearches topSearches = null;
		try 
		{
			topSearches = StatisticsWebServiceUtil.getTopSearches(catType, StatisticsWebServiceUtil.MAX_TOP_SEARCHES);
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
		return getTopSearches(topSearches);
	}
	
	public List<LinkedLabel> getTopSearches(TopSearches topSearches) {
		
		List<LinkedLabel> list = new ArrayList<LinkedLabel>();

		if (topSearches != null && topSearches.getTopSearchList() != null)
		{
			for (TopSearch searchQuery : topSearches.getTopSearchList())
			{
				try
				{
					LinkedLabel label = new LinkedLabel(searchQuery.getQuery(), URLEncoder.encode(searchQuery.getQuery(), StringUtil.DEFAULT_ENCODING));
					label.setCount(searchQuery.getCount());
					list.add(label);
				}
				catch (UnsupportedEncodingException e)
				{
					// this exception will not occur
				}
			}
		}

		return list;
	}

}
