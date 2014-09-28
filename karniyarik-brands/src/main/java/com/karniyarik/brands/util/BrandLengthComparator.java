package com.karniyarik.brands.util;

import java.util.Comparator;

public class BrandLengthComparator implements Comparator<String> {

	@Override
	public int compare(String brand1, String brand2) {
		return -(brand1.length() - brand2.length());
	}

}
