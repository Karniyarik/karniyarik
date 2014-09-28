package com.karniyarik.web.sitemap;

import com.karniyarik.web.sitemap.vo.urlset.TChangeFreq;

public class SiteMapGenerationConstants
{
	public static TChangeFreq CAR_BRAND_CHANGE_FREQ = TChangeFreq.WEEKLY;
	public static Double CAR_BRAND_PRIORITY = 0.7;
	public static Integer CAR_BRAND_MAX_CLUSTER = 50;
	public static Integer CAR_BRAND_MAX_SEARCH_RESULT = 3000;
	
	public static TChangeFreq PRODUCT_BRAND_CHANGE_FREQ = TChangeFreq.WEEKLY;
	public static Double PRODUCT_BRAND_PRIORITY = 0.7;
	public static Integer PRODUCT_BRAND_MAX_CLUSTER = 20;
	public static Integer PRODUCT_BRAND_MAX_SEARCH_RESULT = 1000;
	
	public static TChangeFreq PRODUCT_CATEGORY_CHANGE_FREQ = TChangeFreq.WEEKLY;
	public static Double PRODUCT_CATEGORY_PRIORITY = 0.8;
	public static Integer PRODUCT_CATEGORY_MAX_CLUSTER = 30;
	public static Integer PRODUCT_CATEGORY_MAX_SEARCH_RESULT = 2000;
	
	public static Integer MAX_TOP_SEARCHES = 1000;
	public static TChangeFreq TOP_SEARCHES_FREQ = TChangeFreq.WEEKLY;
	public static Double TOP_SEARCHES_PRIORITY = 0.9;
	
	public static Double INSIGHT_PRIORITY = 0.9;
	public static TChangeFreq INSIGHT_FREQ = TChangeFreq.WEEKLY;
	
	public static String PRODUCT_ROOT = "urun";
	public static String CAR_ROOT = "araba";	
	
	public static String rootURL = "http://www.karniyarik.com";
}
