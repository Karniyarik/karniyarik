package com.karniyarik.search.searcher.logger;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;

import com.karniyarik.ir.SearchConstants;

public class CategorySearchLogIndexer
{
	private IndexReader			indexReader		= null;
	private final String		category;
	private final int			cacheSize;
	private final List<String>	queryList;

	public CategorySearchLogIndexer(String category, IndexReader indexReader, int cacheSize)
	{
		this.category = category;
		this.queryList = new ArrayList<String>();
		this.indexReader = indexReader;
		this.cacheSize = cacheSize;
	}

	public String getCategory()
	{
		return category;
	}

	public int getCacheSize()
	{
		return cacheSize;
	}
	
	public List<String> refreshQueryList()
	{
		List<String> oldQueries = new ArrayList<String>();
		oldQueries.addAll(queryList);
		queryList.clear();
		return oldQueries;
	}

	public void log(String query)
	{
		queryList.add(query);
	}

	public double docFreq(String term)
	{
		double result = 0;
		try
		{
			result = indexReader.docFreq(new Term(SearchConstants.KEYWORDS, term));
		}
		catch (Throwable e)
		{
			getLogger().error("Can not get search log document frequency for term " + term, e);
		}

		return result;
	}

	public boolean shouldSaveCache()
	{
		return queryList.size() >= cacheSize;
	}

	public IndexReader getIndexReader()
	{
		return indexReader;
	}

	public void setIndexReader(IndexReader indexReader)
	{
		this.indexReader = indexReader;
	}
	
	private Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}
}
