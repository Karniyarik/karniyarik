package com.karniyarik.pagerank.dao;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import com.karniyarik.common.db.DBConnectionProvider;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.parser.pojo.Product;

public class NormalizerDAO
{
	private final int					UPDATE_FLUSH_LIMIT	= 10000;
	private final String				getProductHitsQuery;
	private final String				siteName;
	private final Map<String, Integer>	updateMap;
	private final File					productsFile;

	public NormalizerDAO(String siteName, String visitedLinksTableName, File productsFile)
	{
		this.siteName = siteName;
		this.updateMap = new HashMap<String, Integer>();
		this.productsFile = productsFile;
		this.getProductHitsQuery = "SELECT FLD_URL, FLD_HIT FROM " + visitedLinksTableName + " WHERE FLD_HAS_PRODUCT=1 ORDER BY FLD_HIT";
	}

	public List<ProductHit> getProductHits()
	{
		List<ProductHit> list = new ArrayList<ProductHit>();
		Connection connection = DBConnectionProvider.getConnection(Boolean.TRUE, Boolean.FALSE, Connection.TRANSACTION_READ_UNCOMMITTED);
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try
		{
			statement = connection.prepareStatement(getProductHitsQuery);

			resultSet = statement.executeQuery();

			while (resultSet.next())
			{
				list.add(new ProductHit(resultSet.getString("FLD_URL"), resultSet.getInt("FLD_HIT")));
			}

			connection.commit();
		}
		catch (Throwable e)
		{
			getLogger().error("Can not get product hits for " + siteName, e);
		}
		finally
		{
			DBConnectionProvider.closeResources(connection, statement, resultSet);
		}

		return list;
	}

	public void updateRankData(String url, int hitCount) throws Throwable
	{
		updateMap.put(url, hitCount);

		if (updateMap.size() == UPDATE_FLUSH_LIMIT)
		{
			flushUpdates();
		}
	}

	private void flushUpdates() throws Throwable
	{

		File rankFile = new File(productsFile.getParent(), siteName + "_rank_tmp.txt");
		FileWriter writer = new FileWriter(rankFile);
		LineIterator iterator = FileUtils.lineIterator(productsFile, StringUtil.DEFAULT_ENCODING);
		Product product;
		Integer hit;
		while(iterator.hasNext()) {
			product = JSONUtil.parseJSON(iterator.nextLine(), Product.class);
			hit = updateMap.get(product.getUrl());
			if (hit != null)
			{
				product.setRank(hit);
			}
			writer.write(JSONUtil.getJSON(product) + "\n");
		}
		writer.flush();
		writer.close();
		LineIterator.closeQuietly(iterator);
		
		
		FileUtils.forceDelete(productsFile);
		rankFile.renameTo(productsFile);

		updateMap.clear();

	}
	
	public String getSiteName()
	{
		return siteName;
	}

	public void flush() throws Throwable
	{
		flushUpdates();
	}

	public Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

}
