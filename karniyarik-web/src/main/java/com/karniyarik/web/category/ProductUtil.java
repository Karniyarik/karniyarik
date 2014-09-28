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

public class ProductUtil extends BaseCategoryUtil
{
	public List<LinkedLabel> getActiveFilters(HttpServletRequest request, boolean strongLabels)
	{
		List<LinkedLabel> result = new ArrayList<LinkedLabel>();
		
		String[][] filters = new String[][]{
				new String[]{"Fiyat", getParam("price1", "price2", request),"price1,price2"},
				new String[]{"Marka", getParam("brand", request), "brand"},
				new String[]{"Etiket", getParam("tags", request), "tags"},
				new String[]{"Mağaza", getParam("source", request), "source"},
				new String[]{"Kategori", getParam("class", request), "class"}};
	
		fillFilterLabels(strongLabels, result, filters);
		return result;
	}

	@Override
	public String getCanonicalURL(SearchResult result, RequestWrapper wrapper)
	{
		String brand = getParam("brand", wrapper.getRequest());
		
		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
		if(StringUtils.isNotBlank(brand))
		{
			helper.setFilterParam("brand", brand);	
		}
		
		return helper.getRequestQuery();
	}

	public List<LinkedLabel> getBrand(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.BRAND);
	}

	public List<LinkedLabel> getTags(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.TAGS);
	}

	public List<LinkedLabel> getStore(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.STORE);
	}

//	public List<LinkedLabel> getCategory(SearchResult searchResult)
//	{
//		return getList(searchResult, SearchConstants.CATEGORY);
//	}
	
	public List<LinkedLabel> getPrice(SearchResult searchResult)
	{
		return getList(searchResult, SearchConstants.PRICE);
	}
	
	public boolean isPriceFilterApplied(HttpServletRequest request)
	{
		return StringUtils.isNotBlank(getParam("price1", "price2", request));
	}

	public String getSingleProductPageDescription(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		
		buff.append(result.getProductName());
		appendPrice(result, buff);
		appendBrand(result, buff);
		appendStore(result, buff);
		
		int totalSize = buff.length();
		int remainingSize = 140-totalSize;
		if(products.size() > 0 & remainingSize > 10)
		{
			buff.append(" ve ");	
			appendProductResultNames(buff, products, remainingSize, false);
		}
		//buff.append(".");
		return buff.toString();
	}

	public String getSingleProductPageKeywords(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		appendBrand(result, buff);
		buff.append(", ");
		buff.append(result.getSourceName());
		buff.append(", ");
		appendProductResultNames(buff, products, MAX_KEYWORDS, false);
		buff.append(", alışveriş");
		return buff.toString();
	}

	public String getSingleProductPageTitle(ProductResult result, List<ProductResult> products){
		return getSingleProductPageHeader(result, products);
	}
	
	public String getSingleProductPageHeader(ProductResult result, List<ProductResult> products){
		StringBuffer buff = new StringBuffer();
		buff.append(result.getProductName());
		
		buff.append(", Fiyatı:");
		buff.append(result.getPrice());
		buff.append(" ");
		buff.append(result.getPriceCurrency());
		buff.append(" (Marka:");
		if(!isBrandEmpty(result.getBrand()))
		{
			buff.append(result.getBrand());
			buff.append(", ");
		}
		buff.append("Mağaza: ");
		buff.append(result.getSourceName());
		buff.append(") ");
		return buff.toString();
	}
	
	@Override
	public String getPageTitle(String query, List<LinkedLabel> activeFilters, int page, List<ProductResult> list) {
		StringBuffer title = new StringBuffer();		
		title.append(StringUtil.capitilizeFirst(query));
		appendFiltersToTitle(title, activeFilters, new String[]{"brand"});
		title.append(" fiyatları ");
		appendPageNumberToTitle(title, page);
		title.append(" - ");
		if(list != null && list.size()>0)
		{
			title.append("Alışveriş - En uygun, ucuz fiyatlarla istediğiniz ürünü arayın, karşılaştırın, satın alın!");
		}
		else
		{
			title.append("Sonuç Bulunamadı");
		}
		
		
//		String capQuery = StringUtil.removeMultiEmptySpaces(query);
//		capQuery = StringUtil.capitilizeFirst(capQuery);
//		title.append(capQuery);
//		title.append(" Fiyatları | Ucuz ");
//		title.append(capQuery);
//		title.append(" | En Ucuz ");
//		title.append(capQuery);
//		title.append(" Fiyatı | ");
//		title.append(capQuery);
//		title.append(" Alışveriş | ");
//		title.append(capQuery);
//		title.append(" Arama");
//		appendPageNumberToTitle(title, page);
		
		return title.toString();
	}

	@Override
	public List<LinkedLabel> getBreadCrumb(SearchResult result, RequestWrapper wrapper, List<LinkedLabel> activeFilters)
	{
		List<LinkedLabel> crumb = new ArrayList<LinkedLabel>();

		QueryStringHelper helper = getDEfaultQueryStringHelperForBreadCrumb(wrapper);
		
		appendHomePageToBreadCrumb(wrapper.getRequest(), crumb);
		appendCategoryToBreadCrumb(wrapper.getRequest(), crumb, "Ürün", "1");
		appendQueryToBreadCrumb(result, crumb, helper);
		appendFilterToBreadCrumb(activeFilters, crumb, helper, "brand");
		
		return crumb;
	}

	@Override
	public String getPageKeywords(String query,
			List<LinkedLabel> activeFilters, int page,
			List<ProductResult> list, long productCount, int siteCount) {
		
		StringBuffer desc = new StringBuffer();
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(", ");
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(" fiyatları, ");
		
		if(productCount > 0)
		{
			appendProductResultNames(desc, list, MAX_KEYWORDS, false);
		}
		
		if(activeFilters != null && activeFilters.size() > 0)
		{
			//desc.append(", ");
			appendFiltersToTitle(desc, activeFilters, new String[]{"brand"});
		}
		
		return desc.toString();
	}
	
	@Override
	public String getPageDescription(String query, List<LinkedLabel> activeFilters, int page, SearchResult result) {
		int siteCount = result.getSiteCount();
		long productCount = result.getTotalHits();
		List<ProductResult> list = result.getResults();
		
		StringBuffer desc = new StringBuffer();
		desc.append(StringUtil.capitilizeFirst(query));
		desc.append(" Fiyatları");
		
		if(activeFilters != null && activeFilters.size() > 0)
		{
			appendFiltersToTitle(desc, activeFilters, new String[]{"brand"});
		}
		
		if(siteCount > 0 && productCount > 0)
		{
			desc.append(" - ");
			desc.append(siteCount);
			desc.append(" siteden ");
			desc.append(productCount);
			desc.append(" sonuç ");
			
			int totalSize = desc.length();
			int remainingSize = 140-totalSize;
			if(productCount > 0 & remainingSize > 0)
			{
				desc.append("arasında ");
				appendProductResultNames(desc, list, remainingSize, true);
			}			
		}
		else
		{
			desc.append(" fiyatları sorgusu için şu an için hiç bir sonuç bulunamadı. En uygun, ucuz fiyatlarla ");
			desc.append(StringUtil.capitilizeFirst(query));
			desc.append(" arayın, karşılaştırın, satın alın!");
		}
		
//		StringBuffer desc = new StringBuffer();
//		desc.append(StringUtil.capitilizeFirst(query));
//		appendFiltersToTitle(desc, activeFilters, new String[]{"brand"});
//		desc.append(" Fiyatları. En uygun fiyatlarla istediğiniz ürünü arayın, karşılaştırın, satın alın!");		

		appendPageNumberToTitle(desc, page);

		return desc.toString();
	}
	
	@Override
	public String getPageHeader(String query, List<LinkedLabel> activeFilters, int page, List<ProductResult> list) {
		StringBuffer title = new StringBuffer();		
		title.append(StringUtil.capitilizeFirst(query));
		//appendFiltersToTitle(title, activeFilters);
		title.append(" fiyatları");
		//appendPageNumberToTitle(title, page);
		return title.toString();
	}
	
	@Override
	public String getShareDescription(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Arkadaşınız Karniyarik.com'dan bulduğu bu sonucun ilginizi çekeceğini düşündü:");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendBrand(result, shareBuff);

		String str = clearShareString(shareBuff);
		
		return str;
	}
	
	@Override
	public String getShareTitle(ProductResult result)
	{
		StringBuffer shareBuff = new StringBuffer();
		shareBuff.append("Ürün Tavsiyesi: ");
		shareBuff.append(result.getProductName());
		appendPrice(result, shareBuff);
		appendBrand(result, shareBuff);
		
		String shareName = clearShareString(shareBuff);
		
		return shareName;
	}
}
