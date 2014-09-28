package com.karniyarik.common.statistics.vo;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "searchLog", propOrder = { "date", "query", "resultCount", "suggestionList", "time", "category", "httpAgent", "apiKey", "spam", "products" })
public class SearchLog
{

	@XmlElement(name = "date")
	private long					date = new Date().getTime();

	@XmlElement(name = "query")
	private String					query;

	@XmlElement(name = "resultCount")
	private int						resultCount;

	@XmlElement(name = "suggestion")
	@XmlElementWrapper(name = "suggestionList")
	private String[]				suggestionList;

	@XmlElement(name = "time")
	private double					time;

	@XmlElement(name = "category")
	private String					category;

	@XmlElement(name = "httpAgent")
	private String					httpAgent;

	@XmlElement(name = "apiKey")
	private String					apiKey;		// can be empty for web

	@XmlElement(name = "spam")
	private boolean					spam;

	@XmlElement(name = "productSummary")
	@XmlElementWrapper(name = "products")
	private List<ProductSummary>	products;

	public long getDate()
	{
		return date;
	}

	public void setDate(long date)
	{
		this.date = date;
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	public int getResultCount()
	{
		return resultCount;
	}

	public void setResultCount(int resultCount)
	{
		this.resultCount = resultCount;
	}

	public String[] getSuggestionList()
	{
		return suggestionList;
	}

	public void setSuggestionList(String[] suggestionList)
	{
		this.suggestionList = suggestionList;
	}

	public double getTime()
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getHttpAgent()
	{
		return httpAgent;
	}

	public void setHttpAgent(String httpAgent)
	{
		this.httpAgent = httpAgent;
	}

	public String getApiKey()
	{
		return apiKey;
	}

	public void setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
	}
	
	public boolean isSpam()
	{
		return spam;
	}

	public void setSpam(boolean spam)
	{
		this.spam = spam;
	}

	public List<ProductSummary> getProducts()
	{
		return products;
	}

	public void setProducts(List<ProductSummary> products)
	{
		this.products = products;
	}

	/**
	 * Do not name this method isWebQuery to prevent JSON conflicts
	 * 
	 * @return
	 */
	public boolean webQuery()
	{
		return StringUtils.isBlank(apiKey);
	}

	/**
	 * Do not name this method isApiQuery to prevent JSON conflicts
	 * 
	 * @return
	 */
	public boolean apiQuery()
	{
		return StringUtils.isNotBlank(apiKey);
	}

}
