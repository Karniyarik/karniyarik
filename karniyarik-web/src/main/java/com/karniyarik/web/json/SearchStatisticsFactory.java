package com.karniyarik.web.json;



public class SearchStatisticsFactory
{

	public SearchStatistics create(int catType) {
		SearchStatistics log = new SearchStatistics(catType); 
		return log;
	}
	
}
