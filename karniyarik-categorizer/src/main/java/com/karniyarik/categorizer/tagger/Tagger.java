package com.karniyarik.categorizer.tagger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.zemberek.erisim.Zemberek;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.common.util.StringUtil;

public class Tagger {
	public static String COMMON_BREADCRUMB_DELIMITERS = "[->|»|>|/|-|\\\\|&]";
	private static String[] NOISE_WORDS = new String[] {"ana sayfa", "diğer", "tl", 
	"detay", "yeni sayfa", "ürünü", "ucuz alışveriş", "genel", "çeşitli", "ve", 
	"ürünler", "cm", "fa", "inç", "seti"};
	
	private static String STOP_WORDS_REGEX = "\\bana\\s*sayfa[\\wışğüçö]*\\b|\\bdiğer[\\wışğüçö]*\\b|\\bürün[\\wışğüçö]*\\b|\\baraba[\\wışğüçö]*\\b";
	
	private Locale locale = new Locale("tr");
	private Set<String> noisyWordMap = new HashSet<String>();
	private Zemberek zemberek = null;
	private Pattern nonTurkishWordRegEx = null;
	
	private NewWordsCreator newWordsCreator;
	
	private Set<String> predefinedTags = null;
	private Map<String, String> tagSynonyms = new HashMap<String, String>();
	
	public Tagger() {
		for(String noisyWord: NOISE_WORDS)
		{
			noisyWordMap.add(normalizeWordForNoisyMatch(noisyWord));
		}
		
		ZemberekFactory factory = new ZemberekFactory();
		zemberek = factory.createZemberek();
		nonTurkishWordRegEx = Pattern.compile("[^A-Za-zıİşŞğĞüÜçÇöÖ]");
	}

	public Tagger(NewWordsCreator newWordsCreator) {
		this();
		this.newWordsCreator = newWordsCreator;
	}

	public Tagger(boolean loadTags, boolean loadSynonyms) {
		this();
		if(loadTags)
		{
			loadTags();
		}
		
		if(loadSynonyms)
		{
			loadSynonyms();
		}
	}

	private void loadSynonyms() {
		try {
			InputStream is = StreamUtil.getStream("tag_synonyms.csv");
			LineIterator lineIterator = IOUtils.lineIterator(is, StringUtil.DEFAULT_ENCODING);
			while(lineIterator.hasNext())
			{
				String line = lineIterator.nextLine();
				String[] split = line.split("\t");
				String tag = split[0];
				for(int index=1;index<split.length; index++)
				{
					tagSynonyms.put(split[index], tag);	
				}
			}
			
			is.close();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadTags() {
		try {
			InputStream is = StreamUtil.getStream("tags.csv");
			predefinedTags = new HashSet<String>();
			LineIterator lineIterator = IOUtils.lineIterator(is, StringUtil.DEFAULT_ENCODING);
			while(lineIterator.hasNext())
			{
				String line = lineIterator.nextLine();
				String[] split = line.split("\t");
				String tag = split[0];
				predefinedTags.add(tag);
			}
			
			is.close();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<String> getPredefinedTags() {
		return predefinedTags;
	}

	public List<String> getTags(String breadcrumb)
	{
		return split(breadcrumb);
	}
	

	private List<String> split(String breadcrumb)
	{
		List<String> result = new LinkedList<String>();
		
		if(StringUtils.isNotBlank(breadcrumb))
		{
			breadcrumb = StringEscapeUtils.unescapeHtml(breadcrumb);
			breadcrumb = StringUtil.removeMultiEmptySpaces(breadcrumb);
			breadcrumb = breadcrumb.toLowerCase(locale);
			String[] split = breadcrumb.split(COMMON_BREADCRUMB_DELIMITERS);
			for(String str: split)
			{
				str = str.trim();
				if(StringUtils.isNotBlank(str))
				{
					//shall we handle "Bilg." ? 
					if(!isNoise(str))
					{
						//now we shall normalize the word according to language
						//Buzdolaplari --> Buzdolabı
						str = analyze(str);
						str = removeStopWords(str);
						if(StringUtils.isNotBlank(str))
						{
							String[] finalStr = str.split("\\s");
							if(finalStr.length < 4 && str.length()>1)
							{
								if(tagSynonyms.containsKey(str))
								{
									str = tagSynonyms.get(str);
								}
								//check permutations and predefined
								String strToBeReturned = str;
								if(predefinedTags != null && !predefinedTags.contains(str))
								{
									String permutatedTag = null;
									
									String[] subWords = str.split("\\s");
									if(subWords.length > 1)
									{
										Permute permute = new Permute(subWords);
										while(permute.hasNext())
										{
											String[] permutationArr = (String[]) permute.next();
											String permutation = StringUtils.join(permutationArr, " ");
											
											if(predefinedTags.contains(permutation))
											{
												permutatedTag = permutation;
												break;
											}
										}
									}
									
									if(permutatedTag != null)
									{
										strToBeReturned = permutatedTag;
									}
									else
									{
										strToBeReturned = null;
									}
									
								}
								
								if(strToBeReturned != null)
								{
									result.add(strToBeReturned);	
								}
							}
						}						
					}			
				}
			}
		}
		
		return result;
	}
	
	private String removeStopWords(String str) {
		if(str != null)
		{
			str = str.replaceAll(STOP_WORDS_REGEX, "");
			str = StringUtil.removeMultiEmptySpaces(str).trim();
		}
		
		return str;
	}

	private String normalizeWordForNoisyMatch(String str)
	{
		if(str != null)
		{
			str = StringUtil.convertTurkishCharacter(str);		
			str = StringUtil.toLowerCase(str);
			str = StringUtil.removePunctiations(str);			
		}
		return str;
	}
	
	
	private boolean isNoise(String str)
	{
		return noisyWordMap.contains(normalizeWordForNoisyMatch(str));
	}
	
	private String analyze(String str)
	{
		//split into tokens 
		String[] tokens = str.split("[\\s|\\.]");
		
		List<String> words = new ArrayList<String>();
		
		for(String token: tokens)
		{
			Matcher m = nonTurkishWordRegEx.matcher(str);
			
			Kelime word = null;
			Kelime[] producedWords = null;
			
			if (!m.find()) {
				producedWords = zemberek.asciiCozumle(token);			
			}
			else
			{
				producedWords = zemberek.kelimeCozumle(token);
			}
			
			if(producedWords != null && producedWords.length > 0)
			{
				word = getLongestWord(producedWords);
				addWord(words, word);
			} else {
				if(newWordsCreator != null && StringUtils.isNotBlank(token))
				{
					boolean newFoundWord = newWordsCreator.newFoundWord(token);
					
					if(newFoundWord && !words.contains(token.trim()))
					{
						words.add(token.trim());
					}
				}
			}
		}
		
		String result = StringUtils.join(words, " ");
		return result;
	}

	private void addWord(List<String> words, Kelime word) {
		if(word != null)
		{
			String root = getRoot(word);
			if(StringUtils.isNotBlank(root))
			{
				root = root.trim();
				root = TagNameComparator.clearPlural(root);
				if(!words.contains(root))
				{
					words.add(root);	
				}
			}
		}
	}

	private Kelime getLongestWord(Kelime[] producedWords) {
		Kelime longestWord = null;
		for (Kelime tmpWord : producedWords) {
			if (longestWord != null) {
				String tmpWordStr = tmpWord.kok().icerik();
				String tmpLongestStr = longestWord.kok().icerik();
				if (tmpWordStr.length() > tmpLongestStr.length()) {
					longestWord = tmpWord;
				}
				else if (tmpWordStr.length() == tmpLongestStr.length()) {
					if(tmpWord.ekler().size() > longestWord.ekler().size()){
						longestWord = tmpWord;
					}
					else if(tmpWord.ekler().size() == longestWord.ekler().size()){
						List<Ek> ekler = tmpWord.ekler();
						for(Ek ek: ekler)
						{
							if(ek.ad().contains("SAHIPLIK"))
							{
								longestWord = tmpWord;
								break;
							}							
						}						
					}
				}
			}
			else {
				longestWord = tmpWord;
			}
		}
		return longestWord;
	}
	
	private String getRoot(Kelime kelime)
	{
		String root = null;
		if(kelime != null)
		{			
			List<Ek> tmpEkler = kelime.ekler();
			List<Ek> ekler = new ArrayList<Ek>();
			
			if(tmpEkler != null && tmpEkler.size() > 0)
			{
				ekler.add(tmpEkler.get(0));
				for(int index = tmpEkler.size()-1; index > 0; index--)
				{
					Ek ek = tmpEkler.get(index);
					if(!isCekimEki(ek))
					{
						ekler.addAll(tmpEkler.subList(1, index+1));
						break;
					}
				}
				Ek ek = tmpEkler.get(tmpEkler.size()-1);
				if(ek.ad().endsWith("_I"))
				{
					ekler.add(ek);
				}
			}
			
			root = zemberek.kelimeUret(kelime.kok(), ekler);
		}
		
		return root;
	}
	
	private boolean isCekimEki(Ek ek)
	{
		boolean result = false;
		if(ek.ad().startsWith("ISIM_SAHIPLIK") || 
				ek.ad().startsWith("ISIM_COGUL") ||
				ek.ad().startsWith("ISIM_BELIRTME") ||
				ek.ad().startsWith("ISIM_YONELME") ||
				ek.ad().startsWith("ISIM_KUCULTME") ||
				ek.ad().startsWith("ISIM_CIKMA") ||
				ek.ad().startsWith("ISIM_KALMA") ||
				ek.ad().startsWith("ISIM_GIBI") ||
				ek.ad().startsWith("ISIM_TAMLAMA") ||
				ek.ad().startsWith("ISIM_BULUNMA") ||
				ek.ad().startsWith("ISIM_TARAFINDAN") ||
				ek.ad().startsWith("ISIM_DONUSUM") ||
				ek.ad().startsWith("ISIM_BIRLIKTELIK") ||
				ek.ad().startsWith("ISIM_KISI") ||
				ek.ad().startsWith("FIIL_EMIR") ||
				ek.ad().startsWith("FIIL_KISI") ||
				ek.ad().startsWith("ZAMIR_SAHIPLIK")
				){
			result = true;
		}
			
		return ek.halEkiMi() || ek.iyelikEkiMi() || result ;
	}
	
	public static void main(String[] args) {
		String[] data = new String[]{
				"Buzdolapları",
				"Buzdolabı",
				"emzirme",
				"Birimleri",
				"uyku seti",
				"uyku setleri",
			"Kurumsal Ürünler > Network Ürünü > xDSL",
			"Ana Sayfa Hobi & Oyun Ürünleri Hobi Ürünleri",
			"OEM & Çevre Birimleri > Web Kameralar",
			"Beyaz Eşya & Mutfak » Beyaz Eşya & Ankastre » Ankastre Ürünler »	",
			"Takı » TekbirSilver » Bey Pazarı Telkari » Küpe » TEKBIR SILVER",
			"Uyku Setleri -> Uyku Seti -> Daisy Tek Kişilik Uyku Seti(Mavi)",
			"Anne / Emzirme / Emzirme Minderi",
			"Ürün Özellikleri - REPLICA JANT » Toyota Jant",
			"Ana Sayfa Yazılım Ofis / Uygulama Yazılımları",
			"Anasayfa \\ Bilg.Parçaları \\ Bellekler"			
		};
		
		for(String test: data)
		{
			List<String> split = new Tagger().split(test);
			System.out.print(test + ":");
			for(String str: split)
			{
				System.out.print(str + ", ");
			}			
			System.out.println("");
		}
	}
}
