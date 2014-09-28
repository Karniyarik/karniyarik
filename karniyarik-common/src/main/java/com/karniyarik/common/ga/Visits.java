package com.karniyarik.common.ga;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visits")
public class Visits {
	
	@XmlElement(name = "visitlist")
	@XmlElementWrapper(name = "visitlist")
	private List<Visit> visits = new ArrayList<Visit>();
	
	public Visits() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Visit> getVisits() {
		return visits;
	}
	
	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}
}
