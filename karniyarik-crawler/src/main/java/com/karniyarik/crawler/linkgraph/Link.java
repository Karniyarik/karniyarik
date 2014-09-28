package com.karniyarik.crawler.linkgraph;

import java.util.Date;

public class Link
{
	/**
	 * The id of the link in DB
	 */
	private int			mID				= 0;

	/**
	 * The URL of the link
	 */
	private String		mURL			= null;

	/**
	 * The external hits to this link. Used in the caching mechanism.
	 */
	private int			mHitCount		= 0;

	/**
	 * The last hit to this site. Used in the caching mechanism.
	 */
	private long		mLastHitTime	= 0;

	/**
	 * If any data of this link is changed (such as external hit count) the link
	 * is signed as updated so that the data can be written to db. Used when
	 * emptying the cache.
	 */
	private boolean		mIsUpdated		= false;

	/**
	 * True if link content is fetched
	 */
	private boolean		isVisited		= false;

	/**
	 * Total time passed to get the content of the link
	 */
	private long		mFetchTime		= 0;

	/**
	 * The date when the link content is fetched
	 */
	private Date		mFetchDate		= null;

	/**
	 * Indicates whether there a product is extracted from this url or not
	 */
	private boolean		hasProduct		= false;

	// should always be called with a short url (without home page url string)
	public Link(String aUrl)
	{
		mURL = aUrl;
	}

	public int getID()
	{
		return mID;
	}

	public void setID(int aId)
	{
		mID = aId;
	}

	public String getURL()
	{
		return mURL;
	}

	boolean isUpdated()
	{
		return mIsUpdated;
	}

	void setUpdated(boolean aIsUpdated)
	{
		mIsUpdated = aIsUpdated;
	}

	void increaseHitCount()
	{
		mHitCount++;
	}

	int getHitCount()
	{
		return mHitCount;
	}

	void setHitCount(int aHitCount)
	{
		mHitCount = aHitCount;
	}

	public long getLastHitTime()
	{
		return mLastHitTime;
	}

	void setLastHitTime(long aLastHitTime)
	{
		mLastHitTime = aLastHitTime;
	}

	@Override
	public boolean equals(Object aObj)
	{
		Link aLink = (Link) aObj;

		if (aLink != null)
		{
			return this.getURL().equals(aLink.getURL());
		}

		return false;
	}

	@Override
	public String toString()
	{
		StringBuffer aBuffer = new StringBuffer();
		aBuffer.append(getURL());
		aBuffer.append(" - ");
		aBuffer.append(isVisited);
		aBuffer.append(" - ");
		aBuffer.append(getHitCount());

		return aBuffer.toString();
	}

	public boolean isVisited()
	{
		return isVisited;
	}
	
	public void setIsVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public long getFetchTime()
	{
		return mFetchTime;
	}

	public void setFetchTime(long aFetchTime)
	{
		mFetchTime = aFetchTime;
	}

	public Date getFetchDate()
	{
		return mFetchDate;
	}

	public void setFetchDate(Date aFetchDate)
	{
		mFetchDate = aFetchDate;
	}

	public void setUrl(String url)
	{
		this.mURL = url;
	}

	public boolean isHasProduct()
	{
		return hasProduct;
	}

	public void setHasProduct(boolean hasProduct)
	{
		this.hasProduct = hasProduct;
	}

}
