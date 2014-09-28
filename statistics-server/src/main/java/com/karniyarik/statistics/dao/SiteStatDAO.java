package com.karniyarik.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.statistics.vo.SiteStat;

public class SiteStatDAO
{
	private final Logger	logger;
	private final String	saveQuery;
	private final String	getCurrentSiteStatQuery;

	public SiteStatDAO()
	{
		this.logger = Logger.getLogger(this.getClass().getName());
		saveQuery = "INSERT INTO TBL_SITE_STAT (FLD_SITE_NAME, FLD_PRODUCT_COUNT, FLD_DATE, FLD_DATAFEED) VALUES (?, ?, ?, ?)";
		getCurrentSiteStatQuery = "SELECT * FROM TBL_SITE_STAT WHERE FLD_SITE_NAME=? AND FLD_DATE=(SELECT MAX(FLD_DATE) FROM TBL_SITE_STAT WHERE FLD_SITE_NAME=?)";
	}

	public void saveSiteStat(SiteStat stat)
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(saveQuery);
			statement.setString(1, stat.getSiteName());
			statement.setInt(2, stat.getProductCount());
			statement.setTimestamp(3, new java.sql.Timestamp(stat.getDate()));
			statement.setBoolean(4, stat.isDatafeed());
			statement.executeUpdate();
			connection.commit();
		}
		catch (Throwable e)
		{
			logger.error("Can not insert site stat", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}

	public SiteStat getCurrentSiteStat(String siteName)
	{
		SiteStat stat = new SiteStat();

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = connection.prepareStatement(getCurrentSiteStatQuery);
			statement.setString(1, siteName);
			statement.setString(2, siteName);

			resultSet = statement.executeQuery();

			if (resultSet.next())
			{
				stat.setSiteName(siteName);
				stat.setProductCount(resultSet.getInt("FLD_PRODUCT_COUNT"));
				stat.setDatafeed(resultSet.getBoolean("FLD_DATAFEED"));
				stat.setDate(resultSet.getTimestamp("FLD_DATE").getTime());
			}

			connection.commit();
		}
		catch (SQLException e)
		{
			logger.error("Can not get site stat for " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return stat;
	}
	
}
