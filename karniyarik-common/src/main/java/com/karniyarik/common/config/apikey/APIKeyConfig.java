package com.karniyarik.common.config.apikey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

@SuppressWarnings("serial")
public class APIKeyConfig extends ConfigurationBase
{
	private Map<String, APIKey>	keyToConfigMap	= new HashMap<String, APIKey>();

	public APIKeyConfig() throws Exception
	{
		super(ConfigurationURLUtil.getAPIKeyConfig());
		loadAPIKeyConfigList();
	}

	@SuppressWarnings("unchecked")
	private void loadAPIKeyConfigList()
	{
		APIKey key = null;

		List aList = getList("key[@value]");

		String aPath = null;
		for (int anIndex = 0; anIndex < aList.size(); anIndex++)
		{
			aPath = "key(" + anIndex + ")";

			key = new APIKey(configurationAt(aPath));

			keyToConfigMap.put(key.getValue(), key);
		}
	}

	public Set<String> getAPIKeys()
	{
		return keyToConfigMap.keySet();
	}
	
	public APIKey getAPIKey(String name)
	{
		return keyToConfigMap.get(name);
	}

}