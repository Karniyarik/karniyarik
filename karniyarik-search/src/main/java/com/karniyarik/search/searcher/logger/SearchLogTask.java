package com.karniyarik.search.searcher.logger;

import java.util.List;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.index.IndexSearcherFactory;

public class SearchLogTask implements Runnable
{
	
	private final CategorySearchLogIndexer searchLogIndexer;
	private final String query;
	
	public SearchLogTask(CategorySearchLogIndexer searchLogIndexer, String query)
	{
		this.searchLogIndexer = searchLogIndexer;
		this.query = query;
	}

	@Override
	public void run()
	{
		searchLogIndexer.log(query);
		if (searchLogIndexer.shouldSaveCache()) {
			IndexReader oldReader = searchLogIndexer.getIndexReader();
			List<String> queryList = searchLogIndexer.refreshQueryList();
			logCache(queryList, oldReader.directory());
			IndexReader reader = new IndexSearcherFactory().createSearchLogIndexReader(searchLogIndexer.getCategory());
			searchLogIndexer.setIndexReader(reader);
			try
			{
				oldReader.close();
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}

	private void logCache(List<String> list, Directory directory)
	{
		IndexWriter writer = null;
		try
		{
			SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();
			// System.out.println("Borrow index CategorySearchLogIndexer logCache");
			writer = new IndexWriter(directory, new WhitespaceAnalyzer(), new MaxFieldLength(searchConfig.getLuceneMaxFieldLength()));
			writer.setMaxMergeDocs(searchConfig.getLuceneMaxMergeDocs());
			writer.setMergeFactor(searchConfig.getLuceneMergeFactor());
			writer.setMaxBufferedDocs(searchConfig.getLuceneMaxBufferedDocs());
			writer.setUseCompoundFile(true);

			for (String query : list)
			{
				Document doc = new Document();
				doc.add(new Field(SearchConstants.KEYWORDS, query, Field.Store.NO, Field.Index.ANALYZED));
				writer.addDocument(doc);
			}

			writer.optimize();
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot write search logs to index", e);
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (Throwable e)
				{
					throw new RuntimeException("Cannot close index after writing search logs to index", e);
				}
			}
		}
	}
	
}
