package com.karniyarik.search.solr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.statistics.vo.ProductSummary;
import com.karniyarik.common.statistics.vo.SearchLog;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.filter.KarniyarikFilterr;
import com.karniyarik.ir.filter.KarniyarikSort;
import com.karniyarik.ir.filter.KarniyarikTermFilter;
import com.karniyarik.search.indexer.util.ProductDocumentUtil;
import com.karniyarik.search.searcher.ISearcher;
import com.karniyarik.search.searcher.QueryResult;
import com.karniyarik.search.searcher.QueryResultConstructor;
import com.karniyarik.search.searcher.autocomplete.AutoCompleteEngine;
import com.karniyarik.search.searcher.logger.SearchLogIndexer;
import com.karniyarik.search.searcher.logger.SpamController;
import com.karniyarik.search.searcher.query.NewQueryParser;
import com.karniyarik.search.searcher.query.Query;
import com.karniyarik.search.searcher.query.QueryParserFactory;
import com.karniyarik.search.searcher.suggest.Suggester;
import com.karniyarik.search.sponsored.SponsoredMerchantService;

public class SOLRSearcherImpl implements ISearcher
{
	public static int				MIN_QUERY_LENGTH = 1;

	private final CategoryConfig	categoryConfig;
	private final int				categoryType;
	private final QueryParserFactory qpFactory;
	private SponsoredMerchantService smService;
	
	public SOLRSearcherImpl(
			CategoryConfig categoryConfig,
			QueryParserFactory queryParserFactory,
			int categoryType,
			SponsoredMerchantService smService)
	{
		this.categoryConfig = categoryConfig;
		this.qpFactory = queryParserFactory;
		this.categoryType = categoryType;
		this.smService = smService;
	}

	public void refreshSponsoredMerchantService(SponsoredMerchantService newService) {
		
		try
		{
			smService = newService;
		}
		catch (Throwable e)
		{
			getLogger().error("Can not refresh sponsored links data", e);
		}
	}

	@Override
	public int countDocsBySite(String siteName)
	{
		Long result = getSearcher().getSiteDocsCount(siteName);
		return result.intValue(); 
	}

	@Override
	public int getTotalProductCountInSystem()
	{
		Long result = getSearcher().getIndexSize();
		return result.intValue(); 
	}

	@Override
	public Map<String, Integer> getSiteProductCounts()
	{
		Map<String, Integer> aResult = new HashMap<String, Integer>();
		Collection<SiteConfig> siteList = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfigList();

		for (SiteConfig siteConfig : siteList)
		{
			if (siteConfig.getCategory().equals(categoryConfig.getName()))
			{
				aResult.put(siteConfig.getSiteName(), countDocsBySite(siteConfig.getSiteName()));
			}
		}

		return aResult;
	}

	@Override
	public QueryResult search(String productUrl, boolean doLog)
	{
		SOLRSearchProxy searcher = getSearcher();
		QueryResult queryResult = new QueryResult();

		QueryResponse response = searcher.getProduct(productUrl);
		
		if (response != null && response.getResults().getNumFound() > 0)
		{
			Product product = null;

			if (response.getResults().getNumFound() > 1)
			{
				ExceptionNotifier.sendException("duplicate-product", "Duplicate Product", "Search by product id matched multiple products with same url" + productUrl);
			}

			product = ProductDocumentUtil.prepareProduct(response.getResults().get(0), categoryConfig);

			if (product != null)
			{
				queryResult.getResults().add(product);
				queryResult.setSimilarProducts(getSimilarProducts(searcher, product, 14));
				
				if(smService != null && smService.isSponsored(product.getSourceName())) {
					product.setSponsored(true);
				}
				
				if(doLog)
				{
					SearchLog searchLog = new SearchLog();
					searchLog.setQuery(productUrl);
					List<ProductSummary> products = new ArrayList<ProductSummary>();
					products.add(new ProductSummary(product.getLink(), product.getSourceName(), product.getName(), false));
					for (Product similarProduct : queryResult.getSimilarProducts())
					{
						products.add(new ProductSummary(similarProduct.getLink(), similarProduct.getSourceName(), similarProduct.getName(), false));
					}
					searchLog.setProducts(products);
					searchLog.setResultCount(products.size());
					searchLog.setCategory(CategorizerConfig.getCategoryString(categoryType));
					searchLog.setSpam(false);
					StatisticsWebServiceUtil.sendSearchLog(searchLog);
				}
			}
		}

		return queryResult;
	}

	public List<String> autocomplete(String term)
	{
		List<String> result = null;
		result = AutoCompleteEngine.getInstance().autocomplete(term, categoryType);
		return result;
	}
	
	@Override
	public QueryResult search(Query query, boolean doLog)
	{
		SOLRSearchProxy searcher = getSearcher();
		
		QueryResult result = new QueryResult();

		QueryResponse response = null;
		QueryResponse sponsoredResponse = null;
		List<String> clusters = new ArrayList<String>();
		Map<String, QueryResponse> otherVerticalSearchResults = new HashMap<String, QueryResponse>(); 
		try
		{
			if (query != null && StringUtils.isNotBlank(query.getQueryString()) && query.getQueryString().length() > MIN_QUERY_LENGTH)
			{
				try
				{
					NewQueryParser qp = qpFactory.createQueryParser();
					String queryString = query.getQueryString();
					String newQuery = qp.parse(queryString);
					query.setLuceneQuery(newQuery);
					
					response = searcher.search(query);					
					  
					if(smService != null && response != null && response.getResults().getNumFound() > 0 && (query.getSort() != KarniyarikSort.PRICE_LOW_TO_HIGH))
					{
						List<String> sponsoredMerchants = smService.getSponsoredMerchants();
						List<KarniyarikFilterr> sponsoredFilterList = new ArrayList<KarniyarikFilterr>();
						for(String sponsoredSiteName: sponsoredMerchants)
						{
							KarniyarikTermFilter filter = new KarniyarikTermFilter(SearchConstants.STORE, sponsoredSiteName);
							sponsoredFilterList.add(filter);
						}
						
						if(sponsoredFilterList.size() > 0)
						{
							sponsoredResponse = searcher.searchSponsored(query, sponsoredFilterList);
						}
					}
					
					clusters = prepareQuerySuggestions(query, searcher, response);
					
					if(response != null && response.getResults().getNumFound() < 1)
					{
						String orgCategory = query.getCategory();
						//search for other domains
						CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
						for(CategoryConfig categoryConfig: categorizerConfig.getCategoryConfigList())
						{
							String name = categoryConfig.getName();
							int otherCatType = CategorizerConfig.getCategoryType(name);
							if(otherCatType != categoryType)
							{
								//for now
								if(otherCatType == CategorizerConfig.CAR_TYPE  || otherCatType == CategorizerConfig.PRODUCT_TYPE)
								{
									query.setCategory(name);
									QueryResponse otherVerticalSearchResponse = getSearcher(otherCatType).search(query);
									otherVerticalSearchResults.put(name, otherVerticalSearchResponse);
								}
							}
						}
						query.setCategory(orgCategory);
					}
					
				}
				catch (Throwable e)
				{
					throw new RuntimeException("Exception occured during querying/search. query: " + query.getQueryString(), e);
				}
				finally {
					
				}

				QueryResultConstructor constructor = new QueryResultConstructor(response, sponsoredResponse, categoryConfig, smService, clusters, otherVerticalSearchResults);
				
				String[] suggestedWords = new String[]{};
				
				if(response != null && response.getSpellCheckResponse() != null && StringUtils.isNotBlank(response.getSpellCheckResponse().getCollatedResult()))
				{
					suggestedWords = new Suggester().suggest(response, categoryType, searcher, categoryConfig.getName());
				}
				
				result = constructor.construct(query, suggestedWords);
		
				if (result.getResults().isEmpty() && query.getPageNumber() > 1) {
					query.setPageNumber(1);
					result = constructor.construct(query, new String[]{}/*aSuggestedWords*/);
				}
		
				if(response != null)
				{
					result.setTimeTaken(response.getElapsedTime()/ 1000.0);	
				}
		
				if (query != null && doLog && response != null && result != null)
				{
					SearchLog searchLog = prepareSearchLog(result);
					StatisticsWebServiceUtil.sendSearchLog(searchLog);
					SearchLogIndexer.getInstance().log(query.getQueryString(), categoryType);
				}
			}
		}
		finally
		{
			
		}

		return result;
	}

	private SearchLog prepareSearchLog(QueryResult result)
	{
		SearchLog searchLog = new SearchLog();
		searchLog.setQuery(result.getQuery().getQueryString());
		searchLog.setResultCount((int)result.getTotalHits());
		searchLog.setSuggestionList(result.getSuggestedWords());
		searchLog.setTime(result.getTimeTaken());
		searchLog.setCategory(CategorizerConfig.getCategoryString(categoryType));
		searchLog.setHttpAgent(result.getQuery().getHttpAgent());
		searchLog.setApiKey(result.getQuery().getApiKey());
		searchLog.setSpam(SpamController.getInstance().isSpam(result.getQuery().getQueryString()));
		List<ProductSummary> products = new ArrayList<ProductSummary>();
		for (Product product : result.getResults())
		{
			products.add(new ProductSummary(product.getLink(), product.getSourceName(), product.getName(), false));
		}
		
		for (Product product : result.getSponsoredResults())
		{
			products.add(new ProductSummary(product.getLink(), product.getSourceName(), product.getName(), true));
		}
		searchLog.setProducts(products);
		
		return searchLog;
	}

	private List<Product> getSimilarProducts(SOLRSearchProxy searcher, Product product, int maxResults)
	{
		QueryResponse moreLikeThis = searcher.getMoreLikeThis(product.getLink(), 14);
		
		List<Product> result = new LinkedList<Product>();
		List<Product> sponsored = new LinkedList<Product>();

		try
		{
			for (SolrDocument document: moreLikeThis.getResults())
			{
				Product similarProduct = ProductDocumentUtil.prepareProduct(document, categoryConfig);
				
				if(similarProduct.getLink().equalsIgnoreCase(product.getLink()))
				{
					continue;
				}
				if(smService != null && smService.isSponsored(similarProduct.getSourceName())) {
					sponsored.add(similarProduct);
					similarProduct.setSponsored(true);
				}
				else
				{
					result.add(similarProduct);
				}
			}
			result.addAll(0, sponsored);
			
			if (result.size() > 0)
			{
				result.remove(0);
			}
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot find similar products", e);
		} 

		return result;
	}
	
	private List<String> prepareQuerySuggestions(Query query, SOLRSearchProxy solr, QueryResponse response)
	{
		List<String> result = new ArrayList<String>();
		List<String> clusters = new ArrayList<String>();
		if(response != null && response.getResults() != null && response.getResults().getNumFound() > 0)
		{
			clusters = new SOLRSearchClusterConstuctor().getClusters(query, response.getResults().getNumFound(), solr);
			
			if (clusters.size() > 10)
			{
				clusters = clusters.subList(0, 10);
			}
			if (clusters.size() > 3)
			{
				result = clusters;
			}			
		}
		return result;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
	
	private SOLRSearchProxy getSearcher()
	{
		return getSearcher(categoryType);
	}
	
	private SOLRSearchProxy getSearcher(int categoryType)
	{
		return SOLRSearcherFactory.getSearcher(categoryType);
	}

}
