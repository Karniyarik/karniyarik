package com.karniyarik.jobscheduler.util;


public class JobStatisticsSummary
{
	String siteName; 
	
	double ratioProductMiss = 0;
	double ratioProductDuplicate = 0; 
	double ratioProductAccepted = 0;
	String imageProductRatio = "";

	double ratioParsedImage = 0;
	double ratioMissedImage = 0;
	String imageImageRatio = "";

	double ratioParsedBrand = 0;
	double ratioMissedBrand = 0;
	String imageBrandRatio = "";
	
	double ratioParsedBreadcrumb = 0;
	double ratioMissedBreadcrumb= 0;
	String imageBreadcrumbRatio = "";

	String imageRatios = ""; 
		
	int fetchTimePercentage = 0;
	String imageFetchTime = "";
	
	double overallHealth = 0;
	
	public JobStatisticsSummary()
	{
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public double getRatioProductMiss() {
		return ratioProductMiss;
	}

	public void setRatioProductMiss(double ratioProductMiss) {
		this.ratioProductMiss = ratioProductMiss;
	}

	public double getRatioProductDuplicate() {
		return ratioProductDuplicate;
	}

	public void setRatioProductDuplicate(double ratioProductDuplicate) {
		this.ratioProductDuplicate = ratioProductDuplicate;
	}

	public double getRatioProductAccepted() {
		return ratioProductAccepted;
	}

	public void setRatioProductAccepted(double ratioProductAccepted) {
		this.ratioProductAccepted = ratioProductAccepted;
	}

	public String getImageProductRatio() {
		return imageProductRatio;
	}

	public void setImageProductRatio(String imageProductRatio) {
		this.imageProductRatio = imageProductRatio;
	}

	public double getRatioParsedImage() {
		return ratioParsedImage;
	}

	public void setRatioParsedImage(double ratioParsedImage) {
		this.ratioParsedImage = ratioParsedImage;
	}

	public double getRatioMissedImage() {
		return ratioMissedImage;
	}

	public void setRatioMissedImage(double ratioMissedImage) {
		this.ratioMissedImage = ratioMissedImage;
	}

	public String getImageImageRatio() {
		return imageImageRatio;
	}

	public void setImageImageRatio(String imageImageRatio) {
		this.imageImageRatio = imageImageRatio;
	}

	public double getRatioParsedBrand() {
		return ratioParsedBrand;
	}

	public void setRatioParsedBrand(double ratioParsedBrand) {
		this.ratioParsedBrand = ratioParsedBrand;
	}

	public double getRatioMissedBrand() {
		return ratioMissedBrand;
	}

	public void setRatioMissedBrand(double ratioMissedBrand) {
		this.ratioMissedBrand = ratioMissedBrand;
	}

	public String getImageBrandRatio() {
		return imageBrandRatio;
	}

	public void setImageBrandRatio(String imageBrandRatio) {
		this.imageBrandRatio = imageBrandRatio;
	}

	public double getRatioParsedBreadcrumb() {
		return ratioParsedBreadcrumb;
	}

	public void setRatioParsedBreadcrumb(double ratioParsedBreadcrumb) {
		this.ratioParsedBreadcrumb = ratioParsedBreadcrumb;
	}

	public double getRatioMissedBreadcrumb() {
		return ratioMissedBreadcrumb;
	}

	public void setRatioMissedBreadcrumb(double ratioMissedBreadcrumb) {
		this.ratioMissedBreadcrumb = ratioMissedBreadcrumb;
	}

	public String getImageBreadcrumbRatio() {
		return imageBreadcrumbRatio;
	}

	public void setImageBreadcrumbRatio(String imageBreadcrumbRatio) {
		this.imageBreadcrumbRatio = imageBreadcrumbRatio;
	}

	public String getImageRatios() {
		return imageRatios;
	}

	public void setImageRatios(String imageRatios) {
		this.imageRatios = imageRatios;
	}

	public int getFetchTimePercentage() {
		return fetchTimePercentage;
	}

	public void setFetchTimePercentage(int fetchTimePercentage) {
		this.fetchTimePercentage = fetchTimePercentage;
	}

	public String getImageFetchTime() {
		return imageFetchTime;
	}

	public void setImageFetchTime(String imageFetchTime) {
		this.imageFetchTime = imageFetchTime;
	}

	public double getOverallHealth() {
		return overallHealth;
	}

	public void setOverallHealth(double overallHealth) {
		this.overallHealth = overallHealth;
	}
}
