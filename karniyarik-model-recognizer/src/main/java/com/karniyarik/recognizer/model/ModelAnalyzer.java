package com.karniyarik.recognizer.model;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

public class ModelAnalyzer extends Analyzer {

	private static final String[] TURKISH_STOP_WORDS = {"bir", "ona", "ve", "ama",	"fakat"};
	
	public ModelAnalyzer() 
	{
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		TokenStream result = new ModelTokenizer(reader);
		result = new ModelLowerCaseFilter(result);
		result = new StopFilter(false, result, StopFilter.makeStopSet(TURKISH_STOP_WORDS));
		result = new NoiseFilter(result);
		return result;
	}
}
