package com.karniyarik.categorizer.lingpipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.karniyarik.categorizer.io.TrainingSetIO;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.categorizer.xml.trainset.ProductType;
import com.karniyarik.categorizer.xml.trainset.RootType;
import com.karniyarik.categorizer.xml.trainset.SetType;
import com.karniyarik.ir.analyzer.NewTurkishAnalyzer;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;

public class Trainer
{
	private Map<String, SetType>	map			= new HashMap<String, SetType>();

	public Trainer()
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
			DynamicLMClassifier classifier = null;
			if(Constants.activeModel.equals("lingpipe.process"))
			{
				classifier = DynamicLMClassifier.createNGramProcess(categoryList.toArray(new String[categoryList.size()]), Constants.NGRAM_SIZE);	
			}
			else if(Constants.activeModel.equals("lingpipe.tokenized"))
			{
				classifier = DynamicLMClassifier.createTokenized(categoryList.toArray(new String[categoryList.size()]), IndoEuropeanTokenizerFactory.INSTANCE, Constants.NGRAM_SIZE);	
			}
			else if(Constants.activeModel.equals("lingpipe.boundary"))
			{
				classifier = DynamicLMClassifier.createNGramBoundary(categoryList.toArray(new String[categoryList.size()]), Constants.NGRAM_SIZE);
			}

			for (String category : categoryList)
			{
				SetType setType = map.get(category);

				for(ProductType productType: setType.getProduct())
				{
					String instance = getProductDescription(productType.getName(), productType.getBreadcrumb(), productType.getBrand());
					classifier.train(category, instance);
				}
			}
			
			

			System.out.println("Writing to object");
			File file = new File(Constants.modelDir);
			if(!file.exists())
			{
				file.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(Constants.modelDir + "/" + Constants.getModelName());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			classifier.compileTo(oos);
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
		try {
			productDesc.append(getBreadCrumb(analyzer, breadcrumb, brand));
			productDesc.append(" ");
			productDesc.append(getProductName(analyzer, name));
			productDesc.append(" ");
			productDesc.append(getBrand(analyzer, brand));
		} catch (Exception e) {
		}

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

//		if(breadcrumb.toLowerCase().contains(brand.toLowerCase()))
//		{
//			breadcrumb = breadcrumb.replace(brand.toLowerCase(), "");
//		}

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
				if(term.length()>2)
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

	public static void main(String[] args)
	{
		new Trainer();
	}
}
