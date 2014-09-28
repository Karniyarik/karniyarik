package com.karniyarik.ir.cluster.linear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.karniyarik.ir.cluster.ClusterInfo;
import com.karniyarik.ir.cluster.IClusteringAlgorithm;

public class EqualResultCountClusteringAlgorithm extends IClusteringAlgorithm
{	
	@Override
	public List<ClusterInfo> findClusters(List<Double> numberList)
	{
		List<ClusterInfo> aResult = new ArrayList<ClusterInfo>(0);
		
		if (numberList != null && numberList.size() > 1) {

			Collections.sort(numberList);

			int clusterSize = new Double(Math.log(numberList.size())).intValue();
			aResult = new ArrayList<ClusterInfo>(clusterSize);

			int clusterElementCount = 0;
			int clusterElementRemainder = -1;
			int clusterElementCountArr[] = new int[clusterSize];

			if(clusterSize != 0) {

				clusterElementCount = numberList.size() / clusterSize;
				clusterElementRemainder  = numberList.size() % clusterSize;
				ClusterInfo clusterInfo = null;

				int endPointIndex = 0;
				int startPointIndex = 0;
				int incrementValue = 0;
				int elementCount = 0;

				for(int i = 0; i < clusterSize; i++) {

					startPointIndex = 0;
					endPointIndex = 0;
					clusterInfo = new ClusterInfo();

					if (clusterElementRemainder > 0) {
						incrementValue = 1;
						clusterElementRemainder--;
					}
					else {
						incrementValue = 0;
					}

					for (int j = 0; j < i; j++) {
						startPointIndex += clusterElementCountArr[j];
					}

					endPointIndex = startPointIndex + (clusterElementCount  + incrementValue) - 1;
					clusterInfo.setStartPoint(numberList.get(startPointIndex).intValue());
					clusterInfo.setEndPoint(numberList.get(endPointIndex).intValue());
					elementCount = endPointIndex - startPointIndex + 1;
					//clusterInfo.setElementCount(elementCount);
					clusterElementCountArr[i] = elementCount;
					aResult.add(clusterInfo);
				}
			}
		}
		
		return aResult;
	}
}