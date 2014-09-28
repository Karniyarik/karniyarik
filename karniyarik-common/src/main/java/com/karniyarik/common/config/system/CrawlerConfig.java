package com.karniyarik.common.config.system;

import java.util.List;

import org.apache.commons.configuration.HierarchicalConfiguration;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

/**
 * Configuration object for the crawler.
 * 
 * @author siyamed
 * 
 */
@SuppressWarnings("serial")
public class CrawlerConfig extends ConfigurationBase
{
	private static final int	LINKS_TO_VISIT_BLOCK_SIZE	= 1000;
	private static final long	SINGLE_CRAWL_DELAY			= 500;
	private static final long	TOBE_VISITED_QUEUE_SIZE		= 20000;
	private static final long	VISITED_CACHE_SIZE			= 10000;
	private static final int	URL_FETCH_TIMEOUT			= 20000;
	private static final long	MAX_SUCC_EXC_COUNT			= 200;
	private static final long	SLEEP_TIME					= 5;			// minutes
	private static final long	FLUSH_PERIOD				= 60;			// minutes
	private static final String	BOT_NAME					= "Karniyarik";

	public CrawlerConfig() throws Exception
	{
		super(ConfigurationURLUtil.getCrawlerConfig());
	}

	/**
	 * For each downloaded (or visited) page, the system waits for some time.
	 * The length of this time is defined with this parameter.
	 * 
	 * @return
	 */
	public long getSingleCrawlDelay()
	{
		return getLong("crawler.singlecrawldelay", SINGLE_CRAWL_DELAY);
	}

	/**
	 * The system holds the links to visit information in a queue. This queue is
	 * an in memory queue where the information in it is written to database to
	 * keep the memory size at a pre-defined value. This parameter defines the
	 * memory size of the links to visit queue.
	 * 
	 * @return
	 */
	public long getQueueSize()
	{
		return getLong("crawler.linkstovisitsize", TOBE_VISITED_QUEUE_SIZE);
	}

	/**
	 * The visited pages are kept in the system in order not to visit them
	 * again. This information is kept in a list, and this list shall have a
	 * maximum value for memory efficiency. The system itself flushes
	 * information to the database when the maximum size is reached. This
	 * parameter defines the memory size of the visited links list.
	 * 
	 * @return
	 */
	public long getVisitedCacheSize()
	{
		return getLong("crawler.visitedlinksize", VISITED_CACHE_SIZE);
	}

	public int getURLFetchTimeout()
	{
		return getInt("crawler.fetchtimeout", URL_FETCH_TIMEOUT);
	}

	public long getMaxSuccessiveExceptionCount()
	{
		return getLong("crawler.successiveexceptioncount", MAX_SUCC_EXC_COUNT);
	}

	@SuppressWarnings("unchecked")
	public List<String> getCommonIgnoreRuleList()
	{
		return getList("crawler.ignorelist.ignore");
	}

	public long getSleepTime()
	{
		return getLong("crawler.sleeptime", SLEEP_TIME);
	}

	public long getFlushPeriod()
	{
		return getLong("crawler.flushperiod", FLUSH_PERIOD);
	}

	public String getLinksToVisitDirectory()
	{
		return getString("crawler.linkstovisitdir", "");
	}

	public void setLinksToVisitDirectory(String linksToVisitDirectory)
	{
		setProperty("crawler.linkstovisitdir", linksToVisitDirectory);
	}

	public String getProductsDirectory()
	{
		return getString("crawler.productsdir", "");
	}

	public void setProductsDirectory(String productsDirectory)
	{
		setProperty("crawler.productsdir", productsDirectory);
	}

	public int getLinksToVisitBlockSize()
	{
		return getInt("crawler.linkstovisitblocksize", LINKS_TO_VISIT_BLOCK_SIZE);
	}

	public void setLinksToVisitBlockSize(int blockSize)
	{
		setProperty("crawler.linkstovisitblocksize", blockSize);
	}

	public String getBotName()
	{

		return getString("crawler.botname", BOT_NAME);
	}

	public void setBotName(String botName)
	{

		setProperty("crawler.botname", botName);
	}

	public void setProductCacheSize(long value)
	{
		setProperty("crawler.productcachesize", value);
	}

	public void setSingleCrawlDelay(long value)
	{
		setProperty("crawler.singlecrawldelay", SINGLE_CRAWL_DELAY);
	}

	public void setQueueSize(long value)
	{
		setProperty("crawler.linkstovisitsize", value);
	}

	public void setVisitedCacheSize(long value)
	{
		setProperty("crawler.visitedlinksize", value);
	}

	public void setURLFetchTimeout(int value)
	{
		setProperty("crawler.fetchtimeout", value);
	}

	public void setMaxSuccessiveExceptionCount(long value)
	{
		setProperty("crawler.successiveexceptioncount", value);
	}

	public void setCommonIgnoreRuleList(List<String> ignoreList)
	{
		HierarchicalConfiguration tmpConfig = configurationAt("crawler.ignorelist");
		tmpConfig.clearTree("ignore");
		for (String ignoreRule : ignoreList)
		{
			tmpConfig.addProperty("ignore", ignoreRule);
		}
	}

	public void setSleepTime(long value)
	{
		setProperty("crawler.sleeptime", value);
	}

	public void setFlushPeriod(long value)
	{
		setProperty("crawler.flushperiod", value);
	}

	public String getWarningsFile()
	{
		return getString("crawler.crawlerwarningfile");
	}

}