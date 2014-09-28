package com.karniyarik.pagerank;

import java.io.File;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.pagerank.dao.NormalizerDAO;
import com.karniyarik.pagerank.dao.PageRankDAO;
import com.karniyarik.pagerank.normalizer.HistogramNormalizer;

public class PageRankCalculator
{
	private final String	siteName;
	private final String	visitedLinksTableName;

	public PageRankCalculator(String siteName, String visitedLinksTableName)
	{
		this.siteName = siteName;
		this.visitedLinksTableName = visitedLinksTableName;
	}

	public void calculate()
	{
		PageRankDAO pageRankDAO = new PageRankDAO(siteName, visitedLinksTableName);
		NormalizerController aNormalizerController = null;
		int siteRowCount = pageRankDAO.findRowCountForSite();

		if (siteRowCount > 0)
		{

			File productsFile = new File(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getProductsDirectory() + File.separator + siteName + ".txt");
			NormalizerDAO normalizerDAO = new NormalizerDAO(siteName, visitedLinksTableName, productsFile);
			aNormalizerController = new NormalizerController(siteRowCount, normalizerDAO);
			aNormalizerController.addNormalizer(new HistogramNormalizer(siteRowCount, pageRankDAO.getDistinctRanks()));
			// aNormalizerController.addNormalizer(anAlexaNormalizer);
			aNormalizerController.normalize();
		}
	}
}
