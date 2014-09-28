package com.karniyarik.ir.analyzer;

import java.io.Reader;
import java.util.Locale;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.Kelime;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

public class NewTurkishAnalyzer extends Analyzer {

	private static final String[] TURKISH_STOP_WORDS = {"bir", "ona", "ve", "ama",	"fakat"};
	private boolean isStrict = false;
	private boolean isForSearch = false;
	private final Zemberek zemberek;

	public NewTurkishAnalyzer(Zemberek zemberek, boolean isStrict, boolean isForSearch) {

		this.isStrict = isStrict;
		this.isForSearch = isForSearch;
		this.zemberek = zemberek;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		TokenStream result = new ExtendedWhitespaceTokenizer(reader);
		
		if (isForSearch) {
			result =
				new WordDelimiterFilter(
						result, //in Token stream to be filtered.
						1, //generateWordParts If 1, causes parts of words to be generated: "PowerShot", "Power-Shot" => "Power" "Shot"
						1, //generateNumberParts If 1, causes number subwords to be generated: "500-42" => "500" "42"
						1, //catenateWords  1, causes maximum runs of word parts to be catenated: "wi-fi" => "wifi"
						0, //catenateNumbers If 1, causes maximum runs of number parts to be catenated: "500-42" => "50042"
						0, //catenateAll If 1, causes all subword parts to be catenated: "wi-fi-4000" => "wifi4000"
						0, //splitOnCaseChange 1, causes "PowerShot" to be two tokens; ("Power-Shot" remains two parts regards)
						1, //preserveOriginal If 1, includes original words in subwords: "500-42" => "500" "42" "500-42"
						1, //splitOnNumerics 1, causes "j2se" to be three tokens; "j" "2" "se"
						0, //stemEnglishPossessive If 1, causes trailing "'s" to be removed for each subword: "O'Neil's" => "O", "Neil"
						null //protWords If not null is the set of tokens to protect from being delimited
				);
		}
		else {
			result =
				new WordDelimiterFilter(
						result, //in Token stream to be filtered.
						1, //generateWordParts If 1, causes parts of words to be generated: "PowerShot", "Power-Shot" => "Power" "Shot"
						1, //generateNumberParts If 1, causes number subwords to be generated: "500-42" => "500" "42"
						1, //catenateWords  1, causes maximum runs of word parts to be catenated: "wi-fi" => "wifi"
						1, //catenateNumbers If 1, causes maximum runs of number parts to be catenated: "500-42" => "50042"
						1, //catenateAll If 1, causes all subword parts to be catenated: "wi-fi-4000" => "wifi4000"
						1, //splitOnCaseChange 1, causes "PowerShot" to be two tokens; ("Power-Shot" remains two parts regards)
						1, //preserveOriginal If 1, includes original words in subwords: "500-42" => "500" "42" "500-42"
						1, //splitOnNumerics 1, causes "j2se" to be three tokens; "j" "2" "se"
						0, //stemEnglishPossessive If 1, causes trailing "'s" to be removed for each subword: "O'Neil's" => "O", "Neil"
						null //protWords If not null is the set of tokens to protect from being delimited
				);
		}

		result = new LowerCaseFilter(result);
		result = new StopFilter(false, result, StopFilter.makeStopSet(TURKISH_STOP_WORDS));

		if (isStrict) {
			result = new StrictZemberekFilter(zemberek, result);
		}
		else {
			result = new ZemberekFilter(zemberek, result);
		}

		//remove this later and used built-in lucene class
		result = new SynonymFilter(result);

		return result;
	}
	
	/**
	 * This method shall be moved to a more appropriate place.
	 * It is here because creating Zemberek is expensive and
	 * pooled NewTurkishAnalyzers have Zemberek instances.
	 * @param word
	 * @return
	 */
	public String getEnUzunKok(String word) {

		String result = word.toLowerCase(Locale.ENGLISH);

		try {

			Kelime[] asciidenTurkceyeCevirilmisKelimeler = zemberek.asciiCozumle(word);
			
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
				result = enUzunKokluKelime.kok().icerik().toLowerCase(Locale.ENGLISH); 
			}
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass()).log(Priority.ERROR, "can't get enUzunKok with word " + word);
		}
		
		return result; 
	}
}
