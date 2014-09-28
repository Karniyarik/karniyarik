package com.karniyarik.dev;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import com.karniyarik.common.util.StreamUtil;

public class MailSender
{
	public MailSender()
	{
		String hostname = "smtp.gmail.com";
		String user = "";
		String pass = "";
		
		try
		{
			File file = StreamUtil.getFile("mails.txt");
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			
			File msgFile = StreamUtil.getFile("message.txt");
			String message = FileUtils.readFileToString(msgFile, "UTF-8");
			for(String line: lines)
			{
				if(StringUtils.isBlank(line))
				{
					continue;
				}
				
				try
				{
					line = line.trim();
					HtmlEmail email = new HtmlEmail();
					email.setDebug(false); // true if you want to debug
					email.setSSL(true);
					email.setCharset("UTF-8");
					email.setHostName(hostname);
					email.addCc("info@karniyarik.com");
					
					email.addTo(line);
					
					email.setFrom(user, "Siyamed SINIR");
					email.setSubject("Karniyarik.com XML Entegrasyon");
					email.setMsg(message);
					
					email.setAuthenticator(new DefaultAuthenticator(
						user, 
						pass));
					
					email.send();
					
					
				}
				catch (Throwable e)
				{
					System.out.println("Cannot send email to " + line);
				}			
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
}
