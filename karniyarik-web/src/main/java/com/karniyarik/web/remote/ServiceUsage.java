package com.karniyarik.web.remote;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.apikey.APIKeyConfig;

public class ServiceUsage
{
	private Map<String, KeyUsage>	keyUsageMap	= null;
	private static ServiceUsage		instance	= null;

	private ServiceUsage()
	{
		keyUsageMap = Collections.synchronizedMap(new HashMap<String, KeyUsage>());

		APIKeyConfig apiKeyConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getApiKeyConfig();
		Set<String> apiKeys = apiKeyConfig.getAPIKeys();

		for (String key : apiKeys)
		{
			keyUsageMap.put(key, new KeyUsage(apiKeyConfig.getAPIKey(key)));
		}
	}

	public static ServiceUsage getInstance()
	{
		if (instance == null)
		{
			instance = new ServiceUsage();
		}
		return instance;
	}

	public boolean newRequest(String key)
	{
		KeyUsage keyUsage = keyUsageMap.get(key);

		if (keyUsage == null)
		{
			return false;
		}
		
		return keyUsage.increaseRequestByOne();
	}
}