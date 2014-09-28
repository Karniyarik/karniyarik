package com.karniyarik.ir.cluster;

import java.util.Comparator;

public class ClusterinfoComparator implements Comparator<ClusterInfo>
{
	@Override
	public int compare(ClusterInfo aO1, ClusterInfo aO2)
	{
		return Integer.valueOf(aO1.getStartPoint()).compareTo(aO2.getStartPoint());
	}
}
