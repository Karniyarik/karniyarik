package com.karniyarik.categorizer.prep;

public class TermFreq implements Comparable<TermFreq>
{
	private String name;
	private int count;
	
	public TermFreq(String name)
	{
		this.name = name;
	}

	public TermFreq(String name, int count)
	{
		this.name = name;
		this.count = count;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
	
	public void increaseCountByOne()
	{
		setCount(getCount()+1);
	}
	
	@Override
	public int compareTo(TermFreq o)
	{
		return Integer.valueOf(getCount()).compareTo(Integer.valueOf(o.getCount()));
	}	
}
