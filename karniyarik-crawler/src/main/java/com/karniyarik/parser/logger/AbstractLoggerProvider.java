package com.karniyarik.parser.logger;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

public abstract class AbstractLoggerProvider
{
	private String mSiteName = null;
	private String packageName = null;
	
	public AbstractLoggerProvider(String siteName, String appenderPackage, String appenderName)
	{
		mSiteName = siteName;
		packageName = appenderPackage;
		
		for (String operationType : getOperationList())
		{
			addFileAppender(operationType, appenderName);
		}
		
	}
	
	protected abstract String[] getOperationList();
	
	private void addFileAppender(String operationType, String appenderName)
	{
		String aLoggerName = packageName + mSiteName + "." + operationType;

		Logger aLogger = Logger.getLogger(aLoggerName);
		
		if (aLogger.getAppender(appenderName) == null) {
			Appender aRootAppender = aLogger.getParent().getAppender(appenderName);
			
			if (aRootAppender instanceof RollingFileAppender)
			{
				RollingFileAppender anAppender = new RollingFileAppender();

				if (aLogger.getAppender(((RollingFileAppender) aRootAppender).getName()) == null) {
					StringBuffer aFile = new StringBuffer(((RollingFileAppender) aRootAppender).getFile());
					
					if(aFile.lastIndexOf(".") != -1)
					{
						aFile.insert(aFile.lastIndexOf("."), "_" + operationType);				
					}			
					else
					{
						aFile.append("_" + operationType);
					}
					
					if(aFile.lastIndexOf("/") != -1)
					{
						aFile.insert(aFile.lastIndexOf("/"), "/" + mSiteName + "/");				
					}			
					else
					{
						aFile.append("_" + mSiteName);
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
					anAppender.setName(appenderName);
					anAppender.activateOptions();
					
					aLogger.addAppender(anAppender);
					// in order to get rid of root logger
					aLogger.setAdditivity(false);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param operationType Check constants in this class.
	 * @return
	 */
	protected Logger getLogger(String operationType)
	{
		return Logger.getLogger(packageName + mSiteName + "." + operationType);
	}
	

}