package com.karniyarik.brands.nb.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.brands.nb.BrandHolder;
import com.karniyarik.brands.util.BrandsFileUtil;
import com.karniyarik.common.util.StringUtil;

public class BrandsTxtFileService implements BrandsFileService {

	private static final String ACTUAL_BRAND_DELIMETER = "<::>";
	private static final String ALTERNATE_BRAND_DELIMETER = "<>";

	@Override
	public void exportBrandHolders(List<BrandHolder> brandHolderList, File file) throws Exception
	{
		if (brandHolderList == null || file == null) {
			throw new IllegalArgumentException(
					"Input parameters can not be null.");
		}
		
		if (!file.exists()) {
			BrandsFileUtil.createFile(file);
		}

		List<String> lines = new ArrayList<String>();
		for (BrandHolder brandHolder : brandHolderList) {
			lines.add(createBrandHolderLine(brandHolder));
		}

		FileUtils.writeLines(file, StringUtil.DEFAULT_ENCODING, lines);
	}

	public List<BrandHolder> importBrandHolders(InputStream is) throws Exception {

		if (is == null) {
			throw new IllegalArgumentException("InputStream can not be null.");
		}

		List<BrandHolder> brandHolders = new ArrayList<BrandHolder>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(is, StringUtil.DEFAULT_ENCODING));
		String line = null;
		while((line = reader.readLine()) != null) {
			brandHolders.add(parseBrandHolderLine((String) line));			
		}

		return brandHolders;
	}

	private static String createBrandHolderLine(BrandHolder brandHolder) {
		String line = "";

		line += brandHolder.getActualBrand().trim() + ACTUAL_BRAND_DELIMETER;

		if (brandHolder.getListOfAlternateBrands() != null) {
			for (String alternateBrand : brandHolder.getListOfAlternateBrands()) {
				line += alternateBrand.trim() + ALTERNATE_BRAND_DELIMETER;
			}
		}

		return line.trim();
	}

	private static BrandHolder parseBrandHolderLine(String line) {
		BrandHolder holder = new BrandHolder();

		String[] splits = line.split(ACTUAL_BRAND_DELIMETER);

		String actualBrand = splits[0];
		holder.setActualBrand(actualBrand);

		if (splits.length > 1) {
			String alternateListString = splits[1];
			splits = alternateListString.split(ALTERNATE_BRAND_DELIMETER);
			List<String> alternateList = new ArrayList<String>();
			for (String string : splits) {
				if (StringUtils.isNotEmpty(string)) {
					alternateList.add(string);
				}
			}
			
			holder.setListOfAlternateBrands(alternateList);
		}

		return holder;
	}

}
