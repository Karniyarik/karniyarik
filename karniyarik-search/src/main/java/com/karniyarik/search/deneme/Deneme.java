package com.karniyarik.search.deneme;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.karniyarik.search.solr.SOLRQuerySearchProxy;


public class Deneme {

	public Deneme() {
		SOLRQuerySearchProxy proxy = new SOLRQuerySearchProxy();
		//List<String> queries = proxy.getQueries(CategorizerConfig.PRODUCT);
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		
		int count = 0;
		
		for(String query: new String []{"ankara'dan"})
		{
			if(count > 15000)
			{
				break;
			}
			count ++;
			String[] words = query.split("\\s");
			for(String word: words)
			{
				executor.submit(new ZemberekUserThread(word, count));	
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Character.toLowerCase('Ş'));
	}
}
