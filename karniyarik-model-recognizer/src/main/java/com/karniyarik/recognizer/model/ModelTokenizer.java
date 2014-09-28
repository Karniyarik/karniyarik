package com.karniyarik.recognizer.model;

import java.io.Reader;

import org.apache.lucene.analysis.WhitespaceTokenizer;

public class ModelTokenizer extends WhitespaceTokenizer
{
	public ModelTokenizer(Reader in)
	{
		super(in);
	}

	@Override
	protected boolean isTokenChar(char ch)
	{
		return super.isTokenChar(ch) && !Character.isMirrored(ch) && !(ch == ','); 
	}
}
