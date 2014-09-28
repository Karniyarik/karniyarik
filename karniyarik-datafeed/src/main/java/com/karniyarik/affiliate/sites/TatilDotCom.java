package com.karniyarik.affiliate.sites;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.karniyarik.affiliate.IAffiliateSite;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.vo.CurrencyType;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.datafeed.WebServiceDatafeedParser;
import com.karniyarik.parser.pojo.Product;
import com.karniyarik.parser.util.BrandUtil;
import com.karniyarik.recognizer.RecognizerImpl;

public class TatilDotCom extends WebServiceDatafeedParser implements IAffiliateSite {
	
	public String correctUrl(String url)
	{
		StringBuffer newUrl = new StringBuffer(url);
		
//		if(!url.contains("?"))
//		{
//			newUrl.append("?");
//		}
//		newUrl.append("lang=tr");
//		newUrl.append("&aid=334273");
		
		return newUrl.toString();
	}
	
	
	@Override
	public List<Product> parse(String content) {
		Map<String, Product> productMap = new HashMap<String, Product>();
		
		//List<Product> productList = new ArrayList<Product>();
		
		String hotellist = getContentFetcher().getContent("http://www.hotelspro.com/xf_3.0/downloads/DF9852hotellist.xml");
		//String hotellist = getContentFetcher().getContent("file:///C:/Downloads/DF9852hotellist.xml");
		String hotedesc = getContentFetcher().getContent("http://www.hotelspro.com/xf_3.0/downloads/DF9852hoteldescr.xml");
		//String hotedesc = getContentFetcher().getContent("file:///C:/Downloads/DF9852hoteldescr.xml");
		
		//http://www.hotelspro.com/xf_3.0/downloads/DF9852hotelamenities.xml	
		SAXReader reader = new SAXReader();
		Document document;
		Product product;
		if (StringUtils.isNotBlank(hotellist))
		{
			try
			{
				document = reader.read(new StringInputStream(hotellist, getSiteConfig().getSiteEncoding()));
				Date fetchDate = new Date();
				List<Element> elements = document.getRootElement().element("Hotels").elements();
				for (Element element : elements)
				{
					product = parseElement(element);
					if (product != null)
					{
						product.setFetchDate(fetchDate);
						productMap.put(product.getBreadcrumb(), product);
						product.setBreadcrumb("");
					}
				}
			}
			catch (Throwable e)
			{
				getLogger().error("Can not parse XML datafeed for " + getSiteConfig().getSiteName() + " content: " + content);
			}
		}
		
		
		reader = new SAXReader();
		if (StringUtils.isNotBlank(hotedesc))
		{
			try
			{
				document = reader.read(new StringInputStream(hotedesc, getSiteConfig().getSiteEncoding()));
				Date fetchDate = new Date();
				List<Element> elements = document.getRootElement().element("Hotels").elements();
				for (Element element : elements)
				{
					String code = getValue(element, "HotelCode");
					product = productMap.get(code);
					if (product != null)
					{
						product.getProperties().add(new ProductProperty("description", getValue(element, "HotelInfo")));
					}
				}
			}
			catch (Throwable e)
			{
				getLogger().error("Can not parse XML datafeed for " + getSiteConfig().getSiteName() + " content: " + content);
			}
		}
		
		return new ArrayList<Product>(productMap.values());
	}
	
	private Product parseElement(Element element)
	{
		Product product = new Product();
		product.setName(getValue(element, getSiteConfig().getDatafeedNameXPath()));
		
		String hotelCode = getValue(element, "HotelCode");
		product.setBreadcrumb(hotelCode);
		
		String name = product.getName();
		name = name.trim().replaceAll("\\s+", " ");
		name = name.replaceAll("\\s", "_");
		String url = "http://www.tatil.com/otel/" + name + ".htm"; 
		if (StringUtils.isNotBlank(url))
		{
			url = getUrlManager().breakURL(url);
		}
		
		product.setUrl(url);

		//String priceText = getValue(element, getSiteConfig().getDatafeedPriceXPath());
		product.setPrice(1f); //PriceUtil.parsePrice(priceText);

		//String currencyText = getValue(element, getSiteConfig().getDatafeedCurrencyXPath());
//		if (StringUtils.isBlank(currencyText))
//		{
//			currencyText = priceText;
//		}
//		else
//		{
//			currencyText = priceText + " " + currencyText;
//		}
		
		product.setPriceCurrency(CurrencyType.TL.getCode());

		product.setImageUrl(getValue(element, getSiteConfig().getDatafeedImageElementName()));

		String brandText = getValue(element, getSiteConfig().getDatafeedBrandXPath());
		
		product.setBrand(BrandUtil.getBrand(getSiteConfig().getSingleBrand(), brandText, product.getName(), product.getBreadcrumb(), null));

		List<ProductProperty> properties = new ArrayList<ProductProperty>();
		String propertyValue;
		
		for (CategoryPropertyConfig propertyConfig : getCategoryConfig().getPropertyMap().values())
		{
			propertyValue = getValue(element, getSiteConfig().getDatafeedPropertyXQuery(propertyConfig.getName()));
			
			if(propertyConfig.getName().equalsIgnoreCase("country"))
			{
				propertyValue = RecognizerImpl.getInstance().recognizeCountry(propertyValue);				
			}

			if(propertyConfig.getName().equalsIgnoreCase("city"))
			{
				propertyValue = RecognizerImpl.getInstance().recognizeCity(propertyValue);				
			}

			if (StringUtils.isNotBlank(propertyValue))
			{
				properties.add(new ProductProperty(propertyConfig.getName(), propertyValue));
			}
		}
		
		product.setProperties(properties);
		
		return product;
	}

	private String getValue(Element element, String propertyXPath)
	{
		String value = "";
		if (StringUtils.isNotBlank(propertyXPath) && element != null)
		{
			value = element.valueOf(propertyXPath).trim();
		}
		return value;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
}