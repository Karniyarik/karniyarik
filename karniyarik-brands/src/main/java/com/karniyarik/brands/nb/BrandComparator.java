/**
 * 
 */
package com.karniyarik.brands.nb;

/**
 * @author meralan
 *
 */
public interface BrandComparator {

	boolean areBrandsALike(String candidateBrand, String actualBrand);

	boolean areBrandsCaseInsensitiveSame(String brand, String actualBrand);
}
