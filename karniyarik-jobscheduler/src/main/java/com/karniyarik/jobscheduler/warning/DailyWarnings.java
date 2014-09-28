package com.karniyarik.jobscheduler.warning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.common.exception.ExceptionVO;

public class DailyWarnings implements Comparable<DailyWarnings>
{
	private String date = "";
	private Map<String, List<ExceptionVO>> warnings = new HashMap<String, List<ExceptionVO>>();
	
	public DailyWarnings()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setWarnings(Map<String, List<ExceptionVO>> warnings)
	{
		this.warnings = warnings;
	}
	
	public Map<String, List<ExceptionVO>> getWarnings()
	{
		return warnings;
	}
	
	@Override
	public int compareTo(DailyWarnings o)
	{
		return getDate().compareTo(o.getDate());
	}
}
