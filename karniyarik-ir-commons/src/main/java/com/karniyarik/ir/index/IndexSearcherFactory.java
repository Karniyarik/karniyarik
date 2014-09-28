package com.karniyarik.ir.index;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;


public class IndexSearcherFactory
{

	public IndexSearcher createProductIndexSearcher(String category) {
		Directory directory = new DirectoryFactory().createProductIndexDirectory(category, Boolean.TRUE);
		return createSearcher(directory);
	}

	public IndexReader createProductIndexReader(String category) {
		Directory directory = new DirectoryFactory().createProductIndexDirectory(category, Boolean.TRUE);
		return createReader(directory);
	}
	
	public IndexReader createSearchLogIndexReader(String category) {
		Directory directory = new DirectoryFactory().createSearchLogIndexDirectory(category, Boolean.TRUE);
		return createReader(directory);
	}

	public IndexSearcher createTopSearchesLogIndexSearcher(String category) {
		Directory directory = new DirectoryFactory().createTopSearchesIndexDirectory(category, Boolean.TRUE);
		return createSearcher(directory);
	}

	private IndexSearcher createSearcher(Directory directory) {
		IndexSearcher searcher = null;
		try
		{
			searcher = new IndexSearcher(directory, Boolean.TRUE);
		}
		catch (Throwable e)
		{
			getLogger().error("Cannot create searcher.", e);
		}

		return searcher;
	}

	private IndexReader createReader(Directory directory) {
		IndexReader reader = null;
		try
		{
			reader = IndexReader.open(directory, true);
		}
		catch (Throwable e)
		{
			getLogger().error("Cannot create reader.", e);
		}

		return reader;
	}

	private Logger getLogger() {
		return Logger.getLogger(this.getClass().getName());
	}
	
}
