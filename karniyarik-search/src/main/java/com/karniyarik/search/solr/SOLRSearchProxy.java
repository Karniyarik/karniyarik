package com.karniyarik.search.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.util.SimpleOrderedMap;

import com.karniyarik.common.config.ConfigAttributeCollectType;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.filter.KarniyarikBooleanFilter;
import com.karniyarik.ir.filter.KarniyarikFilterr;
import com.karniyarik.ir.filter.KarniyarikSort;
import com.karniyarik.search.searcher.query.Query;

public class SOLRSearchProxy extends SOLRBaseProxy {
	private CategoryConfig categoryConfig = null;
	
	public SOLRSearchProxy(CategoryConfig categoryConfig, int timeOut) {
		super(categoryConfig.getCoreName(), timeOut);
		this.categoryConfig = categoryConfig;
	}
	
	public long getSiteDocsCount(String sitename)
	{
		long result = 0;
		SolrQuery solrQuery = new  SolrQuery();
		solrQuery.setQuery(SearchConstants.STORE+ ":" + sitename);
		solrQuery.setQueryType("standard");
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
			result = response.getResults().getNumFound();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public long getIndexSize()
	{
		long result = 0;
		SolrQuery solrQuery = new  SolrQuery();
		solrQuery.setQuery("*");
		solrQuery.setQueryType("standard");
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
			result = response.getResults().getNumFound();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public QueryResponse getProduct(String url)
	{
		url = ClientUtils.escapeQueryChars(url);
	    
		SolrQuery solrQuery = new  SolrQuery().
	    
        setQuery(SearchConstants.PRODUCT_URL + ":" + url).
        setQueryType("standard");
		setFields(solrQuery);
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public QueryResponse getMoreLikeThis(String url, int size)
	{
		url = ClientUtils.escapeQueryChars(url);
		SolrQuery solrQuery = new  SolrQuery().
        setQuery(SearchConstants.PRODUCT_URL + ":" + url).
		setQueryType("/mlt");
		
		solrQuery.set("mlt.match.include", false).
		set("mlt.mindf", 1).
		set("mlt.mintf", 1).
		set("mlt.count", size).		
		set("mlt.fl", SearchConstants.KEYWORDS+","+SearchConstants.NAME+","+SearchConstants.TAGS).
		set("mlt.interestingTerms", "");
		
		QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public QueryResponse search(Query query)
	{
		String queryStr = ClientUtils.escapeQueryChars(query.getLuceneQuery());
		int startIndex = (query.getPageNumber() - 1) * query.getPageSize();
		
		SolrQuery solrQuery = new  SolrQuery().
		setQuery(queryStr).
        setQueryType("dismax").
        setRows(query.getPageSize()).
        setStart(startIndex);
        
        setFields(solrQuery);
        setFacets(solrQuery);
		setSpellCheck(solrQuery);
        setHighlighting(solrQuery);
		
        setVerticalFilter(query.getCategory(), solrQuery);
		addCategoryFilters(query, solrQuery);

		//do this for clustered elements
		if(query.getSort() != null && query.getSort() != KarniyarikSort.RELEVANCE)
		{
			solrQuery.addSortField(query.getSort().getField(), query.getSort().getOrder());	
		}
		
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return response;		
	}

	private void setSpellCheck(SolrQuery solrQuery) {
		solrQuery.set("spellcheck", true);
	}

	private void setFields(SolrQuery solrQuery) {
		for(String field: default_fields)
		{
			solrQuery.addField(field);	
		}
	
		for(String field: categoryConfig.getPropertyMap().keySet())
		{
			solrQuery.addField(field);
		}
	}
	
	private void setFacets(SolrQuery solrQuery) {
		solrQuery.setFacet(true).
        setFacetMinCount(1).
        setFacetSort(FacetParams.FACET_SORT_INDEX);
		solrQuery.set("stats", true);
		
		for(String field : default_facet_fields)
		{			
			solrQuery.addFacetField(field);
		}

		for(String field : default_stat_fields)
		{
			solrQuery.add("stats.field", field);
		}

		for(String field: categoryConfig.getPropertyMap().keySet())
		{
			CategoryPropertyConfig propConfig = categoryConfig.getPropertyMap().get(field);
			if(propConfig.getCollectType() == ConfigAttributeCollectType.Cluster)
			{
				solrQuery.add("stats.field", field);
			}
			else if(propConfig.getCollectType() == ConfigAttributeCollectType.Occurence)
			{
				solrQuery.addFacetField(field);	
			}
		}
	}

	private void setHighlighting(SolrQuery solrQuery) {
//		  solrQuery.setHighlight(true).
//        setHighlightSnippets(2).
//        setHighlightFragsize(3).
//        setHighlightSimplePost("</b>").
//        setHighlightSimplePre("<b>").
//        setIncludeScore(true).
//        set("hl.fl", SearchConstants.NAME);
	}

	private void addCategoryFilters(Query query, SolrQuery solrQuery) {
		if(query.getCategorySpecificFilterList() != null)
		{
			for(KarniyarikFilterr filter: query.getCategorySpecificFilterList())
			{
				String fq = filter.getSolrFilter();
				solrQuery.addFilterQuery(fq);
			}
		}
	}
	
	public QueryResponse searchSponsored(Query query, List<KarniyarikFilterr> sponsoredSites)
	{
		String queryStr = ClientUtils.escapeQueryChars(query.getLuceneQuery());
		
		SolrQuery solrQuery = new  SolrQuery().
		setQuery(queryStr).
        setQueryType("dismax").
        setRows(100).
        setFields("*");
		
        setHighlighting(solrQuery);
        setVerticalFilter(query.getCategory(), solrQuery);
		addCategoryFilters(query, solrQuery);
		addSponsoredFilters(sponsoredSites, solrQuery);
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return response;		
	}

	private void setVerticalFilter(String category, SolrQuery solrQuery) {
		solrQuery.addFilterQuery(SearchConstants.VERTICAL + ":" + category);
	}

	private void addSponsoredFilters(List<KarniyarikFilterr> sponsoredSites,
			SolrQuery solrQuery) {
		if(sponsoredSites != null)
		{
			KarniyarikBooleanFilter bFilter = new KarniyarikBooleanFilter();
			
			for(KarniyarikFilterr filter: sponsoredSites)
			{
				bFilter.addFilter(filter); 
			}
			String fq = bFilter.getSolrFilter();
			solrQuery.addFilterQuery(fq);

		}
	}
	
	public List<SOLRCluster> getClusters(Query query, int maxSampleResults, int maxClusterCount)
	{
		List<SOLRCluster> result = new ArrayList<SOLRCluster>();
		
		String queryStr = ClientUtils.escapeQueryChars(query.getLuceneQuery());
		
		SolrQuery solrQuery = new  SolrQuery().
		setQuery(queryStr).
		setRows(maxSampleResults).
	    setQueryType("/clustering").
	    setFields(SearchConstants.ID);
		
	    solrQuery.set("LingoClusteringAlgorithm.desiredClusterCountBase", maxClusterCount);
	    solrQuery.set("carrot.numDescriptions", 4);
		
		addCategoryFilters(query, solrQuery);
	    setVerticalFilter(query.getCategory(), solrQuery);
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
		} catch (SolrServerException e) {
			//e.printStackTrace();
		}
		
		if(response != null)
		{
			Object object = response.getResponse().get("clusters");
			if(object != null && object instanceof List)
			{
				List clusterList = (List) object;
				for(Object clusterObj: clusterList)
				{
					SimpleOrderedMap clusterInfo = (SimpleOrderedMap) clusterObj;
					SOLRCluster cluster = new SOLRCluster();
					cluster.setName((String)((List)clusterInfo.get("labels")).get(0));
					cluster.setCount(((List)clusterInfo.get("docs")).size());
					result.add(cluster);
				}
			}
		}
		
		return result;
	}
	
	public void optimize() {
		try{
			getServer().optimize();
		}catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public void commit() {
		try
		{
			getServer().commit();
		}
		catch (Throwable e) 
		{
			getServer().getBaseURL();
			throw new RuntimeException(e);
		}
	}
}
