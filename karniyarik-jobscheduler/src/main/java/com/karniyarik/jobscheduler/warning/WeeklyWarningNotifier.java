package com.karniyarik.jobscheduler.warning;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.notifier.MailNotifier;

public class WeeklyWarningNotifier implements Job
{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		WeeklyWarnings currentWarnings = new WarningMessageController().getCurrentWarnings();
		StringBuffer message = new StringBuffer();
		
		message.append("<html>");
		message.append("<body style='margin:20px;'>");
		message.append("<p>");
		message.append("The following warnings/exceptions has ben occured this week");
		message.append("</p>");
		message.append("<table>");
		for(String type: currentWarnings.getWarningTypeCount().keySet()){
			message.append("<tr>");
			message.append("<td>"+type+"</td>");
			message.append("<td>" + currentWarnings.getWarningTypeCount().get(type)+ "</td>");
			message.append("</tr>");
		} 
		message.append("</table>");
		message.append("<p><a href='http://admin.karniyarik.com/karniyarik-jobscheduler/warnings.jsp'>");
		message.append("Please visit this link for details</a></p>");
		message.append("</body>");
		message.append("</html>");
		
		new MailNotifier().sendHtmlMail("Karniyarik Weekly Warnings", "", message.toString());
	}

	public static void main(String[] args) throws Exception
	{
		new KarniyarikLogger().initLogger();
		new WeeklyWarningNotifier().execute(null);
	}
}
