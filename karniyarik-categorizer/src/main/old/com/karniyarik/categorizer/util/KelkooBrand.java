package com.karniyarik.categorizer.util;

import java.util.ArrayList;
import java.util.List;

public class KelkooBrand
{
	private String			mName			= null;
	private int				mID				= 0;
	private List<String>	mCategoryList	= null;

	public KelkooBrand()
	{
		mCategoryList = new ArrayList<String>();
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String aName)
	{
		mName = aName;
	}

	public int getID()
	{
		return mID;
	}

	public void setID(int aId)
	{
		mID = aId;
	}

	public List<String> getCategoryList()
	{
		return mCategoryList;
	}

	public void setCategoryList(List<String> aCategoryList)
	{
		mCategoryList = aCategoryList;
	}

}
