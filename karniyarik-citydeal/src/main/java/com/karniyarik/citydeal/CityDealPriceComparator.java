package com.karniyarik.citydeal;

import java.util.Comparator;

public class CityDealPriceComparator implements Comparator<CityDeal>
{
	@Override
	public int compare(CityDeal o1, CityDeal o2)
	{
		if(o1.getDiscountPrice() == null && o2.getDiscountPrice()==null)
		{
			return 0;
		}
		else if(o1.getDiscountPrice() == null)
		{
			return 1;
		}
		else if(o2.getDiscountPrice() == null)
		{
			return 0;
		}
		else
		{
			return o1.getDiscountPrice().compareTo(o2.getDiscountPrice());
		}		
	}
}
