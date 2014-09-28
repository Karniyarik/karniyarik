package com.karniyarik.crawler.state;


public interface CrawlerState
{
	public final int	IDLE_STATE_ID		= 0;
	public final int	CRAWLING_STATE_ID	= 1;
	public final int	PAUSED_STATE_ID		= 2;
	public final int	ENDED_STATE_ID		= 3;

	public void start();

	public void pause();

	public void stop();

	public String popLinkToVisit();

	public void handleExcessiveExceptions(String message);
}
