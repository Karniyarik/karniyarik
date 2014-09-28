package com.karniyarik.ir.cluster;

import java.util.ArrayList;
import java.util.List;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.SearchConfig;

public class ClusterConstructor
{
	private String algorithmName;

	public ClusterConstructor()
	{
	}
	
	public ClusterConstructor(String algortihmName)
	{
		super();
		this.algorithmName = algortihmName;
	}
	
	public List<ClusterInfo> findNumberClusters(List<Double> aNumberList, int aClusterSize)
	{
		List<ClusterInfo> aClusterList = new ArrayList<ClusterInfo>();

		if (algorithmName == null) {
			SearchConfig config = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();
			algorithmName = config.getClusteringAlgorithm().trim();
		}
		
		IClusteringAlgorithm anAlgorithm = ClusteringAlgorithmFactory.createAlgorithm(algorithmName);
		
		aClusterList = anAlgorithm.findClusters(aNumberList);
		
		return aClusterList;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
}
