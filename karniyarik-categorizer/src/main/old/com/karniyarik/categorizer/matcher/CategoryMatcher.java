package com.karniyarik.categorizer.matcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.categorizer.io.MatchingRuleReader;
import com.karniyarik.categorizer.io.MatchingRuleWriter;

public class CategoryMatcher
{
	private Map<String, String>	mMatchingRules			= null;
	private String				mSiteName				= null;
	private List<String>		mCategoryIDs			= null;
	private SiteCategoryFetcher	mCategoryIDExtractor	= null;

	public CategoryMatcher()
	{
		mMatchingRules = new HashMap<String, String>();
		mCategoryIDExtractor = new SiteCategoryFetcher();
	}

	public void readRules(String aFileName)
	{
		MatchingRuleReader aMatchingRuleReader = new MatchingRuleReader();
		aMatchingRuleReader.read(aFileName);
		mMatchingRules = aMatchingRuleReader.getMatchingRules();
		mSiteName = aMatchingRuleReader.getSiteName();
		mCategoryIDs = aMatchingRuleReader.getCategoryIDs();
	}

	public void writeRules(String aFileName)
	{
		new MatchingRuleWriter().write(mMatchingRules, mSiteName, mCategoryIDs, aFileName);
	}

	public String getMatchedCategory(String aLHS)
	{
		return mMatchingRules.get(aLHS);
	}

	public void addMatchingRule(String aLHS, String aRHS)
	{
		mMatchingRules.put(aLHS, aRHS);
	}

	public void setSiteName(String aSiteName)
	{
		mSiteName = aSiteName;
	}

	public Map<String, String> getRules()
	{
		return mMatchingRules;
	}

	public String getSiteName()
	{
		return mSiteName;
	}

	public void setCategoryIDs(List<String> aCategoryIDs)
	{
		mCategoryIDs = aCategoryIDs;
	}

	public void clearMatchingRules()
	{
		mMatchingRules.clear();
	}

	public List<String> getCategoryIDs()
	{
		return mCategoryIDs;
	}

	public String getCategoryID(String aURL, String aBreadCrumb)
	{
		return mCategoryIDExtractor.getCategoryID(mCategoryIDs, aURL, aBreadCrumb);
	}
}
