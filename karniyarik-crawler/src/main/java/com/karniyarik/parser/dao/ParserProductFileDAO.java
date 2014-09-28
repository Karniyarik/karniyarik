package com.karniyarik.parser.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.parser.pojo.Product;

public class ParserProductFileDAO
{

	private final File		productsFile;
	private final Logger	logger;

	public ParserProductFileDAO(File productsFile)
	{
		this.productsFile = productsFile;
		this.logger = Logger.getLogger(this.getClass().getName());
	}

	public void saveProducts(List<Product> productList)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(productsFile, Boolean.TRUE);

			for (Product product : productList)
			{
				fileWriter.write(JSONUtil.getJSON(product) + "\n");
			}

			fileWriter.flush();
			fileWriter.close();
		}
		catch (Throwable e)
		{
			logger.error("Can not save products to " + productsFile.getAbsolutePath());
		}
	}

}
