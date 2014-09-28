package com.karniyarik.categorizer.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Category
{
	private String					mName			= null;
	private String					mID				= null;
	private List<Category>			mChildren		= null;
	private Map<String, Keyword>	mKeywords		= null;
	private List<String>			mBrands			= null;
	private List<Category>			mParents		= null;
	private int						mInstanceCount	= 1;
	private boolean					mIsRoot			= false;

	public Category()
	{
		mChildren = new ArrayList<Category>();
		mKeywords = new HashMap<String, Keyword>();
		mBrands = new ArrayList<String>();
		mParents = new ArrayList<Category>();
	}

	public Map<String, Keyword> getKeywords()
	{
		return mKeywords;
	}

	public void setKeywords(Map<String, Keyword> aKeywords)
	{
		mKeywords = aKeywords;
	}

	public void addKeyword(Keyword aKeyword)
	{
		if (StringUtils.isNotBlank(aKeyword.getValue()))
		{
			if (!getKeywords().containsKey(aKeyword.getValue()))
			{
				getKeywords().put(aKeyword.getValue(), aKeyword);
			}
			else
			{
				Keyword anExistingKeyword = getKeywords().get(aKeyword.getValue());
				//anExistingKeyword.setBoost(FormulaUtil.calculateExistingKeywordEffect(anExistingKeyword, aKeyword));
				anExistingKeyword.setFreq(anExistingKeyword.getFreq() + aKeyword.getFreq());
				anExistingKeyword.setNegativeFreq(anExistingKeyword.getNegativeFreq() + aKeyword.getNegativeFreq());
			}
		}
	}

	public void addKeywords(List<Keyword> aKeywordList)
	{
		if (aKeywordList != null)
		{
			for (Keyword aKeyword : aKeywordList)
			{
				addKeyword(aKeyword);
			}
		}
	}

	public void addBrands(List<String> aBrandList)
	{
		if (aBrandList != null)
		{
			for (String aStr : aBrandList)
			{
				addBrand(aStr);
			}
		}
	}

	public void addBrand(String aBrand)
	{
		if (StringUtils.isNotBlank(aBrand) && !getBrands().contains(aBrand))
		{
			getBrands().add(aBrand);
		}
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String aName)
	{
		mName = aName;
	}

	public List<Category> getChildren()
	{
		return mChildren;
	}

	public void addChild(Category aCategory)
	{
		if (!getChildren().contains(aCategory))
		{
			getChildren().add(aCategory);
		}

		aCategory.addParent(this);
	}

	public void setChildren(List<Category> aChildren)
	{
		mChildren = aChildren;
	}

	public String getID()
	{
		return mID;
	}

	public void setID(String aId)
	{
		mID = aId;
	}

	public List<Category> getParents()
	{
		return mParents;
	}

	public void setParents(List<Category> aParents)
	{
		mParents = aParents;
	}

	public void addParent(Category aCategory)
	{
		if (!getParents().contains(aCategory))
		{
			getParents().add(aCategory);
		}
	}

	public List<String> getBrands()
	{
		return mBrands;
	}

	public void setBrands(List<String> aBrands)
	{
		mBrands = aBrands;
	}

	@Override
	public String toString()
	{
		return getName() + " - " + getID();
	}

	public int getInstanceCount()
	{
		return mInstanceCount;
	}

	public void setInstanceCount(int aInstanceCount)
	{
		mInstanceCount = aInstanceCount;
	}
	
	public void increaseInstanceCount()
	{
		mInstanceCount++;
	}

	public boolean isRoot()
	{
		return mIsRoot;
	}

	public void setRoot(boolean aIsRoot)
	{
		mIsRoot = aIsRoot;
	}

}
