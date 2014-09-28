package com.karniyarik.crawler.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.crawler.util.URLManager;

public class LinksToVisitQueue
{
	// FLD_URLDS is MEDIUMTEXT which has size 16,777,215
	// dividing 16,777,215 to 512 gives a number a little bigger than
	// 32750
	private final int					blockSize;

	private final LinkedList<String>	queue;
	private final long					maxQueueSize;
	private final LinksToVisitFileDAO	dao;
	private final LinkedList<String>	erroneousLinks;
	private final JobExecutionStat		crawlerStatistics;
	private final URLManager			urlManager;
	private final Logger				siteLogger;

	public LinksToVisitQueue(long maxQueueSize, int blockSize, LinksToVisitFileDAO linksToVisitDao, JobExecutionStat crawlerStatistics, URLManager urlManager, Logger siteLogger)
	{
		this.blockSize = blockSize;
		this.maxQueueSize = maxQueueSize;
		this.dao = linksToVisitDao;
		this.crawlerStatistics = crawlerStatistics;
		this.urlManager = urlManager;
		this.siteLogger = siteLogger;

		this.queue = new LinkedList<String>();
		this.erroneousLinks = new LinkedList<String>();
	}

	public String pop()
	{
		// if queue is empty fill it with ones in db.
		if (queue.size() < 1)
		{
			Collection<String> aURLList = dao.load();

			queue.addAll(aURLList);

			crawlerStatistics.linksToVisitRead();
		}

		// if there is some data in the queue pop it
		if (queue.size() > 0)
		{
			String strToPop = urlManager.constructURL(queue.pop());
			crawlerStatistics.linksToVisitPopped(strToPop);
			return strToPop;
		}
		else
		{
			return null;
		}
	}

	public void push(String aUrl)
	{

		aUrl = urlManager.breakURL(aUrl);

		if (!erroneousLinks.contains(aUrl))
		{
			if (!queue.contains(aUrl))
			{
				// System.out.println(aUrl);

				queue.push(aUrl);
				// added by km
				crawlerStatistics.linksFound(1);

				crawlerStatistics.linksToVisitAdded(aUrl);
			}

			if (queue.size() > maxQueueSize)
			{
				List<String> aURLListToBeStored = new ArrayList<String>(blockSize);

				for (int anIndex = 0; anIndex < blockSize; anIndex++)
				{
					aURLListToBeStored.add(queue.removeLast());
				}

				dao.store(aURLListToBeStored);

				crawlerStatistics.linksToVisitFlush();
			}
		}
	}

	// this method called with broken urls so don't need to break t again.
	public void pushErrorneous(String aURL)
	{

		if (!erroneousLinks.contains(aURL))
		{
			erroneousLinks.add(aURL);
		}
	}

	public String popErrorneous()
	{
		if (erroneousLinks.size() > 0)
		{
			return urlManager.constructURL(erroneousLinks.pop());
		}
		else
		{
			return null;
		}
	}

	public void flush()
	{
		dao.store(queue);

		crawlerStatistics.linksToVisitFlush();

		queue.clear();
	}

	public void saveState()
	{
		dao.saveState(queue);
	}

	public void restoreLastState()
	{

		queue.clear();

		int queueLoadSize = (int) (maxQueueSize * 3 / 4);

		queue.addAll(dao.restoreState(queueLoadSize, blockSize));

	}

	public int size()
	{
		return queue.size();
	}

	public void clearAll()
	{
		siteLogger.info("Cleanining links to visit list.");

		queue.clear();
		dao.clear();
	}

	public void freeResources()
	{
		queue.clear();
	}
}