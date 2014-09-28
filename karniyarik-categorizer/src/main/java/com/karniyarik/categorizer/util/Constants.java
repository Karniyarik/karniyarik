package com.karniyarik.categorizer.util;

import java.util.HashMap;
import java.util.Map;

public class Constants
{
	public static Map<String, String>	delimiterMap	= new HashMap<String, String>();
	public static Map<String, String>	modelNameMap	= new HashMap<String, String>();
	public static int					NGRAM_SIZE		= 3;

	public static double				luceneFactor	= 0.3;
	public static double				lingpipeFactor	= 0.5;
	public static double				priceFactor		= 0.2;

	public static String				trainsetDir		= "dev-resources/trainset";
	public static String				modelDir		= "dev-resources/model1";
	public static String				analysisDir		= "dev-resources/analysis";
	public static String				categoryDir		= "dev-resources/category";
	public static String				schemaDir		= "dev-resources/schema";
	public static String				mappingDir		= "dev-resources/mapping";
	public static String				luceneDir		= "dev-resources/lucene";

	public static String				price			= "dev-resources/price/price.model";
	public static String				activeSite		= "hepsiburada";
	public static String				activeModel		= "lingpipe.process";

	static
	{
		delimiterMap.put("hepsiburada", ">");
		delimiterMap.put("alisveris", "»");
		delimiterMap.put("bilgestore", "\\\\");
		delimiterMap.put("hipernex", ">");

		modelNameMap.put("lingpipe.process", "lingpipe.process.model");
		modelNameMap.put("lingpipe.boundary", "lingpipe.boundary.model");
		modelNameMap.put("lingpipe.tokenizer", "lingpipe.tokenized.model");
	}

	public static String getDelimiter()
	{
		return delimiterMap.get(activeSite);
	}

	public static String getModelName()
	{
		return modelNameMap.get(activeModel);
	}

}
