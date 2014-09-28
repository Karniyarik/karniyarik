package com.karniyarik.brands;

import java.util.Locale;

import com.karniyarik.common.util.StringUtil;

/**
 * @author meralan
 *
 */
public class BrandComparatorImpl implements BrandComparator {

	public boolean areBrandsALike(String candidateBrand, String actualBrand) {

		return normalize(actualBrand).contains(normalize(candidateBrand)) ||
			normalize(candidateBrand).contains(normalize(actualBrand));
	}

	@Override
	public boolean areBrandsCaseInsensitiveSame(String brand, String actualBrand) {
		return normalize(actualBrand).equals(normalize(brand));
	}

	public String normalize(String aStr)
	{
		return StringUtil.convertTurkishCharacter(aStr).toLowerCase(Locale.ENGLISH);
	}

}
