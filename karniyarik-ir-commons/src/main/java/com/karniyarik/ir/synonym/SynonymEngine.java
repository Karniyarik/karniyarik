package com.karniyarik.ir.synonym;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.karniyarik.common.util.StreamUtil;

public class SynonymEngine
{	
	private static Map<String, String[]> mSynonymMap = null;
	
	public SynonymEngine()
	{
		if(mSynonymMap == null)
		{
			constructSynonyms();
		}
	}

	private void constructSynonyms()
	{
		try
		{
			InputStream aStream = StreamUtil.getStream("conf/dictionary/synonyms.txt");
			
			BufferedReader aReader = new BufferedReader(new InputStreamReader(aStream));

			mSynonymMap = new HashMap<String, String[]>();
			
			String aLine = null;
			String[] aSynonyms = null;
			
			while(true)
			{
				aLine = aReader.readLine();
				
				if(aLine == null)
				{
					break;
				}
				
				aSynonyms = aLine.split("/");
				
				for(String aSynonym: aSynonyms)
				{
					mSynonymMap.put(aSynonym, aSynonyms);
				}

			}
			
			aReader.close();
		} 
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot find synonyms file", e);
		}
	}
	
	public String[] getSnonyms(String aWord)
	{
		return mSynonymMap.get(aWord);
	}
}
