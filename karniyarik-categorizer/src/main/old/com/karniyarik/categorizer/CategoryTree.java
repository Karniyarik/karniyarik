package com.karniyarik.categorizer;

import java.util.HashMap;
import java.util.Map;

import com.karniyarik.categorizer.io.CategoryReader;
import com.karniyarik.categorizer.io.CategoryWriter;
import com.karniyarik.categorizer.vo.Category;
import com.karniyarik.common.config.system.CategorizerConfig;

public class CategoryTree
{
	private Category				mRoot	= null;
	private Map<String, Category>	mIDMap	= null;
	private CategorizerConfig		mConfig	= null;

	public CategoryTree(CategorizerConfig aConfig)
	{
		mIDMap = new HashMap<String, Category>();
		mConfig = aConfig;
	}

	public void read(String aFileName)
	{
		mRoot = new CategoryReader(mConfig).read(aFileName);
		constructIDMap(mRoot);
	}

	public void write(String aFileName)
	{
		new CategoryWriter().write(mRoot, aFileName);
	}

	public void refresh()
	{
		mIDMap.clear();
		constructIDMap(mRoot);
	}

	private void constructIDMap(Category aCategory)
	{
		mIDMap.put(aCategory.getID(), aCategory);

		for (Category aChild : aCategory.getChildren())
		{
			constructIDMap(aChild);
		}
	}

	public Category getRoot()
	{
		return mRoot;
	}

	public Category getCategoryByID(String anID)
	{
		return mIDMap.get(anID);
	}
}
