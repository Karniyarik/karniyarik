package com.karniyarik.search.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

import com.karniyarik.ir.SearchConstants;

public class SOLRQuerySearchProxy extends SOLRBaseProxy {
	private List<SolrInputDocument> buffer = new LinkedList<SolrInputDocument>();
	private static int MAX_BUFF_SIZE = 10000;
	
	public SOLRQuerySearchProxy() {
		super(CORE_QUERY);
	}
	
	public void add(SolrInputDocument doc)
	{
		synchronized (this) {
			buffer.add(doc);
			if(buffer.size() > MAX_BUFF_SIZE)
			{
				processBuffer();
			}				
		}
	}

	private void processBuffer() {
		if(buffer.size() > 0)
		{
			try {
				UpdateRequest req = new UpdateRequest();
				req.setAction(ACTION.COMMIT, false, false);
				req.add(buffer);
				req.process(getServer());
				buffer.clear();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void optimize() {
		try {
			getServer().optimize();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} 
	}

	public void commit() {
		try {
			getServer().commit();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} 
	}
	
	public List<String> autocomplete(String query, String category)
	{
		return autocomplete1(query, category);
	}
	
	public void deleteAll(String vertical)
	{
		try {
			getServer().deleteByQuery("vertical:"+vertical);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> autocomplete1(String query, String category)
	{
		List<String> result = new ArrayList<String>();
		
		String queryStr = ClientUtils.escapeQueryChars(query);

		SolrQuery solrQuery = new SolrQuery().
		setQuery("query_auto:" + queryStr + "*").
        setQueryType("standard");
		
		solrQuery.addSortField(SearchConstants.COUNT, ORDER.desc);
		solrQuery.addFilterQuery(SearchConstants.VERTICAL + ":" + category);
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
			
			if(response != null)
			{
				for(SolrDocument doc: response.getResults())
				{
					result.add((String) doc.getFieldValue(SearchConstants.QUERY));
				}
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return result;		
	}
	
	public List<String> getQueries(String category)
	{
		List<String> result = new ArrayList<String>();
		
		SolrQuery solrQuery = new SolrQuery().
		setQuery("*:*").
        setQueryType("standard").
        setRows(5000).
        setStart(0);
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
			
			if(response != null)
			{
				for(SolrDocument doc: response.getResults())
				{
					result.add((String) doc.getFieldValue(SearchConstants.QUERY));
				}
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return result;		
	}
	
	public List<String> autocomplete2(String query, String category)
	{
		List<String> result = new ArrayList<String>();
		
		String queryStr = ClientUtils.escapeQueryChars(query);

		SolrQuery solrQuery = new SolrQuery().
		setQuery(queryStr).
        setQueryType("/terms");
		
		solrQuery.set("terms", true).
        set("terms.fl", SearchConstants.QUERY + "_auto").
        //set("terms.fl", SearchConstants.NAME +"," + SearchConstants.TAGS).
        set("terms.prefix", query).
        set("terms.lower.incl", "false").        
        set("terms.limit", 10).
        set("terms.sort", "count");
		
		solrQuery.addFilterQuery(SearchConstants.VERTICAL + ":" + category);
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
			
			if(response != null)
			{
				Object object = response.getResponse().get("terms");
				if(object != null && object instanceof NamedList)
				{
					NamedList terms = (NamedList) object;
					if(terms.size() > 0 )
					{
						NamedList terms2 = (NamedList) terms.getVal(0);
						for(int index=0; index <terms2.size(); index++)
						{
							result.add(terms2.getName(index));
						}
					}
				}
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return result;		
	}
	
	public void close()
	{
		processBuffer();
		commit();
		optimize();
	}

}
