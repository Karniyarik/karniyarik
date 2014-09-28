package com.karniyarik.statistics;

import java.io.FileWriter;

import org.apache.log4j.Logger;

import com.karniyarik.common.statistics.vo.ProductClickLog;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.statistics.cache.DailyCache;
import com.karniyarik.statistics.fraud.FraudDictionary;

public class ClickLogCollectorTask implements Runnable
{

	private final FraudDictionary	fraudDictionary;
	private final ProductClickLog	clickLog;
	private final FileWriter		logWriter;
	private final boolean			performFraudCheck;

	public ClickLogCollectorTask(FraudDictionary fraudDictionary, boolean performFraudCheck, ProductClickLog clickLog, FileWriter logWriter)
	{
		this.fraudDictionary = fraudDictionary;
		this.performFraudCheck = performFraudCheck;
		this.clickLog = clickLog;
		this.logWriter = logWriter;
	}

	@Override
	public void run()
	{

		if (performFraudCheck)
		{
			clickLog.setFraud(fraudDictionary.isFraudClick(clickLog));
		}

		DailyCache.getInstance().productClicked(clickLog);
		try
		{
			logWriter.write(JSONUtil.getJSON(clickLog) + "\n");
			logWriter.flush();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not write click log", e);
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
