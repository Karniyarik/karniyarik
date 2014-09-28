package com.karniyarik.statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.Interval;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.statistics.vo.ProductClickLog;
import com.karniyarik.common.statistics.vo.ProductSummary;
import com.karniyarik.common.statistics.vo.SearchLog;
import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.common.util.ExecutorUtil;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.JerseyUtil;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.common.util.TableNameUtil;
import com.karniyarik.statistics.dao.APIStatisticsDAO;
import com.karniyarik.statistics.dao.ClickStatisticsDAOUtil;
import com.karniyarik.statistics.dao.ProductStatisticsDAO;
import com.karniyarik.statistics.dao.SearchLogDAO;
import com.karniyarik.statistics.dao.SiteClickStatDAO;
import com.karniyarik.statistics.fraud.FraudDictionary;
import com.karniyarik.statistics.vo.DailyProductStatistics;
import com.karniyarik.statistics.vo.DailySiteClickStatistics;

public class StatisticsCollector
{

	private static StatisticsCollector	INSTANCE	= null;
	private static ExecutorService		searchLogExecutor;
	private static ExecutorService		clickLogExecutor;
	private final ReadWriteLock			lock		= new ReentrantReadWriteLock();
	private FileWriter					searchLogWriter;
	private FileWriter					clickLogWriter;
	private final File					currentSearchLog;
	private final File					currentClickLog;
	private final FraudDictionary		fraudDictionary;

	private StatisticsCollector()
	{

		try
		{
			searchLogExecutor = Executors.newSingleThreadExecutor();
			clickLogExecutor = Executors.newSingleThreadExecutor();
			fraudDictionary = new FraudDictionary();
			currentSearchLog = getLogFile("search.log");
			currentClickLog = getLogFile("click.log");
			searchLogWriter = new FileWriter(currentSearchLog, Boolean.TRUE);
			clickLogWriter = new FileWriter(currentClickLog, Boolean.TRUE);
		}
		catch (Throwable e)
		{
			getLogger().error("Can not create file writer for statitics log!", e);
			throw new RuntimeException(e);
		}
	}

	private File getLogFile(String logFileName) throws Throwable
	{
		String path = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getStatiticsConfig().getStatisticsDirectory();
		File statisticsDir = new File(path);

		if (!statisticsDir.exists())
		{
			FileUtils.forceMkdir(statisticsDir);
		}

		File statisticsFile = new File(statisticsDir, logFileName);
		if (!statisticsFile.exists())
		{
			statisticsFile.createNewFile();
		}

		return statisticsFile;
	}

	public static StatisticsCollector getInstance()
	{
		if (INSTANCE == null)
		{
			synchronized (StatisticsCollector.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = new StatisticsCollector();
				}
			}
		}
		return INSTANCE;
	}

	public void collectSearchLog(SearchLog searchLog)
	{
		try
		{
			lock.readLock().lock();
			SearchLogCollectorTask task = new SearchLogCollectorTask(searchLog, searchLogWriter);
			searchLogExecutor.execute(task);
		}
		catch (Throwable t)
		{
			getLogger().error("Could not create search log task", t);
		}
		finally
		{
			lock.readLock().unlock();
		}
	}

	public void collectProductClick(ProductClickLog clickLog)
	{
		// At the very beginning we were calling fraud check
		// only for sponsor products and only when the clicks
		// are being collected. Not clicks are being saved to 
		// database.
		//		collectProductClick(clickLog, clickLog.isSponsor());
		
		// Now we're calling fraud check for all clicks
		// But still when the clicks are being collected
		// Not when they're being saved to database.
		collectProductClick(clickLog, true);
	}

	private void collectProductClick(ProductClickLog clickLog, boolean performFraudCheck)
	{
		try
		{
			lock.readLock().lock();
			ClickLogCollectorTask task = new ClickLogCollectorTask(fraudDictionary, performFraudCheck, clickLog, clickLogWriter);
			clickLogExecutor.execute(task);
		}
		catch (Throwable t)
		{
			getLogger().error("Could not create click log task", t);
		}
		finally
		{
			lock.readLock().unlock();
		}
	}

	public void saveCurrentStatistics()
	{

		long startTime = System.currentTimeMillis();

		ExecutorService tmpSearchLogExecutor = searchLogExecutor;
		ExecutorService tmpClickLogExecutor = clickLogExecutor;
		File archivedSearchLogFile = null;
		String archivedSearchLogPath = "search_" + new SimpleDateFormat("dd-MM-yy_HH-mm-ss").format(new Date()) + ".log";
		File archivedClickLogFile = null;
		String archivedClickLogPath = "click_" + new SimpleDateFormat("dd-MM-yy_HH-mm-ss").format(new Date()) + ".log";
		try
		{
			lock.writeLock().lock();

			searchLogExecutor = Executors.newSingleThreadExecutor();
			ExecutorUtil.shutDown(tmpSearchLogExecutor, "search log executor");

			clickLogExecutor = Executors.newSingleThreadExecutor();
			ExecutorUtil.shutDown(tmpClickLogExecutor, "click log executor");

			searchLogWriter.flush();
			searchLogWriter.close();
			
			archivedSearchLogFile = new File(currentSearchLog.getParent(), archivedSearchLogPath);
			
			archiveOldLog(currentSearchLog, archivedSearchLogFile);
			
			searchLogWriter = new FileWriter(currentSearchLog);

			clickLogWriter.flush();
			clickLogWriter.close();
			archivedClickLogFile = new File(currentClickLog.getParent(), archivedClickLogPath);
			
			archiveOldLog(currentClickLog, archivedClickLogFile);

			clickLogWriter = new FileWriter(currentClickLog);
		}
		catch (Throwable t)
		{
			getLogger().error("Could not swicth current log files with new ones", t);
		}
		finally
		{
			lock.writeLock().unlock();
		}

		if (archivedSearchLogFile != null && archivedClickLogFile != null)
		{
			List<ProductClickLog> clickLogList = JSONUtil.parseFile(archivedClickLogFile, ProductClickLog.class);
			List<SearchLog> searchLogList = JSONUtil.parseFile(archivedSearchLogFile, SearchLog.class);
			saveDailyLogs(searchLogList, clickLogList);
		}

		long endTime = System.currentTimeMillis();

		Interval interval = new Interval(startTime, endTime);

		ExceptionNotifier.sendException("statistic-collection-ended", "Statistics Collection Ended", "Collection ended in " + interval.toPeriod().toStandardMinutes().getMinutes() + " minutes");
	}

	private void archiveOldLog(File currentLog, File archivedLogFile)
	{
		try
		{
			if(archivedLogFile.exists()){
				archivedLogFile.delete();
			}

			FileUtils.moveFile(currentLog, archivedLogFile);			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				currentLog.createNewFile();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void importLogs()
	{
		File rootDir = new File(currentSearchLog.getParent());
		String[] list = rootDir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.startsWith("click_");
			}
		});

		for (String fileName : list)
		{
			String clickLogFileName = fileName;
			String searchLogFileName = clickLogFileName.replace("click_", "search_");

			File clickLogFile = new File(rootDir, clickLogFileName);
			File searchLogFile = new File(rootDir, searchLogFileName);

			if (searchLogFile != null && clickLogFile != null)
			{
				List<ProductClickLog> clickLogList = JSONUtil.parseFile(clickLogFile, ProductClickLog.class);
				List<SearchLog> searchLogList = JSONUtil.parseFile(searchLogFile, SearchLog.class);
				saveDailyLogs(searchLogList, clickLogList);
			}
		}
	}

	private void saveDailyLogs(List<SearchLog> searchLogList, List<ProductClickLog> productClickLogList)
	{
		Date today = new Date();
		Date yesterDay = DateUtils.addDays(today, -1);
		Date logDate = null;

		Map<String, Map<String, DailyProductStatistics>> siteMap = new HashMap<String, Map<String, DailyProductStatistics>>();
		Map<String, Map<String, DailyProductStatistics>> apiMap = new HashMap<String, Map<String, DailyProductStatistics>>();

		for (SearchLog searchLog : searchLogList)
		{
			logDate = new Date(searchLog.getDate());

			if (DateUtils.isSameDay(today, logDate))
			{
				// this log will be processed tomorrow
				// save it again to current log file
				try
				{
					collectSearchLog(searchLog);
				}
				catch (Throwable e)
				{
				}
			}
			else if (DateUtils.isSameDay(yesterDay, logDate))
			{
				for (ProductSummary productSummary : searchLog.getProducts())
				{
					applyProductSummary(searchLog, siteMap, productSummary);

					if (searchLog.apiQuery())
					{
						applyProductSummary(searchLog, apiMap, productSummary);
					}
				}
			}
		}

		for (ProductClickLog productClickLog : productClickLogList)
		{
			logDate = new Date(productClickLog.getDate());
			if (DateUtils.isSameDay(today, logDate))
			{
				// this log will be processed tomorrow
				// save it again to current log file
				// skip fraud check since this click
				// has already been checked for
				// fraud clicks
				try
				{
					collectProductClick(productClickLog, false);
				}
				catch (Throwable e)
				{
				}
			}
			else if (DateUtils.isSameDay(yesterDay, logDate))
			{
				if (!productClickLog.isFraud())
				{
					applyProductClickLog(productClickLog, siteMap);

					if (productClickLog.apiClick())
					{
						applyProductClickLog(productClickLog, apiMap);
					}
				}
			}
		}

		Map<String, DailyProductStatistics> urlMap = null;
		ProductStatisticsDAO dao = null;
		for (String siteName : siteMap.keySet())
		{
			urlMap = siteMap.get(siteName);
			dao = new ProductStatisticsDAO(siteName, TableNameUtil.createProductStatTableName(siteName));
			dao.saveDailyProductStats(urlMap.values());
		}

		APIStatisticsDAO apiDao = null;
		for (String apiKey : apiMap.keySet())
		{
			urlMap = apiMap.get(apiKey);
			apiDao = new APIStatisticsDAO(apiKey, TableNameUtil.createApiStatTableName(apiKey));
			apiDao.saveDailyProductStats(urlMap.values());
		}
		
		SiteClickStatDAO siteClickDao = new SiteClickStatDAO();
		DailySiteClickStatistics siteClickStat;
		for (String siteName : siteMap.keySet())
		{
			siteClickStat = new DailySiteClickStatistics(siteName, ClickStatisticsDAOUtil.resetStartTime(yesterDay.getTime()));
			for (DailyProductStatistics dailyProductStatistics : siteMap.get(siteName).values())
			{
				siteClickStat.addDailyProductStatistics(dailyProductStatistics);
			}
			siteClickDao.saveDailySiteClickStat(siteClickStat);
		}
		
		for (String apiKey : apiMap.keySet())
		{
			siteClickStat = new DailySiteClickStatistics(apiKey, ClickStatisticsDAOUtil.resetStartTime(yesterDay.getTime()));
			for (DailyProductStatistics dailyProductStatistics : apiMap.get(apiKey).values())
			{
				siteClickStat.addDailyProductStatistics(dailyProductStatistics);
			}
			siteClickDao.saveDailySiteClickStat(siteClickStat);
		}
		
		SearchLogDAO.saveSearchLogs(searchLogList);

		sendTopSearches();
	}

	private void sendTopSearches()
	{
		// update web server with new top searches
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();

		String masterEndPoint = deploymentConfig.getMasterWebUrl() + "/rest/topSearches/update";
		// String slaveEndPoint = deploymentConfig.getSlaveWebUrl() +
		// "/rest/topSearches/update";

		TopSearches topSearches = SearchLogDAO.getDailyTopSearches(CategorizerConfig.PRODUCT_TYPE, StatisticsWebServiceUtil.MAX_TOP_SEARCHES);
		if (topSearches.getTopSearchList().size() > 0)
		{
			JerseyUtil.sendJSONPut(masterEndPoint, topSearches);
			// JerseyUtil.sendJSONPut(slaveEndPoint, topSearches);
		}

		topSearches = SearchLogDAO.getDailyTopSearches(CategorizerConfig.CAR_TYPE, StatisticsWebServiceUtil.MAX_TOP_SEARCHES);
		if (topSearches.getTopSearchList().size() > 0)
		{
			JerseyUtil.sendJSONPut(masterEndPoint, topSearches);
			// JerseyUtil.sendJSONPut(slaveEndPoint, topSearches);
		}
		
		topSearches = SearchLogDAO.getDailyTopSearches(CategorizerConfig.ESTATE_TYPE, StatisticsWebServiceUtil.MAX_TOP_SEARCHES);
		if (topSearches.getTopSearchList().size() > 0)
		{
			JerseyUtil.sendJSONPut(masterEndPoint, topSearches);
			// JerseyUtil.sendJSONPut(slaveEndPoint, topSearches);
		}

	}

	private void applyProductClickLog(ProductClickLog productClickLog, Map<String, Map<String, DailyProductStatistics>> map)
	{
		Map<String, DailyProductStatistics> urlMap;
		DailyProductStatistics stat;
		String urlMapKey;
		if (productClickLog.apiClick())
		{
			urlMapKey = productClickLog.getApiKey();
		}
		else
		{
			urlMapKey = productClickLog.getSiteName();
		}

		if (!map.containsKey(urlMapKey))
		{
			urlMap = new HashMap<String, DailyProductStatistics>();
			map.put(urlMapKey, urlMap);
		}
		else
		{
			urlMap = map.get(urlMapKey);
		}

		if (!urlMap.containsKey(productClickLog.getUrl()))
		{
			stat = new DailyProductStatistics(productClickLog.getUrl(), productClickLog.getName(), productClickLog.getSiteName(), productClickLog.getDate());
			stat.setProductCategory(productClickLog.getProductCategory());
			urlMap.put(productClickLog.getUrl(), stat);
		}
		else
		{
			stat = urlMap.get(productClickLog.getUrl());
		}

		if (productClickLog.isSponsor())
		{
			stat.setSponsorClicks(stat.getSponsorClicks() + 1);
			stat.getQueryStatisticLog(productClickLog.getQuery()).incrementSponsorClickCount();
		}
		else
		{
			stat.setListingClicks(stat.getListingClicks() + 1);
			stat.getQueryStatisticLog(productClickLog.getQuery()).incrementListingClickCount();
		}

	}

	private void applyProductSummary(SearchLog searchLog, Map<String, Map<String, DailyProductStatistics>> map, ProductSummary productSummary)
	{
		Map<String, DailyProductStatistics> urlMap;
		DailyProductStatistics stat;
		
		String urlMapKey;
		if (searchLog.apiQuery())
		{
			urlMapKey = searchLog.getApiKey();
		}
		else
		{
			urlMapKey = productSummary.getSiteName();
		}

		if (!map.containsKey(urlMapKey))
		{
			urlMap = new HashMap<String, DailyProductStatistics>();
			map.put(urlMapKey, urlMap);
		}
		else
		{
			urlMap = map.get(urlMapKey);
		}
		
		if (!urlMap.containsKey(productSummary.getUrl()))
		{
			stat = new DailyProductStatistics(productSummary.getUrl(), productSummary.getName(), productSummary.getSiteName(), searchLog.getDate());
			stat.setProductCategory(searchLog.getCategory());
			urlMap.put(productSummary.getUrl(), stat);
		}
		else
		{
			stat = urlMap.get(productSummary.getUrl());
		}
		
		if (productSummary.isSponsor())
		{
			stat.setSponsorViews(stat.getSponsorViews() + 1);
			stat.getQueryStatisticLog(searchLog.getQuery()).incrementSponsorViewCount();
		}
		else
		{
			stat.setListingViews(stat.getListingViews() + 1);
			stat.getQueryStatisticLog(searchLog.getQuery()).incrementListingViewCount();
		}
	}

	public void close()
	{
		try
		{
			lock.writeLock().lock();
			ExecutorUtil.shutDown(searchLogExecutor, "search log executor");
			ExecutorUtil.shutDown(clickLogExecutor, "click log executor");

			searchLogWriter.flush();
			searchLogWriter.close();
			clickLogWriter.flush();
			clickLogWriter.close();
		}
		catch (Throwable t)
		{
			getLogger().error("Could not close statistics collector", t);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

	public static void main(String[] args)
	{
		StatisticsCollector.getInstance().saveCurrentStatistics();
		StatisticsCollector.getInstance().close();
	}
}
