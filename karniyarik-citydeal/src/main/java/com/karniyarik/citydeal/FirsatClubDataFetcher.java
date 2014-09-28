package com.karniyarik.citydeal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.xerces.dom.ElementImpl;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;

import com.karniyarik.common.util.StringUtil;
import com.karniyarik.crawler.IContentFetcher;
import com.karniyarik.crawler.SimpleContentFetcher;
import com.karniyarik.recognizer.ext.CitiesInTurkeyRegistry;
import com.karniyarik.recognizer.ext.CityRegistry;

public class FirsatClubDataFetcher {

	private String mainUrl;
	private List<String> cityNames;
	private List<String> ignoredSources;
	private IContentFetcher contentFetcher;
	private Configuration xqueryConfiguration;

	public FirsatClubDataFetcher(List<String> ignoredSources) {
		Collection<String> knownCities = CitiesInTurkeyRegistry.getInstance().getCities();
		this.ignoredSources = ignoredSources;
		cityNames = new ArrayList<String>();
		for(String cityName:knownCities)
		{
			String name = StringUtil.convertTurkishCharacter(cityName);
			name = StringUtil.toLowerCase(name);
			cityNames.add(name);
		}
//		cityNames.add("istanbul");
//		cityNames.add("ankara");
//		cityNames.add("izmir");
//		cityNames.add("bursa");
//		cityNames.add("antalya");
//		cityNames.add("eskisehir");
//		cityNames.add("adana");
//		cityNames.add("kars");
//		cityNames.add("kocaeli");
//		cityNames.add("aydin");
//		cityNames.add("balikesir");
//		cityNames.add("canakkale");
//		cityNames.add("denizli");
//		cityNames.add("kutahya");
//		cityNames.add("manisa");
//		cityNames.add("mugla");
//		cityNames.add("usak");
//		cityNames.add("yalova");
//		cityNames.add("konya");
//		cityNames.add("diyarbakir");
//		cityNames.add("van");
//		cityNames.add("trabzon");
		
		mainUrl = "http://www.firsatclub.com/sehir-firsatlari/";
		contentFetcher = new SimpleContentFetcher("karniyarik-bot", 60000, "UTF-8");
		xqueryConfiguration = new Configuration();
	}
	
	public List<CityDeal> fetchData() {
		List<CityDeal> result = new ArrayList<CityDeal>();

		for (String city : cityNames) {
			result.addAll(extractData(city));
		}

		return result;
	}

	private List<CityDeal> extractData(String cityStr) {

		List<CityDeal> result = new ArrayList<CityDeal>();
		CityDeal tmp = null;
		try {
			String content = contentFetcher.getContent(mainUrl + cityStr);
			
			DynamicQueryContext dynamicContext = initializeDynamicQueryContex(content);
			StaticQueryContext staticContext = new StaticQueryContext(xqueryConfiguration);
			String contentXQuery = "//div[@class=\"v2-content-box\"]";
			XQueryExpression exp = staticContext.compileQuery(contentXQuery);
			List<ElementImpl> dealList = exp.evaluate(dynamicContext);
			
			//XQueryExpression cityQuery = staticContext.compileQuery("//div[@class=\"v2-content-box-title\"]/h2/a");
			//List cityInPage = cityQuery.evaluate(dynamicContext);
			
			String city = cityStr;
			/*if(cityInPage != null && cityInPage.size()>0)
			{
				city = ((Node)cityInPage.get(0)).getTextContent().trim();
			}*/
			
			tmp = new CityDeal();
			int remTimeLng = 0;
			
			int index = 0;
			for (ElementImpl dealEl : dealList) {
				index++;
				String descriptionString = "data(//div[@class=\"v2-content-box-title\"]/h2/a)["+index+"]";
				String timeString= "data(//div[@class=\"v2-content-box\"]["+index+"]//div[@class=\"v2-single-time\"]/span/@data-time)";
				String priceQueryString = "data(//div[@class=\"v2-content-box\"]["+index+"]//span[@class=\"v2-real-price-mini-s\"]/s)";
				String imageString = "data(//div[@class=\"v2-content-box\"]["+index+"]//div[@class=\"v2-content-box-image\"]/a/img/@src)";
				String productURLString = "data(//div[@class=\"v2-content-box\"]["+index+"]//div[@class=\"v2-buy-now-mini-s\"]/a/@href)";
				String sourceURLString = "data(//div[@class=\"v2-content-box\"]["+index+"]//div[@class=\"offer-box\"]/a/@href)";
				String sourceString = "data(//div[@class=\"v2-content-box\"]["+index+"]//div[@class=\"offer-box\"]/a/strong)";
				String discountPriceString = "data(//div[@class=\"v2-content-box\"]["+index+"]//span[@class=\"v2-deal-price-mini-s\"])";
				XQueryExpression timeQuery  = staticContext.compileQuery(timeString);
				List timeInPage = timeQuery.evaluate(dynamicContext);
				if(timeInPage.size() > 0)
				{
					remTimeLng = Integer.parseInt(((String)timeInPage.get(0)).trim());
				}

				//not expired deals
				if (remTimeLng > 0) {
					
					tmp = new CityDeal();
					
					tmp.setCity(city);
					XQueryExpression descriptionQuery = staticContext.compileQuery(descriptionString);
					tmp.setDescription(((String)descriptionQuery.evaluateSingle(dynamicContext)).trim());
					tmp.setName(((String)descriptionQuery.evaluateSingle(dynamicContext)).trim());
					XQueryExpression imageQuery = staticContext.compileQuery(imageString);
					tmp.setImage((String)imageQuery.evaluateSingle(dynamicContext));
					XQueryExpression productURLQuery = staticContext.compileQuery(productURLString);
					tmp.setProductURL((String)productURLQuery.evaluateSingle(dynamicContext));
					XQueryExpression sourceQuery = staticContext.compileQuery(sourceString);
					tmp.setSource((String)sourceQuery.evaluateSingle(dynamicContext));
					
					XQueryExpression sourceURLQuery = staticContext.compileQuery(sourceURLString);
					tmp.setSourceURL((String)sourceURLQuery.evaluateSingle(dynamicContext));
					XQueryExpression priceQuery = staticContext.compileQuery(priceQueryString);
					List priceInPage = priceQuery.evaluate(dynamicContext);
					
					if(priceInPage.size() > 0)
					{
						String priceString = (String)priceInPage.get(0);
						priceString = priceString.trim();
						tmp.setPrice(Double.parseDouble(priceString));
					}
					XQueryExpression discountPriceQuery = staticContext.compileQuery(discountPriceString);
					String discPriceString = ((String)discountPriceQuery.evaluateSingle(dynamicContext)).trim();
					int lengthOfDiscPriceString = discPriceString.length();
					tmp.setDiscountPrice(Double.parseDouble(discPriceString.substring(0,lengthOfDiscPriceString-2)));
					tmp.setPriceCurrency(discPriceString.substring(lengthOfDiscPriceString-2));
					
					tmp.setStartDate(DateUtils.addSeconds(new Date(), -remTimeLng));
					tmp.setEndDate(DateUtils.addSeconds(new Date(), remTimeLng));
					
					if(StringUtils.isNotBlank(tmp.getSource()))
					{
						String source = StringUtil.convertTurkishCharacter(tmp.getSource());
						source = StringUtil.toLowerCase(source);
						source = source.replaceAll("\\s+", "");
						if(!ignoredSources.contains(tmp.getSource()))
						{
							result.add(tmp);
						}
					}
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private String extractCurrency(String priceStr) {
		return priceStr.split(" ")[1];
	}

	private Double extractPrice(String priceStr) {
		return new Double(priceStr.split(" ")[0]);
	}

	//affiliate mailini bulamadim buraya karniyarik affiliate bilgisini eklenecek
	private String constructAffiliateUrl(String orgUrl) {
		return orgUrl;
	}

	private DynamicQueryContext initializeDynamicQueryContex(String htmlContent) throws IOException, XPathException, ParserConfigurationException
	{
		HtmlCleaner cleaner = new HtmlCleaner();
		DynamicQueryContext dynamicContext = new DynamicQueryContext(xqueryConfiguration);
		dynamicContext.setContextItem(xqueryConfiguration.buildDocument(new DOMSource(new DomSerializer(cleaner.getProperties(), true).createDOM(cleaner.clean(htmlContent)))));
		
		
		cleaner = null;
		return dynamicContext;
	}
	
	public static void main(String[] args) {
		FirsatClubDataFetcher fc = new FirsatClubDataFetcher(new ArrayList<String>());
		//fc.fetchData();
		fc.extractData("eskisehir");
	}
}
