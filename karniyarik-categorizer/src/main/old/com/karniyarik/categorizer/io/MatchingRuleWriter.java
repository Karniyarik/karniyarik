package com.karniyarik.categorizer.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.karniyarik.common.exception.KarniyarikBaseException;

public class MatchingRuleWriter
{

	public void write(Map<String, String> aMatchingRules, String aSiteName, List<String> aCategoryIDs, String aFileName)
	{
		try
		{
			File aFile = new File(aFileName);

			List<String> aLines = new ArrayList<String>();

			aLines.add(aSiteName);
			
			String aCategoryIDStr = "";
			
			for(String aCatID: aCategoryIDs)
			{
				aCategoryIDStr += aCatID + ",";
			}
			
			aLines.add(aCategoryIDStr);
			
			for (String aMatchingRuleLHS : aMatchingRules.keySet())
			{
				aLines.add(aMatchingRuleLHS + CategoryIOUtils.MATCHING_RULE_DELIMITER + aMatchingRules.get(aMatchingRuleLHS));
			}

			FileUtils.writeLines(aFile, aLines);
		}
		catch (IOException e)
		{
			throw new KarniyarikBaseException(e);
		}
	}

}
