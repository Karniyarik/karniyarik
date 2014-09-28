package com.karniyarik.crawler;

import java.net.SocketException;
import java.util.Random;

import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class ContentFetcherFactory
{

	private static final int	MIN_DYNAMIC_PORT_NUMBER	= 49152;
	private static final int	MAX_DYNAMIC_PORT_NUMBER	= 65535;
	private static final int	MAX_TRY_COUNT			= 100;

	private final String		botName;
	private final int			urlTimeout;
	private final String		encoding;
	private final String		siteUrl;

	public ContentFetcherFactory(String botName, int urlTimeout,
			String encoding, String siteUrl)
	{
		this.botName = botName;
		this.urlTimeout = urlTimeout;
		this.encoding = encoding;
		this.siteUrl = siteUrl;
	}

	public SimpleContentFetcher createSimpleContentFetcher()
	{
		return new SimpleContentFetcher(botName, urlTimeout, encoding);
	}
	
	public HttpClientContentFetcher createHttpClientContentFetcher() {
		return new HttpClientContentFetcher(botName, urlTimeout, encoding);
	}

	public SeleniumContentFetcher createSeleniumContentFetcher()
	{
		Selenium client = null;
		SeleniumServer server = null;

		try
		{
			boolean serverStarted = false;
			int port = getRandomPortNumber();

			RemoteControlConfiguration serverConf = new RemoteControlConfiguration();

			// selenium will not log anything
			serverConf.setDontTouchLogging(true);
			serverConf.setBrowserSideLogEnabled(false);

			int try_count = 0;

			while (!serverStarted)
			{

				if (try_count > MAX_TRY_COUNT)
				{

					throw new RuntimeException(
							"MAX_TRY_COUNT exceeded, can't find a suitable port");
				}

				try
				{

					serverConf.setPort(port);
					server = new SeleniumServer(serverConf);
					server.start();
					serverStarted = true;
					try_count++;
				}
				catch (SocketException e)
				{
					port = getRandomPortNumber();
				}
			}

			client = new DefaultSelenium("localhost", port, "*firefox", siteUrl);

			client.start();
			client.setBrowserLogLevel("off");
			client.setTimeout("" + urlTimeout);

		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot start selenium!!! ...."
					+ e.getMessage(), e);
		}

		return new SeleniumContentFetcher(server, client, botName, urlTimeout,
				encoding);
	}

	private int getRandomPortNumber()
	{

		Random random = new Random(System.currentTimeMillis());
		return random
				.nextInt(MAX_DYNAMIC_PORT_NUMBER - MIN_DYNAMIC_PORT_NUMBER)
				+ MIN_DYNAMIC_PORT_NUMBER;
	}
	
	public static void main(String[] args)
	{
		SeleniumContentFetcher f = new ContentFetcherFactory("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.86 Safari/533.4", 20000, "UTF-8", "http://www.booking.com").createSeleniumContentFetcher();
		System.out.println("Content: " + f.getContent("http://www.booking.com", "http://feeds.booking.com/partner/uEvnGUwpFz8tdyEDjx5pA.tsv"));
	}

}
