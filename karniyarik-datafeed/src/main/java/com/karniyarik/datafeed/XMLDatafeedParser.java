package com.karniyarik.datafeed;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.InvalidXPathException;
import org.dom4j.io.DOMReader;
import org.dom4j.tree.DefaultElement;
import org.w3c.dom.NodeList;

import com.karniyarik.categorizer.tagger.ProductTagger;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.crawler.util.URLManager;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.parser.pojo.Product;
import com.karniyarik.parser.util.BrandUtil;
import com.karniyarik.parser.util.PriceUtil;
import com.karniyarik.recognizer.RecognizerImpl;

public class XMLDatafeedParser implements DatafeedParser
{

	private final SiteConfig		siteConfig;
	private final URLManager		urlManager;
	private final CategoryConfig	categoryConfig;
	private DecimalFormat decimalFormat = null;
//	private org.dom4j.XPath xpath 	= null;
	
	public XMLDatafeedParser(SiteConfig siteConfig, CategoryConfig categoryConfig, URLManager urlManager)
	{
		this.siteConfig = siteConfig;
		this.categoryConfig = categoryConfig;
		this.urlManager = urlManager;
		
		if(StringUtils.isNotBlank(siteConfig.getDatafeedPricePattern()))
		{
			DecimalFormatSymbols aSymbols = new DecimalFormatSymbols();
			aSymbols.setDecimalSeparator(siteConfig.getDatafeedPriceDecimalSeparator().charAt(0));
			aSymbols.setGroupingSeparator(siteConfig.getDatafeedPriceThousandSeparator().charAt(0));
			decimalFormat = new DecimalFormat(siteConfig.getDatafeedPricePattern(), aSymbols);
		}
	}

	@Override
	public List<Product> parse(String content)
	{
		List<Product> productList = new ArrayList<Product>();
		//SAXReader reader = new SAXReader();
		
		if (StringUtils.isNotBlank(content))
		{
			try
			{
				DocumentBuilderFactory dbf = DocumentBuilderFactoryImpl.newInstance();
				dbf.setNamespaceAware(siteConfig.isDatafeedNamespaceAware());
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				content = stripNonValidXMLCharacters(content);
				content = content.replaceFirst("encoding=\"latin-5\"", "encoding=\"ISO-8859-9\"");
				String encoding = siteConfig.getSiteEncoding();
				StringInputStream stringInputStream = new StringInputStream(content, encoding);
				org.w3c.dom.Document document = db.parse(stringInputStream);
				Document dom4JDocument = new DOMReader().read(document);
				stringInputStream.close();
				Date fetchDate = new Date();
				
				List<Element> elements = getProductElementList(dom4JDocument, siteConfig);
				
				for (Element element : elements)
				{
					Product product = parseElement(element);
					if (product != null)
					{
						product.setFetchDate(fetchDate);
						productList.add(product);
					}
				}
			}
			catch (Throwable e)
			{
				getLogger().error("Can not parse XML datafeed for " + siteConfig.getSiteName() + " content: " );
				throw new RuntimeException(e);				
			}
		}

		return productList;
	}

	private Product parseElement(Element element)
	{
		Product product = new Product();
		String url = getValue(element, siteConfig.getDatafeedProductUrlXPath());
		if (StringUtils.isNotBlank(url) && siteConfig.isSpliturl())
		{
			url = urlManager.breakURL(url);
		}
		product.setUrl(url);
		
		product.setName(clear(getValue(element, siteConfig.getDatafeedNameXPath())));

		String priceText = clear(getValue(element, siteConfig.getDatafeedPriceXPath()));
		String currencyText = getValue(element, siteConfig.getDatafeedCurrencyXPath());
		PriceUtil.setPrice(priceText, currencyText, product, decimalFormat,siteConfig.getKdvValue());
		
		product.setBreadcrumb(clear(getValue(element, siteConfig.getDatafeedBreadcrumbXPath())));
		
		
		String imageUrl = getValue(element, siteConfig.getDatafeedImageElementName());
		if(siteConfig.getSiteName().equals("parfumdunyasi") && imageUrl != null && StringUtils.isNotBlank(imageUrl))
			imageUrl = imageUrl.substring(imageUrl.indexOf("c=") + 2, imageUrl.indexOf(">"));
		product.setImageUrl(imageUrl);

		String brandText = getValue(element, siteConfig.getDatafeedBrandXPath());
		brandText = StringEscapeUtils.unescapeHtml(brandText);
		String brand = BrandUtil.getBrand(siteConfig.getSingleBrand(), brandText, product.getName(), product.getBreadcrumb(), null);
		product.setBrand(brand);
		
		product.setModel(clear(getValue(element, siteConfig.getDatafeedModelXPath())));
		
//		if(!BrandServiceImpl.getInstance().isBrandRecognized(product.getBrand()))
//		{
//			BrandLogger.getInstance().logMissedBrand(brandText);
//		}
		
		List<ProductProperty> properties = new ArrayList<ProductProperty>();
		String propertyValue;
		for (CategoryPropertyConfig propertyConfig : categoryConfig.getPropertyMap().values())
		{
			if(propertyConfig.getName().equals(SearchConstants.TAGS))
			{
				propertyValue = ProductTagger.getInstance().getTags(product.getName(), product.getBrand(), product.getBreadcrumb(), siteConfig.getCategory());
			}
			else
			{
				propertyValue = getValue(element, siteConfig.getDatafeedPropertyXQuery(propertyConfig.getName()));	
			}
			
			if (StringUtils.isNotBlank(propertyValue))
			{
				// TODO remove this if else statements
				if (propertyConfig.getName().equals("color"))
				{
					propertyValue = RecognizerImpl.getInstance().recognizeColor(propertyValue);
				}
				else if (propertyConfig.getName().equals("gear"))
				{
					propertyValue = RecognizerImpl.getInstance().recognizeGear(propertyValue);
				}
				else if (propertyConfig.getName().equals("fuel"))
				{
					propertyValue = RecognizerImpl.getInstance().recognizeFuel(propertyValue);
				}
				else if (propertyConfig.getName().equals("city"))
				{
					propertyValue = RecognizerImpl.getInstance().recognizeCity(propertyValue, true, true);
				}

				// sss: tehlikeli
				propertyValue = propertyValue.replaceAll(",", " ");
				properties.add(new ProductProperty(propertyConfig.getName(), propertyValue));
			}
		}
		
		product.setProperties(properties);

		return product;
	}
	
	public static List<Element> getProductElementList(Document document, SiteConfig siteConfig)
	{
		return getProductElementList(document, siteConfig.getDatafeedProductsXPath()); 
	}
	
	public static List<Element> getProductElementList(Document document, String productXpath)
	{
		List<Element> elements = null;
		
		if(StringUtils.isNotBlank(productXpath))
		{
			elements = new ArrayList<Element>();
			try {
				
				//XPath xpathEvaluator = XPathFactory.newInstance().newXPath();
				String datafeedProductsXPath = productXpath;
				org.dom4j.XPath xpath = document.createXPath(datafeedProductsXPath);
				Object evaluate = xpath.evaluate(document);
				
				if(evaluate instanceof ArrayList)
				{
					ArrayList arrayList = (ArrayList) evaluate;
					for (int i = 0; i < arrayList.size(); i++) 
					{
						Object object = arrayList.get(i);
						if(object != null)
						{
							elements.add((Element)object);	
						}
					}
				}
				else if(evaluate instanceof DefaultElement)
				{
					elements.add((DefaultElement)evaluate);
				}
				else if(evaluate instanceof NodeList) 
				{
					NodeList nodeList = (NodeList) evaluate;
					for (int i = 0; i < nodeList.getLength(); i++) 
					{
						Element element = (Element) nodeList.item(i);
						elements.add(element);
					}					
				}

			} catch (Throwable e) {
				//do nothing
				e.printStackTrace();
			}
		}
		else
		{
			elements = document.getRootElement().elements();	
		}
		
		return elements;
	}

	public static String getValue(Element element, String propertyXPath)
	{
		String value = "";
		if (StringUtils.isNotBlank(propertyXPath) && element != null)
		{
			try {
				org.dom4j.XPath xpath = element.createXPath(propertyXPath);
				value = xpath.valueOf(element).trim();
			} catch (InvalidXPathException e) {
				
			}
		}
		return value;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
	
	public static String clear(String str)
	{
		if(StringUtils.isNotBlank(str))
		{
			str = str.trim();
			str = StringEscapeUtils.unescapeHtml(str);
			str = StringUtil.removeHTMLTags(str);
		}
		return str;
	}

	public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in))) return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
            if ((current == 0x9) ||
                (current == 0xA) ||
                (current == 0xD) ||
                ((current >= 0x20) && (current <= 0xD7FF)) ||
                ((current >= 0xE000) && (current <= 0xFFFD)) ||
                ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }    
}
