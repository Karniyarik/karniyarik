package com.karniyarik.common.config.system;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

@SuppressWarnings("serial")
public class MailConfig extends ConfigurationBase
{
	public MailConfig() throws Exception
	{
		super(ConfigurationURLUtil.getMailConfig());
	}

	public String getFrom()
	{
		return getString("mail.from", "");	
	}

	public String getToString()
	{		
		return getString("mail.to", "");
	}
	
	public String[] getTo()
	{		
		String to = getString("mail.to", "");
		return to.split(",");		
	}
	
	public String getHostName()
	{
		return getString("mail.hostname", "");
	}
	
	public String getUser()
	{
		return getString("mail.user", "");
	}
	
	public String getPassword()
	{
		return getString("mail.password", "");	
	}
	
	public String getStorage() {
		return getString("mail.storage", "");
	}
	
	public boolean isUseSSL()
	{
		return getBoolean("mail.ssl", true);
	}

	
	public void setFrom(String value)
	{
		setProperty("mail.from", value);	
	}
	
	public void setTo(String value)
	{		
		setProperty("mail.to", value);
	}
	
	public void setHostName(String value)
	{
		setProperty("mail.hostname", value);
	}
	
	public void setUser(String value)
	{
		setProperty("mail.user", value);
	}
	
	public void setPassword(String value)
	{
		setProperty("mail.password", value);	
	}
	
	public void setUseSSL(boolean value)
	{
		setProperty("mail.ssl", value);	
	}
	
	public void setEnabled(boolean value)
	{
		setProperty("mail.enabled", value);	
	}

	public boolean isEnabled()
	{
		return getBoolean("mail.enabled", true);	
	}
}