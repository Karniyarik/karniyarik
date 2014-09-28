package com.karniyarik.ir.index;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.util.StreamUtil;

public class DirectoryFactory
{
	private final String	PRODUCT_INDEX		= "productindex";
	private final String	SPONSORED_INDEX		= "sponsoredindex";
	private final String	SPELL_INDEX			= "spellindex";
	private final String	SEARCH_LOG_INDEX	= "searchlog";
	private final String	TOP_SEARCH_INDEX	= "querylog";

	public Directory createProductIndexDirectory(String category, Boolean create)
	{
		return openDirectory(getProductIndexPath(category), create);
	}

	public Directory createSponsoredIndexDirectory(String category, Boolean create)
	{
		return openDirectory(getSponsoredIndexPath(category), create);
	}

	public Directory createSpellIndexDirectory(String category)
	{
		// do not open a lucene index to spell directory
		// it will be opened by spell checker itself
		return openDirectory(getSpellIndexPath(category), false);
	}

	public Directory createSearchLogIndexDirectory(String category, Boolean create)
	{
		return openDirectory(getSearchLogIndexPath(category), create);
	}

	public Directory createTopSearchesIndexDirectory(String category, Boolean create)
	{
		return openDirectory(getTopSearchesIndexPath(category), create);
	}

	private Directory openDirectory(String path, Boolean createLuceneIndex)
	{
		int luceneMaxFieldLength = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getLuceneMaxFieldLength();
		Directory directory = null;
		IndexWriter writer = null;
		try
		{

			File dir = new File(StreamUtil.getURL(path).toURI());
			if (!dir.exists())
			{
				FileUtils.forceMkdir(dir);
			}
			directory = FSDirectory.open(dir);

			if (createLuceneIndex)
			{
				if (!IndexReader.indexExists(directory)) {
					writer = new IndexWriter(directory, null, new MaxFieldLength(luceneMaxFieldLength));
				}
			}
		}
		catch (Throwable e)
		{
			getLogger().error("Can not open lucene directory from " + path, e);
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
					getLogger().error("Can not close index writer used for creating a clean index directory", e);
				}
				finally
				{
					writer = null;
				}
			}
		}
		return directory;
	}

	public String getProductIndexPath(String category)
	{
		return getIndexDirectoryPath() + File.separator + category + File.separator + PRODUCT_INDEX;
	}

	public String getSpellIndexPath(String category)
	{
		return getIndexDirectoryPath() + File.separator + category + File.separator + SPELL_INDEX;
	}

	public String getSearchLogIndexPath(String category)
	{
		return getIndexDirectoryPath() + File.separator + category + File.separator + SEARCH_LOG_INDEX;
	}

	public String getTopSearchesIndexPath(String category)
	{
		return getIndexDirectoryPath() + File.separator + category + File.separator + TOP_SEARCH_INDEX;
	}

	public String getSponsoredIndexPath(String category)
	{
		return getIndexDirectoryPath() + File.separator + category + File.separator + SPONSORED_INDEX;
	}

	private String getIndexDirectoryPath()
	{
		return KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getIndexDirectory();
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
