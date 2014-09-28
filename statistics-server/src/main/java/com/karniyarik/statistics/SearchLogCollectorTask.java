package com.karniyarik.statistics;

import java.io.FileWriter;

import org.apache.log4j.Logger;

import com.karniyarik.common.statistics.vo.SearchLog;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.statistics.cache.DailyCache;

public class SearchLogCollectorTask implements Runnable
{

	private final SearchLog		searchLog;
	private final FileWriter	logWriter;

	public SearchLogCollectorTask(SearchLog searchLog, FileWriter logWriter)
	{
		this.searchLog = searchLog;
		this.logWriter = logWriter;
	}

	@Override
	public void run()
	{
		DailyCache.getInstance().productViewed(searchLog.getProducts());
		
		try
		{
			logWriter.write(JSONUtil.getJSON(searchLog) + "\n");
			logWriter.flush();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not write search log", e);
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
