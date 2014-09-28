package com.karniyarik.crawler.exception;

public class KarniyarikCrawlerURLFetchException extends RuntimeException
{
	private static final long	serialVersionUID	= 535894265317147232L;
	public static final int	TRY_LATER			= 101;
	public static final int	WAIT_AND_TRY_LATER	= 102;
	public static final int	PASS				= 103;
	
	private int	mErrorCode = PASS;
	private int	mHttpCode = 0;
	private String mURL = null;
	
	public KarniyarikCrawlerURLFetchException()
	{
		super();
	}

	public KarniyarikCrawlerURLFetchException(String aMessage)
	{
		super(aMessage);
	}

	public KarniyarikCrawlerURLFetchException(Throwable anException)
	{
		super(anException);
	}

	public KarniyarikCrawlerURLFetchException(String aMessage, Throwable anException)
	{
		super(aMessage, anException);
	}

	public int getErrorCode()
	{
		return mErrorCode;
	}

	public void setErrorCode(int aErrorCode)
	{
		mErrorCode = aErrorCode;
	}

	public String getURL()
	{
		return mURL;
	}

	public void setURL(String aUrl)
	{
		mURL = aUrl;
	}

	public int getHttpCode()
	{
		return mHttpCode;
	}

	public void setHttpCode(int aHttpCode)
	{
		mHttpCode = aHttpCode;
	}
	
	public String getExceptionSummary() {
		return "\n State: " + "\n Error code " + getHttpCode() + "\n URL: " + getURL() + "\n";
	}
}
