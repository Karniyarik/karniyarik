package com.karniyarik.search.searcher;

import java.util.List;
import java.util.Map;

import com.karniyarik.search.searcher.query.Query;
import com.karniyarik.search.sponsored.SponsoredMerchantService;

public interface ISearcher
{
	public QueryResult search(Query aQuery, boolean doLog);
	public QueryResult search(String productUrl, boolean doLog);
	public int countDocsBySite(String siteName);
	public Map<String, Integer> getSiteProductCounts();
	public int getTotalProductCountInSystem();
	public void refreshSponsoredMerchantService(SponsoredMerchantService newService);
	public List<String> autocomplete(String term);
}
