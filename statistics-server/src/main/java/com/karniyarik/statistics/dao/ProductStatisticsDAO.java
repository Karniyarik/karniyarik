package com.karniyarik.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.db.DatabaseTableCreator;
import com.karniyarik.common.statistics.vo.DailyStat;
import com.karniyarik.common.statistics.vo.DailyStats;
import com.karniyarik.common.statistics.vo.ProductStat;
import com.karniyarik.common.statistics.vo.ProductStatSortOptions;
import com.karniyarik.common.statistics.vo.ProductStats;
import com.karniyarik.common.statistics.vo.SiteSponsorStat;
import com.karniyarik.common.statistics.vo.SponsorStat;
import com.karniyarik.statistics.vo.DailyProductStatistics;

public class ProductStatisticsDAO
{
	private final Logger	logger;

	private final String	siteName;
	private final String	productStatTable;

	private final String	saveDailyProductStatsQuery;
	private final String	getSponsorStatsFromDateQuery;
	private final String	getDailyStatsByUrlQuery;
	private final String	getProductStatsQuery;

	public ProductStatisticsDAO(String siteName, String productStatTable)
	{
		logger = Logger.getLogger(this.getClass().getName());
		this.siteName = siteName;
		this.productStatTable = productStatTable;
		saveDailyProductStatsQuery = "INSERT INTO " + productStatTable
				+ " (FLD_URL, FLD_NAME, FLD_LISTING_VIEW, FLD_LISTING_HIT, FLD_SPONSOR_VIEW, FLD_SPONSOR_HIT, FLD_DATE, FLD_QUERIES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		getSponsorStatsFromDateQuery = "SELECT FLD_URL, SUM(FLD_SPONSOR_VIEW) AS VIEWS, SUM(FLD_SPONSOR_HIT) AS HITS FROM " + productStatTable
				+ " WHERE TO_DAYS(FLD_DATE) >= TO_DAYS(?) GROUP BY FLD_URL";
		getProductStatsQuery = "SELECT FLD_URL, FLD_NAME, SUM(FLD_LISTING_VIEW) AS LISTING_VIEWS, SUM(FLD_SPONSOR_VIEW) AS SPONSOR_VIEWS, SUM(FLD_LISTING_HIT) AS LISTING_HITS, SUM(FLD_SPONSOR_HIT) AS SPONSOR_HITS FROM "
				+ productStatTable + " WHERE TO_DAYS(FLD_DATE) >= TO_DAYS(?) AND TO_DAYS(FLD_DATE) <= TO_DAYS(?) GROUP BY FLD_URL";
		getDailyStatsByUrlQuery = "SELECT FLD_DATE, SUM(FLD_LISTING_VIEW) AS LVIEWS, SUM(FLD_SPONSOR_VIEW) AS SVIEWS, SUM(FLD_LISTING_HIT) AS LHITS, SUM(FLD_SPONSOR_HIT) AS SHITS FROM "
				+ productStatTable + " WHERE TO_DAYS(FLD_DATE) >= TO_DAYS(?) AND TO_DAYS(FLD_DATE) <= TO_DAYS(?) AND FLD_URL=? GROUP BY FLD_DATE";
	}

	public void saveDailyProductStats(Collection<DailyProductStatistics> list)
	{

		DatabaseTableCreator tableCreator = new DatabaseTableCreator(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDbConfig());
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;

		try
		{
			tableCreator.createProductStatTable(productStatTable);
			statement = connection.prepareStatement(saveDailyProductStatsQuery);
			for (DailyProductStatistics stat : list)
			{
				statement.setString(1, stat.getUrl());
				statement.setString(2, stat.getName());
				statement.setInt(3, stat.getListingViews());
				statement.setInt(4, stat.getListingClicks());
				statement.setInt(5, stat.getSponsorViews());
				statement.setInt(6, stat.getSponsorClicks());
				statement.setDate(7, new java.sql.Date(stat.getDate()));
				statement.setString(8, stat.getQueryStatisticString());
				statement.executeUpdate();
			}
			connection.commit();
		}
		catch (Throwable e)
		{
			logger.error(siteName + " can not insert daily product statistics", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}

	public SiteSponsorStat getSiteSponsorStatsFromDate(long date)
	{
		SiteSponsorStat siteSponsorStat = new SiteSponsorStat();
		siteSponsorStat.setSiteName(siteName);
		List<SponsorStat> sponsorStatList = new ArrayList<SponsorStat>();
		siteSponsorStat.setSponsorStatList(sponsorStatList);

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = connection.prepareStatement(getSponsorStatsFromDateQuery);
			statement.setDate(1, new java.sql.Date(date));
			resultSet = statement.executeQuery();
			SponsorStat stat;
			while (resultSet.next())
			{
				stat = new SponsorStat();
				stat.setUrl(resultSet.getString("FLD_URL"));
				stat.setViews(resultSet.getInt("VIEWS"));
				stat.setClicks(resultSet.getInt("HITS"));
				sponsorStatList.add(stat);
			}
		}
		catch (SQLException e)
		{
			logger.error("Can not get sponsor statistics by start date for " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return siteSponsorStat;
	}

	public DailyStats getDailyStatsByUrl(String url, long startDate, long endDate)
	{
		Map<Integer, DailyStat> statMap = new HashMap<Integer, DailyStat>();
		
		DailyStats dailyStats = new DailyStats();
		List<DailyStat> dailyStatList = new ArrayList<DailyStat>();
		dailyStats.setDailyStatList(dailyStatList);

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		startDate = ClickStatisticsDAOUtil.resetStartTime(startDate);
		endDate = ClickStatisticsDAOUtil.resetEndTime(endDate);

		Calendar statCal = Calendar.getInstance();
		
		try
		{
			statement = connection.prepareStatement(getDailyStatsByUrlQuery);
			statement.setDate(1, new java.sql.Date(startDate));
			statement.setDate(2, new java.sql.Date(endDate));
			statement.setString(3, url);
			resultSet = statement.executeQuery();
			DailyStat stat;
			while (resultSet.next())
			{
				stat = new DailyStat();
				Date statDate = new Date(resultSet.getDate("FLD_DATE").getTime());
				stat.setDate(statDate.getTime());
				stat.setListingViews(resultSet.getInt("LVIEWS"));
				stat.setSponsorViews(resultSet.getInt("SVIEWS"));
				stat.setListingClicks(resultSet.getInt("LHITS"));
				stat.setSponsorClicks(resultSet.getInt("SHITS"));
				stat.setTotalViews(stat.getListingViews() + stat.getSponsorViews());
				stat.setTotalClicks(stat.getListingClicks() + stat.getSponsorClicks());
				statCal.setTime(statDate);
				int statDayOfYear = statCal.get(Calendar.DAY_OF_YEAR);
				statMap.put(statDayOfYear, stat);
			}
		}
		catch (SQLException e)
		{
			logger.error("Can not get daily statistics for url " + url, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}
		ClickStatisticsDAOUtil.fillEmptyDates(startDate, endDate, statMap, dailyStatList);
		return dailyStats;
	}

	public ProductStats getProductStats(long startDate, long endDate, ProductStatSortOptions options, int page, int pageSize)
	{
		ProductStats stats = new ProductStats();
		List<ProductStat> productStatList = new ArrayList<ProductStat>();
		stats.setProductStatList(productStatList);

		startDate = ClickStatisticsDAOUtil.resetStartTime(startDate);
		endDate = ClickStatisticsDAOUtil.resetEndTime(endDate);

		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = connection.prepareStatement(prepareProductStatsQuery(options));
			statement.setDate(1, new java.sql.Date(startDate));
			statement.setDate(2, new java.sql.Date(endDate));
			int startIndex = 0;
			if (page > 0)
			{
				startIndex = (page - 1) * pageSize;
			}
			statement.setInt(3, startIndex);
			statement.setInt(4, pageSize);
			resultSet = statement.executeQuery();
			ProductStat stat;
			while (resultSet.next())
			{
				stat = new ProductStat();
				stat.setUrl(resultSet.getString("FLD_URL"));
				stat.setName(resultSet.getString("FLD_NAME"));
				stat.setListingClicks(resultSet.getInt("LISTING_HITS"));
				stat.setListingViews(resultSet.getInt("LISTING_VIEWS"));
				stat.setSponsorClicks(resultSet.getInt("SPONSOR_HITS"));
				stat.setSponsorViews(resultSet.getInt("SPONSOR_VIEWS"));
				stat.setTotalViews(stat.getListingViews() + stat.getSponsorViews());
				stat.setTotalClicks(stat.getListingClicks() + stat.getSponsorClicks());
				productStatList.add(stat);
			}
		}
		catch (SQLException e)
		{
			logger.error("Can not get product statistics for " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return stats;
	}

	private String prepareProductStatsQuery(ProductStatSortOptions options)
	{
		String suffix = "";
		if (options.isOrderByListingClicksAscending())
		{
			suffix = " ORDER BY LISTING_HITS ASC LIMIT ?, ?";
		}
		else if (options.isOrderByListingClicksDescending())
		{
			suffix = " ORDER BY LISTING_HITS DESC LIMIT ?, ?";
		}
		else if (options.isOrderByListingViewsAscending())
		{
			suffix = " ORDER BY LISTING_VIEWS ASC LIMIT ?, ?";
		}
		else if (options.isOrderByListingViewsDescending())
		{
			suffix = " ORDER BY LISTING_VIEWS DESC LIMIT ?, ?";
		}
		else if (options.isOrderBySponsorClicksAscending())
		{
			suffix = " ORDER BY SPONSOR_HITS ASC LIMIT ?, ?";
		}
		else if (options.isOrderBySponsorClicksDescending())
		{
			suffix = " ORDER BY SPONSOR_HITS DESC LIMIT ?, ?";
		}
		else if (options.isOrderBySponsorViewsAscending())
		{
			suffix = " ORDER BY SPONSOR_VIEWS ASC LIMIT ?, ?";
		}
		else if (options.isOrderBySponsorViewsDescending())
		{
			suffix = " ORDER BY SPONSOR_VIEWS DESC LIMIT ?, ?";
		}
		else
		{
			suffix = " LIMIT ?, ?";
		}

		return getProductStatsQuery + suffix;
	}
	
}