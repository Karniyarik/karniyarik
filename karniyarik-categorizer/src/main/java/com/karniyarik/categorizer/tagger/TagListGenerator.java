package com.karniyarik.categorizer.tagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;

public class TagListGenerator {
	private Map<String, Integer> tagFreq = new HashMap<String, Integer>();
	
	public TagListGenerator() throws Exception {
				
	}

	public void generate() throws IOException, FileNotFoundException {
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		File file = new File(categorizerConfig.getModelPath() + "/breadcrumb_all.txt");
		List<String> lines = IOUtils.readLines(new FileInputStream(file));
		File outfile = new File(categorizerConfig.getModelPath() + "/tags.csv");
		
		Tagger tagger = new Tagger();
		
		for(String line: lines)
		{
			List<String> tags = tagger.getTags(line);
			for(String tag: tags)
			{
				Integer count = tagFreq.get(tag);
				if(count == null)
				{
					String permutatedTag = null;
					
					String[] words = tag.split("\\s");
					if(words.length > 1)
					{
						Permute permute = new Permute(words);
						while(permute.hasNext())
						{
							String[] permutationArr = (String[]) permute.next();
							String permutation = StringUtils.join(permutationArr, " ");
							count = tagFreq.get(permutation);
							if(count != null)
							{
								permutatedTag = permutation;
								break;
							}
						}
					}
					
					if(permutatedTag != null)
					{
						tag = permutatedTag;
						count ++;	
					}
					else
					{
						count = 0;	
					}
					
					
					tagFreq.put(tag, count);
				}
				else
				{
					count ++;
					tagFreq.put(tag, count);	
				}
				
			}
		}
		
		StringBuffer result = new StringBuffer();
		for(String tag: tagFreq.keySet())
		{
			int count = tagFreq.get(tag);
			if(count > 50)
			{
				result.append(tag);
				result.append("\t");
				result.append(count);
				result.append("\n");				
			}
		}
		
		IOUtils.write(result, new FileOutputStream(outfile), "UTF-8");
	}
	
	public void filter()
	{
		Map<String, NewWord> newWordMap = new HashMap<String, NewWord>();
		
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		File file = new File(categorizerConfig.getModelPath() + "/tags.csv");
		
		try {
			FileInputStream is = new FileInputStream(file);
			List<String> lines = IOUtils.readLines(is);
			
			TagNameComparator comparator = new TagNameComparator();
			for(String line: lines)
			{
				String[] split = line.split("\t");
				int count = Integer.valueOf(split[1]);
				String word = split[0];

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
			is.close();
			
			file = new File(categorizerConfig.getModelPath() +  "/tags_filtered.csv");
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
	
	public static void main(String[] args) throws Exception {
		new TagListGenerator().filter();
		//new TagListGenerator().generate();
	}
}
