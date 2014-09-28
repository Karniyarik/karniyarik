package com.karniyarik.brands.supervisor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringEscapeUtils;

import com.karniyarik.brands.BrandHolder;
import com.karniyarik.common.util.StreamUtil;

public class BrandsReader
{
	private List<BrandHolder>	mBrands	= null;

	public BrandsReader()
	{
		mBrands = new ArrayList<BrandHolder>();
	}

	public List<BrandHolder> readBrands(File aFile)
	{
		try
		{
			mBrands.clear();

			LineIterator anIterator = FileUtils.lineIterator(aFile);

			List<String> aTmpList = new ArrayList<String>();
			String aBrand = null;
			BrandHolder aHolder = null;

			while (anIterator.hasNext())
			{
				aBrand = anIterator.nextLine();
				aBrand = StringEscapeUtils.unescapeHtml(aBrand);
				// aBrand = StringUtil.removePunctiations(aBrand.toLowerCase());

				if (!aTmpList.contains(aBrand))
				{
					aTmpList.add(aBrand);
				}
			}

			for (String aString : aTmpList)
			{
				aHolder = new BrandHolder();
				aHolder.setActualBrand(aString);
				mBrands.add(aHolder);
			}

			aTmpList.clear();

			Collections.sort(mBrands, new BrandsSortComparator());

			return mBrands;
		}
		catch (IOException e)
		{
			throw new RuntimeException("Cannot read brands file", e);
		}
	}

	public List<BrandHolder> getBrands()
	{
		return mBrands;
	}

	public void writeBrands(List<BrandHolder> aBrands, String aString)
	{
		File aFile = StreamUtil.getFile(aString);

		try
		{
			FileWriter aWriter = new FileWriter(aFile);

			for (BrandHolder aHolder : aBrands)
			{
				aWriter.write(aHolder.getActualBrand() + "\n");
				for (String aStr : aHolder.getListOfAlternateBrands())
				{
					aWriter.write(aStr + "\n");
				}
			}

			aWriter.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException("Cannot write brands file", e);
		}
	}
}
