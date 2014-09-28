package com.karniyarik.ir.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.RangeFilter;

import com.karniyarik.ir.DoubleField;

public class KarniyarikIntegerRangeFilter implements KarniyarikFilterr
{
	private Integer lowerNumber = null;
	private Integer upperNumber = null;
	private String attrName		= null;

	public KarniyarikIntegerRangeFilter(String attrName, Integer aLowerNumber, Integer anUpperNumber)
	{
		lowerNumber = aLowerNumber;
		upperNumber = anUpperNumber;
		this.attrName = attrName;
	}

	public KarniyarikIntegerRangeFilter(String attrName, Integer aLowerNumber)
	{
		lowerNumber = aLowerNumber;
		this.attrName = attrName;
	}

	public KarniyarikIntegerRangeFilter(String attrName)
	{
		this.attrName = attrName;
	}

	@Override
	public Filter getLuceneFilter()
	{
		String anUpperTerm = null;
		String aLowerTerm = null; 
		
		if(lowerNumber == null)
		{
			lowerNumber = Integer.MIN_VALUE;
		}
		
		aLowerTerm = DoubleField.getString(lowerNumber);
		
		if(upperNumber == null)
		{
			upperNumber = Integer.MAX_VALUE;
		}
		
		anUpperTerm = Integer.toString(upperNumber);		
		
		RangeFilter aFilter = new RangeFilter(attrName, aLowerTerm, anUpperTerm, true, true);
		
		return new CachingWrapperFilter(aFilter);
	}

	@Override
	public String getSolrFilter() {
		String anUpperTerm = null;
		String aLowerTerm = null; 
		
		if(lowerNumber == null)
		{
			lowerNumber = Integer.MIN_VALUE;
		}
		
		aLowerTerm = Integer.toString(lowerNumber);
		
		if(upperNumber == null)
		{
			upperNumber = Integer.MAX_VALUE;
		}
		
		anUpperTerm = Integer.toString(upperNumber);		
		
		StringBuffer buff = new StringBuffer();
		buff.append(getAttrName());
		buff.append(":[ ");
		buff.append(getLowerNumber());
		buff.append(" TO ");
		buff.append(getUpperNumber());
		buff.append(" ]");
		
		return buff.toString();
	}

	@Override
	public List<String> getSolrFilterList() {
		List<String> result = new ArrayList<String>();
		result.add(getSolrFilter());
		return result;
	}
	
	public Number getLowerNumber()
	{
		return lowerNumber;
	}

	public void setLowerNumber(Integer lowerNumber)
	{
		this.lowerNumber = lowerNumber;
	}

	public Integer getUpperNumber()
	{
		return upperNumber;
	}

	public void setUpperNumber(Integer upperNumber)
	{
		this.upperNumber = upperNumber;
	}

	public String getAttrName()
	{
		return attrName;
	}

	public void setAttrName(String attrName)
	{
		this.attrName = attrName;
	}

	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(attrName);
		buffer.append(" filter: ");
		buffer.append(getLowerNumber());
		buffer.append("-");
		buffer.append(getUpperNumber());
		return buffer.toString();
	}
}
