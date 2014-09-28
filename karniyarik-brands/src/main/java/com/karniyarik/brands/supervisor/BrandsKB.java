package com.karniyarik.brands.supervisor;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import com.karniyarik.brands.BrandHolder;
import com.karniyarik.brands.file.BrandsFileService;
import com.karniyarik.brands.file.BrandsTxtFileService;
import com.karniyarik.common.util.StreamUtil;

public class BrandsKB
{
	private List<BrandHolder> mBrands = null;
	
	private BrandsFileService mFileService = null;
	
	public BrandsKB()
	{
		mFileService = new BrandsTxtFileService();	
	}	
	
	public void load(String aFileName)
	{
		try
		{
			InputStream aStream = StreamUtil.getStream(aFileName);
			
			mBrands = mFileService.importBrandHolders(aStream);
						
			Collections.sort(mBrands, new BrandsSortComparator());
			
			aStream.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("cannot open brands knowledge base");
		}
	}

	public void writeDB(String aFileName)
	{
		try
		{
			File aFile = StreamUtil.getFile(aFileName);
			
			Collections.sort(mBrands, new BrandsSortComparator());
			
			mFileService.exportBrandHolders(getBrands(), aFile);
		}
		catch (Throwable e)
		{
			throw new RuntimeException("cannot write brands knowledge base");		
		}
	}

	public List<BrandHolder> getBrands()
	{
		return mBrands;
	}

	public void setBrands(List<BrandHolder> aBrands)
	{
		mBrands = aBrands;
	}
	
	public BrandHolder getBrand(String aActualBrand)
	{
		for(BrandHolder aHolder: getBrands())
		{
			if(aHolder.getActualBrand().equalsIgnoreCase(aActualBrand))
			{
				return aHolder;
			}
		}
		
		return null;
	}
}
