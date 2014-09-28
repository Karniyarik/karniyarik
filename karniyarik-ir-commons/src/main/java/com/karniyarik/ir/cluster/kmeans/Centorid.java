package com.karniyarik.ir.cluster.kmeans;

class Centroid
{
	private double	mCx;
	private Cluster	mCluster;

	public Centroid(double cx)
	{
		this.mCx = cx;
	}

	public void calcCentroid()
	{ // only called by CAInstance
		int numDP = mCluster.getNumDataPoints();
		double tempX = 0;
		int i;
		// caluclating the new Centroid
		for (i = 0; i < numDP; i++)
		{
			tempX = tempX + mCluster.getDataPoint(i).getX();
			// total for x
		}
		this.mCx = tempX / numDP;
		// calculating the new Euclidean Distance for each Data Point
		tempX = 0;
		for (i = 0; i < numDP; i++)
		{
			mCluster.getDataPoint(i).calcDistance();
		}
		// calculate the new Sum of Squares for the Cluster
		mCluster.calcSumOfSquares();
	}

	public void setCluster(Cluster c)
	{
		this.mCluster = c;
	}

	public double getCx()
	{
		return mCx;
	}

	public Cluster getCluster()
	{
		return mCluster;
	}

}
