package com.karniyarik.controller.exception;

public class KarniyarikSiteControllerException extends RuntimeException
{
	private static final long	serialVersionUID	= 4791174974699598509L;

	public KarniyarikSiteControllerException()
	{
		super();
	}
	
	public KarniyarikSiteControllerException(String aMessage)
	{
		super(aMessage);
	}
	
	public KarniyarikSiteControllerException(Throwable anException)
	{
		super(anException);
	}

	public KarniyarikSiteControllerException(String aMessage, Throwable anException)
	{
		super(aMessage, anException);
	}
}
