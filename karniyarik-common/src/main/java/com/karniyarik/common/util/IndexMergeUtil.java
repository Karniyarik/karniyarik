package com.karniyarik.common.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.config.system.WebConfig;

public class IndexMergeUtil
{
	public static final String	SITE_NAME_PARAMETER	= "s";

	public static final void callMergeSiteIndex(String siteName) throws Throwable
	{
		callMergeSiteIndex(siteName, false);
	}
	
	public static final void callMergeSiteIndex(String siteName, boolean reduceBoost) throws Throwable
	{
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
		DeploymentConfig config = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		//String url = "http://www.karniyarik.com";
		String url = config.getMasterWebUrl();
		URL servletURL = null;
		URLConnection connection = null;
		InputStream is = null;

		String tail = webConfig.getMergeIndexServlet() + "?" + SITE_NAME_PARAMETER + "=" + siteName;

		if (StringUtils.isNotBlank(url))
		{
			if(!tail.startsWith("/") && !url.endsWith("/"))
			{
				url += "/";	
			}

			url += tail;
			
			if(reduceBoost)
			{
				url += "&rb=true";
			}
			
			servletURL = new URL(url);
			connection = servletURL.openConnection();

			connection.connect();
			is = connection.getInputStream();
			is.close();
		}

		servletURL = null;
		connection = null;
		is = null;
		tail = null;

	}

	public static void callReduceSiteIndex(String siteName) throws Throwable{
		callMergeSiteIndex(siteName, true);
	}

	public static void main(String[] args) throws Throwable{
		String[] sites = new String[]{
				"hataystore",
				"damakzevki", "robertopirlanta", "bebekken", "elektrikmalzemem", "starsexshop", "altinsarrafi", "budatoys", "taffybaby", "medikalcim", "beyazdepo", "tasarimbookshop", "boviza",
				"evdepo", "bonnyfood", "beyazkutu", "koctas", "bizimmarket", "narbebe", "gonayakkabi", "tgrtpazarlama", "pasabahce", "vatanbilgisayar", "egerate-store", "dr", "hipernex", "ensarshop",
				"yesil", "dealextreme", "petsrus", "otoyedekparcaburada", "elektrikdeposu", "alisveris", "radikalteknoloji", "ekopasaj", "strawberrynet", "yenisayfa", "adresimegelsin",
				"juenpetmarket", "nadirkitap"};
		
		for(String site: sites)
		{
			System.out.println(site);
			callMergeSiteIndex(site);
			Thread.sleep(10000);
		}
	}
}
