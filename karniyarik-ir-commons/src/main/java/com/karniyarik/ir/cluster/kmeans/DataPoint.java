package com.karniyarik.ir.cluster.kmeans;

import com.karniyarik.ir.cluster.distance.IDistanceCalculator;

public class DataPoint implements Comparable<DataPoint>
{
	private double	mX;
	private Cluster	mCluster;
	private double	mEuDt;

	private IDistanceCalculator mDistanceCalculator;
	
	public DataPoint(double x, IDistanceCalculator aDistanceCalculator)
	{
		this.mX = x;
		this.mCluster = null;
		this.mDistanceCalculator = aDistanceCalculator;
	}

	public void setCluster(Cluster cluster)
	{
		this.mCluster = cluster;
		calcDistance();
	}

	public void calcDistance()
	{
		// called when DP is added to a cluster or when a Centroid is
		// recalculated.
		mEuDt = mDistanceCalculator.calculateDistance(mX , mCluster.getCentroid().getCx());
	}

	public double testDistance(Centroid c)
	{
		return Math.abs(mX - c.getCx());
	}

	public double getX()
	{
		return mX;
	}
	
	public Cluster getCluster()
	{
		return mCluster;
	}

	public double getCurrentEuDt()
	{
		return mEuDt;
	}

	@Override
	public int compareTo(DataPoint o)
	{
		return Double.valueOf(mX).compareTo(o.getX());
	}

	@Override
	public String toString()
	{
		return Double.toString(getX());
	}
}
