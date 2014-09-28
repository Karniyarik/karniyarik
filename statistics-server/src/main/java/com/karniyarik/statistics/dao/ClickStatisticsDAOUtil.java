package com.karniyarik.statistics.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.karniyarik.common.statistics.vo.DailyStat;

public class ClickStatisticsDAOUtil
{

	public static void fillEmptyDates(long startDate, long endDate, Map<Integer, DailyStat> statMap, List<DailyStat> dailyStatList)
	{
		Period p = new Period(startDate, endDate, PeriodType.days());
		int dayCount = p.getDays();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startDate);

		for (int index = 0; index <= dayCount; index++)
		{
			int curDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
			DailyStat stat = statMap.get(curDayOfYear);

			if (stat == null)
			{
				stat = new DailyStat();
				stat.setDate(cal.getTimeInMillis());
				stat.setListingViews(0);
				stat.setSponsorViews(0);
				stat.setListingClicks(0);
				stat.setSponsorClicks(0);
				stat.setTotalViews(0);
				stat.setTotalClicks(0);
			}

			dailyStatList.add(stat);
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

	public static long resetStartTime(long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		return cal.getTimeInMillis();
	}

	public static long resetEndTime(long time)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTimeInMillis();
	}

}
