package com.karniyarik.categorizer;


public class CategoryResult implements Comparable<CategoryResult>
{
	private String id = null;
	private double score = 0;
	
	public CategoryResult()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void setScore(double score)
	{
		this.score = score;
	}
	
	public double getScore()
	{
		return score;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	@Override
	public int compareTo(CategoryResult arg0)
	{
		return Double.valueOf(arg0.getScore()).compareTo(getScore());
	}
	
}
