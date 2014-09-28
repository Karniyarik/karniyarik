package com.karniyarik.web.util;

import java.util.ArrayList;
import java.util.List;

import com.karniyarik.citydeal.City;
import com.karniyarik.recognizer.ext.CitiesInTurkeyRegistry;
import com.karniyarik.web.citydeals.CityResult;

public class CityUtil {
	public static List<CityResult> getCitiesInTurkey()
	{
		List<CityResult> result = new ArrayList<CityResult>();
		
		List<String> cities = CitiesInTurkeyRegistry.getInstance().getCities();
		for(String city:cities)
		{
			CityResult cityResult = new CityResult();
			cityResult.setName(city);
			cityResult.setValue(City.getValue(city));
			result.add(cityResult);
			
		}
		return result;
	}
}
