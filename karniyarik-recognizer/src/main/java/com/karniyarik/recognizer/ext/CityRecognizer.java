package com.karniyarik.recognizer.ext;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.recognizer.BaseFeatureRecognizer;


public class CityRecognizer extends BaseFeatureRecognizer
{
	public static final String featureName = "city.code";
	
	public CityRecognizer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String recognize(String value)
	{
		value = value.trim();
		String result = CityRegistry.getInstance().getCityName(value);
		if(StringUtils.isBlank(result))
		{
			result = null;
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
	
	public List<String> getCitiesInTurkey()
	{
		return CitiesInTurkeyRegistry.getInstance().getCities();
	}
	
	public String recognizeCityInTurkey(String value)
	{
		return CitiesInTurkeyRegistry.getInstance().getCity(value);
	}

}

 