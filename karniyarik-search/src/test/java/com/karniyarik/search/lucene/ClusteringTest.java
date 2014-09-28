package com.karniyarik.search.lucene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.karniyarik.ir.cluster.ClusterConstructor;
import com.karniyarik.ir.cluster.ClusterInfo;

public class ClusteringTest
{
	private static ClusterConstructor clusterConstructor;

	@BeforeClass
	public static void prepareAlgorithm() {
		 clusterConstructor = new ClusterConstructor("count");
	}

	@Test
	public void testClusteringAlgorithm()
	{
		List<Double> aNumberList = new ArrayList<Double>();
		
		Random aRandom = new Random(Calendar.getInstance().getTimeInMillis());
		
		for(int anIndex = 0; anIndex < 50; anIndex++)
		{
			aNumberList.add(aRandom.nextInt(100) * 1.0);
		}
		
		List<ClusterInfo> resultCluster =
			clusterConstructor.findNumberClusters(aNumberList, -1);
		int clusterSize = new Double(Math.log(aNumberList.size())).intValue();

		assertNotNull(resultCluster);
		assertEquals(resultCluster.size(), clusterSize);

		for(int i = 0; i < clusterSize; i++) {

			int iTempElementCount = resultCluster.get(i).getElementCount();
			int iEndPoint = resultCluster.get(i).getEndPoint();

			assertTrue(
					(iTempElementCount == new Double(aNumberList.size() / clusterSize).intValue())
					|| (iTempElementCount == new Double(aNumberList.size() / clusterSize).intValue() + 1));

			for(int j = 0; j < clusterSize; j++) {

				int jTempElementCount =	resultCluster.get(j).getElementCount();
				int jStartPoint = resultCluster.get(j).getStartPoint();

				//consecutive elements count can not have size difference greater than 1
				assertTrue(
						(iTempElementCount == jTempElementCount
						|| ((iTempElementCount + 1) == jTempElementCount)
						|| (iTempElementCount == (jTempElementCount + 1))));


				if ((i + 1)  == j) {
					assertTrue(iEndPoint <= jStartPoint);
				}
			}
		}
	}

	@Test
	public void testEmtpyInputList() {

		List<Double> inputList = new ArrayList<Double>();
		List<ClusterInfo> resultCluster =
			clusterConstructor.findNumberClusters(inputList, -1);

		assertNotNull(resultCluster);
		assertEquals(resultCluster.size(), 0);
	}

	@Test
	public void testNullInputList() {

		List<Double> inputList = null;
		List<ClusterInfo> resultCluster =
			clusterConstructor.findNumberClusters(inputList, -1);

		assertNotNull(resultCluster);
		assertEquals(resultCluster.size(), 0);
	}

	@Test
	public void testSingleElementInputList() {

		List<Double> inputList = new ArrayList<Double>();
		inputList.add(Double.valueOf(2));
		List<ClusterInfo> resultCluster =
			clusterConstructor.findNumberClusters(inputList, -1);

		assertNotNull(resultCluster);
		assertEquals(resultCluster.size(), 0);
	}
	
}
