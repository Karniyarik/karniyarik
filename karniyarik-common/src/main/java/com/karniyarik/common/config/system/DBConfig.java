package com.karniyarik.common.config.system;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

/**
 * Configuration object for the database attributes.
 * 
 * @author siyamed
 * 
 */
@SuppressWarnings("serial")
public class DBConfig extends ConfigurationBase
{
	public DBConfig() throws Exception
	{
		super(ConfigurationURLUtil.getDBConfig());
	}

	/**
	 * The URL of the database.
	 * 
	 * @return
	 */
	public String getDBURL()
	{
		return getString("db.connection.url");
	}

	public String getWebDBURL()
	{
		return getString("webdb.connection.url");
	}

	/**
	 * The JDBC driver class to be loaded.
	 * 
	 * @return
	 */
	public String getDBDriverClass()
	{
		return getString("db.connection.driver");
	}

	/**
	 * The user name to connect to the database.
	 * 
	 * @return
	 */
	public String getDBUserName()
	{
		return getString("db.connection.username");
	}

	/**
	 * If the connections are going to be fetched from a data source on server,
	 * the jndi name of this datasource.
	 * 
	 * @return
	 */
	public String getDatasource()
	{
		return getString("db.datasource.name");
	}

	public String getWebDatasource()
	{
		return getString("webdb.datasource.name");
	}
	
	/**
	 * The password of the db user to connect to database.
	 * 
	 * @return
	 */
	public String getDBPassword()
	{
		return getString("db.connection.password");
	}

	/**
	 * When set to true, the system uses the connection pooling mechanism. This
	 * parameter defines the boolean value for using a connection pool or not.
	 * 
	 * @return
	 */
	public boolean isUseConnectionPool()
	{
		return getBoolean("db.connection.useconnectionpool", true);
	}

	public String getCrawlerStatisticsTableQuery()
	{
		return getString("tables.crawlerstatistics");
	}

	public String getLinksVisitedTableQuery()
	{
		return getString("tables.linksvisited");
	}

	public String getScheduleInfoTableQuery()
	{
		return getString("tables.schedule");
	}

	public String getSiteStatTableQuery() {
		return getString("tables.sitestat");
	}
	
	public String getProductStatTableQuery()
	{
		return getString("tables.productstat");
	}

	public String getHitStatTableQuery()
	{
		return getString("tables.hitstat");
	}

	public String getViewStatTableQuery()
	{
		return getString("tables.viewstat");
	}

	public String getSearchLogTableQuery()
	{
		return getString("tables.searchlog");
	}
	
	public String getApiStatTableQuery() {
		return getString("tables.apistat");
	}

	public String getQueryStatTableQuery() {
		return getString("tables.querystat");
	}

	public String getCityDealEmailTableQuery() {
		return getString("tables.citydealemail");
	}

	public String getSiteClickStatTableQuery()
	{
		return getString("tables.siteclickstat");
	}

}