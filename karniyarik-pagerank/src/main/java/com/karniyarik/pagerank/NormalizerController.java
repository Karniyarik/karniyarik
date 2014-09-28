package com.karniyarik.pagerank;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.pagerank.dao.NormalizerDAO;
import com.karniyarik.pagerank.dao.ProductHit;
import com.karniyarik.pagerank.normalizer.INormalizer;

public class NormalizerController
{
	private List<INormalizer>	mNormalizerList	= null;
	private final NormalizerDAO	normalizerDAO;

	public NormalizerController(int siteRowCount, NormalizerDAO normalizerDAO)
	{
		this.normalizerDAO = normalizerDAO;
		mNormalizerList = new ArrayList<INormalizer>();
	}

	public final void normalize()
	{
		int aPH = 0;
		String productUrl = "";
		int aZ = 0;

		List<ProductHit> productHitList = normalizerDAO.getProductHits();

		try
		{
			for (ProductHit productHit : productHitList)
			{
				aPH = productHit.getHitCount();
				productUrl = productHit.getUrl();

				aZ = aPH;

				for (INormalizer aNormalizer : mNormalizerList)
				{
					aZ = aNormalizer.calculateNewRank(aZ);
				}

				normalizerDAO.updateRankData(productUrl, aZ);
			}
		}
		catch (Throwable e)
		{
			getLogger().error("Normalization failed " + normalizerDAO.getSiteName(), e);
		}
		finally
		{
			try
			{
				normalizerDAO.flush();
			}
			catch (Throwable e)
			{
				getLogger().error("Can not flush normalization data for " + normalizerDAO.getSiteName(), e);
			}
		}
	}

	public void addNormalizer(INormalizer aNormalizer)
	{
		mNormalizerList.add(aNormalizer);
		aNormalizer.initialize();
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}
}