package com.karniyarik.ir.cluster;

public class ClusterInfo
{
	private int mStartPoint = 0;
	private int mEndPoint = 0;
	private int mElementCount = 0;	
	
	public int getStartPoint()
	{
		return mStartPoint;
	}
	public void setStartPoint(int aStartPoint)
	{
		mStartPoint = aStartPoint;
	}
	public int getEndPoint()
	{
		return mEndPoint;
	}
	public void setEndPoint(int aEndPoint)
	{
		mEndPoint = aEndPoint;
	}
	public int getElementCount()
	{
		return mElementCount;
	}
	public void setElementCount(int aElementCount)
	{
		mElementCount = aElementCount;
	}
	
	@Override
	public boolean equals(Object aObj)
	{
		if(aObj instanceof ClusterInfo)
		{
			return mStartPoint == ((ClusterInfo)aObj).getStartPoint() && mEndPoint == ((ClusterInfo)aObj).getEndPoint(); 
		}
		
		return super.equals(aObj);
	}
	
}
