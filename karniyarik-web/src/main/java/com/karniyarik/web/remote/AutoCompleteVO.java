package com.karniyarik.web.remote;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "results", propOrder = {  })
public class AutoCompleteVO
{
	@XmlElement(name = "s")
	private List<String>		queries = new ArrayList<String>();
	public AutoCompleteVO()
	{
	}

	public List<String> getQueries() {
		return queries;
	}
	
	public void setQueries(List<String> queries) {
		this.queries = queries;
	}
}