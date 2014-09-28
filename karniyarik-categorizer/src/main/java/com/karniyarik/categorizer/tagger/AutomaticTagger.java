package com.karniyarik.categorizer.tagger;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import com.aliasi.classify.Classifier;
import com.aliasi.classify.JointClassification;
import com.aliasi.util.AbstractExternalizable;
import com.karniyarik.categorizer.CategoryResult;
import com.karniyarik.categorizer.lingpipe.Trainer;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategorizerConfig;
import com.karniyarik.common.util.StreamUtil;

public class AutomaticTagger
{
	//private Analyzer								analyzer			= null;
	Classifier<CharSequence, JointClassification>	compiledClassifier	= null;
	private static AutomaticTagger instance = null;
	
	private AutomaticTagger()
	{
		try
		{
			CategorizerConfig categorizerConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig();
			String filename = categorizerConfig.getModelPath() + "/" +  Constants.getModelName();
			//analyzer = AnalyzerProvider.getAnalyzer();
			try
			{
				compiledClassifier = (Classifier<CharSequence, JointClassification>) AbstractExternalizable.readObject(StreamUtil.getFile(filename));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static AutomaticTagger getInstance() {
		if(instance == null)
		{
			instance = new AutomaticTagger();
		}
		return instance;
	}

	public String getTags(String name, String brand, String breadcrumb)
	{
		List<String> resolve = resolveTags(name, brand, breadcrumb);
		String result = StringUtils.join(resolve, ",");
		return result;
	}
	public List<String> resolveTags(String name, String brand, String breadcrumb)
	{
		List<CategoryResult> resolve = resolve(name, brand, breadcrumb);
		List<String> result = new ArrayList<String>();
		int size = 5;
		if(resolve.size() > size)
		{
			resolve = resolve.subList(0, size-1);
		}
		for(CategoryResult cat: resolve)
		{
			result.add(cat.getId());
		}
			
		return result;
	}
	
	public List<CategoryResult> resolve(String name, String brand, String breadcrumb)
	{
		List<CategoryResult> result = new ArrayList<CategoryResult>();

		if(compiledClassifier != null)
		{
			String isntance = Trainer.getProductDescription(name, breadcrumb, brand);
			
			JointClassification jc = compiledClassifier.classify(isntance);

			SummaryStatistics stat = new SummaryStatistics();
			
			for (int index = 0; index < 10 && index < jc.size(); index++)
			{
				CategoryResult catResult = new CategoryResult();
				catResult.setId(jc.category(index));
				double score = jc.score(index);
				score = -1 * score;
				if(score > 10)
				{
					score = 10;
				}
				score = 10 - score;
				stat.addValue(score);
				catResult.setScore(score);
				result.add(catResult);
			}

			double min = stat.getMin();
			double max = stat.getMax();
			double distance = max - min;
			double pace = distance / result.size();
			for(CategoryResult catResult: result)
			{
				catResult.setScore((catResult.getScore()-min)/pace * Constants.lingpipeFactor);
			}	
		}		

		return result;
	}
	
	public static void main(String[] args) {
		Object[][] testdata = new Object[][]{
				{"TREXTA TONE CREAM IPHONE 4 KILIFI", "Apple", "", 2400.33},
				{"SAMSUNG C3510 CEP TELEFONU", "Samsung", "", 4.13}
		};
		
		AutomaticTagger tagger= new AutomaticTagger();
		
		for(Object[] item: testdata)
		{
			List<CategoryResult> resolve = tagger.resolve((String)item[0], (String)item[1], (String)item[2]);
			System.out.println(item[0] + ":");
			for(CategoryResult result: resolve)
			{
				System.out.print(result.getId() + ", ");				
			}
			System.out.println("----");
		}

	}
}
