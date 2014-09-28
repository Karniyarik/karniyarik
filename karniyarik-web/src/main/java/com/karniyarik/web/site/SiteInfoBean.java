package com.karniyarik.web.site;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.site.SiteInfo;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;
import com.karniyarik.site.SiteInfoConfig;

public class SiteInfoBean {
	private Integer productCount = 0;
	private String siteDetailURL = null;
	private String logoURL = null;
	private String starImgURL = null;
	private int calculatedRank = 0;
	private int rank = 0;
	private String rankLink = null;
	private String ratingOver5 = "0";
	private SiteInfo enterpriseInfo = null;
	private SiteInfoConfig siteReviewInfo = null;
	private SiteConfig siteConfig = null;
	private AlexaSiteInfo alexaInfo = null; 

	public SiteInfoBean() {

	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getSiteDetailURL() {
		return siteDetailURL;
	}

	public void setSiteDetailURL(String siteDetailURL) {
		this.siteDetailURL = siteDetailURL;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public String getStarImgURL() {
		return starImgURL;
	}

	public void setStarImgURL(String starImgURL) {
		this.starImgURL = starImgURL;
	}

	public int getCalculatedRank() {
		return calculatedRank;
	}

	public void setCalculatedRank(int calculatedRank) {
		this.calculatedRank = calculatedRank;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getRankLink() {
		return rankLink;
	}

	public void setRankLink(String rankLink) {
		this.rankLink = rankLink;
	}

	public String getRatingOver5() {
		return ratingOver5;
	}

	public void setRatingOver5(String ratingOver5) {
		this.ratingOver5 = ratingOver5;
	}

	public SiteInfo getEnterpriseInfo() {
		return enterpriseInfo;
	}

	public void setEnterpriseInfo(SiteInfo enterpriseInfo) {
		this.enterpriseInfo = enterpriseInfo;
	}

	public SiteInfoConfig getSiteReviewInfo() {
		return siteReviewInfo;
	}

	public void setSiteReviewInfo(SiteInfoConfig siteReviewInfo) {
		this.siteReviewInfo = siteReviewInfo;
	}

	public SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public void setSiteConfig(SiteConfig siteConfig) {
		this.siteConfig = siteConfig;
	}
	
	public AlexaSiteInfo getAlexaInfo()
	{
		return alexaInfo;
	}
	
	public void setAlexaInfo(AlexaSiteInfo alexaInfo)
	{
		this.alexaInfo = alexaInfo;
	}
}
