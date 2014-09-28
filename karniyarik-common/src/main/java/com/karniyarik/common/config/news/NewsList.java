package com.karniyarik.common.config.news;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.event.ConfigurationEvent;

import com.karniyarik.common.config.base.ConfigurationBase;
import com.karniyarik.common.util.ConfigurationURLUtil;

public class NewsList extends ConfigurationBase {
	
	private List<News> newsList = new ArrayList<News>();
	
	public NewsList() throws Exception {
		super(ConfigurationURLUtil.getNewsConfig());
		init();
	}

	private void init() 
	{
		newsList.clear();
		News news = null;
		List aList = getList("news[@date]");
		String aPath = null;
		for (int anIndex = 0; anIndex < aList.size(); anIndex++)
		{
			aPath = "news(" + anIndex + ")";

			news = new News(configurationAt(aPath));
			newsList.add(news);
		}
	}
	
	public List<News> getNewsList() {
		return newsList;
	}
	
	@Override
	public void configurationChanged(ConfigurationEvent aEvent) {
		super.configurationChanged(aEvent);
		init();
	}
}
