package com.karniyarik.common.ga;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "analytics", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
public class Analytics
{
	@XmlElement(name = "visits")
	private Visits visits = null;
	
	@XmlElement(name = "events")
	private Events events = null;
	
	@XmlElement(name = "referrals")
	private Referrals referrals = null;
	
	@XmlElement(name = "date")
	private Date date;
	
	public Analytics()
	{
		// TODO Auto-generated constructor stub
	}

	public Visits getVisits()
	{
		return visits;
	}

	public void setVisits(Visits visits)
	{
		this.visits = visits;
	}

	public Events getEvents()
	{
		return events;
	}

	public void setEvents(Events events)
	{
		this.events = events;
	}

	public Referrals getReferrals()
	{
		return referrals;
	}

	public void setReferrals(Referrals referrals)
	{
		this.referrals = referrals;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public Date getDate()
	{
		return date;
	}
}
