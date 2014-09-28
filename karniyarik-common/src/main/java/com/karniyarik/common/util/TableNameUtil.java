package com.karniyarik.common.util;

import java.util.Locale;

public class TableNameUtil
{

	private static final String LINKS_VISITED_SUFFIX = "_LINKS_VISITED";
	private static final String PRODUCT_STAT_SUFFIX = "_PRODUCT_STAT";
	private static final String	API_STAT_PREFIX	= "API_STAT_";
	
	public static String createLinksVisitedTableName(String siteName) {
		return createTableName(siteName, LINKS_VISITED_SUFFIX);
	}
	
	public static String createProductStatTableName(String siteName) {
		return createTableName(siteName, PRODUCT_STAT_SUFFIX);
	}
	
	public static String createApiStatTableName(String apiKey) {
		return createTableName(API_STAT_PREFIX, apiKey);
	}
	
	private static String createTableName(String siteName, String suffix) {
		return siteName.toUpperCase(Locale.ENGLISH).replaceAll("\\W", "") + suffix;
	}
	
}
