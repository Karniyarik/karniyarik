package com.karniyarik.web.remote;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.web.citydeals.CityDealResult;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "citydeal")
public class CityDealVO
{
	@XmlElement(name = "desc")
	private String description;
	@XmlElement(name = "city")
	private String city;
	@XmlElement(name = "price")
	private String price = null;
	@XmlElement(name = "discount")
	private String discountPrice = null;
	@XmlElement(name = "currency")
	private String priceCurrency = null;
	@XmlElement(name = "src")
	private String source = null;
	@XmlElement(name = "srcurl")
	private String sourceURL = null;
	@XmlElement(name = "purl")
	private String productURL = null;
	@XmlElement(name = "shareurl")
	private String shareURL = null;
	@XmlElement(name = "img")
	private String image = null;
	@XmlElement(name = "srcimg")
	private String sourceImage = null;
	@XmlElement(name = "sdate")
	private Date startDate = null;
	@XmlElement(name = "edate")
	private Date endDate = null;
	@XmlElement(name = "remainingstr")
	private String remainingTimeStr;
	@XmlElement(name = "remainingtime")
	private long remainingTime;
	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "discountpercentage")
	private String discountPercentage = null;
	
	public CityDealVO()
	{
	}

	public CityDealVO(CityDealResult cityDeal)
	{
		setDescription(cityDeal.getDescription());
		setCity(cityDeal.getCity());
		setDiscountPercentage(cityDeal.getDiscountPercentage());
		setDiscountPrice(cityDeal.getDiscountPrice());
		setEndDate(cityDeal.getEndDate());
		setStartDate(cityDeal.getStartDate());
		setId(cityDeal.getId());
		setImage(cityDeal.getImage());
		setPrice(cityDeal.getPrice());
		setPriceCurrency(cityDeal.getPriceCurrency());
		setProductURL(cityDeal.getProductURL());
		setRemainingTime(cityDeal.getRemainingTime());
		setRemainingTimeStr(cityDeal.getRemainingTimeStr());
		setShareURL(cityDeal.getShareURL());
		setSource(cityDeal.getSource());
		setSourceImage("http://www.karniyarik.com/" + cityDeal.getSourceImage() + "140.png");
		setSourceURL(cityDeal.getSourceURL());
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String getDiscountPrice()
	{
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice)
	{
		this.discountPrice = discountPrice;
	}

	public String getPriceCurrency()
	{
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency)
	{
		this.priceCurrency = priceCurrency;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public String getSourceURL()
	{
		return sourceURL;
	}

	public void setSourceURL(String sourceURL)
	{
		this.sourceURL = sourceURL;
	}

	public String getProductURL()
	{
		return productURL;
	}

	public void setProductURL(String productURL)
	{
		this.productURL = productURL;
	}

	public String getShareURL()
	{
		return shareURL;
	}

	public void setShareURL(String shareURL)
	{
		this.shareURL = shareURL;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getSourceImage()
	{
		return sourceImage;
	}

	public void setSourceImage(String sourceImage)
	{
		this.sourceImage = sourceImage;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getRemainingTimeStr()
	{
		return remainingTimeStr;
	}

	public void setRemainingTimeStr(String remainingTimeStr)
	{
		this.remainingTimeStr = remainingTimeStr;
	}

	public long getRemainingTime()
	{
		return remainingTime;
	}

	public void setRemainingTime(long remainingTime)
	{
		this.remainingTime = remainingTime;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDiscountPercentage()
	{
		return discountPercentage;
	}

	public void setDiscountPercentage(String discountPercentage)
	{
		this.discountPercentage = discountPercentage;
	}
}