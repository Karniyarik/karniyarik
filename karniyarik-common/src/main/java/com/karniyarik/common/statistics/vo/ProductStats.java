package com.karniyarik.common.statistics.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "productStats", propOrder = { "productStatList" })
public class ProductStats
{

	@XmlElement(name = "productStat")
	@XmlElementWrapper(name = "productStatList")
	private List<ProductStat>	productStatList = new ArrayList<ProductStat>();

	public List<ProductStat> getProductStatList()
	{
		return productStatList;
	}

	public void setProductStatList(List<ProductStat> productStatList)
	{
		this.productStatList = productStatList;
	}

}
