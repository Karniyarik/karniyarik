package com.karniyarik.common.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.system.DeploymentConfig;
import com.karniyarik.common.config.system.WebConfig;
import com.karniyarik.common.log.KarniyarikLogger;

public class SitemapRefreshCall
{
	public static void callWebSitemapRefresh()
	{
		WebConfig webConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getWebConfig();
		DeploymentConfig deploymentConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig();
		
		String refreshSitemapServlet = webConfig.getRefreshSitemapServlet();
		
		
		List<String> urlList = new ArrayList<String>();
		urlList.add(deploymentConfig.getMasterWebUrl());
		//urlList.add(deploymentConfig.getSlaveWebUrl());
		
		for(String url: urlList)
		{
			try
			{				  
				URL aURL = new URL(url + "/" + refreshSitemapServlet);
				
				URLConnection aURLConnection = aURL.openConnection();
				aURLConnection.connect();
				((HttpURLConnection) aURLConnection).getResponseCode();
			}
			catch (Throwable e)
			{
				KarniyarikLogger.logExceptionSummary(
						"Cannot call refresh servlet.", e, Logger
								.getLogger(SitemapRefreshCall.class));
			}			
		}
	}

	public static void main(String[] args)
	{
		SitemapRefreshCall.callWebSitemapRefresh();
	}

}
