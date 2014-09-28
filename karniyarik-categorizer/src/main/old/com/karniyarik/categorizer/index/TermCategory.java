package com.karniyarik.categorizer.index;

public class TermCategory
{
	private String	mCatID			= null;
	private float	mBoost			= 1;
	private int		mFreq			= 1;
	private int		mNegativeFreq	= 0;

	public TermCategory()
	{
	}

	public TermCategory(float aBoost, String aCatID)
	{
		super();
		mBoost = aBoost;
		mCatID = aCatID;
	}

	public String getCatID()
	{
		return mCatID;
	}

	public void setCatID(String aCatID)
	{
		mCatID = aCatID;
	}

	public float getBoost()
	{
		return mBoost;
	}

	public void setBoost(float aBoost)
	{
		mBoost = aBoost;
	}

	public int getFreq()
	{
		return mFreq;
	}

	public void setFreq(int aFreq)
	{
		mFreq = aFreq;
	}

	public void increaseFreq()
	{
		mFreq++;
	}

	public void increaseNegativeFreq()
	{
		mNegativeFreq++;
	}

	public int getNegativeFreq()
	{
		return mNegativeFreq;
	}

	public void setNegativeFreq(int aNegativeFreq)
	{
		mNegativeFreq = aNegativeFreq;
	}
}
