package com.karniyarik.brands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;

import com.karniyarik.brands.file.BrandsFileService;
import com.karniyarik.brands.file.BrandsTxtFileService;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;

public class BrandStore
{
	public static final String BRANDS_TXT_FILE = "dictionary/karniyarik_brands.txt";

	private List<BrandHolder> brandHolderList = null;
	private BrandComparator brandComparator = null;
	private HashMap<String, String> brandHashMap = null;
	private BrandsFileService fileService = null;
	
	private static BrandStore instance = null;
	
	public BrandStore(boolean initialized)
	{
		initialize(initialized);
	}

	private void initialize(boolean initialized) {
		if (initialized) 
		{
			try {
				brandHolderList = getAllBrands();
				brandHashMap = constructBrandHashMap();
			} catch (Throwable e) {
				throw new RuntimeException("Cannot read brands file " + StreamUtil.getURL(BRANDS_TXT_FILE), e); 
			} 
		} else {
			brandHolderList = new ArrayList<BrandHolder>();
			brandHashMap = new HashMap<String, String>();
		}
		brandComparator = new BrandComparatorImpl();
	}

	public static BrandStore getInstance(boolean initialized)
	{
		if(instance == null)
		{
			instance = new BrandStore(initialized);
		}
		return instance;
	}
	
	public List<BrandHolder> getAllBrands() {
		try {
			
			InputStream is = null;
			
			try
			{
				is = StreamUtil.getStream(BRANDS_TXT_FILE);
			}
			catch (Exception e)
			{
				is = StreamUtil.getStream("conf/" + BRANDS_TXT_FILE);
			}
			
			if(is == null)
			{
				throw new RuntimeException("Cannot find brands file");
			}
			
			fileService = new BrandsTxtFileService();
			List<BrandHolder> importBrandHolders = fileService.importBrandHolders(is);
			is.close();
			
			return importBrandHolders;
		} catch (Throwable e) {
			throw new RuntimeException("Cannot get brands holders file: " + BRANDS_TXT_FILE, e);
		}		
	}

	public void updateBrandsFile(String content) {
		try {
			File file = StreamUtil.getFile(BRANDS_TXT_FILE);
			if(file.exists())
			{
				file.delete();
			}
			FileOutputStream os = new FileOutputStream(file);
			IOUtils.write(content, os, StringUtil.DEFAULT_ENCODING);			
		} catch (Throwable e) {
			throw new RuntimeException("Cannot overwrite brand file: " + BRANDS_TXT_FILE, e);
		}		
	}
	
	public void updateBrandKB(String content)
	{
		updateBrandsFile(content);
		initialize(true);
	}
	
	private HashMap<String, String> constructBrandHashMap() {

		HashMap<String, String> result = new HashMap<String, String>();

		for (BrandHolder brandHolder : brandHolderList) {
			
			result.put(StringUtil.removePunctiations(normalize(brandHolder.getActualBrand())), brandHolder.getActualBrand());
			
			if (brandHolder.getListOfAlternateBrands() != null) {
				for (String alternateBrand : brandHolder
						.getListOfAlternateBrands()) {
					result.put(StringUtil.removePunctiations(normalize(alternateBrand)), brandHolder.getActualBrand());
				}
			}
		}

		return result;
	}
	
	public String get(String str)
	{
		return brandHashMap.get(normalize(str));
	}

	public static String normalize(String aStr)
	{
		return StringUtil.convertTurkishCharacter(aStr).toLowerCase(Locale.ENGLISH);
	}
	
	public void organizeBrands(List<String> listOfBrands) {

		boolean brandMatches = false;

		for (String brand : listOfBrands) {
			brandMatches = false;

			for (BrandHolder brandHolder : brandHolderList) {
				// brands are same, ex: Nokia-nokia
				// so skip this brand, later we will answer correctly when
				// it's asked
				// "what is the actual brand name of nokia?" -- it is
				// Nokia...
				if (brandComparator.areBrandsCaseInsensitiveSame(brand,
						brandHolder.getActualBrand())) {
					brandMatches = true;
					break;
				}

				// brands are alike, so update your structure
				if (brandComparator.areBrandsALike(brand, brandHolder
						.getActualBrand())) {
					brandHolder.getListOfAlternateBrands().add(brand);
					updateBrandHashMap(brand, brandHolder.getActualBrand());
					brandMatches = true;
					break;
				}

				for (String alternateBrand : brandHolder
						.getListOfAlternateBrands()) {

					// Brand is case insensitive same with an alternate brand
					// there is no need to add this brand to alternate list.
					if (brandComparator.areBrandsCaseInsensitiveSame(brand,
							alternateBrand)) {
						brandMatches = true;
						break;
					}

					// Brand is alike with an alternate brand
					// Add the brand to hasmap and alternate list
					if (brandComparator.areBrandsALike(brand, alternateBrand)) {
						brandHolder.getListOfAlternateBrands().add(brand);
						updateBrandHashMap(brand, brandHolder.getActualBrand());
						brandMatches = true;
						break;
					}
				}

			}

			if (!brandMatches) {
				BrandHolder tmpBrandHolder = new BrandHolder();
				tmpBrandHolder.setActualBrand(brand);
				brandHolderList.add(tmpBrandHolder);
				// update the hashmap
				updateBrandHashMap(brand, tmpBrandHolder.getActualBrand());
			}
		}
	}

	private void updateBrandHashMap(String brand, String actualBrand) {
		synchronized (brandHashMap) {
			brandHashMap.put(normalize(brand), actualBrand);
		}
	}

	public void exportTo(File file) throws Exception
	{
		fileService.exportBrandHolders(brandHolderList, file);
	}

	public void importFrom(InputStream is) throws Exception 
	{
		brandHolderList = fileService.importBrandHolders(is);
	}
}
