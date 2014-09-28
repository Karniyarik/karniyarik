package com.karniyarik.common;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.karniyarik.common.config.KarniyarikConfig;

/**
 * Common repository to keep the reusable, one time instantiated objects.
 * 
 * @author siyamed
 * 
 */
public class KarniyarikRepository
{
	private KarniyarikConfig			mConfig					= null;
	private Driver						mDBDriver				= null;
	private Map<String, DataSource>		mDataSource				= new HashMap<String, DataSource>();

	private static KarniyarikRepository	mInstance				= null;

	private KarniyarikRepository()
	{
		mConfig = new KarniyarikConfig();
	}

	public static KarniyarikRepository getInstance()
	{
		if (mInstance == null)
		{
			synchronized (KarniyarikRepository.class)
			{
				if (mInstance == null)
				{
					mInstance = new KarniyarikRepository();
				}
			}
		}

		return mInstance;
	}

	public DataSource getDataSource(String datasourceName)
	{
		return mDataSource.get(datasourceName);
	}

	public void setDataSource(DataSource aDataSource, String name)
	{
		mDataSource.put(name, aDataSource);
	}

	public KarniyarikConfig getConfig()
	{
		return mConfig;
	}

	public Driver getDBDriver()
	{
		return mDBDriver;
	}

	public void setDBDriver(Driver aDriver)
	{
		mDBDriver = aDriver;
	}

}
