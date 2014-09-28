package com.karniyarik.jobscheduler.request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.group.GroupMember;
import com.karniyarik.jobscheduler.JobSchedulerAdmin;

public class JobRequestHandler
{
	public static final String	OPERATION_PARAMETER		= "operation";
	public static final String	SITE_NAME_PARAMETER		= "site";

	public static final String	SCHEDULE_ALL_OPERATION	= "schedulaAll";
	public static final String	PAUSE_ALL_OPERATION		= "pauseAll";
	public static final String	SCHEDULE_SITE_OPERATION	= "scheduleSite";
	public static final String	PAUSE_SITE_OPERATION	= "pauseSite";
	public static final String	END_SITE_OPERATION		= "endSite";
	public static final String	DELETE_SITE_OPERATION	= "rm";

	public static final String	SELENIUM_PARAM			= "selenium";
	public static final String	JOBCLASS_PARAM			= "jobclass";
	public static final String	SERVER_CHANGE_PARAM		= "change";
	
	public static void handleCapabilityRequest(HttpServletRequest request, HttpServletResponse response, GroupMember member)
	{
		String serverChangeStr = request.getParameter(SERVER_CHANGE_PARAM);
		
		if(StringUtils.isNotBlank(serverChangeStr))
		{
			String seleniumStr = request.getParameter(SELENIUM_PARAM);
			String jobclassStr =request.getParameter(JOBCLASS_PARAM);
			
			boolean selenium = false;
			boolean jobclass = false;
			if(StringUtils.isNotBlank(seleniumStr))
			{
				 selenium = seleniumStr.trim().equals("on");
			}			
			if(StringUtils.isNotBlank(jobclassStr))
			{
				 jobclass = jobclassStr.trim().equals("on");
			}
			
			JobSchedulerAdmin.getInstance().setCapability(member, selenium, jobclass);
		}
	}
	
	public static void handleCrawlerRequest(HttpServletRequest request, HttpServletResponse response)
	{
		String operation = request.getParameter(OPERATION_PARAMETER);

		if (StringUtils.isNotBlank(operation))
		{
			if (operation.equals(DELETE_SITE_OPERATION))
			{
				String siteName = request.getParameter(SITE_NAME_PARAMETER);
				if (StringUtils.isNotBlank(siteName))
				{
					JobSchedulerAdmin.getInstance().deleteSite(siteName);
				}
			}
			else if (operation.equals(SCHEDULE_ALL_OPERATION))
			{
				JobSchedulerAdmin.getInstance().scheduleAll();
			}
			else if (operation.equals(PAUSE_ALL_OPERATION))
			{
				JobSchedulerAdmin.getInstance().pauseAll();
			}
			else
			{
				String siteName = request.getParameter(SITE_NAME_PARAMETER);
				if (StringUtils.isNotBlank(siteName))
				{
					if (operation.equals(SCHEDULE_SITE_OPERATION))
					{
						JobSchedulerAdmin.getInstance().schedule(siteName);
					}
					else if (operation.equals(PAUSE_SITE_OPERATION))
					{
						JobSchedulerAdmin.getInstance().pause(siteName);
					}
					else if (operation.equals(END_SITE_OPERATION))
					{
						JobSchedulerAdmin.getInstance().end(siteName);
					}
				}
			}

			try
			{
				response.sendRedirect(response.encodeRedirectURL("jobs.jsp"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
