package com.karniyarik.web.remote;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringEscapeUtils;

import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.web.json.ProductResult;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "product", propOrder = { "name", "url", "price", "currency",
		"brand", "source", "imageURL", "properties"})
public class ProductVO {
	@XmlElement(name = "name")
	private String name;

	@XmlElement(name = "url")
	private String url;

	@XmlElement(name = "price")
	private String price;

	@XmlElement(name = "currency")
	private String currency;

	@XmlElement(name = "brand")
	private String brand;

	@XmlElement(name = "site")
	private String source;

	@XmlElement(name = "imageurl")
	private String imageURL;

	@XmlElement(name = "prop")
	@XmlElementWrapper(name = "properties")
	private List<ProductPropertyVO> properties = new ArrayList<ProductPropertyVO>();

	public ProductVO() {

	}

	public ProductVO(ProductResult product) {
		setName(product.getProductName());
		setPrice(product.getPrice());
		setCurrency(product.getPriceCurrency());
		setBrand(StringEscapeUtils.unescapeHtml(product.getBrand()));
		setSource(product.getSourceName());
		setImageURL(StringEscapeUtils.unescapeHtml(product.getImageURL()));
		setUrl(product.getLink());
		
		if(product.getProperties() != null)
		{
			for(ProductProperty prop: product.getProperties())
			{
				if(!prop.getName().equals("brand") && !prop.getName().equals("price") && 
						!prop.getName().equals("source") && !prop.getName().equals("class") &&
						!prop.getName().equals("source"))
				{
					ProductPropertyVO propVO = new ProductPropertyVO(prop);
					properties.add(propVO);
				}
			}			
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public List<ProductPropertyVO> getProperties() {
		return properties;
	}
	
	public void setProperties(List<ProductPropertyVO> properties) {
		this.properties = properties;
	}
}
