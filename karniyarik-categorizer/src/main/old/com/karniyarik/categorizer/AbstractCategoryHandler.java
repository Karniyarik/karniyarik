package com.karniyarik.categorizer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause.Occur;

import com.karniyarik.ir.filter.KarniyarikBooleanFilter;
import com.karniyarik.ir.filter.KarniyarikFilterr;
import com.karniyarik.ir.filter.KarniyarikNumberRangeFilter;
import com.karniyarik.ir.filter.KarniyarikTermFilter;


public abstract class AbstractCategoryHandler implements ICategoryHandler
{
	protected String getParameterValue(String name, Map requestParameters)
	{
		if(requestParameters.get(name) != null && ((String[])requestParameters.get(name)).length > 0)
		{
			return ((String[])requestParameters.get(name))[0];
		}
		
		return null;
	}
	
	protected boolean isEmpty(String filterValue)
	{
		if(filterValue != null && StringUtils.isNotBlank(filterValue) && !filterValue.equals("-1"))
		{
			return false;
		}
		
		return true;
	}
	
	public void appendSingleTermFilter(String attrName, Map requestParameters, List<KarniyarikFilterr> result)
	{
		String filterValue = null;
		
		filterValue = getParameterValue(attrName, requestParameters);
		
		if(!isEmpty(filterValue))
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
						bFilter.addFilter(new KarniyarikTermFilter(attrName, value.trim()));
					}
				}	
				
				result.add(bFilter);
			}
			else
			{
				result.add(new KarniyarikTermFilter(attrName, filterValue.trim()));
			}
		}
	}
	
	protected void appendIntegerRangeFilter(String originalAttrName, String attrName1, String attrName2, Map requestParameters, List<KarniyarikFilterr> result)
	{
		String filterValue = null;
		String filter2Value = null;
		
		filterValue = getParameterValue(attrName1, requestParameters);
		filter2Value = getParameterValue(attrName2, requestParameters);
		
		if(!isEmpty(filterValue) || !isEmpty(filter2Value))
		{
			result.add(new KarniyarikNumberRangeFilter(originalAttrName, convertToDouble(filterValue), convertToDouble(filter2Value)));	
		}
	}
	
	private Number convertToInt(String str)
	{
		if(StringUtils.isNotBlank(str))
		{
			return Integer.parseInt(str.toString());
		}
		
		return null;
	}
	
	private Number convertToDouble(String str)
	{
		if(StringUtils.isNotBlank(str))
		{
			return Double.parseDouble(str.toString());	
		}
		
		return null;
	}
}
