package com.karniyarik.web.json;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StringUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.web.util.Formatter;

public class ProductResult
{
	private String					productName				= null;
	private String					productHighlightedName	= null;

	private String					brand					= null;
	private String					breadcrumb				= null;
	private String					price					= null;
	private String					priceAlternate			= null;
	private String					priceCurrency			= null;
	private String					sourceURL				= null;
	private String					link					= null;
	private String					sourceName				= null;
	private String					lastFetchDate			= null;
	private String					imageURL				= null;
	private String					imageName				= null;
	private String					visualIndex				= null;
	private String					brandLink				= null;
	private String					sameNamedQueryLink		= null;
	private String					storeLink				= null;
	private String					score					= null;
	private String					shareLink				= null;
	private String					shareName				= null;
	private String					shareDesc				= null;
	private boolean 				featured				= false;
	private boolean 				singleShared			= false;
	private boolean					sponsored				= false;
	// private String hit = null;

	private List<ProductProperty>	properties				= new ArrayList<ProductProperty>();

	public ProductResult()
	{
	}

	public ProductResult(Product product, String contextPath, String utmContent)
	{
		this();
		init(product, contextPath, utmContent);
	}

	private void init(Product product, String contextPath, String utmContent)
	{
		try
		{
			productName = product.getName();
			productHighlightedName = product.getHighlightedName();
			brand = StringEscapeUtils.escapeHtml(product.getBrand());
			breadcrumb = product.getBreadcrumb();
			price = Formatter.formatMoney(product.getPrice());			
			if(product.getPriceAlternate() != null &&  product.getPriceAlternate() > 0)
			{
				priceAlternate = Formatter.formatMoney(product.getPriceAlternate());	
			}
			
			priceCurrency = product.getPriceCurrency();
//			sourceURL = StringEscapeUtils.escapeHtml(product.getSourceURL()) + "/?utm_medium=cse&utm_source=karniyarik&utm_campaign=standard&utm_content=sitelink";
//			link = StringEscapeUtils.escapeHtml(product.getLink())+"/?utm_medium=cse&utm_source=karniyarik&utm_campaign=standard&utm_content="+utmContent;
			sourceURL = StringEscapeUtils.escapeHtml(product.getSourceURL());
			link = StringEscapeUtils.escapeHtml(product.getLink());			
			sourceName = product.getSourceName();
			lastFetchDate = Formatter.formatDate(product.getLastFetchDate());
			imageURL = product.getImageURL();
			imageURL = URLDecoder.decode(imageURL, StringUtil.DEFAULT_ENCODING);
			imageURL = URIUtil.encodeAll(imageURL, StringUtil.DEFAULT_ENCODING);
			imageURL = StringEscapeUtils.escapeHtml(imageURL);
			imageName = getImageNameFromProductName(productName);
			score = Formatter.formatScore(product.getScore());
			// hit = Integer.toString(product.getHit());
			properties = product.getProperties();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	public String getScore()
	{
		return score;
	}

	public void setScore(String score)
	{
		this.score = score;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String aName)
	{
		productName = aName;
	}

	public String getProductHighlightedName()
	{
		return productHighlightedName;
	}

	public void setProductHighlightedName(String productHighlightedName)
	{
		this.productHighlightedName = productHighlightedName;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String aBrand)
	{
		brand = aBrand;
	}

	public String getBreadcrumb()
	{
		return breadcrumb;
	}

	public void setBreadcrumb(String aBreadcrumb)
	{
		breadcrumb = aBreadcrumb;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String aPrice)
	{
		price = aPrice;
	}

	public String getSourceURL()
	{
		return sourceURL;
	}

	public void setSourceURL(String aSourceURL)
	{
		sourceURL = aSourceURL;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String aLink)
	{
		link = aLink;
	}

	public String getSourceName()
	{
		return sourceName;
	}

	public void setSourceName(String aSourceName)
	{
		sourceName = aSourceName;
	}

	public String getLastFetchDate()
	{
		return lastFetchDate;
	}

	public void setLastFetchDate(String aLastFetchDate)
	{
		lastFetchDate = aLastFetchDate;
	}

	public String getImageURL()
	{
		return imageURL;
	}

	public String getImageName()
	{
		return imageName;
	}

	public void setImageURL(String aImageURL)
	{
		imageURL = aImageURL;
	}

	public String getVisualIndex()
	{
		return visualIndex;
	}

	public void setVisualIndex(String aVisualIndex)
	{
		visualIndex = aVisualIndex;
	}

	public String getBrandLink()
	{
		return brandLink;
	}

	public void setBrandLink(String aBrandLink)
	{
		brandLink = StringEscapeUtils.escapeXml(aBrandLink);
	}

	public String getShareLink()
	{
		return shareLink;
	}

	public void setShareLink(String aShareLink)
	{
		shareLink = aShareLink;
	}

	// public String getHit()
	// {
	// return hit;
	// }
	//
	// public void setHit(String hit)
	// {
	// this.hit = hit;
	// }

	public String getPriceCurrency()
	{
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency)
	{
		this.priceCurrency = priceCurrency;
	}

	public String getStoreLink()
	{
		return storeLink;
	}

	public void setStoreLink(String storeLink)
	{
		this.storeLink = StringEscapeUtils.escapeXml(storeLink);
	}

	public String getPropertyString()
	{
		StringBuffer buffer = new StringBuffer("");
		int index = 0;
		for (ProductProperty property : getProperties())
		{
			buffer.append(property.getName());
			buffer.append(":");
			buffer.append(property.getValue());
			if (index < (getProperties().size() - 1))
			{
				buffer.append(",");
			}
			index++;
		}

		return trim(buffer.toString(), 1024);
	}

	public List<ProductProperty> getProperties()
	{
		return properties;
	}

	public void setProperties(List<ProductProperty> properties)
	{
		this.properties = properties;
	}

	public String trim(String string, int length)
	{
		if (string != null && string.length() > length)
		{
			string = string.substring(0, length - 1);
		}

		return string;
	}

	public String getProperty(String propertyName)
	{
		String result = StringUtils.EMPTY;

		for (ProductProperty pp : getProperties())
		{

			if (pp.getName().equals(propertyName))
			{
				result = pp.getValue();
				break;
			}
		}

		return result;
	}
	
	public boolean isFeatured() {
		return featured;
	}
	
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	
	public String getPriceAlternate() {
		return priceAlternate;
	}

	public static String getImageNameFromProductName(String productName)
	{
		try {
			String imageName = StringUtil.removePunctiationsAndReduceWhitespace(productName);
			imageName = imageName.replaceAll("\\s", "-");
			imageName = URLEncoder.encode(imageName, StringUtil.DEFAULT_ENCODING);
			
			return imageName;
		} catch (Throwable e) {
		}
		
		return "";
	}

	public static String getEncodedImageURL(String url)
	{
		try {
			String imageURL = URLDecoder.decode(url, StringUtil.DEFAULT_ENCODING);
			imageURL = URIUtil.encodeAll(imageURL, StringUtil.DEFAULT_ENCODING);
			imageURL = StringEscapeUtils.escapeHtml(imageURL);
			
			return imageURL;
		} catch (Throwable e) {
		}
		
		return "";
	}

	public String getShareName()
	{
		return shareName;
	}
	
	public String getShareDesc()
	{
		return shareDesc;
	}
	
	public void setShareDesc(String shareDesc)
	{
		this.shareDesc = shareDesc;
	}
	
	public void setShareName(String shareName)
	{
		this.shareName = shareName;
	}
	
	public void setSingleShared(boolean singleShared) {
		this.singleShared = singleShared;
	}
	
	public boolean isSingleShared() {
		return singleShared;
	}
	
	
	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}
	
	public boolean isSponsored() {
		return sponsored;
	}
	
	public String getSameNamedQueryLink() {
		return sameNamedQueryLink;
	}
	
	
	public void setSameNamedQueryLink(String sameNamedQueryLink) {
		this.sameNamedQueryLink = sameNamedQueryLink;
	}
	
	public static void main(String[] args) {
		Product p =new Product();
		p.setBrand("");
		p.setBreadcrumb("");
		p.setCategory("");
		p.setHighlightedName("");
		p.setImageURL("");
		p.setName("Concord 7700 ( Çift Sim Kart + Tv) \\ 14;5 6/4 5'6 6\"1 Gb.    Hafıza-Kartı_Hediye   ");
		
		System.out.println(new ProductResult(p, null, null).getImageName());
	}
}
