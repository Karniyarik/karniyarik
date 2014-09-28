/**
 * 
 */
package com.karniyarik.brands;

/**
 * @author meralan
 *
 */
public interface BrandComparator {

	boolean areBrandsALike(String candidateBrand, String actualBrand);

	boolean areBrandsCaseInsensitiveSame(String brand, String actualBrand);
}
