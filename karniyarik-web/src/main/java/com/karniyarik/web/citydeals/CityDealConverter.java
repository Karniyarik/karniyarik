package com.karniyarik.web.citydeals;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.citydeal.City;
import com.karniyarik.citydeal.CityDeal;
import com.karniyarik.citydeal.CityDealDiscountAmountComparator;
import com.karniyarik.citydeal.CityDealDiscountPercentageComparator;
import com.karniyarik.citydeal.CityDealEndTimeComparator;
import com.karniyarik.citydeal.CityDealPriceComparator;
import com.karniyarik.citydeal.CityDealProvider;
import com.karniyarik.web.json.LinkedLabel;

public class CityDealConverter {
	
	public static final int SORT_PRICE = 1;
	public static final int SORT_DAMOUNT = 2;
	public static final int SORT_DPERCENTAGE = 3;
	public static final int SORT_DATE = 4;
	public static final int SORT_DEFAULT = SORT_DAMOUNT;
	
	private String cityStr = null;
	private CityResult selectedCity = null;
	private List<CityResult> cities = new ArrayList<CityResult>();
	private List<CityResult> activeCities = new ArrayList<CityResult>();
	private List<CityDealResult> cityDeals = new ArrayList<CityDealResult>();
	private HttpServletRequest request = null;
	private Integer id = null;
	private List<String> sources = new ArrayList<String>();
	private String source = null;
	private boolean expired = true; 
	private int page = 0;
	private int pageSize = 10;
	private List<LinkedLabel> pages = new ArrayList<LinkedLabel>();
	private int sortType = SORT_DPERCENTAGE;
	
	
	public CityDealConverter(HttpServletRequest request) {
		this.request = request;
		cityStr = request.getParameter("city");
		if(StringUtils.isBlank(cityStr))
		{
			String userCity = new IPGeoLookup().getCity(request);
			userCity = City.getValue(userCity);
			City city = CityDealProvider.getInstance().getCity(userCity);
			if(city != null)
			{
				cityStr = userCity;
			}
		}
		
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr))
		{
			try
			{
				id = Integer.parseInt(idStr);
			}
			catch (Throwable e)
			{
			}
		}
		
		source = request.getParameter("source");
		if(StringUtils.isNotBlank(source))
		{
			source = source.trim();
		}
		else
		{
			source = null;
		}

		String expiredStr = request.getParameter("e");
		if(StringUtils.isNotBlank(expiredStr))
		{
			expired = Boolean.valueOf(expiredStr);
		}
		
		String pageStr = request.getParameter("p");
		if(StringUtils.isNotBlank(pageStr))
		{
			try
			{
				page = Integer.valueOf(pageStr);
			}
			catch (NumberFormatException e)
			{
			}
		}
		
		String sortStr = request.getParameter("sort");
		if(StringUtils.isNotBlank(sortStr))
		{
			try
			{
				sortType = Integer.valueOf(sortStr);
			}
			catch (NumberFormatException e)
			{
			}
		}
		
		//expired = false;
		
		init();
	}

	public CityDealConverter(String cityStr, int pageSize, int sortType, boolean addExpired) {
		this.cityStr = cityStr;
		this.pageSize = pageSize;
		this.sortType = sortType;
		this.expired = addExpired;
		init();
	}

	public CityDealConverter(int pageSize) {
		this.pageSize = pageSize;		
		init();
	}
	
	private void init()
	{
		setDefaultCity();
		setCities();
		setDeals();
	}

	private void setDefaultCity() {
		if(StringUtils.isBlank(cityStr))
		{
			cityStr = "istanbul";
		}
	}
	
	private void setCities()
	{
		List<City> cityList = CityDealProvider.getInstance().getCities();
		
		for(City city: cityList)
		{
			CityResult cityResult = new CityResult(city);
			cities.add(cityResult);
			if(city.getDealCount() > 0)
			{
				activeCities.add(cityResult);
			}
			
			if(cityResult.getValue().equalsIgnoreCase(cityStr))
			{
				cityResult.setSelected(true);
				selectedCity = cityResult;
			}
		}
	}
	
//	private void setActiveCities()
//	{
//		List<City> topCityList = CityDealProvider.getInstance().getTopCities();
//		int index =0;
//		for(City city: topCityList)
//		{
//			if(index > 3) {break;}
//			topCities.add(new CityResult(city));
//			index++;
//		}
//	}
	
	private void setDeals()
	{
		Set<String> sourcesSet = new HashSet<String>();
		
		List<CityDeal> dealList = CityDealProvider.getInstance().getDealList(cityStr);
		
		if(dealList.size() < 1)
		{
			List<CityDeal> oldList = CityDealProvider.getInstance().getExpiredDealList(cityStr);
			dealList.addAll(oldList);
		}
		
		switch(sortType)
		{
			case SORT_DPERCENTAGE :
				Collections.sort(dealList, new CityDealDiscountPercentageComparator());
				break;
			case SORT_PRICE: 
				Collections.sort(dealList, new CityDealPriceComparator());
				break;
			case SORT_DATE: 
				Collections.sort(dealList, new CityDealEndTimeComparator());
				break;
			case SORT_DAMOUNT: 
			default:
				Collections.sort(dealList, new CityDealDiscountAmountComparator());				
		}
		
		int start = 0;
		int end = dealList.size();
		if(StringUtils.isBlank(source))
		{
			start = pageSize * page;
			end = pageSize * (page+1);
			if(end > dealList.size())
			{
				end=dealList.size();
			}
			
			if(this.request != null)
			{
				int pagesCount = new Double(Math.ceil(dealList.size() * 1.0 / pageSize)).intValue();

				String sort = "";
				if(sortType != SORT_DEFAULT)
				{
					sort = "?sort=" + sortType;
				}

				for(int index=0; index<pagesCount; index++)
				{
					LinkedLabel label = new LinkedLabel(Integer.toString(index+1), request.getContextPath()+"/"+selectedCity.getUrl() + "/p" + index + sort);
					label.setCssClass("");
					label.setCount(index);
					if(page == index)
					{
						label.setCssClass("act");
					}
					pages.add(label);
				}			
			}			
		}
		
		for(int index = start; index < end; index++)
		{
			CityDeal deal = dealList.get(index);

			CityDealResult cityDeal = new CityDealResult(deal, getSelectedCity().getValue());
			
			if(expired == false && cityDeal.getRemainingTime() < 1)
			{
				continue;
			}
			if(StringUtils.isNotBlank(source) )
			{
				if(cityDeal.getSource().equalsIgnoreCase(source))
				{
					cityDeals.add(cityDeal);
				}
			}
			else
			{
				if(id == null || cityDeal.getId() != id.intValue())
				{
					cityDeals.add(cityDeal);	 
				}
			}
		}
		
		for(CityDeal deal: dealList)
		{
			sourcesSet.add(deal.getSource());
		}
		
		sources.addAll(sourcesSet);
		Collections.sort(sources);
	}
	
	public List<CityResult> getCities()
	{
		return cities;
	}
	
	public List<CityResult> getActiveCities() {
		return activeCities;
	}
	
	public List<CityDealResult> getCityDeals()
	{
		return cityDeals;
	}

	public CityDealResult getSharedCityDeal()
	{
		if(id != null)
		{
			CityDeal deal = CityDealProvider.getInstance().getDeal(id);
			if(deal != null)
			{
				CityDealResult result = new CityDealResult(deal, getSelectedCity().getValue());
				return result;				
			}
		}
		
		return null;
	}

	public CityResult getSelectedCity() {
		if(selectedCity == null)
		{
			selectedCity = new CityResult();
			selectedCity.setName(cityStr);
			selectedCity.setValue(cityStr);
		}
		return selectedCity;
	}
	
	public static String getTitle(CityDealResult deal)
	{
		return getTitle(deal, 60);
	}
	
	public static String getTitle(CityDealResult deal, int titleLength)
	{
		StringBuffer title = new StringBuffer();

		title.append(deal.getPrice());
		title.append(" yerine ");
		title.append(deal.getDiscountPrice());
		title.append(" ");
		title.append(deal.getPriceCurrency());
		title.append("!, ");
		
		String descr = titleLength > 0 ? LinkedLabel.getShortenedLabel(deal.getTitle(), titleLength) : deal.getTitle();  
		title.append(descr);
		title.append(" ");
		title.append(deal.getCity());
		return title.toString();
	}

	public static String getTitle(CityDealResult deal, String currentCity, String source)
	{
		String title = null;
		
		if(deal != null){
			title = getTitle(deal); 
		} else {
			StringBuffer titleBuff = new StringBuffer();
			titleBuff.append("Şehir Fırsatı ");
			titleBuff.append(currentCity);
			titleBuff.append(" Şehir Fırsatları");
			if(StringUtils.isNotBlank(source))
			{
				titleBuff.append(" - ");
				titleBuff.append(source);
			}
			title = titleBuff.toString();
		}
		
		return StringEscapeUtils.escapeHtml(title);
	}
	
	public static String getDescription(CityDealResult deal)
	{
		StringBuffer title = new StringBuffer();
		title.append(deal.getCity());
		title.append(", ");
		title.append(deal.getDiscountPrice());
		title.append(" ");
		title.append(deal.getPriceCurrency());
		title.append(", ");
		title.append(deal.getDescription());
		return title.toString();
	}
	
	public static String getDescription(CityDealResult deal, List<CityDealResult> deals, String currentCity, String source)
	{
		String desc = null;
		
		if(deal != null){
			desc = getDescription(deal);
			StringBuffer descBuff = new StringBuffer();
			descBuff.append("Şehir Fırsatı: ");
			descBuff.append(desc);
			desc = descBuff.toString();
		} else {
			StringBuffer descBuff = new StringBuffer();
			descBuff.append(currentCity);
			if(StringUtils.isNotBlank(source))
			{
				descBuff.append(" ");
				descBuff.append(source);
			}
			descBuff.append(" şehir fırsatı listesi. Karniyarik.com ile tüm şehir fırsatları'ndan haberdar olabilirsiniz.");
			desc = descBuff.toString();
		}
		return StringEscapeUtils.escapeHtml(desc);
	}
	
	public List<String> getSources()
	{
		return sources;
	}
	
	public static String getPageImageSrc(List<CityDealResult> results, CityDealResult deal)
	{
		String image = "http://www.karniyarik.com/images/logo/karniyarik86x86.png";
		String tmpImage = null;
		if(results != null && results.size()>0)
		{
			for(CityDealResult p: results)
			{
				if(StringUtils.isNotBlank(p.getImage()))
				{
					tmpImage = p.getImage();
					break;
				}
			}	
		}
		else if(deal != null)
		{
			if(StringUtils.isNotBlank(deal.getImage()))
			{
				tmpImage = deal.getImage();
			}
		}
				
		if(StringUtils.isNotBlank(tmpImage))
		{
			tmpImage = "http://www.karniyarik.com/imgrsz/.png?w=100&h=100&v=" +tmpImage;
			image = tmpImage;
		}
		
		return image;
	}
	
	public void updateData()
	{
		CityDealProvider.getInstance().update();
	}
	
	public List<LinkedLabel> getBreadcrumb() {
		List<LinkedLabel> crumb = new ArrayList<LinkedLabel>();
		
		LinkedLabel label = new LinkedLabel("Ana Sayfa", request.getContextPath() + "/index.jsp");
		crumb.add(label);
		label = new LinkedLabel("Şehir Fırsatı", request.getContextPath() + "/sehir-firsati");
		crumb.add(label);		
		label = new LinkedLabel(selectedCity.getName(), request.getContextPath() + "/sehir-firsati/" + selectedCity.getValue());
		crumb.add(label);
		
		return crumb;
	}
	
	public String getSource()
	{
		return source;
	}
	
	public List<LinkedLabel> getPages()
	{
		return pages;
	}
	
	public int getPage()
	{
		return page;
	}
}
