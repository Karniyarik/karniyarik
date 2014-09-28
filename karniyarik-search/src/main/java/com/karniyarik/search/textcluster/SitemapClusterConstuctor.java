package com.karniyarik.search.textcluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.IController;
import org.carrot2.core.ProcessingResult;
import org.carrot2.core.SimpleController;
import org.carrot2.core.attribute.AttributeNames;
import org.carrot2.text.linguistic.LanguageCode;
import org.carrot2.util.attribute.AttributeUtils;

import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.StringUtil;

public class SitemapClusterConstuctor
{
	private static int MIN_RESULT_COUNT = 15;
	private static int MAX_RESULT_COUNT = 150;
	
	public SitemapClusterConstuctor()
	{
	}
	
	public List<String> getClusters(String query, String category)
	{
		return getClusters(query, category, 30, 400);
	}
	
	public List<String> getClusters(String query, String category, int maxClusterCount, int maxSearchResultCount)
	{
		List<String> clusterList = new ArrayList<String>();
		
        IController controller = new SimpleController();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(AttributeUtils.getKey(SiteMapClusterSource.class, "searchQuery"),query);
        params.put(AttributeUtils.getKey(SiteMapClusterSource.class, "category"), category);
        params.put(AttributeUtils.getKey(SiteMapClusterSource.class, "resultCount"), maxSearchResultCount);
        params.put("LingoClusteringAlgorithm.desiredClusterCountBase", maxClusterCount);
        params.put(AttributeNames.ACTIVE_LANGUAGE, LanguageCode.TURKISH);
        
		ProcessingResult result = controller.process(params,SiteMapClusterSource.class, LingoClusteringAlgorithm.class);
	        
        Collection<Cluster> clusters = result.getClusters();
        
        String name = null;
        
        boolean isOtherTopics = false;
        
        for(Cluster cluster: clusters)
        {
        	if(clusterList.size() > maxClusterCount)
        	{
        		break;
        	}
        	isOtherTopics = false;
        	
        	if(cluster.getAttribute(Cluster.OTHER_TOPICS) != null)
        	{
        		isOtherTopics = (Boolean) cluster.getAttribute(Cluster.OTHER_TOPICS);
        	}
        	
        	if(cluster.getAllDocuments().size() >= MIN_RESULT_COUNT && cluster.getAllDocuments().size() < MAX_RESULT_COUNT && isOtherTopics == false)
        	{
            	name = cluster.getLabel();
            	if(!normalize(name).contains(normalize(query)))
            	{
            		name = query + " " + name;
            	}
    			clusterList.add(name);
        	}
        }
        
        if(clusterList.size() != 0)
        {
        	clusterList.add(query);
        }
        
        return clusterList;
	}
	
	public String normalize(String aStr)
	{
		return StringUtil.convertTurkishCharacter(aStr).toLowerCase(Locale.ENGLISH);
	}

	
	public static void main(String[] args)
	{
		SitemapClusterConstuctor constructor = new SitemapClusterConstuctor();
		List<String> result =constructor.getClusters("nokia", CategorizerConfig.PRODUCT);
		for(String name: result)
		{
			System.out.println(name);
		}
	}
}
