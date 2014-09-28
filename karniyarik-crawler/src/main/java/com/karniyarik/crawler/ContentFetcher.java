package com.karniyarik.crawler;


public abstract class ContentFetcher {

	private String botName = null;
	private int urlTimeout;
	private String encoding;

	public ContentFetcher(String botName, int urlTimeout, String encoding)
	{
		this.botName = botName;
		this.urlTimeout = urlTimeout;
		this.encoding = encoding;
	}

	public String getBotName() {
		return botName;
	}

	public int getUrlTimeout() {
		return urlTimeout;
	}

	public String getEncoding() {
		return encoding;
	}
	
}
