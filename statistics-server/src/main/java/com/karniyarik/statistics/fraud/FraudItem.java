package com.karniyarik.statistics.fraud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

public class FraudItem
{

	public static final int		MIN_SECS			= 3;
	private static final int	TOLERABLE_CLICKS	= 5;

	private String				ip;
	private String				url;
	private String				uniqueKey;
	private List<Long>			clickDates;

	public FraudItem(String ip, String url, long date)
	{
		this.ip = ip;
		this.url = url;
		this.uniqueKey = ip + "-" + url;
		this.clickDates = new ArrayList<Long>();
		clickDates.add(date);
	}

	public FraudItem()
	{
		ip = "";
		url = "";
		uniqueKey = "";
		clickDates = new ArrayList<Long>();
	}

	public boolean addClickDate(long date)
	{
		boolean result = false;
		if (tooClose(date))
		{
			clickDates.add(date);
			result = true;
		}

		return result;
	}

	public boolean fraudDetected()
	{
		return clickDates.size() > TOLERABLE_CLICKS;
	}

	public boolean tooClose(long newClickDate)
	{
		Date tmpDate = DateUtils.addSeconds(new Date(clickDates.get(clickDates.size() - 1)), MIN_SECS);
		return tmpDate.compareTo(new Date(newClickDate)) > 0;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public List<Long> getClickDates()
	{
		return clickDates;
	}

	public void setClickDates(List<Long> clickDates)
	{
		this.clickDates = clickDates;
	}

	public String getUniqueKey()
	{
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey)
	{
		this.uniqueKey = uniqueKey;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("IP: ");
		sb.append(ip);
		sb.append("\n");
		sb.append("URL: ");
		sb.append(url);
		sb.append("\n");
		sb.append("Click Dates: ");
		sb.append("\n");
		for (long date : clickDates)
		{
			sb.append(new Date(date).toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
