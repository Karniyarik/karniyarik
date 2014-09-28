package com.karniyarik.categorizer.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.categorizer.vo.Category;
import com.karniyarik.categorizer.vo.Keyword;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.vo.Product;

public class CategoryIndex
{
	private Map<String, IndexTerm>		mIndex					= null;
	private Map<String, List<String>>	mCategoryIdToTokenMap	= null;
	private CategorizerConfig			mConfig					= null;
	private Map<String, Category>		mCategoryMap			= null;

	public void deleteCategory(Category aCategory)
	{
		// mDocumentSize -= aCategory.getInstanceCount();

		List<String> aTokenList = mCategoryIdToTokenMap.get(aCategory.getID());

		if (aTokenList != null)
		{
			IndexTerm anIndexTerm = null;

			for (String aTokenString : aTokenList)
			{
				anIndexTerm = mIndex.get(aTokenString);
				anIndexTerm.remove(aCategory.getID());
			}
		}
	}

	public void printIndex()
	{
		List<IndexTerm> aResult = new ArrayList<IndexTerm>(mIndex.values());
		Collections.sort(aResult, new IndexTermComparator());
		
		File aFile = new File("xxx.txt");
		
		List<String> aLines  = new ArrayList<String>();
		for(IndexTerm aTerm: aResult)
		{
			if(aTerm.getTotalFreq() > 10)
				aLines.add(aTerm.getTerm() + " --> " + aTerm.getTotalFreq());
		}
		
		try
		{
			FileUtils.writeLines(aFile, aLines);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}