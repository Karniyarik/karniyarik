package com.karniyarik.common.notifier;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;

import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.MailConfig;
import com.karniyarik.common.log.KarniyarikLogger;

public class MailNotifier 
{
	public void sendTextMail(String subject, String textMessage) {
		MailConfig mailConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getMailConfig();
		
		if(mailConfig.isEnabled())
		{
			sendTextMail(subject, textMessage, mailConfig.getTo(), mailConfig.getFrom(), mailConfig.getHostName(), mailConfig.getUser(), mailConfig.getPassword());	
		}
	}
	
	public void sendTextMail(String subject, String textMessage, String[] toList, String from, String hostName, String user, String password)
	{
		MailConfig mailConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getMailConfig();
		
		try 
		{
			InetAddress addr = InetAddress.getLocalHost();
			
			String hostname = addr.getHostName(); 
			String ip = addr.getHostAddress();
			
			String hostId = hostname + ", " + ip;
			
			subject = hostId + ":" + subject;
			
			textMessage = textMessage + "\n\n" + "Sender: " + hostId;
			
			// Create the email message
			SimpleEmail email = new SimpleEmail();

			// Very Important, Don't use email.setAuthentication()
			email.setAuthenticator(new DefaultAuthenticator(user, password));
			email.setDebug(false); // true if you want to debug
			email.setHostName(hostName);
			email.setSSL(mailConfig.isUseSSL());
			//email.getMailSession().getProperties().put("mail.smtp.starttls.enable",	"true");
			
			for (String to : toList)
			{
				email.addTo(to);
			}

			email.setFrom(from);
			
			email.setSubject(subject);

			// set the alternative message
			email.setMsg(textMessage);
			
			// set charset to UTF-8
			email.setCharset("UTF-8");

			// send the email
			email.send();
		}
		catch (Throwable e) 
		{
			KarniyarikLogger.logException("Cannot send mail", e, Logger.getLogger(this.getClass()));
			
			logMail(textMessage, mailConfig);
		}
	}

	private void logMail(String textMessage, MailConfig mailConfig) {
		try {
			File dir = new File(mailConfig.getStorage());
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			
			File file = new File(mailConfig.getStorage() + "/" + System.currentTimeMillis() + ".mail.txt");
			
			IOUtils.write(textMessage, new FileOutputStream(file));
		} catch (Throwable e) {
			KarniyarikLogger.logExceptionSummary("Cannot log the mail", e, Logger.getLogger(this.getClass()));
		} 
	}	
	
	public void sendHtmlMail(String subject, String textMessage, String htmlMessage)
	{
		MailConfig mailConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getMailConfig();
		
		if(mailConfig.isEnabled())
		{
			sendHtmlMail(subject, textMessage, htmlMessage, mailConfig.getTo(), mailConfig.getFrom(), mailConfig.getHostName(), mailConfig.getUser(), mailConfig.getPassword());	
		}
	}	

	public void sendHtmlMail(String subject, String textMessage, String htmlMessage, String[] toList, String from, String hostName, String user, String password)
	{
		MailConfig mailConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getMailConfig();
		
		try 
		{
			InetAddress addr = InetAddress.getLocalHost();
			
			String hostname = addr.getHostName(); 
			String ip = addr.getHostAddress();
			
			String hostId = hostname + ", " + ip;
			
			subject = hostId + ":" + subject;
			
			textMessage = textMessage + "\n\n" + "Sender: " + hostId;
			
			// Create the email message
			HtmlEmail email = new HtmlEmail();

			// Very Important, Don't use email.setAuthentication()
			email.setAuthenticator(new DefaultAuthenticator(user, password));
			email.setDebug(false); // true if you want to debug
			email.setHostName(hostName);
			email.setSSL(mailConfig.isUseSSL());
			//email.getMailSession().getProperties().put("mail.smtp.starttls.enable",	"true");
			
			for (String to : toList)
			{
				email.addTo(to);
			}

			email.setFrom(from);
			
			email.setSubject(subject);

			// set the html message
			email.setHtmlMsg(htmlMessage);

			// set the alternative message
			email.setTextMsg(textMessage);
			
			// set charset to UTF-8
			email.setCharset("UTF-8");

			// send the email
			email.send();
		}
		catch (Throwable e) 
		{
			KarniyarikLogger.logException("Cannot send mail", e, Logger.getLogger(this.getClass()));
			
			logMail(htmlMessage + "\n\n" + textMessage, mailConfig);
		}
	}	

	
	public static void main(String[] args) 
	{
		new MailNotifier().sendHtmlMail("aloo", "textmessage", "htmlmessage");
	}
}
