package com.karniyarik.recognizer.model;

public class ModelToken
{
	private String term;
	private int start;
	private int end;
	private boolean cleaned;
	
	public ModelToken(String term)
	{
		this.term = term;
	}

	public String getTerm()
	{
		return term;
	}

	public void setTerm(String term)
	{
		this.term = term;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public boolean isCleaned()
	{
		return cleaned;
	}

	public void setCleaned(boolean cleaned)
	{
		this.cleaned = cleaned;
	}
}
