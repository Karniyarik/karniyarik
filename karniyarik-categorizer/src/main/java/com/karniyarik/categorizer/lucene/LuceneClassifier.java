package com.karniyarik.categorizer.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.karniyarik.categorizer.CategoryResult;
import com.karniyarik.categorizer.lingpipe.Trainer;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.ir.SearchConstants;

public class LuceneClassifier
{
	private Directory		directory	= null;
	private IndexSearcher	searcher	= null;

	public LuceneClassifier()
	{
		try
		{
			String filename = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig().getModelPath() + "/lucene";
			directory = FSDirectory.open(StreamUtil.getFile(filename));
			searcher = new IndexSearcher(directory, Boolean.TRUE);
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

		QueryParser qp = new QueryParser(SearchConstants.NAME, new WhitespaceAnalyzer());
		qp.setDefaultOperator(Operator.OR);
		
		CatQueryParser cqp = new CatQueryParser(qp);

		try
		{
			Query aLuceneQuery = cqp.parse(isntance);

			TopDocs topDocs = searcher.search(aLuceneQuery, 10);

			SummaryStatistics stat = new SummaryStatistics();

			for (int index = 0; index < 10 && index < topDocs.totalHits; index++)
			{
				ScoreDoc scoreDoc = topDocs.scoreDocs[index];
				Document doc = searcher.doc(scoreDoc.doc);
				String category = doc.get(SearchConstants.CATEGORY);
				CategoryResult catResult = new CategoryResult();
				catResult.setId(category);
				stat.addValue(scoreDoc.score);
				catResult.setScore(scoreDoc.score);
				// catResult.setScore(10-index);//scoreDoc.score);
				result.add(catResult);
			}

			double min = stat.getMin();
			double max = stat.getMax();
			double distance = max - min;
			double pace = distance / result.size();
			for (CategoryResult catResult : result)
			{
				catResult.setScore((catResult.getScore() - min) / pace * Constants.luceneFactor);
			}
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}

		return result;
	}
}
