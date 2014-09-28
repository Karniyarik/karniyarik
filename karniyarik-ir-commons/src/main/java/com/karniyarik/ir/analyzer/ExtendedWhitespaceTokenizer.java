package com.karniyarik.ir.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.WhitespaceTokenizer;

public class ExtendedWhitespaceTokenizer extends WhitespaceTokenizer
{
	public ExtendedWhitespaceTokenizer(Reader in)
	{
		super(in);
	}

	@Override
	protected boolean isTokenChar(char ch)
	{
		return super.isTokenChar(ch) && !Character.isMirrored(ch) && !(ch == '/') && !(ch == '&') && !(ch == '"'); 
	}
}