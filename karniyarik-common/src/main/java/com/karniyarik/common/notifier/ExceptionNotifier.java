package com.karniyarik.common.notifier;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.exception.ExceptionVO;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.util.JerseyUtil;

public class ExceptionNotifier
{
	private static Logger log = Logger.getLogger(ExceptionNotifier.class);
	
	public static void sendException(ExceptionVO vo)
	{
		try
		{
			DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
			String endPoint = deploymentConfig.getJobSchedulerDeploymentURL() + "/rest/exception/add";
			JerseyUtil.sendJSONPut(endPoint, vo);
		}
		catch (Exception e)
		{
			KarniyarikLogger.logExceptionSummary("CAnnot send exception to scheduler", e, log);
		}	
	}
	
	public static void sendException(String id, String title, String message)
	{
		sendException(id, title, message, null);
	}
	
	public static void sendException(String id, String title, String message, Throwable exception)
	{
		ExceptionVO vo = new ExceptionVO();
		vo.setDate(new Date());
		vo.setIdentifier(id);
		vo.setTitle(title);
		vo.setMessage(message);
		if(exception != null)
		{
			vo.setStackTrace(ExceptionUtils.getFullStackTrace(exception));	
		}
		sendException(vo);
	}
}
