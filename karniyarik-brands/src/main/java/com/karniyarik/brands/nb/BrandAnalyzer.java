package com.karniyarik.brands.nb;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

public class BrandAnalyzer extends Analyzer {

	private static final String[] TURKISH_STOP_WORDS = {"bir", "ona", "ve", "ama",	"fakat"};

	public BrandAnalyzer() 
	{
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		TokenStream result = new BrandWhitespaceTokenizer(reader);
		result = new LowerCaseFilter(result);
		result = new StopFilter(false, result, StopFilter.makeStopSet(TURKISH_STOP_WORDS));
		return result;
	}
}
