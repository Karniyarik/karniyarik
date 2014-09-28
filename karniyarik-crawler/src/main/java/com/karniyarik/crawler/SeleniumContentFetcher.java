package com.karniyarik.crawler;

import org.openqa.selenium.server.SeleniumServer;

import com.karniyarik.crawler.exception.KarniyarikCrawlerURLFetchException;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class SeleniumContentFetcher extends ContentFetcher
{

	private final Selenium			client;
	private final SeleniumServer	server;

	public SeleniumContentFetcher(SeleniumServer server, Selenium client,
			String botName, int urlTimeout, String encoding)
	{
		super(botName, urlTimeout, encoding);
		this.server = server;
		this.client = client;
	}

	public String getContent(String currentPage, String nextPageUrl) throws KarniyarikCrawlerURLFetchException {

		try {
			client.open(currentPage);
		}
		catch (SeleniumException assumedTimeOutException) {

			KarniyarikCrawlerURLFetchException ex = new KarniyarikCrawlerURLFetchException();
			ex.setErrorCode(KarniyarikCrawlerURLFetchException.TRY_LATER);

			throw ex;
		}

		try {
			client.click("xpath=//a[starts-with(@href, \"" + nextPageUrl + "\")][1]");
		}
		catch (SeleniumException assumedXpathException) {

			KarniyarikCrawlerURLFetchException ex = new KarniyarikCrawlerURLFetchException();
			ex.setURL(currentPage);
			ex.setErrorCode(KarniyarikCrawlerURLFetchException.PASS);

			throw ex;
		}

		//selenium.waitForPageToLoad("" + getUrlTimeout());

		return client.getHtmlSource();
	}

	public void stopClient()
	{

		if (client != null)
		{

			client.stop();
		}
	}

	public void stopServer()
	{

		server.stop();
	}

	public void stop()
	{
		stopClient();
		stopServer();
	}
}
