package com.karniyarik.categorizer.trash;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.KeepOnlyLastCommitDeletionPolicy;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.karniyarik.categorizer.ProductClassifier;
import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.CategoryConfig;
import com.karniyarik.common.config.system.SearchConfig;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.vo.Product;
import com.karniyarik.common.vo.ProductProperty;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;

public class IndexUpdate
{
	private static String	origIndex		= "C:/java/krnyrk/index/Ürün/productindex";
	private static String	newIndex		= "C:/java/krnyrk/index/Ürün/productindex1";
	private CategoryConfig	categoryConfig	= null;

	public IndexUpdate()
	{
		IndexWriter writer = null;
		try
		{
			long start = System.currentTimeMillis();
			System.out.println("Started");
			File origIndexDir = getIndexDirectory(origIndex);
			File newIndexDir = getIndexDirectory(newIndex);
			Directory origLuceneDir = FSDirectory.open(origIndexDir);
			Directory newLuceneDir = FSDirectory.open(newIndexDir);
			categoryConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCategorizerConfig().getCategoryConfig("Ürün");

			IndexReader origIndex = IndexReader.open(origLuceneDir, new KeepOnlyLastCommitDeletionPolicy(), true);

			SearchConfig searchConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig();
//			System.out.println("Borrow index IndexUpdate.java constructor");
			writer = new IndexWriter(newLuceneDir, TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer(), new MaxFieldLength(searchConfig.getLuceneMaxFieldLength()));
			writer.setMaxMergeDocs(searchConfig.getLuceneMaxMergeDocs());
			writer.setMergeFactor(searchConfig.getLuceneMergeFactor());
			writer.setMaxBufferedDocs(searchConfig.getLuceneMaxBufferedDocs());
			writer.setUseCompoundFile(true);

			SummaryStatistics stat = new SummaryStatistics();

			ProductClassifier.getInstance(true).resolveCategoryId("dumb", "karniyarik", "fuck", 0);

			List<Document> documentList = new ArrayList<Document>();

			for (int index = 0; index < origIndex.numDocs(); index++)
			{
				Document document = origIndex.document(index);
				Product product = ProductDocumentUtil.prepareProduct(document, categoryConfig);

				String name = product.getName();
				String brand = product.getBrand();
				String breadcrumb = product.getBreadcrumb();
				Double price = product.getPrice();

				long a = System.currentTimeMillis();
				String catId = ProductClassifier.getInstance(true).resolveCategoryId(name, brand, breadcrumb, price);
				long b = System.currentTimeMillis();

				stat.addValue(b - a);

				Document newDocument = prepareDocument(document, product, catId);

				documentList.add(newDocument);

				if (documentList.size() > 100000)
				{
					System.out.println("Flushing into index");
					for (Document doc : documentList)
					{
						writer.addDocument(doc);
					}

					System.out.println("Flushed");
					documentList.clear();
				}

				if (index % 10000 == 0)
				{
					System.out.println("10000 instances handled. current: " + index + ", remained:" + (origIndex.numDocs() - index));
					printStat(stat);
				}
			}

			long end = System.currentTimeMillis();

			System.out.println("Ended in " + (end - start) / (60 * 1000) + " minutes");
			printStat(stat);
			writer.optimize();
		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{

			if (writer != null) {
				try
				{
//					System.out.println("Return index IndexUpdate constructor");
					TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(writer.getAnalyzer());
					writer.close();
				}
				catch (Throwable e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void printStat(SummaryStatistics stat)
	{
		System.out.println("Average time for classification: " + stat.getSum() / stat.getN());
		System.out.println("Mean time for classification: " + stat.getMean());
		System.out.println("Max time for classification: " + stat.getMax());
		System.out.println("Min time for classification: " + stat.getMin());
		System.out.println("Deviation: " + stat.getStandardDeviation());
	}

	private File getIndexDirectory(String dir)
	{
		File file = StreamUtil.getFile(dir);

		if (!file.exists())
		{
			file.mkdir();
		}

		return file;
	}

	public Document prepareDocument(Document oldDocument, Product product, String category)
	{
		Document aDoc = null;

		aDoc = new Document();

		aDoc.add(new Field(SearchConstants.NAME, oldDocument.get(SearchConstants.NAME), Field.Store.YES, Field.Index.ANALYZED));

		aDoc.add(new Field(SearchConstants.CATEGORY, category, Field.Store.YES, Field.Index.NOT_ANALYZED));

		aDoc.add(new Field(SearchConstants.BRAND, oldDocument.get(SearchConstants.BRAND), Field.Store.YES, Field.Index.NOT_ANALYZED));

		aDoc.add(new Field(SearchConstants.STORE, oldDocument.get(SearchConstants.STORE), Field.Store.YES, Field.Index.NOT_ANALYZED));

		aDoc.add(new Field(SearchConstants.BREADCRUMB, oldDocument.get(SearchConstants.BREADCRUMB), Field.Store.YES, Field.Index.ANALYZED));

		aDoc.add(new Field(SearchConstants.PRICE, oldDocument.get(SearchConstants.PRICE), Field.Store.YES, Field.Index.NOT_ANALYZED));

		// all free-text like user entered queries will be searched over this
		// field
		aDoc.add(new Field(SearchConstants.KEYWORDS, ProductDocumentUtil.preapreProductKeywords(product), Field.Store.NO, Field.Index.ANALYZED));

		for (ProductProperty productProperty : product.getProperties())
		{
			aDoc.add(new Field(productProperty.getName(), ProductDocumentUtil.getCategoryPropertyString(productProperty.getName(), productProperty.getValue(), categoryConfig), Field.Store.YES,
					Field.Index.NOT_ANALYZED));
		}

		aDoc.add(new Field(SearchConstants.CURRENCY, oldDocument.get(SearchConstants.CURRENCY), Field.Store.YES, Field.Index.NO));

		aDoc.add(new Field(SearchConstants.STORE_URL, oldDocument.get(SearchConstants.STORE_URL), Field.Store.YES, Field.Index.NO));

		aDoc.add(new Field(SearchConstants.PRODUCT_URL, oldDocument.get(SearchConstants.PRODUCT_URL), Field.Store.YES, Field.Index.NOT_ANALYZED));

		aDoc.add(new Field(SearchConstants.LAST_FETCH_DATE, oldDocument.get(SearchConstants.LAST_FETCH_DATE), Field.Store.YES, Field.Index.NO));

		aDoc.add(new Field(SearchConstants.IMAGE_URL, oldDocument.get(SearchConstants.IMAGE_URL), Field.Store.YES, Field.Index.NO));

		return aDoc;
	}

	public static void main(String[] args)
	{
		new IndexUpdate();
	}
}
