package com.karniyarik.externalrank.alexa;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "siteinfo")
public class AlexaSiteInfo implements Serializable {
	@XmlElement
	String sitename;
	
	@XmlElement
	String title;
	
	@XmlElement
	String description;
	
	@XmlElement
	Date onlineSince;
	
	@XmlElement
	long medianLoadTime = Integer.MAX_VALUE;
	
	@XmlElement
	long loadPercentage = 100;
	
	@XmlElement
	long linksInCount = 0;
	
	@XmlElement
	long rank = Integer.MAX_VALUE;
	
	@XmlElement
	long rankByCountry = Integer.MAX_VALUE;

	public AlexaSiteInfo() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOnlineSince() {
		return onlineSince;
	}

	public void setOnlineSince(Date onlineSince) {
		this.onlineSince = onlineSince;
	}

	public long getMedianLoadTime() {
		return medianLoadTime;
	}

	public void setMedianLoadTime(long medianLoadTime) {
		this.medianLoadTime = medianLoadTime;
	}

	public long getLoadPercentage() {
		return loadPercentage;
	}

	public void setLoadPercentage(long loadPercentage) {
		this.loadPercentage = loadPercentage;
	}

	public long getLinksInCount() {
		return linksInCount;
	}

	public void setLinksInCount(long linksInCount) {
		this.linksInCount = linksInCount;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public long getRankByCountry() {
		return rankByCountry;
	}

	public void setRankByCountry(long rankByCountry) {
		this.rankByCountry = rankByCountry;
	}
	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
}
