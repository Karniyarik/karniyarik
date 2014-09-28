package com.karniyarik.web.json;

import java.util.Locale;

import org.apache.commons.lang.StringEscapeUtils;

public class LinkedLabel
{
	private String	label			= null;
	private int		count			= 0;
	private String	link			= null;
	private String	cssClass		= null;
	private String	tooltip			= null;
	private String	originalValue	= null;

	public LinkedLabel(String aLabel, String aLink, String cssClass, String tooltip)
	{
		this(aLabel, aLink, cssClass);
		setTooltip(tooltip);
	}

	public LinkedLabel(String aLabel, String aLink, String cssClass)
	{
		this(aLabel, aLink);
		setCssClass(cssClass);
	}

	public LinkedLabel(String aLabel, String aLink, int aCount)
	{
		this(aLabel, aLink);
		count = aCount;
	}

	public LinkedLabel(String aLabel, String aLink)
	{
		super();
		this.originalValue = aLabel;
		setLabel(aLabel);
		setLink(aLink);
	}

	public String getOriginalValue() {
		return originalValue;
	}
	
	public String getLabel()
	{
		return StringEscapeUtils.escapeHtml(label);
	}

	public void setLabel(String aLabel)
	{
		label = aLabel;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int aCount)
	{
		count = aCount;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String aLink)
	{
		link = StringEscapeUtils.escapeHtml(aLink);
	}

	public String getCssClass()
	{
		return cssClass;
	}

	public void setCssClass(String aCssClass)
	{
		cssClass = aCssClass;
	}

	public String getTooltip()
	{
		return tooltip;
	}

	public void setTooltip(String tooltip)
	{
		this.tooltip = StringEscapeUtils.escapeHtml(tooltip);
	}

	public String getShortenedLabel()
	{
		return label.length() < 23 ? label.toLowerCase(new Locale("TR")) : label.substring(0, 20).toLowerCase(new Locale("TR")) + "...";
	}
	
	public void setShortenedLabel(){
		
	}
	
	public String getShortenedLabel(int max)
	{
		return label.length() < max ? label.toLowerCase(new Locale("TR")) : label.substring(0, max-3).toLowerCase(new Locale("TR")) + "...";
	}

	public static String getShortenedLabel(String label, int max)
	{
		return label.length() < max ? label : label.substring(0, max-3) + "...";
	}

}
