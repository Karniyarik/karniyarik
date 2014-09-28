package com.karniyarik.web.remote;

import java.util.Calendar;

import com.karniyarik.common.config.apikey.APIKey;

public class KeyUsage
{
	private APIKey	key;
	private int		dailyRequestCount	= 0;
	private int		day					= -1;

	public KeyUsage(APIKey key)
	{
		this.key = key;
	}

	public synchronized boolean increaseRequestByOne()
	{
		int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

		if (today != day)
		{
			day = today;
			dailyRequestCount = 0;
		}

		if (dailyRequestCount > key.getLimit())
		{
			return false;
		}

		dailyRequestCount++;

		return true;
	}
}