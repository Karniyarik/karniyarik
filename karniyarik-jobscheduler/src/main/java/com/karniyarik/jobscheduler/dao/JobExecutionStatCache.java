package com.karniyarik.jobscheduler.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;

public class JobExecutionStatCache {
	
	public static final int MAX_HISTORY_COUNT = 10;

	private static JobExecutionStatCache instance = null;
	
	private Map<String, LinkedList<JobExecutionStat>> cache = new HashMap<String, LinkedList<JobExecutionStat>>();
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private JobExecutionStatDaoFactory daoFactory = new JobExecutionStatDaoFactory();

	
	public JobExecutionStatCache() {
		loadStats();
	}
	
	public static JobExecutionStatCache getInstance() {
		if(instance == null)
		{
			instance = new JobExecutionStatCache();
		}
		return instance;
	}
	
	public void loadStats()
	{
		lock.writeLock().lock();
		
		try {
			SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
			for(SiteConfig config: sitesConfig.getSiteConfigList())
			{
				String sitename = config.getSiteName();
				LinkedList<JobExecutionStat> history = new LinkedList<JobExecutionStat>();
				JobExecutionStatDao dao = daoFactory.create(sitename);
				List<JobExecutionStat> stats = dao.getStats(MAX_HISTORY_COUNT);
				history.addAll(stats);
				cache.put(sitename, history);
			}
		} catch (Throwable e) {
			throw new RuntimeException("Cannot load stat cache", e);
		}
		finally
		{
			lock.writeLock().unlock();	
		}		
	}
	
	public void updateStat(JobExecutionStat stat)
	{
		
		String sitename = stat.getSiteName();
		if(StringUtils.isBlank(sitename))
		{
			return;
		}

		LinkedList<JobExecutionStat> history = cache.get(sitename);
		if(history == null)
		{
			insertStat(stat);
		}
		if(history.size() > 0)
		{
			lock.writeLock().lock();
			history.set(0, stat);			
			lock.writeLock().unlock();
		}
	}
	
	public void insertStat(JobExecutionStat stat)
	{
		lock.writeLock().lock();
		String sitename = stat.getSiteName();
		if(StringUtils.isBlank(sitename))
		{
			return;
		}
		
		LinkedList<JobExecutionStat> history = cache.get(sitename);
		if(history == null)
		{
			history = new LinkedList<JobExecutionStat>();			
			cache.put(stat.getSiteName(), history);
		}
		if(history.size() > (MAX_HISTORY_COUNT-1))
		{
			history.removeLast();
		}
		history.addFirst(stat);
		lock.writeLock().unlock();
	}
	
	public List<JobExecutionStat> getStatHistory(String sitename, int count) {
		lock.writeLock().lock();
		List<JobExecutionStat> result = cache.get(sitename);
		
		if(result == null){
			result = new LinkedList<JobExecutionStat>();
		}
		else
		{
			count = (count < 1) ? result.size() : count;
			int size = count > result.size() ? result.size() : count;
			result = result.subList(0, size);
		}
		
		lock.writeLock().unlock();
		return result;
	}
	
	public JobExecutionStat getCurrentStat(String sitename)
	{
		JobExecutionStat stat = null;
		
		LinkedList<JobExecutionStat> history = cache.get(sitename);
		if(history == null || history.size() < 1)
		{
			JobExecutionStatDao dao = daoFactory.create(sitename);
			stat = dao.getCurrentStat();
		} else {
			stat = history.peek();
		}
		
		return stat;
	}
}
