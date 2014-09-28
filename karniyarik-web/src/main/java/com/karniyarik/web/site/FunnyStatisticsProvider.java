package com.karniyarik.web.site;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.ga.Analytics;
import com.karniyarik.common.ga.Event;
import com.karniyarik.common.ga.GoogleAnalyticsDataFetcher;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StreamUtil;

import edu.emory.mathcs.backport.java.util.Collections;

public class FunnyStatisticsProvider
{
	private static FunnyStatisticsProvider instance = null;
	private FunnyStatistics stat = null;
	
	private FunnyStatisticsProvider()
	{
		load(); 
	}

	public static FunnyStatisticsProvider getInstance()
	{
		if(instance == null){
			instance = new FunnyStatisticsProvider();
		}
		return instance;
	}
	
	public FunnyStatistics getStat() {
		return stat;
	}

	private void load()
	{
		stat = readFromFile();
		if(stat == null)
		{
			stat = calculateFunnyStatistics();
			writeToFile(stat);
		}
	}
	
	public void update()
	{
		stat = calculateFunnyStatistics();
		writeToFile(stat);
	}
	
	private FunnyStatistics calculateFunnyStatistics()
	{
		String siteName;
		String siteUrl;
		
		String[] keywords= new String[]{"bebek", "elektrik", "bilgisayar", "tekno", "sepet", "burada", "reyon", 
				"dunya", "malzeme", "depo", "market", "eczane", "spor", "sanal", "hepsi", "alisveris"};
		
		int totalSiteCount = 0;
		FunnyStatistics funnyStat = new FunnyStatistics();
		
		Collection<SiteConfig> siteConfigList = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfigList();
		
		for(SiteConfig sc : siteConfigList)
		{
			siteName = sc.getSiteName();
			siteUrl = sc.getUrl();
			if(sc.getCategory().equalsIgnoreCase("urun") )
			{
				totalSiteCount++;
				
				for(String keyword: keywords)
				{
					if(siteName.contains(keyword))
					{
						Integer count = funnyStat.getDomainKeywords().get(keyword);
						if(count == null)
						{
							count = 0;
						}
						count++;
						funnyStat.getDomainKeywords().put(keyword, count);
					}
				}
				
				try
				{
					URL url = new URL(siteUrl);
					String host = url.getHost();
					
					int lastIndexOfDot = host.lastIndexOf(".");
					String siteDomainExtension = host.substring(lastIndexOfDot);
					Integer count = funnyStat.getExtensionKeywords().get(siteDomainExtension);
					if(count == null){
						count = 0;
					}
					count++;
					funnyStat.getExtensionKeywords().put(siteDomainExtension, count);
				}
				catch (MalformedURLException e)
				{
					//e.printStackTrace();
				}				
			}
		}
		
		funnyStat.setTotalSiteCount(totalSiteCount);
		
		Sites sites = new Sites(null);

		List<SiteInfoBean> productSites = sites.getProductSites();
		productSites.addAll(sites.getSponsoredProductSites());
		Collections.sort(productSites, new SiteProductCountComparator());
		if(productSites.size() > 5)
		{
			List<SiteInfoBean> subList = productSites.subList(0, 5);
			funnyStat.getTopProductCountSites().addAll(subList);
		}
		
		List<SiteInfoBean> carSites = sites.getCarSites();
		Collections.sort(carSites, new SiteProductCountComparator());
		if(carSites.size() > 5)
		{
			List<SiteInfoBean> subList = carSites.subList(0, 5);
			funnyStat.getTopCarCountSites().addAll(subList);
		}
		
		List<SiteInfoBean> allSites = sites.getProductSites();
		allSites.addAll(sites.getSponsoredProductSites());
		allSites.addAll(sites.getCarSites());
		allSites.addAll(sites.getCityDealSites());
		//add city deals
		
		Map<String, SiteInfoBean> sitesMap = new HashMap<String, SiteInfoBean>();
		for(SiteInfoBean siteInfoBean: allSites)
		{
			sitesMap.put(siteInfoBean.getSiteConfig().getSiteName(), siteInfoBean);
		}
		
		GoogleAnalyticsDataFetcher ga = GoogleAnalyticsDataFetcher.getInstance(false);
		Analytics weeklyAnalytics = ga.getWeeklyAnalytics();
		
		funnyStat.setAnalyticsLastDate(weeklyAnalytics.getDate());
		
		List<Event> events = weeklyAnalytics.getEvents().getEvents();
		for(Event event: events)
		{
			String name = event.getName().trim();
			List<SiteInfoBean> list = null; 
			if(name.equals("araba-listing-click"))
			{
				list = funnyStat.getTopClickedCarCountSites();
			}
			else if(name.equals("urun-listing-click"))
			{
				list = funnyStat.getTopClickedProductCountSites();
			}
			if(name.equals("citydeal-click"))
			{
				list = funnyStat.getTopClickedCityDealSites();
			}
			
			if(list != null)
			{
				List<Event> topSubEvents = event.getTopSubEvents();
				for(Event subEvent: topSubEvents)
				{
					String sitename = subEvent.getName().trim();
					SiteInfoBean siteInfoBean = sitesMap.get(sitename);
					if(siteInfoBean == null)
					{
						int a = 5;
						int b = a;
					}
					else
					{
						list.add(siteInfoBean);	
					}
				}				
			}
		}
		
		return funnyStat;
	}
	
	private void writeToFile(FunnyStatistics fs)
	{
		File file = getFile();
		try
		{
			FileOutputStream stream = new FileOutputStream(file);
			String json = JSONUtil.getJSON(fs);
			IOUtils.write(json, stream);
			stream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private FunnyStatistics readFromFile()
	{
		FunnyStatistics fs = null;
		try
		{
			
			File file = this.getFile();
			if(file.exists())
			{
				String line = FileUtils.readFileToString(file);
				fs = JSONUtil.parseJSON(line, FunnyStatistics.class);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return fs;
	}
	
	private File getFile() {
		String tempDir = StreamUtil.getTempDir();
		File file = new File(tempDir + "/funnystatistics.json");
		return file;
	}
	
	public static void main(String[] args)
	{
		FunnyStatisticsProvider.getInstance().update();
	}	
}
