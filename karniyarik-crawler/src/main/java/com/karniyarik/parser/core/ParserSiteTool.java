package com.karniyarik.parser.core;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.ExecutorUtil;
import com.karniyarik.crawler.linkgraph.VisitedLinksList;
import com.karniyarik.parser.base.XQueryParser;

public class ParserSiteTool
{

	private JobExecutionStat		statistics;
	private ExecutorService			executor;
	private XQueryParser			parser;
	private SiteProductRegistery	registery;
	private SiteProductBuffer		buffer;

	public ParserSiteTool(XQueryParser parser, SiteProductRegistery productRegistery, SiteProductBuffer productBuffer, JobExecutionStat crawlerStatistics)
	{
		executor = Executors.newCachedThreadPool();
		this.parser = parser;
		this.registery = productRegistery;
		this.buffer = productBuffer;
		this.statistics = crawlerStatistics;
	}

	public void parse(String url, String shortUrl, String content, Date fetchDate, VisitedLinksList visitedLinksList)
	{
		executor.execute(new ParseTask(url, shortUrl, content, fetchDate, parser, registery, buffer, statistics, visitedLinksList));
	}

	public void shutDownAndSave()
	{
		ExecutorUtil.shutDown(executor, statistics.getSiteName() + " parser executor");
		buffer.flush();
		registery.clear();
	}

	public ExecutorService getExecutor()
	{
		return executor;
	}

	public XQueryParser getParser()
	{
		return parser;
	}

	public SiteProductRegistery getRegistery()
	{
		return registery;
	}

	public SiteProductBuffer getBuffer()
	{
		return buffer;
	}

}
