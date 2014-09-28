package com.karniyarik.brands;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author meralan
 *
 */
public interface BrandService {

	void organizeBrands(List<String> listOfBrands);
	String getActualBrand(String candidateBrand);
	String resolveBrand(String longProductName);
	boolean isBrandRecognized(String aBrand);
	public List<BrandHolder> getAllBrands();
	void exportTo(File file) throws Exception;
	void importFrom(InputStream is) throws Exception;
	void updateBrandKB(String content);
}
