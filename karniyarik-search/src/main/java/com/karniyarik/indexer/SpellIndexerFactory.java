package com.karniyarik.indexer;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;

import com.karniyarik.ir.index.DirectoryFactory;

public class SpellIndexerFactory
{

	public SpellIndexer createSpellIndexer(String category)
	{
		Directory spellIndexDirectory = null;
		Directory luceneIndexDirectory = null;
		IndexReader reader = null;
		SpellChecker spellChecker = null;
		SpellIndexer spellIndexer = null;
		try
		{
			DirectoryFactory directoryFactory = new DirectoryFactory();
			spellIndexDirectory = directoryFactory.createSpellIndexDirectory(category);
			luceneIndexDirectory = directoryFactory.createProductIndexDirectory(category, Boolean.FALSE);

			reader = IndexReader.open(luceneIndexDirectory, Boolean.TRUE);
			spellChecker = new SpellChecker(spellIndexDirectory);
			spellIndexer = new SpellIndexer(reader, spellChecker);
		}
		catch (Throwable e)
		{
			getLogger().error("Can not create spell indexer", e);
		}

		return spellIndexer;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
