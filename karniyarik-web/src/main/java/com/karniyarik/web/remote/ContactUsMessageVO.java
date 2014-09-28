package com.karniyarik.web.remote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contactus")
public class ContactUsMessageVO {
	
	@XmlElement(name = "goal")
	private String goal;
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "email")
	private String email;
	
	@XmlElement(name = "message")
	private String message;
	
	
	public ContactUsMessageVO() {
	}


	public String getGoal()
	{
		return goal;
	}


	public void setGoal(String goal)
	{
		this.goal = goal;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getEmail()
	{
		return email;
	}


	public void setEmail(String email)
	{
		this.email = email;
	}


	public String getMessage()
	{
		return message;
	}


	public void setMessage(String message)
	{
		this.message = message;
	}
}
