package com.karniyarik.categorizer.tagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.brands.BrandServiceImpl;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;

public class NewWordsCreator {

	private Map<String, Integer> newWords = new HashMap<String, Integer>();
	private Pattern validWordRegEx = null;
	private Pattern numericWordRegEx = null;
	private Pattern alphaWordRegEx = null;

	public NewWordsCreator() {
		validWordRegEx = Pattern.compile("[\\w\\s]+");
		alphaWordRegEx = Pattern.compile("[a-zA-ZöçşığüÖÇŞİĞÜ\\s]+");
		numericWordRegEx = Pattern.compile("[\\s\\d]+");
	}

	public boolean newFoundWord(String token) {		
		boolean result = false;
		
		String convertedToken = StringUtil.convertTurkishCharacter(token);
		Matcher matcher = validWordRegEx.matcher(convertedToken);
		Matcher numericMatcher = numericWordRegEx.matcher(convertedToken);
		Matcher alphaMatcher = alphaWordRegEx.matcher(token);
		
		if (matcher.matches() && !numericMatcher.matches() && alphaMatcher.matches()
				&& token.length() > 4) {

			result = true;
			token = token.trim();
			Integer count = newWords.get(token);
			if (count == null) {
				count = 0;
			}
			count++;
			newWords.put(token, count);
		} 
		
		return result;
	}

	public void writeNewWords() throws Exception {
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		writeFreq(categorizerConfig.getModelPath() + "/newwords.txt", newWords);
	}

	public void writeFreq(String filename, Map<String, Integer> map) throws Exception {
		File file = new File(filename);
		StringBuffer result = new StringBuffer();
		for (String key : map.keySet()) {
			result.append(key);
			result.append("\t");
			result.append(map.get(key));
			result.append("\n");
		}
		IOUtils.write(result, new FileOutputStream(file), "UTF-8");
	}

	public void filter()
	{
		Map<String, NewWord> newWordMap = new HashMap<String, NewWord>();
		
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		File file = new File(categorizerConfig.getModelPath() + "/newwords.txt");
		try {
			FileInputStream is = new FileInputStream(file);
			List<String> lines = IOUtils.readLines(is);
			
			TagNameComparator comparator = new TagNameComparator();
			for(String line: lines)
			{
				String[] split = line.split("\t");
				int count = Integer.valueOf(split[1]);
				String word = split[0];
				if(count > 10)
				{
					Matcher alphaMatcher = alphaWordRegEx.matcher(word);
					String brand = BrandServiceImpl.getInstance().resolveBrand(word);
					
					if(alphaMatcher.matches() && !BrandServiceImpl.getInstance().isBrandRecognized(brand))
					{
						NewWord newWord = new NewWord(word, "", count);
						String maximumSimilarity = comparator.getMaximumSimilarity(word, newWordMap.keySet());
						if(StringUtils.isNotBlank(maximumSimilarity))
						{
							newWordMap.get(maximumSimilarity).addSimilarWord(newWord);
						}
						else
						{
							newWordMap.put(word, newWord);
						}
					}					
				}
			}
			is.close();
			
			file = new File(categorizerConfig.getModelPath() + "/newwords_filtered.txt");
			StringBuffer result = new StringBuffer();
			for (NewWord word: newWordMap.values()) {
				result.append(word.getWord());
				result.append("\t");
				result.append(word.getCount());
				for(NewWord similar: word.getSimilarWords())
				{
					result.append("\t");
					result.append(similar.getWord());
				}
				result.append("\n");
			}
			IOUtils.write(result, new FileOutputStream(file), "UTF-8");

		} catch (Throwable e) {
			e.printStackTrace();
		} 	
	}
	
	public List<String> getNewWords()
	{
		List<String> newWords = new LinkedList<String>();
		try {
			InputStream is = StreamUtil.getStream("newwords_filtered.txt");
			List<String> lines = IOUtils.readLines(is);
			for(String line: lines)
			{
				String[] split = line.split("\t");
				String word = split[0];
				newWords.add(word);
			}		
			is.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return newWords;
	}
	
	public static void main(String[] args) {
		new NewWordsCreator().filter();
		//new NewWordsCreator().newFoundWord("cl1980");
	}
}
