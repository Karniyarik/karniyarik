package com.karniyarik.search.searcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.vo.Product;
import com.karniyarik.search.searcher.query.Query;

public class QueryResult
{
	private long								mTotalHits					= 0;
	private Query								mQuery						= null;
	private String[]							mSuggestedWords				= null;
	//private boolean							mCanUseOr					= false;
	private double								mTimeTaken					= 0;
	private List<Product>						mResults					= null;
	private List<Product>						similarProducts				= null;
	private List<Product>						mSponsoredResults			= null;
	private List<String>						narrowingQueries			= null;
	private Map<String, CrossDomainResult>		otherDomains				= new HashMap<String, CrossDomainResult>();	

	private Map<String, ResultProperty>			categoryResultFilterList	= null;

	public QueryResult()
	{
	}

	public long getTotalHits()
	{
		return mTotalHits;
	}

	public double getTimeTaken()
	{
		return mTimeTaken;
	}

	public List<Product> getResults()
	{
		if (mResults == null)
		{
			mResults = new ArrayList<Product>();
		}

		return mResults;
	}

	public List<Product> getSimilarProducts()
	{
		if (similarProducts == null)
		{
			similarProducts = new ArrayList<Product>();
		}

		return similarProducts;
	}

	
	public Query getQuery()
	{
		return mQuery;
	}

	public String[] getSuggestedWords()
	{
		return mSuggestedWords;
	}

	public void setTotalHits(long totalHits)
	{
		mTotalHits = totalHits;
	}

	public void setQuery(Query query)
	{
		mQuery = query;
	}

	public void setSuggestedWords(String[] suggestedWords)
	{
		mSuggestedWords = suggestedWords;
	}

	public void setTimeTaken(double timeTaken)
	{
		mTimeTaken = timeTaken;
	}

	public void setResults(List<Product> results)
	{
		mResults = results;
	}

	public void setSimilarProducts(List<Product> results)
	{
		similarProducts = results;
	}

//	public boolean isCanUseOr()
//	{
//		return mCanUseOr;
//	}
//
//	void setCanUseOr(boolean aCanUseOr)
//	{
//		mCanUseOr = aCanUseOr;
//	}

	public Map<String, ResultProperty> getCategoryResultFilterList()
	{
		if (categoryResultFilterList == null)
		{
			categoryResultFilterList = new HashMap<String, ResultProperty>();
		}
		return categoryResultFilterList;
	}

	public List<Product> getSponsoredResults()
	{
		if (mSponsoredResults == null)
		{
			mSponsoredResults = new ArrayList<Product>();
		}
		return mSponsoredResults;
	}

	public void setSponsoredResults(List<Product> mSponsoredResults)
	{
		this.mSponsoredResults = mSponsoredResults;
	}

	public List<String> getNarrowingQueries()
	{
		if(narrowingQueries == null)
		{
			narrowingQueries = new ArrayList<String>();
		}
		return narrowingQueries;
	}
	
	public Map<String, CrossDomainResult> getOtherDomains() {
		return otherDomains;
	}
}