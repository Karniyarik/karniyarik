package com.karniyarik.pagerank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.pagerank.histogram.HistogramValueHolder;

public class PageRankDAO
{

	private final String	rowCountQuery;
	private final String	distinctRanksQuery;
	private final String	findMaxRankQuery;
	private final String	siteName;

	public PageRankDAO(String siteName, String visitedLinksTableName)
	{
		this.siteName = siteName;
		this.rowCountQuery = "SELECT COUNT(*) FROM " + visitedLinksTableName + " WHERE FLD_HAS_PRODUCT=1";
		this.distinctRanksQuery = "SELECT COUNT(*), FLD_HIT FROM " + visitedLinksTableName + " WHERE FLD_HAS_PRODUCT=1 GROUP BY FLD_HIT ORDER BY FLD_HIT";
		this.findMaxRankQuery = "SELECT MAX(FLD_HIT) AS MAX_RANK FROM " + visitedLinksTableName;
	}

	public int findRowCountForSite()
	{
		int rowCount = 0;

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.TRUE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{

			statement = connection.prepareStatement(rowCountQuery);
			resultSet = statement.executeQuery();

			if (resultSet.next())
			{
				rowCount = resultSet.getInt(1);
			}
		}
		catch (Throwable e)
		{
			getLogger().error("Can not find row count for " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return rowCount;
	}

	public List<HistogramValueHolder> getDistinctRanks()
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<HistogramValueHolder> aResult = new ArrayList<HistogramValueHolder>();
		try
		{
			statement = connection.prepareStatement(distinctRanksQuery);
			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				aResult.add(new HistogramValueHolder(resultSet.getInt(2), resultSet.getInt(1)));
			}

			connection.commit();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not get distinct ranks for " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return aResult;
	}
	
	public int findMaxRank() throws Throwable
	{
		int maxRank = -1;
		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(findMaxRankQuery);
			resultSet = statement.executeQuery();

			if (resultSet.next())
			{
				maxRank = resultSet.getInt("MAX_RANK");
			}

			connection.commit();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not find max rank for " + siteName, e);
			throw e;
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return maxRank;
	}

	private static Logger getLogger()
	{
		return Logger.getLogger(PageRankDAO.class.getClass());
	}

}
