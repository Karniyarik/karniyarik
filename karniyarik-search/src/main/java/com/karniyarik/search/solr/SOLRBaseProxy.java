package com.karniyarik.search.solr;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.ir.SearchConstants;

public abstract class SOLRBaseProxy {

	public static String CORE_QUERY = "/coreQuery";
	
	private CommonsHttpSolrServer solr = null;
	
	public static String[] default_fields = new String[]{
		SearchConstants.ID,
		SearchConstants.UUID, 
		SearchConstants.SKU, 		 
		SearchConstants.NAME, 
		SearchConstants.BREADCRUMB, 
		SearchConstants.BRAND, 
		SearchConstants.PRICE, 
		SearchConstants.PRICE_ALTERNATE, 
		SearchConstants.CURRENCY, 
		SearchConstants.STORE, 
		SearchConstants.PRODUCT_URL, 
		SearchConstants.STORE_URL, 
		SearchConstants.IMAGE_URL, 
		SearchConstants.LAST_FETCH_DATE, 
		SearchConstants.TIMESTAMP};    

	public static String[] default_facet_fields = new String[]{
		SearchConstants.BRAND,SearchConstants.STORE, SearchConstants.TIMESTAMP};
	
	public static String[] default_stat_fields = new String[]{SearchConstants.PRICE};
	
	public SOLRBaseProxy(String core) {
		this(core, 5000);
	}

	public SOLRBaseProxy(String core, int timeOut) {
		try {
			String solrAddress = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig().getSolr();
			solr = new CommonsHttpSolrServer(solrAddress + core);
			//solr = new CommonsHttpSolrServer("http://77.92.136.5:8080/solr" + core);
			solr.setSoTimeout(timeOut);  // socket read timeout
			solr.setConnectionTimeout(timeOut);
			solr.setDefaultMaxConnectionsPerHost(300);
			solr.setMaxTotalConnections(300);
			solr.setFollowRedirects(false);  
			solr.setAllowCompression(true);
			solr.setMaxRetries(1); // defaults to 0.  > 1 not recommended.			
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public CommonsHttpSolrServer getServer() {
		return solr;
	}
	
}
