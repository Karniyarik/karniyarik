package com.karniyarik.ir.cluster.kmeans;

import java.util.ArrayList;
import java.util.List;

class Cluster
{
	private String			mName;
	private Centroid		mCentroid;
	private double			mSumSqr;
	private List<DataPoint>	mDataPoints;

	public Cluster(String name)
	{
		this.mName = name;
		this.mCentroid = null; // will be set by calling setCentroid()
		mDataPoints = new ArrayList<DataPoint>();
	}

	public void setCentroid(Centroid c)
	{
		mCentroid = c;
	}

	public Centroid getCentroid()
	{
		return mCentroid;
	}

	public void addDataPoint(DataPoint dp)
	{ // called from CAInstance
		dp.setCluster(this); // initiates a inner call to
		// calcEuclideanDistance() in DP.
		this.mDataPoints.add(dp);
		calcSumOfSquares();
	}

	public void removeDataPoint(DataPoint dp)
	{
		this.mDataPoints.remove(dp);
		calcSumOfSquares();
	}

	public int getNumDataPoints()
	{
		return this.mDataPoints.size();
	}

	public DataPoint getDataPoint(int pos)
	{
		return (DataPoint) this.mDataPoints.get(pos);
	}

	public void calcSumOfSquares()
	{ // called from Centroid
		int size = this.mDataPoints.size();
		double temp = 0;
		for (int i = 0; i < size; i++)
		{
			temp = temp
					+ ((DataPoint) this.mDataPoints.get(i))
							.getCurrentEuDt();
		}
		this.mSumSqr = temp;
	}

	public double getSumSqr()
	{
		return this.mSumSqr;
	}

	public String getName()
	{
		return this.mName;
	}

	public List<DataPoint> getDataPoints()
	{
		return this.mDataPoints;
	}

}