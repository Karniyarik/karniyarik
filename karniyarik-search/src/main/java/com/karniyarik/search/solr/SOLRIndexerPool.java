package com.karniyarik.search.solr;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.CategoryConfig;

public class SOLRIndexerPool
{
	private Map<String, GenericObjectPool>	poolMap = new HashMap<String, GenericObjectPool>();
	public static SOLRIndexerPool instance = null;
	
	private SOLRIndexerPool()
	{
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		
		for(CategoryConfig config: categorizerConfig.getCategoryConfigList())
		{
			String coreName = config.getCoreName();
			GenericObjectPool pool = new GenericObjectPool(new SOLRIndexerPoolFactory(coreName, config.getName()));
			poolMap.put(config.getName(), pool);
			pool.setMaxActive(4);
			pool.setMaxIdle(0);
			pool.setMaxWait(2*24*60*60*1000);
			pool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
		}
	}
	
	public static SOLRIndexerPool getInstance()
	{
		if(instance == null)
		{
			instance = new SOLRIndexerPool();
		}
		return instance;
	}
	
	public SOLRIndexerProxy getIndexer(String catName)
	{
		try
		{
			return (SOLRIndexerProxy) poolMap.get(catName).borrowObject();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot borrow object from SFTP session pool", e);
		}
	}
	
	public void returnIndexer(SOLRIndexerProxy solr)
	{
		try
		{
			poolMap.get(solr.getCategoryName()).returnObject(solr);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot borrow object from SFTP session pool", e);
		}
	}
	
	public static void main(String[] args)
	{
	}
}
