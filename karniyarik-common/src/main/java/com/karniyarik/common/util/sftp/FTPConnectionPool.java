package com.karniyarik.common.util.sftp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool;

import com.jcraft.jsch.Session;

public class FTPConnectionPool
{
	private GenericObjectPool	pool = null;
	public static FTPConnectionPool instance = null;
	
	private FTPConnectionPool()
	{
		pool = new GenericObjectPool(new FTPConnectionPoolFactory());
		pool.setMaxActive(2);
		pool.setMaxIdle(2);
		pool.setMaxWait(2*24*60*60*1000);
		pool.setTestOnBorrow(true);
		pool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
	}
	
	public static FTPConnectionPool getInstance()
	{
		if(instance == null)
		{
			instance = new FTPConnectionPool();
		}
		return instance;
	}
	
	public Session getSession()
	{
		try
		{
			return (Session) pool.borrowObject();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot borrow object from SFTP session pool", e);
		}
	}
	
	public void returnSession(Session session)
	{
		try
		{
			pool.returnObject(session);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot borrow object from SFTP session pool", e);
		}
	}
	
	public static void main(String[] args)
	{
		List<Session> list = new ArrayList<Session>();
		for(int i = 0; i < 10; i++)
		{
			list.add(FTPConnectionPool.getInstance().getSession());
			System.out.println("--- " + i);
		}
		
	}
}
