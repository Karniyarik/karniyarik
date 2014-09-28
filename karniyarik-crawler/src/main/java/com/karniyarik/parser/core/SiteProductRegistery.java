package com.karniyarik.parser.core;

import java.util.TreeSet;

public class SiteProductRegistery
{
	private final TreeSet<Integer>	set;

	public SiteProductRegistery(TreeSet<Integer> registeryData)
	{
		this.set = registeryData;
	}

	public boolean isProductRegistered(Integer productHash)
	{
		boolean registered;
		synchronized (set)
		{
			registered = !set.add(productHash);
		}
		return registered;
	}

	public void clear()
	{
		synchronized (set)
		{
			set.clear();
		}
	}

}
