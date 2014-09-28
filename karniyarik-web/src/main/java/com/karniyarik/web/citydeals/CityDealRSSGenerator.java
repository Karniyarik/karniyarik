package com.karniyarik.web.citydeals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.karniyarik.common.util.StringUtil;
import com.karniyarik.web.json.LinkedLabel;
import com.karniyarik.web.servlet.image.ImageServlet;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndImageImpl;

public class CityDealRSSGenerator {

	//http://feeds.feedburner.com/sehirfirsatlari/rss/adana
	
	public CityDealRSSGenerator() {
	}
	
	public SyndFeed generateCityRSS(CityResult result,int maxNumberOfDeals)
	{
		try {
			List<CityDealResult> cityDeals = new CityDealConverter(result.getValue(), Integer.MAX_VALUE, CityDealConverter.SORT_DPERCENTAGE, false).getCityDeals();
			int addedDealCount = 0;
			Date date = new Date();
			
			SyndFeed feed = new SyndFeedImpl();
			feed.setFeedType("rss_2.0");
			feed.setAuthor("info@karniyarik.com");
			feed.setDescription(result.getName() + " şehir fırsatlarının tümünü bulabileceğiniz RSS kanalı");
			feed.setEncoding(StringUtil.DEFAULT_ENCODING);
			feed.setTitle("Karnıyarık "+ result.getName() +" Şehir Fırsatları");
			feed.setLink("http://www.karniyarik.com/sehirfirsatlari/rss/" + result.getName().toLowerCase(Locale.ENGLISH));
			SyndImage feedImage = new SyndImageImpl();
			feedImage.setLink(feed.getLink());
			feedImage.setTitle(feed.getTitle());
			feedImage.setUrl("http://www.karniyarik.com/images/logo/karniyarik86x86.png");
			feed.setImage(feedImage);
			feed.setLanguage("tr");
			feed.setPublishedDate(date);
		    
			List<SyndEntry> entries = new ArrayList<SyndEntry>();
			
			for(CityDealResult cityDeal: cityDeals)
			{
				if(addedDealCount == maxNumberOfDeals)
					break;
				SyndEntry entry = new SyndEntryImpl();
				entry.setAuthor("info@karniyarik.com");
				entry.setTitle(CityDealConverter.getTitle(cityDeal, 100));
				//entry.setLink(cityDeal.getShareURL());
				
				String productURL = cityDeal.getShareURL();
				productURL = productURL.replace("source=web", "source=rss");
				entry.setLink(productURL);
				entry.setPublishedDate(cityDeal.getStartDate());
				entry.setSource(feed);
				entry.setUpdatedDate(cityDeal.getStartDate());
				SyndContent content = new SyndContentImpl();
				content.setType("text/html");
				
				StringBuffer htmlContent = new StringBuffer();
				htmlContent.append("<div>");
				
				htmlContent.append("<div style='width:180px;height:200px;float:left;margin-right:10px;'>");
				htmlContent.append("<a href='");htmlContent.append(productURL);htmlContent.append("'>");
				String imgUrl = ImageServlet.getImageRszUrl("http://www.karniyarik.com", cityDeal.getImage(), cityDeal.getImageName(), 180, 140);
				htmlContent.append("<img style='width:180px;height:140px;' src='");htmlContent.append(imgUrl);htmlContent.append("'/>");
				htmlContent.append("</a>");
				
		        htmlContent.append("<div style='width:180px;background-color:#81AB00;border:1px solid #AAC652;line-height:40px;text-align:center;vertical-align:center;margin-top:10px;font-size:20px;'>");
		        htmlContent.append("<a style='color:white;text-decoration:none;' href='");htmlContent.append(productURL);htmlContent.append("'>");
				htmlContent.append("Satın Al");
				htmlContent.append("</a>");
		        htmlContent.append("</div>");

				htmlContent.append("</div>");
				
				htmlContent.append("<div style='display:table;min-width:540px;height:200px;'>");
				
				htmlContent.append("<div style='font-size:16px;height:140px;display:inline-block;color:#6F6F6F;overflow:hidden;'>");
				htmlContent.append(LinkedLabel.getShortenedLabel(cityDeal.getDescription(), 350));
				htmlContent.append("<a style='color:white;text-decoration:none;' href='");htmlContent.append(productURL);htmlContent.append("'>");
				htmlContent.append("Devamı için tıklayınız");
				htmlContent.append("</a>");
				htmlContent.append("</div>");				
				
				htmlContent.append("<div style='font-size:11px;font-weight:bold;width:240px;text-align:center;margin-top:10px;height:40px;line-height:16px;'>");
				
				htmlContent.append("<div style='background-color:#EFEFEF;color:#222222;float:left;width:80px;'><div>Fiyat<br><span style='font-size:16px !important;line-height:24px;'>");
				htmlContent.append(cityDeal.getDiscountPrice());
				htmlContent.append(" ");
				htmlContent.append(cityDeal.getPriceCurrency());
				htmlContent.append("</span></div></div>");
				htmlContent.append("<div style='background-color:#AAC652;color:#446A00;float:left;width:80px;'><div>Eski Fiyat<br><span style='font-size:16px !important;line-height:24px;'>");
				htmlContent.append(cityDeal.getPrice());
				htmlContent.append(" ");
				htmlContent.append(cityDeal.getPriceCurrency());
				htmlContent.append("</span></div></div>");
				htmlContent.append("<div style='background-color:#81AB00;color:#FFFFFF;float:left;width:80px;'><div>Kazanç<br><span style='font-size:16px !important;line-height:24px;'>");
				htmlContent.append(cityDeal.getDiscountPercentage());
				htmlContent.append("</span></div></div>");
		        
		        htmlContent.append("</div>");
				
				htmlContent.append("</div>");
				
				htmlContent.append("</div>");
				
				content.setValue(htmlContent.toString());
				entry.setDescription(content);
				entries.add(entry);
				addedDealCount++;
			}
			
			feed.setEntries(entries);
			
			return feed;
			
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public static void main(String[] args) {
		
	}
}
