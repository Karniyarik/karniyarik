package com.karniyarik.web.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

public class SearchResult
{
	private String							query					= "";
	private String							originalQuery			= "";
	private int							totalHits				= 0;
	private String							timeTaken				= null;
	private int								pageNumber				= 0;
	private int								pageSize				= 0;
	private int								sort					= 1;
	private int								showImages				= 0;
	//private boolean						canUseOr				= false;
	private int								siteCount				= 0;
	private String							categoryFilter			= null;
	private String							pageTitle				= null;
	private String							pageDescription			= null;
	private String							pageKeywords			= null;
	private LinkedLabel						moreResults				= null;
	private List<LinkedLabel>				suggestedWords			= null;
	private List<ProductResult>				results					= null;
	private List<ProductResult>				sponsoredProducts		= null;
	private List<ProductResult>				similarProducts			= null;
	private List<LinkedLabel>				pagerResults			= null;
	private List<LinkedLabel>				suggestedQueries		= null;
	private Map<String, List<LinkedLabel>>	categoryPropMap			= null;
	private String							pageHeader				= null;
	private String							canonical				= null;
	private boolean							showFilters				= false;
	private boolean							showProducts			= false;
	private boolean							showNotFound			= false;
	private boolean							showEmptySearchError	= false;
	private boolean							showURLNotFoundError	= false;
	private boolean							showSuggestion			= false;
	private boolean							showMoreResults			= false;
	private boolean							showToolbar				= false;
	private boolean							showSimilarityGauge		= false;
	private List<String>					showFilterPages			= null;
	private boolean							showPageHeader			= true;
	private boolean							isSimilar				= false;
	private List<LinkedLabel>				breadcrumb				= null;
	private String							pageImgSrc 				= null;
	private Map<String, CrossDomainSearchResult>	otherVerticalResults	= null;
	
	public SearchResult()
	{
		results = new ArrayList<ProductResult>();
		similarProducts = new ArrayList<ProductResult>();
		sponsoredProducts = new ArrayList<ProductResult>();
		suggestedWords = new ArrayList<LinkedLabel>();
		pagerResults = new ArrayList<LinkedLabel>();
		showFilterPages = new ArrayList<String>();
		categoryPropMap = new HashMap<String, List<LinkedLabel>>();
		suggestedQueries = new ArrayList<LinkedLabel>();
		breadcrumb = new ArrayList<LinkedLabel>();
		otherVerticalResults = new HashMap<String, CrossDomainSearchResult>();
	}

	public String getQuery()
	{
		return query;
	}

	public String getEscapedQuery()
	{
		//return StringEscapeUtils.escapeJava(query);
		//javayi neden escape ettigimizi anlamadim
		return StringEscapeUtils.escapeHtml(getOriginalQuery());
	}

	public void setQuery(String aQuery)
	{
		query = StringEscapeUtils.escapeHtml(aQuery);
	}

	public int getTotalHits()
	{
		return totalHits;
	}

	public void setTotalHits(int aTotalHits)
	{
		totalHits = aTotalHits;
	}

	public String getTimeTaken()
	{
		return timeTaken;
	}

	public void setTimeTaken(String aTimeTaken)
	{
		timeTaken = aTimeTaken;
	}

	public List<LinkedLabel> getSuggestedWords()
	{
		return suggestedWords;
	}

	public void setSuggestedWords(List<LinkedLabel> aSuggestedWords)
	{
		suggestedWords = aSuggestedWords;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(int aPageNumber)
	{
		pageNumber = aPageNumber;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int aPageSize)
	{
		pageSize = aPageSize;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int aSort)
	{
		sort = aSort;
	}

//	public boolean isCanUseOr()
//	{
//		return canUseOr;
//	}
//
//	public void setCanUseOr(boolean aCanUseOr)
//	{
//		canUseOr = aCanUseOr;
//	}

	public List<ProductResult> getResults()
	{
		return results;
	}

	public void setResults(List<ProductResult> aResults)
	{
		results = aResults;
	}

	public int getSiteCount()
	{
		return siteCount;
	}

	public List<ProductResult> getSimilarProducts()
	{
		return similarProducts;
	}

	public boolean isShowPageHeader()
	{
		return showPageHeader;
	}

	public void setShowPageHeader(boolean showPageHeader)
	{
		this.showPageHeader = showPageHeader;
	}

	public void setSimilarProducts(List<ProductResult> similarProducts)
	{
		this.similarProducts = similarProducts;
	}

	public void setSiteCount(int aSiteCount)
	{
		siteCount = aSiteCount;
	}

	public int isShowImages()
	{
		return showImages;
	}

	public void setShowImages(int aShowImages)
	{
		showImages = aShowImages;
	}

	public int getShowImages()
	{
		return showImages;
	}

	public LinkedLabel getMoreResults()
	{
		return moreResults;
	}

	public void setMoreResults(LinkedLabel aMoreResults)
	{
		moreResults = aMoreResults;
	}

	public List<LinkedLabel> getPagerResults()
	{
		return pagerResults;
	}

	public void setPagerResults(List<LinkedLabel> aPagerResults)
	{
		pagerResults = aPagerResults;
	}

	public boolean isShowFilters()
	{
		return showFilters;
	}

	public void setShowFilters(boolean aShowFilters)
	{
		showFilters = aShowFilters;
	}

	public boolean isShowProducts()
	{
		return showProducts;
	}

	public void setShowProducts(boolean aShowProducts)
	{
		showProducts = aShowProducts;
	}

	public boolean isShowNotFound()
	{
		return showNotFound;
	}

	public void setShowNotFound(boolean aShowNotFound)
	{
		showNotFound = aShowNotFound;
	}

	public boolean isShowEmptySearchError()
	{
		return showEmptySearchError;
	}

	public void setShowURLNotFoundError(boolean showURLNotFoundError) {
		this.showURLNotFoundError = showURLNotFoundError;
	}
	
	public boolean isShowURLNotFoundError() {
		return showURLNotFoundError;
	}
	
	public void setShowEmptySearchError(boolean aShowEmptySearchError)
	{
		showEmptySearchError = aShowEmptySearchError;
	}

	public boolean isShowSuggestion()
	{
		return showSuggestion;
	}

	public void setShowSuggestion(boolean aShowSuggestion)
	{
		showSuggestion = aShowSuggestion;
	}

	public boolean isShowMoreResults()
	{
		return showMoreResults;
	}

	public void setShowMoreResults(boolean aShowMoreResults)
	{
		showMoreResults = aShowMoreResults;
	}

	public boolean isShowToolbar()
	{
		return showToolbar;
	}

	public void setShowToolbar(boolean aShowToolbar)
	{
		showToolbar = aShowToolbar;
	}

	public boolean isShowSimilarityGauge()
	{
		return showSimilarityGauge;
	}

	public void setShowSimilarityGauge(boolean aShowSimilarityGauge)
	{
		showSimilarityGauge = aShowSimilarityGauge;
	}

	public String getPageTitle()
	{
		return pageTitle;
	}

	public void setPageTitle(String aPageTitle)
	{
		pageTitle = StringEscapeUtils.escapeHtml(aPageTitle);
	}

	public String getPageDescription()
	{
		return pageDescription;
	}

	public void setPageDescription(String aPageDescription)
	{
		//pageDescription = StringUtil.removePunctiations(aPageDescription);
		pageDescription = StringEscapeUtils.escapeHtml(aPageDescription);
	}

	public String getPageKeywords() {
		return pageKeywords;
	}
	
	public void setPageKeywords(String pageKeywords) {
		//this.pageKeywords = StringUtil.removePunctiations(pageKeywords );
		this.pageKeywords = StringEscapeUtils.escapeHtml(pageKeywords);
	}
	
	public String getCategoryFilter()
	{
		return categoryFilter;
	}

	public void setCategoryFilter(String aCategoryFilter)
	{
		categoryFilter = aCategoryFilter;
	}

	public List<String> getShowFilterPages()
	{
		return showFilterPages;
	}

	public List<ProductResult> getSponsoredProducts()
	{
		return sponsoredProducts;
	}

	public void setSponsoredProducts(List<ProductResult> sponsoredProducts)
	{
		this.sponsoredProducts = sponsoredProducts;
	}

	public Map<String, List<LinkedLabel>> getCategoryPropMap()
	{
		return categoryPropMap;
	}

	public void setCategoryPropMap(Map<String, List<LinkedLabel>> categoryPropMap)
	{
		this.categoryPropMap = categoryPropMap;
	}

	public List<LinkedLabel> getSuggestedQueries()
	{
		return suggestedQueries;
	}

	public void setSuggestedQueries(List<LinkedLabel> suggestedQueries)
	{
		this.suggestedQueries = suggestedQueries;
	}
	
	public void setSimilar(boolean isSimilar) {
		this.isSimilar = isSimilar;
	}
	
	public boolean isSimilar() {
		return isSimilar;
	}
	
	public void setPageHeader(String pageHeader) {
		this.pageHeader = pageHeader;
	}
	
	public String getPageHeader() {
		return pageHeader;
	}
	
	public String getOriginalQuery() {
		return originalQuery;
	}
	
	public void setOriginalQuery(String originalQuery) {
		this.originalQuery = originalQuery;
	}	
	
	public String getCanonical()
	{
		return canonical;
	}
	
	public void setCanonical(String canonical)
	{
		this.canonical = canonical;
	}
	
	public List<LinkedLabel> getBreadcrumb()
	{
		return breadcrumb;
	}
	
	public void setBreadcrumb(List<LinkedLabel> breadcrumb)
	{
		this.breadcrumb = breadcrumb;
	}
	
	public String getPageImgSrc() {
		return pageImgSrc;
	}
	
	public void setPageImgSrc(String pageImgSrc) {
		this.pageImgSrc = pageImgSrc;
	}
	
	public Map<String, CrossDomainSearchResult> getOtherVerticalResults() {
		return otherVerticalResults;
	}
}