package com.karniyarik.common.cache;

import java.io.InputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import com.karniyarik.common.util.ConfigurationURLUtil;
import com.karniyarik.common.util.StreamUtil;

public class CacheProvider {
	static CacheProvider instance = new CacheProvider();
	
	private CacheManager manager = null;
	
	public CacheProvider() {
		try {
			InputStream stream = StreamUtil.getStream(ConfigurationURLUtil.getCacheConfig());
			manager = new CacheManager(stream);
			stream.close();
		} 
		catch (Throwable e) 
		{
			throw new RuntimeException("Cannot load cache config", e);
		} 
	}
	
	public static CacheProvider getInstance() {
		return instance;
	}
	
	public Cache getCache(String name)
	{
		return manager.getCache(name);
	}
}
