package com.karniyarik.web.citydeals;

import com.karniyarik.citydeal.City;

public class CityResult {
	public String name;
	public String value;
	public String url;
	public boolean selected = false;
	private int dealCount;
	
	public CityResult() {
		
	}

	public CityResult(City city) {
		setName(city.getName());
		setValue(city.getValue());
		setUrl("sehir-firsati/" + city.getValue() + "-firsatlari");
		setDealCount(city.getDealCount());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public int getDealCount()
	{
		return dealCount;
	}
	
	public void setDealCount(int dealCount)
	{
		this.dealCount = dealCount;
	}
}
