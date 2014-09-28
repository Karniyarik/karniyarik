package com.karniyarik.search.searcher;

import org.apache.log4j.Logger;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;

import com.karniyarik.ir.index.DirectoryFactory;

public class SpellCheckerFactory
{

	public SpellChecker create(String category)
	{
		SpellChecker spellChecker = null;
		Directory spellDirectory = null;
		try
		{
			spellDirectory = new DirectoryFactory().createSpellIndexDirectory(category);
			spellChecker = new SpellChecker(spellDirectory);
		}
		catch (Throwable e)
		{
			getLogger().error("Can not create spell checker", e);
			throw new RuntimeException("Can not create spell checker", e);
		}

		return spellChecker;
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
