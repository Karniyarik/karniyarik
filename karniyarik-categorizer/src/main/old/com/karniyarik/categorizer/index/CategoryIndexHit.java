package com.karniyarik.categorizer.index;

public class CategoryIndexHit
{
	private String	mID			= null;
	private float	mScore		= 0;
	private float	mPercentage	= 0;
	private int		mHitCount	= 1;

	public CategoryIndexHit()
	{

	}

	public String getID()
	{
		return mID;
	}

	public void setID(String aId)
	{
		mID = aId;
	}

	public float getScore()
	{
		return mScore;
	}

	public void setScore(float aScore)
	{
		mScore = aScore;
	}

	public float getPercentage()
	{
		return mPercentage;
	}

	public void setPercentage(float aPercentage)
	{
		mPercentage = aPercentage;
	}

	public int getHitCount()
	{
		return mHitCount;
	}

	public void setHitCount(int aHitCount)
	{
		mHitCount = aHitCount;
	}

}
