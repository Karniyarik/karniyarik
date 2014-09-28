package com.karniyarik.common.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.config.system.WebConfig;

public class IndexRefreshUtil
{
	public static void callWebIndexRefresh() throws Throwable
	{
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		
		URL aURL = null;
		URLConnection aURLConnection = null;
		String tail = "";
		
		tail = webConfig.getRefreshIndexServlet();
		
		String url = deploymentConfig.getMasterWebUrl();
		
		if(!tail.startsWith("/") && !url.endsWith("/"))
		{
			url += "/";	
		}

		url += tail;
		
		aURL = new URL(url);
		aURLConnection = aURL.openConnection();
		
		aURLConnection.connect();
		((HttpURLConnection) aURLConnection).getResponseCode();
		aURLConnection.getInputStream().close();
		
		aURL = null;
		aURLConnection = null;
		tail = null;
	}

}
