package com.karniyarik.web.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.site.SiteInfo;
import com.karniyarik.common.site.SiteRegistry;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;
import com.karniyarik.site.SiteInfoConfig;
import com.karniyarik.site.SiteInfoProvider;
import com.karniyarik.web.citydeals.CityDealConverter;
import com.karniyarik.web.citydeals.CityDealResult;
import com.karniyarik.web.util.Formatter;
import com.karniyarik.web.util.WebApplicationDataHolder;

import edu.emory.mathcs.backport.java.util.Collections;

public class Sites {
	
	private List<SiteInfoBean> sponsoredProductSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> productSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> featuredSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> carSites = new ArrayList<SiteInfoBean>();
	private List<SiteInfoBean> cityDealSites = new ArrayList<SiteInfoBean>();
	
	public Sites(HttpServletRequest request) {
		Collection<SiteConfig> aSitesConfigCollection = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfigListOrderByName();
		SiteInfoProvider infoProvider = SiteInfoProvider.getInstance();
		
		for(SiteConfig siteConfig: aSitesConfigCollection){ 
			SiteInfoBean bean = new SiteInfoBean();
			
			SiteInfo enterpriseSiteInfo = SiteRegistry.getInstance().getSiteInfo(siteConfig.getSiteName());
			SiteInfoConfig siteReviewInfo = infoProvider.getSiteInfo(siteConfig.getSiteName());

			bean.setEnterpriseInfo(enterpriseSiteInfo);
			bean.setSiteReviewInfo(siteReviewInfo);
			bean.setSiteConfig(siteConfig);

			WebApplicationDataHolder aDataHolder = WebApplicationDataHolder.getInstance();
			Integer productCount = aDataHolder.getSiteProductCount(siteConfig.getSiteName());
			
			int rank = -1;
			int calculatedRank = -1;
			String rankLink = "";
			String ratingOver5 = "0";
			String logourl = "";
			
			if(enterpriseSiteInfo != null && StringUtils.isNotBlank(enterpriseSiteInfo.getLogourl())) {
				logourl = enterpriseSiteInfo.getLogourl();
			} else{
				logourl= getContextPath(request) + "/images/nologo.png";
			}
			
			if(siteReviewInfo != null)
			{
				ratingOver5 = Formatter.formatRating(siteReviewInfo.getSiteRankOver10());
				calculatedRank = siteReviewInfo.getCalculatedRank();
				rank = siteReviewInfo.getRank();
				rankLink = siteReviewInfo.getSiteReviewURL();
			}
			
			String starURL = "";
			if (calculatedRank >= 0) {
				starURL = getContextPath(request) + "/images/stars/stars"+ calculatedRank + ".png"; 
			} else { 
				starURL = getContextPath(request) + "/images/stars/stars0.png";
			}
			
			String siteUrl = getContextPath(request) + "/site/" + siteConfig.getSiteName();
			
			bean.setCalculatedRank(calculatedRank);
			bean.setLogoURL(logourl);
			bean.setProductCount(productCount);
			bean.setRank(rank);
			bean.setRankLink(rankLink);
			bean.setRatingOver5(ratingOver5);
			bean.setSiteDetailURL(siteUrl);
			bean.setStarImgURL(starURL);
			
			AlexaSiteInfo alexaInfo = SiteRegistry.getInstance().getAlexaInfo(siteConfig.getSiteName());
			bean.setAlexaInfo(alexaInfo);
			
			if(bean.getEnterpriseInfo() != null && bean.getEnterpriseInfo().isFeatured())
			{
				featuredSites.add(bean);
			}
			
			if(bean.getEnterpriseInfo() != null && bean.getEnterpriseInfo().isSponsored())
			{
				sponsoredProductSites.add(bean);
			}
			else if(siteConfig.getCategory().equalsIgnoreCase("urun"))
			{
				productSites.add(bean);
			}
			else if(siteConfig.getCategory().equalsIgnoreCase("araba"))
			{
				carSites.add(bean);	
			}
		}
		
		Collections.sort(getFeaturedSites(), new SiteInfoBeanNameAndImageComparator());
		Collections.sort(getProductSites(), new SiteInfoBeanNameAndImageComparator());
		Collections.sort(getSponsoredProductSites(), new SiteInfoBeanNameAndImageComparator());
		Collections.sort(getCarSites(), new SiteInfoBeanNameAndImageComparator());
		
		List<String> sources= new CityDealConverter(Integer.MAX_VALUE).getSources();
		Map<String, SiteInfoBean> cityDealSites = new HashMap<String, SiteInfoBean>();
		for(String source: sources)
		{
			SiteInfoBean siteInfoBean = cityDealSites.get(source);
			if(siteInfoBean == null)
			{
				SiteInfoBean bean = new SiteInfoBean();
				SiteConfig config= new SiteConfig();
				config.setSiteName(source);
				bean.setSiteConfig(config);
				bean.setLogoURL(CityDealResult.constructSourceImage(source));
				//config.setUrl(cityDeal.getSourceURL());
				cityDealSites.put(source, bean);
				bean.setProductCount(1);
			}
			else
			{
				siteInfoBean.setProductCount(siteInfoBean.getProductCount()+1);
			}
		}
		
		this.cityDealSites.addAll(cityDealSites.values());
		Collections.sort(getCityDealSites(), new SiteInfoBeanNameAndImageComparator());
	}

	private String getContextPath(HttpServletRequest request) {
		if(request != null)
		{
			return request.getContextPath();	
		}
		
		return "";
	}
	
	public List<SiteInfoBean> getCarSites() {
		return carSites;
	}
	
	public List<SiteInfoBean> getProductSites() {
		return productSites;
	}
	
	public List<SiteInfoBean> getSponsoredProductSites() {
		return sponsoredProductSites;
	}
	
	public List<SiteInfoBean> getFeaturedSites() {
		return featuredSites;
	}
	
	public List<SiteInfoBean> getCityDealSites()
	{
		return cityDealSites;
	}
}
