package com.karniyarik.jobscheduler.util;

import java.util.Comparator;

public class JobHealthComparator implements Comparator<JobStatisticsSummary>
{
	@Override
	public int compare(JobStatisticsSummary o1, JobStatisticsSummary o2)
	{
		if(o1.getOverallHealth() == 0 && o2.getOverallHealth()==0)
		{
			return 0;
		}
		if(o1.getOverallHealth() == 0 && o2.getOverallHealth()!=0)
		{
			return 1;
		}
		if(o1.getOverallHealth() != 0 && o2.getOverallHealth()==0)
		{
			return -1;
		}

		return Double.valueOf(o1.getOverallHealth()).compareTo(o2.getOverallHealth()); 
	}
}
