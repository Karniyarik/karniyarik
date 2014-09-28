package com.karniyarik.recognizer.hepsiburada;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
public class GelirOrtaklariProductList
{
	@XmlElement(name = "product")
	@XmlElementWrapper(name = "products")
	private List<GelirOrtaklariProduct> products = new ArrayList<GelirOrtaklariProduct>();
	
	public GelirOrtaklariProductList()
	{
		// TODO Auto-generated constructor stub
	}
	
	public List<GelirOrtaklariProduct> getProducts()
	{
		return products;
	}
	
	public void setProducts(List<GelirOrtaklariProduct> products)
	{
		this.products = products;
	}	
}
