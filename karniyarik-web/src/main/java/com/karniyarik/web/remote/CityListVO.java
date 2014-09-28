package com.karniyarik.web.remote;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.web.citydeals.CityResult;

@XmlRootElement(name = "cities", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cities")
public class CityListVO
{
	@XmlElement(name = "city")
	@XmlElementWrapper(name = "cities")
	private List<CityVO>	cities	= new ArrayList<CityVO>();

	public CityListVO()
	{
		
	}

	public CityListVO(List<CityResult> activeCities)
	{
		for(CityResult city: activeCities)
		{
			getCities().add(new CityVO(city));
		}
	}

	public List<CityVO> getCities()
	{
		return cities;
	}

	public void setCities(List<CityVO> cities)
	{
		this.cities = cities;
	}
}