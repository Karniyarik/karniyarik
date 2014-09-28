package com.karniyarik.parser.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.trans.XPathException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;

import com.karniyarik.categorizer.tagger.ProductTagger;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.ConfigAttributeType;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.CategoryPropertyConfig;
import com.karniyarik.common.util.URLUtil;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.parser.logger.ParserBrandsLogger;
import com.karniyarik.parser.pojo.Product;
import com.karniyarik.parser.util.BrandUtil;
import com.karniyarik.parser.util.PriceUtil;
import com.karniyarik.recognizer.RecognizerImpl;

public class XQueryParser
{

	private SiteConfig			siteConfig;
	private ParserBrandsLogger	brandsLogger	= null;
	private Configuration		xqueryConfiguration;
	private final URLUtil		urlUtil;
	private static final String	BASE_QUERY		= "data(//base/@href)";

	public XQueryParser(SiteConfig siteConfig, ParserBrandsLogger brandsLogger, URLUtil urlUtil)
	{
		this.siteConfig = siteConfig;
		this.xqueryConfiguration = new Configuration();
		this.brandsLogger = brandsLogger;
		this.urlUtil = urlUtil;
	}

	private DynamicQueryContext initializeDynamicQueryContex(String htmlContent) throws IOException, XPathException, ParserConfigurationException
	{
		HtmlCleaner cleaner = new HtmlCleaner();
		DynamicQueryContext dynamicContext = new DynamicQueryContext(xqueryConfiguration);
		dynamicContext.setContextItem(xqueryConfiguration.buildDocument(new DOMSource(new DomSerializer(cleaner.getProperties(), true).createDOM(cleaner.clean(htmlContent)))));

		cleaner = null;
		return dynamicContext;
	}

	public Product parse(String fullUrl, String shortUrl, String aContent, Date fetchDate)
	{
		Product product = new Product();

		try
		{
			DynamicQueryContext dynamicContext = initializeDynamicQueryContex(aContent);

			String productName = evaluateSingleXQuery(fullUrl, siteConfig.getNameXQuery(), dynamicContext);
			String priceText = evaluateSingleXQuery(fullUrl, siteConfig.getPriceXQuery(), dynamicContext);

			PriceUtil.setPrice(priceText, product);

			if (StringUtils.isNotBlank(productName) && product.getPrice() > 0f)
			{
				//product = new Product();
				product.setUrl(shortUrl);
				product.setFetchDate(fetchDate);
				product.setName(productName);
				
				try
				{
					// parse breadcrumb
					product.setBreadcrumb(evaluateSingleXQuery(fullUrl, siteConfig.getBreadcrumbXQuery(), dynamicContext));

					// parse brand
					String brandText = evaluateSingleXQuery(fullUrl, siteConfig.getBrandXQuery(), dynamicContext);
					product.setBrand(BrandUtil.getBrand(siteConfig.getSingleBrand(), brandText, product.getName(), product.getBreadcrumb(), brandsLogger));
					
					//parse model
					String modelText = evaluateSingleXQuery(fullUrl, siteConfig.getModelXQuery(), dynamicContext);
					product.setModel(modelText);

					// parse image url
					product.setImageUrl(parseImageUrl(fullUrl, dynamicContext));

					// parse properties
					product.setProperties(parseProperties(fullUrl, dynamicContext));

					CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
					CategoryConfig categoryConfig = categorizerConfig.getCategoryConfig(getSiteConfig().getCategory());
					
					if(categoryConfig.getPropertyMap().containsKey(SearchConstants.TAGS))
					{
						String tags = ProductTagger.getInstance().getTags(product.getName(), product.getBrand(), product.getBreadcrumb(), siteConfig.getCategory());
						product.getProperties().add(new ProductProperty(SearchConstants.TAGS, tags));						
					}
				}
				catch (Throwable e)
				{
					getLogger().error("Parser failed to parse " + fullUrl + " for site " + siteConfig.getSiteName(), e);
				}
			} else {
				product = null;
			}

			dynamicContext = null;
		}
		catch (IOException e)
		{
			getLogger().warn("HtmlCleaner failed to clean html content of " + fullUrl + " for site " + siteConfig.getSiteName(), e);
		}
		catch (ParserConfigurationException e)
		{
			getLogger().warn("Could not create DOM object from clean content of " + fullUrl + " for site " + siteConfig.getSiteName(), e);
		}
		catch (XPathException e)
		{
			getLogger().warn("Could not build saxon xml document from DOM object of " + fullUrl + " for site " + siteConfig.getSiteName(), e);
		}
		catch (Throwable e)
		{
			getLogger().error("Parser failed to parse " + fullUrl + " for site " + siteConfig.getSiteName(), e);
		}

		return product;
	}

	public Product testParse(String url, String aContent)
	{
		Product product = null;
		String priceText = null;
		if (aContent != null)
		{
			try
			{
				product = new Product();
				DynamicQueryContext dynamicContext = initializeDynamicQueryContex(aContent);

				product.setName(evaluateSingleXQuery(url, siteConfig.getNameXQuery(), dynamicContext));
				priceText = evaluateSingleXQuery(url, siteConfig.getPriceXQuery(), dynamicContext);
				PriceUtil.setPrice(priceText, product);
				product.setBreadcrumb(evaluateSingleXQuery(url, siteConfig.getBreadcrumbXQuery(), dynamicContext));
				product.setBrand(evaluateSingleXQuery(url, siteConfig.getBrandXQuery(), dynamicContext));
				product.setImageUrl(parseImageUrl(url, dynamicContext));
				product.setProperties(parseProperties(url, dynamicContext));

				dynamicContext = null;
			}
			catch (Exception e)
			{
				getLogger().error("Parser test method failed", e);
			}
		}

		return product;
	}

	private String evaluateSingleXQuery(String url, String xQuery, DynamicQueryContext dynamicContext)
	{
		String result = null;

		if (StringUtils.isNotBlank(xQuery))
		{
			try
			{
				StaticQueryContext staticContext = new StaticQueryContext(xqueryConfiguration);
				XQueryExpression exp = staticContext.compileQuery(xQuery);
				Object queryResultOject = exp.evaluateSingle(dynamicContext);

				String queryResult = null;
				if (queryResultOject != null)
				{
					queryResult = queryResultOject.toString();
					if (StringUtils.isNotBlank(queryResult))
					{
						// normalize special white space characters
						queryResult = queryResult.replace((char) 160, ' ');
						queryResult = StringUtils.trim(queryResult);
					}
				}

				if (StringUtils.isNotBlank(queryResult))
				{
					result = StringEscapeUtils.unescapeHtml(queryResult);
					result = StringEscapeUtils.unescapeXml(queryResult);
				}

				// memory cleaning
				staticContext = null;
				exp = null;
				queryResult = null;
			}
			catch (XPathException e)
			{
				getLogger().error(url + " Parser could not execute xquery \"" + xQuery + "\"", e);
			}
		}

		return result;
	}

	private String parseImageUrl(String url, DynamicQueryContext dynamicContext)
	{
		String imageUrl = evaluateSingleXQuery(url, siteConfig.getImageXQuery(), dynamicContext);

		if (StringUtils.isNotBlank(imageUrl))
		{
			String baseUrl = evaluateSingleXQuery(url, BASE_QUERY, dynamicContext);
			if (StringUtils.isBlank(baseUrl))
			{
				baseUrl = url;
			}

			imageUrl = urlUtil.createUrl(baseUrl, imageUrl);
		}

		return imageUrl;
	}

	private List<ProductProperty> parseProperties(String url, DynamicQueryContext dynamicContext)
	{
		List<ProductProperty> properties = new ArrayList<ProductProperty>();

		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		CategoryConfig categoryConfig = categorizerConfig.getCategoryConfig(getSiteConfig().getCategory());

		ProductProperty property = null;
		for (CategoryPropertyConfig propertyConfig : categoryConfig.getPropertyMap().values())
		{
			property = parseProperty(url, propertyConfig, categoryConfig, dynamicContext);
			if (property != null)
			{
				properties.add(property);
			}
		}

		return properties;
	}

	private ProductProperty parseProperty(String url, CategoryPropertyConfig propertyConfig, CategoryConfig categoryConfig, DynamicQueryContext dynamicContext)
	{

		ProductProperty property = null;

		String value = evaluateSingleXQuery(url, siteConfig.getPropertyXQuery(propertyConfig.getName()), dynamicContext);

		if (StringUtils.isNotBlank(value) && verifyProperty(propertyConfig, value))
		{
			// TODO remove this if else statements
			if (propertyConfig.getName().equals("color"))
			{
				value = RecognizerImpl.getInstance().recognizeColor(value);
			}
			else if (propertyConfig.getName().equals("gear"))
			{
				value = RecognizerImpl.getInstance().recognizeGear(value);
			}
			else if (propertyConfig.getName().equals("fuel"))
			{
				value = RecognizerImpl.getInstance().recognizeFuel(value);
			}
			else if (propertyConfig.getName().equals("city"))
			{
				value = RecognizerImpl.getInstance().recognizeCity(value, true, true);
			}

			// sss: tehlikeli
			value = value.replaceAll(",", " ");
			property = new ProductProperty(propertyConfig.getName(), value);
		}

		return property;
	}

	private boolean verifyProperty(CategoryPropertyConfig propertyConfig, String value)
	{
		boolean result = Boolean.TRUE;

		if (propertyConfig.getType() == ConfigAttributeType.Double)
		{
			try
			{
				Double.parseDouble(value);
			}
			catch (Exception e)
			{
				result = Boolean.FALSE;
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Integer)
		{
			try
			{
				Integer.parseInt(value);
			}
			catch (Exception e)
			{
				result = Boolean.FALSE;
			}
		}
		else if (propertyConfig.getType() == ConfigAttributeType.Date)
		{
			throw new NotImplementedException("Date filter typew is not implemented!!!");
		}

		return result;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

	public SiteConfig getSiteConfig()
	{
		return siteConfig;
	}

	public void setSiteConfig(SiteConfig siteConfig)
	{
		this.siteConfig = siteConfig;
	}

}
