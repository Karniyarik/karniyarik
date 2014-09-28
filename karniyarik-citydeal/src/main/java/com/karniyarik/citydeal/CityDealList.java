package com.karniyarik.citydeal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "citydeals", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
public class CityDealList {
	
	@XmlElement(name = "cityDeal")
	@XmlElementWrapper(name = "cityDeals")
	private List<CityDeal> cityDeals = new ArrayList<CityDeal>();

	@XmlElement(name = "city")
	@XmlElementWrapper(name = "cities")
	private List<City> cities = new ArrayList<City>();
	
	public CityDealList() {
		
	}

	public List<CityDeal> getCityDeals() {
		return cityDeals;
	}

	public void setCityDeals(List<CityDeal> cityDeals) {
		this.cityDeals = cityDeals;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
