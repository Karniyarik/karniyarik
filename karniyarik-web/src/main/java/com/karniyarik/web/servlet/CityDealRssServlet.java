package com.karniyarik.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.citydeal.City;
import com.karniyarik.web.citydeals.CityDealConverter;
import com.karniyarik.web.citydeals.CityDealRSSGenerator;
import com.karniyarik.web.citydeals.CityResult;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
public class CityDealRssServlet extends HttpServlet
{
	private static final long	serialVersionUID	= 8407146295211650016L;
	String cityName;
	int maxNumberOfDeals;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		CityResult cityRes = null;
		CityDealConverter cityDealConverter = new CityDealConverter(Integer.MAX_VALUE);
		cityName = req.getParameter("city");
		if(req.getParameter("max") != null)
		{
			maxNumberOfDeals = Integer.parseInt(req.getParameter("max"));
		}
		else
		{
			maxNumberOfDeals = Integer.MAX_VALUE;
		}
		if(StringUtils.isNotBlank(cityName))
		{
			cityName = City.getValue(cityName); 
		}
		
		List<CityResult> cities = cityDealConverter.getCities();
		for(CityResult result : cities)
		{
			if(result.getValue().equalsIgnoreCase(cityName))
			{	
				cityRes = result;
			}
		}
		
		if(cityRes != null)
		{
			SyndFeed feed = new CityDealRSSGenerator().generateCityRSS(cityRes,maxNumberOfDeals);
			SyndFeedOutput output = new SyndFeedOutput();
			try
			{
				resp.setContentType("text/xml;charset=utf-8");
				output.output(feed, resp.getWriter());
			}
			catch (FeedException e)
			{
				resp.getWriter().append("Feed can not be generated");
			}		
		}
		else
		{
			resp.getWriter().append("City can not be found");
		}
	}

}
