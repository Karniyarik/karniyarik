package com.karniyarik.search.lucene;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.karniyarik.common.vo.Product;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.filter.KarniyarikNumberRangeFilter;
import com.karniyarik.ir.filter.KarniyarikSort;
import com.karniyarik.ir.filter.KarniyarikTermFilter;
import com.karniyarik.search.searcher.ISearcher;
import com.karniyarik.search.searcher.QueryResult;
import com.karniyarik.search.searcher.SearcherFactory;
import com.karniyarik.search.searcher.query.Query;

public class QueryParserTests 
{
	private void executeAndPrintQuery(Query aQuery)
	{
		Date aStartDate = Calendar.getInstance().getTime();
		
		ISearcher aSearcher = new SearcherFactory().create("ürün");

		QueryResult aQueryResult = aSearcher.search(aQuery,false);
		
		Date anEndDate = Calendar.getInstance().getTime();
	
		printHits(aStartDate, aQueryResult, anEndDate);				
	}
	
	@Test
	public void testBasicQuery() throws Exception
	{
		Query aQuery = new Query();
		aQuery.setQueryString("nokia n95");
	
		executeAndPrintQuery(aQuery);		
	}

	@Test
	public void testQueryWithRelevanceSorting() throws Exception
	{
		Query aQuery = new Query();
		aQuery.setQueryString("nokia n95");
		aQuery.setSort(KarniyarikSort.RELEVANCE);
		
		executeAndPrintQuery(aQuery);		
	}

	@Test
	public void testQueryWithPriceLowToHighSorting() throws Exception
	{
		Query aQuery = new Query();
		aQuery.setQueryString("nokia n95");
		aQuery.setSort(KarniyarikSort.PRICE_LOW_TO_HIGH);
		executeAndPrintQuery(aQuery);
	}

	@Test
	public void testQueryWithPriceHighToLowSorting() throws Exception
	{
		Query aQuery = new Query();
		aQuery.setQueryString("nokia n95");
		aQuery.setSort(KarniyarikSort.PRICE_HIGH_TO_LOW);
		executeAndPrintQuery(aQuery);
	}

	@Test
	public void testQueryWithPriceFilter() throws Exception
	{
		Query aQuery = new Query();
		aQuery.setQueryString("nokia");
		aQuery.getCategorySpecificFilterList().add(new KarniyarikNumberRangeFilter(SearchConstants.PRICE, 2.0, 4.0));
		executeAndPrintQuery(aQuery);
	}	
	
	@Test
	public void testQueryWithBrandName() throws Exception
	{
		Query aQuery = new Query();
		aQuery.setQueryString("telefon");
		aQuery.getCategorySpecificFilterList().add(new KarniyarikTermFilter(SearchConstants.BRAND, "NOKIA"));
		executeAndPrintQuery(aQuery);
	}
	
	private void printHits(Date aStartDate, QueryResult aQueryResult, Date anEndDate)
	{
		System.out.println("Search finished in " + (anEndDate.getTime() - aStartDate.getTime())+ " m seconds");
		
		System.out.println("Total results: " + aQueryResult.getTotalHits());
		
		List<Product> aResults = aQueryResult.getResults();
		
		for(Product aProduct: aResults)
		{
			//System.out.print(aProduct.getCurrentIndex() + " - ");
			//System.out.print(aProduct.getScore()+ " - ");
			System.out.print(aProduct.getName() + " - ");
			System.out.print(aProduct.getBreadcrumb() + " - ");
			System.out.print(aProduct.getPrice() + " - ");
			System.out.print(aProduct.getBrand() + " - ");
			System.out.print(aProduct.getLink() + " - ");
			System.out.print(aProduct.getSourceName() + " - ");
			System.out.print(aProduct.getSourceURL());
			
			System.out.println("");
			//System.out.println("-----------------------------------");
			//System.out.println(aSearcher.getExplanation(aHits.id(anIndex)));			
		}
		System.out.println("###############################################\n\n");
	}

	@Test
	public void testBasicQueryWithHighlighting() throws Exception
	{
		Query aQuery = new Query();
		aQuery.setQueryString("telefon");
		aQuery.setUseHighLighter(true);
		
		executeAndPrintQuery(aQuery);
	}
}