package com.karniyarik.recognizer.ext;

import java.util.List;

import com.karniyarik.recognizer.BaseFeatureRecognizer;
import com.karniyarik.recognizer.ScoreHit;


public class FuelRecognizer extends BaseFeatureRecognizer
{
	public static final String featureName = "fueltype";
	public static int success = 0;
	public static int total = 0;
	
	@Override
	public String recognize(String value)
	{
		total += 1;
		
		value = removeNonTrimmableSpaces(value);
		value = convertTurkishChars(value);
		value = toLowercase(value);
		value = removePunctuations(value, " ");
		value = removeWhitespaces(value, " ");
		value = value.trim();
		
		List<ScoreHit> hitList = findHits(featureName, value);
		
		String result = null;
		
		if(hitList.size() > 0)
		{
			result = hitList.get(0).getValue();
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

 