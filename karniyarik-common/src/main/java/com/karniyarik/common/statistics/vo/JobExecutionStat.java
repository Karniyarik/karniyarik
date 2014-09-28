package com.karniyarik.common.statistics.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.karniyarik.common.util.DateUtil;
import com.karniyarik.common.util.FetchTimeCalculator;

@XmlRootElement(name = "results", namespace = "http://www.karniyarik.com/api")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jobExecutionStat")
public class JobExecutionStat implements Serializable
{
	private static final long serialVersionUID = -96677665510787093L;

	@XmlElement(name = "status")
	private JobExecutionState					status						= JobExecutionState.IDLE;

	@XmlElement(name = "siteName")
	private String								siteName					= "";

	@XmlElement(name = "totalVisitedLinks")
	private int									totalVisitedLinks			= 0;

	@XmlElement(name = "totalLinksToVisit")
	private int									totalLinksToVisit			= 0;

	@XmlElement(name = "totalExceptionCount")
	private int									totalExceptionCount			= 0;

	@XmlElement(name = "startDate")
	private long								startDate					= 0;

	@XmlElement(name = "endDate")
	private long								endDate						= 0;

	@XmlElement(name = "totalFetchTime")
	private long								totalFetchTime				= 0;

	@XmlElement(name = "fetchTimeCount")
	private long								fetchTimeCount				= 0;

	@XmlElement(name = "totalLinkCount")
	private int									totalLinkCount				= 0;

	@XmlElement(name = "totalLinksToVisitFlush")
	private int									totalLinksToVisitFlush		= 0;

	@XmlElement(name = "totalLinksToVisitRead")
	private int									totalLinksToVisitRead		= 0;

	@XmlElement(name = "totalDBOperationCount")
	private int									totalDBOperationCount		= 0;

	@XmlElement(name = "totalMemoryCacheHitCount")
	private int									totalMemoryCacheHitCount	= 0;

	@XmlElement(name = "totalMemoryCacheMissCount")
	private int									totalMemoryCacheMissCount	= 0;

	@XmlElement(name = "totalDBCacheMissCount")
	private int									totalDBCacheMissCount		= 0;

	@XmlElement(name = "totalDBCacheHitCount")
	private int									totalDBCacheHitCount		= 0;

	@XmlElement(name = "totalVisitedLinkFlush")
	private int									totalVisitedLinkFlush		= 0;

	@XmlElement(name = "totalVisitedLinkRead")
	private int									totalVisitedLinkRead		= 0;

	@XmlElement(name = "visitedLinkCacheSize")
	private int									visitedLinkCacheSize		= 0;

	@XmlElement(name = "windowedAvgFetchTime")
	private long								windowedAvgFetchTime		= 0;

	@XmlElement(name = "parsedPageCount")
	private int									parsedPageCount				= 0;

	@XmlElement(name = "productCount")
	private int									productCount				= 0;

	@XmlElement(name = "productMissCount")
	private int									productMissCount			= 0;

	@XmlElement(name = "brandsParsedCount")
	private int									brandsParsedCount			= 0;

	@XmlElement(name = "brandsMissedCount")
	private int									brandsMissedCount			= 0;

	@XmlElement(name = "breadcrumbsParsedCount")
	private int									breadcrumbsParsedCount		= 0;

	@XmlElement(name = "breadcrumbsMissedCount")
	private int									breadcrumbsMissedCount		= 0;

	@XmlElement(name = "imagesParsedCount")
	private int									imagesParsedCount			= 0;

	@XmlElement(name = "imagesMissedCount")
	private int									imagesMissedCount			= 0;

	@XmlElement(name = "duplicateProductCount")
	private int									duplicateProductCount		= 0;

	@XmlElement(name = "totalProductsToIndex")
	private int									totalProductsToIndex		= 0;

	@XmlElement(name = "productsIndexed")
	private int									productsIndexed				= 0;

	@XmlElement(name = "productsRemaining")
	private int									productsRemaining			= 0;

	@XmlElement(name = "statusMessage")
	private String								statusMessage				= "";

	@XmlElement(name = "indexingTime")
	private String								indexingTime				= "";

	@XmlElement(name = "runningServer")
	private String								runningServer				= "";

	@XmlElement(name = "datafeed")
	private boolean								datafeed					= false;

	@XmlElement(name = "nextExecutionDate")
	private long								nextExecutionDate			= 0;
	
	private transient FetchTimeCalculator	fetchTimeCalculator = null;
	
	public JobExecutionStat()
	{
		statusMessage = "Idle";
	}

	public int getTotalVisitedLinks()
	{
		return totalVisitedLinks;
	}

	public int getTotalLinksToVisit()
	{
		return totalLinksToVisit;
	}

	public int getTotalExceptionCount()
	{
		return totalExceptionCount;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public void setTotalVisitedLinks(int totalVisitedLinks)
	{
		this.totalVisitedLinks = totalVisitedLinks;
	}

	public void setTotalLinksToVisit(int totalLinksToVisit)
	{
		this.totalLinksToVisit = totalLinksToVisit;
	}

	public void setTotalExceptionCount(int totalExceptionCount)
	{
		this.totalExceptionCount = totalExceptionCount;
	}

	public long getStartDate()
	{
		return startDate;
	}

	public void setStartDate(long startDate)
	{
		this.startDate = startDate;
	}

	public long getEndDate()
	{
		return endDate;
	}

	public void setEndDate(long endDate)
	{
		this.endDate = endDate;
	}

	public long calculateAvgFetchTime()
	{
		if (fetchTimeCount != 0)
		{
			return totalFetchTime / fetchTimeCount;
		}

		return 0;
	}

	public long getTotalFetchTime()
	{
		return totalFetchTime;
	}

	public void setTotalFetchTime(long totalFetchTime)
	{
		this.totalFetchTime = totalFetchTime;
	}

	public void dbCacheHit(String url)
	{
		totalDBCacheHitCount++;
	}

	public void dbCacheMiss(String url)
	{
		totalDBCacheMissCount++;
	}

	public void dbOperation()
	{
		totalDBOperationCount++;
	}

	public void exceptionOccured(Throwable e)
	{
		totalExceptionCount++;
	}

	public void linksToVisitFlush()
	{
		totalLinksToVisitFlush++;
	}

	public void linksToVisitRead()
	{
		totalLinksToVisitRead++;
	}

	public void linkVisited(String url)
	{
		totalVisitedLinks++;
	}

	public void memoryCacheHit(String url)
	{
		totalMemoryCacheHitCount++;
	}

	public void memoryCacheMiss(String url)
	{
		totalMemoryCacheMissCount++;
	}

	public void visitedLinkFlush()
	{
		totalVisitedLinkFlush++;
	}

	public void visitedLinkRead()
	{
		totalVisitedLinkRead++;
	}

	public void linksToVisitAdded(String url)
	{
		totalLinksToVisit++;
	}

	public void linksToVisitPopped(String url)
	{
		totalLinksToVisit--;
	}

	public void linksFound(int linkCount)
	{
		totalLinkCount += linkCount;
	}

	public void crawlerStarted()
	{
		setStartDate(DateUtil.getCurrentLocalDate().getTime());
		status = JobExecutionState.CRAWLING;
	}

	public void crawlingEnded()
	{
		setEndDate(DateUtil.getCurrentLocalDate().getTime());
		status = JobExecutionState.CRAWLING_ENDED;
	}

	public void crawlingEnding()
	{
		status = JobExecutionState.CRAWLING_ENDING;
	}

	public void crawlingPaused()
	{
		status = JobExecutionState.CRAWLING_PAUSED;
	}

	public void crawlingPausing()
	{
		status = JobExecutionState.CRAWLING_PAUSING;
	}

	public void crawlingFailed()
	{
		status = JobExecutionState.CRAWLING_FAILED;
	}

	public void fetchTimeGathered(long fetchTime)
	{
		totalFetchTime += fetchTime;
		fetchTimeCount++;
		if(fetchTimeCalculator == null)
		{
			fetchTimeCalculator = new FetchTimeCalculator();
		}
		fetchTimeCalculator.fetchTimeGathered(fetchTime);
		windowedAvgFetchTime = fetchTimeCalculator.getWindowedAvgFetchTime();
	}

	public long getWindowedAvgFetchTime()
	{
		return windowedAvgFetchTime;
	}

	public String getStatusMessage()
	{
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage)
	{
		this.statusMessage = statusMessage;
	}

	public long getFetchTimeCount()
	{
		return fetchTimeCount;
	}

	public void setFetchTimeCount(long fetchTimeCount)
	{
		this.fetchTimeCount = fetchTimeCount;
	}

	public int getTotalLinkCount()
	{
		return totalLinkCount;
	}

	public void setTotalLinkCount(int totalLinkCount)
	{
		this.totalLinkCount = totalLinkCount;
	}

	public int getTotalLinksToVisitFlush()
	{
		return totalLinksToVisitFlush;
	}

	public void setTotalLinksToVisitFlush(int totalLinksToVisitFlush)
	{
		this.totalLinksToVisitFlush = totalLinksToVisitFlush;
	}

	public int getTotalLinksToVisitRead()
	{
		return totalLinksToVisitRead;
	}

	public void setTotalLinksToVisitRead(int totalLinksToVisitRead)
	{
		this.totalLinksToVisitRead = totalLinksToVisitRead;
	}

	public int getTotalDBOperationCount()
	{
		return totalDBOperationCount;
	}

	public void setTotalDBOperationCount(int totalDBOperationCount)
	{
		this.totalDBOperationCount = totalDBOperationCount;
	}

	public int getTotalMemoryCacheHitCount()
	{
		return totalMemoryCacheHitCount;
	}

	public void setTotalMemoryCacheHitCount(int totalMemoryCacheHitCount)
	{
		this.totalMemoryCacheHitCount = totalMemoryCacheHitCount;
	}

	public int getTotalMemoryCacheMissCount()
	{
		return totalMemoryCacheMissCount;
	}

	public void setTotalMemoryCacheMissCount(int totalMemoryCacheMissCount)
	{
		this.totalMemoryCacheMissCount = totalMemoryCacheMissCount;
	}

	public int getTotalDBCacheMissCount()
	{
		return totalDBCacheMissCount;
	}

	public void setTotalDBCacheMissCount(int totalDBCacheMissCount)
	{
		this.totalDBCacheMissCount = totalDBCacheMissCount;
	}

	public int getTotalDBCacheHitCount()
	{
		return totalDBCacheHitCount;
	}

	public void setTotalDBCacheHitCount(int totalDBCacheHitCount)
	{
		this.totalDBCacheHitCount = totalDBCacheHitCount;
	}

	public int getTotalVisitedLinkFlush()
	{
		return totalVisitedLinkFlush;
	}

	public void setTotalVisitedLinkFlush(int totalVisitedLinkFlush)
	{
		this.totalVisitedLinkFlush = totalVisitedLinkFlush;
	}

	public int getTotalVisitedLinkRead()
	{
		return totalVisitedLinkRead;
	}

	public void setTotalVisitedLinkRead(int totalVisitedLinkRead)
	{
		this.totalVisitedLinkRead = totalVisitedLinkRead;
	}

	public int getVisitedLinkCacheSize()
	{
		return visitedLinkCacheSize;
	}

	public void setVisitedLinkCacheSize(int visitedLinkCacheSize)
	{
		this.visitedLinkCacheSize = visitedLinkCacheSize;
	}

	public synchronized void duplicateProductFound()
	{
		duplicateProductCount = duplicateProductCount + 1;
		parsedPageCount = parsedPageCount + 1;
	}

	public synchronized void productFound()
	{
		productCount = productCount + 1;
		parsedPageCount = parsedPageCount + 1;
	}

	public synchronized void productMissed()
	{
		productMissCount = productMissCount + 1;
		parsedPageCount = parsedPageCount + 1;
	}

	public synchronized void brandMissed()
	{
		brandsMissedCount = brandsMissedCount + 1;
	}

	public synchronized void brandParsed()
	{
		brandsParsedCount = brandsParsedCount + 1;
	}

	public synchronized void breadcrumbMissed()
	{
		breadcrumbsMissedCount = breadcrumbsMissedCount + 1;
	}

	public synchronized void breadcrumbParsed()
	{
		breadcrumbsParsedCount = breadcrumbsParsedCount + 1;
	}

	public synchronized void imageMissed()
	{
		imagesMissedCount = imagesMissedCount + 1;
	}

	public synchronized void imageParsed()
	{
		imagesParsedCount = imagesParsedCount + 1;
	}

	public int getParsedPageCount()
	{
		return parsedPageCount;
	}

	public void setParsedPageCount(int parsedPageCount)
	{
		this.parsedPageCount = parsedPageCount;
	}

	public int getProductCount()
	{
		return productCount;
	}

	public void setProductCount(int productCount)
	{
		this.productCount = productCount;
	}

	public int getProductMissCount()
	{
		return productMissCount;
	}

	public void setProductMissCount(int productMissCount)
	{
		this.productMissCount = productMissCount;
	}

	public int getBrandsParsedCount()
	{
		return brandsParsedCount;
	}

	public void setBrandsParsedCount(int brandsParsedCount)
	{
		this.brandsParsedCount = brandsParsedCount;
	}

	public int getBrandsMissedCount()
	{
		return brandsMissedCount;
	}

	public void setBrandsMissedCount(int brandsMissedCount)
	{
		this.brandsMissedCount = brandsMissedCount;
	}

	public int getBreadcrumbsParsedCount()
	{
		return breadcrumbsParsedCount;
	}

	public void setBreadcrumbsParsedCount(int breadcrumbsParsedCount)
	{
		this.breadcrumbsParsedCount = breadcrumbsParsedCount;
	}

	public int getBreadcrumbsMissedCount()
	{
		return breadcrumbsMissedCount;
	}

	public void setBreadcrumbsMissedCount(int breadcrumbsMissedCount)
	{
		this.breadcrumbsMissedCount = breadcrumbsMissedCount;
	}

	public int getImagesParsedCount()
	{
		return imagesParsedCount;
	}

	public void setImagesParsedCount(int imagesParsedCount)
	{
		this.imagesParsedCount = imagesParsedCount;
	}

	public int getImagesMissedCount()
	{
		return imagesMissedCount;
	}

	public void setImagesMissedCount(int imagesMissedCount)
	{
		this.imagesMissedCount = imagesMissedCount;
	}

	public int getDuplicateProductCount()
	{
		return duplicateProductCount;
	}

	public void setDuplicateProductCount(int duplicateProductCount)
	{
		this.duplicateProductCount = duplicateProductCount;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public void setWindowedAvgFetchTime(long windowedAvgFetchTime)
	{
		this.windowedAvgFetchTime = windowedAvgFetchTime;
	}

	public boolean checkExcessiveProductMisses()
	{
		return (productMissCount * 1d / parsedPageCount) > 0.45;
	}

	public boolean checkExcessiveDuplicateProducts()
	{
		return (duplicateProductCount * 1d / productCount) > 0.1;
	}

	public JobExecutionState getStatus()
	{
		return status;
	}

	public void setStatus(JobExecutionState status)
	{
		this.status = status;
	}

	public int getTotalProductsToIndex()
	{
		return totalProductsToIndex;
	}

	public void setTotalProductsToIndex(int totalProductsToIndex)
	{
		this.totalProductsToIndex = totalProductsToIndex;
	}

	public int getProductsIndexed()
	{
		return productsIndexed;
	}

	public void setProductsIndexed(int productsIndexed)
	{
		this.productsIndexed = productsIndexed;
	}

	public int getProductsRemaining()
	{
		return productsRemaining;
	}

	public void setProductsRemaining(int productsRemaining)
	{
		this.productsRemaining = productsRemaining;
	}

	public String getRunningServer()
	{
		return runningServer;
	}

	public void setRunningServer(String runningServer)
	{
		this.runningServer = runningServer;
	}

	public void startIndex(int totalProductsToIndex)
	{
		this.totalProductsToIndex = totalProductsToIndex;
		productsIndexed = 0;
		productsRemaining = totalProductsToIndex;
		status = JobExecutionState.INDEXING;
		statusMessage = "Indexing started.";
	}

	public void startPublishing()
	{
		status = JobExecutionState.PUBLISHING;
	}

	public void startCallingMergeServlets()
	{
		status = JobExecutionState.CALLING_MERGE_SERVLETS;
	}

	public void mergeServletCallsFailed(String reason)
	{
		status = JobExecutionState.MERGE_CALLS_FAILED;
		statusMessage = reason;
	}

	public void rankingStarted()
	{
		status = JobExecutionState.RANKING;
		statusMessage = "Ranking";
	}

	public void rankingFailed(String reason)
	{
		status = JobExecutionState.RANKING_FAILED;
		statusMessage = "Ranking failed: " + reason;
	}

	public void rankingEnded()
	{
		status = JobExecutionState.RANKING_ENDED;
		statusMessage = "Ranking ended";
	}

	public void endIndex()
	{
		status = JobExecutionState.INDEXING_ENDED;
		statusMessage = "Indexing ended successfully";
	}

	public void productIndexed()
	{
		productsIndexed = productsIndexed + 1;
		productsRemaining = productsRemaining - 1;
	}

	public void indexingFailed(String reason)
	{
		status = JobExecutionState.INDEXING_FAILED;
		statusMessage = reason;
	}

	public void publishingFailed(String reason)
	{
		status = JobExecutionState.PUBLISHING_FAILED;
		statusMessage = reason;
	}

	public void publishingEnded()
	{
		status = JobExecutionState.PUBLISHING_ENDED;
	}

	public void mergeCallsEnded()
	{
		status = JobExecutionState.MERGE_CALLS_ENDED;
	}

	public String getIndexingTime()
	{
		return indexingTime;
	}

	public void setIndexingTime(String indexingTime)
	{
		this.indexingTime = indexingTime;
	}

	public void end()
	{
		status = JobExecutionState.ENDED;
		statusMessage = "Job Ended Successfully";
	}
	
	public void setDatafeed(boolean datafeed)
	{
		this.datafeed = datafeed;
	}
	
	public boolean isDatafeed()
	{
		return datafeed;
	}
	
	public long getNextExecutionDate() {
		return nextExecutionDate;
	}
	
	public void setNextExecutionDate(long nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}
}