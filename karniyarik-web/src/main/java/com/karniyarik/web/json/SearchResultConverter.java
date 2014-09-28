package com.karniyarik.web.json;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.json.JSONObject;

import com.karniyarik.affiliate.AffiliateProvider;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.site.SiteInfo;
import com.karniyarik.common.site.SiteRegistry;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.vo.ResultFilterInfo;
import com.karniyarik.search.EngineRepository;
import com.karniyarik.search.searcher.CrossDomainResult;
import com.karniyarik.search.searcher.ISearcher;
import com.karniyarik.search.searcher.QueryResult;
import com.karniyarik.search.searcher.ResultProperty;
import com.karniyarik.search.searcher.logger.SpamController;
import com.karniyarik.search.searcher.query.Query;
import com.karniyarik.web.bendeistiyorum.DailyOpportunityLoader;
import com.karniyarik.web.category.BaseCategoryUtil;
import com.karniyarik.web.category.UtilProvider;
import com.karniyarik.web.sitemap.BaseURLProvider;
import com.karniyarik.web.util.Formatter;
import com.karniyarik.web.util.QueryStringHelper;
import com.karniyarik.web.util.RequestWrapper;

public class SearchResultConverter
{
	private RequestWrapper	requestWrapper	= null;
	
	public SearchResultConverter(HttpServletRequest request)
	{
		requestWrapper = RequestWrapper.getRequestWrapper(request);
	}

	public SearchResult getSearchResult()
	{
		SearchResult result = new SearchResult();

		if (StringUtils.isNotBlank(requestWrapper.getCategory()))
		{
			if (StringUtils.isNotBlank(requestWrapper.getProductUrl()))
			{
				result.setSimilar(true);
				prepareSingleProductResult(result);
			}
			else if (StringUtils.isNotBlank(requestWrapper.getQuery()))
			{
				result.setSimilar(false);
				prepareOrdinarySearchResult(result);
			}
			else if (requestWrapper.getCategoryType() == CategorizerConfig.DAILY_OPPORTUNITY_TYPE)
			{
				result.setSimilar(false);
				prepareDailyOpportunitySearchResult(result);
			}
			else
			{
				result.setShowEmptySearchError(true);
			}
		}

		return result;
	}

	private void prepareDailyOpportunitySearchResult(SearchResult searchResult) {
		List<Product> products = DailyOpportunityLoader.getInstance().getProducts();
		QueryResult aResult = new QueryResult();
		Query query = new Query();
		query.setPageNumber(0);
		aResult.setQuery(query);
		aResult.setResults(products);
		aResult.setSimilarProducts(new ArrayList<Product>());
		aResult.setSponsoredResults(new ArrayList<Product>());
		aResult.setSuggestedWords(new String[]{});
		aResult.setTimeTaken(0);
		aResult.setTotalHits(products.size());
		requestWrapper.setQueryResult(aResult);
		prepareProductResults(requestWrapper, searchResult, false);
		
		String title = "Günlük Fırsat Ürünleri";				
		searchResult.setPageTitle(title);
		
		String desc = "Günlük Fırsat Ürünleri sitelerinden toplamış olan en ucuz ürünler";
		searchResult.setPageDescription(desc);
		
		String header = "Günlük Fırsat Ürünleri";
		searchResult.setPageHeader(header);

		searchResult.setShowImages(requestWrapper.getShowImages());
		searchResult.setShowSimilarityGauge(false);
		searchResult.setShowProducts(true);
		searchResult.setShowFilters(true);
		searchResult.setShowToolbar(true);
	}

	private void prepareSingleProductResult(SearchResult result)
	{
		ISearcher aSearcher = EngineRepository.getInstance().getCategorySearcher(requestWrapper.getCategory());
		String url = requestWrapper.getProductUrl();
		QueryResult aResult = aSearcher.search(url, requestWrapper.isShallLog());
		requestWrapper.setQueryResult(aResult);
		prepareProductResults(requestWrapper, result, false);
		result.setShowImages(requestWrapper.getShowImages());
		result.setShowSimilarityGauge(false);
		
		if (result.getResults().size() > 0)
		{
			BaseCategoryUtil domainUtil = UtilProvider.getDomainUtil(requestWrapper.getCategoryType());
			
			ProductResult product = result.getResults().get(0);
			product.setSingleShared(true);
			List<ProductResult> similarResults = null;
			
			if(result.getResults().size() > 1)
			{
				similarResults = result.getResults().subList(1, result.getResults().size());
			}
			else
			{
				similarResults = new ArrayList<ProductResult>();
			}	
			
			String title = domainUtil.getSingleProductPageTitle(product, similarResults);				
			result.setPageTitle(title);
			
			String desc = domainUtil.getSingleProductPageDescription(product, similarResults);
			result.setPageDescription(desc);
			
			String header = domainUtil.getSingleProductPageHeader(product, similarResults);
			result.setPageHeader(header);
			
			String keywords = domainUtil.getSingleProductPageKeywords(product, similarResults);
			result.setPageKeywords(keywords);
			
			String pageImgSrc = domainUtil.getSingleProductImageSource(product);
			result.setPageImgSrc(pageImgSrc);
		} else {
			result.setPageTitle("Sonuç bulunamadı");
			result.setPageHeader("Sonuç bulunamadı");
			result.setPageDescription("Sonuç bulunamadı");
		}

		arrangeVisibility(result);
	}

	private void prepareOrdinarySearchResult(SearchResult result)
	{
		Query aQuery = new Query();
		aQuery.setQueryString(requestWrapper.getQuery());
		aQuery.setPageSize(requestWrapper.getPageSize());
		aQuery.setUseHighLighter(true);
		aQuery.setPageNumber(requestWrapper.getPageNumber());
		aQuery.setApiKey(requestWrapper.getApiKey());

		if (requestWrapper.getSort() != null)
		{
			aQuery.setSort(requestWrapper.getSort());
		}

		aQuery.getCategorySpecificFilterList().clear();
		if (requestWrapper.getCategorySpecificFilters() != null)
		{
			aQuery.getCategorySpecificFilterList().addAll(requestWrapper.getCategorySpecificFilters());
		}

		//aQuery.setConvertSpacesToOr(requestWrapper.isConvertSpacesToOr());

		ISearcher aSearcher = EngineRepository.getInstance().getCategorySearcher(requestWrapper.getCategory());
		aQuery.setHttpAgent(requestWrapper.getUserAgent());
		aQuery.setCategory(requestWrapper.getCategory());
		QueryResult aResult = aSearcher.search(aQuery, requestWrapper.isShallLog());
		if (!SpamController.getInstance().isSpam(aQuery.getQueryString()) && aResult.getTotalHits() > 0  && requestWrapper.isShallLog()) {
			SearchStatisticsManager.getInstance().searchOcurred(requestWrapper.getCategoryType(), aQuery.getQueryString());
		}
		requestWrapper.setQueryResult(aResult);
		// page number may have been changed if there is no result at the original page but in page 1
		if(aResult.getQuery() != null)
		{
			requestWrapper.setPageNumber(aResult.getQuery().getPageNumber());	
		}

		initSearchResult(requestWrapper, result);

		List<LinkedLabel> activeFilters = new ArrayList<LinkedLabel>();
		
		BaseCategoryUtil domainUtil = UtilProvider.getDomainUtil(requestWrapper.getCategoryType());
		
		activeFilters = domainUtil.getActiveFilters(requestWrapper.getRequest(), false);
		
		String title = domainUtil.getPageTitle(requestWrapper.getQuery(), activeFilters, requestWrapper.getPageNumber(), result.getResults());
		result.setPageTitle(title);
		
		String header = domainUtil.getPageHeader(requestWrapper.getQuery(), activeFilters, requestWrapper.getPageNumber(), result.getResults());
		result.setPageHeader(header);

		String desc = domainUtil.getPageDescription(requestWrapper.getQuery(), activeFilters, requestWrapper.getPageNumber(), result);
		result.setPageDescription(desc.toString());

		desc = domainUtil.getPageKeywords(requestWrapper.getQuery(), activeFilters, requestWrapper.getPageNumber(), result.getResults(), result.getTotalHits(), result.getSiteCount());
		result.setPageKeywords(desc.toString());
		
		String canonical = domainUtil.getCanonicalURL(result, requestWrapper);
				
		if(StringUtils.isNotBlank(canonical))
		{
			result.setCanonical(canonical);
		}
		
		List<LinkedLabel> breadCrumb = domainUtil.getBreadCrumb(result, requestWrapper, activeFilters);
		result.setBreadcrumb(breadCrumb);
		
		String pageImgSrc = domainUtil.getPageImageSource(result.getResults());
		result.setPageImgSrc(pageImgSrc);
		
		arrangeVisibility(result);
	}

	public String getFullSearchResults()
	{
		JSONObject jsonObject = new JSONObject(getSearchResult());

		try
		{
			return new String(jsonObject.toString().getBytes(), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Cannot convert JSON encoding", e);
		}
	}

//	private void prepareMoreResults(RequestWrapper requestWrapper, SearchResult searchResult)
//	{
//		QueryStringHelper queryStringHelper = new QueryStringHelper(requestWrapper);
//		queryStringHelper.setDestinationPage("search.jsp");
//
//		LinkedLabel moreResults = null;
//		queryStringHelper.setPageNumber(null);
//
//		if (requestWrapper.getQueryResult() != null && requestWrapper.getQueryResult().isCanUseOr())
//		{
//			queryStringHelper.setCanUseOr(true);
//			moreResults = new LinkedLabel("Daha fazla sonuç için tıklayınız", queryStringHelper.getRequestQuery());
//		}
//		else if (requestWrapper.isConvertSpacesToOr())
//		{
//			queryStringHelper.setCanUseOr(false);
//			moreResults = new LinkedLabel("Daha sade sonuçlar için tıklayınız", queryStringHelper.getRequestQuery());
//		}
//
//		searchResult.setMoreResults(moreResults);
//	}

	private void initSearchResult(RequestWrapper requestWrapper, SearchResult searchResult)
	{
		QueryResult result = requestWrapper.getQueryResult();

		if (result.getQuery() != null)
		{
			searchResult.setPageNumber(result.getQuery().getPageNumber());
			searchResult.setPageSize(result.getQuery().getPageSize());
			searchResult.setQuery(result.getQuery().getQueryString());
			searchResult.setOriginalQuery(result.getQuery().getOriginalQuery());
			prepareProductResults(requestWrapper, searchResult, true);
			prepareCategoryPropList(requestWrapper, searchResult);
			prepareSuggestedWords(requestWrapper, searchResult);
			//prepareMoreResults(requestWrapper, searchResult);
			preparePagerResults(requestWrapper, searchResult);
			prepareSuggestedQueries(requestWrapper, searchResult);
			prepareOtherCategoryResults(requestWrapper, searchResult);
		}else {
			searchResult.setQuery(requestWrapper.getQuery());
			searchResult.setOriginalQuery(requestWrapper.getQuery());
		}

		searchResult.setCategoryFilter(requestWrapper.getCategoryFilterValue());

		//searchResult.setCanUseOr(result.isCanUseOr());
		searchResult.setSort(requestWrapper.getSortType());
		searchResult.setTimeTaken(Formatter.formatTime(result.getTimeTaken()));
		searchResult.setTotalHits((int)result.getTotalHits());
		searchResult.setShowImages(requestWrapper.getShowImages());
		if(searchResult.getCategoryPropMap().get(SearchConstants.STORE) != null)
		{
			searchResult.setSiteCount(searchResult.getCategoryPropMap().get(SearchConstants.STORE).size());	
		}
		
	}

	private void prepareOtherCategoryResults(RequestWrapper requestWrapper,
		SearchResult searchResult) {
		QueryResult result = requestWrapper.getQueryResult();

		QueryStringHelper queryStringHelper = new QueryStringHelper(requestWrapper);
		queryStringHelper.setDestinationPage("search.jsp");
		queryStringHelper.setSearchQuery(requestWrapper.getQuery());
		for(String domainName: result.getOtherDomains().keySet())
		{
			CrossDomainResult crossDomainResult = result.getOtherDomains().get(domainName);
			CrossDomainSearchResult crossDomainSearchResult = new CrossDomainSearchResult();
			crossDomainSearchResult.setCategoryName(crossDomainResult.getCategoryName());
			crossDomainSearchResult.setTotalHits(crossDomainResult.getTotalHits());
			queryStringHelper.setCategoryFilterValue(StringUtil.toLowerCase(domainName));
			crossDomainSearchResult.setUrl(queryStringHelper.getRequestQuery());
			searchResult.getOtherVerticalResults().put(domainName, crossDomainSearchResult);
			fillProductResults(requestWrapper, queryStringHelper, crossDomainResult.getResults(), 
					crossDomainSearchResult.getResults(),  "standard", null);	
		}
	}

	private void arrangeVisibility(SearchResult searchResult)
	{
		if (StringUtils.isNotBlank(requestWrapper.getProductUrl()))
		{
			if (searchResult.getResults().size() > 0)
			{
				searchResult.setShowProducts(true);
			}
			else
			{
				searchResult.setShowURLNotFoundError(true);
			}
			
			searchResult.setShowPageHeader(false);
		}
		else
		{
			if (StringUtils.isNotBlank(searchResult.getQuery()))
			{
				if (searchResult.getSuggestedWords().size() > 0  && requestWrapper.getCategorySpecificFilters().size() == 0)
				{
					searchResult.setShowSuggestion(true);
				}

				if (searchResult.getResults().size() > 0 || searchResult.getSponsoredProducts().size() > 0)
				{
					searchResult.setShowProducts(true);
					searchResult.setShowFilters(true);

					if (searchResult.getMoreResults() != null)
					{
						searchResult.setShowMoreResults(true);
					}

					searchResult.setShowToolbar(true);
				}
				else if (searchResult.getResults().size() == 0 && searchResult.getSponsoredProducts().size() == 0 && !searchResult.isShowSuggestion())
				{
					searchResult.setShowNotFound(true);
				}
			}
			else
			{
				searchResult.setShowEmptySearchError(true);
			}
		}
	}

	private void preparePagerResults(RequestWrapper requestWrapper, SearchResult searchResult)
	{
		PagerCalculator calculator = new PagerCalculator(requestWrapper);

		QueryStringHelper queryStringHelper = new QueryStringHelper(requestWrapper);
		queryStringHelper.setDestinationPage("search.jsp");

//		if (calculator.isFirstButtonRequired())
//		{
//			queryStringHelper.setPageNumber(calculator.getFirstButtonPage());
//			LinkedLabel aLinkedLabel = new LinkedLabel("İlk", queryStringHelper.getRequestQuery());
//			aLinkedLabel.setCssClass("");
//			aLinkedLabel.setTooltip("İlk sayfaya atla");
//			searchResult.getPagerResults().add(aLinkedLabel);
//		}

		if (calculator.isNextButtonOnLeftRequired())
		{
			queryStringHelper.setPageNumber(calculator.getFastPreviousButtonPage());
			LinkedLabel aLinkedLabel = new LinkedLabel("<<", queryStringHelper.getRequestQuery());
			aLinkedLabel.setCssClass("");
			aLinkedLabel.setCount(queryStringHelper.getPageNumber());
			aLinkedLabel.setTooltip(PagerCalculator.MAX_PAGER + " Sayfa geri atla");
			searchResult.getPagerResults().add(aLinkedLabel);
		}

		if (calculator.getPageCount() > 1)
		{
			if (calculator.getPageNumber() > 1)
			{
				queryStringHelper.setPageNumber(calculator.getPreviousButtonPage());
				LinkedLabel aLinkedLabel = new LinkedLabel("<", queryStringHelper.getRequestQuery());
				aLinkedLabel.setCssClass("");
				aLinkedLabel.setCount(queryStringHelper.getPageNumber());
				aLinkedLabel.setTooltip("Önceki sayfa");
				searchResult.getPagerResults().add(aLinkedLabel);
			}

			for (int anIndex = calculator.getPageStart(); anIndex <= calculator.getPageEnd(); anIndex++)
			{
				queryStringHelper.setPageNumber(anIndex);
				LinkedLabel aLinkedLabel = new LinkedLabel(Integer.toString(anIndex), queryStringHelper.getRequestQuery());
				aLinkedLabel.setCssClass("");
				aLinkedLabel.setTooltip("Sayfaya git");
				aLinkedLabel.setCount(queryStringHelper.getPageNumber());
				if (queryStringHelper.getPageNumber() == calculator.getPageNumber())
				{
					aLinkedLabel.setCssClass("act");
					aLinkedLabel.setTooltip("Şu anki sayfa");
				}
				searchResult.getPagerResults().add(aLinkedLabel);

			}

			if (calculator.getPageEnd() > calculator.getPageNumber())
			{
				queryStringHelper.setPageNumber(calculator.getNextButtonPage());
				LinkedLabel aLinkedLabel = new LinkedLabel(">", queryStringHelper.getRequestQuery());
				aLinkedLabel.setCssClass("");
				aLinkedLabel.setCount(queryStringHelper.getPageNumber());
				aLinkedLabel.setTooltip("Sonraki sayfa");
				searchResult.getPagerResults().add(aLinkedLabel);
			}
		}

		if (calculator.isNextButtonOnRightRequired())
		{
			queryStringHelper.setPageNumber(calculator.getFastNextButtonPage());
			LinkedLabel aLinkedLabel = new LinkedLabel(">>", queryStringHelper.getRequestQuery());
			aLinkedLabel.setCssClass("page");
			aLinkedLabel.setCount(queryStringHelper.getPageNumber());
			aLinkedLabel.setTooltip(PagerCalculator.MAX_PAGER + " Sayfa ileri atla");
			searchResult.getPagerResults().add(aLinkedLabel);
		}
		
//		if (calculator.isLastButtonRequired())
//		{
//			queryStringHelper.setPageNumber(calculator.getLastButtonPage());
//			LinkedLabel aLinkedLabel = new LinkedLabel("Son", queryStringHelper.getRequestQuery());
//			aLinkedLabel.setCssClass("");
//			aLinkedLabel.setTooltip("Son sayfaya atla");
//			searchResult.getPagerResults().add(aLinkedLabel);
//		}
	}

	private void prepareSuggestedWords(RequestWrapper requestWrapper, SearchResult searchResult)
	{
		QueryResult result = requestWrapper.getQueryResult();

		if (result.getSuggestedWords() != null)
		{
			QueryStringHelper queryStringHelper = new QueryStringHelper(requestWrapper);
			queryStringHelper.setDestinationPage("search.jsp");

			for (String suggestion : result.getSuggestedWords())
			{
				suggestion = BaseURLProvider.cleanProblematicCharacters(suggestion);
				queryStringHelper.setSearchQuery(suggestion);
				searchResult.getSuggestedWords().add(new LinkedLabel(suggestion.trim(), queryStringHelper.getRequestQuery()));
			}
		}
	}

	private void prepareSuggestedQueries(RequestWrapper requestWrapper, SearchResult searchResult)
	{
		QueryResult result = requestWrapper.getQueryResult();

		if (result.getNarrowingQueries() != null)
		{
			QueryStringHelper queryStringHelper = new QueryStringHelper(requestWrapper);
			queryStringHelper.setDestinationPage("search.jsp");
			queryStringHelper.setPageNumber(null);
			queryStringHelper.setRestful(true);
			for (String query: result.getNarrowingQueries())
			{
				String linkQuery = query.trim(); 
				linkQuery = BaseURLProvider.cleanProblematicCharacters(linkQuery);
				queryStringHelper.setSearchQuery(linkQuery);
				
				LinkedLabel linkedLabel = new LinkedLabel(query.trim(), queryStringHelper.getRequestQuery());
				searchResult.getSuggestedQueries().add(linkedLabel);
			}
		}
	}

	

	private String getPriceApendix(Integer price)
	{
		String priceStr = price.toString();
		char lastChar = priceStr.charAt(priceStr.length() - 1);
		String result = "dan";
		switch (lastChar)
		{
		case '0':
			result = "dan";
			break;
		case '1':
			result = "den";
			break;
		case '2':
			result = "den";
			break;
		case '3':
			result = "ten";
			break;
		case '4':
			result = "ten";
			break;
		case '5':
			result = "den";
			break;
		case '6':
			result = "dan";
			break;
		case '7':
			result = "den";
			break;
		case '8':
			result = "den";
			break;
		case '9':
			result = "dan";
			break;
		default:
		}

		return result;
	}

	private void prepareCategoryPropList(RequestWrapper requestWrapper,
			SearchResult searchResult)
	{
		QueryResult result = requestWrapper.getQueryResult();

		QueryStringHelper queryHelper = new QueryStringHelper(requestWrapper);
		queryHelper.setDestinationPage("search.jsp");
		queryHelper.setPageNumber(null);
		queryHelper.setBaseURL(requestWrapper.getRequest().getRequestURL().toString());
		queryHelper.setAppendFilterParameters(true);
		queryHelper.setRestful(false);
		queryHelper.setRestful(true);

		for (String propName : result.getCategoryResultFilterList().keySet())
		{
			ResultProperty field = result.getCategoryResultFilterList().get(propName);

			List<LinkedLabel> propLinkedLabelList = searchResult.getCategoryPropMap().get(propName);
			if (propLinkedLabelList == null)
			{
				propLinkedLabelList = new ArrayList<LinkedLabel>();
				searchResult.getCategoryPropMap().put(propName, propLinkedLabelList);
			}

			if (field.getStat() != null)
			{
				int min = field.getStat().getMin().intValue();
				int max = field.getStat().getMax().intValue();
				
				LinkedLabel label1 = new LinkedLabel("min", null,min);
				LinkedLabel label2 = new LinkedLabel("max", null,max);
				propLinkedLabelList.add(label1);
				propLinkedLabelList.add(label2);
				//List<LinkedLabel> clusters = prepareClusteredLabels(propName, list, searchResult);
				//searchResult.getCategoryPropMap().put(propName, clusters);
			}
			else
			{
				for (Count count: field.getField().getValues())
				{
					if(!isBannedBrand(count)){
						queryHelper.cleanFilterParam(propName);
						queryHelper.setFilterParam(propName, count.getName());					
						propLinkedLabelList.add(new LinkedLabel(count.getName(), queryHelper.getRequestQuery(), (int) count.getCount()));
					}
				}
				queryHelper.cleanFilterParam(propName);
			}
		}
	}

	private List<LinkedLabel> prepareClusteredLabels(String propName, List<ResultFilterInfo> list, SearchResult searchResult)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();

		if (list != null && list.size() > 0)
		{
			int index = 0;
			String label = null;
			Integer firstValue = null;
			Integer secondValue = null;

			QueryStringHelper queryHelper = new QueryStringHelper(requestWrapper);
			queryHelper.setPageNumber(null);
			queryHelper.setAppendFilterParameters(true);
			queryHelper.setRestful(false);
			queryHelper.setDestinationPage("search.jsp");

			for (ResultFilterInfo resultFilterInfo : list)
			{
				firstValue = resultFilterInfo.getStartPoint();
				secondValue = resultFilterInfo.getEndPoint();
				queryHelper.cleanFilterParam(propName + "1");
				queryHelper.cleanFilterParam(propName + "2");
				//queryHelper.setFilterParam(propName + "1", firstValue.toString());

				if (index == 0)
				{
					label = secondValue + "'" + getPriceApendix(secondValue) + " küçük";
					queryHelper.setFilterParam(propName + "2", secondValue.toString());
				}
				else if (index == list.size() - 1)
				{
					queryHelper.setFilterParam(propName + "1", firstValue.toString());
					label = firstValue + "'" + getPriceApendix(firstValue) + " büyük";
					// queryHelper.setFilterParam(propName + "2", secondValue
					// .toString());
				}
				else
				{
					queryHelper.setFilterParam(propName + "1", firstValue.toString());
					label = firstValue + " - " + secondValue;
					queryHelper.setFilterParam(propName + "2", secondValue.toString());
				}

				result.add(new LinkedLabel(label, queryHelper.getRequestQuery(), resultFilterInfo.getElementCount()));

				index++;
			}
		}

		return result;
	}
	public void prepareProductResults(RequestWrapper requestWrapper, SearchResult searchResult, boolean calculateVisualIndex)
	{
		QueryStringHelper queryStringHelper = new QueryStringHelper(requestWrapper);
		queryStringHelper.setDestinationPage("search.jsp");

		QueryResult result = requestWrapper.getQueryResult();

		//why mr anderson why did you do this??? sss
		for(Product product: result.getSimilarProducts())
		{
			product.setName(product.getName());
		}
		
		BaseCategoryUtil domainUtil = UtilProvider.getDomainUtil(requestWrapper.getCategoryType());
		
		fillProductResults(requestWrapper, queryStringHelper, result.getResults(), searchResult.getResults(), "standard", domainUtil);
		fillProductResults(requestWrapper, queryStringHelper, result.getSponsoredResults(), searchResult.getSponsoredProducts(), "sponsored", domainUtil);
		fillProductResults(requestWrapper, queryStringHelper, result.getSimilarProducts(), searchResult.getResults(), "similar", domainUtil);
	}

	private void fillProductResults(RequestWrapper requestWrapper,
			QueryStringHelper queryStringHelper, List<Product> productList, List<ProductResult> resultList, String utmContent, BaseCategoryUtil domainUtil)
	{
		
		ProductResult productResult;
		String shareURL = requestWrapper.getRequest().getRequestURL().toString();
		//shareURL = shareURL.replace("search.jsp", "product.jsp");
		
		for (Product product : productList)
		{
			productResult = new ProductResult(product, requestWrapper.getRequest().getContextPath(), utmContent);
			productResult.setSponsored(product.isSponsored());
			if(requestWrapper.getCategoryType() == CategorizerConfig.HOTEL_TYPE)
			{
				if(product.getPrice() < 1.1d)
				{
					productResult.setPrice("?");	
				}
			}
			
			String sitename = productResult.getSourceName();
			SiteInfo siteInfo = SiteRegistry.getInstance().getSiteInfo(sitename);
			
			if(siteInfo != null && siteInfo.isFeatured())
			{
				productResult.setFeatured(true);
			}
			
			if(AffiliateProvider.getInstance().isAffiliate(sitename))
			{
				String newUrl = AffiliateProvider.getInstance().correctUrl(productResult.getLink(), sitename);
				productResult.setLink(newUrl);				
			}
//			queryStringHelper.setSearchQuery(escapeSameNameForQuery(product.getName()));
//			productResult.setSameNamedQueryLink(queryStringHelper.getRequestQuery());
			productResult.setSameNamedQueryLink(escapeSameNameForQuery(product.getName()));
			queryStringHelper.setFilterParam(RequestWrapper.BRAND, product.getBrand());
			productResult.setBrandLink(queryStringHelper.getRequestQuery());
			queryStringHelper.cleanFilterParam(RequestWrapper.BRAND);
			
			queryStringHelper.setFilterParam(RequestWrapper.STORE, product.getSourceName());
			productResult.setStoreLink(queryStringHelper.getRequestQuery());
			queryStringHelper.cleanFilterParam(RequestWrapper.STORE);
			
			productResult.setShareLink( shareURL
					+ "?"
					+ RequestWrapper.PRODUCT_URL
					+ "="
					+ encodeProductUrl(productResult.getLink()));

			//this shall be done after sharelink is set
			//otherwise when clicked to similar products
			//modified url is fetched and therefore no such url can be found in the index
			if(SiteLinkPrefixUpdater.getInstance().isShallBeChanged(sitename))
			{
				String newUrl = SiteLinkPrefixUpdater.getInstance().updateURL(productResult.getLink(), sitename);
				productResult.setLink(newUrl);
			}

			if(domainUtil != null)
			{
				productResult.setShareName(domainUtil.getShareTitle(productResult));
				productResult.setShareDesc(domainUtil.getShareDescription(productResult));
			}
			checkBannedProduct(productResult);
			resultList.add(productResult);
		}
	}

	String[] bannedNames = new String[] {"solgar", "calivita"};
	
	private void checkBannedProduct(ProductResult productResult) {
		//String name = productResult.getProductName();
		//Pattern pattern = Pattern.compile(".*[sS][oO][lL][gG][aA][rR].*");
		
		
		for(String name: bannedNames)
		{
			if(productResult.getBrand().equalsIgnoreCase(name))
			{
				productResult.setLink("http://www.karniyarik.com/urun/"+name+"-fiyatlari");
				productResult.setImageURL("");
				productResult.setSourceName("-");
				productResult.setSourceURL(productResult.getLink());
				productResult.setProductName("Yayınlanması Yasaklanmıştır");
				productResult.setBrand("İsmi Yasaklanmıştır");
				productResult.setPrice("0");
				productResult.setPriceCurrency("");
			}			
		}		
	}

	private boolean isBannedBrand(Count count) {
		for(String name: bannedNames)
		{
			if(count.getName().equalsIgnoreCase(name))
			{			
				count.setName("-");
				return true;
			}
		}		
		
		return false;
	}

	private String encodeProductUrl(String productUrl)
	{
		String encodedProductUrl = null;
		try
		{
			encodedProductUrl = URLEncoder.encode(productUrl, StringUtil.DEFAULT_ENCODING);
		}
		catch (UnsupportedEncodingException e)
		{
			// this exception will not occur
			encodedProductUrl = "";
		}
		return encodedProductUrl;
	}
	
	public static String escapeSameName(String name)
	{
		 name = StringEscapeUtils.escapeHtml(name);
		 name = name.replaceAll("\\\\", " ");
		 name = name.replaceAll("\\/", " ");
		 name = StringUtil.removeMultiEmptySpaces(name);
		 
		 return name;
	}
	
	public static String escapeSameNameForQuery(String name)
	{
		 name = StringEscapeUtils.unescapeHtml(name);
		 name = name.replaceAll("\\\\", " ");
		 name = name.replaceAll("\\/", " ");
		 name = name.replaceAll("\"", " ");
		 name = name.replaceAll("'", " ");
		 name = StringUtil.removeMultiEmptySpaces(name);
		 name = StringEscapeUtils.escapeJavaScript(name);
		 //name = name.replaceAll("&", " ");
		 
		 return name;
	}
	
}
