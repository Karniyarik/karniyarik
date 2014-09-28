package com.karniyarik.affiliate.sites;

import com.karniyarik.affiliate.IAffiliateSite;

public class BookingDotCom implements IAffiliateSite{
	
	public String correctUrl(String url)
	{
		StringBuffer newUrl = new StringBuffer(url);
		
		if(!url.contains("?"))
		{
			newUrl.append("?");
		}
		newUrl.append("lang=tr");
		newUrl.append("&aid=334273");
		
		return newUrl.toString();
	}
}