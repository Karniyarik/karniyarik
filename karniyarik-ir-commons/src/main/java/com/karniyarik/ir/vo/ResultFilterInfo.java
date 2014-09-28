package com.karniyarik.ir.vo;

import com.karniyarik.ir.cluster.ClusterInfo;


public class ResultFilterInfo extends ClusterInfo implements Comparable<ResultFilterInfo>
{	
	private String	value			= null;
	private boolean isClustered = false;
	
	public ResultFilterInfo()
	{
		// TODO Auto-generated constructor stub
	}
	
	public ResultFilterInfo(ClusterInfo cluster)
	{
		this();
		setStartPoint(cluster.getStartPoint());
		setEndPoint(cluster.getEndPoint());
		setElementCount(cluster.getElementCount());
		isClustered = true;
	}
	
	
	public boolean isClustered()
	{
		return isClustered;
	}

	public void setClustered(boolean isClustered)
	{
		this.isClustered = isClustered;
	}

	public String getName()
	{
		return value;
	}

	public void setValue(String aName)
	{
		value = aName;
	}

	@Override
	public boolean equals(Object aObj)
	{
		if (aObj instanceof ResultFilterInfo)
		{
			return value.equals(((ResultFilterInfo) aObj).getName());
		}

		return super.equals(aObj);
	}
	
	@Override
	public int compareTo(ResultFilterInfo aO)
	{
		return getName().compareTo(aO.getName());
	}
	
	public void addOccurrence()
	{
		setElementCount(getElementCount()+1);
	}
}