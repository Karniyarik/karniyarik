package com.karniyarik.ir.cluster;

import java.util.List;

import com.karniyarik.ir.cluster.distance.IDistanceCalculator;

public abstract class IClusteringAlgorithm
{
	private IDistanceCalculator mDistanceCalculator = null;
	
	public abstract List<ClusterInfo> findClusters(List<Double> mNumberList);
	
	protected void setDistanceCalculator(IDistanceCalculator distanceCalculator)
	{
		mDistanceCalculator = distanceCalculator;
	}
	
	public IDistanceCalculator getDistanceCalculator()
	{
		return mDistanceCalculator;
	}
	
	protected int convertClusterPointToInt(double clusterPoint)
	{
		clusterPoint = Math.floor(clusterPoint);
		int clusterIntPoint = new Double(clusterPoint).intValue();
		int mod = 0;
		
		if(clusterIntPoint > 10)
		{
			mod = clusterIntPoint % 10;
			
			if(mod > 2)
			{
				clusterIntPoint = clusterIntPoint + (5-mod);
			}
			else
			{
				clusterIntPoint = clusterIntPoint -mod;
			}
		}

		return clusterIntPoint;
	}
}
