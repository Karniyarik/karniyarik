package com.karniyarik.common.config;

import org.junit.Test;

import com.karniyarik.common.config.system.CrawlerConfig;

public class CrawlerConfigTest
{	
	@Test
	public void testConfigurationLoading() throws Exception
	{
		CrawlerConfig config = new CrawlerConfig();
		System.out.println(config.getVisitedCacheSize());
	}
}
