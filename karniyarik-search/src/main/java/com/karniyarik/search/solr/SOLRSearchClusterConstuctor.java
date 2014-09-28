package com.karniyarik.search.solr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.ir.analyzer.NewTurkishAnalyzer;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;
import com.karniyarik.search.searcher.query.Query;

public class SOLRSearchClusterConstuctor
{
	public List<String> getClusters(String query, String category, long totalResultSize, SOLRSearchProxy solr)
	{
		return null;
		//return getClusters(query, null, category, totalResultSize, solr);
	}
	
	public List<String> getClusters(Query query, long totalResultSize, SOLRSearchProxy solr)
	{
		List<String> clusterList = new ArrayList<String>();

		SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();

		int minSearchResultSize = searchConfig.getMinSearchResultSize();

		if (totalResultSize > minSearchResultSize)
		{
			int maxConstructedClusterCount = searchConfig.getMaxConstructedClusterCount();
			int maxSampleSize = searchConfig.getMaxSampleSize();
			int minClusterContains = searchConfig.getMinClusterContains();
			int maxClusterContains = searchConfig.getMaxClusterContains();

			List<SOLRCluster> clusters = solr.getClusters(query, maxSampleSize, maxConstructedClusterCount);
			
			String clusterLabel = null;

			boolean isOtherTopics = false;

			String originalQueryString = query.getOriginalQuery() == null ? query.getQueryString(): query.getOriginalQuery();

			NewTurkishAnalyzer analyzer = TurkishAnalyzerPool.getInstance().borrowSearchAnalyzer();
			for (SOLRCluster cluster : clusters)
			{
				isOtherTopics = false;

				if (cluster.getName().equals("Other Topics"))
				{
					isOtherTopics = true;
				}

				if (cluster.getCount() >= minClusterContains && cluster.getCount() < maxClusterContains && isOtherTopics == false)
				{
					// name = BrandServiceImpl.getInstance().removeBrand(cluster.getLabel());
					StringBuffer sb = new StringBuffer(originalQueryString);
					clusterLabel = cluster.getName();

					Map<String, String> queryKokleri = getKokler(query.getQueryString(), analyzer);
					Map<String, String> clusterKokleri = getKokler(clusterLabel, analyzer);

					if (StringUtils.isNotBlank(clusterLabel))
					{
						String[] clTerms = clusterLabel.trim().split(" ");

						for (String clTerm : clTerms)
						{
							if (!contains(queryKokleri, clusterKokleri, clTerm))
							{
								sb.append(" " + clTerm);
							}
						}

						if (!sb.toString().equalsIgnoreCase(originalQueryString))
						{
							String string = sb.toString();
							clusterList.add(string);
						}
					}
				}
			}

			TurkishAnalyzerPool.getInstance().returnSearchAnalyzer(analyzer);
		}
		return clusterList;
	}

	private boolean contains(Map<String, String> queryKokleri, Map<String, String> clusterKokleri, String term)
	{
		String clusterKok = clusterKokleri.get(term);

		for (String queryKok : queryKokleri.values())
		{
			if (queryKok.equalsIgnoreCase(clusterKok))
			{
				return true;
			}
		}

		return false;
	}

	private Map<String, String> getKokler(String query, NewTurkishAnalyzer analyzer)
	{

		Map<String, String> result = new HashMap<String, String>();

		String[] words = query.split(" ");

		for (String word : words)
		{
			result.put(word, analyzer.getEnUzunKok(word));
		}

		return result;
	}

}
