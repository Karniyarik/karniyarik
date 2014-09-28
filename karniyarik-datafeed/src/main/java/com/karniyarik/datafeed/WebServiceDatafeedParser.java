package com.karniyarik.datafeed;

import java.util.List;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.crawler.IContentFetcher;
import com.karniyarik.crawler.util.URLManager;
import com.karniyarik.parser.pojo.Product;

public abstract class WebServiceDatafeedParser implements DatafeedParser {
	
	private SiteConfig siteConfig;
	private URLManager urlManager;
	private CategoryConfig categoryConfig;
	private IContentFetcher contentFetcher;
	
	public SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public URLManager getUrlManager() {
		return urlManager;
	}

	public CategoryConfig getCategoryConfig() {
		return categoryConfig;
	}
	
	public void setSiteConfig(SiteConfig siteConfig) {
		this.siteConfig = siteConfig;
	}

	public void setUrlManager(URLManager urlManager) {
		this.urlManager = urlManager;
	}

	public void setCategoryConfig(CategoryConfig categoryConfig) {
		this.categoryConfig = categoryConfig;
	}

	public IContentFetcher getContentFetcher() {
		return contentFetcher;
	}
	
	public void setContentFetcher(IContentFetcher contentFetcher) {
		this.contentFetcher = contentFetcher;
	}
	
	public abstract List<Product> parse(String content);
}
