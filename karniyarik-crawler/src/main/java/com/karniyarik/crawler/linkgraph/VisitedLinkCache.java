package com.karniyarik.crawler.linkgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.StringUtil;

/**
 * This cache holds Link's in the memory. If needed the ones in the memory are
 * written to db. The Links in the memory can be result of an isVisisted query,
 * a database load or a new Link found.
 * 
 * @author siyamed
 * 
 */
public class VisitedLinkCache
{
	private final Map<String, Link>			cacheMap;
	private final List<Link>				cacheList;
	private final long						cacheSize;
	private final VisitedLinksDAO			dao;
	private final VisitedLinksComparator	comparator;
	private boolean							isDBEmpty	= true;
	private final Logger					siteLogger;
	private final JobExecutionStat			crawlerStatistics;
	private final ReadWriteLock				lock;

	public VisitedLinkCache(long visitedCacheSize, JobExecutionStat crawlerStatistics, Logger siteLogger, VisitedLinksDAO visitedLinksDao)
	{
		this.cacheSize = visitedCacheSize;
		this.cacheMap = new HashMap<String, Link>();
		this.cacheList = new LinkedList<Link>();
		this.dao = visitedLinksDao;
		this.comparator = new VisitedLinksComparator();
		this.isDBEmpty = dao.isDBEmpty();
		this.siteLogger = siteLogger;
		this.crawlerStatistics = crawlerStatistics;
		this.lock = new ReentrantReadWriteLock();
	}

	public void add(Link aLink)
	{

		// if it is not already in cache
		// this is a control for safety
		if (get(aLink.getURL()) == null)
		{

			try
			{
				lock.writeLock().lock();

				// add link to the map
				cacheMap.put(StringUtil.toLowerCase(aLink.getURL()), aLink);

				// also add it to the list
				cacheList.add(aLink);

				// flush is thread safe so call it outside this lock
				// if the size got bigger than threshold clean the caches
				if (cacheList.size() > cacheSize)
				{
					flush();
				}
			}
			catch (Throwable e)
			{
				siteLogger.error("Can not add link to links visited cache " + aLink.getURL(), e);
			}
			finally
			{
				lock.writeLock().unlock();
			}

		}
	}

	public Link get(String aURL)
	{
		Link link = null;
		aURL = StringUtil.toLowerCase(aURL);
		try
		{
			lock.readLock().lock();
			link = cacheMap.get(aURL);
		}
		catch (Throwable e)
		{
			siteLogger.error("Can not read url from links visited cache map " + aURL, e);
		}
		finally
		{
			lock.readLock().unlock();
		}

		return link;
	}

	/**
	 * add method is thread safe there is no need to lock load operation
	 * 
	 * @param aUrl
	 * @return
	 */
	public Link load(String aUrl)
	{
		Link aLink = dao.load(aUrl);

		if (aLink != null)
		{
			aLink.setUpdated(false);
			add(aLink);
		}

		return aLink;
	}

	private void flush()
	{

		siteLogger.info("*** FLUSHING ***" + '\n' + "mCacheSize = " + cacheSize + " --- mCacheListSize = " + cacheList.size() + "\n*** FLUSHING ***");
		long anElementCountToBeFlushed = cacheSize;

		// 1.calculate the size of the elements to be removed
		anElementCountToBeFlushed = cacheSize * 1 / 4;

		// 2.sorts links in ascending order so that low used ones will be
		// removed from cache
		// not the ones that are used frequently.
		comparator.setCurrentTime(System.currentTimeMillis());

		Collections.sort(cacheList, comparator);

		// 3. collect the links to be removed from cache
		// 3.a. collect only the links that are not a product
		// or the links that are product but is not updated
		List<Link> aListToBeRemoved = new ArrayList<Link>();

		List<Link> anUpdatedLinkList = new ArrayList<Link>();

		int aCollectedLinkCount = 0;

		Link aTmpLink = null;

		for (int anIndex = 0; (aCollectedLinkCount < anElementCountToBeFlushed) && (anIndex < cacheList.size()); anIndex++)
		{
			aTmpLink = cacheList.get(anIndex);

			aListToBeRemoved.add(aTmpLink);

			// if the link is a product that is updated and is stored
			// before, add to the updated product list.
			if (aTmpLink.isUpdated())
			{
				anUpdatedLinkList.add(aTmpLink);
			}

			aCollectedLinkCount++;
		}

		// 4. store the links and products to db
		dao.storeLinks(anUpdatedLinkList);

		// 5.remove the stored ones from cache
		// 5.a. remove from map
		for (Link aTmpLink1 : aListToBeRemoved)
		{
			cacheMap.remove(StringUtil.toLowerCase(aTmpLink1.getURL()));
		}

		// 5.b. remove from list
		cacheList.removeAll(aListToBeRemoved);

		isDBEmpty = false;

		crawlerStatistics.visitedLinkFlush();
	}

	public void saveState()
	{
		dao.storeLinks(cacheList);
		siteLogger.info("*** STATE SAVED ***" + '\n' + "mCacheSize = " + cacheSize + " --- mCacheListSize = " + cacheList.size() + "\n*** STATE SAVED ***");
	}

	public void restoreLastState()
	{
		loadCache();
		siteLogger.info("*** RESTORED ***" + '\n' + "mCacheSize = " + cacheSize + " --- mCacheListSize = " + cacheList.size() + "\n*** RESTORED ***");
	}

	public void loadCache()
	{

		try
		{
			lock.writeLock().lock();
			reset();

			long aSize = cacheSize * 9 / 10;

			List<Link> aList = dao.loadCachedData(aSize);

			for (Link aLink : aList)
			{
				add(aLink);
			}

			aList.clear();
		}
		catch (Throwable e)
		{
			siteLogger.error("Can not load visited links cache for " + crawlerStatistics.getSiteName(), e);
		}
		finally
		{
			lock.writeLock().unlock();
		}

	}

	public int size()
	{
		int size = -1;
		try
		{
			lock.readLock().lock();
			size = cacheList.size();
		}
		catch (Throwable e)
		{
			siteLogger.error("Can read visited links cache size for " + crawlerStatistics.getSiteName(), e);
		}
		finally
		{
			lock.readLock().unlock();
		}
		return size;
	}

	public boolean isDBEmpty()
	{
		boolean result = false;
		try
		{
			lock.readLock().lock();
			result = isDBEmpty;
		}
		catch (Throwable e)
		{
			siteLogger.error("Can not read isDBEmpty from links visited cache for " + crawlerStatistics.getSiteName(), e);
		}
		finally
		{
			lock.readLock().unlock();
		}

		return result;
	}

	private void reset()
	{
		cacheMap.clear();
		cacheList.clear();
		isDBEmpty = dao.isDBEmpty();
	}

	public void clear()
	{
		try
		{
			lock.writeLock().lock();
			reset();
			dao.clearAll();
		}
		catch (Throwable e)
		{
			siteLogger.error("Can not clear links visited cache map for " + crawlerStatistics.getSiteName(), e);
		}
		finally
		{
			lock.writeLock().unlock();
		}

	}

	public void freeResources()
	{
		try
		{
			lock.writeLock().lock();
			reset();
		}
		catch (Throwable e)
		{
			siteLogger.error("Can not free links visited cache resources for " + crawlerStatistics.getSiteName(), e);
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}
}
