package com.karniyarik.citydeal;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.karniyarik.common.vo.CurrencyType;
import com.karniyarik.crawler.HttpClientContentFetcher;
import com.karniyarik.crawler.IContentFetcher;
import com.karniyarik.datafeed.XMLDatafeedParser;
import com.karniyarik.parser.util.PriceUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class CityDealXMLDatafeed
{
	private DecimalFormat decimalFormat = null;
	private DateFormat	dateFormat = null;
	private String dateFormatStr = null;
	
	public List<CityDeal> parse(String url, String encoding, String source, String productsXpath, String titleXPath, 
			String descriptionXPath, String urlXPath, String priceXPath, String discountPriceXPath, String currencyXPath, 
			String cityXPath, String startDateXPath, String endDateXPath, String imageUrlXPath, String dateFormatStr,String sourceXPath,
			boolean paid, String dateLocale, String append)
	{
		this.dateFormatStr = dateFormatStr;
		
		if(StringUtils.isNotBlank(dateLocale))
		{
			dateFormat = new SimpleDateFormat(dateFormatStr, new Locale(dateLocale));	
		}
		else
		{
			dateFormat = new SimpleDateFormat(dateFormatStr);
		}
		
		
		List<CityDeal> cityDealList = new ArrayList<CityDeal>();
		SAXReader reader = new SAXReader();
		Document document;
		String content = "";
		
		try
		{
			IContentFetcher fetcher = new HttpClientContentFetcher("karniyarik-bot", 180000, encoding);
			content = fetcher.getContent(url);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		
		if (StringUtils.isNotBlank(content))
		{
			try
			{
				content = stripNonValidXMLCharacters(content);
				StringInputStream stringInputStream = new StringInputStream(content, encoding);
				document = reader.read(stringInputStream);
				stringInputStream.close();
				
				List<Element> elements = getProductElementList(document, productsXpath);
				
				for (Element element : elements)
				{
					List<CityDeal> parsedList = parseElement(element, source, titleXPath, descriptionXPath, urlXPath, priceXPath, discountPriceXPath, currencyXPath, cityXPath, startDateXPath, endDateXPath, imageUrlXPath,sourceXPath, append);
					
					for(CityDeal cityDeal: parsedList)
					{
						cityDeal.setPaid(paid);
						cityDealList.add(cityDeal);
					}
				}
			}
			catch (Throwable e)
			{
				throw new RuntimeException(e);
			}
		}

		return cityDealList;
	}
	
	private List<CityDeal> parseElement(Element element, String source, String titleXPath, String descriptionXPath, String urlXPath, String priceXPath, String discountPriceXPath, String currencyXPath, 
			String cityXPath, String startDateXPath, String endDateXPath, String imageUrlXPath, String sourceXPath, String append)
	{
		List<CityDeal> cityDealList = new ArrayList<CityDeal>();
		
		try
		{
			String cityStr = clear(getValue(element, cityXPath));
			
			List<String> cityList = new ArrayList<String>();
			
			if(cityStr.contains(","))
			{
				List<String> splittedCityList = Arrays.asList(cityStr.split(","));
				for(String city: splittedCityList)
				{
					cityList.add(city.trim());	
				}				
			}
			else
			{
				cityList.add(cityStr);
			}
			
			for(String city: cityList)
			{
				CityDeal cityDeal = new CityDeal();
				
				cityDeal.setCity(city);
				
				cityDeal.setProductURL(getValue(element, urlXPath));
				if(StringUtils.isNotBlank(append))
				{
					cityDeal.setProductURL(cityDeal.getProductURL() + append);
				}
				cityDeal.setName(clear(getValue(element, titleXPath)));
				cityDeal.setDescription(clear(getValue(element, descriptionXPath)));
				setPrice(clear(getValue(element, priceXPath)), decimalFormat, cityDeal);
				setDiscountPrice(clear(getValue(element, discountPriceXPath)), decimalFormat, cityDeal);
				if(StringUtils.isNotBlank(currencyXPath))
				{
					setCurrency(getValue(element, currencyXPath), cityDeal);	
				}
				else
				{
					cityDeal.setPriceCurrency("TL");
				}
				
				cityDeal.setImage(getValue(element, imageUrlXPath));
				
				
				
				String start = reformatDate(clear(getValue(element, startDateXPath)), dateFormatStr);
				String end = reformatDate(clear(getValue(element, endDateXPath)), dateFormatStr);
				
				cityDeal.setEndDate(dateFormat.parse(end));
				cityDeal.setStartDate(dateFormat.parse(start));
				
				if(source == null)
				{
					String sourceStr = clear(getValue(element, sourceXPath));
					if(sourceStr.startsWith("www"))
					{
						sourceStr = sourceStr.split("\\.")[1];
						source = sourceStr;
					}
				}
				cityDeal.setSource(source);
				
				cityDealList.add(cityDeal);
			}
			
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return cityDealList;
	}
	
	public String reformatDate(String date, String format)
	{
		 
		if(date.contains("+") && format.contains("z") && !date.contains("GMT"))
		{
			StringBuffer dateBuff = new StringBuffer(date);
			int index = date.indexOf("+");
			dateBuff.insert(index, "GMT");
			return dateBuff.toString();
		}
		
		return date;
	}
	public void  setPrice(String rawPriceString, DecimalFormat formatter, CityDeal deal)
	{
		float price = PriceUtil.parsePrice(rawPriceString, formatter);
		deal.setPrice(new Double(price));
	}

	public void  setDiscountPrice(String rawPriceString, DecimalFormat formatter, CityDeal deal)
	{
		float price = PriceUtil.parsePrice(rawPriceString, formatter);
		deal.setDiscountPrice(new Double(price));
	}

	private void setCurrency(String currencyText, CityDeal deal)
	{
		CurrencyType currency = PriceUtil.determineCurrency(currencyText);
		deal.setPriceCurrency(currency.getSymbol());
	}

	
	public static List<Element> getProductElementList(Document document, String productXPath)
	{
		return XMLDatafeedParser.getProductElementList(document, productXPath);
	}
	
	public static String getValue(Element element, String propertyXPath)
	{
		return XMLDatafeedParser.getValue(element, propertyXPath);
	}

	public String stripNonValidXMLCharacters(String in) {
		return XMLDatafeedParser.stripNonValidXMLCharacters(in);
    }    
	
	public static String clear(String str)
	{
		return XMLDatafeedParser.clear(str);
	}


}
