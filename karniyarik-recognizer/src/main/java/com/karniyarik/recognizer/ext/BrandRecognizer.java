package com.karniyarik.recognizer.ext;

import com.karniyarik.recognizer.BaseFeatureRecognizer;

public class BrandRecognizer extends BaseFeatureRecognizer  
{
	public static final String featureName = "car.brands";
	
	@Override
	public String recognize(String value)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String resolve(String sentence)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String normalize(String str)
	{
		str = convertTurkishChars(str);
		str = toLowercase(str);
		str = removeWhitespaces(str,"");
		return str;
	}
	
}
