package com.karniyarik.common.util;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.log.KarniyarikLogger;
import com.karniyarik.common.statistics.vo.SiteStat;
import com.sun.jersey.api.client.AsyncWebResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.AsyncWebResource.Builder;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class JerseyUtil
{
	private static Logger log = Logger.getLogger(JerseyUtil.class);
	
	public static void sendFormPost(String endPoint, Map<String, String> parameters)
	{
		try
		{
			Client client = createClient();
			WebResource webResource = client.resource(endPoint);
			webResource.accept(MediaType.APPLICATION_FORM_URLENCODED);
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			for (String name : parameters.keySet())
			{
				params.add(name, parameters.get(name));
			}
			String result = webResource.post(String.class, params);
			System.out.println(result);
		}
		catch (Exception e)
		{
			KarniyarikLogger.logExceptionSummary("cannot call REST service (method: Form Post, endpoint: " + endPoint +")", e, log);
		}
	}

	public static void sendFormPut(String endPoint, Map<String, String> parameters)
	{
		try
		{
			Client client = createClient();
			AsyncWebResource webResource = client.asyncResource(endPoint);
			webResource.accept(MediaType.APPLICATION_FORM_URLENCODED);
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			for (String name : parameters.keySet())
			{
				params.add(name, parameters.get(name));
			}
			webResource.put(String.class, params);
		}
		catch (Exception e)
		{
			KarniyarikLogger.logExceptionSummary("cannot call REST service (method: Form Put, endpoint: " + endPoint +")", e, log);
		}
	}

	public static void sendJSONPut(String endPoint, Object vo)
	{
		sendJSONPut(endPoint, vo, true);
	}

	public static void sendJSONPut(String endPoint, Object vo, boolean async)
	{
		sendJSONPut(endPoint, vo, async, 10000);
	}
	
	public static void sendJSONPut(String endPoint, Object vo, boolean async, int timeout)
	{
		sendJSONPut(endPoint, vo, async, timeout, MediaType.APPLICATION_JSON + ";charset=UTF-8");
	}
	
	public static void sendJSONPut(String endPoint, Object vo, boolean async, int timeout, String mediaType)
	{
		try
		{
			Client client = createClient(timeout);
			/**
			 * Async web resource used because of search logs.
			 * Each search call uses this method
			 * User servlet threads should not wait here
			 */
			
			if(async){
				AsyncWebResource asyncResource = client.asyncResource(endPoint);
				Builder type = asyncResource.type(mediaType);
				type.put(vo);
			}
			else
			{
				WebResource webResource = client.resource(endPoint);
				com.sun.jersey.api.client.WebResource.Builder type = webResource.type(mediaType);
				type.put(vo);
			}
		}
		catch (Exception e)
		{
			KarniyarikLogger.logExceptionSummary("cannot call REST service (method: Put, endpoint: " + endPoint +")", e, log);
		}		
	}
	
	public static <T> T getObject(String endPoint, Map<String, String> parameters, Class<T> aClass, int timeout)
	{
		T t = null;
		
		Client client = createClient();
		
		try
		{
			WebResource webResource = client.resource(endPoint);
			client.setReadTimeout(timeout);
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();
			for (String name : parameters.keySet())
			{
				params.add(name, parameters.get(name));
			}
			ClientResponse response = webResource.queryParams(params).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

			if (Status.OK.getStatusCode() == response.getStatus())
			{
				t = response.getEntity(aClass);
			}
		}
		catch (Exception e)
		{
			KarniyarikLogger.logExceptionSummary("cannot call REST service (method: Get, endpoint: " + endPoint +")", e, log);
		}

		return t;		
	}

	public static <T> T getObject(String endPoint, Map<String, String> parameters, Class<T> aClass)
	{
		return getObject(endPoint, parameters, aClass, 10000);
	}

	private static Client createClient() {
		return createClient(-1);
	}
	
	private static Client createClient(int timeout) {
		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		if(timeout < 0)
		{
			timeout = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getURLFetchTimeout(); 
		}
		
		client.setConnectTimeout(timeout);
		client.setReadTimeout(KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getCrawlerConfig().getURLFetchTimeout());
		return client;
	}
	
	public static void main(String[] args)
	{
		Locale.setDefault(new Locale("en"));
		String endpoint = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getDeploymentConfig().getStatisticsDeploymentURL();
		SiteStat stat = new SiteStat();
		stat.setDatafeed(true);
		stat.setDate(new Date().getTime());
		stat.setProductCount(310);
		stat.setSiteName("dermaderm");
		JerseyUtil.sendJSONPut(endpoint + "/rest/api/sitestat", stat);
	}
	
}