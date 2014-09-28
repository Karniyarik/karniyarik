package com.karniyarik.common.site;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "certificate", propOrder = { "name", "image"})
public class Certificate {
	/*VERISIGN("verisign.gif", "VeriSign"),
	MCAFEE("mcafee.gif","McAfee"),
	VISA("visa.gif","Visa"),
	MASTERCARD("mastercard.gif","Mastercard"),
	GODADDY("godaddy.gif","GoDaddy");*/

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "image")
	private String image;

	Certificate(String name, String image)
	{
		this.name = name;
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}
}
