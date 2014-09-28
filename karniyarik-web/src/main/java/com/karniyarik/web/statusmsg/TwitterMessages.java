package com.karniyarik.web.statusmsg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.Status;

import com.karniyarik.web.util.WebLoggerProvider;

public class TwitterMessages 
{
	private static TwitterMessages instance = null;
	private static String regex  = "((https?|ftp):((//)|(\\\\\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	private List<TwitterMessage> messages = Collections.synchronizedList(new ArrayList<TwitterMessage>());
		
	private TwitterMessages() {
		update();
	}
	
	public static TwitterMessages getInstance() {
		if(instance == null)
		{
			instance = new TwitterMessages();
		}
		return instance;
	}
	
	public void update() {
		List<Status> statusUpdates = null;
		try 
		{
			statusUpdates = KTwitter.getInstance().getStatusUpdates(5);
			messages.clear();
			for(Status status: statusUpdates)
			{
				String msg = status.getText();
				msg = msg.replaceAll(regex, "<a rel='nofollow' href='$1'>$1</a>");
				messages.add(new TwitterMessage(msg));
			}
		} 
		catch (Throwable e) 
		{
			WebLoggerProvider.logException("Cannot get twitter messages", e);
		}	
	}
	
	public List<TwitterMessage> getMessages() {
		return messages;
	}
	
	public static void main(String[] args) {
		TwitterMessages.getInstance().update();
	}
}
