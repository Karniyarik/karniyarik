package com.karniyarik.common.config.site;

import java.util.Comparator;

public class SiteConfigNameComparator implements Comparator<SiteConfig>{
	@Override
	public int compare(SiteConfig o1, SiteConfig o2) {
		return o1.getSiteName().compareTo(o2.getSiteName());
	}
}
