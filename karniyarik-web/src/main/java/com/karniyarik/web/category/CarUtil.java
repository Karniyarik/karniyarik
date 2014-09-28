package com.karniyarik.web.category;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StringUtil;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.web.json.LinkedLabel;
import com.karniyarik.web.json.ProductResult;
import com.karniyarik.web.json.SearchResult;
import com.karniyarik.web.util.QueryStringHelper;
import com.karniyarik.web.util.RequestWrapper;

public class CarUtil extends BaseCategoryUtil
{
	public List<LinkedLabel> getActiveFilters(HttpServletRequest request, boolean useStrong)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();
		
		String[][] filters = new String[][]{
				new String[]{"Fiyat", getParam("price1", "price2", request), "price1,price2"},
				new String[]{"Marka", getParam("brand", request), "brand"},
				new String[]{"Mağaza", getParam("source", request), "source"},
				new String[]{"Şehir", getParam("city", request), "city"}, 
				new String[]{"Renk", getParam("color", request), "color"}, 
				new String[]{"Yakıt Tipi", getParam("fuel", request), "fuel"},
				new String[]{"Kilometre", getParam("km1", "km2", request), "km1,km2"},
				new String[]{"Motor Gücü", getParam("enginepower1", "enginepower2", request), "enginepower1,enginepower2"},
				new String[]{"Motor Hacmi", getParam("enginevolume1","enginevolume2", request),  "enginevolume1,enginevolume2"},
				new String[]{"Vites Tipi", getParam("gear", request), "gear"},
				new String[]{"Yılı", getParam("modelyear1", "modelyear2", request), "modelyear1,modelyear2"}};
	
		fillFilterLabels(useStrong, result, filters);
		return result;
	}
	

	public List<LinkedLabel> getCity(SearchResult searchResult)
	{
		return getList(searchResult, "city");
	}

	public List<LinkedLabel> getBrand(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.BRAND);
	}

	public List<LinkedLabel> getStore(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.STORE);
	}

	public List<LinkedLabel> getColor(SearchResult searchResult)
	{
		return getList(searchResult, "color");
	}

	public List<LinkedLabel> getFuel(SearchResult searchResult)
	{
		return getList(searchResult, "fuel");
	}
	
	public List<LinkedLabel> getKm(SearchResult searchResult)
	{
		return getList(searchResult, "km");
	}

	public List<LinkedLabel> getPower(SearchResult searchResult)
	{
		return getList(searchResult, "enginepower");
	}
	
	public List<LinkedLabel> getVolume(SearchResult searchResult)
	{
		return getList(searchResult, "enginevolume");
	}

	public List<LinkedLabel> getGear(SearchResult searchResult)
	{
		return getList(searchResult, "gear");
	}

	public List<LinkedLabel> getYear(SearchResult searchResult, HttpServletRequest request)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();

		LinkedLabel tmp = null;

		int yearEnd = Calendar.getInstance().get(Calendar.YEAR);
		int yearBegin = yearEnd - 50;

		tmp = new LinkedLabel("", "", yearBegin);
		result.add(tmp);
		tmp = new LinkedLabel("", "", yearEnd);
		result.add(tmp);
		
		String val1 = getParam(SearchConstants.YEAR+1, request);
		String val2 = getParam(SearchConstants.YEAR+2, request);
		
		int selectedMin = yearBegin;
		int selectedMax = yearEnd;
		
		if(StringUtils.isNotBlank(val1))
		{
			selectedMin = parseIntegerValue(val1);
		}

		tmp = new LinkedLabel("", "", selectedMin);
		result.add(tmp);
		
		if(StringUtils.isNotBlank(val2))
		{
			selectedMax = parseIntegerValue(val2);
		}
		tmp = new LinkedLabel("", "",selectedMax );
		result.add(tmp);
		
		return result;
	}
	
	public List<LinkedLabel> getEngineVolume(SearchResult searchResult, HttpServletRequest request)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();

		LinkedLabel tmp = null;

		tmp = new LinkedLabel("", "", 250);
		result.add(tmp);
		tmp = new LinkedLabel("", "", 10000);
		result.add(tmp);
		
		String val1 = getParam(SearchConstants.VOLUME+1, request);
		String val2 = getParam(SearchConstants.VOLUME+2, request);
		
		int selectedMin = 250;
		int selectedMax = 10000;
		
		if(StringUtils.isNotBlank(val1))
		{
			selectedMin = parseIntegerValue(val1);
		}

		tmp = new LinkedLabel("", "", selectedMin);
		result.add(tmp);
		
		if(StringUtils.isNotBlank(val2))
		{
			selectedMax = parseIntegerValue(val2);
		}
		tmp = new LinkedLabel("", "",selectedMax );
		result.add(tmp);
		
		return result;
	}
	
	public List<LinkedLabel> getEnginePower(SearchResult searchResult, HttpServletRequest request)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();

		LinkedLabel tmp = null;

		tmp = new LinkedLabel("", "", 25);
		result.add(tmp);
		tmp = new LinkedLabel("", "", 1000);
		result.add(tmp);
		
		String val1 = getParam(SearchConstants.HP+1, request);
		String val2 = getParam(SearchConstants.HP+2, request);
		
		int selectedMin = 25;
		int selectedMax = 1000;
		
		if(StringUtils.isNotBlank(val1))
		{
			selectedMin = parseIntegerValue(val1);
		}

		tmp = new LinkedLabel("", "", selectedMin);
		result.add(tmp);
		
		if(StringUtils.isNotBlank(val2))
		{
			selectedMax = parseIntegerValue(val2);
		}
		tmp = new LinkedLabel("", "",selectedMax );
		result.add(tmp);
		
		return result;
	}
	
	public String getSingleProductPageDescription(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		
		buff.append(result.getProductName());
		appendBrand(result, buff);
		appendProperty(result, buff, "city");
		appendProperty(result, buff, "modelyear");
		appendPrice(result, buff);
		
		int totalSize = buff.length();
		int remainingSize = 140-totalSize;
		if(products.size() > 0 & remainingSize > 10)
		{
			buff.append(" ve ");	
			appendProductResultNames(buff, products, remainingSize, false);
		}
		buff.append("ikinci el araçlar.");
		return buff.toString();		
	}

	public String getSingleProductPageKeywords(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		appendBrand(result, buff);
		appendProperty(result, buff, "city");
		appendProperty(result, buff, "modelyear");
		buff.append(", ");
		buff.append(result.getSourceName());
		buff.append(", ");
		appendProductResultNames(buff, products, MAX_KEYWORDS, false);
		buff.append(" ikinci el araba, ikinci el, ikinci el araç");
		return buff.toString();
	}

	public String getSingleProductPageTitle(ProductResult result, List<ProductResult> products){
		return getSingleProductPageHeader(result, products);
	}
	
	public String getSingleProductPageHeader(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		appendBrand(result, buff);
		appendProperty(result, buff, "city");
		appendProperty(result, buff, "modelyear");
		appendPrice(result, buff);
		return buff.toString();
	}


	@Override
	public String getPageTitle(String query, List<LinkedLabel> activeFilters,int page, List<ProductResult> list) {
		StringBuffer title = new StringBuffer();		
		title.append(StringUtil.capitilizeFirst(query));
		appendFiltersToTitle(title, activeFilters, new String[]{"brand", "city"});
		
		title.append(" fiyatları ");
		appendPageNumberToTitle(title, page);
		title.append(" - ");
		
		if(list != null && list.size()>0)
		{
			title.append("En uygun fiyatlarla istediğiniz ikinci el aracı arayın, karşılaştırın, satın alın!");
		}
		else
		{
			title.append("Sonuç Bulunamadı");
		}
				
		return title.toString();
	}
	
	public String getPageKeywords(String query,
			List<LinkedLabel> activeFilters, int page,
			List<ProductResult> list, long productCount, int siteCount) {
		
		StringBuffer desc = new StringBuffer();
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(", ");
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(" fiyatları, ikinci el araba, ikinci el, sahibinden, ");
		
		if(productCount > 0)
		{
			appendProductResultNames(desc, list, MAX_KEYWORDS, false);
		}
		
		if(activeFilters != null && activeFilters.size() > 0)
		{
			//desc.append(", ");
			appendFiltersToTitle(desc, activeFilters, new String[]{"brand", "city"});
		}
		
		return desc.toString();
	}
	
	@Override
	public String getPageDescription(String query, List<LinkedLabel> activeFilters, int page, SearchResult result) {
		long productCount = result.getTotalHits();
		int siteCount = result.getSiteCount();
		
		List<ProductResult> list = result.getResults();
		
		StringBuffer desc = new StringBuffer();
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(" Fiyatları");
		
		if(siteCount > 0 && productCount > 0)
		{
			desc.append(" - ");
			desc.append(productCount);
			desc.append(" sonuç ");
			
			if(productCount > 0 )
			{
				desc.append("arasında ");
				appendProductResultNames(desc, list, MAX_DESCRIPTION, true);
			}
			
			if(activeFilters != null && activeFilters.size() > 0)
			{
				desc.append(", ");
				appendFiltersToTitle(desc, activeFilters);
			}
		}				
		else
		{
			desc.append(" fiyatları sorgusu için şu an için hiç bir sonuç bulunamadı. En uygun, ucuz fiyatlarla ikinci el ve sahibinden ");
			desc.append(StringUtil.capitilizeFirst(query));
			desc.append(" arayın, karşılaştırın, satın alın!");
			if(activeFilters != null && activeFilters.size() > 0)
			{
				appendFiltersToTitle(desc, activeFilters);
			}
		}
		
		appendPageNumberToTitle(desc, page);

		return desc.toString();
	}
	
	
	@Override
	public String getPageHeader(String query, List<LinkedLabel> activeFilters, int page, List<ProductResult> list) {
		StringBuffer title = new StringBuffer();		
		title.append(StringUtil.capitilizeFirst(query));
		//appendFiltersToTitle(title, activeFilters);
		title.append(" fiyatları");
		return title.toString();
	}
	
	@Override
	public List<LinkedLabel> getBreadCrumb(SearchResult result, RequestWrapper wrapper, List<LinkedLabel> activeFilters)
	{
		List<LinkedLabel> crumb = new ArrayList<LinkedLabel>();

		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
		appendHomePageToBreadCrumb(wrapper.getRequest(), crumb);
		appendCategoryToBreadCrumb(wrapper.getRequest(), crumb, "İkinci El Araç", "2");
		appendQueryToBreadCrumb(result, crumb, helper);
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "brand");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "city");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "gear");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "fuel");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "color");
		
		return crumb;
	}
	
	@Override
	public String getCanonicalURL(SearchResult result, RequestWrapper wrapper)
	{
		String brand = getParam("brand", wrapper.getRequest());
		String city = getParam("city", wrapper.getRequest());
		
		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
		if(StringUtils.isNotBlank(brand))
		{
			helper.setFilterParam("brand", brand);	
		}

		if(StringUtils.isNotBlank(city))
		{
			helper.setFilterParam("city", city);	
		}

		return helper.getRequestQuery();
	}
	
	@Override
	public String getShareDescription(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Arkadaşınız Karniyarik.com'dan bulduğu bu aracın ilginizi çekeceğini düşündü:");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendBrand(result, shareBuff);
		appendProperty(result, shareBuff, "city");
		appendProperty(result, shareBuff, "modelyear");

		String str = clearShareString(shareBuff);
		
		return str;
	}
	
	@Override
	public String getShareTitle(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Araç Tavsiyesi: ");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendBrand(result, shareBuff);
		appendProperty(result, shareBuff, "city");
		appendProperty(result, shareBuff, "modelyear");
		
		String shareName = clearShareString(shareBuff);
		
		return shareName;
	}
}
