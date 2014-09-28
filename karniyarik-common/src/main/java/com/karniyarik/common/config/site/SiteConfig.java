package com.karniyarik.common.config.site;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

@XmlAccessorType(XmlAccessType.FIELD)
public class SiteConfig implements Serializable
{
	private static final long serialVersionUID = -2017227080048504428L;

	@XmlTransient
	public transient static String	XML_DATAFEED				= "XML";
	
	@XmlTransient
	public transient static String	TAB_SEPARATED_DATAFEED		= "TAB";
	
	@XmlTransient
	public transient static String	WS_DATAFEED					= "WS";

	@XmlElement(name = "siteName")
	private String					siteName					= "";

	@XmlElement(name = "image")
	private String					image						= "";
	// setDelimiterParsingDisabled();
	@XmlElement(name = "siteRank")
	private int						siteRank					= 50;

	@XmlElement(name = "url")
	private String					url							= "";

	@XmlElement(name = "siteEncoding")
	private String					siteEncoding				= "";

	@XmlElement(name = "category")
	private String					category					= "";

	@XmlElement(name = "ignoreList")
	private TreeSet<String>			ignoreList					= new TreeSet<String>();

	@XmlElement(name = "ruleClassName")
	private String					ruleClassName				= "";

	@XmlElement(name = "crawlDelay")
	private int						crawlDelay					= 2000;

	@XmlElement(name = "singleBrand")
	private String					singleBrand					= "";

	@XmlElement(name = "nameXQuery")
	private String					nameXQuery					= "";

	@XmlElement(name = "brandXQuery")
	private String					brandXQuery					= "";

	@XmlElement(name = "modelXQuery")
	private String					modelXQuery					= "";

	@XmlElement(name = "breadcrumbXQuery")
	private String					breadcrumbXQuery			= "";

	@XmlElement(name = "priceXQuery")
	private String					priceXQuery					= "";

	@XmlElement(name = "imageXQuery")
	private String					imageXQuery					= "";

	@XmlElement(name = "propertyXQueryMap")
	private HashMap<String, String>	propertyXQueryMap			= new HashMap<String, String>();

	@XmlElement(name = "pagingRule")
	private String					pagingRule					= "";

	@XmlElement(name = "datafeedType")
	private String					datafeedType				= "";

	@XmlElement(name = "datafeedConfig")
	private String					datafeedConfig				= "";

	@XmlElement(name = "datafeedUrlList")
	private ArrayList<String>		datafeedUrlList				= new ArrayList<String>();

	@XmlElement(name = "datafeedProductsXPath")
	private String					datafeedProductsXPath		= "";

	@XmlElement(name = "datafeedPricePattern")
	private String					datafeedPricePattern		= "";

	@XmlElement(name = "datafeedPriceDecimalSeparator")
	private String					datafeedPriceDecimalSeparator 	= "";

	@XmlElement(name = "datafeedPriceThousandSeparator")
	private String					datafeedPriceThousandSeparator 	= "";

	@XmlElement(name = "datafeedProductUrlXPath")
	private String					datafeedProductUrlXPath		= "";

	@XmlElement(name = "datafeedNameXPath")
	private String					datafeedNameXPath			= "";

	@XmlElement(name = "datafeedBrandXPath")
	private String					datafeedBrandXPath			= "";

	@XmlElement(name = "datafeedModelXPath")
	private String					datafeedModelXPath			= "";
	
	@XmlElement(name = "datafeedBreadcrumbXPath")
	private String					datafeedBreadcrumbXPath		= "";

	@XmlElement(name = "datafeedPriceXPath")
	private String					datafeedPriceXPath			= "";

	@XmlElement(name = "datafeedImageElementName")
	private String					datafeedImageElementName	= "";

	@XmlElement(name = "datafeedCurrencyXPath")
	private String					datafeedCurrencyXPath		= "";

	@XmlElement(name = "datafeedPropertyXPathMap")
	private HashMap<String, String>	datafeedPropertyXPathMap	= new HashMap<String, String>();

	@XmlElement(name = "affiliateClass")
	private String					affiliateClass				= "";

	@XmlElement(name = "rankHelper")
	private String					rankHelper					= "";

	@XmlElement(name = "domainName")
	private String					domainName					= "";

	@XmlElement(name = "cron")
	private String					cron						= "";

	@XmlElement(name = "ecommerceConfig")
	private String					ecommerceConfig				= "";

	@XmlElement(name = "spliturl")
	private boolean 				spliturl 					= true;
	
	@XmlElement(name = "executingip")
	private String	 				executingip					= "";
	
	@XmlElement(name = "kdvValue")
	private int	 				kdvValue					= 0;
	
	@XmlElement(name = "datafeedNamespaceAware")
	private boolean	 			datafeedNamespaceAware					= false;
	
	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public String getEcommerceConfig()
	{
		return ecommerceConfig;
	}

	public void setEcommerceConfig(String ecommerceConfig)
	{
		this.ecommerceConfig = ecommerceConfig;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public int getSiteRank()
	{
		return siteRank;
	}

	public String getDatafeedProductsXPath()
	{
		return datafeedProductsXPath;
	}

	public void setDatafeedProductsXPath(String datafeedProductsXPath)
	{
		this.datafeedProductsXPath = datafeedProductsXPath;
	}

	public void setSiteRank(int siteRank)
	{
		this.siteRank = siteRank;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getSiteEncoding()
	{
		return siteEncoding;
	}

	public void setSiteEncoding(String siteEncoding)
	{
		this.siteEncoding = siteEncoding;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getRuleClassName()
	{
		return ruleClassName;
	}

	public void setRuleClassName(String ruleClassName)
	{
		this.ruleClassName = ruleClassName;
	}

	public int getCrawlDelay()
	{
		return crawlDelay;
	}

	public void setCrawlDelay(int crawlDelay)
	{
		this.crawlDelay = crawlDelay;
	}

	public String getSingleBrand()
	{
		return singleBrand;
	}

	public void setSingleBrand(String singleBrand)
	{
		this.singleBrand = singleBrand;
	}

	public String getNameXQuery()
	{
		return nameXQuery;
	}

	public void setNameXQuery(String nameXQuery)
	{
		this.nameXQuery = nameXQuery;
	}

	public String getBrandXQuery()
	{
		return brandXQuery;
	}

	public String getModelXQuery()
	{
		return modelXQuery;
	}
	
	public void setModelXQuery(String modelXQuery)
	{
		this.modelXQuery = modelXQuery;
	}

	public void setBrandXQuery(String brandXQuery)
	{
		this.brandXQuery = brandXQuery;
	}

	public String getBreadcrumbXQuery()
	{
		return breadcrumbXQuery;
	}

	public void setBreadcrumbXQuery(String breadcrumbXQuery)
	{
		this.breadcrumbXQuery = breadcrumbXQuery;
	}

	public String getPriceXQuery()
	{
		return priceXQuery;
	}

	public void setPriceXQuery(String priceXQuery)
	{
		this.priceXQuery = priceXQuery;
	}

	public String getImageXQuery()
	{
		return imageXQuery;
	}

	public void setImageXQuery(String imageXQuery)
	{
		this.imageXQuery = imageXQuery;
	}

	public String getPagingRule()
	{
		return pagingRule;
	}

	public void setPagingRule(String pagingRule)
	{
		this.pagingRule = pagingRule;
	}

	public String getDatafeedType()
	{
		return datafeedType;
	}

	public void setDatafeedType(String datafeedType)
	{
		this.datafeedType = datafeedType;
	}

	public String getDatafeedConfig()
	{
		return datafeedConfig;
	}

	public void setDatafeedConfig(String datafeedConfig)
	{
		this.datafeedConfig = datafeedConfig;
	}

	public String getDatafeedProductUrlXPath()
	{
		return datafeedProductUrlXPath;
	}

	public void setDatafeedProductUrlXPath(String datafeedProductUrlXPath)
	{
		this.datafeedProductUrlXPath = datafeedProductUrlXPath;
	}

	public String getDatafeedNameXPath()
	{
		return datafeedNameXPath;
	}

	public void setDatafeedNameXPath(String datafeedNameXPath)
	{
		this.datafeedNameXPath = datafeedNameXPath;
	}

	public String getDatafeedBrandXPath()
	{
		return datafeedBrandXPath;
	}

	public void setDatafeedBrandXPath(String datafeedBrandXPath)
	{
		this.datafeedBrandXPath = datafeedBrandXPath;
	}

	public String getDatafeedBreadcrumbXPath()
	{
		return datafeedBreadcrumbXPath;
	}

	public void setDatafeedBreadcrumbXPath(String datafeedBreadcrumbXPath)
	{
		this.datafeedBreadcrumbXPath = datafeedBreadcrumbXPath;
	}

	public String getDatafeedPriceXPath()
	{
		return datafeedPriceXPath;
	}

	public void setDatafeedPriceXPath(String datafeedPriceXPath)
	{
		this.datafeedPriceXPath = datafeedPriceXPath;
	}

	public String getDatafeedImageElementName()
	{
		return datafeedImageElementName;
	}

	public void setDatafeedImageElementName(String datafeedImageElementName)
	{
		this.datafeedImageElementName = datafeedImageElementName;
	}

	public String getDatafeedCurrencyXPath()
	{
		return datafeedCurrencyXPath;
	}

	public void setDatafeedCurrencyXPath(String datafeedCurrencyXPath)
	{
		this.datafeedCurrencyXPath = datafeedCurrencyXPath;
	}

	public String getAffiliateClass()
	{
		return affiliateClass;
	}

	public void setAffiliateClass(String affiliateClass)
	{
		this.affiliateClass = affiliateClass;
	}

	public String getRankHelper()
	{
		return rankHelper;
	}

	public void setRankHelper(String rankHelper)
	{
		this.rankHelper = rankHelper;
	}

	public String getDomainName()
	{
		return domainName;
	}

	public void setDomainName(String domainName)
	{
		this.domainName = domainName;
	}

	public String getCron()
	{
		return cron;
	}

	public void setCron(String cron)
	{
		this.cron = cron;
	}

	public boolean hasDatafeed()
	{
		return datafeedUrlList.size() > 0;
	}

	public boolean isTabSeperatedDatafeed()
	{
		return TAB_SEPARATED_DATAFEED.equals(getDatafeedType());
	}

	public boolean isWebServiceDataFeed()
	{
		return WS_DATAFEED.equals(getDatafeedType());
	}

	public String getPropertyXQuery(String name)
	{
		return propertyXQueryMap.get(name);
	}

	public String getDatafeedPropertyXQuery(String name)
	{
		return datafeedPropertyXPathMap.get(name);
	}

	public TreeSet<String> getIgnoreList()
	{
		return ignoreList;
	}

	public void setIgnoreList(TreeSet<String> ignoreList)
	{
		this.ignoreList = ignoreList;
	}

	public HashMap<String, String> getPropertyXQueryMap()
	{
		return propertyXQueryMap;
	}

	public void setPropertyXQueryMap(HashMap<String, String> propertyXQueryMap)
	{
		this.propertyXQueryMap = propertyXQueryMap;
	}

	public ArrayList<String> getDatafeedUrlList()
	{
		return datafeedUrlList;
	}

	public void setDatafeedUrlList(ArrayList<String> datafeedUrlList)
	{
		this.datafeedUrlList = datafeedUrlList;
	}

	public HashMap<String, String> getDatafeedPropertyXPathMap()
	{
		return datafeedPropertyXPathMap;
	}

	public void setDatafeedPropertyXPathMap(HashMap<String, String> datafeedPropertyXPathMap)
	{
		this.datafeedPropertyXPathMap = datafeedPropertyXPathMap;
	}

	public boolean isSpliturl()
	{
		return spliturl;
	}
	
	public void setSpliturl(boolean spliturl)
	{
		this.spliturl = spliturl;
	}
	
	public String getDatafeedPricePattern() {
		return datafeedPricePattern;
	}
	
	public void setDatafeedPricePattern(String datafeedPricePattern) {
		this.datafeedPricePattern = datafeedPricePattern;
	}
	
	public String getDatafeedPriceDecimalSeparator() {
		return datafeedPriceDecimalSeparator;
	}
	
	public String getDatafeedPriceThousandSeparator() {
		return datafeedPriceThousandSeparator;
	}
	public void setDatafeedPriceDecimalSeparator(
			String datafeedPriceDecimalSeparator) {
		this.datafeedPriceDecimalSeparator = datafeedPriceDecimalSeparator;
	}
	
	public void setDatafeedPriceThousandSeparator(
			String datafeedPriceThousandSeparator) {
		this.datafeedPriceThousandSeparator = datafeedPriceThousandSeparator;
	}
	
	public void setExecutingip(String executingip)
	{
		this.executingip = executingip;
	}
	
	public String getExecutingip()
	{
		return executingip;
	}
	
	public boolean isDatafeed()
	{
		return hasDatafeed();
	}
	
	public boolean isSelenium()
	{
		return StringUtils.isNotBlank(getPagingRule());
	}
	
	public boolean isCrawler()
	{
		return !isSelenium() && !isDatafeed();
	}
	
	public String getECommerceName()
	{
		if(StringUtils.isNotBlank(getDatafeedConfig()))
		{
			return getDatafeedConfig();
		}
		else if(StringUtils.isNotBlank(getEcommerceConfig()))
		{
			return getEcommerceConfig();
		}
		else return "";
	}

	public int getKdvValue() {
		return kdvValue;
	}

	public void setKdvValue(int kdvValue) {
		this.kdvValue = kdvValue;
	}
	
	public String getDatafeedModelXPath()
	{
		return datafeedModelXPath;
	}
	
	public void setDatafeedModelXPath(String datafeedModelXPath)
	{
		this.datafeedModelXPath = datafeedModelXPath;
	}

	public boolean isDatafeedNamespaceAware() {
		return datafeedNamespaceAware;
	}

	public void setDatafeedIsNamespaceAware(boolean datafeedNamespaceAware) {
		this.datafeedNamespaceAware = datafeedNamespaceAware;
	}
	
}
