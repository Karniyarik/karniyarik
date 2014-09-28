package com.karniyarik.common.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DBConfig;

public class DBConnectionProvider
{

	public static Connection getConnection(boolean isReadOnly,
			boolean isAutoCommit, int aTransactionIsolation)
	{
		DBConfig dbConfig = KarniyarikRepository.getInstance().getConfig()
		.getConfigurationBundle().getDbConfig();
		
		return getConnection(isReadOnly, isAutoCommit, aTransactionIsolation, dbConfig.getDatasource(), dbConfig.getDBURL());
	}

	public static Connection getWebConnection(boolean isReadOnly,
			boolean isAutoCommit, int aTransactionIsolation)
	{
		DBConfig dbConfig = KarniyarikRepository.getInstance().getConfig()
		.getConfigurationBundle().getDbConfig();
		
		return getConnection(isReadOnly, isAutoCommit, aTransactionIsolation, dbConfig.getWebDatasource(), dbConfig.getWebDBURL());
	}

	
	private static Connection getConnection(boolean isReadOnly,
			boolean isAutoCommit, int aTransactionIsolation, String jndiName, String dburl)
	{
		DBConfig aConfig = KarniyarikRepository.getInstance().getConfig()
				.getConfigurationBundle().getDbConfig();

		Connection aConnection = null;

		if (StringUtils.isNotBlank(jndiName))
		{
			aConnection = getJNDIConnection(aConfig, jndiName, dburl);
		}
		else if (aConfig.isUseConnectionPool())
		{
			aConnection = getConnectionFromPool(aConfig, jndiName, dburl);
		}
		else
		{
			aConnection = getSimpleConnection(aConfig, dburl);
		}

		try
		{
			aConnection.setAutoCommit(isAutoCommit);
			aConnection.setReadOnly(isReadOnly);
			aConnection.setTransactionIsolation(aTransactionIsolation);

			if (aConnection.isClosed()) {
				getLogger().error("!!!!!!!!!!!!!!!!!!!!Connection taken from pool is closed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		
		return aConnection;
	}

	private static Connection getJNDIConnection(DBConfig aConfig, String jndiName, String dburl)
	{
		Connection aResult = null;

		DataSource aDatasource = KarniyarikRepository.getInstance().getDataSource(jndiName);

		if (aDatasource == null)
		{
			try
			{
				Context initialContext = new InitialContext();

				if (initialContext == null)
				{
					throw new RuntimeException("Cannot get JNDI context");
				}

				Context aContext = (Context) initialContext.lookup("java:/comp/env");

				aDatasource = (DataSource) aContext.lookup(jndiName);

				if (aDatasource != null)
				{
					KarniyarikRepository.getInstance().setDataSource(aDatasource, jndiName);
				}
				else
				{
					throw new RuntimeException("cannot get datasource " + aConfig);
				}
			}
			catch (Throwable e)
			{
				throw new RuntimeException("cannot get datasource " + aConfig.getDatasource(), e);
			}
		}

		try
		{
			aResult = aDatasource.getConnection();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot get connection from datasource " + aDatasource, e);
		}

		return aResult;
	}

	private static Connection getSimpleConnection(DBConfig aConfig, String dbUrl)
	{
		Connection aResult = null;

		Driver aDriver = KarniyarikRepository.getInstance().getDBDriver();

		if (aDriver == null)
		{
			try
			{
				aDriver = (Driver) Class.forName(aConfig.getDBDriverClass()).newInstance();
			}
			catch (Exception ex)
			{
				throw new RuntimeException("Check classpath. Cannot load db driver: "+ aConfig.getDBDriverClass());
			}
		}

		try
		{
			aResult = DriverManager.getConnection(dbUrl, aConfig.getDBUserName(), aConfig.getDBPassword());
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Driver loaded, but cannot connect to db: "+ dbUrl);
		}
		return aResult;
	}

	public static Connection getSimpleConnectionForInitializing(DBConfig aConfig, boolean isReadOnly, boolean isAutocommit,int aTransactionIsolation)
	{
		return getSimpleConnectionForInitializing(aConfig, isReadOnly, isAutocommit, aTransactionIsolation, aConfig.getDBURL());
	}
	
	@SuppressWarnings("unused")
	private static Connection getSimpleConnectionForInitializing(DBConfig aConfig, boolean isReadOnly, boolean isAutocommit,int aTransactionIsolation, String dburl)
	{
		Connection aResult = null;

		try
		{
			Driver aDriver = (Driver) Class.forName(aConfig.getDBDriverClass()).newInstance();
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Check classpath. Cannot load db driver: "+ aConfig.getDBDriverClass());
		}

		try
		{
			aResult = DriverManager.getConnection(dburl, aConfig.getDBUserName(), aConfig.getDBPassword());
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Driver loaded, but cannot connect to db: "+ dburl);
		}

		try
		{
			aResult.setAutoCommit(isAutocommit);
			aResult.setReadOnly(isReadOnly);
			aResult.setTransactionIsolation(aTransactionIsolation);
		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}

		return aResult;
	}

	private static Connection getConnectionFromPool(DBConfig aConfig, String jndiName, String dburl)
	{
		Connection aResult = null;

		DataSource aDataSource = KarniyarikRepository.getInstance().getDataSource(jndiName);

		if (aDataSource == null)
		{
			try
			{
				DriverAdapterCPDS aCpds = new DriverAdapterCPDS();
				aCpds.setDriver(aConfig.getDBDriverClass());
				aCpds.setUrl(dburl);
				aCpds.setUser(aConfig.getDBUserName());
				aCpds.setPassword(aConfig.getDBPassword());

				SharedPoolDataSource aSharedDataSource = new SharedPoolDataSource();
				aSharedDataSource.setConnectionPoolDataSource(aCpds);
				aSharedDataSource.setMaxActive(10);
				aSharedDataSource.setMaxWait(50);

				aDataSource = aSharedDataSource;

				KarniyarikRepository.getInstance().setDataSource(aSharedDataSource, jndiName);
			}
			catch (ClassNotFoundException e)
			{
				throw new RuntimeException("Cannot create connection pool", e);
			}
		}

		try
		{
			aResult = aDataSource.getConnection();
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Cannot get connection from pool",
					e);
		}

		return aResult;
	}

	public static void closeConnection(Connection connection)
	{
		if (connection != null) {
			try
			{
				if (!connection.isClosed())
				{
					connection.close();
				}
			}
			catch (SQLException e)
			{
				throw new RuntimeException(
						"A fatal error occured with database, cannot close connection.",
						e);
			}
		}
	}

	public static void closeResources(Connection connection,
			PreparedStatement statement, ResultSet resultSet)
	{
		if (resultSet != null) {
			try
			{
				resultSet.close();
			}
			catch (SQLException e)
			{
				getLogger().error("Can not close result set", e);
			}
		}
		
		if (statement != null) {
			try
			{
				statement.close();
			}
			catch (SQLException e)
			{
				getLogger().error("Can not close sql statement", e);
			}
		}
		
		if (connection != null) {
			try
			{
				if (!connection.isClosed()) {
					connection.close();
				}
			}
			catch (SQLException e)
			{
				getLogger().error("Can not close database connection!", e);
			}
		}
	}
	
	private static Logger getLogger() {
		return Logger.getLogger(DBConnectionProvider.class.getName());
	}

}