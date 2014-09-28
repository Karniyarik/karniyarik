package com.karniyarik.jobscheduler.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.util.JSONUtil;
import com.karniyarik.common.util.StringUtil;

@SuppressWarnings("unchecked")
public class JobExecutionStatDao
{

	private final String	siteName;
	private final File		jobExecutionStatFile;

	public JobExecutionStatDao(String siteName, File jobExecutionStatFile)
	{
		this.siteName = siteName;
		this.jobExecutionStatFile = jobExecutionStatFile;
	}

	JobExecutionStat getCurrentStat()
	{
		JobExecutionStat stat = null;

		try
		{
			LineIterator iterator = FileUtils.lineIterator(jobExecutionStatFile);
			String line = "";
			while (iterator.hasNext())
			{
				line = iterator.nextLine();
			}
			LineIterator.closeQuietly(iterator);

			if (StringUtils.isNotBlank(line))
			{
				stat = JSONUtil.parseJSON(line, JobExecutionStat.class);
			}
			else
			{
				stat = new JobExecutionStat();
				stat.setSiteName(siteName);
				insertStat(stat);
			}
		}
		catch (Throwable e)
		{
			getLogger().error("Can not read current job execution stat from " + jobExecutionStatFile.getAbsolutePath(), e);
		}

		return stat;
	}

	public void insertStat(JobExecutionStat stat)
	{
		try
		{
			FileWriter writer = new FileWriter(jobExecutionStatFile, Boolean.TRUE);
			writer.write(JSONUtil.getJSON(stat) + "\n");
			writer.flush();
			writer.close();
			JobExecutionStatCache.getInstance().insertStat(stat);
		}
		catch (Throwable e)
		{
			getLogger().error("Can not insert job execution stat", e);
		}
	}

	public void updateCurrentStat(JobExecutionStat stat)
	{
		try
		{
			List<String> lines = FileUtils.readLines(jobExecutionStatFile, StringUtil.DEFAULT_ENCODING);
			lines.set(lines.size() - 1, JSONUtil.getJSON(stat));
			FileUtils.writeLines(jobExecutionStatFile, StringUtil.DEFAULT_ENCODING, lines);
			JobExecutionStatCache.getInstance().updateStat(stat);
		}
		catch (Throwable e)
		{
			getLogger().error("Can not update job execution stat", e);
		}
	}

	private Logger getLogger()
	{
		return Logger.getLogger(this.getClass().getName());
	}

	List<JobExecutionStat> getStats(int count)
	{
		LinkedList<JobExecutionStat> result= new LinkedList<JobExecutionStat>();

		try
		{
			List lines = FileUtils.readLines(jobExecutionStatFile);
			int realCount = count > lines.size() ? lines.size() : count;
			List<String> subList = lines.subList(lines.size()-realCount, lines.size());
			
			for(String line : subList)
			{
				if (StringUtils.isNotBlank(line))
				{
					JobExecutionStat stat = JSONUtil.parseJSON(line, JobExecutionStat.class);
					result.addFirst(stat);
				}
			}
		}
		catch (Throwable e)
		{
			getLogger().error("Can not read current job execution stat from " + jobExecutionStatFile.getAbsolutePath(), e);
		}

		return result;
	}
}
