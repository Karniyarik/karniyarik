package com.karniyarik.web.sitemap.providers;

import com.karniyarik.web.sitemap.BaseURLProvider;
import com.karniyarik.web.sitemap.vo.urlset.TChangeFreq;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;

public class StaticPagesProvider extends BaseURLProvider {
	String[] staticPages = new String[] {
			"http://www.karniyarik.com/index.jsp",
			"http://www.karniyarik.com/aboutus.jsp",
			"http://www.karniyarik.com/sites.jsp",
			"http://www.karniyarik.com/api.jsp",
			"http://www.karniyarik.com/help.jsp",
			"http://www.karniyarik.com/iphone",
			"http://www.karniyarik.com/datafeed",
			"http://www.karniyarik.com/tarif",
			"http://www.karniyarik.com/sehir-firsati",
			"http://www.karniyarik.com/kurumsal",
			"http://www.karniyarik.com/firsat",
			"http://www.karniyarik.com/contact.jsp",
			"http://www.karniyarik.com/semanticweb.rdf" };

	int index = 0;

	@Override
	public String getFilename() {
		return "static";
	}

	@Override
	public boolean hasNext() {
		return index < staticPages.length;
	}

	@Override
	public TUrl next() {
		String url = staticPages[index];
		TUrl urlType = createTUrl(url, 0.9, TChangeFreq.WEEKLY) ;
		index++;
		return urlType;
	}

}
