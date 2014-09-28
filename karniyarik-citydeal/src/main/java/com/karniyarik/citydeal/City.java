package com.karniyarik.citydeal;

import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StringUtil;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "city")
public class City {
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "value")
	private String value;
	
	@XmlElement(name = "dealcount")
	private int dealCount;
	
	public City() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if(StringUtils.isNotBlank(name))
		{
			setValue(getValue(name));
		}
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}
	
	public static String getValue(String name)
	{
		if(StringUtils.isNotBlank(name))
		{
			return StringUtil.convertTurkishCharacter(name).toLowerCase(Locale.ENGLISH);
		}
		
		return name;
	}
	
	public int getDealCount() {
		return dealCount;
	}
	
	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}
}
