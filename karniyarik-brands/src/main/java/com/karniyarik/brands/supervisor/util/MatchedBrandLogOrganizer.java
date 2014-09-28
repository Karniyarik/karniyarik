package com.karniyarik.brands.supervisor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.karniyarik.brands.BrandHolder;
import com.karniyarik.brands.file.BrandsTxtFileService;
import com.karniyarik.brands.supervisor.BrandsSortComparator;
import com.karniyarik.common.util.StreamUtil;

@SuppressWarnings("unchecked")
public class MatchedBrandLogOrganizer
{
	private Map<String, BrandHolder> mMatchedBrands = null;
	
	public MatchedBrandLogOrganizer() throws Exception
	{
		mMatchedBrands = new HashMap<String, BrandHolder>();
	}

	public void organizeMatched(String aFileName) throws Exception
	{
		readFile(aFileName);
		writeFile(aFileName);	
	}
	
	public void sortResolved(String aFileName) throws Exception
	{
		File aFile = StreamUtil.getFile(aFileName);
		List<String> aLines = FileUtils.readLines(aFile);
		Collections.sort(aLines);
		FileUtils.writeLines(aFile, aLines);
	}
	
	private void writeFile(String aFileName) throws Exception
	{
		File aFile = StreamUtil.getFile(aFileName);
				
		List<BrandHolder> aList = new ArrayList<BrandHolder>(mMatchedBrands.values());
		
		Collections.sort(aList, new BrandsSortComparator());
		
		BrandsTxtFileService mFileService = new BrandsTxtFileService();
		
		mFileService.exportBrandHolders(aList, aFile);
		
	}

	private void readFile(String aFileName) throws Exception
	{
		List<String> aLines = FileUtils.readLines(StreamUtil.getFile(aFileName));
		String[] aStrArr = null;
		BrandHolder aBrandHolder = null;
		for(String aLine: aLines)
		{
			aStrArr = aLine.split("->");
			if(mMatchedBrands.containsKey(aStrArr[1].trim()))
			{
				aBrandHolder = mMatchedBrands.get(aStrArr[1].trim());
				aBrandHolder.addAlternateBrand(aStrArr[0].trim());
			}
			else
			{
				aBrandHolder = new BrandHolder();
				aBrandHolder.setActualBrand(aStrArr[1].trim());
				mMatchedBrands.put(aBrandHolder.getActualBrand(), aBrandHolder);
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		String[] aStrArr = new String[] {
		"brands/deveyuku/karniyarik_brands_notresolved.log",
		"brands/deveyuku/karniyarik_brands_resolved.log",
		"brands/genpatech/karniyarik_brands_notresolved.log",
		"brands/genpatech/karniyarik_brands_resolved.log",
		"brands/hipernex/karniyarik_brands_notresolved.log",
		"brands/hipernex/karniyarik_brands_resolved.log",
		"brands/hitbox/karniyarik_brands_notresolved.log",
		"brands/hitbox/karniyarik_brands_resolved.log",
		"brands/nebbu/karniyarik_brands_notresolved.log",
		"brands/nebbu/karniyarik_brands_resolved.log",
		"brands/teknosa/karniyarik_brands_notresolved.log",
		"brands/teknosa/karniyarik_brands_resolved.log"};
		
		for(String aStr: aStrArr)
		{
			new MatchedBrandLogOrganizer().sortResolved(aStr);	
		}
		
	}
}
