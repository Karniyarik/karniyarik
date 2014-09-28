package com.karniyarik.categorizer.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DBConfig;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.vo.Product;

public class DBIO
{
	private static DBIO	reader	= null;

	private DBIO()
	{
	}

	public static DBIO getReader()
	{
		if (reader == null)
		{
			reader = new DBIO();
			reader.init();
		}

		return reader;
	}

	public void init()
	{
	}

	public List<Product> readAll(String sitename)
	{
		List<Product> result = new ArrayList<Product>();

		Connection connection = DBConnectionProvider.getConnection(true, false, Connection.TRANSACTION_READ_UNCOMMITTED);

		String query = "select FLD_NAME, FLD_BREADCRUMB, FLD_BRAND from " + sitename.toUpperCase() + "_PRODUCTS";

		try
		{
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();

			while (resultSet.next())
			{
				Product product = new Product();
				product.setName(resultSet.getString("FLD_NAME"));
				product.setBreadcrumb(resultSet.getString("FLD_BREADCRUMB"));
				product.setBrand(resultSet.getString("FLD_BRAND"));
				product.setPrice(resultSet.getDouble("FLD_PRICE"));
				result.add(product);
			}
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DBConnectionProvider.closeConnection(connection);
		}

		return result;
	}

	public ProductIterator iterate(String sitename)
	{
		DBConfig aConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDbConfig();
		Connection connection = DBConnectionProvider.getSimpleConnectionForInitializing(aConfig, true, false, Connection.TRANSACTION_READ_UNCOMMITTED);
		// Connection connection = DBConnectionProvider.getConnection(true,
		// false, Connection.TRANSACTION_READ_UNCOMMITTED);

		String query = "select FLD_NAME, FLD_BREADCRUMB, FLD_BRAND, FLD_PRICE from " + sitename + "_PRODUCTS";

		try
		{
			PreparedStatement stmt = connection.prepareStatement(query);
			ResultSet resultSet = stmt.executeQuery();

			return new ProductIterator(connection, resultSet);
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}

}
