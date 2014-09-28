package com.karniyarik.categorizer.tagger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.karniyarik.categorizer.CategoryResult;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.StringUtil;

public class TagTester {

	public static void main(String[] args) throws Exception {
		CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		File trainingFile  = new File(categorizerConfig.getModelPath() + "/trainingset.csv");
		FileInputStream is = new FileInputStream(trainingFile);
		LineIterator lineIterator = IOUtils.lineIterator(is, StringUtil.DEFAULT_ENCODING);
		
		AutomaticTagger tagger= AutomaticTagger.getInstance();
		
		StringBuffer buff = new StringBuffer();
		
		DecimalFormat format = new DecimalFormat("##.#");
		int jump = 5000;
		int count = 0;
		while(lineIterator.hasNext())
		{
			String line = lineIterator.nextLine();
			count++;
			if(count < jump)
			{
				continue;
			}
			else
			{
				count = 0;
			}
			
			String[] split = line.split("\t");
			
			String name = split[0];
			String brand = split[1];
			String breadcrumb = ""; //split[2];
			
			Set<String> tags = new HashSet<String>();
			for(int index = 3; index < split.length; index++)
			{
				tags.add(split[index]);
			}
			
			List<CategoryResult> resolved = tagger.resolve(name, brand, breadcrumb);
			
			if(resolved.size() > 0)
			{
				int end = 3;
				
				if(end > resolved.size())
				{
					end = resolved.size();
				}

				int size = resolved.size();
				if(tags.size() < size)
				{
					size = tags.size();
				}
				
				double score = 0;
				for(int index = 0; index < size; index++)
				{
					score += tags.contains(resolved.get(index).getId()) ? 1: 0; 
				}
				
				String scoreStr = "U";
				if(size != 0)
				{
					score = score/size*100;
					scoreStr = format.format(score);
				}				
				
				List<CategoryResult> subList = resolved.subList(0, end);
				StringBuffer lineOut = new StringBuffer();
				lineOut.append(scoreStr);
				lineOut.append("\t");
				lineOut.append(name);
				lineOut.append("\t");
				lineOut.append(brand);
				lineOut.append("\t");
				for(CategoryResult result: subList)
				{
					lineOut.append(result.getId());
					lineOut.append("\t");
				}				
				lineOut.append("\n");
				System.out.print(lineOut);
				buff.append(lineOut);
			}
		}
		
		categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
		File file  = new File(categorizerConfig.getModelPath() + "/testresults.csv");
		FileOutputStream os = new FileOutputStream(file);
		IOUtils.write(buff, os);
		os.close();
	}
}
