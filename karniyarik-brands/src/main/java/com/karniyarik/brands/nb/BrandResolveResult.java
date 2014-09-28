package com.karniyarik.brands.nb;

public class BrandResolveResult
{
	private String actualBrandName;
	private String foundBrandName;
	private int index;
	private Double score;
	
	public BrandResolveResult()
	{
		// TODO Auto-generated constructor stub
	}

	public String getActualBrandName()
	{
		return actualBrandName;
	}

	public void setActualBrandName(String actualBrandName)
	{
		this.actualBrandName = actualBrandName;
	}

	public String getFoundBrandName()
	{
		return foundBrandName;
	}

	public void setFoundBrandName(String foundBrandName)
	{
		this.foundBrandName = foundBrandName;
	}

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public Double getScore()
	{
		return score;
	}
	
	public void setScore(Double score)
	{
		this.score = score;
	}
}
