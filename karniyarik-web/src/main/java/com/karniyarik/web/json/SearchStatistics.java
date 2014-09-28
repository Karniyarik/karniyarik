package com.karniyarik.web.json;

import java.util.ArrayList;
import java.util.List;

import com.karniyarik.web.util.SearchLogUtil;

public class SearchStatistics
{
	private final int			catType;
	private List<LinkedLabel>	topSearches = new ArrayList<LinkedLabel>();
	private List<LinkedLabel>	lastSearches = new ArrayList<LinkedLabel>();
	
	public SearchStatistics(int catType)
	{
		this.catType = catType;
	}
	
	public void init()
	{
		SearchStatLoader loader = new SearchStatLoader(this);
		new Thread(loader).start();
	}

	public int getCatType()
	{
		return catType;
	}

	public List<LinkedLabel> getTopSearches()
	{
		return topSearches;
	}

	public void setTopSearches(List<LinkedLabel> topSearches)
	{
		this.topSearches = topSearches;
	}

	public List<LinkedLabel> getLastSearches()
	{
		return lastSearches;
	}

	public void setLastSearches(List<LinkedLabel> lastSearches)
	{
		this.lastSearches = lastSearches;
	}
	
	class SearchStatLoader implements Runnable	
	{
		private SearchStatistics stat;
		public SearchStatLoader(SearchStatistics stat) {
			this.stat = stat;
		}
		@Override
		public void run() {
			try {
				stat.setLastSearches(new SearchLogUtil().getLastSearches(stat.getCatType()));
				stat.setTopSearches(new SearchLogUtil().getTopSearches(stat.getCatType()));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	@Override
	public void searchOccured(String query)
	{
		if (!lastSearchesMap.containsKey(query.toLowerCase())) {
			lastSearchesMap
		}
		if (catType == this.catType && !lastSearchResultCache.contains(query.toLowerCase()))
		{
			try
			{
				lastSearchResults.add(0, new LinkedLabel(query, URLEncoder.encode(query, StringUtil.DEFAULT_ENCODING)));
				lastSearchResultCache.add(query.toLowerCase());

				if (lastSearchResults.size() >= SearchLogger.MAX_LAST_SEARCHES)
				{
					lastSearchResults.remove(lastSearchResults.size() - 1);
					lastSearchResultCache.remove(lastSearchResultCache.size() - 1);
				}
			}
			catch (UnsupportedEncodingException e)
			{
				// do nothing
			}
		}
	}

	public List<LinkedLabel> getLastSearches()
	{
		if (lastSearchResults.isEmpty())
		{

			List<String> lastSearches = SearchLogger.getLastSearches(catType);

			for (String searchStr : lastSearches)
			{
				try
				{
					lastSearchResults.add(new LinkedLabel(searchStr, URLEncoder.encode(searchStr, StringUtil.DEFAULT_ENCODING)));
					lastSearchResultCache.add(searchStr.toLowerCase());
				}
				catch (UnsupportedEncodingException e)
				{
					// do nothing
				}
			}
		}

		return lastSearchResults;
	}

	public List<LinkedLabel> getTopSearches()
	{
		// TODO: this block must be thread safe
		if (topSearchCount.incrementAndGet() == 500)
		{
			topSearchResults = searchLogUtil.getTopSearches(catType);
			topSearchCount.set(0);
		}

		return topSearchResults;
	}
	*/
}
