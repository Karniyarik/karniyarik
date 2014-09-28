package com.karniyarik.citydeal;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;


public class CityNameComparator implements Comparator<City>{
	
	Collator collator = null;
	
	public CityNameComparator() {
		collator = Collator.getInstance(new Locale("tr"));
		collator.setStrength(Collator.PRIMARY);
	}
	
	@Override
	public int compare(City o1, City o2) {
		int result = Integer.valueOf(o2.getDealCount()).compareTo(o1.getDealCount());
		if(result == 0)
		{
			result = collator.compare(o1.getName(), o2.getName());
		}
		return result; 
	}
}
