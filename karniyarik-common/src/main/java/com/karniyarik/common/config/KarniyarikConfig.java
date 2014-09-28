package com.karniyarik.common.config;

import com.karniyarik.common.config.base.ConfigurationBundle;

/**
 * Main configuration object that includes of all other configuration objects.
 * Stored in the singleton repository.
 * 
 * @author siyamed
 * @author meralan
 * 
 */
public class KarniyarikConfig {
	private ConfigurationBundle configurationBundle = null;

	public KarniyarikConfig() {
		configurationBundle = new ConfigurationBundle();
		initURLConnection();
	}

	private void initURLConnection() {
		// TODO: get from crawler config or whatever
		System.setProperty("http.keepAlive", "true");
		System.setProperty("http.maxConnections", "20");
	}

	public ConfigurationBundle getConfigurationBundle() {
		return configurationBundle;
	}

}
