package com.karniyarik.categorizer.index;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IndexTerm
{
	private Map<String, TermCategory>	mTermCategoryMap	= null;
	private String						mTerm				= null;
	private int							mTotalFreq			= -1;
	
	public IndexTerm(String aTerm)
	{
		mTermCategoryMap = new HashMap<String, TermCategory>();
		mTerm = aTerm;
	}

	public void remove(String aCategoryID)
	{
		if (mTermCategoryMap.containsKey(aCategoryID))
		{
			mTermCategoryMap.remove(aCategoryID);
		}
	}

	public void add(String aCategoryID, TermCategory aTermCategory)
	{
		mTermCategoryMap.put(aCategoryID, aTermCategory);
	}

	public TermCategory get(String aCategoryID)
	{
		return mTermCategoryMap.get(aCategoryID);
	}

	public Collection<TermCategory> getTermCategories()
	{
		return mTermCategoryMap.values();
	}

	public int getDocumentFreq()
	{
		return mTermCategoryMap.values().size();
	}

	public int getTotalFreq()
	{
		if(mTotalFreq == -1)
		{
			mTotalFreq = 0;
			
			for(TermCategory aTermCategory: getTermCategories())
			{
				mTotalFreq += aTermCategory.getFreq();
			}
		}
		
		return mTotalFreq;
	}

	public String getTerm()
	{
		return mTerm;
	}
}
