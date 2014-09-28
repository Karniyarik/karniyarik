package com.karniyarik.jobscheduler.warning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyWarnings
{
	private List<DailyWarnings> warnings = new ArrayList<DailyWarnings>();
	private Map<String, Integer> warningTypeCount = new HashMap<String, Integer>();
	
	public WeeklyWarnings()
	{
		// TODO Auto-generated constructor stub
	}
	
	public List<DailyWarnings> getWarnings()
	{
		return warnings;
	}
	
	public void setWarnings(List<DailyWarnings> warnings)
	{
		this.warnings = warnings;
	}
	
	public Map<String, Integer> getWarningTypeCount()
	{
		return warningTypeCount;
	}
	
	public void setWarningTypeCount(Map<String, Integer> warningTypeCount)
	{
		this.warningTypeCount = warningTypeCount;
	}
}
