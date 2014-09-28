package com.karniyarik.indexer.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.util.StringUtil;

public class IndexerDAOFactory
{

	public IndexerDAO create(SiteConfig siteConfig)
	{
		String path = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getProductsDirectory() + File.separator + siteConfig.getSiteName() + ".txt";
		
		return create(siteConfig, path);
	}
	
	public IndexerDAO create(SiteConfig siteConfig, String path)
	{
		IndexerDAO dao = null;
		File file = new File(path);

		int count = countLines(file);

		LineIterator iterator = null;
		try
		{
			iterator = FileUtils.lineIterator(file, StringUtil.DEFAULT_ENCODING);
		}
		catch (Throwable e)
		{
			// file not found exception causes a null iterator
			// if we try to index before we crawl
			// products file does not exist
			// but zero product count prevents future exceptions
		}

		dao = new SiteIndexerFileDAO(siteConfig.getSiteName(), siteConfig.getUrl(), iterator, count);

		return dao;
	}

	private int countLines(File file)
	{
		int count = 0;

		InputStream is;
		try
		{
			is = new BufferedInputStream(new FileInputStream(file));
			byte[] c = new byte[1024];
			int readChars = 0;
			while ((readChars = is.read(c)) != -1)
			{
				for (int i = 0; i < readChars; ++i)
				{
					if (c[i] == '\n')
						++count;
				}
			}
			is.close();
		}
		catch (Throwable e)
		{
			// if we try to count before crawling
			// products file will not exist
			count = 0;
		}

		return count;
	}

}
