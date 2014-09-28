package com.karniyarik.recognizer.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.aliasi.chunk.CharLmHmmChunker;
import com.aliasi.dict.DictionaryEntry;
import com.aliasi.dict.MapDictionary;
import com.aliasi.dict.TrieDictionary;
import com.aliasi.hmm.HmmCharLmEstimator;
import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.ScoredObject;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StringUtil;
import com.karniyarik.datafeed.DatafeedProductCollector;
import com.karniyarik.datafeed.DatafeedProductCollectorFactory;
import com.karniyarik.parser.pojo.Product;

import edu.emory.mathcs.backport.java.util.Collections;
import edu.emory.mathcs.backport.java.util.LinkedList;

public class ModelTrainer
{
	public ModelTrainer()
	{
	}

	public void train(List<SiteConfig> siteConfigList)
	{
		List<Product> productList = new ArrayList<Product>();

		Set<String> models = readModels();
			
		for (SiteConfig siteConfig : siteConfigList)
		{
			//System.out.println("fetching " + siteConfig2.getSiteName());
			DatafeedProductCollector collector = new DatafeedProductCollectorFactory().create(siteConfig, new JobExecutionStat());
			List<Product> products = collector.collectProducts();
			productList.addAll(products);
		}
		
		for (Product product : productList)
		{
			if (StringUtils.isNotBlank(product.getModel()))
			{
				models.add(product.getModel().trim());
			}
		}

		MapDictionary<String> dictionary = new MapDictionary<String>();
		TrieDictionary<String> trieDictionary = new TrieDictionary<String>();

		int MAX_N_GRAM = 20;
		int NUM_CHARS = 256;
		double LM_INTERPOLATION = MAX_N_GRAM / 2; // default behavior

		HmmCharLmEstimator estimator = new HmmCharLmEstimator(MAX_N_GRAM, NUM_CHARS, LM_INTERPOLATION, false);
		CharLmHmmChunker hmmChunker = new CharLmHmmChunker(IndoEuropeanTokenizerFactory.INSTANCE, estimator);

		//System.out.println("Constructing dictionary");

		for (String model: models)
		{
			if (StringUtils.isNotBlank(model))
			{
				dictionary.addEntry(new DictionaryEntry<String>(model, "MODEL", 100.0));
				trieDictionary.addEntry(new DictionaryEntry<String>(model, "MODEL", 1.0));
				hmmChunker.trainDictionary(model, "MODEL");
			}
		}

		//System.out.println("Writing");
		try
		{
			writeModels(models);
			
			String rootDir = getRootDir();
			
			FileOutputStream fos = new FileOutputStream(rootDir + "/hmm.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			hmmChunker.compileTo(oos);
			oos.close();

			fos = new FileOutputStream(rootDir + "/dict.obj");
			oos = new ObjectOutputStream(fos);
			dictionary.compileTo(oos);
			oos.close();
			
			fos = new FileOutputStream(rootDir + "/triedict.obj");
			oos = new ObjectOutputStream(fos);
			trieDictionary.compileTo(oos);
			oos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private Set<String> readModels() 
	{
		Set<String> modelList = new HashSet<String>();
		String fileName = getModelFile();
		
		List<String> lines = new ArrayList<String>();
		
		try
		{
			FileInputStream is = new FileInputStream(fileName);
			lines = IOUtils.readLines(is);
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		for(String line: lines)
		{
			if(StringUtils.isNotBlank(line))
			{
				modelList.add(line.trim());
			}
		}
		
		return modelList;
	}
	
	private void writeModels(Set<String> modelList) 
	{
		String fileName = getModelFile();
		List<String> lines = new ArrayList<String>();
		
		try
		{
			FileOutputStream os = new FileOutputStream(fileName);
			IOUtils.writeLines(modelList, "\n", os, StringUtil.DEFAULT_ENCODING);
			os.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		for(String line: lines)
		{
			if(StringUtils.isNotBlank(line))
			{
				modelList.add(line.trim());
			}
		}
	}

	private String getModelFile()
	{
		String rootDir = getRootDir();
		
		String fileName = rootDir + "/model-kb.txt";
		
		try
		{
			File file = new File(fileName);
			if(!file.exists())
			{
				file.createNewFile();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return fileName;
	}

	public static String getRootDir()
	{
		String baseDir = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig().getBaseDir();
		
		String rootDir = baseDir + "/model-kb";
		
		File file = new File(rootDir);
		if(!file.exists())
		{
			file.mkdirs();
		}
		
		return rootDir;
	}

	public void dumpColocationFreq()
	{
		List<Product> products = JSONUtil.parseFile(new File("C:/work/karniyarik/files/products/hepsiburada.txt"), Product.class);
		StringBuffer buff = new StringBuffer();
		for (Product product : products)
		{
			buff.append(product.getName());
			buff.append(". ");
		}

		TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;

		int NGRAM = 3; 
		TokenizedLM backgroundModel = new TokenizedLM(tokenizerFactory, NGRAM); 
		backgroundModel.handle(buff.toString().toCharArray(), 0,buff.length());
		backgroundModel.sequenceCounter().prune(3);

		SortedSet<ScoredObject<String[]>> coll = backgroundModel.collocationSet(2, 5, 1000);
		System.out.println("\nCollocations in Order of Significance:");
		report(coll);
	}
	
	public void dumpWordFreq()
	{
		Map<String, TermFreq> terms = new HashMap<String, TermFreq>();
		
		List<Product> products = JSONUtil.parseFile(new File("C:/work/karniyarik/files/products/hepsiburada.txt"), Product.class);
		StringBuffer buff = new StringBuffer();
		for (Product product : products)
		{
			buff.append(product.getName());
			buff.append(". ");
		}

		TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;

		Tokenizer tokenizer = tokenizerFactory.tokenizer(buff.toString().toCharArray(), 0, buff.length());
		
		Iterator<String> iterator = tokenizer.iterator();
		
		while(iterator.hasNext())
		{
			String next = iterator.next().trim();
			TermFreq termFreq = terms.get(next);
			if(termFreq == null)
			{
				termFreq = new TermFreq(next,0);
				terms.put(termFreq.getName(), termFreq);
			}
			
			termFreq.increaseCountByOne();
		}
		
		List<TermFreq> list = new LinkedList();
		list.addAll(terms.values());
		Collections.sort(list);
		
		StringBuffer result = new StringBuffer();
		for(TermFreq term: list)
		{
			result.append(term.getName());
			result.append(",");
			result.append(term.getCount());
			result.append("\n");
		}
		
		try
		{
			IOUtils.write(result, new FileOutputStream(new File("D:/terms.csv")), "UTF-8");
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
	}

	private static void report(SortedSet<ScoredObject<String[]>> nGrams)
	{
		for (ScoredObject<String[]> nGram : nGrams)
		{
			double score = nGram.score();
			String[] toks = nGram.getObject();
			report_filter(score, toks);
		}
	}

	private static void report_filter(double score, String[] toks)
	{
		String accum = "";
		for (int j = 0; j < toks.length; ++j)
		{
			if (nonCapWord(toks[j]))
				return;
			accum += " " + toks[j];
		}
		System.out.println(accum);
	}

	private static boolean nonCapWord(String tok)
	{
		if (!Character.isUpperCase(tok.charAt(0)))
			return true;
		for (int i = 1; i < tok.length(); ++i)
			if (!Character.isLowerCase(tok.charAt(i)))
				return true;
		return false;
	}

	public static void main(String[] args)
	{
		List<SiteConfig> siteConfigList = new ArrayList<SiteConfig>();
		
		SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig("teknosa");
		siteConfigList.add(siteConfig);
		siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig("darty");
		siteConfigList.add(siteConfig);
		
		new ModelTrainer().train(siteConfigList);//dumpWordFreq();
	}
}
