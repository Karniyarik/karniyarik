package com.karniyarik.categorizer.analyzer;

import java.io.IOException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.Kelime;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class CatZemberekFilter extends TokenFilter {

	private final Stack<Token> cozumlenenTokenStack;
	private final Zemberek zemberek;
	private final Pattern regEx = Pattern.compile("[^A-Za-zıİşŞğĞüÜçÇöÖ]");
	
	public CatZemberekFilter(Zemberek zemberek, TokenStream input) {

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
		
		Matcher m = regEx.matcher(token.term());

		if (!m.find()) {

			Kelime[] asciidenTurkceyeCevirilmisKelimeler = zemberek.asciiCozumle(token.term());
			
			Kelime enUzunKokluKelime = null;
			
			for (Kelime cevirilmisKelime : asciidenTurkceyeCevirilmisKelimeler) {
				
				if (enUzunKokluKelime != null) {
					if (cevirilmisKelime.kok().icerik().length() > enUzunKokluKelime.kok().icerik().length()) {
						enUzunKokluKelime = cevirilmisKelime;
					}
				}
				else {
					enUzunKokluKelime = cevirilmisKelime;
				}
			}
			
			if (enUzunKokluKelime != null) {
				
				Token cozumlenenToken = new Token(
						enUzunKokluKelime.kok().icerik(),
						token.startOffset(),
						token.endOffset());
				
				cozumlenenToken.setPositionIncrement(1);
				
				cozumlenenTokenStack.push(cozumlenenToken);
				result = true;
				
				//add original word also
				if (!enUzunKokluKelime.kok().icerik().equals(token.term())) {
					token.setPositionIncrement(0);
					cozumlenenTokenStack.add(token);
					result = true;
				}
				//
			}
		}
		return result;
	}
}
