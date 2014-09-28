package com.karniyarik.recognizer.ext;

import java.util.List;
import java.util.Locale;

import com.karniyarik.recognizer.BaseFeatureRecognizer;
import com.karniyarik.recognizer.ScoreHit;


public class ColorRecognizer extends BaseFeatureRecognizer
{
	public static final String featureName = "colors";
	private static final String[] metalikConstants = new String[]{"m.", "metalik", "met-", "-met", "metal", "met.", "met", "mt."};
	private static final String[] saturationConstants = new String[]{"acik", "koyu"};
	public static int success = 0;
	public static int total = 0;
	
	@Override
	public String recognize(String value)
	{
		total += 1;
		
		value = removeNonTrimmableSpaces(value);
		value = value.trim();
		value = convertTurkishChars(value);
		value = value.toLowerCase(Locale.ENGLISH);
		
		boolean isMetalik = false;
		
		for(String metalik: metalikConstants)
		{
			if(value.contains(metalik))
			{
				isMetalik = true;
				value = value.replace(metalik, " ");
			}
		}

		for(String saturation: saturationConstants)
		{
			if(value.contains(saturation))
			{
				value = value.replace(saturation, " ");
			}
		}
		
		value = removePunctuations(value, " ");
		value = removeWhitespaces(value, " ");
		value = value.trim();
		
		List<ScoreHit> hitList = findHits(featureName, value);
		
		String result = null;
		if(hitList.size() > 0)
		{
			result = hitList.get(0).getValue();
			if(isMetalik)
			{
				result += " Metalik";
			}			
			success += 1;
		}
		
		return result;
	}
	
	@Override
	public String resolve(String sentence)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String normalize(String value)
	{
		value = convertTurkishChars(value);
		value = toLowercase(value);
		value = removePunctuations(value,"");
		value = removeWhitespaces(value,"");
		return value;
	}	
}

 