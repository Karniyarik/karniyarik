package com.karniyarik.web.site;

import java.util.Comparator;

public class SiteProductCountComparator implements Comparator<SiteInfoBean>{
	@Override
	public int compare(SiteInfoBean o1, SiteInfoBean o2) {
		return o2.getProductCount().compareTo(o1.getProductCount());
	}
}
