package com.karniyarik.categorizer.io;

import java.sql.Connection;
import java.sql.ResultSet;

import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.vo.Product;

public class ProductIterator
{
	private Connection	connection	= null;
	private ResultSet	resultSet	= null;

	public ProductIterator()
	{
		// TODO Auto-generated constructor stub
	}

	public ProductIterator(Connection connection, ResultSet resultSet)
	{
		this.connection = connection;
		this.resultSet = resultSet;
	}

	public Product next()
	{
		try
		{
			if (resultSet.next())
			{
				Product result = new Product();
				result.setName(resultSet.getString("FLD_NAME"));
				result.setBreadcrumb(resultSet.getString("FLD_BREADCRUMB"));
				result.setBrand(resultSet.getString("FLD_BRAND"));
				result.setPrice(resultSet.getDouble("FLD_PRICE"));
				return result;
			}
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}

		return null;
	}

	public void close()
	{
		DBConnectionProvider.closeConnection(connection);
	}
}
