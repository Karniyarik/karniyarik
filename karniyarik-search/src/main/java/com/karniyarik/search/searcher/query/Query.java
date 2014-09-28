package com.karniyarik.search.searcher.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.ir.filter.KarniyarikFilterr;
import com.karniyarik.ir.filter.KarniyarikSort;

public class Query
{
	private String							mQueryString				= null;
	private String							originalQuery				= null;
	private String 							mLuceneQuery				= null;
	private KarniyarikSort					mSort						= null;
	private List<KarniyarikFilterr>			categorySpecificFilterList	= new ArrayList<KarniyarikFilterr>();
	private int								mPageNumber					= 0;
	private int								mPageSize					= 0;
	private boolean							useHighLighter				= false;
	private String							apiKey						= "";
	private String							httpAgent					= "";
	private String 								category				= CategorizerConfig.PRODUCT;

	private String preProcessQuery(String queryString) {

		queryString = queryString.replaceAll("/|&", " ");
		queryString = queryString.trim();
		queryString = queryString.replaceAll("\\s+", " ");
		
		return queryString;
	}

	public String getQueryString()
	{
		return mQueryString;
	}

	public void setQueryString(String queryString)
	{
		//km: i hate karniyarik
		originalQuery = queryString;
		mQueryString = preProcessQuery(queryString);
	}

	public String getLuceneQuery()
	{
		return mLuceneQuery;
	}

//	public org.apache.lucene.search.Query getLuceneQuery()
//	{
//		return mLuceneQuery;
//	}

	public void setLuceneQuery(String luceneQuery)
	{
		mLuceneQuery = luceneQuery;
	}

	public KarniyarikSort getSort()
	{
		return mSort;
	}

	public void setSort(KarniyarikSort sort)
	{
		mSort = sort;
	}

	public int getPageNumber()
	{
		return mPageNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		if(pageNumber < 1)
		{
			pageNumber = 1;
		}
		mPageNumber = pageNumber;
	}

	public int getPageSize()
	{
		return mPageSize;
	}

	public void setPageSize(int pageSize)
	{
		mPageSize = pageSize;
	}

	public boolean isUseHighLighter()
	{
		return useHighLighter;
	}

	public void setUseHighLighter(boolean useHighLighter)
	{
		this.useHighLighter = useHighLighter;
	}

	public List<KarniyarikFilterr> getCategorySpecificFilterList()
	{
		return categorySpecificFilterList;
	}
	
	public String getApiKey()
	{
		return apiKey;
	}

	public void setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
	}
	
	public boolean isFromApi() {
		return StringUtils.isNotBlank(apiKey);
	}

	public String getHttpAgent()
	{
		return httpAgent;
	}

	public void setHttpAgent(String httpAgent)
	{
		this.httpAgent = httpAgent;
	}

	public String getOriginalQuery() {
		return originalQuery;
	}
	
	public String  getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
