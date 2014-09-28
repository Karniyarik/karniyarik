package com.karniyarik.ir.cluster;

import com.karniyarik.ir.cluster.distance.EucledianDistanceCalculator;
import com.karniyarik.ir.cluster.distance.IDistanceCalculator;
import com.karniyarik.ir.cluster.kmeans.KMeansClusteringAlgorithm;
import com.karniyarik.ir.cluster.linear.EqualResultCountClusteringAlgorithm;

public class ClusteringAlgorithmFactory
{
	public static IClusteringAlgorithm createAlgorithm(String algorithmName)
	{
		IClusteringAlgorithm  anAlgorithm = null;
		
		if(algorithmName.equalsIgnoreCase("count"))
		{
			anAlgorithm = new EqualResultCountClusteringAlgorithm();
		}
		else if(algorithmName.equalsIgnoreCase("kmeans"))
		{
			anAlgorithm = new KMeansClusteringAlgorithm();
		}
		
		
		anAlgorithm.setDistanceCalculator(createDistanceCalculator());
		
		return anAlgorithm;
	}
	
	public static IDistanceCalculator createDistanceCalculator()
	{
		return new EucledianDistanceCalculator();
	}
}
