package com.karniyarik.jobscheduler.util;

import java.util.Comparator;
import java.util.List;

import com.karniyarik.common.statistics.vo.JobExecutionStat;

public class JobExecutionStatComparator implements Comparator<JobExecutionStat>
{

	private final List<String>	waitingList;

	public JobExecutionStatComparator(List<String> waitingList)
	{
		this.waitingList = waitingList;
	}

	@Override
	public int compare(JobExecutionStat o1, JobExecutionStat o2)
	{
		int result = Boolean.valueOf(o1.getStatus().isRunning()).compareTo(Boolean.valueOf(o2.getStatus().isRunning()));

		if (result == 0)
		{
			result = Boolean.valueOf(o1.getStatus().hasEnded()).compareTo(Boolean.valueOf(o2.getStatus().hasEnded()));

			if (result == 0)
			{
				result = Boolean.valueOf(o1.getStatus().hasPaused()).compareTo(Boolean.valueOf(o2.getStatus().hasPaused()));

				if (result == 0)
				{
					result = o1.getStatus().compareTo(o2.getStatus());

					if (result == 0)
					{
						result = Boolean.valueOf(waitingList.contains(o1.getSiteName())).compareTo(Boolean.valueOf(waitingList.contains(o2.getSiteName())));
					}
				}
			}
		}

		return -result;
	}

}
