package com.karniyarik.search.textcluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Document;
import org.carrot2.core.IController;
import org.carrot2.core.ProcessingResult;
import org.carrot2.core.SimpleController;
import org.carrot2.core.attribute.AttributeNames;
import org.carrot2.text.linguistic.LanguageCode;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.analyzer.NewTurkishAnalyzer;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;

public class SearchClusterConstuctor
{

	public List<String> getClusters(String query, TopDocs aTopDocs, IndexSearcher indexSearcher)
	{
		return getClusters(query, null, aTopDocs, indexSearcher);
	}
	
	public List<String> getClusters(String query, String originalQuery, TopDocs aTopDocs, IndexSearcher indexSearcher)
	{
		List<String> clusterList = new ArrayList<String>();

		SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();

		int minSearchResultSize = searchConfig.getMinSearchResultSize();

		if (aTopDocs.totalHits > minSearchResultSize)
		{
			int maxConstructedClusterCount = searchConfig.getMaxConstructedClusterCount();
			int maxSampleSize = searchConfig.getMaxSampleSize();
			int minClusterContains = searchConfig.getMinClusterContains();
			int maxClusterContains = searchConfig.getMaxClusterContains();

			List<Document> documents = new ArrayList<Document>();

			org.apache.lucene.document.Document luceneDoc = null;
			Document document = null;

			for (int index = 0; index < aTopDocs.totalHits && index < maxSampleSize; index++)
			{
				try
				{
					document = new Document();
					luceneDoc = indexSearcher.doc(aTopDocs.scoreDocs[index].doc);
					document.setField(Document.TITLE, luceneDoc.get(SearchConstants.NAME));
					documents.add(document);
				}
				catch (Throwable e)
				{
					// do nothing
					e.printStackTrace();
				}
			}

			IController controller = new SimpleController();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("LingoClusteringAlgorithm.desiredClusterCountBase", maxConstructedClusterCount);
			params.put(AttributeNames.DOCUMENTS, documents);
			params.put(AttributeNames.ACTIVE_LANGUAGE, LanguageCode.TURKISH);

			ProcessingResult result = controller.process(params, LingoClusteringAlgorithm.class);

			Collection<Cluster> clusters = result.getClusters();

			String clusterLabel = null;

			boolean isOtherTopics = false;

			String originalQueryString = originalQuery == null ? query : originalQuery;
//			System.out.println("Borrow search SearchClusterConstructor getClusters");
			NewTurkishAnalyzer analyzer = TurkishAnalyzerPool.getInstance().borrowSearchAnalyzer();
			for (Cluster cluster : clusters)
			{
				isOtherTopics = false;

				if (cluster.getAttribute(Cluster.OTHER_TOPICS) != null)
				{
					isOtherTopics = (Boolean) cluster.getAttribute(Cluster.OTHER_TOPICS);
				}

				if (cluster.getAllDocuments().size() >= minClusterContains && cluster.getAllDocuments().size() < maxClusterContains && isOtherTopics == false)
				{
					// name =
					// BrandServiceImpl.getInstance().removeBrand(cluster.getLabel());
					StringBuffer sb = new StringBuffer(originalQueryString);
					clusterLabel = cluster.getLabel();

					Map<String, String> queryKokleri = getKokler(query, analyzer);
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

//			System.out.println("Return search SearchClusterConstructor getClusters");
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
