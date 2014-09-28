package com.karniyarik.web.sitemap;

import java.util.Calendar;
import java.util.Date;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.web.sitemap.vo.index.Sitemapindex;
import com.karniyarik.web.sitemap.vo.index.TSitemap;
import com.karniyarik.web.sitemap.vo.urlset.TUrl;
import com.karniyarik.web.sitemap.vo.urlset.Urlset;
import com.karniyarik.web.util.WebLoggerProvider;

public class SitemapDateUpdater {
	
	private String rootPath;
	
	public SitemapDateUpdater() {
		this.rootPath = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig().getSitemapRootPath();
	}
	
	public void update()
	{
		Date date = Calendar.getInstance().getTime();
		String dateStr = Generator.getUTCDate(date);
		
		try {
			Sitemapindex index = SitemapIO.readIndex(rootPath);
			if(index != null)
			{
				for(TSitemap sitemap: index.getSitemap())
				{
					sitemap.setLastmod(dateStr);
					String loc = sitemap.getLoc();
					loc = loc.replace(SiteMapGenerationConstants.rootURL + "/sitemap", "").trim();
					
					Urlset urlset = SitemapIO.readMap(rootPath, loc);
					for(TUrl url: urlset.getUrl())
					{
						url.setLastmod(dateStr);
					}
					
					SitemapIO.writeMap(rootPath, loc, urlset);
				}
				
				SitemapIO.writeIndex(rootPath, index);
			}
		} catch (Exception e) {
			WebLoggerProvider.logException("Unable to update dates in sitemaps ", e);
		}
	}
}
