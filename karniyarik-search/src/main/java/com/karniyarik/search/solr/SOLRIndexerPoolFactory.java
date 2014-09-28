package com.karniyarik.search.solr;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class SOLRIndexerPoolFactory extends BasePoolableObjectFactory
{
	private String coreName;
	private String categoryName;
	
	public SOLRIndexerPoolFactory(String coreName, String categoryName)
	{
		this.coreName = coreName;
		this.categoryName = categoryName;
	}
	
	@Override
	public Object makeObject() throws Exception
	{
		return new SOLRIndexerProxy(coreName, categoryName);
	}
	
	@Override
	public void passivateObject(Object obj) throws Exception
	{
		SOLRIndexerProxy solr = (SOLRIndexerProxy) obj; 
		solr.close();
	}
	
	@Override
	public boolean validateObject(Object obj)
	{
		return true;
	}
	
	@Override
	public void activateObject(Object obj) throws Exception
	{
		SOLRIndexerProxy solr = (SOLRIndexerProxy) obj; 
		solr.init();
	}
	
	@Override
	public void destroyObject(Object obj) throws Exception
	{
		try
		{
			SOLRIndexerProxy solr = (SOLRIndexerProxy) obj; 
			solr.close();
		}
		catch (Exception e)
		{
			//do nothing
		}
	}
}
