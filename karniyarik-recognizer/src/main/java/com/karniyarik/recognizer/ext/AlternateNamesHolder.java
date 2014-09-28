package com.karniyarik.recognizer.ext;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class AlternateNamesHolder {
	private String name;
	private List<String> alternateNames=new ArrayList<String>();
	
	public AlternateNamesHolder() {
		
	}

	public AlternateNamesHolder(String[] strArr) {
		if(strArr == null || strArr.length == 0 || StringUtils.isBlank(strArr[0]))
		{
			throw new RuntimeException("Invalid input for country");
		}
		
		setName(strArr[0].trim());
		
		for(int index=1; index<strArr.length; index++)
		{
			if(StringUtils.isNotBlank(strArr[index]))
			{
				alternateNames.add(strArr[index].trim());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAlternateNames() {
		return alternateNames;
	}

	public void setAlternateNames(List<String> alternateNames) {
		this.alternateNames = alternateNames;
	}
}
