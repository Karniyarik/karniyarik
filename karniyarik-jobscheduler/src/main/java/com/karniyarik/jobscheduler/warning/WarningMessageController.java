package com.karniyarik.jobscheduler.warning;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;

import com.karniyarik.common.exception.ExceptionVO;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.util.JSONUtil;

import edu.emory.mathcs.backport.java.util.Collections;

public class WarningMessageController
{
	private static Logger log = Logger.getLogger(WarningMessageController.class);
	
	public WarningMessageController()
	{
	}
	
	public void warningOccured(ExceptionVO vo)
	{
		String json = JSONUtil.getJSON(vo);
		log.info(json);
	}
	
	public List<String> getWarningWeeks()
	{
		List<String> result = new LinkedList<String>();
		
		DailyRollingFileAppender appender = (DailyRollingFileAppender) log.getParent().getAppender("SchedulerWarningAppender");
		
		File file = new File(appender.getFile());
		File directory = file.getParentFile();
		
		for(String filename: directory.list())
		{
			String newFileName = filename.replace(file.getName(), "");
			if(StringUtils.isBlank(newFileName))
			{
				newFileName = "current";
			}
			else
			{
				if(newFileName.startsWith("."))
				{
					newFileName = newFileName.substring(1);
				}
			}
			
			result.add(newFileName);
		}
		
		return result;
	}
	
	public WeeklyWarnings getCurrentWarnings()
	{
		return getWarnings("");
	}
	
	public WeeklyWarnings getWarnings(String week)
	{
		WeeklyWarnings result = new WeeklyWarnings();
		
		Map<String, DailyWarnings> dailyWarningsMap = new HashMap<String, DailyWarnings>();
		
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		try
		{
			DailyRollingFileAppender appender = (DailyRollingFileAppender) log.getParent().getAppender("SchedulerWarningAppender");
			
			String fileName = appender.getFile();
			if(!StringUtils.isBlank(week) && !week.equals("current"))
			{
				fileName = fileName + "." + week;
			}
			
			File file = new File(fileName);
			List<String> lines = FileUtils.readLines(file);
			for(String line: lines)
			{
				ExceptionVO vo = JSONUtil.parseJSON(line, ExceptionVO.class);
				
				String dateStr = format.format(vo.getDate());
				
				DailyWarnings dailyWarnings = dailyWarningsMap.get(dateStr);
				if(dailyWarnings == null)
				{
					dailyWarnings = new DailyWarnings();
					dailyWarnings.setDate(dateStr);
					dailyWarningsMap.put(dateStr, dailyWarnings);
				}
				
				List<ExceptionVO> list = dailyWarnings.getWarnings().get(vo.getIdentifier());
				
				if(list == null)
				{
					list = new LinkedList<ExceptionVO>();
					dailyWarnings.getWarnings().put(vo.getIdentifier(), list);
				}
				
				Integer typeCount = result.getWarningTypeCount().get(vo.getIdentifier());
				if(typeCount == null)
				{
					typeCount = 0;
				}
				typeCount++;
				result.getWarningTypeCount().put(vo.getIdentifier(), typeCount);
				
				list.add(vo);
			}
			
			Collection<DailyWarnings> values = dailyWarningsMap.values();
			result.getWarnings().addAll(values);
			Collections.sort(result.getWarnings());
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		new KarniyarikLogger().initLogger();
		//new WarningMessageController().warningOccured(new ExceptionVO());
		new WarningMessageController().getWarningWeeks();
	}
}
