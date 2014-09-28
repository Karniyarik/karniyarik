package com.karniyarik.web.remote;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.salt.FixedStringSaltGenerator;
import org.json.JSONArray;
import org.json.JSONException;

import twitter4j.org.json.JSONObject;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.statistics.vo.ProductClickLog;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.search.EngineRepository;
import com.karniyarik.search.searcher.ISearcher;
import com.karniyarik.web.bendeistiyorum.DailyOpportunityLoader;
import com.karniyarik.web.citydeals.CityDealConverter;
import com.karniyarik.web.json.LinkedLabel;
import com.karniyarik.web.json.ProductResult;
import com.karniyarik.web.json.SearchResult;
import com.karniyarik.web.json.SearchStatisticsManager;
import com.karniyarik.web.util.RequestWrapper;
import com.karniyarik.web.util.SearchResultRequestWrapper;
import com.sun.jersey.api.json.JSONWithPadding;

@Path("/")
public class SearchService
{
	@Context
	HttpServletRequest	servletRequest;
	
	@Context
	HttpServletResponse servletResponse;

	@GET
	@Path("js/{searchType}")
	@Produces("application/x-javascript;charset=UTF-8")
	public JSONWithPadding searchJavaScript(
			@QueryParam("q") @DefaultValue("") String query, 
			@QueryParam("s") @DefaultValue("1") int sort, 
			@QueryParam("ps") @DefaultValue("10") int pagesize,
			@QueryParam("p") @DefaultValue("1") int page, 
			@QueryParam("src") @DefaultValue("") String sources, 
			@QueryParam("brand") @DefaultValue("") String brands,
			@QueryParam("price") @DefaultValue("") String prices,
			@QueryParam("evol") @DefaultValue("") String enginevolume,
			@QueryParam("epow") @DefaultValue("") String enginepower,
			@QueryParam("year") @DefaultValue("") String year,
			@QueryParam("km") @DefaultValue("") String km,
			@QueryParam("city") @DefaultValue("") String city,
			@QueryParam("fuel") @DefaultValue("") String fuel,
			@QueryParam("gear") @DefaultValue("") String gear,
			@QueryParam("color") @DefaultValue("") String color,			
			@QueryParam("key") @DefaultValue("") String key,
			@QueryParam("v") @DefaultValue("") String version,
			@QueryParam("callback") @DefaultValue("") String callback,
			@PathParam("searchType") @DefaultValue("") String searchType)
	{
		ResultVO result = search(version, query, sort, pagesize, page, sources, brands, prices, key, searchType, 
				enginevolume, enginepower, year, km, city, fuel, gear, color, false, true);
		
		return new JSONWithPadding(result, callback);
	}

	
	@GET
	@Path("json/{searchType}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public ResultVO searchJSON(
			@QueryParam("q") @DefaultValue("") String query, 
			@QueryParam("s") @DefaultValue("1") int sort, 
			@QueryParam("ps") @DefaultValue("10") int pagesize,
			@QueryParam("p") @DefaultValue("1") int page, 
			@QueryParam("src") @DefaultValue("") String sources, 
			@QueryParam("brand") @DefaultValue("") String brands,
			@QueryParam("price") @DefaultValue("") String prices,
			@QueryParam("evol") @DefaultValue("") String enginevolume,
			@QueryParam("epow") @DefaultValue("") String enginepower,
			@QueryParam("year") @DefaultValue("") String year,
			@QueryParam("km") @DefaultValue("") String km,
			@QueryParam("city") @DefaultValue("") String city,
			@QueryParam("fuel") @DefaultValue("") String fuel,
			@QueryParam("gear") @DefaultValue("") String gear,
			@QueryParam("color") @DefaultValue("") String color,			
			@QueryParam("key") @DefaultValue("") String key,
			@QueryParam("v") @DefaultValue("") String version,
			@QueryParam("r") @DefaultValue("false") boolean random,
			@PathParam("searchType") @DefaultValue("") String searchType, 
			@QueryParam("f") @DefaultValue("true") boolean returnFilters)
	{
		return search(version, query, sort, pagesize, page, sources, brands, prices, key, searchType, 
				enginevolume, enginepower, year, km, city, fuel, gear, color, random, returnFilters);
	}

	@GET
	@Path("xml/{searchType}")
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	public ResultVO searchXML(
			@QueryParam("q") @DefaultValue("") String query, 
			@QueryParam("s") @DefaultValue("1") int sort, 
			@QueryParam("ps") @DefaultValue("10") int pagesize,
			@QueryParam("p") @DefaultValue("1") int page, 
			@QueryParam("src") @DefaultValue("") String sources, 
			@QueryParam("brand") @DefaultValue("") String brands,
			@QueryParam("price") @DefaultValue("") String prices,
			@QueryParam("evol") @DefaultValue("") String enginevolume,
			@QueryParam("epow") @DefaultValue("") String enginepower,
			@QueryParam("year") @DefaultValue("") String year,
			@QueryParam("km") @DefaultValue("") String km,
			@QueryParam("city") @DefaultValue("") String city,
			@QueryParam("fuel") @DefaultValue("") String fuel,
			@QueryParam("gear") @DefaultValue("") String gear,
			@QueryParam("color") @DefaultValue("") String color,
			@QueryParam("key") @DefaultValue("") String key,
			@QueryParam("v") @DefaultValue("") String version,
			@QueryParam("r") @DefaultValue("false") boolean random,
			@PathParam("searchType") @DefaultValue("") String searchType,
			@QueryParam("f") @DefaultValue("true") boolean returnFilters)
	{
		return search(version, query, sort, pagesize, page, sources, brands, prices, key, searchType, 
				enginevolume, enginepower, year, km, city, fuel, gear, color, random, returnFilters);
	}

	@GET
	@Path("dailyProduct")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public ResultVO dailyProduct()
	{
		Product p = DailyOpportunityLoader.getInstance().getDailyProduct();
		
		ResultVO resultVO = new ResultVO(new ProductResult(p, servletRequest.getContextPath(), ""));

		return resultVO;
	}
	
	@GET
	@Path("xml/citydeal/cities")
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	public CityListVO getCityDealsCitiesXML(@QueryParam("key") @DefaultValue("") String key)
	{
		return getCityDealsCities(key);
	}

	@GET
	@Path("json/citydeal/cities")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public CityListVO getCityDealsCitiesJSON(@QueryParam("key") @DefaultValue("") String key)
	{
		return getCityDealsCities(key);
	}

	private CityListVO getCityDealsCities(String key)
	{
		boolean authorized = ServiceUsage.getInstance().newRequest(key);
		if(!authorized)
		{
			CityListVO vo = new CityListVO();
			return vo;
		}

		CityDealConverter converter = new CityDealConverter(Integer.MAX_VALUE);
		CityListVO vo = new CityListVO(converter.getActiveCities());
		return vo;
	}

	@GET
	@Path("xml/citydeal")
	@Produces(MediaType.APPLICATION_XML + ";charset=UTF-8")
	public CityDealResultVO getCityDealsXML(@QueryParam("c") @DefaultValue("") String city, @QueryParam("key") @DefaultValue("") String key)
	{
		return getCityDeals(city, key);
	}
	
	@GET
	@Path("json/citydeal")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public CityDealResultVO getCityDealsJSON(@QueryParam("c") @DefaultValue("") String city, @QueryParam("key") @DefaultValue("") String key)
	{
		return getCityDeals(city,key);
	}
	
	public CityDealResultVO getCityDeals(String city, String key)
	{
		boolean authorized = ServiceUsage.getInstance().newRequest(key);
		if(!authorized)
		{
			CityDealResultVO vo = new CityDealResultVO();
			return vo;
		}

		CityDealConverter converter = new CityDealConverter(city,Integer.MAX_VALUE, CityDealConverter.SORT_DEFAULT, false);
		CityDealResultVO vo = new CityDealResultVO(converter.getCityDeals(), city);
		return vo;
	}
	
	@GET
	@Path("r")
	public Response redirectProduct(@QueryParam("p") @DefaultValue("") String parameter)
	{
		Response r = null;
		if (StringUtils.isNotBlank(parameter)) {
			StandardPBEStringEncryptor enc = createEncriptor();
			try
			{
				parameter = enc.decrypt(parameter);
				JSONObject json = new JSONObject(parameter);
				ProductClickLog log = new ProductClickLog();
				log.setName(json.getString("n"));
				log.setSiteName(json.getString("src"));
				log.setUrl(json.getString("u"));
				log.setQuery(json.getString("q"));
				if (json.getInt("t") == 1) {
					log.setSponsor(true);
				} else {
					log.setSponsor(false);
				}
				log.setIp(servletRequest.getRemoteAddr());
				log.setApiKey(json.getString("key"));

				StatisticsWebServiceUtil.sendProductHit(log);
				
				r = Response.seeOther(new URI(log.getUrl())).build();
			}
			catch (Throwable e)
			{
				r = Response.serverError().build();
			}
		} else {
			r = Response.serverError().build();
		}
		return r;
	}

	@GET
	@Path("autocomplete")
	@Produces(MediaType.TEXT_PLAIN + ";charset=UTF-8")
	public String autocomplete(
			@QueryParam("term") @DefaultValue("") String query,
			@QueryParam("c") @DefaultValue("1") int category,
			@QueryParam("os") @DefaultValue("false") boolean opensearch
			)
	{
		
		String categoryString = CategorizerConfig.getCategoryString(category);
		ISearcher categorySearcher = EngineRepository.getInstance().getCategorySearcher(categoryString);
		List<String> autocomplete = categorySearcher.autocomplete(query);
//		AutoCompleteVO autoCompleteVO = new AutoCompleteVO();
//		autoCompleteVO.setQueries(autocomplete);
		String string="";
		try {
			JSONArray jsonArray = new JSONArray();
			for(String str: autocomplete)
			{
				if(!opensearch)
				{
					org.json.JSONObject jsonObj = new org.json.JSONObject();
					jsonObj.put("value", str);
					jsonArray.put(jsonObj);					
				}
				else
				{
					jsonArray.put(str);		
				}
			}
			string = jsonArray.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return string;
	}

	
	private ResultVO search(String version, String query, int sort, int pagesize, int page, 
			String sources, String brands, String prices, String key, String searchType, 
			String enginevolume, String enginepower, String year, String km, 
			String city, String fuel, String gear, String color, boolean random, boolean returnFilters)
	{
		boolean authorized = ServiceUsage.getInstance().newRequest(key);
		
		if(!authorized)
		{
			ResultVO vo = new ResultVO();
			vo.setError("Daily count reached or not a valid api key");			
			return vo;
		}

		StringBuffer link = new StringBuffer("http://www.karniyarik.com/");
		
		int catType = CategorizerConfig.PRODUCT_TYPE;
		
		if(searchType.equals("product"))
		{
			catType = CategorizerConfig.PRODUCT_TYPE;
			servletRequest.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.PRODUCT);
			link.append("urun/");
		}
		else if(searchType.equals("car"))
		{
			catType = CategorizerConfig.CAR_TYPE;
			servletRequest.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.CAR);
			link.append("araba/");
		}
		else if(searchType.equals("otel"))
		{
			catType = CategorizerConfig.HOTEL_TYPE;
			servletRequest.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.HOTEL);
			link.append("otel/");
		}
		else
		{
			throw new KarniyarikAPIException("Unknown search type");
		}

		if(random)
		{
			List<LinkedLabel> lastSearches = SearchStatisticsManager.getInstance().getLastSearches(catType);
			int randomInt = 0;
			if(lastSearches.size() > 0)
			{
				randomInt = new Random(System.currentTimeMillis()).nextInt(lastSearches.size());
				query = StringEscapeUtils.unescapeHtml(lastSearches.get(randomInt).getLabel());
			}
			else
			{
				query = "karnıyarık tenceresi";
			}
			
		}

		Locale.setDefault(new Locale("tr"));		

		if (pagesize > 10)
		{
			pagesize = 10;
		}

		// WARNING: Introduces Tomcat dependency
		//ParameterMap parameterMap = (ParameterMap) servletRequest.getParameterMap();
		//parameterMap.setLocked(false);
		Map parameterMap =  servletRequest.getParameterMap();

		try
		{
			Method declaredMethod = parameterMap.getClass().getDeclaredMethod("setLocked", boolean.class);
			declaredMethod.invoke(parameterMap, false);
		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addParameter(parameterMap, "query", query, null);
		link.append("search.jsp?query=");
		link.append(query);
		
		appendFilter(prices, "price1", "price2", link, parameterMap);
		appendFilter(enginepower, "enginepower1", "enginepower2", link, parameterMap);
		appendFilter(enginevolume, "enginevolume1", "enginevolume2", link, parameterMap);
		appendFilter(km, "km1", "km2", link, parameterMap);
		appendFilter(year, "modelyear1", "modelyear2", link, parameterMap);
		
		addParameter(parameterMap, "sort", sort, link);
		addParameter(parameterMap, "source", sources, link);
		addParameter(parameterMap, "brand", brands, link);
		addParameter(parameterMap, "page", page, link);
		addParameter(parameterMap, "psize", pagesize, link);
		
		addParameter(parameterMap, "city", city, link);
		addParameter(parameterMap, "fuel", fuel, link);
		addParameter(parameterMap, "gear", gear, link);
		addParameter(parameterMap, "color", color, link);
		// save the link before adding api key
		// this link will be posted inside the results
		String linkWithoutApiKey = link.toString();
		addParameter(parameterMap, "apiKey", key, link);

		parameterMap.remove("q");
		parameterMap.remove("s");
		parameterMap.remove("ps");
		parameterMap.remove("p");
		parameterMap.remove("src");
		parameterMap.remove("brand");
		parameterMap.remove("price");			
		parameterMap.remove("epow");
		parameterMap.remove("evol");
		parameterMap.remove("km");
		parameterMap.remove("year");
		parameterMap.remove("key");
		
		SearchResult searchResult = SearchResultRequestWrapper.getInstance(servletRequest).getSearchResult();

		ResultVO result = new ResultVO(searchResult, linkWithoutApiKey); 
		
		//if (StringUtils.isNotBlank(version) && version.equals("1.1")) {
			String productUrl = null;
			StandardPBEStringEncryptor enc = createEncriptor();
			URL u = null;
			String parameter = null;
			try
			{
				for (ProductVO productVO : result.getProducts())
				{
					productUrl = StringEscapeUtils.unescapeHtml(productVO.getUrl());
					parameter = createProductUrlJson(productUrl, query, productVO.getSource(), productVO.getName(), false, key); 
					parameter = enc.encrypt(parameter);
					u = new URL(servletRequest.getRequestURL().toString());
					u = new URL(u, servletRequest.getContextPath() + "/rest/r?p=" + parameter);
					productVO.setUrl(u.toString());
					if(!key.equals("E800975E64488DBBE2E1814AF0C284BE"))
					{
						String imgurl = StringEscapeUtils.unescapeHtml(productVO.getImageURL());
						try {
							imgurl = URIUtil.decode(imgurl, StringUtil.DEFAULT_ENCODING);
						} catch (URIException e) {
							int a = 9;
						}
						productVO.setImageURL(imgurl);
					}
				}
			}
			catch (MalformedURLException e)
			{
				// will not happen
			}
			
		//}
		
		if(!returnFilters)
		{
			result.getFilters().clear();
		}
		
		return result;
	}

	private void appendFilter(String value, String name1, String name2, StringBuffer link, Map parameterMap)
	{
		if (StringUtils.isNotBlank(value))
		{
			String[] valueArr = value.split(",");
			
			String value1 = null;
			String value2 = null;
			if(valueArr.length > 0)
			{
				value1 = valueArr[0];
			}
			if(valueArr.length > 1)
			{
				value2 = valueArr[1];
			}
			
			if (StringUtils.isNotEmpty(value1) && StringUtils.isEmpty(value2))
			{
				addParameter(parameterMap, name1, value1, link);
			}
			else if (StringUtils.isEmpty(value1) && StringUtils.isNotEmpty(value2))
			{
				addParameter(parameterMap, name2, value2, link);
			}
			else if (StringUtils.isNotEmpty(value1) && StringUtils.isNotEmpty(value2))
			{
				addParameter(parameterMap, name1, value1, link);
				addParameter(parameterMap, name2, value2, link);
			}
		}
	}
	
	private void addParameter(Map parameterMap, String name, Object value, StringBuffer linkBuff)
	{
		if(value != null)
		{
			String valueStr = value.toString();
			if(StringUtils.isNotBlank(valueStr))
			{
				parameterMap.put(name, new String[]{valueStr});
				if(linkBuff != null)
				{
					linkBuff.append("&");
					linkBuff.append(name);
					linkBuff.append("=");
					linkBuff.append(valueStr.trim());
				}
			}			
		}
	}
	
	private StandardPBEStringEncryptor createEncriptor() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		// do not enter hexadecimal
		// deep inside to upper case is called
		// which turns to HEXADECİMAL depending on the locale
		// and it does not macth HEXADECIMAL
		encryptor.setStringOutputType("hex");
		encryptor.setPassword("");
		FixedStringSaltGenerator generator = new FixedStringSaltGenerator();
		generator.setSalt("karniyarikkarniyarikkarniyarikkarniyarikkarniyarik");
		encryptor.setSaltGenerator(generator);
		return encryptor;
	}
	
	private String createProductUrlJson(String productUrl, String query, String siteName, String productName, boolean sponsor, String key) {
		JSONObject json = new JSONObject();
		try
		{
			json.put("u", productUrl);
			json.put("q", query);
			json.put("src", siteName);
			json.put("n", productName);
			if (sponsor) {
				json.put("t", 1);
			} else {
				json.put("t", 0);
			}
			json.put("key", key);
		}
		catch (Throwable e)
		{
			// will not happen
		}
		return json.toString();
	}
	
	public static void main(String[] args) throws EncoderException, DecoderException
	{
		StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
		enc.setPassword("");
		String s = enc.encrypt("");
		System.out.println(s);
		System.out.println(enc.decrypt(s));

	}
}
