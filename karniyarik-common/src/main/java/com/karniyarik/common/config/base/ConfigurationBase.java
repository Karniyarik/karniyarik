package com.karniyarik.common.config.base;

import java.net.URL;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * Base configuration class. All other configurations are extended from this
 * class. (changed for fake commit, 15.05.2008, you're in the army now. :))
 * 
 * @author siyamed
 * @author meralan
 * 
 */
@SuppressWarnings("serial")
public class ConfigurationBase extends XMLConfiguration implements ConfigurationListener
{
	/**
	 * The Apache Commons Configuration Object.
	 */
	//private XMLConfiguration	configuration	= null;
	
	public ConfigurationBase(URL configurationURL) throws Exception
	{
		super(configurationURL);
		setEncoding("UTF-8");
		setReloadingStrategy(new FileChangedReloadingStrategy());
		addConfigurationListener(this);
	}

	/**
	 * Check if the configuration item identified with the name given is empty
	 * or not.
	 * 
	 * @param configId
	 *            The name of the configuration item.
	 * 
	 * @return True if the item is null or has empty value.
	 */
	protected boolean isConfigEmpty(String configId)
	{
		if (getString(configId) == null
				|| getString(configId).equals(""))
			return true;
		return false;
	}

	@Override
	public void configurationChanged(ConfigurationEvent aEvent)
	{
		
	}
}
