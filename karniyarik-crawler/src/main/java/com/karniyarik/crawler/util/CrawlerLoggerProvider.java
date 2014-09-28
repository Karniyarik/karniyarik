package com.karniyarik.crawler.util;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import com.karniyarik.common.log.KarniyarikLogger;

public class CrawlerLoggerProvider
{
	private String	mLoggerName	= null;

	public CrawlerLoggerProvider(String aSiteName)
	{
		mLoggerName = "crawler." + aSiteName;

		Logger aLogger = Logger.getLogger(mLoggerName);

		addFileAppender(aLogger.getParent().getAppender(
				KarniyarikLogger.INFO_FILE_APPENDER_NAME), aLogger, aSiteName);
		
//		addFileAppender(aLogger.getParent().getAppender(
//				KarniyarikLogger.ERROR_FILE_APPENDER_NAME), aLogger, aSiteName);
	}

	private void addFileAppender(Appender aRootAppender, Logger aLogger,
			String aSiteName)
	{
		if (aRootAppender instanceof RollingFileAppender)
		{
			RollingFileAppender anAppender = new RollingFileAppender();

			StringBuffer aFile = new StringBuffer(((RollingFileAppender) aRootAppender).getFile());
			if(aFile.lastIndexOf(".") != -1)
			{
				aFile.insert(aFile.lastIndexOf("."), "_" + aSiteName);				
			}			
			else
			{
				aFile.append("_" + aSiteName);	
			}
			
			anAppender.setFile(aFile.toString());
			anAppender.setMaxBackupIndex(((RollingFileAppender) aRootAppender)
					.getMaxBackupIndex());
			anAppender.setMaximumFileSize(((RollingFileAppender) aRootAppender)
					.getMaximumFileSize());
			anAppender.setLayout(((RollingFileAppender) aRootAppender)
					.getLayout());
			anAppender.setThreshold(((RollingFileAppender) aRootAppender)
					.getThreshold());
			anAppender.setName(((RollingFileAppender) aRootAppender).getName());
			anAppender.activateOptions();

			aLogger.addAppender(anAppender);
			// in order to get rid of root logger
			aLogger.setAdditivity(false);
		}
	}

	public Logger getLogger()
	{
		return Logger.getLogger(mLoggerName);
	}
}
