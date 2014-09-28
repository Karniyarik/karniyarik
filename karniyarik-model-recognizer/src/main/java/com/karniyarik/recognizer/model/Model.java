package com.karniyarik.recognizer.model;

public class Model implements Comparable<Model>
{
	private String name = null;
	private double score;
	private int start;
	private int end;
	
	public Model()
	{
		// TODO Auto-generated constructor stub
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}
	
	public int getEnd()
	{
		return end;
	}
	
	public int getStart()
	{
		return start;
	}
	
	public void setEnd(int end)
	{
		this.end = end;
	}
	
	public void setStart(int start)
	{
		this.start = start;
	}
	
	@Override
	public int compareTo(Model o)
	{
		return Double.valueOf(o.getScore()).compareTo(getScore());
	}
}
