package com.karniyarik.jobscheduler.request;

import java.util.Comparator;

import com.karniyarik.jobscheduler.vo.ScheduleInformation;

public class ScheduleInfoTimeLeftComparator implements Comparator<ScheduleInformation>
{
	@Override
	public int compare(ScheduleInformation o1, ScheduleInformation o2)
	{
		return Long.valueOf(o1.getDuration()).compareTo(o2.getDuration());
	}
}
