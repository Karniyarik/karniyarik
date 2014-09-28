package com.karniyarik.controller;

public enum SiteControllerState
{
	IDLE("Idle"), CRAWLING("Crawling"), RANKING("Ranking"), INDEXING("Indexing"), CLEANING("Cleaning"), ENDED("Ended"), FAILED("Failed");

	private final String	str;

	private SiteControllerState(String str)
	{
		this.str = str;
	}

	@Override
	public String toString()
	{
		return str;
	}
}
