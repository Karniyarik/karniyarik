package com.karniyarik.crawler.linkgraph;

import org.apache.log4j.Logger;

import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.crawler.util.URLManager;

public class VisitedLinksList
{
	private final VisitedLinkCache	visitedLinkCache;
	private final URLManager		urlManager;
	private final JobExecutionStat	crawlerStatistics;
	private final Logger			siteLogger;

	public VisitedLinksList(VisitedLinkCache visitedLinkCache, URLManager urlManager, JobExecutionStat crawlerStatistics, Logger siteLogger)
	{
		this.visitedLinkCache = visitedLinkCache;
		this.urlManager = urlManager;
		this.crawlerStatistics = crawlerStatistics;
		this.siteLogger = siteLogger;
	}

	// this method should be called with a long (complete) url string such as:
	// http://www.hepsiburada.com/products.asp?ProductID=3242
	public Link isVisited(String aURL)
	{
		// boolean isVisited = false;

		aURL = urlManager.breakURL(aURL);

		// try to get the link from memory cache
		Link aLink = visitedLinkCache.get(aURL);

		// if the link is in the memory
		if (aLink != null)
		{
			crawlerStatistics.memoryCacheHit(aURL);
		}
		else
		{
			crawlerStatistics.memoryCacheMiss(aURL);

			if (!visitedLinkCache.isDBEmpty())
			{
				aLink = visitedLinkCache.load(aURL);

				crawlerStatistics.visitedLinkRead();

				if (aLink != null)
				{
					// isVisited = aLink.isVisited();

					crawlerStatistics.dbCacheHit(aURL);
				}
				else
				{
					crawlerStatistics.dbCacheMiss(aURL);
				}
			}
		}

		if (aLink == null)
		{
			aLink = new Link(aURL);
			visitedLinkCache.add(aLink);
		}

		hit(aLink);

		return aLink;
	}

	public Link visit(Link aLink)
	{
		// visit is called just after isVisited call
		// after isVisited in any case the link is in memory cache

		// just for exceptional cases
		String url = aLink.getURL();
		int id = aLink.getID();

		// check if it really exists
		aLink = visitedLinkCache.get(aLink.getURL());

		if (aLink == null)
		{
			if (id != 0)
			{

				aLink = visitedLinkCache.load(url);
				Logger.getLogger(this.getClass()).info("The link [id =" + id + "]" + url + " is expected to be in memory, but i cant find it!");
			}
			else
			{
				Logger.getLogger(this.getClass()).info("The link (without an id) " + url + " is expected to be in memory, but i cant find it!");
			}
		}

		if (aLink != null)
		{
			aLink.setIsVisited(true);

			hit(aLink);
			
			crawlerStatistics.linkVisited(aLink.getURL());
		} else {
			System.out.println("Error: " + url);
		}

		return aLink;
	}

	private void hit(Link aLink)
	{
		long aTime = System.currentTimeMillis();

		aLink.setLastHitTime(aTime);

		aLink.increaseHitCount();

		aLink.setUpdated(true);
	}

	public Link productFound(String url)
	{
		// check if it really exists
		Link aLink = visitedLinkCache.get(url);

		if (aLink == null)
		{
			aLink = visitedLinkCache.load(url);
		}

		aLink.setHasProduct(true);
		aLink.setUpdated(true);

		return aLink;
	}

	public void clearAll()
	{
		siteLogger.info("Cleaning links visited list");
		visitedLinkCache.clear();
	}

	public void loadCache()
	{
		visitedLinkCache.loadCache();
	}

	public void freeResources()
	{
		visitedLinkCache.freeResources();
	}

	public VisitedLinkCache getVisitedLinkCache()
	{
		return visitedLinkCache;
	}

	public void saveState()
	{

		visitedLinkCache.saveState();
	}

	public void restoreLastState()
	{

		visitedLinkCache.restoreLastState();
	}
}