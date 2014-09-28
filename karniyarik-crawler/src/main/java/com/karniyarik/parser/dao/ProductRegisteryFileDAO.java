package com.karniyarik.parser.dao;

import java.io.File;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.parser.pojo.Product;

public class ProductRegisteryFileDAO
{

	private final File		productsFile;
	private final Logger	logger;

	public ProductRegisteryFileDAO(File productsFile)
	{
		this.productsFile = productsFile;
		this.logger = Logger.getLogger(this.getClass().getName());
	}

	public TreeSet<Integer> loadRegistery()
	{
		TreeSet<Integer> set = new TreeSet<Integer>();
		LineIterator iterator = null;
		Product product = null;
		try
		{
			// fill the queue
			iterator = FileUtils.lineIterator(productsFile, StringUtil.DEFAULT_ENCODING);
			while (iterator.hasNext())
			{
				product = JSONUtil.parseJSON(iterator.nextLine(), Product.class);
				if (product != null)
				{
					set.add(product.hashCode());
				}
			}
		}
		catch (Throwable e)
		{
			logger.error("Can not load product registery from products file " + productsFile.getAbsolutePath(), e);
		}
		finally
		{
			LineIterator.closeQuietly(iterator);
		}

		return set;
	}

}
