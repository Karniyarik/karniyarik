package com.karniyarik.parser.tester;

import com.karniyarik.parser.pojo.Product;

public class UrlTestResult
{
	private String url;
	private UrlTestState state;
	private Product product;
	private String testClass;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public UrlTestState getState()
	{
		return state;
	}

	public void setState(UrlTestState state)
	{
		this.state = state;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public String getTestClass() {
		return testClass;
	}

	public void setTestClass(String testClass) {
		this.testClass = testClass;
	}
	
}
