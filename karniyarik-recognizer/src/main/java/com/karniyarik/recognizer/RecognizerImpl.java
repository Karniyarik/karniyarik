package com.karniyarik.recognizer;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.recognizer.ext.CarModelRecognizer;
import com.karniyarik.recognizer.ext.CitiesInTurkeyRegistry;
import com.karniyarik.recognizer.ext.CityRecognizer;
import com.karniyarik.recognizer.ext.ColorRecognizer;
import com.karniyarik.recognizer.ext.CountryCodeRecognizer;
import com.karniyarik.recognizer.ext.FuelRecognizer;
import com.karniyarik.recognizer.ext.GearRecognizer;

public class RecognizerImpl
{
	private static RecognizerImpl	instance		= null;

	private ItemStore				itemStore		= null;
	private ControllerMap			controllerMap	= null;
	private Zemberek				zemberek		= null;

	private RecognizerImpl()
	{
		zemberek = new Zemberek(new TurkiyeTurkcesi());
		controllerMap = new ControllerMap(zemberek);
		itemStore = new ItemStore(controllerMap);
		itemStore.read();
	}

	public static RecognizerImpl getInstance()
	{
		if (instance == null)
		{
			instance = new RecognizerImpl();
		}
		return instance;
	}

	private BaseFeatureRecognizer getRecognizer(String type)
	{
		BaseFeatureRecognizer recognizer = controllerMap.getRecognizer(type);
		recognizer.setItemStore(itemStore);
		recognizer.setZemberek(zemberek);
		return recognizer;
	}

	public String recognize(String value, String type)
	{
		return recognize(value, type, true);
	}

	public String recognize(String value, String type, boolean chechDefaultValue)
	{
		BaseFeatureRecognizer recognizer = getRecognizer(type);
		String recognize = recognizer.recognize(value);
		if(chechDefaultValue)
		{
			return checkDefaultValue(recognize);	
		}
		return recognize;
	}

	private String resolve(String value, String type)
	{
		BaseFeatureRecognizer recognizer = getRecognizer(type);
		return checkDefaultValue(recognizer.resolve(value));
	}

	public String recognizeColor(String color)
	{
		return recognize(color, ColorRecognizer.featureName);
	}

	public String recognizeCountry(String country)
	{
		return recognize(country, CountryCodeRecognizer.featureName);
	}

	public String recognizeCity(String city)
	{
		return recognize(city, CityRecognizer.featureName, false);
	}

	public String recognizeCity(String city, boolean checkOnesInTurkey, boolean returnSameOnFail)
	{
		String recognizedCity = recognizeCity(city);
		if(StringUtils.isBlank(recognizedCity) && checkOnesInTurkey)
		{
			recognizedCity = CitiesInTurkeyRegistry.getInstance().getCity(city);
		}
		
		if(returnSameOnFail && StringUtils.isBlank(recognizedCity))
		{
			recognizedCity = city;
		}			
		
		return recognizedCity;
	}

	public String recognizeGear(String gear)
	{
		return recognize(gear, GearRecognizer.featureName);
	}

	public String recognizeFuel(String fuel)
	{
		return recognize(fuel, FuelRecognizer.featureName);
	}

	public String resolveCarModel(String line)
	{
		return resolve(line, CarModelRecognizer.featureName);
	}

	private String checkDefaultValue(String value)
	{
		if (StringUtils.isBlank(value))
		{
			value = BaseFeatureRecognizer.DEFAULT_VALUE;
		}
		return value;
	}
	
	public static void main(String[] args) {
		RecognizerImpl recognizerImpl = new RecognizerImpl();
		String recognizeCity = recognizerImpl.recognizeCity("ankara");
		System.out.println(recognizeCity);
		
		recognizeCity = recognizerImpl.recognizeCity("İstanbul/Avrupa", true, true);
		System.out.println(recognizeCity);
	}
}
