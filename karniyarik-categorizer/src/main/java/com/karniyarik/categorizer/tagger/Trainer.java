package com.karniyarik.categorizer.tagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;

import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.ir.analyzer.NewTurkishAnalyzer;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;

public class Trainer
{
	public Trainer()
	{
		try
		{
			Tagger tagger = new Tagger(true, true);
			
			System.out.println("Training");
			List<String> categoryList = new ArrayList<String>(tagger.getPredefinedTags());
			
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
			
			CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
			File trainingFile = new File(categorizerConfig.getModelPath() + "/trainingset.csv");
			
			FileInputStream is = new FileInputStream(trainingFile);
			LineIterator lineIterator = IOUtils.lineIterator(is, StringUtil.DEFAULT_ENCODING);
			while(lineIterator.hasNext())
			{
				String line = lineIterator.nextLine();
				String[] split = line.split("\t");
				
				String name = split[0];
				String brand = split[1];
				String breadcrumb = split[2];
				String productDesc = getProductDescription(name, breadcrumb, brand);
				for(int index=3; index < split.length; index++)
				{
					classifier.train(split[index], productDesc);
				}
			}
			
			System.out.println("Writing to object");
			File file = new File(Constants.modelDir);
			if(!file.exists())
			{
				file.mkdirs();
			}
			
			FileOutputStream fos = new FileOutputStream(categorizerConfig.getModelPath() + "/" + Constants.getModelName());
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
		breadcrumb = StringUtil.removePunctiationsAndReduceWhitespace(breadcrumb);

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
