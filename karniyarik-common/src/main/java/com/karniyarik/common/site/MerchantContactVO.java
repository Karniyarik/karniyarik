package com.karniyarik.common.site;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "results", propOrder = { "company", "address", "email","phone", "fax", "url", "contactus", "notes" })
public class MerchantContactVO {
	@XmlElement(name = "company")
	private String company = null;

	@XmlElement(name = "address")
	private String address = null;

	@XmlElement(name = "email")
	private String email = null;

	@XmlElement(name = "phone")
	private String phone = null;

	@XmlElement(name = "fax")
	private String fax = null;

	@XmlElement(name = "url")
	private String url = null;

	@XmlElement(name = "notes")
	private String notes = null;

	@XmlElement(name = "contactus")
	private String contactus = null;

	public MerchantContactVO() {
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getContactus() {
		return contactus;
	}
	
	public void setContactus(String contactus) {
		this.contactus = contactus;
	}
}