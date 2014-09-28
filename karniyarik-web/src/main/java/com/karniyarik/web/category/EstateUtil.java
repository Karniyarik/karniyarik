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

public class EstateUtil extends BaseCategoryUtil
{
	public List<LinkedLabel> getActiveFilters(HttpServletRequest request, boolean useStrong)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();
		
		String[][] filters = new String[][]{
				new String[]{"Fiyat", getParam("price1", "price2", request), "price1,price2"},
				new String[]{"Site", getParam("source", request), "source"},
				new String[]{"Şehir", getParam("city", request), "city"}, 
				new String[]{"İlçe", getParam("district", request), "district"},
				new String[]{"Oda Sayısı", getParam("roomcount", request), "roomcount"},
				new String[]{"Salon Sayısı", getParam("salooncount", request), "salooncount"},
				new String[]{"Katı", getParam("floorcount", request), "floorcount"}, 
				new String[]{"Isınma", getParam("warmingtype", request), "warmingtype"},
				new String[]{"Emlak Tipi", getParam("buildingtype", request), "buildingtype"},				
				new String[]{"Alanı", getParam("area1", "area2", request), "area1,area2"}};
	
		fillFilterLabels(useStrong, result, filters);
		return result;
	}

//	public List<LinkedLabel> getArea(SearchResult searchResult)
//	{
//		return getList(searchResult, "area");
//	}
	public List<LinkedLabel> getPrice(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.PRICE);
	}

	public List<LinkedLabel> getArea(SearchResult searchResult)
	{
		return getList(searchResult, "area");
	}

	public boolean isAreaFilterApplied(HttpServletRequest request)
	{
		return StringUtils.isNotBlank(getParam("area1", "area2", request));
	}

	public List<LinkedLabel> getBuildingType(SearchResult searchResult)
	{
		return getList(searchResult, "buildingtype");
	}
	
	public List<LinkedLabel> getWarmingType(SearchResult searchResult)
	{
		return getList(searchResult, "warmingtype");
	}
	
	public List<LinkedLabel> getFloorCount(SearchResult searchResult)
	{
		return getList(searchResult, "floorcount");
	}

	public List<LinkedLabel> getSaloonCount(SearchResult searchResult)
	{
		return getList(searchResult, "salooncount");
	}
	
	public List<LinkedLabel> getRoomCount(SearchResult searchResult)
	{
		return getList(searchResult, "roomcount");
	}

	public List<LinkedLabel> getCity(SearchResult searchResult)
	{
		return getList(searchResult, "city");
	}

	public List<LinkedLabel> getDistrict(SearchResult searchResult)
	{
		return getList(searchResult, "district");
	}

	public List<LinkedLabel> getStore(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.STORE);
	}


	public String getSingleProductPageDescription(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		
		buff.append(result.getProductName());
		appendBrand(result, buff);
		appendProperty(result, buff, "city");
		appendProperty(result, buff, "district");
		appendPrice(result, buff);
		
		int totalSize = buff.length();
		int remainingSize = 140-totalSize;
		if(products.size() > 0 & remainingSize > 10)
		{
			buff.append(" ve ");	
			appendProductResultNames(buff, products, remainingSize, false);
		}
		buff.append(" emlak ilanları.");
		return buff.toString();		
	}

	public String getSingleProductPageKeywords(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		appendBrand(result, buff);
		appendProperty(result, buff, "city");
		appendProperty(result, buff, "district");
		buff.append(", ");
		buff.append(result.getSourceName());
		buff.append(", ");
		appendProductResultNames(buff, products, MAX_KEYWORDS, false);
		buff.append(" kiralık emlak, satılık emlak, konut, daire");
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
		appendProperty(result, buff, "district");
		appendPrice(result, buff);
		return buff.toString();
	}


	@Override
	public String getPageTitle(String query, List<LinkedLabel> activeFilters,int page, List<ProductResult> list) {
		StringBuffer title = new StringBuffer();		
		title.append(StringUtil.capitilizeFirst(query));
		appendFiltersToTitle(title, activeFilters, new String[]{"city", "district"});
		title.append(" Emlak fiyatları");
		appendPageNumberToTitle(title, page);
		return title.toString();
	}
	
	public String getPageKeywords(String query,
			List<LinkedLabel> activeFilters, int page,
			List<ProductResult> list, long productCount, int siteCount) {
		
		StringBuffer desc = new StringBuffer();
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(", ");
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(" fiyatları, ikinci el araba, ikinci el, ");
		
		if(productCount > 0)
		{
			appendProductResultNames(desc, list, MAX_KEYWORDS, false);
		}
		
		if(activeFilters != null && activeFilters.size() > 0)
		{
			//desc.append(", ");
			appendFiltersToTitle(desc, activeFilters, new String[]{"city", "district"});
		}
		
		return desc.toString();
	}
	
	@Override
	public String getPageDescription(String query, List<LinkedLabel> activeFilters, int page, SearchResult result) {
		//int siteCount = result.getSiteCount();
		long  productCount = result.getTotalHits();
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
		title.append(" emlak fiyatları");
		return title.toString();
	}
	
	@Override
	public List<LinkedLabel> getBreadCrumb(SearchResult result, RequestWrapper wrapper, List<LinkedLabel> activeFilters)
	{
		List<LinkedLabel> crumb = new ArrayList<LinkedLabel>();

		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
		appendHomePageToBreadCrumb(wrapper.getRequest(), crumb);
		appendCategoryToBreadCrumb(wrapper.getRequest(), crumb, "Emlak", "4");
		appendQueryToBreadCrumb(result, crumb, helper);
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "city");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "district");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "buildingtype");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "roomcount");
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "salooncount");
		
		return crumb;
	}
	
	@Override
	public String getCanonicalURL(SearchResult result, RequestWrapper wrapper)
	{
		String brand = getParam("city", wrapper.getRequest());
		String city = getParam("district", wrapper.getRequest());
		
		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
		if(StringUtils.isNotBlank(brand))
		{
			helper.setFilterParam("city", brand);	
		}

		if(StringUtils.isNotBlank(city))
		{
			helper.setFilterParam("district", city);	
		}

		return helper.getRequestQuery();
	}
	
	@Override
	public String getShareDescription(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Arkadaşınız Karniyarik.com'dan bulduğu bu emlak ilanının ilginizi çekeceğini düşündü:");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendBrand(result, shareBuff);
		appendProperty(result, shareBuff, "city");
		appendProperty(result, shareBuff, "district");
		appendProperty(result, shareBuff, "buildingtype");

		String str = clearShareString(shareBuff);
		
		return str;
	}
	
	@Override
	public String getShareTitle(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Emlak Tavsiyesi: ");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendBrand(result, shareBuff);
		appendProperty(result, shareBuff, "city");
		appendProperty(result, shareBuff, "district");
		
		String shareName = clearShareString(shareBuff);
		
		return shareName;
	}
}
