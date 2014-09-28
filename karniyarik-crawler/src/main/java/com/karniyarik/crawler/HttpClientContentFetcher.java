package com.karniyarik.crawler;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientContentFetcher implements IContentFetcher
{
	private final String	encoding;
	private final String	botName;
	private final int		timeout;

	public HttpClientContentFetcher(String botName, int timeout, String encoding)
	{
		this.encoding = encoding;
		this.timeout = timeout;
		this.botName = botName;
	}

	public String getContent(String urlString)
	{
		String aResult = "";
		InputStream content = null;

		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpParams params = client.getParams();
			HttpConnectionParams.setConnectionTimeout(params, timeout);
			HttpConnectionParams.setSoTimeout(params, timeout);
			params.setParameter("User-agent", botName);
			params.setParameter("Cache-Control", "max-age=0,no-cache");
			params.setParameter("Pragma", "no-cache");
			HttpGet get = new HttpGet(urlString);
			
			HttpResponse execute = client.execute(get);
			content = execute.getEntity().getContent();
			aResult = IOUtils.toString(content, encoding);
			execute.getEntity().consumeContent();
		}
		catch (Throwable t)
		{
			throw new RuntimeException(t);
		}

		return aResult;
	}

	public static void main(String[] args)
	{
		// XContentFetcher fetcher = new
		// XContentFetcher("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.86 Safari/533.4",
		// 20000, "UTF-8");
		HttpClientContentFetcher fetcher = new HttpClientContentFetcher("karniyarik", 20000, "UTF-8");
		System.out.println("content: " + fetcher.getContent("http://www.e-bebek.com/xml.aspx?user=krnyrk001&pass=k001&type=karniyarik"));
	}
}
