package com.karniyarik.statistics.vo;

import java.util.Date;

public class HitView
{
	public static int TYPE_HIT = 0;
	public static int TYPE_VIEW = 1;
	
	private long productId = 0;
	private Date date = null;
	private int count = 0;
	
	public HitView()
	{
		// TODO Auto-generated constructor stub
	}

	public HitView(long productId, Date date, int count)
	{
		super();
		this.productId = productId;
		this.date = date;
		this.count = count;
	}

	public long getProductId()
	{
		return productId;
	}

	public void setProductId(long productId)
	{
		this.productId = productId;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
}
