package com.karniyarik.common.exception;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "exception", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exception")
public class ExceptionVO
{
	@XmlElement(name = "identifier")
	private String identifier;
	
	@XmlElement(name = "title")
	private String title;
	
	@XmlElement(name = "message")
	private String message;
	
	@XmlElement(name = "stacktrace")
	private String stackTrace;
	
	@XmlElement(name = "date")
	private Date date;
	
	public ExceptionVO()
	{

	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getStackTrace()
	{
		return stackTrace;
	}

	public void setStackTrace(String stackTrace)
	{
		this.stackTrace = stackTrace;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
}
