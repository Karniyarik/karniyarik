package com.karniyarik.crawler.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.karniyarik.common.util.StringUtil;

public class RobotsTxtAnalyzer {

	private static final String ROBOTS_TXT = "robots.txt";
	private List<String> disallowedUrlList;
	private String robotsTxtContent;
	private String karniyarikBotName;

	//should only be called in an exceptional case
	//just creates an empty ignore list
	public RobotsTxtAnalyzer() {

		disallowedUrlList = new ArrayList<String>();
	}

	//fetch robots.txt file from given home page url
	public RobotsTxtAnalyzer(String url, String botName, int fetchTimeout) {
		init(url, botName, fetchTimeout);
	}

	private void init(String url, String botName, int fetchTimeout) {

		try {
			robotsTxtContent = fetchRobotsTxtContent(url, botName, fetchTimeout);
		}
		catch (FileNotFoundException aFileNotFoundException) {
		
		}
		catch (IOException e) {
			
		}
		catch (Exception e) {

		}

		if (robotsTxtContent != null) {
			extractRules(url);
		}
	}

	private String fetchRobotsTxtContent(String url, String botName, int fetchTimeout)
	throws FileNotFoundException, IOException {

		String result = null;

		karniyarikBotName = botName;

		URLConnection aURLConnection = null;
		disallowedUrlList = new ArrayList<String>();

		URL realUrl = new URL(createRobotsTxtUrl(url));

		aURLConnection = realUrl.openConnection();

		aURLConnection.setConnectTimeout(fetchTimeout);
		aURLConnection.setDefaultUseCaches(false);
		aURLConnection.setUseCaches(false);
		aURLConnection.setRequestProperty("Cache-Control","max-age=0,no-cache");
		aURLConnection.setRequestProperty("Pragma", "no-cache");

		aURLConnection.connect();

		InputStream aStream = aURLConnection.getInputStream();

		result = IOUtils.toString(aStream, StringUtil.DEFAULT_ENCODING);

		aStream.close();

		return result;
	}

	private void extractRules(String url) {

		extractRules(robotsTxtContent, url);
	}

	public void extractRules(String robotsTxtContent, String url) {

		String[] lines = robotsTxtContent.split("\n");
		List<String> generalRules = new ArrayList<String>();
		List<String> specificRules = new ArrayList<String>();

		for (int i = 0; i < lines.length; i++) {

			String line = lines[i].trim();

			//robots.txt consists of a single line
			//ex: User-agent: *
			if (lines.length == 1) {
				
				continue;
			}

			if (line.contains("User-agent")) {

				String ruleLine = lines[i + 1].trim();
				
				while (!ruleLine.equals(StringUtils.EMPTY) && !ruleLine.contains("User-agent")) {
					
					String rule = StringUtils.EMPTY;
					
					if (ruleLine.split(":").length == 2) {
						rule = ruleLine.split(":")[1].trim();
					}
					
					if ((ruleLine.split(":")[0].trim().equalsIgnoreCase("Disallow"))) {
						
						if (isGeneralAgent(line)) {
							generalRules.add(rule);
						}
						else if (spesificAgentName(line)) {
							specificRules.add(rule);
						}
					}
					//not in the standard but some use as convention
					//however not implemented in here yet
					
					i++;
					
					if ((i + 1) < lines.length) {
						ruleLine = lines[i + 1].trim();
					}
					else {
						break;
					}
				}
			}
		}

		for (String rule : specificRules) {

			//crawl nothing
			if (rule.equals("/")) {
				disallowedUrlList.clear();
				disallowedUrlList.add(url);
				return;
			}
			//crawl everything
			else if (rule.equals(StringUtils.EMPTY)){
				disallowedUrlList.clear();
				return;
			}
			else {
				disallowedUrlList.add(rule);
			}
		}

		for (String rule : generalRules) {

			//crawl nothing
			if (rule.equals("/")) {
				disallowedUrlList.clear();
				disallowedUrlList.add(url);
				return;
			}
			//crawl everything other than specific rules
			else if (rule.equals(StringUtils.EMPTY)) {
				return;
			}
			else if (!disallowedUrlList.contains(rule)) {
				disallowedUrlList.add(rule);
			}
		}
	}

	private boolean spesificAgentName(String line) {
		boolean result = Boolean.FALSE;

		try {
			String agentName = line.split(":")[1].trim();

			if (agentName.equals(karniyarikBotName)) {
				result = Boolean.TRUE;
			}
		}
		catch (Exception e) {
			result = Boolean.FALSE;
		}

		return result;
	}

	private boolean isGeneralAgent(String line) {
		boolean result = Boolean.FALSE;

		try {
			String agentName = line.split(":")[1].trim();

			if (agentName.equals("*")) {
				result = Boolean.TRUE;
			}
		}
		catch (Exception e) {
			result = Boolean.FALSE;
		}

		return result;
	}

	private String createRobotsTxtUrl(String url) {

		if (!url.endsWith("/")) {

			url += "/";
		}

		return (url + ROBOTS_TXT);
	}

	public List<String> getDisallowedUrlList() {
		return disallowedUrlList;
	}
}
