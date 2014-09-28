package com.karniyarik.web.category;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause.Occur;

import com.karniyarik.categorizer.ProductClassifier;
import com.karniyarik.ir.filter.KarniyarikBooleanFilter;
import com.karniyarik.ir.filter.KarniyarikFilterr;
import com.karniyarik.ir.filter.KarniyarikIntegerRangeFilter;
import com.karniyarik.ir.filter.KarniyarikNumberRangeFilter;
import com.karniyarik.ir.filter.KarniyarikTermFilter;


public abstract class AbstractCategoryHandler implements ICategoryHandler
{
	private static final ThreadLocal<DecimalFormat>	DOT_LAST_FORMAT_THREAD_LOCAL	= new ThreadLocal<DecimalFormat>()
	{
		protected synchronized DecimalFormat initialValue()
		{
			DecimalFormatSymbols aSymbols = new DecimalFormatSymbols();
			aSymbols.setDecimalSeparator('.');
			aSymbols.setGroupingSeparator(',');
			return new DecimalFormat("###,###.##", aSymbols);
		}
	};
	
	public static String getParameterValue(String name, Map<Object, Object> requestParameters)
	{
		if(requestParameters.get(name) != null && ((String[])requestParameters.get(name)).length > 0)
		{
			return ((String[])requestParameters.get(name))[0];
		}
		
		return null;
	}

	public void appendSingleTermFilter(String attrName, Map<Object, Object> requestParameters, List<KarniyarikFilterr> result)
	{
		String filterValue = getParameterValue(attrName, requestParameters);
		
		if(StringUtils.isNotBlank(filterValue))
		{
			String[] values = filterValue.split(",");
			
			if(values.length > 1)
			{
				KarniyarikBooleanFilter bFilter = new KarniyarikBooleanFilter(); 
				bFilter.setOccur(Occur.SHOULD);
				for(String value: values)
				{
					if(StringUtils.isNotBlank(value) && !value.equals("-1"))
					{
						value = getCategoryValue(attrName, value);
						bFilter.addFilter(new KarniyarikTermFilter(attrName, value.trim()));
					}
				}	
				
				result.add(bFilter);
			}
			else
			{
				String value = getCategoryValue(attrName, filterValue.trim());
				result.add(new KarniyarikTermFilter(attrName, value));
			}
		}
	}

	private String getCategoryValue(String attrName, String value)	{
		if(attrName.equals("class"))
		{
			value = ProductClassifier.getInstance(false).getCategoryId(value);
		}
		return value;
	}
	
	protected void appendIntegerRangeFilter(String originalAttrName, String attrName1, String attrName2, Map<Object, Object> requestParameters, List<KarniyarikFilterr> result)
	{
		String filterValue = getParameterValue(attrName1, requestParameters);
		String filter2Value = getParameterValue(attrName2, requestParameters);
		
		if (StringUtils.isNotBlank(filterValue) || StringUtils.isNotBlank(filter2Value)) {
			Integer lowerLimit;
			try
			{
				lowerLimit = DOT_LAST_FORMAT_THREAD_LOCAL.get().parse(filterValue).intValue();
			}
			catch (Throwable e)
			{
				lowerLimit = null;
			}

			Integer upperLimit;
			try
			{
				upperLimit = DOT_LAST_FORMAT_THREAD_LOCAL.get().parse(filter2Value).intValue();
			}
			catch (Throwable e)
			{
				upperLimit = null;
			} 
			
			// if both limits are given
			// but lower is bigger then upper
			// switch them to provide reasonable 
			// search results.
			if (lowerLimit != null && upperLimit != null) {
				if (lowerLimit > upperLimit) {
					Integer tmp = upperLimit;
					upperLimit = lowerLimit;
					lowerLimit = tmp;
				}
			}
				
			result.add(new KarniyarikIntegerRangeFilter(originalAttrName, lowerLimit, upperLimit));	
		} 
	}
	
	protected void appendDoubleRangeFilter(String originalAttrName, String attrName1, String attrName2, Map<Object, Object> requestParameters, List<KarniyarikFilterr> result)
	{
		String filterValue = getParameterValue(attrName1, requestParameters);
		String filter2Value = getParameterValue(attrName2, requestParameters);
		
		if (StringUtils.isNotBlank(filterValue) || StringUtils.isNotBlank(filter2Value)) {
			Double lowerLimit;
			try
			{
				lowerLimit = DOT_LAST_FORMAT_THREAD_LOCAL.get().parse(filterValue).doubleValue();
			}
			catch (Throwable e)
			{
				lowerLimit = null;
			}

			Double upperLimit;
			try
			{
				upperLimit = DOT_LAST_FORMAT_THREAD_LOCAL.get().parse(filter2Value).doubleValue();
			}
			catch (Throwable e)
			{
				upperLimit = null;
			} 
			
			// if both limits are given
			// but lower is bigger then upper
			// switch them to provide reasonable 
			// search results.
			if (lowerLimit != null && upperLimit != null) {
				if (lowerLimit > upperLimit) {
					Double tmp = upperLimit;
					upperLimit = lowerLimit;
					lowerLimit = tmp;
				}
			}
				
			result.add(new KarniyarikNumberRangeFilter(originalAttrName, lowerLimit, upperLimit));	
		} 
	}
	
}
