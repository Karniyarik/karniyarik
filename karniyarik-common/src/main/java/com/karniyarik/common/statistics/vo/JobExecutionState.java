package com.karniyarik.common.statistics.vo;

public enum JobExecutionState
{
	//idle state
	IDLE(true, false, false, false, false), 
	
	//running state
	CRAWLING(false, true, false, false, false),
	
	//paused state
	CRAWLING_PAUSING(false, false, true, false, false),
	CRAWLING_PAUSED(false, false, true, false, false),
	
	//running state
	CRAWLER_RESUMING(false, true, false, false, false),
	CRAWLING_ENDING(false, true, false, false, false), 
	CRAWLING_ENDED(false, true, false, false, false),
	
	//failed state
	CRAWLING_FAILED(false, false, false, false, true),
	
	//running state
	RANKING(false, true, false, false, false), 
	RANKING_ENDED(false, true, false, false, false),
	
	//failed state
	RANKING_FAILED(false, false, false, false, true),
	
	//running state
	INDEXING(false, true, false, false, false), 
	INDEXING_ENDED(false, true, false, false, false), 

	//failed state
	INDEXING_FAILED(false, false, false, false, true),
	
	//running state
	PUBLISHING(false, true, false, false, false), 
	PUBLISHING_ENDED(false, true, false, false, false),
	
	//failed state
	PUBLISHING_FAILED(false, false, false, false, true),
	
	//running state
	CALLING_MERGE_SERVLETS(false, true, false, false, false), 
	
	//running state
	MERGE_CALLS_ENDED(false, true, false, false, false),
	
	//ended state
	MERGE_CALLS_FAILED(false, false, false, true, false),
	
	//ended state
	ENDED(false, false, false, true, false);
	
	private boolean idle = false;
	private boolean failed = false;
	private boolean running = false;
	private boolean paused = false;
	private boolean ended = false;
	
	private JobExecutionState(boolean isIdleState, boolean isRunningState, boolean isPauseState, boolean isEndedState, boolean isFailState) 
	{
		this.failed = isFailState;
		this.running = isRunningState;
		this.paused = isPauseState;
		this.ended = isEndedState;
		this.idle = isIdleState;
	}

	public boolean isIdle()
	{
		return idle;
	}

	public boolean isRunning()
	{
		return running;
	}

	public boolean hasPaused()
	{
		return paused;
	}

	public boolean hasEnded()
	{
		return ended;
	}

	public boolean hasFailed()
	{
		return failed;
	}

}
