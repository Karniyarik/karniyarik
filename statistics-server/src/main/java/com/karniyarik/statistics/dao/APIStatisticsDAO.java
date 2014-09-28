package com.karniyarik.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.db.DatabaseTableCreator;
import com.karniyarik.statistics.vo.DailyProductStatistics;

public class APIStatisticsDAO
{

	private final Logger	logger;
	private final String	saveDailyProductStatsQuery;
	private final String	apiKey;
	private final String	apiStatTable;

	public APIStatisticsDAO(String apiKey, String apiStatTable)
	{
		logger = Logger.getLogger(this.getClass().getName());
		this.apiKey = apiKey;
		this.apiStatTable = apiStatTable;
		saveDailyProductStatsQuery = "INSERT INTO " + apiStatTable
				+ " (FLD_URL, FLD_SITE_NAME, FLD_NAME, FLD_PRODUCT_CATEGORY, FLD_LISTING_VIEW, FLD_LISTING_HIT, FLD_SPONSOR_VIEW, FLD_SPONSOR_HIT, FLD_DATE, FLD_QUERIES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

	public void saveDailyProductStats(Collection<DailyProductStatistics> list)
	{
		DatabaseTableCreator tableCreator = new DatabaseTableCreator(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDbConfig());
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;

		try
		{
			tableCreator.createApiStatTable(apiStatTable);
			statement = connection.prepareStatement(saveDailyProductStatsQuery);
			for (DailyProductStatistics stat : list)
			{
				statement.setString(1, stat.getUrl());
				statement.setString(2, stat.getSiteName());
				statement.setString(3, stat.getName());
				statement.setString(4, stat.getProductCategory());
				statement.setInt(5, stat.getListingViews());
				statement.setInt(6, stat.getListingClicks());
				statement.setInt(7, stat.getSponsorViews());
				statement.setInt(8, stat.getSponsorClicks());
				statement.setDate(9, new java.sql.Date(stat.getDate()));
				statement.setString(10, stat.getQueryStatisticString());
				statement.executeUpdate();
			}
			connection.commit();
		}
		catch (Throwable e)
		{
			logger.error(apiKey + " can not insert daily product statistics", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}
}
