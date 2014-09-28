package com.karniyarik.common.site;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.common.config.site.SiteConfig;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "site", propOrder = { "name", "displayName", "url", "feedURL", "ecommerce"})
public class SiteVO implements Serializable
{
	@XmlElement(name = "name")
	private String	name;

	@XmlElement(name = "displayName")
	private String	displayName;

	@XmlElement(name = "feedURL")
	private String	feedURL;

	@XmlElement(name = "url")
	private String	url;

	@XmlElement(name = "ecommerce")
	private String	ecommerce;

	public SiteVO()
	{
	}

	public SiteVO(SiteConfig config)
	{
		setName(config.getSiteName());
		// setDisplayName(config.get)
		if (config.getDatafeedUrlList().size() > 0)
		{
			setFeedURL(config.getDatafeedUrlList().get(0));
		} else {
			setFeedURL("");
		}
		setUrl(config.getUrl());
		
		setEcommerce(config.getEcommerceConfig());
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getFeedURL()
	{
		return feedURL;
	}

	public void setFeedURL(String feedURL)
	{
		this.feedURL = feedURL;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getEcommerce() {
		return ecommerce;
	}
	
	public void setEcommerce(String ecommerce) {
		this.ecommerce = ecommerce;
	}
}
