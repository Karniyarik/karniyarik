package com.karniyarik.crawler.queue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.karniyarik.common.util.StringUtil;

public class LinksToVisitFileDAOTest
{

	private static final int	BLOCK_SIZE	= 1000;

	@SuppressWarnings("unchecked")
	@Test
	public void testStore()
	{
		File dir = getCleanDirectory();
		LinksToVisitFileDAO dao = new LinksToVisitFileDAO(dir, getLogger());
		List<String> inputList = getUrlList("test_block");

		dao.store(inputList);

		File[] fileList = dir.listFiles();
		Assert.assertTrue(fileList.length == 1);

		File blockFile = fileList[0];

		try
		{
			List<String> lines = FileUtils.readLines(blockFile, StringUtil.DEFAULT_ENCODING);
			Assert.assertEquals(lines.size(), inputList.size());
			for (int i = 0; i < lines.size(); i++)
			{
				Assert.assertEquals(lines.get(i), inputList.get(i));
			}
		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}

		dao = null;
	}

	private List<String> getUrlList(String blockName)
	{
		List<String> inputList = new ArrayList<String>();

		for (int i = 0; i < BLOCK_SIZE; i++)
		{
			inputList.add(blockName + "_" + i);
		}
		return inputList;
	}
	
	@Test
	public void testLoad() {
		File dir = getCleanDirectory();
		LinksToVisitFileDAO dao = new LinksToVisitFileDAO(dir, getLogger());
		
		List<List<String>> blockList = new ArrayList<List<String>>();
		List<String> urlList;
		for (int i = 0; i < 10; i++)
		{
			urlList = getUrlList("block" + i);
			dao.store(urlList);
			blockList.add(urlList);
		}
		
		List<String> loaded = null;
		for (List<String> block : blockList)
		{
			loaded = dao.load();
			Assert.assertEquals(block.size(), loaded.size());
			
			for (int i = 0; i < block.size(); i++)
			{
				Assert.assertEquals(block.get(i), loaded.get(i));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSaveState() {
		File dir = getCleanDirectory();
		LinksToVisitFileDAO dao = new LinksToVisitFileDAO(dir, getLogger());
		
		List<String> all = new ArrayList<String>();
		List<String> queue = new ArrayList<String>();
		for (int i = 0; i < 3; i++)
		{
			queue.addAll(getUrlList("queue" + i));
		}
		
		all.addAll(queue);
		
		List<String> urlList;
		for (int i = 0; i < 5; i++)
		{
			urlList = getUrlList("block" + i);
			all.addAll(urlList);
			dao.store(urlList);
		}
		
		dao.saveState(queue);
		
		File saveStateFile = new File(dir, "saved_state.txt");
		try
		{
			List<String> lines = FileUtils.readLines(saveStateFile, StringUtil.DEFAULT_ENCODING);
			Assert.assertEquals(all.size(), lines.size());
			for (int i = 0; i < lines.size(); i++)
			{
				Assert.assertEquals(lines.get(i), all.get(i));
			}
		
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRestoreState() {
		File dir = getCleanDirectory();
		LinksToVisitFileDAO dao = new LinksToVisitFileDAO(dir, getLogger());
		
		List<String> all = new ArrayList<String>();
		List<String> queue = new ArrayList<String>();
		for (int i = 0; i < 3; i++)
		{
			queue.addAll(getUrlList("queue" + i));
		}
		
		all.addAll(queue);
		
		List<String> urlList;
		for (int i = 0; i < 5; i++)
		{
			urlList = getUrlList("block" + i);
			all.addAll(urlList);
			dao.store(urlList);
		}
		
		dao.saveState(queue);
		
		List<String> restoredAll = dao.restoreState(BLOCK_SIZE, BLOCK_SIZE);
		
		List<String> loaded = dao.load();
		while(loaded.size() > 0) {
			restoredAll.addAll(loaded);
			loaded = dao.load();
		}
		
		Assert.assertEquals(all.size(), restoredAll.size());
		for (int i = 0; i < all.size(); i++)
		{
			Assert.assertEquals(all.get(i), restoredAll.get(i));
		}
		
	}

	@After
	public void clean() {
		File dir = getCleanDirectory();
		try
		{
			FileUtils.forceDelete(dir);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Logger getLogger()
	{
		return Logger.getLogger(LinksToVisitFileDAO.class.getName());
	}

	private File getCleanDirectory()
	{
		File testDir = new File("test");
		try
		{
			if (testDir.exists())
			{
				FileUtils.forceDelete(testDir);
			}
			FileUtils.forceMkdir(testDir);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return testDir;
	}

}
