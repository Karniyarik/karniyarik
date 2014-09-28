package com.karniyarik.common.statistics.vo;

public class ProductStatSortOptions
{

	private static final int	LISTING_VIEWS	= 0;
	private static final int	LISTING_CLICKS	= 1;
	private static final int	SPONSOR_VIEWS	= 2;
	private static final int	SPONSOR_CLICKS	= 3;

	private static final int	ASCENDING		= 0;
	private static final int	DESCENDING		= 1;

	private final int			sortColumn;
	private final int			sortType;

	public ProductStatSortOptions(int sortColumn, int sortType)
	{
		this.sortColumn = sortColumn;
		this.sortType = sortType;
	}

	public boolean isOrderByListingViewsAscending()
	{
		return (sortColumn == LISTING_VIEWS) && (sortType == ASCENDING);
	}

	public boolean isOrderByListingViewsDescending()
	{
		return (sortColumn == LISTING_VIEWS) && (sortType == DESCENDING);
	}

	public boolean isOrderByListingClicksAscending()
	{
		return (sortColumn == LISTING_CLICKS) && (sortType == ASCENDING);
	}

	public boolean isOrderByListingClicksDescending()
	{
		return (sortColumn == LISTING_CLICKS) && (sortType == DESCENDING);
	}

	public boolean isOrderBySponsorViewsAscending()
	{
		return (sortColumn == SPONSOR_VIEWS) && (sortType == ASCENDING);
	}

	public boolean isOrderBySponsorViewsDescending()
	{
		return (sortColumn == SPONSOR_VIEWS) && (sortType == DESCENDING);
	}

	public boolean isOrderBySponsorClicksAscending()
	{
		return (sortColumn == SPONSOR_CLICKS) && (sortType == ASCENDING);
	}

	public boolean isOrderBySponsorClicksDescending()
	{
		return (sortColumn == SPONSOR_CLICKS) && (sortType == DESCENDING);
	}

}
