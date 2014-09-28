package com.karniyarik.crawler.linkgraph;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.crawler.util.DAOUtil;
import com.mysql.jdbc.Statement;

public class VisitedLinksDAO
{
	private final String			visitedLinksTableName;
	private final JobExecutionStat	crawlerStatistics;
	private final String			siteName;
	private final Logger			siteLogger;
	private final String			INSERT_QUERY;
	private final String			UPDATE_QUERY;
	private final String			LOAD_URL_QUERY;
	private final String			LOAD_CACHE_QUERY;
	private final String			DB_EMPTY_QUERY;

	public VisitedLinksDAO(String siteName, String visitedLinksTableName, JobExecutionStat crawlerStatistics, Logger siteLogger)
	{
		this.siteName = siteName;
		this.visitedLinksTableName = visitedLinksTableName;
		this.crawlerStatistics = crawlerStatistics;
		this.siteLogger = siteLogger;
		this.INSERT_QUERY = "INSERT INTO " + visitedLinksTableName + " (FLD_URL, FLD_HIT, FLD_LOWERCASE_URL, FLD_FETCH_TIME, FLD_HAS_PRODUCT, FLD_IS_VISITED) VALUES(?,?,?,?,?,?)";
		this.UPDATE_QUERY = "UPDATE " + visitedLinksTableName + " SET FLD_HIT=? , FLD_FETCH_TIME=?, FLD_HAS_PRODUCT=?, FLD_IS_VISITED=? WHERE FLD_ID= ?";
		this.LOAD_URL_QUERY = "SELECT FLD_ID, FLD_URL, FLD_HIT, FLD_FETCH_TIME, FLD_HAS_PRODUCT, FLD_IS_VISITED FROM " + visitedLinksTableName + " WHERE FLD_LOWERCASE_URL=?";
		this.LOAD_CACHE_QUERY = "SELECT FLD_ID, FLD_URL, FLD_HIT, FLD_FETCH_TIME, FLD_HAS_PRODUCT, FLD_IS_VISITED FROM " + visitedLinksTableName + " ORDER BY FLD_HIT DESC LIMIT ?";
		this.DB_EMPTY_QUERY = "SELECT COUNT(*) FROM " + visitedLinksTableName;
	}

	public void storeLinks(Collection<Link> aListToBeStored)
	{
		List<Link> updateList = new ArrayList<Link>();
		List<Link> insertList = new ArrayList<Link>();

		for (Link link : aListToBeStored)
		{
			if (link.getID() != 0)
			{
				updateList.add(link);
			}
			else
			{
				if (DAOUtil.canSaveURL(link.getURL()))
				{
					insertList.add(link);
				}
				else
				{
					siteLogger.error("Cannot store visited url, since the string size " + "is bigger than the columns size in db. " + link.getURL());
				}
			}
		}

		updateLinks(updateList);
		insertLinks(insertList);
	}

	private void insertLinks(List<Link> list)
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
			for (Link link : list)
			{
				statement.setString(1, link.getURL());
				statement.setInt(2, link.getHitCount());
				statement.setString(3, StringUtil.toLowerCase(link.getURL()));
				statement.setLong(4, link.getFetchTime());
				statement.setBoolean(5, link.isHasProduct());
				statement.setBoolean(6, link.isVisited());

				statement.executeUpdate();
				resultSet = statement.getGeneratedKeys();

				if (resultSet.next())
				{
					link.setID(resultSet.getInt(1));
				}
			}

			connection.commit();
		}
		catch (SQLException e)
		{
			siteLogger.error("Could not insert links!", e);
			throw new RuntimeException("Error occured with the database connection", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}
	}

	private void updateLinks(List<Link> list)
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement updateStatement = null;

		try
		{
			updateStatement = connection.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			for (Link link : list)
			{
				updateStatement.setInt(1, link.getHitCount());
				updateStatement.setLong(2, link.getFetchTime());
				updateStatement.setBoolean(3, link.isHasProduct());
				updateStatement.setBoolean(4, link.isVisited());
				updateStatement.setInt(5, link.getID());
				updateStatement.executeUpdate();
			}

			connection.commit();
		}
		catch (Throwable e)
		{
			siteLogger.error("Could not update links!", e);
			throw new RuntimeException("Error occured with the database connection", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, updateStatement, null);
		}
	}

	public Link load(String url)
	{
		Link aResult = null;

		url = StringUtil.toLowerCase(url);

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_COMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(LOAD_URL_QUERY);
			statement.setString(1, url);
			resultSet = statement.executeQuery();
			if (resultSet.next())
			{
				aResult = new Link(resultSet.getString("FLD_URL"));
				aResult.setID(resultSet.getInt("FLD_ID"));
				aResult.setHitCount(resultSet.getInt("FLD_HIT"));
				aResult.setFetchTime(resultSet.getLong("FLD_FETCH_TIME"));
				aResult.setHasProduct(resultSet.getBoolean("FLD_HAS_PRODUCT"));
				aResult.setIsVisited(resultSet.getBoolean("FLD_IS_VISITED"));
			}

			connection.commit();
		}
		catch (Throwable e)
		{
			siteLogger.error("Could not load url " + url, e);
			throw new RuntimeException("Error occured with the database connection", e);
		}
		finally
		{
			crawlerStatistics.dbOperation();
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return aResult;
	}

	/**
	 * Sets the State flags of links visited table to "DELETED".
	 */
	public void clearAll()
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_SERIALIZABLE);

		StringBuffer aVisitedLinkQuery = new StringBuffer();
		aVisitedLinkQuery.append("TRUNCATE TABLE ");
		aVisitedLinkQuery.append(visitedLinksTableName);

		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(aVisitedLinkQuery.toString());
			statement.executeUpdate();
			connection.commit();
		}
		catch (SQLException e)
		{
			siteLogger.error("Could not clear all visited links for " + siteName, e);
			throw new RuntimeException("Cannot clean links to visit table");
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}

	public List<Link> loadCachedData(long size)
	{
		List<Link> aResult = new ArrayList<Link>();

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_SERIALIZABLE);
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(LOAD_CACHE_QUERY);
			statement.setLong(1, size);
			statement.setFetchSize(1000);

			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				Link aLink = new Link(resultSet.getString("FLD_URL"));
				aLink.setID(resultSet.getInt("FLD_ID"));
				aLink.setHitCount(resultSet.getInt("FLD_HIT"));
				aLink.setHasProduct(resultSet.getBoolean("FLD_HAS_PRODUCT"));
				aLink.setFetchTime(resultSet.getLong("FLD_FETCH_TIME"));
				aLink.setIsVisited(resultSet.getBoolean("FLD_IS_VISITED"));

				aResult.add(aLink);
			}

			connection.commit();
		}
		catch (SQLException e)
		{
			siteLogger.error("Could not load cache data for " + siteName, e);
			throw new RuntimeException("Cannot load links to visit table", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return aResult;
	}

	public boolean isDBEmpty()
	{
		boolean result = true;
		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_COMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = connection.prepareStatement(DB_EMPTY_QUERY);
			resultSet = statement.executeQuery();

			if (resultSet.next())
			{
				int aCount = resultSet.getInt(1);

				if (aCount > 0)
				{
					result = false;
				}
			}

			connection.commit();
		}
		catch (Throwable e)
		{
			siteLogger.error("Cannot get visited table count for site " + siteName, e);
			throw new RuntimeException("Cannot get visited table count for site " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return result;
	}
}