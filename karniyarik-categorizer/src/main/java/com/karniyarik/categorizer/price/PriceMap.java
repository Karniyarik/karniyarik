package com.karniyarik.categorizer.price;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PriceMap implements Serializable
{
	private Map<String, CategoryPrice>	catPriceMap	= new HashMap<String, CategoryPrice>();
	
	public PriceMap()
	{
	}
	
	public Map<String, CategoryPrice> getCatPriceMap()
	{
		return catPriceMap;
	}
}
