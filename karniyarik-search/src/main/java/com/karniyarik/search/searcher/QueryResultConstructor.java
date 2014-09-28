package com.karniyarik.search.searcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.search.indexer.util.ProductDocumentUtil;
import com.karniyarik.search.searcher.query.Query;
import com.karniyarik.search.sponsored.SponsoredMerchantService;

public class QueryResultConstructor
{
	public static final int			MAX_NAME_LENGTH	= 80;
	private static final int		SPONSOR_PRODUCTS_TO_SHOW = 5;

	private QueryResponse 			queryResponse;
	private QueryResponse 			sponsoredResponse;
	private final CategoryConfig	categoryConfig;
	private SponsoredMerchantService smService;
	private List<String> clusters;
	
	private Map<String, QueryResponse> otherVerticalSearchResults = null;
	
	public QueryResultConstructor(
			QueryResponse queryResponse,
			QueryResponse sponsoredResponse,
			CategoryConfig categoryConfig,
			SponsoredMerchantService smService,
			List<String> clusters, 
			Map<String, QueryResponse> otherVerticalSearchResults)
	{
		this.queryResponse = queryResponse;
		this.sponsoredResponse = sponsoredResponse;
		this.categoryConfig = categoryConfig;
		this.smService = smService;
		this.clusters = clusters;
		this.otherVerticalSearchResults = otherVerticalSearchResults;
	}

	public QueryResult construct(Query aQuery, String[] aSuggestedWords)
	{
		QueryResult result = new QueryResult();

		if (aQuery != null)
		{
			result.setQuery(aQuery);
			result.setSuggestedWords(aSuggestedWords);
			if(queryResponse != null)
			{
				result.setTotalHits(queryResponse.getResults().getNumFound());
				prepareFacets(result);
				iterateOverDocs(result);
			}
			result.getNarrowingQueries().addAll(clusters);
			
			prepareOtherVerticalSearchResults(result);
		}

		return result;
	}
	
	private void prepareOtherVerticalSearchResults(QueryResult result) {
		for(String otherVerticalCatName: otherVerticalSearchResults.keySet())
		{
			QueryResponse queryResponse = otherVerticalSearchResults.get(otherVerticalCatName);
			if(queryResponse != null && queryResponse.getResults().getNumFound() > 0)
			{
				
				CrossDomainResult domainResult = new CrossDomainResult();
				domainResult.setCategoryName(otherVerticalCatName);
				domainResult.setTotalHits(queryResponse.getResults().getNumFound());
				int count = 0;
				for(SolrDocument doc: queryResponse.getResults())
				{
					collectProductInformation(doc, domainResult.getResults());
					count++;
					if(count > 3){
						break;
					}
				}
				result.getOtherDomains().put(otherVerticalCatName, domainResult);
			}
		}
	}

	private void prepareFacets(QueryResult result) {
		List<FacetField> facetFields = queryResponse.getFacetFields();
		for(FacetField field: facetFields)
		{
			if(field.getValueCount() > 0)
			{
				FieldStatsInfo fieldStatsInfo = queryResponse.getFieldStatsInfo().get(field.getName());
				ResultProperty prop = new ResultProperty(field.getName(), field, fieldStatsInfo);
				result.getCategoryResultFilterList().put(field.getName(), prop);
			}
		}
		
		for(FieldStatsInfo statInfo: queryResponse.getFieldStatsInfo().values())
		{
			if(!result.getCategoryResultFilterList().containsKey(statInfo.getName()))
			{
				ResultProperty prop = new ResultProperty(statInfo.getName(), null, statInfo);
				result.getCategoryResultFilterList().put(statInfo.getName(), prop);
			}
		}
	}
	
//	private List<ResultFilterInfo> clusterResultFilters(String propName, List<Double> list)
//	{
//		List<ResultFilterInfo> result = new ArrayList<ResultFilterInfo>();
//
//		ClusterConstructor clusterConstructor = new ClusterConstructor();
//
//		List<ClusterInfo> clusterList = clusterConstructor.findNumberClusters(list, clusterSize);
//
//		ResultFilterInfo resultInfo = null;
//		for (ClusterInfo cluster : clusterList)
//		{
//			resultInfo = new ResultFilterInfo(cluster);
//			result.add(resultInfo);
//		}
//
//		return result;
//	}


	private void iterateOverDocs(QueryResult result) {
		List<Product> productList = new ArrayList<Product>();
		List<Product> sponsoredProductList = new ArrayList<Product>();

		for(int i = 0; i < queryResponse.getResults().size(); i++) {
 			collectProductInformation(queryResponse.getResults().get(i), productList);
		}

		if(sponsoredResponse != null && sponsoredResponse.getResults().getNumFound() > 0)
		{
			for(int i = 0; i < sponsoredResponse.getResults().size(); i++) {
	 			collectProductInformation(sponsoredResponse.getResults().get(i), sponsoredProductList);
			}			
		}

		prepareProductResults(result, productList, sponsoredProductList);
	}

	private void prepareProductResults(
			QueryResult queryResult,
			List<Product> productList,
			List<Product> sponsoredProductList) {

		List<Product> sortedSponsoredProducts =	smService.sortSponsoredProducts(sponsoredProductList);

		if (sortedSponsoredProducts.size() < SPONSOR_PRODUCTS_TO_SHOW) {
			queryResult.getSponsoredResults().addAll(sortedSponsoredProducts);
		}
		else {
			boolean sponsoredProductsNotFilled = true;

			Product sponsoredProduct = null;

			int counter = 0;
			int index = ((queryResult.getQuery().getPageNumber() - 1) * SPONSOR_PRODUCTS_TO_SHOW);

			while(sponsoredProductsNotFilled) 
			{
				if (index >= sortedSponsoredProducts.size()) {

					index = 0;
				}
				
				sponsoredProduct = sortedSponsoredProducts.get(index);
				queryResult.getSponsoredResults().add(sponsoredProduct);

				index++;
				if(++counter == SPONSOR_PRODUCTS_TO_SHOW) {
					sponsoredProductsNotFilled = false;
				}

			}
		}

		//remove all shown sponsored results
		productList.removeAll(queryResult.getSponsoredResults());

		for (Product product: productList)// int i = pageStartIndex; (i < pageStartIndex + queryResult.getQuery().getPageSize()) && (i < productList.size()); i++) {
		{
//			highlightedName = trimProductName(product.getName());
//			if (queryResult.getQuery().isUseHighLighter())
//			{
//				highlightedName = highlight(highlightedName, SearchConstants.NAME);
//			}
//			product.setHighlightedName(highlightedName);
			product.setHighlightedName(product.getName());
			queryResult.getResults().add(product);
		}

//		for (Product product: sponsoredProductList)
//		{
//			product.setHighlightedName(product.getName());
//			queryResult.getSponsoredResults().add(product);
//		}

		productList = null;
	}

	private String trimProductName(String name)
	{
		if (name != null && name.length() > MAX_NAME_LENGTH)
		{
			name = name.substring(0, MAX_NAME_LENGTH) + "...";
		}
		return name;
	}

	private void collectProductInformation(
			SolrDocument doc,
			List<Product> productList) {

		Product product = ProductDocumentUtil.prepareProduct(doc, categoryConfig);			
		productList.add(product);
	}
}
