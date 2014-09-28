package com.karniyarik.citydeal;

import java.util.Comparator;

public class CityDealDiscountAmountComparator implements Comparator<CityDeal>
{
	@Override
	public int compare(CityDeal o1, CityDeal o2)
	{
		if(o1.getDiscountAmount() == null && o2.getDiscountAmount()==null)
		{
			return 0;
		}
		else if(o1.getDiscountAmount() == null)
		{
			return 1;
		}
		else if(o2.getDiscountAmount() == null)
		{
			return 0;
		}
		else
		{
			return o2.getDiscountAmount().compareTo(o1.getDiscountAmount());	
		}		
	}
}
