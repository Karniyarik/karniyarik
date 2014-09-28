package com.karniyarik.brands.nb;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Token;

public class ResolveResult implements Comparable<ResolveResult>
{
	private String foundValue;
	private List<Token> token = new ArrayList<Token>();
	private Double score;
	private boolean isDefault = false; 
	
	public ResolveResult()
	{
		// TODO Auto-generated constructor stub
	}

	public String getFoundValue()
	{
		return foundValue;
	}

	public void setFoundValue(String foundValue)
	{
		this.foundValue = foundValue;
	}

	public Double getScore()
	{
		return score;
	}
	
	public void setScore(Double score)
	{
		this.score = score;
	}
	
	public List<Token> getToken()
	{
		return token;
	}
	
	public void setToken(List<Token> token)
	{
		this.token = token;
	}
	
	public boolean isDefault()
	{
		return isDefault;
	}
	
	public void setDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}
	
	@Override
	public int compareTo(ResolveResult o)
	{//reverse sort
		return o.getScore().compareTo(getScore());
	}
}
