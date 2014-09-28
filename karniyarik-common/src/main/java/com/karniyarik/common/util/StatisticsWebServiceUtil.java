package com.karniyarik.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.statistics.vo.LastSearches;
import com.karniyarik.common.statistics.vo.ProductClickLog;
import com.karniyarik.common.statistics.vo.SearchLog;
import com.karniyarik.common.statistics.vo.SponsorStats;
import com.karniyarik.common.statistics.vo.TopSearches;

public class StatisticsWebServiceUtil extends JerseyUtil
{

	public static final int	MAX_LAST_SEARCHES	= 20;
	public static final int	MAX_TOP_SEARCHES	= 20;

	public static void sendSiteStat(Object obj)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String endPoint = deploymentConfig.getStatisticsDeploymentURL();
		sendJSONPut(endPoint + "/rest/api/sitestat", obj);
	}

	public static void sendProductHit(ProductClickLog log)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String endPoint = deploymentConfig.getStatisticsDeploymentURL();
		sendJSONPut(endPoint + "/rest/api/hit", log);
	}

	public static void sendSearchLog(SearchLog searchLog)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String endPoint = deploymentConfig.getStatisticsDeploymentURL();
		sendJSONPut(endPoint + "/rest/api/searchlog", searchLog);
	}

	public static LastSearches getLastSearches(int category, int maxLastSearches)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String endPoint = deploymentConfig.getStatisticsDeploymentURL() + "/rest/report/lastSearches";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("c", "" + category);
		parameters.put("l", "" + maxLastSearches);
		return getObject(endPoint, parameters, LastSearches.class, 30000);
	}

	public static TopSearches getTopSearches(int category, int maxTopSearches)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String endPoint = deploymentConfig.getStatisticsDeploymentURL() + "/rest/report/topSearches";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("c", "" + category);
		parameters.put("l", "" + maxTopSearches);
		return getObject(endPoint, parameters, TopSearches.class, 30000);
	}

	public static TopSearches getWeeklySearches(int category, int maxTopSearches, long date)
	{
		return getWeeklySearches(category, maxTopSearches, date, 30000);
	}
	
	public static TopSearches getWeeklySearches(int category, int maxTopSearches, long date, int timeout)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		//String endPoint = deploymentConfig.getStatisticsDeploymentURL() + "/rest/report/weeklyTopSearches";
		String endPoint = "http://77.92.136.8:8080/statistics-server/rest/report/weeklyTopSearches";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("c", "" + category);
		parameters.put("l", "" + maxTopSearches);
		parameters.put("d", "" + date);
		return getObject(endPoint, parameters, TopSearches.class, timeout);
	}

	public static TopSearches getMonthlySearches(int category, int maxTopSearches, long date)
	{
		return getMonthlySearches(category, maxTopSearches, date, 60000);
	}
	
	public static TopSearches getMonthlySearches(int category, int maxTopSearches, long date, int timeout)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String endPoint = deploymentConfig.getStatisticsDeploymentURL() + "/rest/report/monthlyTopSearches";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("c", "" + category);
		parameters.put("l", "" + maxTopSearches);
		parameters.put("d", "" + date);
		return getObject(endPoint, parameters, TopSearches.class, timeout);
	}

	public static SponsorStats getSponsorStats(List<String> sponsoredMerchants, long date)
	{
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		String endPoint = deploymentConfig.getStatisticsDeploymentURL() + "/rest/report/sponsorStat";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("s", StringUtils.join(sponsoredMerchants, ","));
		parameters.put("d", "" + date);
		return getObject(endPoint, parameters, SponsorStats.class, 20000);
	}
}
