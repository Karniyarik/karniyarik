package com.karniyarik.recognizer;

import java.util.HashMap;
import java.util.Map;

import net.zemberek.erisim.Zemberek;

import com.karniyarik.recognizer.ext.BrandRecognizer;
import com.karniyarik.recognizer.ext.CarModelRecognizer;
import com.karniyarik.recognizer.ext.CityRecognizer;
import com.karniyarik.recognizer.ext.ColorRecognizer;
import com.karniyarik.recognizer.ext.CountryCodeRecognizer;
import com.karniyarik.recognizer.ext.FuelRecognizer;
import com.karniyarik.recognizer.ext.GearRecognizer;

public class ControllerMap
{
	private Map<String, Class<? extends BaseFeatureRecognizer>> recognizers = new HashMap<String, Class<? extends BaseFeatureRecognizer>>();
	private Zemberek zemberek;
	
	public ControllerMap(Zemberek zemberek)
	{
		this.zemberek = zemberek;
		recognizers.put(ColorRecognizer.featureName, ColorRecognizer.class);
		recognizers.put(GearRecognizer.featureName, GearRecognizer.class);
		recognizers.put(FuelRecognizer.featureName, FuelRecognizer.class);
		recognizers.put(CarModelRecognizer.featureName, CarModelRecognizer.class);
		recognizers.put(BrandRecognizer.featureName, BrandRecognizer.class);
		recognizers.put(CountryCodeRecognizer.featureName, CountryCodeRecognizer.class);
		recognizers.put(CityRecognizer.featureName, CityRecognizer.class);
	}

	public BaseFeatureRecognizer getRecognizer(String type)
	{
		BaseFeatureRecognizer recognizer = null;
		
		try
		{
			if(recognizers.get(type) != null)
			{
				recognizer = recognizers.get(type).newInstance();
				recognizer.setZemberek(zemberek);
			}
		}
		catch (Throwable  e)
		{
			throw new RuntimeException(e);
		}
		
		return recognizer;
	}

}
