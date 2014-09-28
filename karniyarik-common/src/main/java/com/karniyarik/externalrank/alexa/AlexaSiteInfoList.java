package com.karniyarik.externalrank.alexa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alexa", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
public class AlexaSiteInfoList {

	@XmlElement(name = "siteinfo")
	@XmlElementWrapper(name = "list")
	List<AlexaSiteInfo> sites = new ArrayList<AlexaSiteInfo>();
	
	@XmlElement
	Date lastFetchDate = null;
	
	public AlexaSiteInfoList() {
	}

	public List<AlexaSiteInfo> getSites() {
		return sites;
	}

	public void setSites(List<AlexaSiteInfo> sites) {
		this.sites = sites;
	}

	public Date getLastFetchDate() {
		return lastFetchDate;
	}

	public void setLastFetchDate(Date lastFetchDate) {
		this.lastFetchDate = lastFetchDate;
	}
}
