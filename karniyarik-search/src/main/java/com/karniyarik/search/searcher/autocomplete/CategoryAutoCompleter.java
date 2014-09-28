package com.karniyarik.search.searcher.autocomplete;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import com.karniyarik.common.statistics.vo.TopSearch;
import com.karniyarik.common.statistics.vo.TopSearches;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.search.solr.SOLRQuerySearchProxy;
import com.karniyarik.search.solr.SOLRSearcherFactory;

public class CategoryAutoCompleter {
	
	private String categoryName; 
	
	public CategoryAutoCompleter(String categoryName) {
		this.categoryName = categoryName;
	}

	public void indexQueries(TopSearches topSearches)
	{
		try
		{
			SOLRQuerySearchProxy solr = getSearcher();
			if(topSearches != null && topSearches.getTopSearchList() != null && topSearches.getTopSearchList().size() > 0)
			{
				solr.deleteAll(categoryName);
				for (TopSearch topSearch: topSearches.getTopSearchList())
				{
					SolrInputDocument document = new SolrInputDocument();
					document.addField(SearchConstants.QUERY, topSearch.getQuery());
					document.addField(SearchConstants.COUNT, topSearch.getCount());
					document.addField(SearchConstants.VERTICAL, categoryName);
					solr.add(document);
				}
				solr.close();
			}
			
			
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot write search logs to index", e);
		}
//		IndexWriter writer = null;
//		try
//		{
//			Directory directory = searcher.getIndexReader().directory();
//			SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();
//			writer = new IndexWriter(directory, TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer(), new MaxFieldLength(searchConfig.getLuceneMaxFieldLength()));
//			writer.setMaxMergeDocs(searchConfig.getLuceneMaxMergeDocs());
//			writer.setMergeFactor(searchConfig.getLuceneMergeFactor());
//			writer.setMaxBufferedDocs(searchConfig.getLuceneMaxBufferedDocs());
//			writer.setUseCompoundFile(true);
//			writer.deleteAll();
//			writer.commit();
//			
//			for (TopSearch topSearch: topSearches.getTopSearchList())
//			{
//				Document doc = new Document();
//				//doc.add(new Field(SearchConstants.ANALYZED_QUERY, topSearch.getQuery(), Field.Store.NO, Field.Index.ANALYZED));
//				doc.add(new Field(SearchConstants.QUERY, topSearch.getQuery(), Field.Store.YES, Field.Index.NOT_ANALYZED));
//				doc.add(new Field(SearchConstants.COUNT, Integer.toString(topSearch.getCount()), Field.Store.YES, Field.Index.NOT_ANALYZED));
//				writer.addDocument(doc);
//			}
//
//			writer.optimize();
//		}
//		catch (Throwable e)
//		{
//			throw new KarniyarikSearchException("Cannot write search logs to index", e);
//		}
//		finally
//		{
//			if (writer != null)
//			{
//				try
//				{
//					// System.out.println("Return index CategorySearchLogIndexer logCache");
//					TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(writer.getAnalyzer());
//					writer.close();
//				}
//				catch (Throwable e)
//				{
//					throw new KarniyarikSearchException("Cannot close index after writing search logs to index", e);
//				}
//			}
//		}
//		
//		createSearcher();
	}
	
	public List<String> autocomplete(String term)
	{
		List<String> result = new ArrayList<String>();
		result = getSearcher().autocomplete(term, categoryName);
		return result;
//		int max = 10;
//		int count = 0;
//		if(StringUtils.isNotBlank(term))
//		{
//			try {
//				PrefixQuery query = new PrefixQuery(new Term(SearchConstants.QUERY, term));
//				
//				SortField field = new SortField(SearchConstants.COUNT, SortField.INT, true);
//				TopFieldCollector collector = TopFieldCollector.create(new Sort(field), 50, true, false, false, false); 
//				
//				searcher.search(query, collector);
//				
//				ScoreDoc[] scoreDocs = collector.topDocs().scoreDocs;
//				for(ScoreDoc scoreDoc: scoreDocs)
//				{
//					if(count >= max)
//					{
//						break;
//					}
//					
//					Document doc = searcher.doc(scoreDoc.doc);
//					String string = doc.get(SearchConstants.QUERY);
//					if(StringUtils.isNotBlank(string) && !resultSet.contains(string))
//					{
//						resultSet.add(string);
//						result.add(string);
//						count++;
//					}
//				}
//			} catch (Throwable e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	private SOLRQuerySearchProxy getSearcher()
	{
		return SOLRSearcherFactory.getQuerySearcher();
	}

}
