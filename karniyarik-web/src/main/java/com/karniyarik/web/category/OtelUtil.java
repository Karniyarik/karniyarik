package com.karniyarik.web.category;

import java.util.ArrayList;
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

public class OtelUtil extends BaseCategoryUtil
{
	public List<LinkedLabel> getActiveFilters(HttpServletRequest request, boolean strongLabels)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();
		
		String[][] filters = new String[][]{
				new String[]{"Fiyat", getParam("price1", "price2", request),"price1,price2"},
				new String[]{"Şehir", getParam("city", request), "city"},
				new String[]{"Ülke", getParam("country", request), "country"},
				new String[]{"Site", getParam("source", request), "source"},
				new String[]{"Değerlendirme", getParam("star", request), "star"}};

		fillFilterLabels(strongLabels, result, filters);
		
		return result;
	}
	
	public List<LinkedLabel> getStore(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.STORE);
	}

	public List<LinkedLabel> getStar(SearchResult searchResult)
	{
		return getList(searchResult, "star");
	}

	public List<LinkedLabel> getPrice(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.PRICE);
	}

	public List<LinkedLabel> getCity(SearchResult searchResult)
	{
		return getList(searchResult, "city");
	}

	public List<LinkedLabel> getCountry(SearchResult searchResult)
	{
		return getList(searchResult, "country");
	}

	public String getNumberOfRooms(ProductResult product)
	{
		return product.getProperty("nr_rooms");
	}

	public int getStar(ProductResult product)
	{
		String property = product.getProperty("star");
		return getStar(property);
	}

	public int getStar(String value)
	{
		int star = 0;
		if(StringUtils.isNotBlank(value))
		{
			try{
				Double starD = Double.parseDouble(value)*2;
				star = starD.intValue();
			}
			catch(Throwable t){}
		}
		
		return star;
	}

	public String getSingleProductPageDescription(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		
		buff.append(result.getProductName());
		appendProperty(result, buff, "country");
		appendProperty(result, buff, "city");
		appendPrice(result, buff);
		
		int totalSize = buff.length();
		int remainingSize = 140-totalSize;
		if(products.size() > 0 & remainingSize > 10)
		{
			buff.append(" ve ");	
			appendProductResultNames(buff, products, remainingSize, false);
		}
		buff.append(" ve benzer oteller.");
		return buff.toString();	
	}

	public String getSingleProductPageKeywords(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		appendProperty(result, buff, "country");
		appendProperty(result, buff, "city");
		buff.append(", ");
		buff.append(result.getSourceName());
		buff.append(", ");
		appendProductResultNames(buff, products, MAX_KEYWORDS, false);
		buff.append(" tatil, otel, otel fiyatlari, otel fiyatı");
		return buff.toString();
	}

	public String getSingleProductPageTitle(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		return buff.toString();
	}
	
	public String getSingleProductPageHeader(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		appendBrand(result, buff);
		appendProperty(result, buff, "country");
		appendProperty(result, buff, "city");
		appendPrice(result, buff);
		return buff.toString();
	}
	
	public String getPageKeywords(String query,
			List<LinkedLabel> activeFilters, int page,
			List<ProductResult> list, long productCount, int siteCount) {
		
		StringBuffer desc = new StringBuffer();
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(", ");
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(" fiyatları, otel, tatil, ");
		
		if(productCount > 0)
		{
			appendProductResultNames(desc, list, MAX_KEYWORDS, false);
		}
		
		if(activeFilters != null && activeFilters.size() > 0)
		{
			//desc.append(", ");
			appendFiltersToTitle(desc, activeFilters, new String[]{"country", "city"});
		}
		
		return desc.toString();
	}
	
	@Override
	public String getPageDescription(String query, List<LinkedLabel> activeFilters, int page, SearchResult result) {
		long productCount = result.getTotalHits();
		List<ProductResult> list = result.getResults();
		
		StringBuffer desc = new StringBuffer();
		desc.append(StringUtil.capitilizeFirst(query));
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
		
		appendPageNumberToTitle(desc, page);

		return desc.toString();
	}
	
	@Override
	public String getPageHeader(String query, List<LinkedLabel> activeFilters, int page, List<ProductResult> list) {
		StringBuffer title = new StringBuffer();		
		title.append(StringUtil.capitilizeFirst(query));
		//appendFiltersToTitle(title, activeFilters);
		title.append(" otel fiyatları");
		return title.toString();
	}
	
	@Override
	public String getPageTitle(String query, List<LinkedLabel> activeFilters,int page, List<ProductResult> list) {
		StringBuffer title = new StringBuffer();		
		title.append(StringUtil.capitilizeFirst(query));
		appendFiltersToTitle(title, activeFilters, new String[]{"country", "city"});
		title.append(" otel fiyatları - En uygun fiyatlarla istediğiniz oteli arayın, karşılaştırın, satın alın!");
		appendPageNumberToTitle(title, page);
		return title.toString();
	}

	@Override
	public String getCanonicalURL(SearchResult result, RequestWrapper wrapper)
	{
		String city = getParam("city", wrapper.getRequest());
//		String country = getParam("country", wrapper.getRequest());
		
		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
//		if(StringUtils.isNotBlank(country))
//		{
//			helper.setFilterParam("country", country);	
//		}

		if(StringUtils.isNotBlank(city))
		{
			helper.setFilterParam("city", city);	
		}

		return helper.getRequestQuery();
	}
	

	@Override
	public List<LinkedLabel> getBreadCrumb(SearchResult result, RequestWrapper wrapper, List<LinkedLabel> activeFilters)
	{
		List<LinkedLabel> crumb = new ArrayList<LinkedLabel>();

		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
		appendHomePageToBreadCrumb(wrapper.getRequest(), crumb);
		appendCategoryToBreadCrumb(wrapper.getRequest(), crumb, "Otel", "3");
		appendQueryToBreadCrumb(result, crumb, helper);
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "country");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "city");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "star");
		
		return crumb;
	}
	
	@Override
	public String getShareDescription(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Arkadaşınız Karniyarik.com'dan bulduğu bu otelin ilginizi çekeceğini düşündü:");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendBrand(result, shareBuff);
		appendProperty(result, shareBuff, "country");
		appendProperty(result, shareBuff, "city");

		String str = clearShareString(shareBuff);
		
		return str;
	}
	
	@Override
	public String getShareTitle(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Otel Tavsiyesi: ");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendProperty(result, shareBuff, "city");
		appendProperty(result, shareBuff, "country");
		
		String shareName = clearShareString(shareBuff);
		
		return shareName;
	}
}
