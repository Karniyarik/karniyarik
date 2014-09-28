package com.karniyarik.affiliate.sites;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.karniyarik.affiliate.IAffiliateSite;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.datafeed.WebServiceDatafeedParser;
import com.karniyarik.datafeed.ws.tatilsepeti.AuthHeader;
import com.karniyarik.datafeed.ws.tatilsepeti.Servis;
import com.karniyarik.datafeed.ws.tatilsepeti.ServisSoap;
import com.karniyarik.parser.pojo.Product;
import com.karniyarik.parser.util.BrandUtil;
import com.karniyarik.parser.util.PriceUtil;
import com.karniyarik.recognizer.RecognizerImpl;

public class TatilSepetiDotCom extends WebServiceDatafeedParser implements IAffiliateSite {
	
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
		List<Product> productList = new ArrayList<Product>();
		
		System.getProperties().put("http.proxySet", "true");
		System.getProperties().put("http.proxyHost", "");
		System.getProperties().put("http.proxyPort", "");
		System.getProperties().put("http.proxyUser", "");
		System.getProperties().put("http.proxyPassword", "");
		Authenticator.setDefault(new Authenticator(){
			protected Object clone() throws CloneNotSupportedException{return super.clone();}
			protected PasswordAuthentication getPasswordAuthentication(){return new PasswordAuthentication("", "".toCharArray());}
		});
		
		ServisSoap servisSoap = new Servis().getServisSoap12();
		AuthHeader header = new AuthHeader();
		header.setUsername("");
		header.setPassword("");
		String tesisler = servisSoap.tesisler(header);
		
		SAXReader reader = new SAXReader();
		Document document;
		Product product;
		if (StringUtils.isNotBlank(tesisler))
		{
			try
			{
				document = reader.read(new StringInputStream(tesisler, getSiteConfig().getSiteEncoding()));
				Date fetchDate = new Date();
				List<Element> elements = document.getRootElement().elements();
				for (Element element : elements)
				{
					product = parseElement(element);
					if (product != null)
					{
						product.setFetchDate(fetchDate);
						productList.add(product);
					}
				}
			}
			catch (Throwable e)
			{
				getLogger().error("Can not parse XML datafeed for " + getSiteConfig().getSiteName() + " content: " + content);
			}
		}
		
		return productList;
	}
	
	private Product parseElement(Element element)
	{
		Product product = new Product();
		String url = getValue(element, "url");
		if (StringUtils.isNotBlank(url))
		{
			url = getUrlManager().breakURL(url);
		}
		
		product.setUrl(url);

		product.setName(getValue(element, getSiteConfig().getDatafeedNameXPath()));

		String priceText = getValue(element, getSiteConfig().getDatafeedPriceXPath());
		String currencyText = getValue(element, getSiteConfig().getDatafeedCurrencyXPath());
		PriceUtil.setPrice(priceText, currencyText, product);

		product.setImageUrl(getValue(element, getSiteConfig().getDatafeedImageElementName()));

		String brandText = getValue(element, getSiteConfig().getDatafeedBrandXPath());
		product.setBreadcrumb("");
		product.setBrand(BrandUtil.getBrand(getSiteConfig().getSingleBrand(), brandText, product.getName(), product.getBreadcrumb(), null));

		List<ProductProperty> properties = new ArrayList<ProductProperty>();
		String propertyValue;
		for (CategoryPropertyConfig propertyConfig : getCategoryConfig().getPropertyMap().values())
		{
			propertyValue = getValue(element, getSiteConfig().getDatafeedPropertyXQuery(propertyConfig.getName()));
			
			if(propertyConfig.getName().equalsIgnoreCase("city"))
			{
				if (StringUtils.isNotBlank(propertyValue) && propertyValue.contains("-"))
				{
					String[] cityNames=propertyValue.split("-");
					String realValue = null;
					for(int index=cityNames.length-1; index>=0; index--)
					{
						String cityName = cityNames[index];
						realValue = RecognizerImpl.getInstance().recognizeCity(cityName);
						if(realValue != null)
						{
							propertyValue  = realValue;
							break;
						}
					}
				}
				else
				{
					propertyValue = RecognizerImpl.getInstance().recognizeCity(propertyValue);	
				}
			}
			
			if (StringUtils.isNotBlank(propertyValue))
			{
				properties.add(new ProductProperty(propertyConfig.getName(), propertyValue));
			}
		}
		product.setProperties(properties);
		
		String kategori = getValue(element, "kategori");
		int star = -1;
		String starStr = null;
		
		try{
			starStr = kategori.trim().substring(0,1);
			star = Integer.parseInt(starStr);
		}catch(Throwable e){}
		
		if(star != -1)
		{
			product.getProperties().add(new ProductProperty("star", starStr));
		}
		
		String recognizeCountry = RecognizerImpl.getInstance().recognizeCountry("tr");
		product.getProperties().add(new ProductProperty("country", recognizeCountry));
		
		StringBuffer desc = new StringBuffer();
		if(star == -1)
		{
			desc.append(kategori);
			desc.append(",");
		}
		
		Element ozelliklerElement = element.element("ozellikler");
		List<Element> elements = ozelliklerElement.elements("ozellik");
		for(Element child: elements)
		{
			desc.append(child.getTextTrim());
			desc.append(", ");
		}
		
		product.getProperties().add(new ProductProperty("description", desc.toString()));
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