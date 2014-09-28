package com.karniyarik.ir.filter;

import java.util.List;

import org.apache.lucene.search.Filter;

public interface KarniyarikFilterr
{
	Filter getLuceneFilter();
	String getSolrFilter();
	List<String> getSolrFilterList();
}
