package com.karniyarik.categorizer.prep;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermEnum;

import com.karniyarik.categorizer.io.IndexIO;
import com.karniyarik.categorizer.util.Constants;
import com.karniyarik.common.vo.Product;
import com.karniyarik.ir.SearchConstants;
import com.karniyarik.ir.analyzer.NewTurkishAnalyzer;
import com.karniyarik.ir.analyzer.TurkishAnalyzerPool;
import com.karniyarik.ir.index.IndexSearcherFactory;


public class ProductNameDumper
{
	Map<String, TermFreq> map = new HashMap<String, TermFreq>();
	
	public ProductNameDumper()
	{
		
	}	
	
	public void dump()
	{
		constructTerms2();
		Collection<TermFreq> values = map.values();
		List<TermFreq> list = new ArrayList<TermFreq>(values);
		Collections.sort(list);
		writeFile(list);
	}
	
	public void constructTerms1()
	{
		try
		{
			IndexReader indexReader = new IndexSearcherFactory().createProductIndexReader("urun");
			TermEnum terms = indexReader.terms();

			while(terms.next())
			{
				if(terms.term().field().equalsIgnoreCase(SearchConstants.NAME))
				{
					map.put(terms.term().text(), new TermFreq(terms.term().text(),terms.docFreq()));
				}
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void constructTerms2()
	{
		long start = System.currentTimeMillis();
		
		List<Product> productList = IndexIO.getReader().readAll();
		
		NewTurkishAnalyzer indexAnalyzer = TurkishAnalyzerPool.getInstance().borrowIndexAnalyzer();
		
		try
		{
			int count = 0;
			for(Product product: productList)
			{
				count++;
				
				String name = product.getName();
				TokenStream tokenStream = indexAnalyzer.tokenStream(null, new StringReader(name));
				Token token = null;
				while((token = tokenStream.next()) != null)
				{
					String term = token.term();
					TermFreq termFreq = map.get(term);
					
					if(termFreq == null)
					{
						termFreq = new TermFreq(term, 1);
						map.put(term, termFreq);
					}
					else
					{
						termFreq.increaseCountByOne();
					}
				}
				
				if(count % 100000 == 0)
				{
					System.out.println("100,000 products handled");
				}
			}
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			TurkishAnalyzerPool.getInstance().returnIndexAnalyzer(indexAnalyzer);
		}

		long end = System.currentTimeMillis();
		
		System.out.println("finished " + productList.size() + " products in " + (end-start) + " seconds" );
	}

	private void writeFile(Collection<TermFreq> list)
	{
		try
		{
			File file = new File(Constants.analysisDir + "/namefreq.txt");
			file.createNewFile();
			FileOutputStream fileout = new FileOutputStream(file);
			for(TermFreq termFreq: list)
			{
				IOUtils.write(termFreq.getName() + "\t," + termFreq.getCount() + "\n", fileout, "UTF-8");
			}
			
			fileout.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args)
	{
		new ProductNameDumper().dump();
	}
}
