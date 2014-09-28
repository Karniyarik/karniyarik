package com.karniyarik.common.ga;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "events")
public class Events {

	@XmlElement(name = "eventlist")
	@XmlElementWrapper(name = "eventlist")
	private List<Event> events = new ArrayList<Event>();
	
	public Events() {
		//do nothing
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public void print()
	{
		System.out.println();
		for(Event event: events)
		{
			event.print("");
		}
		System.out.println();
	}
}
