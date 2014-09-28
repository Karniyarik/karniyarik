package com.karniyarik.search.solr;

import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrInputDocument;

import com.karniyarik.ir.SearchConstants;

public class SOLRIndexerProxy extends SOLRBaseProxy {
	private List<SolrInputDocument> buffer = new LinkedList<SolrInputDocument>();
	private static int MAX_BUFF_SIZE = 20000;
	
	private String categoryName;
	
	SOLRIndexerProxy(String coreName, String categoryName) {
		super(coreName,120000);
		this.categoryName = categoryName;
	}
	
	public void init()
	{
		buffer.clear();
	}
	
	public void add(SolrInputDocument doc)
	{
		synchronized (this) {
			buffer.add(doc);
			//processBuffer();
			if(buffer.size() > MAX_BUFF_SIZE)
			{
				processBuffer();
			}				
		}
	}

	public void add(List<SolrInputDocument> docs)
	{
		try {
			synchronized (this) {
				buffer.addAll(docs);
				if(buffer.size() > MAX_BUFF_SIZE)
				{
					processBuffer();
				}
			}
//			getServer().add(docs);
//			UpdateRequest req = new UpdateRequest();
//			req.setAction(ACTION.COMMIT, false, false);
//			req.add(docs);
//			req.process(getServer());
//			buffer.clear();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	private void processBuffer() {
		if(buffer.size() > 0)
		{
			try {
//				UpdateRequest req = new UpdateRequest();
//				req.setAction(ACTION.COMMIT, false, false);
//				req.add(buffer);
				getServer().add(buffer);
//				req.process(getServer());
				buffer.clear();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

//	public void optimize() {
//		try{
//			getServer().optimize();
//		}catch (Throwable e) {
//			throw new RuntimeException(e);
//		}
//	}

	public void commit() {
		try
		{
			getServer().commit();
		}
		catch (Throwable e) 
		{
			throw new RuntimeException(e);
		}
	}
	
	public void close()
	{
		processBuffer();
	}

	public void delete(String siteName) {
		try {
			siteName = ClientUtils.escapeQueryChars(siteName);
			String query = SearchConstants.STORE + ":" + siteName;
			getServer().deleteByQuery(query);
			//commit();
			Thread.sleep(60000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	
	public QueryResponse getDocuments(String sitename, int page, int pagesize)
	{
		sitename = ClientUtils.escapeQueryChars(sitename);
		int startIndex = page * pagesize;
		
		SolrQuery solrQuery = new  SolrQuery().
		setQuery(SearchConstants.STORE + sitename).
        setQueryType("standard").
        setFields("*").
        setRows(pagesize).
        setStart(startIndex);
		
	    QueryResponse response = null;
	    try {
			response = getServer().query(solrQuery);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return response;		
	}
	
	public void deleteDocuments(List<String> ids)
	{
	    try {
	    	getServer().deleteById(ids);
	    	//commit();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
}
