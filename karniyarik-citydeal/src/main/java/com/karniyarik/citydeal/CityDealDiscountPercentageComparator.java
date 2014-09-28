package com.karniyarik.citydeal;

import java.util.Comparator;

public class CityDealDiscountPercentageComparator implements Comparator<CityDeal>
{
	@Override
	public int compare(CityDeal o1, CityDeal o2)
	{
		if(o1.getDiscountPercentage() == null && o2.getDiscountPercentage()==null)
		{
			return 0;
		}
		else if(o1.getDiscountPercentage() == null)
		{
			return 1;
		}
		else if(o2.getDiscountPercentage() == null)
		{
			return 0;
		}
		else
		{
			return o2.getDiscountPercentage().compareTo(o1.getDiscountPercentage());
		}		
	}
}
