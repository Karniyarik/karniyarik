package com.karniyarik.ir.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.CharTokenizer;
import org.apache.lucene.util.AttributeSource;

public class ExtendedWhitespaceTokenizerSolr  extends CharTokenizer {
	  public ExtendedWhitespaceTokenizerSolr(Reader in) {
	    super(in);
	  }

	  public ExtendedWhitespaceTokenizerSolr(AttributeSource source, Reader in) {
	    super(source, in);
	  }

	  public ExtendedWhitespaceTokenizerSolr(AttributeFactory factory, Reader in) {
	    super(factory, in);
	  }
	  
	  protected boolean isTokenChar(char c) {
	    return !Character.isWhitespace(c) && !Character.isMirrored(c) && !(c == '/') && !(c == '&') && !(c == '"');
	  }
}