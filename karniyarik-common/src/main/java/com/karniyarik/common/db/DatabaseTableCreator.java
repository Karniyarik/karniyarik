package com.karniyarik.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.karniyarik.common.config.system.DBConfig;

public class DatabaseTableCreator
{

	private final Logger	logger;
	private final DBConfig	dbConfig;
	
	private final String DROP_QUERY = "DROP TABLE IF EXISTS %s";

	public DatabaseTableCreator(DBConfig dbConfig)
	{
		this.logger = Logger.getLogger(this.getClass().getName());
		this.dbConfig = dbConfig;
	}

	public void createCrawlerStatisticsTable() throws Throwable
	{
		executeQuery(dbConfig.getCrawlerStatisticsTableQuery());
	}

	public void createScheduleInfoTable() throws Throwable
	{
		executeQuery(dbConfig.getScheduleInfoTableQuery());
	}

	public void createSearchLogTable() throws Throwable
	{
		executeQuery(dbConfig.getSearchLogTableQuery());
	}
	
	public void createVisitedLinksTable(String tableName) throws Throwable {
		executeQuery(String.format(dbConfig.getLinksVisitedTableQuery(), new Object[] { tableName }));
	}

	public void dropAndCreateVisitedLinksTable(String tableName) throws Throwable {
		executeQuery(String.format(DROP_QUERY, new Object[] {tableName}));
		executeQuery(String.format(dbConfig.getLinksVisitedTableQuery(), new Object[] { tableName }));
	}

	public void createSiteStatTable() throws Throwable 
	{
		executeQuery(dbConfig.getSiteStatTableQuery());
	}
	
	public void createSiteClickStatTable() throws Throwable
	{
		executeQuery(dbConfig.getSiteClickStatTableQuery());
	}
	
	public void createProductStatTable(String tableName) throws Throwable
	{
		executeQuery(String.format(dbConfig.getProductStatTableQuery(), new Object[] { tableName }));
	}
	
	public void createApiStatTable(String tableName) throws Throwable
	{
		executeQuery(String.format(dbConfig.getApiStatTableQuery(), new Object[] { tableName }));
	}

	public void createHitStatTable(String tableName) throws Throwable
	{
		executeQuery(dbConfig.getHitStatTableQuery());
	}

	public void createViewStatTable() throws Throwable
	{
		executeQuery(dbConfig.getViewStatTableQuery());
	}

	public void createCityDealEmailTable() 
	{
		try {
			executeQuery(dbConfig.getCityDealEmailTableQuery());
		} catch (Throwable e) {
			throw new RuntimeException("Cannot create City Deals Email Table", e);
		}
	}

	private void executeQuery(String query) throws Throwable
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(query);
			statement.executeUpdate();
			connection.commit();
		}
		catch (Throwable e)
		{
			logger.error("Could not execute query " + query, e);
			throw e;
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}

}
