package com.karniyarik.categorizer.lucene;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;

import com.karniyarik.categorizer.io.TrainingSetIO;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.trainset.ProductType;
import com.karniyarik.categorizer.xml.trainset.RootType;
import com.karniyarik.categorizer.xml.trainset.SetType;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.analyzer.NewTurkishAnalyzer;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;

public class LuceneTrainer
{
	private Map<String, SetType>		map			= new HashMap<String, SetType>();
	private Map<String, StringBuffer>	catDescMap	= new HashMap<String, StringBuffer>();

	public LuceneTrainer()
	{
		try
		{
			RootType rootType = new TrainingSetIO().read("karniyarik");

			for (SetType setType : rootType.getSet())
			{
				map.put(setType.getCatid(), setType);
			}

			System.out.println("Training");
			List<String> categoryList = new ArrayList<String>(map.keySet());

			for (String category : categoryList)
			{
				SetType setType = map.get(category);

				for (ProductType productType : setType.getProduct())
				{
					String instance = getProductDescription(productType.getName(), productType.getBreadcrumb(), productType.getBrand());
					StringBuffer buff = catDescMap.get(category);
					if(buff == null)
					{
						buff = new StringBuffer();
						catDescMap.put(category, buff);
					}
					buff.append(" ");
					buff.append(instance);
				}
			}

			System.out.println("Indexing");
			SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();
			try
			{
				IndexWriter writer = new IndexWriter(getIndexDirectory(), 
						new WhitespaceAnalyzer(), 
						new MaxFieldLength(searchConfig.getLuceneMaxFieldLength()));
				writer.setMaxMergeDocs(searchConfig.getLuceneMaxMergeDocs());
				writer.setMergeFactor(searchConfig.getLuceneMergeFactor());
				writer.setMaxBufferedDocs(searchConfig.getLuceneMaxBufferedDocs());
				writer.setUseCompoundFile(true);
				
				for(String cat: catDescMap.keySet())
				{
					Document doc = prepareDocument(catDescMap.get(cat).toString(), cat);
					writer.addDocument(doc);
				}
				
				writer.optimize();
				writer.close();
			}
			catch (Throwable e)
			{
				throw new RuntimeException("Can not create indexer for category analyzer", e);
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	public static String getProductDescription(String name, String breadcrumb, String brand)
	{
		NewTurkishAnalyzer analyzer = TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer();
		
		StringBuffer productDesc = new StringBuffer();
		productDesc.append(getBreadCrumb(analyzer, breadcrumb, brand));
		productDesc.append(" ");
		productDesc.append(getProductName(analyzer, name));
		productDesc.append(" ");
		productDesc.append(getBrand(analyzer, brand));

		TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(analyzer);
		return productDesc.toString();
	}

	public static String getBrand(Analyzer analyzer, String brand)
	{
		return brand;
	}

	public static String getProductName(Analyzer analyzer, String name)
	{
		return analyze(analyzer, name);
	}

	public static String getBreadCrumb(Analyzer analyzer, String breadcrumb, String brand)
	{
		breadcrumb = breadcrumb.replaceAll("##", " ");
		breadcrumb = breadcrumb.replaceAll("\\s+", " ");
		// if(breadcrumb.toLowerCase().contains(brand.toLowerCase()))
		// {
		// breadcrumb = breadcrumb.replace(brand.toLowerCase(), "");
		// }
		breadcrumb = analyze(analyzer, breadcrumb.trim());

		return breadcrumb;
	}

	public static String analyze(Analyzer analyzer, String str)
	{
		try
		{
			StringBuffer result = new StringBuffer();
			TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(str));
			Token token = null;
			while ((token = tokenStream.next()) != null)
			{
				String term = token.term();
				if (term.length() > 2)
				{
					result.append(term);
					result.append(" ");
				}
			}

			return result.toString();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private File getIndexDirectory()
	{
		File file = StreamUtil.getFile(Constants.luceneDir);
		
		if(!file.exists())
		{
			file.mkdir();
		}
		
		return file;
	}
	
	public Document prepareDocument(String description, String category)
	{
		Document aDoc = new Document();
		aDoc.add(new Field(SearchConstants.NAME, description,Field.Store.YES, Field.Index.ANALYZED));
		aDoc.add(new Field(SearchConstants.CATEGORY, category, Field.Store.YES,Field.Index.NOT_ANALYZED));
		return aDoc;
	}

	public static void main(String[] args)
	{
		new LuceneTrainer();
	}
}
