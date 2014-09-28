package com.karniyarik.web.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.notifier.MailNotifier;

public class WebLoggerProvider
{
	public synchronized static void logException(String aMessage, Throwable e)
	{
		KarniyarikLogger.logException(aMessage, e, getLogger());
		ExceptionNotifier.sendException("web-exception", "Web Exception Occured", aMessage, e);
		new MailNotifier().sendTextMail("Web exception occured", aMessage + "\n" + ExceptionUtils.getStackTrace(e));
	}

	public synchronized static Logger getLogger()
	{
		return Logger.getLogger(WebLoggerProvider.class);
	}
}
