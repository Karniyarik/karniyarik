package com.karniyarik.indexer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.notifier.ExceptionNotifier;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.SiteStat;
import com.karniyarik.common.util.IndexRefreshUtil;
import com.karniyarik.common.util.StatisticsWebServiceUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.indexer.dao.IndexerDAO;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.search.indexer.RankHelper;
import com.karniyarik.search.indexer.util.ProductDocumentUtil;
import com.karniyarik.search.solr.SOLRIndexerPool;
import com.karniyarik.search.solr.SOLRIndexerProxy;

public class SiteIndexer
{

	private final IndexerDAO		dao;
	private final CategoryConfig	categoryConfig;
	private final JobExecutionStat	stat;
	private SiteConfig siteConfig;
	private SOLRIndexerProxy solr;
	
	public SiteIndexer(JobExecutionStat stat, IndexerDAO dao, CategoryConfig categoryConfig, SiteConfig config)
	{
		this.stat = stat;
		this.dao = dao;
		this.categoryConfig = categoryConfig;
		this.siteConfig = config;
		solr = SOLRIndexerPool.getInstance().getIndexer(categoryConfig.getName());
	}

	public final void index() 
	{
		RankHelper rankHelper = new RankHelper(categoryConfig,siteConfig);
		
		StopWatch watch = new StopWatch();
		watch.start();
		
		if (!dao.hasNext())
		{
			// start index statistics so that stop watch begins
			// when we call indexingFailed stop watch is being stopped.
			// If we do not start indexing here stop watch will throw an
			// exception
			// since it is being stopped before being started.
			stat.startIndex(dao.getProductCount());
			watch.stop();
			stat.indexingFailed("No products to index");
			getLogger().info("Indexer will not publish to web server since there are no products to index from " + stat.getSiteName());
		}
		else
		{
			stat.startIndex(dao.getProductCount());

			try
			{
				deleteOldDocuments();
				
				while (dao.hasNext())
				{
					addDocument(dao.getNextProduct(), rankHelper);
				}

				//solr.commit();
				//solr.optimize();
				stat.endIndex();
			}
			catch (Throwable e)
			{
				getLogger().error("Could not index products of " + stat.getSiteName(), e);
				stat.indexingFailed("Could not index site products " + e.getMessage());
			}
			finally
			{
				rankHelper = null;
				watch.stop();
				statisticsCall(dao.getProductCount());
				refreshCall();
			}
		}
		
		try {
			close();
		} catch (Exception e) {
			stat.indexingFailed("Could not index site products " + e.getMessage());
		}
		
		stat.setIndexingTime(watch.toString());
	}
	
	public void reduceBoost()
	{
		int blockSize = 1000;
		int pageCount = 0;
		
		try {
			long totalSize = solr.getSiteDocsCount(siteConfig.getSiteName());
			if(totalSize > 0)
			{
				pageCount = (int) Math.ceil(totalSize * 1.0 / blockSize);
				List<SolrInputDocument> newDocs = new LinkedList<SolrInputDocument>();
				List<String> ids = new LinkedList<String>();
				
				solr.delete(siteConfig.getSiteName());
				
//				for(int page=0; page < pageCount; page++)
//				{
//					newDocs.clear();
//					ids.clear();
//					QueryResponse documents = solr.getDocuments(siteConfig.getSiteName(), page, blockSize);
//					
//					if(documents != null && documents.getResults() != null)
//					{
//						for(SolrDocument doc: documents.getResults())
//						{
//							SolrInputDocument newDoc = new SolrInputDocument();
//							for(String fieldName: doc.getFieldNames())
//							{
//								if(!fieldName.equals(SearchConstants.KEYWORDS))
//								{
//									newDoc.addField(fieldName, doc.getFieldValue(fieldName), 0.00001f);	
//								}
//							}
//							newDocs.add(newDoc);
//							ids.add((String)doc.getFieldValue(SearchConstants.ID));
//						}
//						
//						solr.deleteDocuments(ids);
//						solr.add(newDocs);
//					}
//				}			
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			close();
		}
	}

	public void close()
	{
		if(solr != null)
		{
			solr.close();
			SOLRIndexerPool.getInstance().returnIndexer(solr);	
		}
		
		dao.close();
	}

	private void addDocument(Product product, RankHelper rankHelper)
	{
		try
		{
			float rank = rankHelper.getRank(product);
			
			SolrInputDocument aDoc = ProductDocumentUtil.prepareDocument(product, categoryConfig);
			aDoc.setDocumentBoost(rank);
			solr.add(aDoc);
			stat.productIndexed();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not add document to " + stat.getSiteName() + " index", e);
		}
	}

	private void deleteOldDocuments()
	{
		solr.delete(siteConfig.getSiteName());
	}
	
	private void statisticsCall(int productCount)
	{
		try {
			SiteStat stat = new SiteStat();
			stat.setSiteName(siteConfig.getSiteName());
			stat.setProductCount(productCount);
			stat.setDate(new Date().getTime());
			stat.setDatafeed(siteConfig.hasDatafeed());
			StatisticsWebServiceUtil.sendSiteStat(stat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void refreshCall()
	{
		try
		{
			IndexRefreshUtil.callWebIndexRefresh();
		}
		catch (Throwable e)
		{
			ExceptionNotifier.sendException("index-refresh-failed", "index merge succeeded but refresh failed", "", e);
			getLogger().error("Can not call refresh index on ");
		}
	}
	
	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
