package com.karniyarik.recognizer.ext;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.recognizer.BaseFeatureRecognizer;


public class CountryCodeRecognizer extends BaseFeatureRecognizer
{
	public static final String featureName = "country.code";
	
	public CountryCodeRecognizer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String recognize(String value)
	{
		value = value.trim();
		String result = CountryRegistry.getInstance().getCountyName(value);
		if(StringUtils.isBlank(result))
		{
			result = value;
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
		return value;
	}	
}

 