package com.karniyarik.web.site;

import java.util.Comparator;

public class SiteInfoBeanNameAndImageComparator implements Comparator<SiteInfoBean>{
	@Override
	public int compare(SiteInfoBean o1, SiteInfoBean o2) {
		String logo1 = o1.getLogoURL();
		String logo2 = o2.getLogoURL();
		
		if(!logo1.contains("nologo") && !logo2.contains("nologo"))
		{
			return o1.getSiteConfig().getSiteName().compareTo(o2.getSiteConfig().getSiteName());
		}
		else if(!logo1.contains("nologo"))
		{
			return -1;	
		}
		else if(!logo2.contains("nologo"))
		{
			return 1;
		}		
		else
		{
			return o1.getSiteConfig().getSiteName().compareTo(o2.getSiteConfig().getSiteName());
		}
	}
}
