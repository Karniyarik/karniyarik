package com.karniyarik.web.sitemap;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gdata.client.webmastertools.WebmasterToolsService;
import com.google.gdata.data.webmastertools.SitemapsRegularEntry;
import com.google.gdata.data.webmastertools.SitesEntry;
import com.google.gdata.data.webmastertools.SitesFeed;
import com.google.gdata.util.AuthenticationException;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.MailConfig;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.web.util.WebLoggerProvider;

public class GoogleSitemapSubmit
{
	private WebmasterToolsService webmastersService = null;
	
	public GoogleSitemapSubmit()
	{
	    MailConfig mailConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getMailConfig();
	    webmastersService = new WebmasterToolsService("karniyarik-com-1");
	    try
		{
			webmastersService.setUserCredentials(mailConfig.getUser(), mailConfig.getPassword());
		}
		catch (AuthenticationException e)
		{
			ExceptionNotifier.sendException("authenticate-webmasters", "Cannot authenticate to google webmasters", "", e);
			WebLoggerProvider.logException("Cannot authenticate to google webmasters", e);
		}
	}
	
	public void submit(List<String> filenameList)
	{
		for(String filename: filenameList)
		{
			submit(filename);
		}
	}
	
	public void printUserSites() {
		try {
			System.out.println("Printing user's sites:");

			// Request the feed
			URL feedUrl = new URL("https://www.google.com/webmasters/tools/feeds/sites/");
			SitesFeed sitesResultFeed = webmastersService.getFeed(feedUrl, SitesFeed.class);

			// Print the results
			for (SitesEntry entry : sitesResultFeed.getEntries()) {
					System.out.println("\t" + entry.getTitle().getPlainText());
			}
		} catch (Exception e) {
			
		}
}
	
	public void submit(String filename)
	{
		try
		{
			// Create the Sitemap entry to submit
			SitemapsRegularEntry entry = new SitemapsRegularEntry();
			
			String sitemapURLStr = SiteMapGenerationConstants.rootURL + "/sitemap/" + filename;
			
			// sitemapUrl is the URL of the Sitemap to be submitted
			entry.setId(sitemapURLStr);
			entry.setSitemapType("WEB");
			String sitemapId = URLEncoder.encode(sitemapURLStr, "UTF-8");

			// Submit the Sitemap
			String siteId = URLEncoder.encode("http://www.karniyarik.com/","UTF-8");
			URL postURL = new URL("http://www.google.com/webmasters/tools/feeds/"+ siteId + "/sitemaps");
			
			webmastersService.insert(postURL, entry);
		}
		catch (Throwable e)
		{
			ExceptionNotifier.sendException("sitemap-submit", "Cannot send sitemap to google", "Filename " + filename, e);
			WebLoggerProvider.logException("Cannot submit sitemap to google webmasters (" + filename + ")", e);
	
		}
	}
	
	public static void main(String[] args)
	{
		new GoogleSitemapSubmit().printUserSites();
		new GoogleSitemapSubmit().submit("product_search100.xml");
	}
}
