package com.karniyarik.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.karniyarik.search.searcher.autocomplete.AutoCompleteEngine;

public class AutoCompleteRefreshJob implements Job {
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		AutoCompleteEngine.getInstance().refresh();
	}
}
