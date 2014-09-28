package com.karniyarik.common.ga;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.google.gdata.client.analytics.AnalyticsService;
import com.google.gdata.client.analytics.DataQuery;
import com.google.gdata.data.analytics.AccountEntry;
import com.google.gdata.data.analytics.AccountFeed;
import com.google.gdata.data.analytics.DataEntry;
import com.google.gdata.data.analytics.DataFeed;
import com.google.gdata.util.ServiceException;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.MailConfig;

public class GoogleAnalyticsDataFetcher {

	private static final String ACCOUNT_NAME = "karniyarik.com";
	public static final String DATA_URL = "https://www.google.com/analytics/feeds/data";
	public static final String ACCOUNTS_URL = "https://www.google.com/analytics/feeds/accounts/default";
	
	private AnalyticsService analyticsService = null;
	private AccountEntry mainAccount = null;
	private SimpleDateFormat dateformat = null;
	private Analytics yesterdayAnalytics= null;
	private Analytics todayAnalytics = null;
	private Analytics weeklyAnalytics = null;
	private boolean refreshDaily;
	
	private static GoogleAnalyticsDataFetcher instance = null;
	
	private GoogleAnalyticsDataFetcher(boolean refreshDaily) {
		try {
			this.refreshDaily = refreshDaily;
			MailConfig mailConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getMailConfig();
			analyticsService = new AnalyticsService("karniyarik");
			analyticsService.setUserCredentials(mailConfig.getUser(), mailConfig.getPassword());
			AccountFeed availableAccounts = getAvailableAccounts();
		    for (AccountEntry entry : availableAccounts.getEntries()) {
		    	if(entry.getTitle().getPlainText().equalsIgnoreCase(ACCOUNT_NAME))
		    	{
		    		mainAccount = entry;
		    	}
		    }
		    if(mainAccount == null)
		    {
		    	throw new RuntimeException("Analytics account cannot be found");
		    }
		    
		    dateformat = new SimpleDateFormat("yyyy-MM-dd");
		    refreshData();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static GoogleAnalyticsDataFetcher getInstance(boolean refreshDaily) {
		if(instance == null)
		{
			instance = new GoogleAnalyticsDataFetcher(refreshDaily);
		}
		return instance;
	}
	
	private AccountFeed getAvailableAccounts() throws IOException, ServiceException {
		URL feedUrl = new URL(ACCOUNTS_URL);
		return analyticsService.getFeed(feedUrl, AccountFeed.class);
	}
	
	private Events getEvents(Date start, Date end)
	{
		Events events = new Events();
		
		try {
			DataQuery query = new DataQuery(new URL(DATA_URL));
			query.setIds(mainAccount.getTableId().getValue());
			query.setStartDate(dateformat.format(start));
			query.setEndDate(dateformat.format(end));
			query.setDimensions("ga:eventCategory");
			query.setMetrics("ga:totalEvents,ga:uniqueEvents,ga:eventValue");
			query.setMaxResults(20);
			query.setSort("-ga:totalEvents");
			DataFeed dataFeed = analyticsService.getFeed(query.getUrl(), DataFeed.class);
			
			String[] mainCaptions = new String[] {"ga:eventCategory", "ga:totalEvents", "ga:uniqueEvents", "ga:eventValue"};
			String[] subCaptions = new String[] {"ga:eventAction", "ga:totalEvents", "ga:uniqueEvents", "ga:eventValue"};
		    for (DataEntry entry : dataFeed.getEntries()) {
				Event event = new Event(entry, mainCaptions);
		    	events.getEvents().add(event);
		    	
		    	query.setDimensions("ga:eventAction");
				//query.setMetrics("ga:totalEvents,ga:eventValue");
				query.setMetrics("ga:totalEvents,ga:uniqueEvents,ga:eventValue");
				String filter = entry.getTitle().getPlainText();
				filter =filter.replace("=", "==");
				query.setFilters(filter);
				query.setMaxResults(50);
				DataFeed subDataFeed = analyticsService.getFeed(query.getUrl(), DataFeed.class);
				for (DataEntry subEntry : subDataFeed.getEntries()) {
					Event subEvent = new Event(subEntry, subCaptions);
					event.getTopSubEvents().add(subEvent);
				}
		    }
		    //events.print();
		    //printData("HONK", dataFeed);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	    
	    return events;
	}
	
	private Referrals getReferreals(Date start, Date end)
	{
		Referrals referrals = new Referrals();
		
		try {
			DataQuery query = new DataQuery(new URL(DATA_URL));
			query.setIds(mainAccount.getTableId().getValue());
			query.setStartDate(dateformat.format(start));
			query.setEndDate(dateformat.format(end));
			query.setDimensions("ga:source");
			query.setMetrics("ga:visits,ga:pageviews,ga:timeOnSite,ga:exits");
			query.setSort("-ga:visits");
			query.setMaxResults(20);
			//query.setMetrics("ga:pageviews,ga:timeOnSite,ga:newVisits,ga:visits,ga:visitors");
			
			DataFeed dataFeed = analyticsService.getFeed(query.getUrl(), DataFeed.class);
			for (DataEntry entry : dataFeed.getEntries()) {
				Referral referral = new Referral(entry);
				referrals.getReferrals().add(referral);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	    
	    return referrals;
	}
//	  public static void printData(String title, DataFeed dataFeed) {
//	    System.out.println(title);
//	    for (DataEntry entry : dataFeed.getEntries()) {
//	    	System.out.println(entry.stringValueOf("ga:source") 
//	    			+ " - " +   entry.longValueOf("ga:visits")
//	    			+ " - " +  entry.longValueOf("ga:pageviews")
//	    			+ " - " +  entry.longValueOf("ga:timeOnSite")	    			
//	    			+ " - " +   entry.longValueOf("ga:exits"));
//	    }
//	    System.out.println();
//	  }
//	
	private Visits getVisits(Date start, Date end)
	{
		Visits visits = new Visits();
		
		try {
			DataQuery query = new DataQuery(new URL(DATA_URL));
			query.setIds(mainAccount.getTableId().getValue());
			query.setStartDate(dateformat.format(start));
			query.setEndDate(dateformat.format(end));
			query.setMetrics("ga:pageviews,ga:timeOnSite,ga:newVisits,ga:visits,ga:visitors");
			DataFeed dataFeed = analyticsService.getFeed(query.getUrl(), DataFeed.class);
			if(dataFeed.getEntries() != null && dataFeed.getEntries().size() > 0)
			{
				Visit visit = new Visit(dataFeed.getEntries().get(0));
				visits.getVisits().add(visit);
			}
			
			query.setMetrics("ga:pageviews,ga:visits,ga:newVisits,ga:timeOnSite");
			query.setFilters("ga:isMobile==Yes");
			dataFeed = analyticsService.getFeed(query.getUrl(), DataFeed.class);
			if(dataFeed.getEntries() != null && dataFeed.getEntries().size() > 0)
			{
				Visit visit = new Visit(dataFeed.getEntries().get(0));
				visit.setMobile(true);
				visits.getVisits().add(visit);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	    
	    return visits;
	}
	
	private Analytics getAnalytics(Date start, Date end)
	{
		Analytics analytics = new Analytics();
		Events events = getEvents(start, end);
		Referrals referrals = getReferreals(start, end);
		Visits visits = getVisits(start, end);
		analytics.setEvents(events);
		analytics.setReferrals(referrals);
		analytics.setVisits(visits);
		analytics.setDate(end);
		return analytics;
	}
	
	public Analytics getTodayAnalytics()
	{
		return todayAnalytics;
	}
	
	public Analytics getYesterdayAnalytics()
	{
		return yesterdayAnalytics;
	}
	
	public Analytics getWeeklyAnalytics() {
		return weeklyAnalytics;
	}
	
	public void refreshData()
	{
		Calendar cal = Calendar.getInstance();
	    Date today = cal.getTime(); 
		
	    cal.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = cal.getTime();
		
		if(refreshDaily)
		{
			if(yesterdayAnalytics == null){
				yesterdayAnalytics = new GoogleAnalyticsIO().read(yesterday);
				if(yesterdayAnalytics == null){
					yesterdayAnalytics = getAnalytics(yesterday, yesterday);
					new GoogleAnalyticsIO().write(yesterdayAnalytics, yesterday);	
				}
			} 

			todayAnalytics =  getAnalytics(today, today);
		}
				
		//fetch weekly if 1 week is passed
		//otherwise read from file
	    boolean shallUpdate = true;
		weeklyAnalytics = new GoogleAnalyticsIO().read("weekly");
		
		if(weeklyAnalytics != null)
		{
			Period p = new Period(weeklyAnalytics.getDate().getTime(), today.getTime(), PeriodType.days());
			if(p.getDays() < 7)
			{
				shallUpdate = false;
			}
		}
		
		if(shallUpdate)
		{
			cal = Calendar.getInstance();
		    cal.add(Calendar.DAY_OF_YEAR, -7);

			weeklyAnalytics = getAnalytics(cal.getTime(), yesterday);
			new GoogleAnalyticsIO().write(weeklyAnalytics, "weekly");
		}
	}
	
	public static void main(String[] args) {
		//Analytics todayAnalytics2 = GoogleAnalyticsDataFetcher.getInstance(true).getTodayAnalytics();
		//Analytics yesterdayAnalytics2 = GoogleAnalyticsDataFetcher.getInstance(true).getYesterdayAnalytics();
		Analytics weeklyAnalytics = GoogleAnalyticsDataFetcher.getInstance(false).getYesterdayAnalytics();
		int i = 0;
	}
}
