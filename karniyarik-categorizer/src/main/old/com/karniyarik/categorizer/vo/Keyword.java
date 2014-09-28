package com.karniyarik.categorizer.vo;

public class Keyword
{
	private String	mValue			= null;
	private float	mBoost			= 0;
	private int		mFreq			= 0;
	private int		mNegativeFreq	= 0;

	public Keyword()
	{
		// TODO Auto-generated constructor stub
	}

	public Keyword(float aBoost, String aValue)
	{
		super();
		mBoost = aBoost;
		mValue = aValue;
	}

	public String getValue()
	{
		return mValue;
	}

	public void setValue(String aValue)
	{
		mValue = aValue;
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

	public int getNegativeFreq()
	{
		return mNegativeFreq;
	}

	public void setNegativeFreq(int aNegativeFreq)
	{
		mNegativeFreq = aNegativeFreq;
	}
	
	public void increaseFreq()
	{
		mFreq ++;		
	}
	
	public void increaseNegativeFreq()
	{
		mNegativeFreq++;		
	}
}
