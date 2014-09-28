package com.karniyarik.web.remote;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.web.citydeals.CityDealResult;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "results", propOrder = { "deals", "city" })
public class CityDealResultVO
{
	@XmlElement(name = "deal")
	@XmlElementWrapper(name = "deals")
	private List<CityDealVO>	deals	= new ArrayList<CityDealVO>();

	@XmlElement(name = "city")
	private String				city	= null;

	public CityDealResultVO()
	{
	}

	public CityDealResultVO(List<CityDealResult> cityDeals, String city)
	{
		setCity(city);
		for(CityDealResult cityDeal: cityDeals)
		{
			getDeals().add(new CityDealVO(cityDeal));
		}
	}

	public List<CityDealVO> getDeals()
	{
		return deals;
	}

	public void setDeals(List<CityDealVO> deals)
	{
		this.deals = deals;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}
}