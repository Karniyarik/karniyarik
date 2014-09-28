package com.karniyarik.ir.analyzer;

import java.io.Reader;

import org.apache.solr.analysis.BaseTokenizerFactory;

public class ExtendedWhitespaceTokenizerFactorySolr extends BaseTokenizerFactory {

	public ExtendedWhitespaceTokenizerSolr create(Reader input) {
	    return new ExtendedWhitespaceTokenizerSolr(input);
	  }

}
