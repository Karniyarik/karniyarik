package com.karniyarik.web.sitemap;

import com.karniyarik.web.sitemap.vo.index.ObjectFactory;
import com.karniyarik.web.sitemap.vo.index.Sitemapindex;
import com.karniyarik.web.sitemap.vo.index.TSitemap;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;
import com.karniyarik.web.sitemap.vo.urlset.Urlset;

public class SitemapTypeFactory {
	
	public static Sitemapindex createIndex()
	{
		return new ObjectFactory().createSitemapindex();
	}

	public static TSitemap createSitemap()
	{
		return new ObjectFactory().createTSitemap();
	}

	public static Urlset createUrlSet()
	{
		return new com.karniyarik.web.sitemap.vo.urlset.ObjectFactory().createUrlset();
	}
	
	public static TUrl createUrl()
	{
		return new com.karniyarik.web.sitemap.vo.urlset.ObjectFactory().createTUrl();
	}

}
