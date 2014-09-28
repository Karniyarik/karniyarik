package com.karniyarik.dev;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.config.site.SitesConfig;

public class SiteXLSGenerator {
	
	public static void main(String[] args) throws Throwable{
		SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
		File file = new File("sites.csv");
		if(file.exists())
		{
			file.delete();
		}
		file.createNewFile();
		
		List<String> lines = new LinkedList<String>();
		String line= "Sitename\tURL\tDatafeed\tSelenium";
		lines.add(line);
		for(SiteConfig config: sitesConfig.getSiteConfigList())
		{
			line= config.getSiteName() + "\t" + config.getUrl() + "\t" +config.isDatafeed() + "\t" + config.isSelenium();
			lines.add(line);
		}
		
		IOUtils.writeLines(lines, "\n", new FileOutputStream(file));
	}
}
