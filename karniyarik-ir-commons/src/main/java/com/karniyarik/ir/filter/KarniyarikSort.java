package com.karniyarik.ir.filter;

import org.apache.solr.client.solrj.SolrQuery.ORDER;

import com.karniyarik.ir.SearchConstants;

public enum KarniyarikSort
{
	RELEVANCE(1, null, null),
	PRICE_LOW_TO_HIGH(2, SearchConstants.PRICE, ORDER.asc),
	PRICE_HIGH_TO_LOW(3, SearchConstants.PRICE, ORDER.desc),
	KM_LOW_TO_HIGH(4, SearchConstants.KM, ORDER.asc),
	KM_HIGH_TO_LOW(5, SearchConstants.KM, ORDER.desc),
	YEAR_LOW_TO_HIGH(6, SearchConstants.YEAR, ORDER.asc),
	YEAR_HIGH_TO_LOW(7, SearchConstants.YEAR, ORDER.desc),
	HP_LOW_TO_HIGH(8, SearchConstants.HP, ORDER.asc),
	HP_HIGH_TO_LOW(9, SearchConstants.HP, ORDER.desc),
	VIEW_COUNT(10, SearchConstants.VIEW_COUNT, ORDER.asc);
	
	private int mValue = -1;
	private String field = null;
	private ORDER order = null;
	
	KarniyarikSort(int aValue, String field, ORDER order)
	{	
		mValue = aValue;
		this.field = field;
		this.order = order;
	}
	
	public static KarniyarikSort getKarniyarikSort(int value){
		KarniyarikSort result = null;
		
		for (KarniyarikSort sort : KarniyarikSort.values())
		{
			if (sort.getValue() == value)
			{
				result = sort;
				break;
			}
		}
		return result;
	}

	public String getField() {
		return field;
	}
	
	public ORDER getOrder() {
		return order;
	}
	
	public int getValue() {
		return mValue;
	}
	
//	public Sort getLuceneSort()
//	{
//		switch(mValue)
//		{
//			case 1: 
//				return Sort.RELEVANCE;
//			case 2: 
//				return new Sort(new SortField(SearchConstants.PRICE, SortField.DOUBLE));
//			case 3: 
//				return new Sort(new SortField(SearchConstants.PRICE, SortField.DOUBLE, true));
//			case 4: 
//				return new Sort(new SortField("km", SortField.DOUBLE));
//			case 5: 
//				return new Sort(new SortField("km", SortField.DOUBLE, true));
//			case 6: 
//				return new Sort(new SortField("modelyear", SortField.DOUBLE));
//			case 7: 
//				return new Sort(new SortField("modelyear", SortField.DOUBLE, true));
//			case 8: 
//				return new Sort(new SortField("enginepower", SortField.DOUBLE));
//			case 9: 
//				return new Sort(new SortField("enginepower", SortField.DOUBLE, true));
//			case 10: 
//				return new Sort(new SortField(SearchConstants.VIEW_COUNT, SortField.LONG));				
//			default: return null;
//		}
//	}	

}
