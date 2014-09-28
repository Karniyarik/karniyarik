package com.karniyarik.ir.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanFilter;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilterClause;
import org.apache.lucene.search.BooleanClause.Occur;

public class KarniyarikBooleanFilter implements KarniyarikFilterr
{
	private List<KarniyarikFilterr>	filterList	= new ArrayList<KarniyarikFilterr>();
	private Occur					occur		= Occur.MUST;

	public KarniyarikBooleanFilter()
	{

	}

	public KarniyarikBooleanFilter(KarniyarikFilterr aFilter1,
			KarniyarikFilterr aFilter2)
	{
		if (aFilter1 != null & aFilter2 != null)
		{
			filterList.add(aFilter1);
			filterList.add(aFilter2);
		}
		else
		{
			throw new RuntimeException(
					"Both filters must be present, one of the filters is null.");
		}
	}

	public void addFilter(KarniyarikFilterr filter)
	{
		filterList.add(filter);
	}

	public void setOccur(Occur occur)
	{
		this.occur = occur;
	}

	@Override
	public String getSolrFilter() {
		List<String> filterStr = new ArrayList<String>();
		for(KarniyarikFilterr filter: filterList)
		{
			filterStr.add(filter.getSolrFilter());
		}
		return StringUtils.join(filterStr, " OR ");
	}
	
	@Override
	public List<String> getSolrFilterList() {
		List<String> result = new ArrayList<String>();
		for(KarniyarikFilterr filter: filterList)
		{
			result.add(filter.toString());
		}
		return result;
	}
	
	@Override
	public Filter getLuceneFilter()
	{
		BooleanFilter aBooleanFilter = new BooleanFilter();

		for (KarniyarikFilterr filter : filterList)
		{
			FilterClause clause = new FilterClause(filter.getLuceneFilter(),
					occur);
			aBooleanFilter.add(clause);
		}

		return new CachingWrapperFilter(aBooleanFilter);
	}
}
