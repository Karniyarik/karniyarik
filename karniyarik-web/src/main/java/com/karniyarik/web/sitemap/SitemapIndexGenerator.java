package com.karniyarik.web.sitemap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.karniyarik.web.sitemap.vo.index.Sitemapindex;
import com.karniyarik.web.sitemap.vo.index.TSitemap;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;
import com.karniyarik.web.sitemap.vo.urlset.Urlset;

public class SitemapIndexGenerator {
	
	private String rootPath;
	private List<String> fileNames = new ArrayList<String>();
	private Sitemapindex index = null;
	Map<String, TSitemap> sitemaps = new HashMap<String, TSitemap>(); 
	private URLCache cache = new URLCache();
	
	public SitemapIndexGenerator(String rootPath) {
		this.rootPath = rootPath;
		
		try {
			index = SitemapIO.readIndex(rootPath);
		} catch (Exception e) {
			
		}
		
		if(index == null)
		{
			index = SitemapTypeFactory.createIndex();
		}
		
		for(TSitemap sitemap: index.getSitemap())
		{
			sitemaps.put(sitemap.getLoc(), sitemap);
			
			String loc = sitemap.getLoc();
			loc = loc.replace(SiteMapGenerationConstants.rootURL + "/sitemap/", "").trim();
			
			Urlset urlset = SitemapIO.readMap(rootPath, loc);
			for(TUrl url: urlset.getUrl())
			{
				cache.add(url.getLoc());
			}
		}		
	}

	public URLCache getCache() {
		return cache;
	}
	
	public void addFiles(List<String> fileNames) {
		this.fileNames.addAll(fileNames);
	}
	
	public void generate() {
		Date date = Calendar.getInstance().getTime();
		String dateStr = Generator.getUTCDate(date);
		
		for(String fileName: fileNames)
		{
			fileName = SiteMapGenerationConstants.rootURL + "/sitemap/" + fileName;
			TSitemap sitemap = null;
			
			if(sitemaps.containsKey(fileName))
			{
				sitemap = sitemaps.get(fileName);
			}
			else
			{
				sitemap = SitemapTypeFactory.createSitemap();
				sitemap.setLoc(fileName);
				sitemap.setLastmod(dateStr);
			}
			
			sitemaps.put(sitemap.getLoc(), sitemap);
		}
		
		index.getSitemap().clear();
		index.getSitemap().addAll(sitemaps.values());
		
		SitemapIO.writeIndex(rootPath, index);
		
		new GoogleSitemapSubmit().submit(fileNames);
	}
}
