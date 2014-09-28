package com.karniyarik.controller.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.util.DateUtil;
import com.karniyarik.controller.SiteControllerState;

public class SchedulerDAO
{
	private final String	GET_SCHEDULE_INFO_QUERY	= "SELECT FLD_SITE_NAME, FLD_NEXT_EXECUTION_DATE, FLD_STATE, FLD_PERIOD FROM TBL_SCHEDULE_INFO";
	private final String	DELETE_SCHEDULE_INFO_QUERY	= "DELETE FROM TBL_SCHEDULE_INFO";
	private final String	INSERT_SCHEDULE_INFO_QUERY	= "INSERT INTO TBL_SCHEDULE_INFO(FLD_SITE_NAME, FLD_NEXT_EXECUTION_DATE, FLD_STATE, FLD_PERIOD) VALUES (?,?,?,?)";

	public void storeSchedulerState(List<SiteScheduleInfo> anInfoList)
	{
		try
		{
			deleteSchedulerInfo();
			insertSchedulerInfo(anInfoList);
		}
		catch (Throwable e)
		{
			getLogger().error("Could not store site schdule informations", e);
		}
		
	}
	
	private void insertSchedulerInfo(List<SiteScheduleInfo> anInfoList) throws Throwable {
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		
		try
		{
			statement = connection.prepareStatement(INSERT_SCHEDULE_INFO_QUERY);
			for(SiteScheduleInfo anInfo: anInfoList)
			{
				statement.setString(1, anInfo.getSiteConfig().getSiteName());
				statement.setTimestamp(2, DateUtil.getTimestamp(anInfo.getNextExecutionDate()));
				statement.setInt(3, anInfo.getState().ordinal());
				statement.setInt(4, anInfo.getCrawlingPeriod());
				
				statement.executeUpdate();
			}
			
			connection.commit();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not save scheduler information", e);
			throw e;
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}
	
	private void deleteSchedulerInfo() throws Throwable {
		Connection connection = DBConnectionProvider.getConnection(Boolean.FALSE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		
		try
		{
			statement = connection.prepareStatement(DELETE_SCHEDULE_INFO_QUERY);
			statement.executeUpdate();
			connection.commit();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not delete scheduler info", e);
			throw e;
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, null);
		}
	}

	public List<SiteScheduleInfo> getSchedulerStates()
	{
		List<SiteScheduleInfo> scheduleInfoList = new ArrayList<SiteScheduleInfo>();
		
		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(GET_SCHEDULE_INFO_QUERY);
			resultSet = statement.executeQuery();
			
			SiteScheduleInfo anInfo = null;
			SitesConfig aConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
			while(resultSet.next())
			{
				anInfo = new SiteScheduleInfo();
				anInfo.setSiteConfig(aConfig.getSiteConfig(resultSet.getString("FLD_SITE_NAME")));
				
				anInfo.setNextExecutionDate(DateUtil.getDate(resultSet.getTimestamp("FLD_NEXT_EXECUTION_DATE")));
				anInfo.setState(SiteControllerState.values()[resultSet.getInt("FLD_STATE")]);
				anInfo.setCrawlingPeriod(resultSet.getInt("FLD_PERIOD"));
				
				scheduleInfoList.add(anInfo);
			}
			
			connection.commit();
		}
		catch (Throwable e)
		{
			getLogger().error("Could not get schduler states", e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}
		
		return scheduleInfoList;
	}
	
	private Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}
}
