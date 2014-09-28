package com.karniyarik.common.ga;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.google.gdata.data.analytics.DataEntry;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "referral")
public class Referral {
	
	@XmlElement(name = "source")
	private String source;
	
	@XmlElement(name = "totalVisitors")
	private long totalVisitors;
	
	@XmlElement(name = "totalPageviews")
	private long totalPageviews;
	
	@XmlElement(name = "pagesPerVisit")
	private double pagesPerVisit;
	
	@XmlElement(name = "timeOnSite")
	private long timeOnSite;
	
	@XmlElement(name = "exits")
	private long exits;
	
	@XmlElement(name = "avgTimeOnSite")
	private long avgTimeOnSite;
	
	public Referral() {
		// TODO Auto-generated constructor stub
	}

	public Referral(DataEntry entry) {
		setTotalPageviews(entry.longValueOf("ga:pageviews"));
		setTimeOnSite(entry.longValueOf("ga:timeOnSite"));
    	setTotalVisitors(entry.longValueOf("ga:visits"));
    	setSource(entry.stringValueOf("ga:source"));
    	setExits(entry.longValueOf("ga:exits"));
	}

	public long getTotalVisitors() {
		return totalVisitors;
	}

	public void setTotalVisitors(long totalVisitors) {
		this.totalVisitors = totalVisitors;
	}

	public long getTotalPageviews() {
		return totalPageviews;
	}

	public void setTotalPageviews(long totalPageviews) {
		this.totalPageviews = totalPageviews;
	}

	public double getPagesPerVisit() {
		return pagesPerVisit;
	}

	public void setPagesPerVisit(double pagesPerVisit) {
		this.pagesPerVisit = pagesPerVisit;
	}

	public long getAvgTimeOnSite() {
		return avgTimeOnSite;
	}

	public void setAvgTimeOnSite(long avgTimeOnSite) {
		this.avgTimeOnSite = avgTimeOnSite;
	}
	
	public long getExits() {
		return exits;
	}
	
	public void setExits(long exits) {
		this.exits = exits;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setTimeOnSite(long timeOnSite) {
		this.timeOnSite = timeOnSite;
	}
	
	public long getTimeOnSite() {
		return timeOnSite;
	}
}
