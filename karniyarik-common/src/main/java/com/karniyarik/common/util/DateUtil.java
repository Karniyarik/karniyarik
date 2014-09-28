package com.karniyarik.common.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil
{
	public static Date getCurrentLocalDate()
	{
		return Calendar.getInstance(TimeZone.getTimeZone("Turkey")).getTime();
	}
	
	public static Date getCurrentDate()
	{
		return Calendar.getInstance().getTime();
	}
	
	public static Timestamp getTimestamp(Date aDate)
	{
		return new Timestamp(aDate.getTime());
	}
	
	public static Date getDate(Timestamp aTimestamp)
	{
		return new Date(aTimestamp.getTime());
	}
}
