package com.karniyarik.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ExecutorUtil
{
	private static final long MAX_MINUTES_TO_WAIT = 50;

	public static final void shutDown(ExecutorService executor, String executorLogName) {
		executor.shutdown();
		try
		{
			// wait for executing task to complete
			if (!executor.awaitTermination(MAX_MINUTES_TO_WAIT, TimeUnit.MINUTES))
			{
				// executing tasks did not complete
				// cancel current tasks
				executor.shutdownNow();

				// after cancelling wait for termination
				if (!executor.awaitTermination(MAX_MINUTES_TO_WAIT, TimeUnit.MINUTES))
				{
					getLogger().error(
							"Could not shutdown " + executorLogName + " within wait limits");
				}
			}
		}
		catch (InterruptedException e)
		{
			// cancel current tasks
			executor.shutdownNow();

			// log the interruption
			getLogger().error(
					"Shutdown operation for " + executorLogName + " is interrupted." , e);

			// preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	private static Logger getLogger()
	{
		return Logger.getLogger(ExecutorUtil.class.getName());
	}

}
