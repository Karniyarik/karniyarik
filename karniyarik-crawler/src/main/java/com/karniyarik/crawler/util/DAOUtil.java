package com.karniyarik.crawler.util;

public class DAOUtil
{
	public static boolean canSaveURL(String aURL)
	{
		return aURL!=null && aURL.length() < 512;
	}
}
