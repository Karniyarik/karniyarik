package com.karniyarik.common.site;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "payment", propOrder = { "name", "image"})
public class Payment {
	/*DOOR("door.gif","Kapıda"),
	MASTERCARD("mastercard.gif","Kredi Kartı, Mastercard"),
	PAYOUT("payout.gif","Havale"),
	VISA("visa.gif","Kredi Kartı,Visa"),
	PAYPAL("paypal.gif","PayPal");*/
	
	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "image")
	private String image;

	Payment(String name, String image)
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
