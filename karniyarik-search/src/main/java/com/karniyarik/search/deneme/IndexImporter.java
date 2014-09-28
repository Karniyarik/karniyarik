package com.karniyarik.search.deneme;

import java.io.File;

import org.apache.solr.common.SolrInputDocument;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.vo.Product;
import com.karniyarik.indexer.dao.IndexerDAO;
import com.karniyarik.indexer.dao.IndexerDAOFactory;
import com.karniyarik.search.indexer.util.ProductDocumentUtil;
import com.karniyarik.search.solr.SOLRIndexerPool;
import com.karniyarik.search.solr.SOLRIndexerProxy;


public class IndexImporter {
	
	public IndexImporter() {
		
		File productsDir = new File("C:/work/karniyarik/files/products1/p");
		for(File file: productsDir.listFiles())
		{
			String filename = file.getName();
			String sitename = filename.substring(0,filename.indexOf("."));
			SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
			CategoryConfig categoryConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig().getCategoryConfig(siteConfig.getCategory());
			IndexerDAO dao = new IndexerDAOFactory().create(siteConfig);
			
			if(dao.getProductCount() > 0)
			{
				System.out.println(sitename + " - " + dao.getProductCount());
				SOLRIndexerProxy indexer = SOLRIndexerPool.getInstance().getIndexer(siteConfig.getCategory());
				indexer.delete(sitename);
				while(dao.hasNext())
				{
					Product product = dao.getNextProduct();
					SolrInputDocument solrDoc = ProductDocumentUtil.prepareDocument(product, categoryConfig);
					indexer.add(solrDoc);	
				}
				indexer.commit();
				indexer.close();
				SOLRIndexerPool.getInstance().returnIndexer(indexer);
			}
		}
		
	}
	
	public static void main(String[] args) {
		new IndexImporter();
	}
}
