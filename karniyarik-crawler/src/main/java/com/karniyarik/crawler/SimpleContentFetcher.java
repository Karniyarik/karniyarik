package com.karniyarik.crawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;

import com.karniyarik.crawler.exception.KarniyarikCrawlerURLFetchException;

public class SimpleContentFetcher extends ContentFetcher implements IContentFetcher
{

	public SimpleContentFetcher(String botName, int urlTimeout, String encoding)
	{
		super(botName, urlTimeout, encoding);
	}

	public KarniyarikCrawlerURLFetchException createException(int anErrorCode,
			String aURL, Throwable aThrowable, int aHttpCode)
	{
		KarniyarikCrawlerURLFetchException anEx = new KarniyarikCrawlerURLFetchException(
				"Cannot cannot fetch content for url " + aURL, aThrowable);

		anEx.setErrorCode(anErrorCode);
		anEx.setURL(aURL);
		anEx.setHttpCode(aHttpCode);
		return anEx;
	}

	public String getContent(String urlString)
	{
		String aResult = null;

		URLConnection aURLConnection = null;

		try
		{
			URL aURL = new URL(urlString);

			aURLConnection = aURL.openConnection();

			if (aURLConnection instanceof HttpURLConnection)
			{
				// ((HttpURLConnection)
				// aURLConnection).setInstanceFollowRedirects(false);
			}

			aURLConnection.setConnectTimeout(getUrlTimeout());
			aURLConnection.setReadTimeout(getUrlTimeout());
			aURLConnection.setDefaultUseCaches(false);
			aURLConnection.setUseCaches(false);
			aURLConnection.setRequestProperty("User-agent", getBotName());
			aURLConnection.setRequestProperty("Cache-Control",
					"max-age=0,no-cache");
			aURLConnection.setRequestProperty("Pragma", "no-cache");

			aURLConnection.connect();

			if (aURLConnection instanceof HttpURLConnection)
			{
				// we can use this function to check smth.
				checkHttpStatus(aURLConnection);
			}

			InputStream aStream = aURLConnection.getInputStream();

			aResult = IOUtils.toString(aStream, getEncoding());

			aStream.close();
		}
		catch (KarniyarikCrawlerURLFetchException aKarniyarikException)
		{
			clearConnectionBuffer(aURLConnection);
			throw aKarniyarikException;
		}
		catch (UnknownHostException anUnknownHostException)
		{
			clearConnectionBuffer(aURLConnection);
			throw createException(
					KarniyarikCrawlerURLFetchException.WAIT_AND_TRY_LATER,
					urlString, anUnknownHostException, 0);
		}
		catch (ConnectException aConnectException)
		{
			clearConnectionBuffer(aURLConnection);
			throw createException(
					KarniyarikCrawlerURLFetchException.WAIT_AND_TRY_LATER,
					urlString, aConnectException, 0);
		}
		catch (NoRouteToHostException aNoRouteToHostException)
		{
			clearConnectionBuffer(aURLConnection);
			throw createException(
					KarniyarikCrawlerURLFetchException.WAIT_AND_TRY_LATER,
					urlString, aNoRouteToHostException, 0);
		}
		catch (SocketTimeoutException aSocketTimeoutException)
		{
			clearConnectionBuffer(aURLConnection);
			throw createException(KarniyarikCrawlerURLFetchException.TRY_LATER,
					urlString, aSocketTimeoutException, 0);
		}
		catch (SocketException aSocketException)
		{
			clearConnectionBuffer(aURLConnection);
			throw createException(KarniyarikCrawlerURLFetchException.TRY_LATER,
					urlString, aSocketException, 0);
		}
		catch (FileNotFoundException aFileNotFoundException)
		{
			clearConnectionBuffer(aURLConnection);
			throw createException(KarniyarikCrawlerURLFetchException.PASS,
					urlString, aFileNotFoundException, 0);
		}
		catch (Throwable e)
		{
			clearConnectionBuffer(aURLConnection);
			throw createException(KarniyarikCrawlerURLFetchException.TRY_LATER,
					urlString, e, 0);
		}

		return aResult;
	}

	private void clearConnectionBuffer(URLConnection aURLConnection)
	{
		if (aURLConnection != null
				&& aURLConnection instanceof HttpURLConnection)
		{
			try
			{
				// int aRespCode = ((HttpURLConnection)
				// aURLConnection).getResponseCode();
				InputStream anErrorStream = ((HttpURLConnection) aURLConnection)
						.getErrorStream();

				// int ret = 0;

				IOUtils.toString(anErrorStream);

				anErrorStream.close();
			}
			catch (Throwable ex)
			{
				// deal with the exception
			}

		}
	}

	private void checkHttpStatus(URLConnection aURLConnection)
			throws IOException
	{
		int anHttpStatus = ((HttpURLConnection) aURLConnection)
				.getResponseCode();

		switch (anHttpStatus)
		{
		// 2XX: success
		case HttpURLConnection.HTTP_OK:
		case HttpURLConnection.HTTP_CREATED:
		case HttpURLConnection.HTTP_ACCEPTED:
		case HttpURLConnection.HTTP_NOT_AUTHORITATIVE:
		case HttpURLConnection.HTTP_RESET:
		case HttpURLConnection.HTTP_NO_CONTENT:
		case HttpURLConnection.HTTP_PARTIAL:
			// 3XX: relocation/redirect
		case HttpURLConnection.HTTP_MULT_CHOICE:
		case HttpURLConnection.HTTP_MOVED_PERM:
		case HttpURLConnection.HTTP_MOVED_TEMP:
		case HttpURLConnection.HTTP_SEE_OTHER:
		case HttpURLConnection.HTTP_NOT_MODIFIED:
		case HttpURLConnection.HTTP_USE_PROXY:
			return; // OK CONTINUE FETCHING CONTENT
			// 4XX: client error
		case HttpURLConnection.HTTP_BAD_REQUEST:
		case HttpURLConnection.HTTP_UNAUTHORIZED:
		case HttpURLConnection.HTTP_PAYMENT_REQUIRED:
		case HttpURLConnection.HTTP_FORBIDDEN:
		case HttpURLConnection.HTTP_NOT_FOUND:
		case HttpURLConnection.HTTP_BAD_METHOD:
		case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
		case HttpURLConnection.HTTP_PROXY_AUTH:
		case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
		case HttpURLConnection.HTTP_CONFLICT:
		case HttpURLConnection.HTTP_GONE:
		case HttpURLConnection.HTTP_LENGTH_REQUIRED:
		case HttpURLConnection.HTTP_PRECON_FAILED:
		case HttpURLConnection.HTTP_ENTITY_TOO_LARGE:
		case HttpURLConnection.HTTP_REQ_TOO_LONG:
		case HttpURLConnection.HTTP_UNSUPPORTED_TYPE:
			// 5XX: server error
		case HttpURLConnection.HTTP_NOT_IMPLEMENTED:
		case HttpURLConnection.HTTP_BAD_GATEWAY:
		case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
		case HttpURLConnection.HTTP_VERSION:
			throw createException(KarniyarikCrawlerURLFetchException.PASS,
					aURLConnection.getURL().toString(), null, anHttpStatus);
		case HttpURLConnection.HTTP_UNAVAILABLE:
		case HttpURLConnection.HTTP_INTERNAL_ERROR:
			throw createException(
					KarniyarikCrawlerURLFetchException.WAIT_AND_TRY_LATER,
					aURLConnection.getURL().toString(), null, anHttpStatus);
		}
	}
	
	public static void main(String[] args)
	{
		SimpleContentFetcher fetcher = new SimpleContentFetcher("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.86 Safari/533.4", 20000, "UTF-8");
		System.out.println("content: " + fetcher.getContent("http://feeds.booking.com/partner/uEvnGUwpFz8tdyEDjx5pA.tsv"));
	}
}
