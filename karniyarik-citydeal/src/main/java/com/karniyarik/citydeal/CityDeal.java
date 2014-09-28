package com.karniyarik.citydeal;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "citydeal")
public class CityDeal {
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "description")
	private String description;
	
	@XmlElement(name = "city")
	private String city;
	
	@XmlElement(name = "price")
	private Double price = null;
	
	@XmlElement(name = "discountprice")
	private Double discountPrice = null;
	
	@XmlElement(name = "priceCurrency")
	private String priceCurrency = "TL";
	
	@XmlElement(name = "source")
	private String source = null;
	
	@XmlElement(name = "sourceurl")
	private String sourceURL = null;
	
	@XmlElement(name = "producturl")
	private String productURL = null;
	
	@XmlElement(name = "image")
	private String image = null;
	
	@XmlElement(name = "enddate")
	private Date endDate = null;
	
	@XmlElement(name = "startdate")
	private Date startDate = null;
	
	@XmlElement(name = "id")
	private Integer id; 

	@XmlElement(name = "percentage")
	private Double discountPercentage = null;

	@XmlElement(name = "damount")
	private Double discountAmount = null;

	@XmlElement(name = "paid")
	private boolean paid = false;

	public CityDeal() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		if(source != null)
		{
			source = source.trim();
		}
		this.source = source;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public String getProductURL() {
		return productURL;
	}

	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void setPriceCurrency(String priceCurrency) {
		this.priceCurrency = priceCurrency;
	}
	
	public String getPriceCurrency() {
		return priceCurrency;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public boolean isPaid()
	{
		return paid;
	}
	
	public void setPaid(boolean paid)
	{
		this.paid = paid;
	}
	
	public Double getDiscountAmount()
	{
		return discountAmount;
	}
	
	public void setDiscountAmount(Double discountAmount)
	{
		this.discountAmount = discountAmount;
	}
}