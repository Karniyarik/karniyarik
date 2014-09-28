package com.karniyarik.web.remote;

public class KarniyarikAPIException extends RuntimeException
{
	private static final long serialVersionUID = -4200238598076356074L;

	public KarniyarikAPIException()
	{
		super();
	}
	
	public KarniyarikAPIException(String aMessage)
	{
		super(aMessage);
	}
	
	public KarniyarikAPIException(Throwable anException)
	{
		super(anException);
	}

	public KarniyarikAPIException(String aMessage, Throwable anException)
	{
		super(aMessage, anException);
	}
}
