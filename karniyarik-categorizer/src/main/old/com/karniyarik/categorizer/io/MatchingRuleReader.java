package com.karniyarik.categorizer.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.exception.KarniyarikBaseException;

public class MatchingRuleReader
{
	private String mSiteName = null;
	private List<String> mCategoryIDs = null;
	private Map<String, String> mMatchingRules = null;
	
	public void read(String aFileName)
	{
		mSiteName = null;
		mCategoryIDs = new ArrayList<String>();
		mMatchingRules = new HashMap<String, String>();
		
		try
		{
			File aFile = new File(aFileName);
			List<String> aLines = FileUtils.readLines(aFile);
			String[] aRuleSides = null;		
						
			int anIndex = 0;
			
			for(String aLine: aLines)
			{
				if(anIndex == 0 )
				{
					mSiteName = aLine.trim();
					anIndex++;
				}
				else if(anIndex == 1)
				{
					String aCategoryIDStr = aLine.trim();
					String[] aCatIDs = aCategoryIDStr.split(",");
					List<String> aList = new ArrayList<String>();
					for(String aCatID: aCatIDs)
					{
						if(StringUtils.isNotBlank(aCatID))
						{
							mCategoryIDs.add(aCatID);
						}
					}
					
					anIndex++;
				}
				else
				{
					aRuleSides = aLine.trim().split(CategoryIOUtils.MATCHING_RULE_DELIMITER);
					mMatchingRules.put(aRuleSides[0], aRuleSides[1]);
				}
			}					
		}
		catch (IOException e)
		{
			throw new KarniyarikBaseException();
		}
	}

	public String getSiteName()
	{
		return mSiteName;
	}

	public List<String> getCategoryIDs()
	{
		return mCategoryIDs;
	}

	public Map<String, String> getMatchingRules()
	{
		return mMatchingRules;
	}	
}
