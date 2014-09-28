package com.karniyarik.common.config.system;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

@SuppressWarnings("serial")
public class StatisticsConfig extends ConfigurationBase
{
	private final String STATISTICS_DIR	= "C:/krnyrk/statistics";

	public StatisticsConfig() throws Exception
	{
		super(ConfigurationURLUtil.getStatisticsConfig());
	}

	public String getStatisticsDirectory()
	{
		return getString("statistics[@dir]", STATISTICS_DIR);
	}
}
