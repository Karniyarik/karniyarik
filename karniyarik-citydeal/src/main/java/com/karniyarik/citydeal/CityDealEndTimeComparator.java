package com.karniyarik.citydeal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class CityDealEndTimeComparator implements Comparator<CityDeal>{
	
	@Override
	public int compare(CityDeal o1, CityDeal o2) {
		Date date = new Date();
		
		int paidComparison = Boolean.valueOf(o2.isPaid()).compareTo(o1.isPaid());
		
		long o1Time = date.getTime() - o1.getEndDate().getTime();
		long o2Time = date.getTime() - o2.getEndDate().getTime();
		
		if(o1Time * o2Time > 0 && paidComparison != 0)
		{
			return paidComparison;	
		}
			
		if(o1Time * o2Time < 0)
		{
			o1Time = o1Time * -1;
			o2Time = o2Time * -1;
		}
		
		int timeComparison = Long.valueOf(o2Time).compareTo(o1Time);
		
		return timeComparison;
	} 
	
	public static void main(String[] args)
	{
		Object[][] data = new Object[][]{{-10, true, 1}, {-5, false, 2}, {5, true, 3}, {10, false, 4}};
		
		List<CityDeal> testDeals = new ArrayList<CityDeal>();
		
		for(int index=0; index<data.length; index++)
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, (Integer) data[index][0]);
			CityDeal deal = new CityDeal();
			deal.setEndDate(cal.getTime());
			deal.setPaid((Boolean) data[index][1]);
			deal.setId((Integer) data[index][2]);
			deal.setProductURL("");
			testDeals.add(deal);
		}
		
		Collections.sort(testDeals, new CityDealEndTimeComparator());
		
		for(CityDeal deal: testDeals)
		{
			System.out.println(deal.getId() + " - " + deal.getEndDate() + " - " + deal.isPaid());
		}

	}
}
