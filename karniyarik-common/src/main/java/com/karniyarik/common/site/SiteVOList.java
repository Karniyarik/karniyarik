package com.karniyarik.common.site;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("serial")
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "vo", propOrder = { "sitelist" })
public class SiteVOList implements Serializable {
	@XmlElement(name = "sitelist")
	private List<SiteVO> sitelist = new ArrayList<SiteVO>();

	public SiteVOList() {
	}
	
	public List<SiteVO> getSitelist() {
		return sitelist;
	}
	
	public void setSitelist(List<SiteVO> sitelist) {
		this.sitelist = sitelist;
	}	
}
