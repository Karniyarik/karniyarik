package com.karniyarik.crawler.queue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import com.karniyarik.common.util.StringUtil;

public class LinksToVisitFileDAO
{

	private final String	SAVE_STATE_FILE	= "saved_state.txt";
	private final File		dir;
	private final Logger	logger;

	public LinksToVisitFileDAO(File linksToVisitDirectory, Logger siteLogger)
	{
		this.dir = linksToVisitDirectory;
		this.logger = siteLogger;
	}

	public void clear()
	{
		try
		{
			FileUtils.deleteDirectory(dir);
			FileUtils.forceMkdir(dir);
		}
		catch (Throwable e)
		{
			logger.error("Can not clear links to visit directory", e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> load()
	{
		List<String> urlList = null;

		File[] blockFileList = getFlushFileList();
		if (blockFileList.length == 0)
		{
			urlList = new ArrayList<String>();
		}
		else
		{
			File blockFile = chooseOldestBlock(blockFileList);
			try
			{
				urlList = FileUtils.readLines(blockFile,
						StringUtil.DEFAULT_ENCODING);
			}
			catch (Throwable e)
			{
				urlList = new ArrayList<String>();
				logger.error("Can not read links to visit block from "
						+ blockFile.getName(), e);
			}

			try
			{
				FileUtils.forceDelete(blockFile);
			}
			catch (Throwable e)
			{
				logger.error("Can not delete block " + blockFile.getName()
						+ " after loading it", e);
			}
		}

		return urlList;
	}

	private File chooseOldestBlock(File[] blockFileList)
	{
		File oldestBlock = blockFileList[0];
		File temp = null;
		for (int i = 1; i < blockFileList.length; i++)
		{
			temp = blockFileList[i];
			if (temp.lastModified() < oldestBlock.lastModified()
					&& !temp.getName().equals(SAVE_STATE_FILE))
			{
				oldestBlock = temp;
			}
		}

		return oldestBlock;
	}

	public void store(List<String> urlList)
	{
		if (urlList.size() > 0)
		{
			File blockFile = new File(dir, UUID.randomUUID().toString()
					+ ".txt");
			try
			{
				blockFile.createNewFile();
				FileUtils.writeLines(blockFile, StringUtil.DEFAULT_ENCODING,
						urlList);
			}
			catch (IOException e)
			{
				logger.error("Can not store links to visit url list", e);
			}
		}
	}

	/**
	 * Stores URLs in memory together with the URLs flushed to files into a
	 * single file.
	 * 
	 * @param urlList
	 */
	public void saveState(List<String> urlList)
	{
		File saveStateFile = new File(dir, SAVE_STATE_FILE);

		try
		{
			// write queue to file
			FileUtils.writeLines(saveStateFile, StringUtil.DEFAULT_ENCODING,
					urlList);

			List<File> sortedFlushFileList = new ArrayList<File>();
			File[] flushFileList = getFlushFileList();
			for (File file : flushFileList)
			{
				sortedFlushFileList.add(file);
			}
			
			Collections.sort(sortedFlushFileList, new Comparator<File>()
			{
				@Override
				public int compare(File o1, File o2)
				{
					return new Long(o1.lastModified()).compareTo(new Long(o2.lastModified()));
				}
			});

			OutputStream out = new FileOutputStream(saveStateFile, Boolean.TRUE);
			InputStream in;
			for (File file : sortedFlushFileList)
			{
				in = new FileInputStream(file);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0)
				{
					out.write(buf, 0, len);
				}
				in.close();
			}

			out.close();
		}
		catch (Throwable e)
		{
			logger.error("Can not save links to visit state", e);
		}
	}
	
	public List<String> restoreState(int queueSize, int blockSize) {
		List<String> queue = new ArrayList<String>(queueSize);
		
		LineIterator iterator = null;
		try
		{
			// delete old flush files
			// their content is store in 
			// save state file
			for (File file : getFlushFileList())
			{
				file.delete();
			}
			
			// fill the queue
			iterator = FileUtils.lineIterator(new File(dir, SAVE_STATE_FILE), StringUtil.DEFAULT_ENCODING);
			while(iterator.hasNext() && queue.size() < queueSize) {
				queue.add(iterator.nextLine());
			}
			
			// create new block files from remaining URLs
			List<String> block = new ArrayList<String>(blockSize);
			while(iterator.hasNext()) {
				if (block.size() == blockSize) {
					store(block);
					block.clear();
				}
				
				block.add(iterator.nextLine());
			}
			
			if (block.size() > 0) {
				store(block);
			}
			
		}
		catch (Throwable e)
		{
			logger.error("Can not store links to visit state", e);
		} finally {
			LineIterator.closeQuietly(iterator);
		}
		
		return queue;
	}
	
	private File[] getFlushFileList() {
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return (!name.equals(SAVE_STATE_FILE));
			}
		};
		
		return dir.listFiles(filter);
	}

}
