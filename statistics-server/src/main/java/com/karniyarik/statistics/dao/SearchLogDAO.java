package com.karniyarik.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.statistics.vo.LastSearches;
import com.karniyarik.common.statistics.vo.SearchLog;
import com.karniyarik.common.statistics.vo.TopSearch;
import com.karniyarik.common.statistics.vo.TopSearches;

public class SearchLogDAO
{
	private final static String	QUERY								= "INSERT INTO TBL_SEARCH_LOG (FLD_QUERY, FLD_RESULT_COUNT, FLD_SUGGESTION, FLD_TIME, FLD_SPAM, FLD_CAT, FLD_AGENT, FLD_TIMESTAMP) " +
																		"VALUES(?,?,?,?,?,?,?,?)";
	
	private final static String	SELECT_MAX_QUERY					= "SELECT MAX(FLD_ID) FROM TBL_SEARCH_LOG WHERE FLD_CAT=";

	private final static String	SELECT_LAST_SEARCHES_QUERY			= "SELECT DISTINCT FLD_QUERY FROM TBL_SEARCH_LOG WHERE FLD_ID > ? AND FLD_RESULT_COUNT > 0 "
																			+ "AND FLD_SPAM = 0 AND FLD_CAT=? ORDER BY FLD_ID DESC LIMIT ?";
	private final static int	SELECT_LAST_SEARCHES_QUERY_LIMIT	= 50;

	private final static String	SELECT_TOP_SEARCHES_QUERY			= "SELECT FLD_QUERY,COUNT(*) AS FLD_COUNT " +
																		"FROM TBL_SEARCH_LOG WHERE " +
																		"FLD_SPAM = 0 AND FLD_CAT=? AND FLD_TIMESTAMP >= ? AND FLD_RESULT_COUNT > 0 " +
																		"GROUP BY FLD_QUERY ORDER BY FLD_COUNT DESC LIMIT ?";

//	private final static String	SELECT_DAILY_TOP_SEARCHES_QUERY		= "SELECT "
//																			+ "FLD_QUERY,"
//																			+ " COUNT(*) AS FLD_COUNT "
//																			+ "FROM "
//																			+ "TBL_SEARCH_LOG "
//																			+ "WHERE FLD_SPAM = 0 AND FLD_CAT=? AND FLD_RESULT_COUNT > 0 AND (FLD_TIMESTAMP between ? and DATE_ADD(?, INTERVAL 7 DAY))GROUP BY FLD_QUERY ORDER BY FLD_COUNT DESC LIMIT ?";

//	private final static String	QUERY_LOG_INSERT					= "INSERT INTO TBL_QUERY_LOG (FLD_QUERY, FLD_COUNT, FLD_SPAM, FLD_CAT) VALUES(?,?,?,?)";

	// private final static String SELECT_RECENT_SEARCHES_QUERY =
	// "SELECT FLD_QUERY FROM TBL_SEARCH_LOG WHERE FLD_RESULT_COUNT > 0 AND FLD_SPAM = 0 LIMIT ? OFFSET ?"
	// ;
	// private final static String SELECT_SEARCH_LOG_COUNT_QUERY =
	// "SELECT COUNT(*) FROM TBL_SEARCH_LOG WHERE FLD_SPAM = 0 AND FLD_RESULT_COUNT > 0"
	// ;

	private final static String	SELECT_TOP_SEARCHES_QUERY_2		= "SELECT "
		+ "FLD_QUERY,"
		+ " COUNT(*) AS FLD_COUNT "
		+ "FROM "
		+ "TBL_SEARCH_LOG "
		+ "WHERE FLD_SPAM = 0 AND FLD_CAT=? AND FLD_RESULT_COUNT > 0 AND (FLD_TIMESTAMP between ? and ?) GROUP BY FLD_QUERY ORDER BY FLD_COUNT DESC LIMIT ?";

	public static void saveSearchLogs(List<SearchLog> searchLogList)
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(QUERY);
			String query = null;
			String httpAgent = null;
			
			for (SearchLog searchLog : searchLogList)
			{
				query = searchLog.getQuery();
				if (StringUtils.isNotBlank(query))
				{
					if (query.length() > 512)
					{
						query = query.substring(0, 511);
					}
					
					httpAgent = searchLog.getHttpAgent();
					if (StringUtils.isNotBlank(httpAgent) && httpAgent.length() > 200)
					{
						httpAgent = httpAgent.substring(0, 199);
					}

					String firstSuggestion = null;

					if (searchLog.getSuggestionList() != null && searchLog.getSuggestionList().length > 0)
					{
						firstSuggestion = searchLog.getSuggestionList()[0];
					}

					statement.setString(1, query);
					statement.setInt(2, searchLog.getResultCount());
					statement.setString(3, firstSuggestion);
					statement.setDouble(4, searchLog.getTime());
					statement.setBoolean(5, searchLog.isSpam());
					statement.setInt(6, CategorizerConfig.getCategoryType(searchLog.getCategory()));
					statement.setString(7, httpAgent);
					statement.setTimestamp(8, new java.sql.Timestamp(searchLog.getDate()));
					statement.executeUpdate();
				}
			}

			connection.commit();
		}
		catch (SQLException e)
		{
			Logger.getLogger(SearchLogDAO.class).error("Cannot save search logs.", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}

	private static int getMaxId(int catType)
	{
		int id = -1;
		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(SELECT_MAX_QUERY + catType);
			resultSet = statement.executeQuery();

			if (resultSet.next())
			{
				id = resultSet.getInt(1);
			}

			connection.commit();
		}
		catch (SQLException e)
		{
			getLogger().error("Can not get max id from search log table", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return id;
	}

	public static LastSearches getLastSearches(int catType, int maxLastSearches)
	{
		LastSearches lastSearches = new LastSearches();
		lastSearches.setCategory(catType);
		List<String> result = new ArrayList<String>();
		lastSearches.setLastSearchList(result);
		int count = getMaxId(catType);

		if (count != -1)
		{
			Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
			PreparedStatement statement = null;
			ResultSet resultSet = null;

			try
			{
				statement = connection.prepareStatement(SELECT_LAST_SEARCHES_QUERY);

				int idStart = count < SELECT_LAST_SEARCHES_QUERY_LIMIT ? 0 : count - SELECT_LAST_SEARCHES_QUERY_LIMIT;

				statement.setInt(1, idStart);
				statement.setInt(2, catType);
				statement.setInt(3, maxLastSearches);

				resultSet = statement.executeQuery();

				String tmpQueryResult = new String();

				while (resultSet.next())
				{
					tmpQueryResult = resultSet.getString(1).trim();
					if (!result.contains(tmpQueryResult))
					{
						result.add(tmpQueryResult);
					}
				}

				connection.commit();
			}
			catch (Exception e)
			{
				getLogger().error("Cannot fetch last searches.", e);
			}
			finally
			{
				DBConnectionProvider.closeResources(connection, statement, resultSet);
			}
		}

		return lastSearches;
	}

	public static TopSearches getDailyTopSearches(int catType, int maxTopSearches)
	{
		TopSearches topSearches = new TopSearches();
		topSearches.setCategory(catType);
		List<TopSearch> result = new ArrayList<TopSearch>();
		topSearches.setTopSearchList(result);

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Date date = DateUtils.addMonths(new Date(), -1);
		try
		{
			statement = connection.prepareStatement(SELECT_TOP_SEARCHES_QUERY);
			statement.setInt(1, catType);
			statement.setDate(2, new java.sql.Date(date.getTime()));
			statement.setInt(3, maxTopSearches);

			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				result.add(new TopSearch(resultSet.getString(1).trim(), resultSet.getInt(2)));
			}

			connection.commit();
		}
		catch (SQLException e)
		{
			getLogger().error("Cannot fetch last searches.", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		if (result.size() > maxTopSearches)
		{
			result = result.subList(0, maxTopSearches);
		}

		return topSearches;
	}

	public static TopSearches getTopSearches(int catType, int count, long startDate, long endDate)
	{
		TopSearches topSearches = new TopSearches();
		topSearches.setCategory(catType);
		List<TopSearch> result = new ArrayList<TopSearch>();
		topSearches.setTopSearchList(result);

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);

		PreparedStatement statement = null;

		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(SELECT_TOP_SEARCHES_QUERY_2);
			statement.setInt(1, catType);
			statement.setDate(2, new java.sql.Date(startDate));
			statement.setDate(3, new java.sql.Date(endDate));
			statement.setInt(4, count);

			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				result.add(new TopSearch(resultSet.getString(1).trim(), resultSet.getInt(2)));
			}

			connection.commit();
		}
		catch (SQLException e)
		{
			getLogger().error("Cannot fetch dailiy top searches.", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		if (result.size() > count)
		{
			result = result.subList(0, count);
		}

		return topSearches;		
	}
	
	public static TopSearches getWeeklyTopSearches(int catType, int count, long date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		Date endDate = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		Date startDate = cal.getTime();

		return getTopSearches(catType, count, startDate.getTime(), endDate.getTime());
	}

	public static TopSearches getMonthlyTopSearches(int catType, int count, long date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date));
		Date endDate = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date startDate = cal.getTime();

		return getTopSearches(catType, count, startDate.getTime(), endDate.getTime());
	}

	private static Logger getLogger()
	{
		return Logger.getLogger(SearchLogDAO.class.getName());
	}
}
