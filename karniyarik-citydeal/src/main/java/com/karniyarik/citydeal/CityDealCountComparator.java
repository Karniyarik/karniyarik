package com.karniyarik.citydeal;

import java.util.Comparator;


public class CityDealCountComparator implements Comparator<City>{
	@Override
	public int compare(City o1, City o2) {
		return Integer.valueOf(o2.getDealCount()).compareTo(o1.getDealCount());
	}
}
