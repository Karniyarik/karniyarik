package com.karniyarik.ir.cluster.kmeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.karniyarik.ir.cluster.ClusterInfo;
import com.karniyarik.ir.cluster.ClusterinfoComparator;
import com.karniyarik.ir.cluster.IClusteringAlgorithm;

public class KMeansClusteringAlgorithm extends IClusteringAlgorithm
{	
	@Override
	public List<ClusterInfo> findClusters(List<Double> numberList)
	{
		List<ClusterInfo> aResult = new ArrayList<ClusterInfo>();
		
		if(numberList != null && numberList.size() > 1)
		{
			List<DataPoint> aList = new ArrayList<DataPoint>(numberList.size());
			
			for(Double aNumber: numberList)
			{
				aList.add(new DataPoint(aNumber, getDistanceCalculator()));
			}
			
			int aClusterSize = new Double(Math.log(numberList.size())).intValue();
			
			if(aClusterSize > 1)
			{
				JCA jca = new JCA(aClusterSize,100,aList);
				
				jca.startAnalysis();
				
				Cluster[] aClusterOutputArray= jca.getClusterOutput();
				
				ClusterInfo aClusterInfo = null;
				
				for(Cluster aCluster: aClusterOutputArray)
				{
					if(aCluster.getDataPoints() != null && aCluster.getDataPoints().size()>0)
					{
						Collections.sort(aCluster.getDataPoints());
						
						aClusterInfo = new ClusterInfo();
						
						aClusterInfo.setStartPoint(convertClusterPointToInt(aCluster.getDataPoints().get(0).getX()));
						aClusterInfo.setEndPoint(convertClusterPointToInt(aCluster.getDataPoints().get(aCluster.getDataPoints().size()-1).getX()));
						aClusterInfo.setElementCount(aCluster.getDataPoints().size());
						
						if(!aResult.contains(aClusterInfo))
						{
							aResult.add(aClusterInfo);
						}					
					}
				}
				
				Collections.sort(aResult, new ClusterinfoComparator());
				
				aResult.get(0).setStartPoint(0);
				//aResult.remove(0);
				//aResult.addFirst(0);
			}
			
		}
		
		return aResult;
	}
	
}