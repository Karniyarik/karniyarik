package com.karniyarik.citydeal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.crawler.util.URLAnalyzer;
import com.karniyarik.recognizer.ext.CitiesInTurkeyRegistry;

import edu.emory.mathcs.backport.java.util.Collections;

public class CityDealProvider
{

	private static CityDealProvider		instance		= null;

	private List<City>					cities			= new ArrayList<City>();
	private Map<String, List<CityDeal>>	cityDealMap		= new HashMap<String, List<CityDeal>>();
	private List<CityDeal>				allDeals		= new ArrayList<CityDeal>();
	private List<City>					activeCities	= new ArrayList<City>();
	private Map<String, City>			cityMap			= new HashMap<String, City>();
	private Map<Integer, CityDeal>		cityDealIDMap	= new HashMap<Integer, CityDeal>();

	
	private FirsatClubDataFetcher		dataFetcher		= null;
	private final ReadWriteLock			lock;

	private CityDealProvider()
	{
		lock = new ReentrantReadWriteLock();
		loadCities();
		loadFromPersistentStorage();
	}


	private void loadCities() {
		List<String> cityList = CitiesInTurkeyRegistry.getInstance().getCities();
		
		for(String cityStr: cityList)
		{
			City city = new City();
			city.setName(cityStr);
			cities.add(city);
			cityMap.put(city.getValue(), city);
		}
	}


	private void loadFromPersistentStorage() {
		CityDealList readDeals = new CityDealIO().readDeals();
		if(readDeals == null)
		{
			//wait the scheduled update operation
			//update();
		}
		else
		{
			contructLists(readDeals.getCityDeals(), false);
		}
	}


	public static CityDealProvider getInstance()
	{
		if (instance == null)
		{
			instance = new CityDealProvider();
		}
		return instance;
	}
	
	public List<CityDeal> getAllDeals()
	{
		return allDeals;
	}

	public List<City> getCities()
	{
		lock.readLock().lock();
		List<City> cities = this.cities;		
		lock.readLock().unlock();

		return cities;
	}

	public City getCity(String value)
	{
		lock.readLock().lock();
		City city = cityMap.get(value);
		lock.readLock().unlock();

		return city;
	}

	public List<CityDeal> getDealList(String cityValue)
	{
		lock.readLock().lock();
		List<CityDeal> list = cityDealMap.get(cityValue);
		if (list == null)
		{
			list = new ArrayList<CityDeal>();
		}
		
		List<CityDeal> result = new ArrayList<CityDeal>();
		
		Date curDate = new Date();
		for(CityDeal deal: list)
		{
			if(deal.getEndDate().getTime() >= curDate.getTime())
			{
				result.add(deal);	
			}
		}
		
		lock.readLock().unlock();
		
		return result;
	}

	public List<CityDeal> getExpiredDealList(String cityValue)
	{
		lock.readLock().lock();
		List<CityDeal> list = cityDealMap.get(cityValue);
		if (list == null)
		{
			list = new ArrayList<CityDeal>();
		}
		
		List<CityDeal> result = new ArrayList<CityDeal>();
		
		Date curDate = new Date();
		for(CityDeal deal: list)
		{
			if(deal.getEndDate().getTime() < curDate.getTime())
			{
				result.add(deal);	
			}
		}
		
		lock.readLock().unlock();
		
		return result;
	}

	public List<City> getActiveCities()
	{
		lock.readLock().lock();
		List<City> activeCities = this.activeCities;
		lock.readLock().unlock();

		return activeCities;
	}
	
	public CityDeal getDeal(int id)
	{
		lock.readLock().lock();
		CityDeal cityDeal = cityDealIDMap.get(id);
		lock.readLock().unlock();
		return cityDeal;
	}

	private void contructLists(List<CityDeal> deals, boolean setDealParameters) {
		lock.writeLock().lock();
		
		try{
			if (deals != null && deals.size() > 0)
			{
				Date curDate = new Date();
				long threshold = 1000*60*60*24;
				
				List<CityDeal> newAllDeals = new ArrayList<CityDeal>();
				
				for(CityDeal deal: allDeals)
				{
					Date date = deal.getEndDate();
					if(curDate.getTime() - date.getTime() < threshold)
					{
						newAllDeals.add(deal);
					}
					else
					{
						cityDealIDMap.remove(deal.getId());
					}
				}
				
				
				//cities.clear();
				//cityMap.clear();
				allDeals.clear();
				cityDealIDMap.clear();
				activeCities.clear();
				cityDealMap.clear();
				
//				for(City city: cities)
//				{
//					cityMap.put(city.getValue(), city);
//				}
				
				insertDeals(newAllDeals, false);
				insertDeals(deals, setDealParameters);

				for (City city : cities)
				{
					List<CityDeal> list = cityDealMap.get(city.getValue());
					if (list != null)
					{
						Collections.sort(list, new CityDealEndTimeComparator());
						city.setDealCount(list.size());
						activeCities.add(city);
					}
					else
					{
						city.setDealCount(0);
					}
						
				}

				Collections.sort(activeCities, new CityDealCountComparator());
				Collections.sort(cities, new CityNameComparator());
			}
		}
		finally
		{
			lock.writeLock().unlock();	
		}
	}


	private void insertDeals(List<CityDeal> deals, boolean setDealParameters)
	{
		for (CityDeal deal : deals)
		{
			List<CityDeal> cityDeals = checkCityIfNotExistsCreate(deal);

			if(setDealParameters){
				deal.setId((deal.getCity() +deal.getDescription().toString()).hashCode());

				String url = deal.getProductURL();
				deal.setProductURL(processURL(url));

				String imgUrl = deal.getImage();
				deal.setImage(processImgURL(imgUrl));
				
				double percentage = 0;
				if (deal.getPrice() != null || deal.getPrice() != 0)
				{
					percentage = 100 - (deal.getDiscountPrice() / deal.getPrice() * 100d);
				}

				deal.setDiscountPercentage(percentage);
			}

			deal.setPaid(isPaid(deal.getProductURL()));
			
			CityDeal oldCityDeal = cityDealIDMap.get(deal.getId());
			if(deal.getSource().equalsIgnoreCase("limango"))
			{}
			//if not inserted before
			else //if(oldCityDeal == null || deal.getEndDate().getTime() > oldCityDeal.getEndDate().getTime())
			{
				if(oldCityDeal != null)
				{
					cityDeals.remove(oldCityDeal);
					allDeals.remove(oldCityDeal);
				}
				
				cityDeals.add(deal);
				allDeals.add(deal);
				cityDealIDMap.put(deal.getId(), deal);
			}
		}
	}


	private List<CityDeal> checkCityIfNotExistsCreate(CityDeal deal)
	{
		String recognizedCity = CitiesInTurkeyRegistry.getInstance().getCity(deal.getCity());
		if(StringUtils.isBlank(recognizedCity))
		{
			recognizedCity = deal.getCity();
		}
		
		List<CityDeal> cityDeals = cityDealMap.get(City.getValue(recognizedCity));

		if (cityDeals == null)
		{
			cityDeals = new ArrayList<CityDeal>();
			City city = cityMap.get(City.getValue(deal.getCity()));
			
			if(city == null)
			{
				city = new City();
				city.setName(deal.getCity());
				cityMap.put(city.getValue(), city);
				cities.add(city);
			}
			
			cityDealMap.put(city.getValue(), cityDeals);
		}
		return cityDeals;
	}

	private String processURL(String url)
	{
		url = url.replaceFirst("aff_id=(\\d+)", "aff_id=239");
		url = url.replaceFirst("source=(\\w+)", "source=web");

		return url;
	}

	private boolean isPaid(String url)
	{
		Map<String, String> queryParameters = new URLAnalyzer().getQueryParameters(url);
		
		String offerid = queryParameters.get("offer_id");
		if(StringUtils.isNotBlank(offerid))
		{
			int offidInt = Integer.parseInt(offerid);
			if(offidInt > 0)
			{
				return true;
			}
		}
		return false;
	}

	
	private String processImgURL(String url)
	{
		url = url.replaceFirst("w=(\\d+)", "w=180");
		url = url.replaceFirst("h=(\\d+)", "h=140");

		return url;
	}

	public void update()
	{
		try
		{
//			String[] ignored = new String[]{"sehirfirsati", "aktifkampanya", "firmanya", "grupca", "markapon", "piriveta", "grupanya"};
//			List<String> ignoredSources = Arrays.asList(ignored);
//			
//			dataFetcher = new FirsatClubDataFetcher(ignoredSources);
			CityDealXMLCollector xmlFetcher = new CityDealXMLCollector();
			
			List<CityDeal> xmlDeals = xmlFetcher.getDeals();
			//List<CityDeal> crawledDeals = dataFetcher.fetchData();
			//xmlDeals.addAll(crawledDeals);

			contructLists(xmlDeals, true);
			
			CityDealList storage = new CityDealList();
			storage.setCities(cities);
			storage.setCityDeals(allDeals);
			new CityDealIO().writeDeals(storage);

		}
		catch (Exception e)
		{
		}
	}

	public static void main(String[] args)
	{
		CityDealProvider.getInstance().getCities();
		CityDealProvider.getInstance().update();
		List<CityDeal> dealList = CityDealProvider.getInstance().getDealList("ankara");
		
		int a = 8;
	}
}
