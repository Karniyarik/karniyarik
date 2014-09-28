package com.karniyarik.brands.supervisor;

import java.util.Comparator;
import java.util.Locale;

import com.karniyarik.brands.BrandHolder;

public class BrandsSortComparator implements Comparator<BrandHolder>
{
	@Override
	public int compare(BrandHolder aO1, BrandHolder aO2)
	{
		return aO1.getActualBrand().toLowerCase(Locale.ENGLISH).compareTo(aO2.getActualBrand().toLowerCase(Locale.ENGLISH));
	}
}
