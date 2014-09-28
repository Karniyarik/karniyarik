package com.karniyarik.brands.nb;

import java.io.Reader;

import org.apache.lucene.analysis.WhitespaceTokenizer;

public class BrandWhitespaceTokenizer extends WhitespaceTokenizer
{
	public BrandWhitespaceTokenizer(Reader in)
	{
		super(in);
	}

	@Override
	protected boolean isTokenChar(char ch)
	{
		return super.isTokenChar(ch) && !Character.isMirrored(ch) && !(ch == ',') && !(ch == '"') && !(ch == '(') && !(ch == ')'); 
	}	
}
