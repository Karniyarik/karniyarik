package com.karniyarik.web.json;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.common.util.StringUtil;

public class SearchStatisticsTask implements Runnable
{
	private final String			query;
	private final SearchStatistics	searchLog;

	public SearchStatisticsTask(String query, SearchStatistics searchLog)
	{
		this.query = query;
		this.searchLog = searchLog;
	}

	@Override
	public void run()
	{
		List<LinkedLabel> newLastSearches = new ArrayList<LinkedLabel>();
		try
		{
			newLastSearches.add(new LinkedLabel(query, URLEncoder.encode(query, StringUtil.DEFAULT_ENCODING)));
		}
		catch (UnsupportedEncodingException e)
		{
			// this exception will not occur
		}

		boolean uniqueQuery = true;
		for (LinkedLabel linkedLabel : searchLog.getLastSearches())
		{
			if (linkedLabel.getOriginalValue().toLowerCase().equals(query.toLowerCase()))
			{
				uniqueQuery = false;
			}
			else
			{
				newLastSearches.add(linkedLabel);
			}
		}

		if (uniqueQuery && (newLastSearches.size() > StatisticsWebServiceUtil.MAX_LAST_SEARCHES))
		{
			newLastSearches.remove(newLastSearches.size() - 1);
		}

		searchLog.setLastSearches(newLastSearches);

	}
}
