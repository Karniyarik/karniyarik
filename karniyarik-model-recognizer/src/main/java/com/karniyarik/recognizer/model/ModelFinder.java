package com.karniyarik.recognizer.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Token;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.HmmChunker;
import com.aliasi.dict.ApproxDictionaryChunker;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.dict.MapDictionary;
import com.aliasi.dict.TrieDictionary;
import com.aliasi.spell.FixedWeightEditDistance;
import com.aliasi.spell.WeightedEditDistance;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.util.AbstractExternalizable;
import com.karniyarik.brands.nb.BrandServiceImpl;
import com.karniyarik.brands.nb.ResolveResult;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.parser.pojo.Product;

import edu.emory.mathcs.backport.java.util.Collections;

public class ModelFinder
{
	private MapDictionary<String>	dictionary			= null;
	private TrieDictionary<String>	trieDictionary		= null;
	private HmmChunker				hmmChunker			= null;
	private ApproxDictionaryChunker	approxChunker		= null;
	private ExactDictionaryChunker	dictionaryChunker	= null;
	private Set<String>				noises				= new HashSet<String>();
	private Pattern 				nonDigitPattern		= Pattern.compile("\\D+");
	private Pattern 				digitPattern		= Pattern.compile("\\d+");
	private Pattern 				alphaNumericPattern		= Pattern.compile("[\\p{Alnum}-]+");
	private Pattern 				alphaNumericWithAtLeastOneDashPattern		= Pattern.compile("\\p{Alnum}+-+\\p{Alnum}+");
	private Pattern 				commonModelPattern		= Pattern.compile("[a-zA-Z]{2,4}\\d+");
	
	private String[]				forbiddenCategories = new String[] {"Yazılım Ürünleri","Film","Müzik","Kitap","Oyuncak", "Oyun"};
	private Set<String>				forbiddenCategoriesSet = new HashSet<String>();
	private Pattern[] 				noisePatterns = new Pattern[]{
		Pattern.compile("\\d+GB"),
		Pattern.compile("\\d+MP"),
		Pattern.compile("[\\d\\.]+[\"\']+"),
		Pattern.compile("\\w+\\.+\\w+"),		
		Pattern.compile("\\d{2,4}X\\d{2,4}")
	};
	
	private static ModelFinder instance = null;
	
	private ModelFinder()
	{
		try
		{
			String rootDir = ModelTrainer.getRootDir();
			dictionary = (MapDictionary<String>) AbstractExternalizable.readObject(StreamUtil.getFile(rootDir + "/dict.obj"));
			trieDictionary = (TrieDictionary<String>) AbstractExternalizable.readObject(StreamUtil.getFile(rootDir + "/triedict.obj"));
			hmmChunker = (HmmChunker) AbstractExternalizable.readObject(StreamUtil.getFile(rootDir + "/hmm.obj"));

			dictionaryChunker = new ExactDictionaryChunker(dictionary, IndoEuropeanTokenizerFactory.INSTANCE, false, false);

			WeightedEditDistance editDistance = new FixedWeightEditDistance(0, -1, -1, -1, Double.NaN);
			double maxDistance = 0.7;
			approxChunker = new ApproxDictionaryChunker(trieDictionary, IndoEuropeanTokenizerFactory.INSTANCE, editDistance, maxDistance);

			InputStream stream = StreamUtil.getStream("model/noisewords.txt");
			List<String> lines = (List<String>) IOUtils.readLines(stream);
			stream.close();
			for (String line : lines)
			{
				line = line.trim().toLowerCase();
				String[] terms = line.split("\\s");
				for(String term: terms)
				{
					term  = term.trim();
					if (StringUtils.isNotBlank(term) && term.length() > 1)
					{
						noises.add(term.toLowerCase(Locale.ENGLISH));
					}					
				}
			}
			
			for(String cat: forbiddenCategories)
			{
				forbiddenCategoriesSet.add(cat.toLowerCase(new Locale("tr")));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static ModelFinder getInstance()
	{
		if(instance == null)
		{
			instance = new ModelFinder();
		}
		
		return instance;
	}

	public Model getModel(String name, String category)
	{
		Model result = null;

		name = name.replaceAll("\\(.*\\)", "");
		//name = name.replaceAll("\\d+\\'+", " ");
		name = name.replaceAll("\\+", " ");
		//name = name.replaceAll("\\\"", " ");
		
		name = name.replaceAll("\\d+\\s?[mM][mM]"," ");
		name = name.replaceAll("\\d+\\s?[cC][mM]"," ");
		name = name.replaceAll("\\d+\\s?[mM][lL]"," ");
		name = name.replaceAll("\\d+\\s?[mM][tT]"," ");
		name = name.replaceAll("\\d+\\s?[cC][cC]"," ");
		name = name.replaceAll("\\d+\\s?[gG][rR]"," ");
		name = name.replaceAll("\\d+\\s?[lL][tT]"," ");
		name = name.replaceAll("\\d{2,4}\\s?[xX]\\s?\\d{2,4}", " ");
		name = name.replaceAll("%\\d+"," ");

		name = name.replaceAll("\\s+", " ");
		
		ResolveResult resolve = BrandServiceImpl.getInstance().resolve(name);
		
		if(forbiddenCategoriesSet.contains(category.toLowerCase(new Locale("tr"))))
		{
			return null;
		}
		
		List<List<String>> finalTokenGroups = new ArrayList<List<String>>();
		List<List<String>> prunedTokenGroups = new ArrayList<List<String>>();
		
		if (resolve != null && BrandServiceImpl.getInstance().isBrandRecognized(resolve.getFoundValue()))
		{
			int brandEnd = -1;
			
			List<Token> tokens = resolve.getToken();
			if(tokens.size()>0)
			{
				brandEnd = tokens.get(tokens.size()-1).endOffset();
			}

			String nameFilteredForBrand = name.substring(brandEnd).trim();
			String[] tokensAfterBrand = nameFilteredForBrand.split("\\s");
			
			List<String> finalTokens = new ArrayList<String>();
			List<String> prunedFinalTokens = new ArrayList<String>();

			int added = 0;
			for(int index=0;index<tokensAfterBrand.length; index++)
			{
				String token = tokensAfterBrand[index].trim();
				
				boolean isNoise = false;
				//if(noises.contains(token.toLowerCase(Locale.ENGLISH)) || token.length() < 2)
				if(noises.contains(token.toLowerCase(Locale.ENGLISH)))
				{
					isNoise = true;
				}
				else
				{
					for(Pattern pat: noisePatterns)
					{
						Matcher matcher = pat.matcher(token);
						if(matcher.matches())
						{
							isNoise = true;
							break;
						}
					}
				}
					
				if(isNoise)
				{
					if(finalTokens.size() > 0)
					{
						finalTokenGroups.add(finalTokens);
						finalTokens = new ArrayList<String>();
					}
					if(prunedFinalTokens.size() > 0)
					{
						prunedTokenGroups.add(prunedFinalTokens);
						prunedFinalTokens = new ArrayList<String>(); 
					}

				}
				else 
				{
					if(added < 3)
					{
						prunedFinalTokens.add(token);
						added++;
					}
					finalTokens.add(token);
				}
			}
			
			if(finalTokens.size() > 0)
			{
				finalTokenGroups.add(finalTokens);
				finalTokens = new ArrayList<String>();
			}
			if(prunedFinalTokens.size() > 0)
			{
				prunedTokenGroups.add(prunedFinalTokens);
				prunedFinalTokens = new ArrayList<String>(); 
			}
		}

		List<Model> models = new ArrayList<Model>();

		for(List<String> tokens: finalTokenGroups)
		{
			//exact 
			String exactName = StringUtils.join(tokens, " ").trim();
			String exactNameLowerCased = exactName.toLowerCase(Locale.ENGLISH);
			
			Chunking chunking = dictionaryChunker.chunk(exactNameLowerCased);
			List<Model> modelsFromChunkResult = getModelsFromChunkResult(chunking, exactName, 10);
			models.addAll(modelsFromChunkResult);			
		}
		
		for(List<String> tokens: finalTokenGroups)
		{
			//hmm
			String hmmName = StringUtils.join(tokens, " ").trim();			
			String hmmNameLowerCased = hmmName.toLowerCase(Locale.ENGLISH);
			Iterator<Chunk> nBestChunks = hmmChunker.nBestChunks(hmmNameLowerCased.toCharArray(), 0, hmmName.length(), 3);
			List<Model> modelsFromChunkResult = getModelsFromChunkResult(nBestChunks, hmmName, 1);
			models.addAll(modelsFromChunkResult);
		}
		
		for(List<String> tokens: finalTokenGroups)
		{
			List<Model> findWithCustom = findWithCustom(tokens,10);
			models.addAll(findWithCustom);
		}
		
//		if (models.size() == 0)
//		{
//			chunking = approxChunker.chunk(hmmNameLowerCased);
//			modelsFromChunkResult = getModelsFromChunkResult(chunking, hmmName);
//			models.addAll(modelsFromChunkResult);
//		}	
		
		
		
		Map<String, Model> modelMap = new HashMap<String, Model>();
		for(Model model: models)
		{
			Model modelInMap = modelMap.get(model.getName());
			
			if(model.getName().startsWith("-"))
			{
				model.setName(model.getName().substring(1));
			}
			
			if(model.getName().endsWith("-"))
			{
				model.setName(model.getName().substring(0,model.getName().length()-1));
			}
			
			Matcher matcher = nonDigitPattern.matcher(model.getName());
			if(matcher.matches())
			{
				continue;
			}
			
			if(modelInMap != null)
			{
				modelInMap.setScore(modelInMap.getScore() + model.getScore());
			}
			else
			{
				if(!model.getName().contains(".") && 
						!model.getName().contains(":") && 
						!model.getName().contains("%") && 
						!model.getName().contains(",") &&
						!model.getName().contains("?") &&
						model.getName().length() > 2)
				{
					modelMap.put(model.getName(), model);
				}
			}
		}
		
		models.clear();
		models.addAll(modelMap.values());
		
		for(Model model: models)
		{
			Matcher matcher = nonDigitPattern.matcher(model.getName());
			
			if(!matcher.matches())
			{
				model.setScore(model.getScore()+20);
			}
			
			Matcher matcher2 = alphaNumericWithAtLeastOneDashPattern.matcher(model.getName());
			if(matcher2.matches())
			{
				model.setScore(model.getScore()+50);
			}
						
//			if(model.getName().split("\\s").length > 1)
//			{
//				model.setScore(model.getScore()+10);
//			}
			
			if(model.getName().length() > 4 && model.getName().length() < 20)
			{
				model.setScore(model.getScore() + model.getName().length());	
			}			
		}
		
		Collections.sort(models);
		
//		for(Model model: models)
//		{
//			System.out.println(model.getName() + " - " + model.getScore());
//		}

		if (models.size() > 0)
		{
			result = models.get(0);
		}

		return result;
	}

	private List<Model> findWithCustom(List<String> tokens, int score)
	{
		List<Model> result = new ArrayList<Model>();
		
		for(int groupsize = 2; groupsize >0; groupsize--)
		{
			for (int anIndex = 0; anIndex < tokens.size() - (groupsize - 1); anIndex++)
			{
				List<String> tokensUnderAnalysis = new ArrayList<String>();
				
				tokensUnderAnalysis.add(tokens.get(anIndex));
	
				for (int aGroupIndex = 1; aGroupIndex < groupsize; aGroupIndex++)
				{
					tokensUnderAnalysis.add(tokens.get(anIndex + aGroupIndex));
				}
	
				String finalToken = StringUtils.join(tokensUnderAnalysis,"");
				
				if(groupsize == 1)
				{
					Matcher alphaNumericMatcher = alphaNumericPattern.matcher(finalToken);
					Matcher noDigitMatcher = nonDigitPattern.matcher(finalToken);

					if(alphaNumericMatcher.matches() && !noDigitMatcher.matches() && finalToken.length()>2)
					{
						Model model = new Model();
						model.setName(StringUtils.join(tokensUnderAnalysis," "));
						model.setScore(score);
						result.add(model);
					}					
				}
				else
				{
					Matcher commonModelMatcher = commonModelPattern.matcher(finalToken);

					if(commonModelMatcher.matches())
					{
						Model model = new Model();
						model.setName(StringUtils.join(tokensUnderAnalysis," "));
						model.setScore(score);
						result.add(model);
					}		
				}
			}
		}
		
		return result;
	}
	
	private List<Model> getModelsFromChunkResult(Chunking chunking, String str, int score)
	{
		List<Model> results = new ArrayList<Model>();

		if (chunking != null)
		{
			for (Chunk chunk : chunking.chunkSet())
			{
				Model model = getModelFromChunk(chunk, str, score);
				addModelToResult(results, model);
			}
		}

		return results;
	}

	private List<Model> getModelsFromChunkResult(Iterator<Chunk> chunks, String str, int score)
	{
		List<Model> results = new ArrayList<Model>();

		if (chunks != null)
		{
			while (chunks.hasNext())
			{
				Model model = getModelFromChunk(chunks.next(), str, score);
				addModelToResult(results, model);
			}
		}

		return results;
	}

	public void addModelToResult(List<Model> models, Model model)
	{
		//if (model.getScore() > 0.9 && model.getName().length() < 25)
		{
			models.add(model);
		}
	}

	private Model getModelFromChunk(Chunk chunk, String str, int score)
	{
		Model model = new Model();
		int start = chunk.start();
		int end = chunk.end();
		// String type = chunk.type();
//		double score = chunk.score();
//		score = Math.pow(2.0, score);
		model.setScore(score);
		String phrase = str.substring(start, end);
		model.setName(phrase);
		model.setStart(start);
		model.setEnd(end);
		return model;
	}

	public void test()
	{
		List<Product> products = JSONUtil.parseFile(new File("C:/work/karniyarik/files/products/hepsiburada.txt"), Product.class);
		
		StringBuffer buff = new StringBuffer();
		
		for (int index=0; index< products.size();index++)
		//for (Product product : products)
		{
			Product product = products.get(index);
			buff.append(product.getName().replaceAll("\\s+", " "));
			buff.append("\t");
			Model model = getModel(product.getName(), product.getBreadcrumb());
			
			if (model != null)
			{
				buff.append(model.getName());
			}
			else
			{
				//buff.append(" ");
			}
			
			buff.append("\n");
			
//			try
//			{
//				Thread.sleep(500);
//			}
//			catch (InterruptedException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		try
		{
			IOUtils.write(buff, new FileOutputStream("D:/hepsi.csv"), "UTF-8");
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		ModelFinder modelFinder = ModelFinder.getInstance();
		
		String[] testdata = new String[]{
//				"Samsung B7320 Omnia Pro (Route 66 Navigasyon Hediyeli)",
//				"Sapphire Ati HD3650 1GB/2.8GB* HM 128Bit DDR2 (DirectX 10.1) PCI-E x16 2.0 Ekran Kartı (11127-66-20G)",
//				"TOSHIBA L655 1G2 GRI NOTEBOOK",
//				"PIRANHA VEGA PLUS 2GB FM RADYOLU ŞARJLI DİJİTAL MP4 ÇALAR",
//				"HP LASERJET P1102 YAZICI",
//				"Britax-Römer King Plus Oto Koltuğu Olivia",
				"Asus Eee Pc 1008P KARIM RASHID Intel Atom N450 1.66GHZ 1GB 250GB 10\" Netbook Bilgisayar"
//				"Tommy Hilfiger Kadın Ayakkabı-TW22880",
//				"Black Diamond Turbo Buz Vidası (22 CM)-BD490113",
//				"Mapiwire 3:1 Bobin Tel Spiral"
//				"Quattro 8698720982403 5.0''Ekranlı  Araç Navigasyon Cihazı TV,FM ve 3D Özellikli",
//				"AIRTIES RT 204 54MBPS KABLOSUZ ADSL2+ 1 PORT MODEM + 8GB USB BELLEK",
//				"Eurosoft My Filter Pro Filtreleme Sistemi",
//				"HP Pavilion DM4-1050ET Intel Core i5 430M 2.26GHZ 3GB 320GB 14\" Taşınabilir Bilgisayar WQ081EA",
//				"Sony 46'' Full Hd Led Televizyon KDL-46HX800 + Transmitter + 5 Yıl Gatanti",
//				"Flormar Aqua Stay-On Fondöten 001",
//				"Pro2000 P2B767LED19 AMD Phenom X3 8400 2.1GHz 2GB 500GB 18.5\" LED Masaüstü Bilgisayar KDL-46HX800"
				
		};
		
		for(String testStr: testdata)
		{
			System.out.println("\n----------------");
			System.out.println(testStr);
			Model model = modelFinder.getModel(testStr, "");
			if(model != null)
			{
				System.out.println("--> " + model.getName() + "(" + model.getScore() +  ")");
			}
			else
			{
				System.out.println("No MAtch!!!");
			}
		}

//		modelFinder.test();
	}
}
