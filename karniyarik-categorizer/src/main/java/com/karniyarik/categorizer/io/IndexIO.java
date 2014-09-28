package com.karniyarik.categorizer.io;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.index.IndexSearcherFactory;

public class IndexIO
{
	private static IndexIO reader = null;
	
	private IndexSearcherFactory indexSearcherFactory = null;
	private IndexSearcher searcher = null;
	private String category="urun";
	
	private IndexIO()
	{
	}
	
	public static IndexIO getReader()
	{
		if(reader == null)
		{
			reader = new IndexIO();
			reader.init();
		}
		
		return reader;
	}
	
	public void init()
	{
		try
		{
			//SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();
			indexSearcherFactory = new IndexSearcherFactory();
			searcher = indexSearcherFactory.createProductIndexSearcher(category);
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public List<Product> readAll()
	{
		List<Product> result = new ArrayList<Product>();
		
		try
		{
			QueryParser parser = new org.apache.lucene.queryParser.QueryParser(SearchConstants.STORE, new KeywordAnalyzer());
			SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
			for(SiteConfig siteConfig: sitesConfig.getSiteConfigList())
			{
				String sitename = siteConfig.getSiteName();
				org.apache.lucene.search.Query aLuceneQuery = parser.parse(sitename);
				TopDocs topDocs = searcher.search(aLuceneQuery, 200000);
				
				for(int index=0; index<topDocs.scoreDocs.length; index++)
				{
					ScoreDoc scoreDoc = topDocs.scoreDocs[index];
					Document currentDocument = searcher.doc(scoreDoc.doc);	
					Product product = new Product();
					product.setBreadcrumb(currentDocument.get(SearchConstants.BREADCRUMB));
					product.setName(currentDocument.get(SearchConstants.NAME));
					result.add(product);
				}				
			}
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
		
		return result;
	}
}
