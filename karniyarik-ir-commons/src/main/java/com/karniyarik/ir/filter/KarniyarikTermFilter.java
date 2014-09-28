package com.karniyarik.ir.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.TermsFilter;
import org.apache.solr.client.solrj.util.ClientUtils;

public class KarniyarikTermFilter implements KarniyarikFilterr
{
	private String termValue = null;
	private String attrName = null;
	
	public KarniyarikTermFilter(String attrName, String termValue)
	{
		this.termValue = termValue;
		this.attrName = attrName;
	}

	@Override
	public Filter getLuceneFilter()
	{
		TermsFilter aTermsFilter = new TermsFilter();
		aTermsFilter.addTerm(new Term(attrName, termValue));
		return new CachingWrapperFilter(aTermsFilter);
	}
	
	@Override
	public String getSolrFilter() {
		StringBuffer buff = new StringBuffer();
		buff.append(attrName);
		buff.append(":");
		String value = ClientUtils.escapeQueryChars(termValue);
		if(value.equals("NOT") || value.equals("AND") || value.equals("OR"))
		{
			value = "\"" + value + "\"";
		}
		buff.append(value);
		
		return buff.toString();
	}
	
	@Override
	public List<String> getSolrFilterList() {
		List<String> result = new ArrayList<String>();
		result.add(getSolrFilter());
		return result;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(attrName);
		buffer.append(" filter: ");
		buffer.append(termValue);
		return buffer.toString();
	}
}
