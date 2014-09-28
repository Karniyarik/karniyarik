package com.karniyarik.web.category;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.web.json.LinkedLabel;
import com.karniyarik.web.json.ProductResult;
import com.karniyarik.web.json.SearchResult;
import com.karniyarik.web.util.QueryStringHelper;
import com.karniyarik.web.util.RequestWrapper;

public abstract class BaseCategoryUtil
{
	public static int MAX_DESCRIPTION = 80;
	public static int MAX_KEYWORDS = 140;
	
	public static DecimalFormat numberFormat = new DecimalFormat("###,###,###,###.###");
	 
	public abstract List<LinkedLabel> getActiveFilters(HttpServletRequest request, boolean strongLabels);
	public abstract String getPageDescription(String query, List<LinkedLabel> activeFilters, int page, SearchResult result);
	public abstract String getPageKeywords(String query, List<LinkedLabel> activeFilters, int page, List<ProductResult> list, long productCount, int siteCount);
	public abstract String getPageTitle(String query, List<LinkedLabel> activeFilters, int page, List<ProductResult> list);
	public abstract String getPageHeader(String query, List<LinkedLabel> activeFilters, int page, List<ProductResult> list);
	public abstract String getSingleProductPageKeywords(ProductResult result, List<ProductResult> products);
	public abstract String getSingleProductPageDescription(ProductResult result, List<ProductResult> products);
	public abstract String getSingleProductPageTitle(ProductResult result, List<ProductResult> products);
	public abstract String getSingleProductPageHeader(ProductResult result, List<ProductResult> products);
	public abstract String getCanonicalURL(SearchResult result, RequestWrapper wrapper);
	public abstract List<LinkedLabel> getBreadCrumb(SearchResult result, RequestWrapper wrapper, List<LinkedLabel> activeFilters);
	public abstract String getShareTitle(ProductResult result);
	public abstract String getShareDescription(ProductResult result);
	
	public String getPageImageSource(List<ProductResult> result)
	{
		String image = "http://www.karniyarik.com/images/logo/karniyarik86x86.png";
		String tmpImage = null;
		for(ProductResult p: result)
		{
			if(StringUtils.isNotBlank(p.getImageURL()))
			{
				tmpImage = p.getImageURL();
				break;
			}
		}
		
		if(StringUtils.isNotBlank(tmpImage))
		{
			tmpImage = "http://www.karniyarik.com/imgrsz/.png?w=100&amp;h=100&amp;v=" +tmpImage;
			image = tmpImage;
		}
		
		return image;
	}
	
	public String getSingleProductImageSource(ProductResult result)
	{
		String image = "http://www.karniyarik.com/images/logo/karniyarik86x86.png";
		String tmpImage = null;
		if(StringUtils.isNotBlank(result.getImageURL()))
		{
			tmpImage = "http://www.karniyarik.com/imgrsz/.png?w=100&amp;h=100&amp;v=" +tmpImage;
			image = tmpImage;
		}
		
		return image;
	}
	
	public List<LinkedLabel> getActiveFilters(HttpServletRequest request)
	{
		return getActiveFilters(request, true);
	}
	
	public void fillFilterLabels(boolean strongLabels, List<LinkedLabel> result, String[][] filters) {
		for(String[] filterDesc: filters)
		{
			if(StringUtils.isNotBlank(filterDesc[1]))
			{
				if(strongLabels)
				{
					result.add(new LinkedLabel("<b>" + filterDesc[0] + "</b>", filterDesc[1], filterDesc[2]));	
				}
				else
				{
					result.add(new LinkedLabel(filterDesc[0], filterDesc[1], filterDesc[2]));
				}				
			}
		}
	}
	
	public List<String> getRangeFilterVariables(String fieldName, SearchResult searchResult, HttpServletRequest request)
	{
		List<String> result = new ArrayList<String>();

		List<LinkedLabel> priceClusters = getList(searchResult, fieldName);
		
		String rangeMax = StringUtil.getValue(request.getParameter(fieldName +"max"));
		String rangeMin = StringUtil.getValue(request.getParameter(fieldName +"min"));

		String price1 = StringUtil.getValue(request.getParameter(fieldName +"1"));
		String price2 = StringUtil.getValue(request.getParameter(fieldName + "2"));

		int minPrice = 0;
		int maxPrice = 0;
		
		int selectedMin = 0;
		int selectedMax = 0;
		
		try
		{
			if(priceClusters != null && priceClusters.size() > 0)
			{
				if(StringUtils.isBlank(price1))
				{
					minPrice = priceClusters.get(0).getCount();
					selectedMin = minPrice;
				}
				else
				{
					selectedMin = parseIntegerValue(price1);		
				}
				
				if(StringUtils.isBlank(price2) && priceClusters.size() > 1)
				{
					maxPrice = priceClusters.get(1).getCount();
					selectedMax = maxPrice;
				}
				else
				{
					selectedMax = parseIntegerValue(price2);		
				}
				
				if(StringUtils.isBlank(rangeMax)) { 
					rangeMax = Integer.toString(maxPrice);
				}
				if(StringUtils.isBlank(rangeMin)) { 
					rangeMin = Integer.toString(minPrice);
				}
			}
			else
			{
				if(StringUtils.isNotBlank(price1))
				{
					selectedMin = parseIntegerValue(price1);
					if(StringUtils.isBlank(rangeMax)) { 
						rangeMax = Integer.toString(selectedMin);
					}
				}
				
				if(StringUtils.isNotBlank(price2))
				{
					selectedMax = parseIntegerValue(price2);		
					if(StringUtils.isBlank(rangeMin)) { 
						rangeMin = Integer.toString(selectedMax);
					}
				}
			}
			
			
		}
		catch (Exception e)
		{
			System.out.println("Cannot get range filter var " + fieldName + ". " + searchResult.getQuery());
		}
		
		result.add(price1);
		result.add(price2);
		result.add(rangeMin);
		result.add(rangeMax);
		result.add(Integer.toString(selectedMin));
		result.add(Integer.toString(selectedMax));
		
		return result;
	}
	
	public String getParam(String name, HttpServletRequest request)
	{
		String result = request.getParameter(name);
		
		if(result == null)
		{
			result = "";
		}
		
		if(result.startsWith(","))
		{
			result = result.replaceFirst(",","");
		}
		
		result = result.replace(",",", ");
		
		return result;
	}

	public String getParam(String name, String name2, HttpServletRequest request)
	{
		String param1 = getParam(name, request);
		String param2 = getParam(name2, request);
		
		String result = param1 + " - " + param2;
		
		if(result.trim().equals("-"))
		{
			result = "";
		}
		
		return result;
	}

	public void appendBrand(ProductResult result, StringBuffer buff) {
		if(!isBrandEmpty(result.getBrand()))
		{
			buff.append(", ");
			buff.append(result.getBrand());
		}
	}

	public void appendStore(ProductResult result, StringBuffer buff) {
		buff.append(", ");
		buff.append(result.getSourceName());
	}

	public void appendPrice(ProductResult result, StringBuffer buff) {
		buff.append(", ");
		buff.append(result.getPrice());
		buff.append(" TL ");
		//buff.append(result.getPriceCurrency());
	}

	public static void appendProperty(ProductResult result, StringBuffer buff, String propStr) {
		String prop = result.getProperty(propStr);
		if(StringUtils.isNotBlank(prop))
		{
			buff.append(", ");
			buff.append(prop);
		}
	}

	public boolean isBrandEmpty(String brand)
	{
		return StringUtils.isBlank(brand) && brand.equals("Diğer");
	}
	
	public String[] getFilterData(List<LinkedLabel> list, String parameterName, HttpServletRequest request)
	{
		String data = null;
		String value = null;
		if(StringUtils.isBlank(request.getParameter(parameterName))){
			data = "";
			value = "";
		}else{
			StringBuffer buff = new StringBuffer();
			int index = 0;
			for(LinkedLabel linkedLabel: list){
				buff.append("'");
				buff.append(StringEscapeUtils.unescapeHtml(linkedLabel.getLabel()));
				buff.append("'");
				if(++index < list.size()){buff.append(",");}
			}
			data=buff.toString();
			value = request.getParameter(parameterName); 
		}
		
		String[] result = new String[2];
		result[0] = data;
		result[1] = value; 
		return result;
	}
	
	public List<LinkedLabel> getList(SearchResult searchResult, String name)
	{
		List<LinkedLabel> result = null;
		result = searchResult.getCategoryPropMap().get(name);
		if(result == null)
		{
			result = new ArrayList<LinkedLabel>();
		}
		return result;
	}

	public boolean isPriceFilterApplied(HttpServletRequest request)
	{
		return StringUtils.isNotBlank(getParam("price1", "price2", request));
	}

	public void appendFiltersToTitle(StringBuffer title, List<LinkedLabel> activeFilters)
	{
		if(activeFilters.size()>0)
		{
			title.append(" ");
			for(LinkedLabel label: activeFilters)
			{
				title.append(StringEscapeUtils.unescapeHtml(label.getLink()));
				title.append(", ");
			}
		}		
	}

	public void appendFiltersToTitle(StringBuffer title, List<LinkedLabel> activeFilters, String[] targets)
	{
		if(activeFilters.size()>0)
		{
			StringBuffer buff = new StringBuffer();
			//title.append(" ");
			boolean appended = false;
			for(LinkedLabel label: activeFilters)
			{
				for(String target: targets)
				{
					if(label.getCssClass().equalsIgnoreCase(target))
					{
						String str = StringEscapeUtils.unescapeHtml(label.getLink());
						//if(!title.toString().toLowerCase().contains(str.toLowerCase()))
						{
							if(appended)
							{
								buff.append(" ,");
							}
							buff.append(str);
							appended = true;
						}
					}					
				}
			}
			
			if(appended)
			{
				title.append(" (");
				title.append(buff);
				title.append(" )");
			}
		}		
	}
	
	
	public List<LinkedLabel> getFilters(List<LinkedLabel> activeFilters, String[] targets)
	{
		List<LinkedLabel> filters = new ArrayList<LinkedLabel>();
		
		if(activeFilters.size()>0)
		{
			for(LinkedLabel label: activeFilters)
			{
				for(String target: targets)
				{
					if(label.getCssClass().equalsIgnoreCase(target))
					{
						filters.add(label);
					}					
				}
			}
		}
		
		return filters;
	}
	public void appendProductResultNames(StringBuffer buff, List<ProductResult> results, int maxSize, boolean appendMore)
	{
		StringBuffer tmp = new StringBuffer();
		tmp.append(buff.toString().toUpperCase(Locale.ENGLISH));
		
		if(results.size()>0)
		{
			StringBuffer newBuff = new StringBuffer();
			
			for(ProductResult result: results)
			{
				String str = StringEscapeUtils.unescapeHtml(result.getProductName());
				str = str.replaceAll("\\s+", " ");
				String upper = str.toUpperCase(Locale.ENGLISH);
				
				if(tmp.indexOf(upper) == -1)
				{
					if((newBuff.length()+str.length()) < maxSize)
					{
						newBuff.append(str);
						newBuff.append(", ");
						tmp.append(str.toUpperCase(Locale.ENGLISH));
					}
					else
					{
						break;
					}
				}
				if(newBuff.length() > maxSize)
				{
					break;
				}
			}
			
			if(appendMore)
			{
				newBuff.append("ve daha fazlası.");	
			}
			
			buff.append(newBuff);
		}		
	}

	public void appendPageNumberToTitle(StringBuffer title, int page)
	{
		if(page != 1)
		{
			title.append(" (");
			title.append(page);
			title.append(")");			
		}
	}
	
	public void appendFilterToBreadCrumb(List<LinkedLabel> activeFilters, List<LinkedLabel> crumb, QueryStringHelper helper, String filter)
	{
		LinkedLabel label;
		List<LinkedLabel> brand = getFilters(activeFilters, new String[]{filter});
		if(brand != null && brand.size() > 0)
		{
			helper.setFilterParam(filter, brand.get(0).getLink());
			String value = StringEscapeUtils.unescapeHtml(brand.get(0).getLink());
			label = new LinkedLabel(value, helper.getRequestQuery());
			crumb.add(label);
		}
	}

	public void appendQueryToBreadCrumb(SearchResult result, List<LinkedLabel> crumb, QueryStringHelper helper)
	{
		LinkedLabel label;
		String query = StringEscapeUtils.unescapeHtml(result.getQuery());
		query = StringUtil.capitilizeFirst(query).trim();
		//query = query + " fiyatları";
		label = new LinkedLabel(query, helper.getRequestQuery());
		crumb.add(label);
	}

	public void appendCategoryToBreadCrumb(HttpServletRequest request, List<LinkedLabel> crumb, String name, String stab)
	{
		LinkedLabel label = new LinkedLabel(name, request.getContextPath() + "/index.jsp?stab="+stab);
		crumb.add(label);
	}

	public QueryStringHelper getDEfaultQueryStringHelperForBreadCrumb(RequestWrapper wrapper)
	{
		QueryStringHelper helper = new QueryStringHelper(wrapper);
		helper.setDestinationPage("search.jsp");
		helper.resetFilterParams();
		helper.setSort(RequestWrapper.DEFAULT_SORT_TYPE);
		helper.setPageSize(SearchConfig.DEFAULT_PAGE_SIZE);
		helper.setShowImages(RequestWrapper.DEFAULT_SHOW_IMAGES);
		return helper;
	}

	public static void appendHomePageToBreadCrumb(HttpServletRequest request, List<LinkedLabel> crumb)
	{
		appendPageToBreadCrumb(request, crumb, "Ana Sayfa", "index.jsp");
	}
	
	public static void appendPageToBreadCrumb(HttpServletRequest request, List<LinkedLabel> crumb, String pageName, String pageURL)
	{
		LinkedLabel label = new LinkedLabel(pageName, request.getContextPath() + "/" +  pageURL);
		crumb.add(label);
	}

	public static void setStaticPageBreadCrumb(String pageName, String pageURL, HttpServletRequest request)
	{
		request.setAttribute(RequestWrapper.BREADCRUMB_KEY, getStaticPageBreadCrumb(pageName, pageURL, request));
	}

	public static void setBreadCrumb(HttpServletRequest request, List<LinkedLabel> crumb)
	{
		request.setAttribute(RequestWrapper.BREADCRUMB_KEY, crumb);
	}
	
	public static List<LinkedLabel> getBreadCrumb(HttpServletRequest request)
	{
		Object obj = request.getAttribute(RequestWrapper.BREADCRUMB_KEY);
		if(obj != null)
		{
			List<LinkedLabel> crumb = (List<LinkedLabel>) obj;
			return crumb;
		}
		
		return null;
	}

	public static List<LinkedLabel> getStaticPageBreadCrumb(String pageName, String pageURL, HttpServletRequest request)
	{
		List<LinkedLabel> crumb = new ArrayList<LinkedLabel>();
		appendHomePageToBreadCrumb(request, crumb);
		appendPageToBreadCrumb(request, crumb, pageName, pageURL);
		return crumb;
	}
	
	public static String clearShareString(StringBuffer shareBuff)
	{
		String shareName = StringUtil.removeMultiEmptySpaces(shareBuff.toString());
		shareName = StringEscapeUtils.escapeHtml(shareName);
		shareName = StringEscapeUtils.escapeJavaScript(shareName);
		return shareName;
	}
	
	public int parseIntegerValue(String value)
	{
		try {
			value = value.replaceAll("\\s+","");
			return numberFormat.parse(value).intValue();
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return 0;
	}
	
	public double parseDoubleValue(String value)
	{
		try {
			value = value.replaceAll("\\s+","");
			return numberFormat.parse(value).doubleValue();
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return 0;
	}
}
