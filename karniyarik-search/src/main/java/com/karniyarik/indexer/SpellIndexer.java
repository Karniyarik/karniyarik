package com.karniyarik.indexer;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.RAMDirectory;

import com.karniyarik.ir.SearchConstants;

public class SpellIndexer
{
	private final IndexReader	reader;
	private final SpellChecker	spellChecker;

	public SpellIndexer(IndexReader reader, SpellChecker spellChecker)
	{
		this.reader = reader;
		this.spellChecker = spellChecker;
	}

	public Boolean refreshSpellIndex()
	{
		Boolean refreshed = Boolean.FALSE;
		
		try
		{
			spellChecker.clearIndex();
			spellChecker.indexDictionary(new LuceneDictionary(reader, SearchConstants.NAME));
			spellChecker.indexDictionary(new LuceneDictionary(reader, SearchConstants.BREADCRUMB));
			spellChecker.indexDictionary(new LuceneDictionary(reader, SearchConstants.BRAND));
			refreshed = Boolean.TRUE;
		}
		catch (Throwable e)
		{
			getLogger().error("Can not create spell index on web server", e);
		}
		
		return refreshed;
	}

	public void close()
	{
		try
		{
			reader.close();
			
			// TODO: this method is called as a hack
			// so that spell checker closes the index searcher
			// this line must be replaced with spellChecker.close()
			// when such a method is implemented.
			spellChecker.setSpellIndex(new RAMDirectory());
		}
		catch (IOException e)
		{
			getLogger().error("Can not close index reader after creating spell index", e);
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
