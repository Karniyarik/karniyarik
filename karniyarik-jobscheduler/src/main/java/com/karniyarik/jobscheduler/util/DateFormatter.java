package com.karniyarik.jobscheduler.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.Interval;

public class DateFormatter {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
	
	public static String format(long date)
	{
		if(date == 0)
		{
			return "";
		}
		
		return format(new Date(date));
	}
	
	public static String format(Date date)
	{
		if(date == null)
		{
			return "";
		}
		
		return dateFormat.format(date);
	}

	public static long getDuration(long startDate, long endDate)
	{
		if(startDate >0 && endDate > 0)
		{
			return getDuration(new Date(startDate), new Date(endDate));
		}
		
		return 0;
	}
	
	public static long getDuration(Date startDate, Date endDate)
	{
		if(startDate != null && endDate != null)
		{
			return endDate.getTime() - startDate.getTime();
		}
		
		return 0;
	}
	
	public static String getDurationStr(long startDate, long endDate)
	{
		if(startDate >0 && endDate > 0)
		{
			return getDurationStr(new Date(startDate), new Date(endDate));
		}
		
		return "";
	}
	
	public static String getDurationStr(Date startDate, Date endDate)
	{
		StringBuffer buff = new StringBuffer();
		
		if(startDate != null && endDate != null)
		{
			if(startDate.getTime() > endDate.getTime())
			{
				Date tmp = startDate;
				startDate = endDate;
				endDate = tmp;
				buff.append("-");
			}
			Interval interval = new Interval(startDate.getTime(), endDate.getTime());
			
			int days  = interval.toPeriod().getDays();
			int hours = interval.toPeriod().getHours();
			int minutes = interval.toPeriod().getMinutes();
			int seconds = interval.toPeriod().getSeconds();
			
			
			if(days > 0)
			{
				buff.append(days);
				buff.append("D ");
			}
			
			if(hours > 0)
			{
				buff.append(hours);
				buff.append("H ");
			}

			if(minutes > 0)
			{
				buff.append(minutes);
				buff.append("M ");
			}
			
			if(seconds > 0)
			{
				buff.append(seconds);
				buff.append("S");
			}
		}


		return buff.toString();
	}
}
