package com.karniyarik.ir.analyzer;

import java.io.IOException;
import java.util.Stack;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import com.karniyarik.ir.synonym.SynonymEngine;

public class SynonymFilter extends TokenFilter
{
	private final static String		TOKEN_TYPE_SYNONYM	= "synonym";

	private Stack<Token>			mSynonymStack		= null;
	private static SynonymEngine	mEngine				= null;

	public SynonymFilter(TokenStream in)
	{
		super(in);

		mSynonymStack = new Stack<Token>();

		if (mEngine == null)
		{
			mEngine = new SynonymEngine();
		}

	}

	@Override
	public Token next() throws IOException
	{
		if (mSynonymStack.size() > 0)
		{
			return mSynonymStack.pop();
		}

		Token aToken = input.next();

		if (aToken == null)
		{
			return null;
		}

		addAliasesToStack(aToken);

		return aToken;
	}

	private void addAliasesToStack(Token aToken)
	{
		String[] aSynonymsArray = mEngine.getSnonyms(String.valueOf(aToken
				.termBuffer(), 0, aToken.termLength()));

		Token aSynonymToken = null;

		if (aSynonymsArray != null && aSynonymsArray.length > 0)
		{
			for (String aSnonym : aSynonymsArray)
			{
				if (aSnonym.trim().equalsIgnoreCase(aToken.term().trim()))
				{
					continue;
				}
			
				aSynonymToken = new Token(aToken.startOffset(), aToken
						.endOffset(), TOKEN_TYPE_SYNONYM);
				aSynonymToken.setTermBuffer(aSnonym.toCharArray(), 0, aSnonym.length());

				aSynonymToken.setPositionIncrement(0);

				mSynonymStack.push(aSynonymToken);
			}
		}
	}
}
