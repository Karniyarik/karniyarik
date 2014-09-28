package com.karniyarik.categorizer.supervisor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.exception.KarniyarikBaseException;

public class DBDataSource
{
	private Connection			mConnection	= null;
	private PreparedStatement	mStatement	= null;
	private ResultSet			mResultSet	= null;

	public DBDataSource()
	{

	}

	public void loadData(int aStart, int anEnd)
	{
		loadData(aStart, anEnd, null);
	}
	
	public void loadData(String aSiteName)
	{
		loadData(0, 0, aSiteName);
	}
	
	public void loadData(int aStart, int anEnd, String aSiteName)
	{
		mConnection = DBConnectionProvider.getConnection(true, false,Connection.TRANSACTION_READ_COMMITTED);
		try
		{
			String aQuery = null; 
				
			if(aSiteName == null)
			{
				aQuery = "SELECT FLD_NAME, FLD_BREADCRUMB, FLD_BRAND FROM TBL_PRODUCTS LIMIT " + anEnd + " OFFSET " + aStart;	
			}
			else
			{
				aQuery = "SELECT FLD_NAME, FLD_BREADCRUMB, FLD_BRAND, L.FLD_LINK FROM TBL_PRODUCTS, TBL_LINKS L WHERE FLD_LINK_ID=L.FLD_ID AND L.FLD_SITE_NAME=" + aSiteName ;
			}			

			mStatement = mConnection.prepareStatement(aQuery);

			mResultSet = mStatement.executeQuery();
		}
		catch (Throwable t)
		{
			throw new KarniyarikBaseException("Cannot get products.", t);
		}
	}

	public boolean next()
	{
		if (mResultSet != null)
		{
			try
			{
				return mResultSet.next();
			}
			catch (SQLException e)
			{
				throw new KarniyarikBaseException("Cannot get next product", e);
			}
		}

		return false;
	}

	public String getProductName()
	{
		try
		{
			return mResultSet.getString("FLD_NAME");
		}
		catch (SQLException e)
		{
			throw new KarniyarikBaseException("Cannot get product name", e);
		}
	}

	public String getBreadCrumb()
	{
		try
		{
			return mResultSet.getString("FLD_BREADCRUMB");
		}
		catch (SQLException e)
		{
			throw new KarniyarikBaseException("Cannot get product breadcrumb", e);
		}
	}

	public String getBrand()
	{
		try
		{
			return mResultSet.getString("FLD_BRAND");
		}
		catch (SQLException e)
		{
			throw new KarniyarikBaseException("Cannot get product brand", e);
		}
	}

	public String getURL()
	{
		try
		{
			return mResultSet.getString("L.FLD_LINK");
		}
		catch (SQLException e)
		{
			throw new KarniyarikBaseException("Cannot get product link", e);
		}
	}
	
	public void close()
	{
		try
		{
			if (mResultSet != null)
			{
				mResultSet.close();
			}

			if (mStatement != null)
			{
				mStatement.close();
			}
			
			mConnection.commit();
		}
		catch (SQLException e)
		{
			throw new KarniyarikBaseException("Cannot close database connection", e);
		}

		if (mConnection != null)
		{
			DBConnectionProvider.closeConnection(mConnection);
		}
	}
}
