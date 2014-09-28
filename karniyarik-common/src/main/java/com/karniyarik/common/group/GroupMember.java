package com.karniyarik.common.group;

import java.io.Serializable;

import org.jgroups.Address;

public class GroupMember implements Serializable {
	private static final long	serialVersionUID	= 6300325597057108476L;
	
	private int type = GroupMemberFactory.NONE;
	private String uuid = "";
	private String ip = "";
	private Address address = null;
	private boolean isSeleniumCapable = false;
	private boolean isJobsWithClassCapable = false;
	private boolean gelirotaklariCapable = false;
	
	public GroupMember() {
		
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isSeleniumCapable() {
		return isSeleniumCapable;
	}
	
	public void setSeleniumCapable(boolean isSeleniumCapable)
	{
		this.isSeleniumCapable = isSeleniumCapable;
	}
	
	public String getUuid()
	{
		return uuid;
	}
	
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
	
	public boolean isScheduler()
	{
		return getType() == GroupMemberFactory.SCHEDULER;
	}

	public boolean isExecutor()
	{
		return getType() == GroupMemberFactory.EXECUTOR;
	}

	public boolean isJobsWithClassCapable()
	{
		return isJobsWithClassCapable;
	}
	
	public void setJobsWithClassCapable(boolean isJobsWithClassCapable)
	{
		this.isJobsWithClassCapable = isJobsWithClassCapable;
	}
	
	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(getAddress() != null)
		{
			return getAddress().equals(((GroupMember)obj).getAddress());	
		}
		else
		{
			return getUuid().equals(((GroupMember)obj).getUuid());
		}
		
	}
	
	public void setGelirotaklariCapable(boolean gelirotaklariCapable)
	{
		this.gelirotaklariCapable = gelirotaklariCapable;
	}
	
	public boolean isGelirotaklariCapable()
	{
		return gelirotaklariCapable;
	}
}
