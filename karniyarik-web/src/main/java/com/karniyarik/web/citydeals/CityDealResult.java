package com.karniyarik.web.citydeals;

import java.util.Date;

import org.joda.time.Interval;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.karniyarik.citydeal.CityDeal;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.web.category.BaseCategoryUtil;
import com.karniyarik.web.json.LinkedLabel;
import com.karniyarik.web.json.ProductResult;
import com.karniyarik.web.util.Formatter;

public class CityDealResult {

	private String title;
	private String description;
	private String city;
	private String price = null;
	private String discountPrice = null;
	private Double discountAmount = null;
	private String priceCurrency = null;
	private String source = null;
	private String sourceURL = null;
	private String productURL = null;
	private String shareURL = null;
	private String image = null;
	private String imageName = null;
	private String sourceImage = null;
	private Date startDate = null;
	private Date endDate = null;
	private String remainingTimeStr;
	private long remainingTime;
	private int id;
	private String shareDesc = null;
	private String shareName = null;

	private String discountPercentage = null;
	private Double discountPercentageDbl = null;
	private Double discountPriceDbl = null;

	public CityDealResult(CityDeal deal, String cityValue) {
		setTitle(deal.getName());
		setDescription(deal.getDescription());
		setCity(deal.getCity());
		setPrice(Formatter.formatMoneyWithoutComma(deal.getPrice()));
		setDiscountPrice(Formatter.formatMoneyWithoutComma(deal.getDiscountPrice()));
		setPriceCurrency(deal.getPriceCurrency());
		setSource(deal.getSource());
		setSourceURL(deal.getSourceURL());
		setProductURL(deal.getProductURL());
		
		String image = ProductResult.getEncodedImageURL(deal.getImage());
		if(StringUtils.isBlank(image))
		{
			image = deal.getImage();
		}
		
		setImage(image);
		imageName = ProductResult.getImageNameFromProductName(LinkedLabel.getShortenedLabel(getDescription(), 20));
		setDiscountPercentage(Formatter.formatDoubleWithoutComma(deal.getDiscountPercentage()) + "%");
		String sourceImage = constructSourceImage(getSource());
		setSourceImage(sourceImage);
		setId(deal.getId());
		setStartDate(deal.getStartDate());
		setEndDate(deal.getEndDate());
		setDiscountAmount(deal.getPrice() - deal.getDiscountPrice());
		setShareURL("http://www.karniyarik.com/sehir-firsati/?id=" + getId());
		setDiscountPercentageDbl(deal.getDiscountPercentage());
		setDiscountPriceDbl(deal.getDiscountPrice());
		
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Şehir Fırsatı Tavsiyesi: ");
		shareBuff.append(getCity());
		shareBuff.append(", ");
		shareBuff.append(getDiscountPrice());
		shareBuff.append(" ");
		shareBuff.append(getPriceCurrency());
		shareBuff.append(" (");
		shareBuff.append(getDiscountPercentage());
		shareBuff.append(" Kazanç!) ");
		shareBuff.append(LinkedLabel.getShortenedLabel(getDescription(), 20));
		setShareName(BaseCategoryUtil.clearShareString(shareBuff));
		
		shareBuff = new StringBuffer();
		shareBuff.append("Arkadaşınız Karniyarik.com'dan bulduğu bu şehir fırsatının ilginizi çekeğini düşündü:");
		shareBuff.append(getCity());
		shareBuff.append(", ");
		shareBuff.append(getPrice());
		shareBuff.append(" ");
		shareBuff.append(getPriceCurrency());
		shareBuff.append(" (");
		shareBuff.append(getDiscountPercentage());
		shareBuff.append(" Kazanç!) ");
		shareBuff.append(LinkedLabel.getShortenedLabel(getDescription(), 20));
		setShareDesc(BaseCategoryUtil.clearShareString(shareBuff));
		
		try {
			Date date = new Date();
			if(deal.getEndDate() != null)
			{
				if(deal.getEndDate().getTime() > date.getTime())
				{
					Interval interval = new Interval(date.getTime(), deal.getEndDate().getTime());
					int months =  interval.toPeriod().getMonths();
					int days  = interval.toPeriod().getDays();
					int hours = interval.toPeriod().getHours();
					int minutes = interval.toPeriod().getMinutes();
					StringBuffer remainingTimeBuff = new StringBuffer();
					if(months > 0)
					{
						remainingTimeBuff.append(months);
						remainingTimeBuff.append(" Ay ");
					}
					
					if(days > 0)
					{
						remainingTimeBuff.append(days);
						remainingTimeBuff.append(" Gün ");
					}
					
					if(hours > 0 && months < 1)
					{
						remainingTimeBuff.append(hours);
						remainingTimeBuff.append(" Saat ");
					}

					if(minutes > 0 && months < 1)
					{
						remainingTimeBuff.append(minutes);
						remainingTimeBuff.append(" Dakika ");
					}

					setRemainingTimeStr(remainingTimeBuff.toString());
					long millis = 0;
					if(months < 1)
					{
						millis = interval.toPeriod().toStandardSeconds().getSeconds();
					}
					else
					{
						millis = deal.getEndDate().getTime() - date.getTime();
					}
					setRemainingTime(millis);					
				}
				else
				{
					setRemainingTimeStr("Zamanı Tükendi");
					setRemainingTime(0);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String constructSourceImage(String source)
	{
		String sourceTrimmed = source.replaceAll("\\s", "");
		sourceTrimmed = StringUtil.convertTurkishCharacter(sourceTrimmed);
		sourceTrimmed = sourceTrimmed.toLowerCase();
		String sourceImage = "/images/sehir-firsati/sites/" + sourceTrimmed;
		return sourceImage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getPriceCurrency() {
		return priceCurrency;
	}

	public void setPriceCurrency(String priceCurrency) {
		this.priceCurrency = priceCurrency;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		if(source != null)
		{
			source = source.trim();
		}
		this.source = source;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public String getProductURL() {
		return productURL;
	}

	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getSourceImage() {
		return sourceImage;
	}
	
	public void setSourceImage(String sourceImage) {
		this.sourceImage = sourceImage;
	}

	public String getRemainingTimeStr() {
		return remainingTimeStr;
	}

	public void setRemainingTimeStr(String remainingTimeStr) {
		this.remainingTimeStr = remainingTimeStr;
	}

	public long getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(long remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setShareURL(String shareURL)
	{
		this.shareURL = shareURL;
	}
	
	public String getShareURL()
	{
		return shareURL;
	}
	
	public void setShareDesc(String shareDesc)
	{
		this.shareDesc = shareDesc;
	}
	
	public String getShareDesc()
	{
		return shareDesc;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public String getShareName()
	{
		return shareName;
	}
	
	public void setShareName(String shareName)
	{
		this.shareName = shareName;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setDiscountAmount(Double discountAmount)
	{
		this.discountAmount = discountAmount;
	}
	
	public Double getDiscountAmount()
	{
		return discountAmount;
	}
	
	public void setDiscountPercentageDbl(Double discountPercentageDbl)
	{
		this.discountPercentageDbl = discountPercentageDbl;
	}
	
	public Double getDiscountPercentageDbl()
	{
		return discountPercentageDbl;
	}
	
	public void setDiscountPriceDbl(Double discountPriceDbl)
	{
		this.discountPriceDbl = discountPriceDbl;
	}
	
	public Double getDiscountPriceDbl()
	{
		return discountPriceDbl;
	}
}
