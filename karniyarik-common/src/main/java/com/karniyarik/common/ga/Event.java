package com.karniyarik.common.ga;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringEscapeUtils;

import com.google.gdata.data.analytics.DataEntry;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "event")
public class Event {
	
	@XmlElement(name = "name")
	private String name = null;
	
	@XmlElement(name = "totalValue")
	private Double totalValue = 0d;
	
	@XmlElement(name = "totalEvents")
	private Long totalEvents = 0l;
	
	@XmlElement(name = "uniqueEvents")
	private Long uniqueEvents = 0l;
	
	@XmlElement(name = "topSubEvents")
	private List<Event> topSubEvents = new ArrayList<Event>();
	
	@XmlElement(name = "isSponsored")
	boolean isSponsored = false;
	
	@XmlElement(name = "isClick")
	boolean isClick = false;
	
	@XmlElement(name = "isView")
	boolean isView = false;
	
	public Event() {
		// do nothing
	}

	public Event(DataEntry entry, String[] captions) {
		String caption = entry.stringValueOf(captions[0]);
		caption = StringEscapeUtils.unescapeHtml(caption);
		setName(caption);
    	setTotalEvents(entry.longValueOf(captions[1]));
    	setUniqueEvents(entry.longValueOf(captions[2]));
    	setTotalValue(entry.doubleValueOf(captions[3]));
    	if(getName().contains("sponsored"))
    	{
    		isSponsored = true;
    	}
    	if(getName().contains("click"))
    	{
    		isClick = true;
    	}
    	if(getName().contains("view"))
    	{
    		isView = true;
    	}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Long getTotalEvents() {
		return totalEvents;
	}

	public void setTotalEvents(Long totalEvents) {
		this.totalEvents = totalEvents;
	}

	public List<Event> getTopSubEvents() {
		return topSubEvents;
	}

	public void setTopSubEvents(List<Event> topSubEvents) {
		this.topSubEvents = topSubEvents;
	}
	
	public Long getUniqueEvents() {
		return uniqueEvents;
	}
	
	public void setUniqueEvents(Long uniqueEvents) {
		this.uniqueEvents = uniqueEvents;
	}
	
	public boolean isClick()
	{
		return isClick;
	}
	
	public boolean isSponsored()
	{
		return isSponsored;
	}
	
	public boolean isView()
	{
		return isView;
	}
	
	public void setClick(boolean isClick)
	{
		this.isClick = isClick;
	}
	
	public void setSponsored(boolean isSponsored)
	{
		this.isSponsored = isSponsored;
	}
	
	public void setView(boolean isView)
	{
		this.isView = isView;
	}
	
	public void print(String tab)
	{
		System.out.println(tab + getName() + " - " + getTotalEvents() + " - " + getTotalValue() + " - " + getUniqueEvents());
		for(Event event: getTopSubEvents())
		{
			event.print("\t\t");
		}
	}
}
