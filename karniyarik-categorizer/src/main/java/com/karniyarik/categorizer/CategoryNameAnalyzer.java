package com.karniyarik.categorizer;

import java.io.Reader;

import net.zemberek.erisim.Zemberek;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

import com.karniyarik.ir.analyzer.ExtendedWhitespaceTokenizer;
import com.karniyarik.ir.analyzer.SynonymFilter;
import com.karniyarik.ir.analyzer.WordDelimiterFilter;

public class CategoryNameAnalyzer extends Analyzer
{
	private static final String[]	TURKISH_STOP_WORDS	= { "bir", "ona", "ve", "ama", "fakat", "siyah", "set", "çar", "100", "beyaz", "diğer" };
	private final Zemberek			zemberek;

	public CategoryNameAnalyzer(Zemberek zemberek)
	{
		this.zemberek = zemberek;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader)
	{

		TokenStream result = new ExtendedWhitespaceTokenizer(reader);

		result = new WordDelimiterFilter(result, // in Token stream to be
				// filtered.
				1, // generateWordParts If 1, causes parts of words to be
				// generated: "PowerShot", "Power-Shot" => "Power" "Shot"
				1, // generateNumberParts If 1, causes number subwords to be
				// generated: "500-42" => "500" "42"
				1, // catenateWords 1, causes maximum runs of word parts to be
				// catenated: "wi-fi" => "wifi"
				1, // catenateNumbers If 1, causes maximum runs of number parts
				// to be catenated: "500-42" => "50042"
				1, // catenateAll If 1, causes all subword parts to be
				// catenated: "wi-fi-4000" => "wifi4000"
				1, // splitOnCaseChange 1, causes "PowerShot" to be two tokens;
				// ("Power-Shot" remains two parts regards)
				1, // preserveOriginal If 1, includes original words in
				// subwords: "500-42" => "500" "42" "500-42"
				1, // splitOnNumerics 1, causes "j2se" to be three tokens; "j"
				// "2" "se"
				0, // stemEnglishPossessive If 1, causes trailing "'s" to be
				// removed for each subword: "O'Neil's" => "O", "Neil"
				null // protWords If not null is the set of tokens to protect
		// from being delimited
		);

		result = new LowerCaseFilter(result);
		result = new StopFilter(false, result, StopFilter.makeStopSet(TURKISH_STOP_WORDS));
		result = new CategoryTurkishFilter(zemberek, result);
		// remove this later and used built-in lucene class
		result = new SynonymFilter(result);

		return result;
	}
}
