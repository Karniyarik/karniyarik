package com.karniyarik.recognizer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.util.StreamUtil;

public class TestDataConstructor
{
	String[] alphabet = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","q","s","t","u","v","y","x","z"};
	public TestDataConstructor()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void construct(String filename, String fieldName) throws Exception
	{
		String url = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSearchConfig().getIndexDirectory() + File.separator + "araba" + File.separator + "productindex";
		Directory directory = FSDirectory.open(new File(StreamUtil.getURL(url).toURI()));
		IndexSearcher searcher = new IndexSearcher(directory, Boolean.TRUE);
		QueryParser qp = new QueryParser("body", new StandardAnalyzer());

		Set<String> strList = new HashSet<String>(); 
		for(String str: alphabet)
		{
			runQuery(fieldName, searcher, qp, strList, str + "*");	
		}
		
		StringBuffer results = new StringBuffer();
		for(String str: strList)
		{
			results.append(str);
			results.append("\n");
		}
		
		FileOutputStream fileOutputStream = new FileOutputStream(new File(filename));
		IOUtils.write(results.toString(), fileOutputStream);
		fileOutputStream.close();
	}

	private void runQuery(String fieldName, IndexSearcher searcher, QueryParser qp, Set<String> list, String queryStr) throws Exception
	{
		Query query = qp.parse(queryStr);
		TopDocs searchResult = searcher.search(query, 5000);
		for(ScoreDoc scoreDoc: searchResult.scoreDocs)
		{
			Document doc = searcher.doc(scoreDoc.doc);
			if(doc.get(fieldName) != null)
			{
				list.add(doc.get(fieldName).trim());	
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new TestDataConstructor().construct("carmodel.txt", "name");
	}
}
