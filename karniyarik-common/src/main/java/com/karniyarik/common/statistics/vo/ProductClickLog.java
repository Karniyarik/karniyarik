package com.karniyarik.common.statistics.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.vo.Product;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "productClickLog", propOrder = { "url", "name", "siteName", "query", "productCategory", "sponsor", "date", "apiKey", "ip", "fraud" })
public class ProductClickLog
{

	@XmlElement(name = "url")
	private String url = "";
	
	@XmlElement(name = "name")
	private String name = "";
	
	@XmlElement(name = "siteName")
	private String siteName = "";
	
	@XmlElement(name = "query")
	private String query = "";
	
	@XmlElement(name = "productCategory")
	private String productCategory = "";
	
	@XmlElement(name = "sponsor")
	private boolean sponsor;
	
	@XmlElement(name = "date")
	private long date = new Date().getTime();
	
	@XmlElement(name = "apiKey")
	private String apiKey;
	
	@XmlElement(name = "ip")
	private String ip;
	
	@XmlElement(name = "fraud")
	private boolean fraud = false;

	public ProductClickLog() {
	}

	public ProductClickLog(Product product)
	{
		setName(product.getName());
		setUrl(product.getLink());
		setSiteName(product.getSourceName());
		setProductCategory(product.getCategory());
	}

	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getSiteName()
	{
		return siteName;
	}
	
	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public String getApiKey()
	{
		return apiKey;
	}

	public void setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
	}

	public long getDate()
	{
		return date;
	}

	public void setDate(long date)
	{
		this.date = date;
	}

	public String getProductCategory()
	{
		return productCategory;
	}

	public void setProductCategory(String productCategory)
	{
		this.productCategory = productCategory;
	}

	public boolean isSponsor()
	{
		return sponsor;
	}

	public void setSponsor(boolean sponsor)
	{
		this.sponsor = sponsor;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public boolean isFraud()
	{
		return fraud;
	}

	public void setFraud(boolean fraud)
	{
		this.fraud = fraud;
	}

	/**
	 * Do not name this method isWebQuery
	 * to prevent JSON conflicts
	 * @return
	 */
	public boolean webClick() {
		return StringUtils.isBlank(apiKey);
	}
	
	/**
	 * Do not name this method isApiQuery
	 * to prevent JSON conflicts
	 * @return
	 */
	public boolean apiClick() {
		return StringUtils.isNotBlank(apiKey);
	}
}
