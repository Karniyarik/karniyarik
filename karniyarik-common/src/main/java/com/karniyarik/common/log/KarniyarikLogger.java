package com.karniyarik.common.log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.karniyarik.common.util.ConfigurationURLUtil;

public class KarniyarikLogger
{
	public static final String INFO_FILE_APPENDER_NAME = "InfoAppender";
	//public static final String ERROR_FILE_APPENDER_NAME = "ErrorAppender";
	
	public synchronized void initLogger()
	{
		synchronized (this)
		{
			try
			{
				InputStream aConfigStream = ConfigurationURLUtil.getLogConf().openStream();				
				Properties aProperties = new Properties();
				aProperties.load(aConfigStream);
				PropertyConfigurator.configure(aProperties);
				aConfigStream.close();
//				LogManager.getLogManager().readConfiguration(
//						ConfigurationLoader.getLogConf().openStream());
			}
			catch (Exception e)
			{
				throw new RuntimeException(
						"Cannot read loggin configuration.", e);
			}
		}
	}

	public synchronized String getLog(String aFileName)
	{
		String aResult = "";

		try
		{
			aResult = FileUtils.readFileToString(new File(aFileName));
		}
		catch (IOException e)
		{
			throw new RuntimeException("Cannot read log", e);
		}

		return aResult;
	}

	public static void logException(String aMsg, Throwable e, Logger aLogger)
	{
		StringBuffer aMessage = new StringBuffer();
		aMessage.append(aMsg);
		aMessage.append("\n");
		aMessage.append(ExceptionUtils.getStackTrace(e));
		aLogger.error(aMessage.toString());
	}
	
	public static void logExceptionSummary(String aMsg, Throwable e, Logger aLogger)
	{
		StringBuffer aMessage = new StringBuffer();
		aMessage.append(aMsg);
		aMessage.append(": ");
		aMessage.append(e.getMessage());
		aMessage.append(" - ");
		aMessage.append(ExceptionUtils.getRootCauseMessage(e));
		aLogger.error(aMessage.toString());
	}

}
