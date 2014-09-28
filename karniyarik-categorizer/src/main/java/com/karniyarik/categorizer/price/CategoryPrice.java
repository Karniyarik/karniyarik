package com.karniyarik.categorizer.price;

import java.io.Serializable;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.simpleframework.xml.Transient;

public class CategoryPrice implements Serializable
{
	private String id;
	private double min = Integer.MAX_VALUE;
	private double max = 0;
	private double mean = 0;
	private double stddev = 0;
	
	private transient SummaryStatistics stat = null;
	
	public CategoryPrice()
	{
		
	}

	public void setStat(SummaryStatistics stat)
	{
		this.stat = stat;
	}
	
	public SummaryStatistics getStat()
	{
		return stat;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public double getMax()
	{
		return max;
	}
	
	public double getMin()
	{
		return min;
	}
	
	public void setMax(double max)
	{
		this.max = max;
	}
	
	public void setMin(double min)
	{
		this.min = min;
	}
	
	public double getMean()
	{
		return mean;
	}
	
	public double getStddev()
	{
		return stddev;
	}
	
	public void setMean(double mean)
	{
		this.mean = mean;
	}
	
	public void setStddev(double stddev)
	{
		this.stddev = stddev;
	}

	public void setValues()
	{
		setStddev(getStat().getStandardDeviation());
		setMax(getStat().getMax());
		setMean(getStat().getMean());
		setMin(getStat().getMin());
	}
}
