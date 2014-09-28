package com.karniyarik.parser.core;

import java.util.ArrayList;
import java.util.List;

import com.karniyarik.parser.dao.ParserProductFileDAO;
import com.karniyarik.parser.pojo.Product;

public class SiteProductBuffer
{
	private final List<Product>			buffer;
	private final int					maxSize;
	private final ParserProductFileDAO	dao;

	public SiteProductBuffer(int maxSize, ParserProductFileDAO dao)
	{
		this.maxSize = maxSize;
		this.dao = dao;
		this.buffer = new ArrayList<Product>();
	}

	public void addProduct(Product product)
	{
		synchronized (buffer)
		{
			buffer.add(product);
			if (buffer.size() == maxSize)
			{
				dao.saveProducts(buffer);
				buffer.clear();
			}
		}
	}

	public Product get(int hash)
	{
		for (Product p : buffer)
		{
			if (p.hashCode() == hash)
			{
				return p;
			}
		}
		return null;
	}

	// assumes single thread access
	public void flush()
	{
		dao.saveProducts(buffer);
		buffer.clear();
	}

}
