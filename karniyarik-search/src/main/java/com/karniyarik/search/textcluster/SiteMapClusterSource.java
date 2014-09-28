package com.karniyarik.search.textcluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Document;
import org.carrot2.core.IController;
import org.carrot2.core.IDocumentSource;
import org.carrot2.core.ProcessingComponentBase;
import org.carrot2.core.ProcessingException;
import org.carrot2.core.ProcessingResult;
import org.carrot2.core.SimpleController;
import org.carrot2.core.attribute.AttributeNames;
import org.carrot2.core.attribute.Internal;
import org.carrot2.core.attribute.Processing;
import org.carrot2.text.linguistic.LanguageCode;
import org.carrot2.util.attribute.Attribute;
import org.carrot2.util.attribute.AttributeUtils;
import org.carrot2.util.attribute.Bindable;
import org.carrot2.util.attribute.Input;
import org.carrot2.util.attribute.Output;
import org.carrot2.util.attribute.constraint.IntRange;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.search.EngineRepository;
import com.karniyarik.search.searcher.ISearcher;
import com.karniyarik.search.searcher.QueryResult;
import com.karniyarik.search.searcher.query.Query;

@Bindable
public class SiteMapClusterSource extends ProcessingComponentBase implements
		IDocumentSource
{
	@Processing
	@Output
	@Attribute(key = AttributeNames.DOCUMENTS)
	@Internal
	public List<Document>	documents;

	@Processing
	@Input
	@Attribute
	public String			searchQuery;

	@Processing
	@Input
	@Attribute
	public String			category	= CategorizerConfig.PRODUCT;

	@Processing
	@Input
	@Attribute(key = AttributeNames.RESULTS)
	@IntRange(min = 100, max = 5000)
	public int				resultCount	= 150;

	public SiteMapClusterSource()
	{

	}

	@Override
	public void process() throws ProcessingException
	{
		documents = new ArrayList<Document>();

		Query query = new Query();
		query.setQueryString(searchQuery);
		query.setPageSize(resultCount);
		query.setPageNumber(1);
		//query.setConvertSpacesToOr(false);

		ISearcher searcher = EngineRepository.getInstance()
				.getCategorySearcher(category);
		try
		{
			QueryResult result = searcher.search(query, false);
			for (Product product : result.getResults())
			{
				Document document = new Document();
				document.setField(Document.TITLE, product.getName());
				documents.add(document);
			}
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		IController controller = new SimpleController();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put(AttributeUtils.getKey(SiteMapClusterSource.class,
				"searchQuery"), "kitap");
		params.put(AttributeUtils
				.getKey(SiteMapClusterSource.class, "category"), CategorizerConfig.PRODUCT);
		params.put(AttributeUtils.getKey(SiteMapClusterSource.class,
				"resultCount"), 1000);
		params.put(AttributeNames.ACTIVE_LANGUAGE, LanguageCode.TURKISH);
		params.put("LingoClusteringAlgorithm.desiredClusterCountBase", 40);
		params.put(AttributeNames.ACTIVE_LANGUAGE, LanguageCode.TURKISH);

		cluster(controller, params);
	}

	private static void cluster(IController controller,
			Map<String, Object> params)
	{
		ProcessingResult result = controller.process(params,
				SiteMapClusterSource.class, LingoClusteringAlgorithm.class);

		Collection<Cluster> clusters = result.getClusters();

		for (Cluster cluster : clusters)
		{
			boolean isOtherTopics = false;

			if (cluster.getAttribute(Cluster.OTHER_TOPICS) != null)
			{
				isOtherTopics = (Boolean) cluster
						.getAttribute(Cluster.OTHER_TOPICS);
			}

			if (cluster.getAllDocuments().size() >= 5
					&& cluster.getAllDocuments().size() < 150
					&& isOtherTopics == false)
			{
				System.out.println(cluster.getLabel() + " ("
						+ cluster.getSubclusters().size() + ") - "
						+ cluster.getAllDocuments().size());
			}

		}

		System.out.println("--------------------------------");
	}
}
