package com.karniyarik.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.common.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class QueryStringHelper
{
	private Integer				mPageNumber				= null;
	private String				mSearchQuery			= null;
	private String				mDestinationPage		= null;
	private Integer				mSort					= null;
	private String				mCategoryFilterValue	= null;
	private Integer				mPageSize				= null;
	private Integer				mShowImages				= null;
	//private boolean				mCanUseOr				= false;
	private boolean				appendFilterParameters	= true;
	private Map<String, String>	filterParams			= null;
	private boolean				restful					= true;
	private boolean 			autoCategorySelection 	= false;
	private RequestWrapper		wrapper					= null;
	private	String				baseURL					= null;
	private String 				contextPath;
	private boolean				isMobile				= false;
	private boolean				insertMobileFalse		= false;
	private HttpServletRequest 	request 				= null;

	public QueryStringHelper(RequestWrapper aWrapper)
	{
		this(aWrapper.getRequest());
		this.wrapper = aWrapper;
		setFilterParams(aWrapper);
		setPageNumber(aWrapper.getPageNumber());
		setSearchQuery(aWrapper.getQuery());
		setSort(aWrapper.getSortType());
		setCategoryFilterValue(aWrapper.getCategoryFilterValue());
		//setCanUseOr(aWrapper.isConvertSpacesToOr());
		setPageSize(aWrapper.getPageSize());
		setShowImages(aWrapper.getShowImages());
		
		
//		if(wrapper != null){
//			if(wrapper.isMobileClient()){
//				isMobile = true;	
//			}
//			if(wrapper.isInsertMobileFalse()){
//				insertMobileFalse = true;	
//			}
//		}
	}

	public QueryStringHelper()
	{
		filterParams = new HashMap<String, String>();
		
	}

	public QueryStringHelper(HttpServletRequest request)
	{
		this();
		this.request = request;
		String oIsMobile = request.getParameter(RequestWrapper.ISMOBILE);
		if(StringUtils.isNotBlank(oIsMobile))
		{
			try{isMobile = Boolean.valueOf(oIsMobile);} catch (Exception e){};
			if(isMobile == false)
			{
				insertMobileFalse = true;
			}
		}
		contextPath = request.getContextPath();
	}

	public void setFilterParam(String paramName, String value)
	{
		filterParams.put(paramName, value);
	}

	public void cleanFilterParam(String paramName)
	{
		filterParams.remove(paramName);
	}


	@SuppressWarnings("unchecked")
	private void setFilterParams(RequestWrapper aWrapper)
	{
		String[] defaultParams = new String[] { RequestWrapper.QUERY_STR,
				/*RequestWrapper.CONVERT_SPACES_TO_OR,*/ RequestWrapper.PAGE_SIZE,
				RequestWrapper.PAGE_NUMBER, RequestWrapper.PRODUCT_URL,
				RequestWrapper.SHOW_IMAGES, RequestWrapper.SHOW_RANKS,
				RequestWrapper.SORT_TYPE, "stab"};

		List<String> defaultParamsList = Arrays.asList(defaultParams);

		filterParams = new HashMap<String, String>();

		Map parameters = aWrapper.getRequest().getParameterMap();
		String value = null;
		String[] valueArr;
		for (Object paramName : parameters.keySet())
		{
			if (!defaultParamsList.contains(paramName.toString()))
			{
				value = null;
				if(parameters.get(paramName) instanceof String[])
				{
					valueArr = (String[]) parameters.get(paramName);
					if(valueArr != null && valueArr.length > 0)
					{
						value = valueArr[0];
						if(value.startsWith(","))
						{
							value = value.replaceFirst(",", "");
						}
					}
				}
				else
				{
					value = (String) parameters.get(paramName);
				}
				
				if(StringUtils.isNotBlank(value))
				{
					filterParams.put((String) paramName, value);	
				}
			}
		}
	}
	
	public void resetFilterParams()
	{
		filterParams.clear();
	}
	
	public boolean isRestful()
	{
		return restful;
	}

	public void setRestful(boolean restful)
	{
		this.restful = restful;
	}

	public String getRequestQuery()
	{
		StringBuffer aBuffer = new StringBuffer();

		if (inRestfulCondition())
		{
			aBuffer.append(contextPath);
			aBuffer.append("/");
			
			appendMobileContext(aBuffer);

			if (getCategoryFilterValue() != null)
			{
				aBuffer.append(getCategoryFilterValue());
				aBuffer.append("/");
			}
			
			//aBuffer.append("ne-kadar/");
			String lowercasequery = getSearchQuery().toLowerCase(new Locale("TR"));
			lowercasequery = lowercasequery.replace("fiyatları", "fiyatlari");
			lowercasequery = lowercasequery.replace("fiyatı", "fiyati");
			
			if(!lowercasequery.contains("fiyatlari") && !lowercasequery.contains("fiyati"))
			{
				lowercasequery += "-fiyatlari";
			}
				
			aBuffer.append(appendRestfullParameter(RequestWrapper.QUERY_STR, lowercasequery));
			
			if(getCategoryFilterValue().equalsIgnoreCase("otel"))
			{
				aBuffer.append(appendRestfullParameter("city",filterParams.get("city")));
			}
			else
			{
				aBuffer.append(appendRestfullParameter(RequestWrapper.BRAND,filterParams.get(RequestWrapper.BRAND)));	
			}
			
			
			if(getPageNumber() != null && getPageNumber() != RequestWrapper.DEFAULT_PAGE_NUMBER)
			{
				aBuffer.append(appendRestfullParameter(RequestWrapper.PAGE_NUMBER,
						getPageNumber()));		
			}			
			
			aBuffer.setLength(aBuffer.length() - 1);
			
			if(insertMobileFalse)
			{
				aBuffer.append("?");
				aBuffer.append(RequestWrapper.ISMOBILE);
				aBuffer.append("=false");
			}
			return aBuffer.toString();
		}
		else
		{
			if (mDestinationPage == null)
			{
				throw new RuntimeException(
						"Destination page cannot be null.");
			}

			if(baseURL != null)
			{
				aBuffer.append(baseURL);
			}
			else
			{
				aBuffer.append(contextPath);
				aBuffer.append("/");
				
				appendMobileContext(aBuffer);
				
				if (getCategoryFilterValue() != null)
				{
					aBuffer.append(getCategoryFilterValue());
					aBuffer.append("/");
				}

				aBuffer.append(mDestinationPage);				
			}
			
			aBuffer.append("?");

			if(getPageNumber() != null && getPageNumber() != RequestWrapper.DEFAULT_PAGE_NUMBER)
			{
				appendValuetoQueryStr(RequestWrapper.PAGE_NUMBER, getPageNumber(),aBuffer);	
			}
			
			appendValuetoQueryStr(RequestWrapper.QUERY_STR, getSearchQuery(), aBuffer);
			
			if(getSort() != null && getSort() != RequestWrapper.DEFAULT_SORT_TYPE)
			{
				appendValuetoQueryStr(RequestWrapper.SORT_TYPE, getSort(), aBuffer);	
			}
			
			// appendValuetoQueryStr(RequestWrapper.FILTER_CATEGORY,
			// getCategoryFilterValue(), aBuffer);
			
			if(getPageSize() != null && getPageSize() != SearchConfig.DEFAULT_PAGE_SIZE)
			{
				appendValuetoQueryStr(RequestWrapper.PAGE_SIZE, getPageSize(),aBuffer);	
			}
			
			if(getShowImages() != null && getShowImages() != RequestWrapper.DEFAULT_SHOW_IMAGES)
			{
				appendValuetoQueryStr(RequestWrapper.SHOW_IMAGES, getShowImages(),aBuffer);	
			}
			

//			if (isCanUseOr() == true)
//			{
//				appendValuetoQueryStr(RequestWrapper.CONVERT_SPACES_TO_OR, isCanUseOr(), aBuffer);
//			}

			for (String name : filterParams.keySet())
			{
				appendValuetoQueryStr(name, filterParams.get(name), aBuffer);
			}
			
			if(insertMobileFalse)
			{
				aBuffer.append(RequestWrapper.ISMOBILE);
				aBuffer.append("=false");
			}
			
			return aBuffer.toString();
		}
	}

	private void appendMobileContext(StringBuffer aBuffer) {
		if(isMobile && request != null)
		{
			String context = (String) request.getAttribute(RequestWrapper.MOBILECONTEXT_ATTR);
			aBuffer.append(context);
			aBuffer.append("/");	
		}else if(isMobile)
		{
			aBuffer.append(RequestWrapper.MOBILECONTEXT);
			aBuffer.append("/");
		}
	}

	private boolean inRestfulCondition()
	{
		Map<String, String> tmpParams = new HashMap<String, String>();
		tmpParams.putAll(filterParams);
		tmpParams.remove("brand");
		tmpParams.remove("stab");
		if(getCategoryFilterValue().equalsIgnoreCase("otel"))
		{
			tmpParams.remove("city");
		}
		return (restful && getPageSize() == SearchConfig.DEFAULT_PAGE_SIZE
				&& getShowImages() == 1 && getSort() == 1 && /*!isCanUseOr() &&*/ 
				(tmpParams.size() == 0));
	}

	private String appendRestfullParameter(String parameterType,
			Object parameter)
	{
		String result = StringUtils.EMPTY;

		if (parameter != null && !parameter.equals(StringUtils.EMPTY))
		{

			if (parameterType == RequestWrapper.PAGE_NUMBER)
			{

				result = "p" + parameter + "/";
			}
			else
			{
				try
				{
					result = URLEncoder.encode(parameter.toString(), StringUtil.DEFAULT_ENCODING) + "/";
				}
				catch (UnsupportedEncodingException e)
				{
					result = parameter + "/";
				}
			}
		}

		return result;
	}

	public boolean isAppendFilterParameters()
	{
		return appendFilterParameters;
	}

	public void setAppendFilterParameters(boolean appendFilterParameters)
	{
		this.appendFilterParameters = appendFilterParameters;
	}

	private void appendValuetoQueryStr(String aName, Object aValue,
			StringBuffer aBuffer)
	{
		if (aValue != null)
		{
			if (aBuffer.charAt(aBuffer.length() - 1) != '?')
			{
				aBuffer.append("&");
			}

			aBuffer.append(aName);
			aBuffer.append("=");

			try
			{
				aBuffer.append(URLEncoder.encode(aValue.toString(),
						StringUtil.DEFAULT_ENCODING));
			}
			catch (UnsupportedEncodingException e)
			{
				throw new RuntimeException("Cannot encode URL", e);
			}
		}
	}

	public void clear()
	{
		mPageNumber = null;
		mSearchQuery = null;
		mDestinationPage = null;
		//mCanUseOr = false;
		mPageSize = null;
		mShowImages = null;
		mCategoryFilterValue = null;
	}
//
//	public boolean isCanUseOr()
//	{
//		return mCanUseOr;
//	}
//
//	public void setCanUseOr(boolean aCanUseOr)
//	{
//		mCanUseOr = aCanUseOr;
//	}

	public Integer getPageNumber()
	{
		return mPageNumber;
	}

	public Integer getPageSize()
	{
		return mPageSize;
	}

	public Integer getShowImages()
	{
		return mShowImages;
	}

	public void setPageNumber(Integer pageNumber)
	{
		mPageNumber = pageNumber;
	}

	public void setPageSize(Integer pageSize)
	{
		mPageSize = pageSize;
	}

	public void setShowImages(Integer showImages)
	{
		mShowImages = showImages;
	}

	public String getSearchQuery()
	{
		if(mSearchQuery != null)
		{
			mSearchQuery = mSearchQuery.trim();
			mSearchQuery = mSearchQuery.replaceAll("\\s+", "-");
			return mSearchQuery.trim();
		}
		return mSearchQuery;
	}

	public void setSearchQuery(String searchQuery)
	{
		mSearchQuery = searchQuery;
	}

	public String getDestinationPage()
	{
		return mDestinationPage;
	}

	public void setDestinationPage(String destinationPage)
	{
		mDestinationPage = destinationPage;
	}

	public Integer getSort()
	{
		return mSort;
	}

	public void setSort(Integer sort)
	{
		mSort = sort;
	}

	public void setCategoryFilterValue(String aCategoryFilterValue)
	{
		mCategoryFilterValue = aCategoryFilterValue;
	}

	public String getCategoryFilterValue()
	{
		return mCategoryFilterValue;
	}

	public boolean isAutoCategorySelection() {
		return autoCategorySelection;
	}
	
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
	
	public void setInsertMobileFalse(boolean insertMobileFalse) {
		this.insertMobileFalse = insertMobileFalse;
	}
}
