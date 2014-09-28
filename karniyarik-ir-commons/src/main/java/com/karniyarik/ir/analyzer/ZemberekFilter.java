package com.karniyarik.ir.analyzer;

import java.io.IOException;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.Kelime;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class ZemberekFilter extends TokenFilter {

	private Stack<Token> cozumlenenTokenStack;
	private final Zemberek zemberek;
	private final Set<String> cozumlenenTokenSet = new TreeSet<String>();
	
	public ZemberekFilter(Zemberek zemberek, TokenStream input) {

		super(input);
		cozumlenenTokenStack = new Stack<Token>();
		this.zemberek = zemberek;
	}
	
	@Override
	public Token next() throws IOException {

		if (cozumlenenTokenStack.size() > 0) {
			return cozumlenenTokenStack.pop();
		}

		Token token = input.next();

		if (token == null) {
			return null;
		}

		if (addAliasesToStack(token)) {
			return cozumlenenTokenStack.pop();
		}
		else {
			return token;
		}
	}

	private boolean addAliasesToStack(Token token) {

		boolean result = false;

		Kelime[] asciidenTurkceyeCevirilmisKelimeler = zemberek.asciiCozumle(token.term());

		for (Kelime cevirilmisKelime : asciidenTurkceyeCevirilmisKelimeler) {

			Token cozumlenenToken = new Token(
					cevirilmisKelime.kok().icerik(),
					token.startOffset(),
					token.endOffset());
			
			cozumlenenToken.setPositionIncrement(0);
			
			if (cozumlenenTokenSet.add(cozumlenenToken.term())) {
				cozumlenenTokenStack.push(cozumlenenToken);
				result = true;
			}
		}

		//add original word also
		if (cozumlenenTokenSet.add(token.term())) {
			cozumlenenTokenStack.add(token);
			result = true;
		}
		//
		return result;
	}
}
