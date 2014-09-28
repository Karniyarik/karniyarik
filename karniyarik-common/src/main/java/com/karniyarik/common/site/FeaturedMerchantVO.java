package com.karniyarik.common.site;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "results", propOrder = { "name", "certificates", "payment", "shipment", "facebook", "twitter", 
		"aboutus", "links", "contact", "logo", "sitename"})
public class FeaturedMerchantVO implements Serializable {
	private static final long serialVersionUID = 4163146907056375378L;

	@XmlElement(name = "sitename")
	private String sitename = null;
	
	@XmlElement(name = "name")
	private String name = null;

	@XmlElement(name = "certificates")
	// @XmlElementWrapper(name = "certificates")
	private List<Certificate> certificates = new ArrayList<Certificate>();

	@XmlElement(name = "payment")
	private List<Payment> payment = new ArrayList<Payment>();

	@XmlElement(name = "shipment")
	private List<Shipment> shipment = new ArrayList<Shipment>();

	@XmlElement(name = "facebook")
	private String facebook = null;

	@XmlElement(name = "twitter")
	private String twitter = null;

	@XmlElement(name = "aboutus")
	private String aboutus = null;
	
	@XmlElement(name = "logo")
	private String logo = null;

	@XmlElement(name = "links")
	private Map<String, String> links = new HashMap<String, String>();

	@XmlElement(name = "contact")
	private MerchantContactVO contact = new MerchantContactVO();

	public FeaturedMerchantVO() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public Map<String, String> getLinks() {
		return links;
	}

	public void setLinks(Map<String, String> links) {
		this.links = links;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

	public List<Shipment> getShipment() {
		return shipment;
	}

	public void setShipment(List<Shipment> shipment) {
		this.shipment = shipment;
	}
	
	public MerchantContactVO getContact() {
		return contact;
	}
	
	public void setContact(MerchantContactVO contact) {
		this.contact = contact;
	}
	
	public String getAboutus() {
		return aboutus;
	}
	
	public void setAboutus(String aboutus) {
		this.aboutus = aboutus;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public String getSitename() {
		return sitename;
	}
	
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
}