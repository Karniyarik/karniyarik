package com.karniyarik.common.site;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "shipment", propOrder = { "name", "image"})
public class Shipment {
	/*ARAS("aras.gif","Aras Kargo"),
	MNG("mng.gif","MNG Kargo"),
	DHL("dhl.gif","DHL"),
	UPS("ups.gif","UPS"),
	YURTICI("yurtici.gif","Yurtiçi Kargo");*/

	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "image")
	private String image;

	Shipment(String name, String image)
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
