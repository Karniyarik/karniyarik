package com.karniyarik.categorizer.lingpipe;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

import com.aliasi.classify.Classifier;
import com.aliasi.classify.JointClassification;
import com.aliasi.util.AbstractExternalizable;
import com.karniyarik.categorizer.CategoryResult;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.util.StreamUtil;

public class LingpipeClassifier
{
	Classifier<CharSequence, JointClassification>	compiledClassifier	= null;

	public LingpipeClassifier()
	{
		try
		{
			String filename = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig().getModelPath() + "/" + Constants.getModelName();
			compiledClassifier = (Classifier<CharSequence, JointClassification>) AbstractExternalizable.readObject(StreamUtil.getFile(filename));
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<CategoryResult> resolve(String name, String brand, String breadcrumb)
	{
		List<CategoryResult> result = new ArrayList<CategoryResult>();

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

		return result;
	}
}
