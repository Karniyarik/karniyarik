package com.karniyarik.web.remote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "siteRegistration", propOrder={"registrarName", "siteName", "url","feedURL","description","email","phone"} )
public class SiteRegistrationRequestVO {
	
	@XmlElement(name = "registrarName")
	private String registrarName;
	
	@XmlElement(name = "siteName")
	private String siteName;
	
	@XmlElement(name = "url")
	private String url;
	
	@XmlElement(name = "feedURL")
	private String feedURL;
	
	@XmlElement(name = "description")
	private String description;
	
	@XmlElement(name = "email")
	private String email;
	
	@XmlElement(name = "phone")
	private String phone;
	
	public SiteRegistrationRequestVO() {
	}

	public String getRegistrarName() {
		return registrarName;
	}

	public void setRegistrarName(String registrarName) {
		this.registrarName = registrarName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFeedURL() {
		return feedURL;
	}

	public void setFeedURL(String feedURL) {
		this.feedURL = feedURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

