package com.karniyarik.common.ga;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.google.gdata.data.analytics.DataEntry;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visit")
public class Visit {
	
	@XmlElement(name = "totalVisitors")
	private long totalVisitors;
	
	@XmlElement(name = "uniqueVisitors")
	private long uniqueVisitors;
	
	@XmlElement(name = "totalPageviews")
	private long totalPageviews;
	
	@XmlElement(name = "pagesPerVisit")
	private double pagesPerVisit;
	
	@XmlElement(name = "bounceRate")
	private double bounceRate;
	
	@XmlElement(name = "avgTimeOnSite")
	private long avgTimeOnSite;
	
	@XmlElement(name = "newVisits")
	private long newVisits;
	
	@XmlElement(name = "isMobile")
	private boolean isMobile = false;
	
	public Visit() {
		// TODO Auto-generated constructor stub
	}

	public Visit(DataEntry entry) {
		setTotalPageviews(entry.longValueOf("ga:pageviews"));
		setAvgTimeOnSite(entry.longValueOf("ga:timeOnSite"));
    	setNewVisits(entry.longValueOf("ga:newVisits"));
    	setTotalVisitors(entry.longValueOf("ga:visits"));
    	if(entry.getMetric("ga:visitors") != null)
    	{
    		setUniqueVisitors(entry.longValueOf("ga:visitors"));	
    	}
	}
	
	public long getTotalVisitors() {
		return totalVisitors;
	}

	public void setTotalVisitors(long totalVisitors) {
		this.totalVisitors = totalVisitors;
	}

	public long getUniqueVisitors() {
		return uniqueVisitors;
	}

	public void setUniqueVisitors(long uniqueVisitors) {
		this.uniqueVisitors = uniqueVisitors;
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

	public double getBounceRate() {
		return bounceRate;
	}

	public void setBounceRate(double bounceRate) {
		this.bounceRate = bounceRate;
	}

	public long getAvgTimeOnSite() {
		return avgTimeOnSite;
	}

	public void setAvgTimeOnSite(long avgTimeOnSite) {
		this.avgTimeOnSite = avgTimeOnSite;
	}

	public long getNewVisits() {
		return newVisits;
	}

	public void setNewVisits(long newVisits) {
		this.newVisits = newVisits;
	}
	
	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
	
	public boolean isMobile() {
		return isMobile;
	}
}
