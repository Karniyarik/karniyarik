package com.karniyarik.common.util;

import java.io.InputStream;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzSchedulerFactory {
	private static QuartzSchedulerFactory instance = null;
	SchedulerFactory schedFact = null; 
	
	public static QuartzSchedulerFactory getInstance() {
		if(instance == null)
		{
			instance = new QuartzSchedulerFactory();
		}
		return instance;
	}

	public SchedulerFactory getSchedFact() {
		if(schedFact == null)
		{
			try {
				InputStream stream = StreamUtil.getStream("karniyarik.quartz.properties");
				Properties quartzProperties = new Properties();
				quartzProperties.load(stream);
				stream.close();
				
				schedFact = new StdSchedulerFactory(quartzProperties);
				Scheduler sched = schedFact.getScheduler();
				sched.start();
			} catch (Throwable e) {
				e.printStackTrace();
			} 
		}
		return schedFact;
	}
	
	public void shutdown()
	{
		try {
			getSchedFact().getScheduler().shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
