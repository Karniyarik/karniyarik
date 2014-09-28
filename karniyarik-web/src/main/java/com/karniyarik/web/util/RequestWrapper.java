package com.karniyarik.web.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.common.util.RobotChecker;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.ir.filter.KarniyarikFilterr;
import com.karniyarik.ir.filter.KarniyarikSort;
import com.karniyarik.search.searcher.QueryResult;
import com.karniyarik.web.category.AbstractCategoryHandler;
import com.karniyarik.web.category.CarProvider;
import com.karniyarik.web.category.EstateProvider;
import com.karniyarik.web.category.ICategoryHandler;
import com.karniyarik.web.category.OtelProvider;
import com.karniyarik.web.category.ProductProvider;

public class RequestWrapper
{
	public static final String		PAGE_NUMBER				= "page";
	public static final String		BRAND					= "brand";
	public static final String		STORE					= "source";
	public static final String		QUERY_STR				= "query";
	public static final String		SORT_TYPE				= "sort";
	
	public static final String		ISROBOT					= "isrobot";
	public static final String		ISMOBILE				= "m";
	public static final String		MOBILECONTEXT			= "m";
	public static final String		IMOBILECONTEXT			= "i";
	public static final String		MOBILECONTEXT_ATTR		= "mc";
	
	
	public static final int			SORT_TYPE_RELEVANCE		= 1;
	public static final int			SORT_TYPE_ASCENDING		= 2;
	public static final int			SORT_TYPE_DESCENDING	= 3;
	public static final int			SORT_TYPE_KM_ASCENDING	= 4;
	public static final int			SORT_TYPE_KM_DESCENDING	= 5;
	public static final int			SORT_TYPE_YEAR_ASCENDING = 6;
	public static final int			SORT_TYPE_YEAR_DESCENDING = 7;
	public static final int			SORT_TYPE_HP_ASCENDING = 8;
	public static final int			SORT_TYPE_HP_DESCENDING = 9;
	
	public static final int			DEFAULT_SORT_TYPE		= SORT_TYPE_RELEVANCE;
	public static final int			DEFAULT_SHOW_IMAGES		= 1;
	public static final int			DEFAULT_PAGE_NUMBER		= 1;
	
	//public static final String		CONVERT_SPACES_TO_OR	= "csto";
	public static final String		PAGE_SIZE				= "psize";
	public static final String		SHOW_IMAGES				= "simg";
	public static final String		SHOW_RANKS				= "showranks";
	public static final int			MAX_PAGE_SIZE			= 500;
	public static final String		PRODUCT_URL				= "u";
	public static final String		CATEGORY				= "cat";
	public static final String		SEARCH_RESULT			= "sr";
	public static final String		GA_TRCK_TYPE_VALUE		= "gatracktv";
	public static final String		API_KEY					= "apiKey";
	public static final String		BREADCRUMB_KEY			= "bcrumb";
	
	private HttpServletRequest		mRequest				= null;
	private int						mPageNumber				= DEFAULT_PAGE_NUMBER;
	private String					mQuery					= null;
	private QueryResult				mQueryResult			= null;
	private String					mCategoryFilterValue	= null;
	private Integer					categoryType			= null;
	private List<KarniyarikFilterr>	categorySpecificFilters	= new ArrayList<KarniyarikFilterr>();
	private Integer					mSortType				= DEFAULT_SORT_TYPE;
	private KarniyarikSort			mSort					= null;
//	private boolean					mIsConvertSpacesToOr	= false;
	private Integer					mPageSize				= SearchConfig.DEFAULT_PAGE_SIZE;
	private boolean					mShowRanks				= false;
	private Integer					mShowImages				= DEFAULT_SHOW_IMAGES;
	private String					schema					= null;
	private String					productUrl				= "";
	private String					category				= "";
	private boolean					shallLog 				= true;
	private String					userAgent				= null;
	private String					apiKey					= "";
	private boolean					mobileClient			= false;					

	public RequestWrapper(HttpServletRequest aRequest)
	{
		mRequest = aRequest;

		init();
	}

	public static RequestWrapper getInstance(HttpServletRequest aRequest)
	{
		RequestWrapper aWrapper = (RequestWrapper) aRequest
				.getAttribute("requestwrapper");
		if (aWrapper == null)
		{
			aWrapper = new RequestWrapper(aRequest);
			aRequest.setAttribute("requestwrapper", aWrapper);
		}

		return aWrapper;
	}

	private void init()
	{
		mQuery = getParameterValue(QUERY_STR);

		if (mQuery == null)
		{
			mQuery = "";
		}
		
		mQuery = mQuery.replaceAll("-", " ");
//		mQuery = mQuery.replace("fiyatları", "");
//		mQuery = mQuery.replace("fiyatlari", "");
//		mQuery = mQuery.replace("fiyatı", "");
//		mQuery = mQuery.replace("fiyati", "");
		mQuery = mQuery.replaceAll("[fF][iİıI][yY][aA][tT]([iİıI])?([lL][aA][rR][iİıI])?", " ");
		mQuery = mQuery.replaceAll("\\\\", " ");
		mQuery = mQuery.replaceAll(":", " ");
		mQuery = mQuery.replaceAll("\\/", " ");
		mQuery = StringUtil.removeMultiEmptySpaces(mQuery);
		mQuery = mQuery.trim();

		apiKey = getParameterValue(API_KEY);

		if (apiKey == null)
		{
			apiKey = "";
		}
		
		String aPageNumberStr = getParameterValue(PAGE_NUMBER);

		if (aPageNumberStr != null && !aPageNumberStr.equals(""))
		{
			try 
			{
				mPageNumber = Integer.parseInt(aPageNumberStr);
			} catch (NumberFormatException e) {
				//e.printStackTrace();
			}
		}
		
		Object oIsMobile = mRequest.getAttribute(ISMOBILE);
		if(oIsMobile != null)
		{
			mobileClient = (Boolean) oIsMobile;
		}

		prepareProductUrl();

		prepareSort();

		prepareCategory();

//		prepareConvertSpacesToOr();

		preparePageSize();

		prepareShowImages();
		
		prepareShallLog();
	}

	private void prepareShallLog()
	{
		userAgent = getRequest().getHeader("user-agent");
		shallLog = !RobotChecker.getInstance().isRobot(userAgent);
	}

	private void prepareProductUrl()
	{
		productUrl = getParameterValue(PRODUCT_URL);

		if (StringUtils.isNotBlank(productUrl))
		{
			productUrl = StringEscapeUtils.unescapeHtml(productUrl);
		}
	}


	private void preparePageSize()
	{
		String aStr = getParameterValue(PAGE_SIZE);

		if (aStr != null && !aStr.equals(""))
		{
			try
			{
				mPageSize = Integer.parseInt(aStr);
			}
			catch (NumberFormatException e)
			{
				mPageSize = KarniyarikRepository.getInstance().getConfig()
						.getConfigurationBundle().getSearchConfig()
						.getDefaultPageSize();
				if(getCategoryType() == CategorizerConfig.HOTEL_TYPE)
				{
					mPageSize = 10; 
				}
			}

			mPageSize = mPageSize > 500 ? 500 : mPageSize;
		}
		else
		{
			mPageSize = KarniyarikRepository.getInstance().getConfig()
					.getConfigurationBundle().getSearchConfig()
					.getDefaultPageSize();
			
			if(isMobileClient()){
				mPageSize = 10;
			}
			if(getCategoryType() == CategorizerConfig.HOTEL_TYPE)
			{
				mPageSize = 10; 
			}
		}
	}

	private void prepareShowImages()
	{
		String aStr = getParameterValue(SHOW_IMAGES);

		if (aStr != null && !aStr.equals(""))
		{
			mShowImages = aStr.trim().equals("on") ? 0 : 1;
		}
		else
		{
			mShowImages = 1;
		}
	}

	public Integer getPageSize()
	{
		return mPageSize;
	}

	public Integer getShowImages()
	{
		return mShowImages;
	}

//	private void prepareConvertSpacesToOr()
//	{
//		String aStr = getParameterValue(CONVERT_SPACES_TO_OR);
//
//		if (aStr != null && !aStr.equals(""))
//		{
//			mIsConvertSpacesToOr = true;
//		}
//		else
//		{
//			mIsConvertSpacesToOr = false;
//		}
//	}
	
	private void prepareSort()
	{
		String aSortString = getParameterValue(SORT_TYPE);

		if (aSortString != null && !aSortString.equals(""))
		{
			try {
				mSortType = Integer.parseInt(aSortString);
			} catch (NumberFormatException e) {
			}

			mSort = KarniyarikSort.getKarniyarikSort(mSortType);
//			switch (mSortType)
//			{
//			case (SORT_TYPE_DESCENDING):
//				mSort = KarniyarikSort.PRICE_HIGH_TO_LOW;
//				break;
//			case (SORT_TYPE_ASCENDING):
//				mSort = KarniyarikSort.PRICE_LOW_TO_HIGH;
//				break;
//			default:
//				break;
//			}
		}
	}

	private void prepareCategory()
	{
		category = getAttribute(CATEGORY);
		if (StringUtils.isBlank(category))
		{
			category = getParameterValue(CATEGORY);
			// if (StringUtils.isBlank(category)) {
			// category = CategorizerConfig.PRODUCT;
			// }
		}

		mCategoryFilterValue = CategorizerConfig
				.lowercaseCategoryString(category);
		categoryType = CategorizerConfig.getCategoryType(mCategoryFilterValue);

		if (StringUtils.isNotBlank(category))
		{
			ICategoryHandler categoryHandler = null;

			if (categoryType == CategorizerConfig.CAR_TYPE) {
				categoryHandler = new CarProvider();
			}
			else if (categoryType == CategorizerConfig.PRODUCT_TYPE) {
				categoryHandler = new ProductProvider();
			}
			else if (categoryType == CategorizerConfig.HOTEL_TYPE) {
				categoryHandler = new OtelProvider();
			}
			else if (categoryType == CategorizerConfig.ESTATE_TYPE) {
				categoryHandler = new EstateProvider();
			}
			if (categoryHandler != null)
			{
				categorySpecificFilters = categoryHandler.getFilters(mRequest.getParameterMap());
			}
		}
	}

	public static RequestWrapper getRequestWrapper(HttpServletRequest aRequest)
	{
		RequestWrapper aWrapper = (RequestWrapper) aRequest
				.getAttribute("requestwrapper");
		if (aWrapper == null)
		{
			aWrapper = new RequestWrapper(aRequest);
			aRequest.setAttribute("requestwrapper", aWrapper);
		}

		return aWrapper;
	}

	public HttpServletRequest getRequest()
	{
		return mRequest;
	}

	public void setRequest(HttpServletRequest request)
	{
		mRequest = request;
	}

	public int getPageNumber()
	{
		return mPageNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		mPageNumber = pageNumber;
	}

	public String getQuery()
	{
		return mQuery;
	}

	public void setQuery(String query)
	{
		mQuery = query;
	}

	public QueryResult getQueryResult()
	{
		return mQueryResult;
	}

	public void setQueryResult(QueryResult queryResult)
	{
		mQueryResult = queryResult;
	}

	public Integer getSortType()
	{
		return mSortType;
	}

	public KarniyarikSort getSort()
	{
		return mSort;
	}

//	public boolean isConvertSpacesToOr()
//	{
//		return mIsConvertSpacesToOr;
//	}

	public boolean isShowRanks()
	{
		return mShowRanks;
	}

	private String getParameterValue(String aParamName)
	{
		return AbstractCategoryHandler.getParameterValue(aParamName, getRequest().getParameterMap());
		//return getRequest().getParameter(aParamName);
	}

	private String getAttribute(String aParamName)
	{
		Object attr = getRequest().getAttribute(aParamName);
		if (attr == null)
		{
			return null;
		}
		else
		{
			return attr.toString();
		}
	}

	public String getSchema()
	{
		return schema;
	}

	public String getCategoryFilterValue()
	{
		return mCategoryFilterValue;
	}

	public int getCategoryType()
	{
		return categoryType;
	}

	public void setCategoryFilterValue(String categoryFilterValue)
	{
		mCategoryFilterValue = categoryFilterValue;
	}

	public List<KarniyarikFilterr> getCategorySpecificFilters()
	{
		return categorySpecificFilters;
	}

	public String getProductUrl()
	{
		return productUrl;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public boolean isShallLog() 
	{
		return shallLog;
	}

	public String getApiKey() {
		return apiKey;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public boolean isMobileClient()
	{
		return mobileClient;
	}
}
