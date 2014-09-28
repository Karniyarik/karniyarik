package com.karniyarik.web.util;

import javax.servlet.http.HttpServletRequest;

import com.karniyarik.web.json.SearchResult;
import com.karniyarik.web.json.SearchResultConverter;

public class SearchResultRequestWrapper
{
	private SearchResult searchResult = null;
	private HttpServletRequest request = null;
	
	private SearchResultRequestWrapper(HttpServletRequest request)
	{
		this.request = request;
		this.searchResult = new SearchResultConverter(request).getSearchResult();
	}

	public SearchResult getSearchResult()
	{
		return searchResult;
	}
	
	public HttpServletRequest getRequest()
	{
		return request;
	}

	public static SearchResultRequestWrapper getInstance(HttpServletRequest request)
	{
		SearchResultRequestWrapper wrapper = (SearchResultRequestWrapper) request.getAttribute("searchresultwrapper");
		
		if(wrapper == null)
		{
			wrapper = new SearchResultRequestWrapper(request);
			request.setAttribute("searchresultwrapper", wrapper);
		}
		
		return wrapper;
	}
}
