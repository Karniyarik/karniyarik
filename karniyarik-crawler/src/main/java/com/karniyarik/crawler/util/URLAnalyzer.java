package com.karniyarik.crawler.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.UrlValidator;

import com.karniyarik.common.util.URLUtil;

public class URLAnalyzer
{
	private static final String[] schemes = new String[] {"http","https"};

	private UrlValidator validator	= null;
	private URLUtil	urlUtil = null;

	private static final String trapRegEx = ".*(/.+?).*?\\1.*?\\1/";
	private final Pattern trapPattern = Pattern.compile(trapRegEx); 

	public URLAnalyzer()
	{
		validator = new UrlValidator(schemes);
		urlUtil = new URLUtil();
	}

	public String clean(String aURL)
	{
		if (aURL != null)
		{
			aURL = aURL.trim();

			aURL = aURL.replaceAll(" ", "+");

			aURL = StringEscapeUtils.unescapeHtml(aURL);

			if(!aURL.endsWith("/?"))
			{
				if (aURL.endsWith("?"))
				{
					aURL = aURL.substring(0, aURL.length() - 1);
				}
			}
			
			aURL = cleanEmptyParams(aURL);

		}
		return urlUtil.normalize(aURL);
	}

	public Set<String> cleanUrlSet(Set<String> urlSet) {

		Set<String> result = new TreeSet<String>();

		for (String url : urlSet) {
			result.add(clean(url));
		}

		return result;
	}

	public URL getURL(String aURLString)
	{
		try
		{
			return new URL(aURLString);
		}
		catch (Throwable e)
		{
			throw new RuntimeException("Cannot convert "
					+ aURLString + " to URL");
		}
	}

	public boolean isHostsEqual(String aURLString, String aSecondURLString)
	{
		try
		{
			URL aMainURL = getURL(aURLString);
			URL aSecondURL = getURL(aSecondURLString);

			return aMainURL.getHost().equalsIgnoreCase(aSecondURL.getHost());
		}
		catch (Throwable e)
		{
			return false;
		}
	}

	public String toString(URL aURL)
	{
		return aURL.toExternalForm();
	}

	public boolean isValid(String aURLString)
	{
		return validator.isValid(aURLString);
	}
	
	public static Map<String, String> getQueryParameters(String aURL)
	{
		return getQueryParameters(aURL, false);
	}
	
	public static Map<String, String> getQueryParameters(String aURL, boolean lowercase)
	{
		Map<String, String> aResult = new HashMap<String, String>();
		
		if(StringUtils.isNotBlank(aURL))
		{
			StringTokenizer st = new StringTokenizer(aURL, "?&=", true);

			String previous = null;

			while (st.hasMoreTokens())
			{
				String current = st.nextToken();

				if ("?".equals(current) || "&".equals(current))
				{
					// ignore
				}
				else if ("=".equals(current))
				{
					String aValue = null;

					try
					{
						aValue = st.nextToken();
						if("?".equals(aValue) || "&".equals(aValue))
							aValue = null;
					}
					catch (Throwable e)
					{
						// do nothing
					}

					if(lowercase)
					{
						previous = previous.toLowerCase(Locale.ENGLISH);
					}
					
					aResult.put(previous, aValue);
				}
				else
				{
					previous = current;
				}
			}			
		}
		return aResult;
	}
	
	public static String cleanEmptyParams(String aURL)
	{
		if(aURL.contains("?"))
		{
			Map<String,String> aParams = getQueryParameters(aURL);
			
			if(aParams != null && aParams.size() > 0)
			{
				String aHost = aURL.substring(0,aURL.lastIndexOf("?")+1);
				
				String aResult = "";
				
				for(String aParam: aParams.keySet())
				{
					if(StringUtils.isNotBlank(aParams.get(aParam)))
						aResult += aParam + "=" + aParams.get(aParam) + "&";
				}
				
				if(StringUtils.isNotBlank(aResult))
				{
					aResult = aResult.substring(0,aResult.length()-1);	
				}
				
				return aHost + aResult;				
			}
			
			return aURL;
		}
		return aURL;
	}

	public static String removeParam(String aURL, String aParamToBeRemoved)
	{
		if(aURL.contains("?"))
		{
			Map<String,String> aParams = getQueryParameters(aURL);
			
			String aHost = aURL.substring(0,aURL.lastIndexOf("?")+1);
			
			String aResult = "";
			
			for(String aParam: aParams.keySet())
			{
				if(!aParam.equalsIgnoreCase(aParamToBeRemoved))
					aResult += aParam + "=" + aParams.get(aParam) + "&";
			}
			
			aResult = aResult.substring(0,aResult.length()-1);
			
			return aHost + aResult;
		}
		return aURL;
	}

	public Set<String> removeTraps(Set<String> linkSet) {

		Set<String> result = new TreeSet<String>();

		Matcher trapMatcher = null;

		for (String link : linkSet) {

			trapMatcher = trapPattern.matcher(link);

			if (!trapMatcher.find()) {
				result.add(link);
			}
		}

		return result;
	}

	public static void main(String[] args)
	{
//		String a = new URLAnalyzer().clean("http://www.ceplen.com/?urun-detay_464_Motorola_Rokr_Z6_Cep_Telefonu_.html");
//		String b = new URLAnalyzer().cleanEmptyParams("http://www.ceplen.com/?urun-detay_464_Motorola_Rokr_Z6_Cep_Telefonu_.html");
//		String c = new URLAnalyzer().cleanEmptyParams("http://www.ceplen.com/catinfo.asp?offset=21&kactane=21&cid=62&src=&mrk=&grp=&cmp=&brw=&stock=&typ=&order=&direction=");
//		String d = new URLAnalyzer().clean("http://www.vatanbilgisayar.com/toshiba-nb100-125-intel-atom-n270-1.6ghz-1024mb-160gb-8.9-%3Eentegre-vga-cam-xp-home/productdetails.aspx?I_ID=36826");
//		
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);
//		System.out.print(d);

		Set<String> trappingUrlSet = new TreeSet<String>();
		trappingUrlSet.add("/kitap/virgulden/www.ideefixe.com/www.ideefixe.com/www.ideefixe.com/www.ideefixe.com/tanim.asp?sid=KQEVF8BY1E5E139I02XO");
		trappingUrlSet.add("/kitap/virgulden/www.ideefixe.com/www.ideefixe.com/www.ideefixe.com/tanim.asp?sid=KQEVF8BY1E5E139I02XO");
		trappingUrlSet.add("xxx/urun/deneme/urun/deneme/urun/deneme/urun.asp?id=12");
		trappingUrlSet.add("xxx/urun/yyy/urun/bbb/urun/fff/urun.asp?id=12");
		trappingUrlSet.add("xxx/ali/ahmet/aslan/urun.asp?id=12");
		trappingUrlSet.add("xxx/deneeme/urun.asp?id=12");
		trappingUrlSet.add("/kitap/virgulden/www.ideefixe.com/tanim.asp?sid=KQEVF8BY1E5E139I02XO");

		URLAnalyzer ua = new URLAnalyzer();
		System.out.println("original url set");
		for (String url : trappingUrlSet) {
			System.out.println("url: " + url);
		}
		System.out.println("removing traps...");
		System.out.println("new url set");
		trappingUrlSet = ua.removeTraps(trappingUrlSet);
		for (String url : trappingUrlSet) {
			System.out.println("url: " + url);
		}
	}
}