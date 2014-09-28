package com.karniyarik.common.ga;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "referrals")
public class Referrals {
	
	@XmlElement(name = "referrallist")
	@XmlElementWrapper(name = "referrallist")
	private List<Referral> referrals = new ArrayList<Referral>();
	
	public Referrals() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Referral> getReferrals() {
		return referrals;
	}
	
	public void setReferrals(List<Referral> referrals) {
		this.referrals = referrals;
	}
}
