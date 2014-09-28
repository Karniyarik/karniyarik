package com.karniyarik.statistics.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.statistics.vo.DailyStat;
import com.karniyarik.common.statistics.vo.DailyStats;
import com.karniyarik.statistics.cache.DailyCache;
import com.karniyarik.statistics.vo.DailySiteClickStatistics;

public class SiteClickStatDAO
{

	private final static String	saveDailySiteClickStatQuery = "INSERT INTO TBL_SITE_CLICK_STAT (FLD_SITE_NAME, FLD_LISTING_VIEW, FLD_LISTING_HIT, FLD_SPONSOR_VIEW, FLD_SPONSOR_HIT, FLD_DATE) VALUES (?, ?, ?, ?, ?, ?)";
	//SELECT DISTINCT ??
	private final static String	getDailyStatsQuery = "SELECT FLD_DATE, SUM(FLD_LISTING_VIEW) AS LVIEWS, SUM(FLD_SPONSOR_VIEW) AS SVIEWS, SUM(FLD_LISTING_HIT) AS LHITS, SUM(FLD_SPONSOR_HIT) AS SHITS FROM TBL_SITE_CLICK_STAT WHERE TO_DAYS(FLD_DATE) >= TO_DAYS(?) AND TO_DAYS(FLD_DATE) <= TO_DAYS(?) AND FLD_SITE_NAME=? GROUP BY FLD_DATE";
	
	public void saveDailySiteClickStat(DailySiteClickStatistics siteClickStat)
	{
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;

		try
		{
			statement = connection.prepareStatement(saveDailySiteClickStatQuery);
			statement.setString(1, siteClickStat.getSiteName());
			statement.setInt(2, siteClickStat.getListingViews());
			statement.setInt(3, siteClickStat.getListingClicks());
			statement.setInt(4, siteClickStat.getSponsorViews());
			statement.setInt(5, siteClickStat.getSponsorClicks());
			statement.setDate(6, new java.sql.Date(siteClickStat.getDate()));
			statement.executeUpdate();
			connection.commit();
		}
		catch (Throwable e)
		{
			Logger.getLogger(this.getClass().getName()).error(siteClickStat.getSiteName() + " can not insert daily site click statistics", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}
	
	public DailyStats getDailyStats(String siteName, long startDate, long endDate)
	{
		Map<Integer, DailyStat> statMap = new HashMap<Integer, DailyStat>(); 
		
		DailyStats dailyStats = new DailyStats();
		List<DailyStat> dailyStatList = new ArrayList<DailyStat>();
		dailyStats.setDailyStatList(dailyStatList);
		
		startDate = ClickStatisticsDAOUtil.resetStartTime(startDate);
		endDate = ClickStatisticsDAOUtil.resetEndTime(endDate);
			
		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		Calendar statCal = Calendar.getInstance();
		try
		{
			statement = connection.prepareStatement(getDailyStatsQuery);
			statement.setDate(1, new java.sql.Date(startDate));
			statement.setDate(2, new java.sql.Date(endDate));
			statement.setString(3, siteName);
			resultSet = statement.executeQuery();
			DailyStat stat;
			
			while (resultSet.next())
			{
				Date statDate = new Date(resultSet.getDate("FLD_DATE").getTime());
				
				stat = new DailyStat();
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
			Logger.getLogger(this.getClass().getName()).error("Can not get daily statistics for " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}
		
		if(DateUtils.isSameDay(new Date(), new Date(endDate)))
		{
			DailyStat dailyStat = DailyCache.getInstance().getDailyStat(siteName);
			if(dailyStat != null)
			{
				statCal.setTime(new Date());
				int statDayOfYear = statCal.get(Calendar.DAY_OF_YEAR);
				statMap.put(statDayOfYear, dailyStat);
			}
		}
		
		ClickStatisticsDAOUtil.fillEmptyDates(startDate, endDate, statMap, dailyStatList);
		return dailyStats;
	}
	
}
