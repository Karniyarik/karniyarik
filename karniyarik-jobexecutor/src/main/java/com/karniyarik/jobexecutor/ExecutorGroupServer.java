package com.karniyarik.jobexecutor;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;

import com.karniyarik.brands.BrandService;
import com.karniyarik.brands.BrandServiceImpl;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.group.GroupMember;
import com.karniyarik.common.group.GroupMemberFactory;
import com.karniyarik.common.group.IExecutorService;
import com.karniyarik.common.statistics.vo.JobExecutionContext;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionStats;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;

public class ExecutorGroupServer extends ReceiverAdapter implements
		MembershipListener, IExecutorService {

	private static ExecutorGroupServer instance = null;
	
	private JChannel channel;
	private RpcDispatcher dispatcher;
	private List<GroupMember> activeMembers = new ArrayList<GroupMember>();
	private GroupMember thisMember = null;
	private GroupMember schedulerMember = null;
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private ExecutorGroupServer() {
		try {
			thisMember = GroupMemberFactory.getThisMember();
			thisMember.setType(GroupMemberFactory.EXECUTOR);
			
			URL url = StreamUtil.getURL("udp.xml");
			channel = new JChannel(url);
			channel.setReceiver(this);
			dispatcher = new RpcDispatcher(channel, null, null, this);
			channel.connect(GroupMemberFactory.ADMIN_GROUP_NAME);
			channel.getState(null, 10000);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static ExecutorGroupServer getInstance() {
		if (instance == null) {
			instance = new ExecutorGroupServer();
		}
		return instance;
	}
	
	public GroupMember getMemberDetails()
	{
		return thisMember;
	}
	
	public void setCapability(Boolean selenium, Boolean jobclass)
	{
		thisMember.setSeleniumCapable(selenium);
		thisMember.setJobsWithClassCapable(jobclass);
		GroupMemberFactory.saveThisMemberCapability(thisMember);
	}
	
	public List<GroupMember> getActiveMembers() {
		return activeMembers;
	}

	public void shutDown() {
		channel.close();
	}

	public void refreshActiveMembers() {
		lock.writeLock().lock();
		activeMembers = new ArrayList<GroupMember>();

		try {
			Vector<Address> membersV = channel.getView().getMembers();

			for(Address memberAdd: membersV)
			{
				Object object = dispatcher.callRemoteMethod(memberAdd,"getMemberDetails", null, new Class[] {}, RequestOptions.SYNC);
				if(object != null)
				{
					GroupMember memberDetail = (GroupMember) object;
					memberDetail.setAddress(memberAdd);
					activeMembers.add(memberDetail);
					
					if(memberDetail.isScheduler())
					{
						schedulerMember = memberDetail;
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		finally
		{
			lock.writeLock().unlock();
		}
	}
	
	private RpcDispatcher getDispatcher() {
		return dispatcher;
	}
	
	public boolean sendJobExecutionStatJobToScheduler(JobExecutionStat stat) {
		try {
			refreshActiveMembers();
			if(schedulerMember != null)
			{
				getDispatcher().callRemoteMethod(schedulerMember.getAddress(), "saveStat",new Object[] { stat }, new Class[] {JobExecutionStat.class},RequestOptions.SYNC);	
			}
			else
			{
				// do put in queue
			}
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public AlexaSiteInfo getAlexaInfo(String sitename) {
		AlexaSiteInfo result = null;
		try {
			
			refreshActiveMembers();
			if(schedulerMember != null)
			{
				Object callResult = getDispatcher().callRemoteMethod(schedulerMember.getAddress(), "getAlexaInfo",new Object[] { sitename }, new Class[] {String.class}, RequestOptions.SYNC);
				result = (AlexaSiteInfo) callResult;
			}
			else
			{
				// do put in queue
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public SiteConfig getSiteConfig(String sitename) {
		SiteConfig result = null;
		try {
			
			refreshActiveMembers();
			if(schedulerMember != null)
			{
				Object callResult = getDispatcher().callRemoteMethod(schedulerMember.getAddress(), "getSiteConfig",new Object[] { sitename }, new Class[] {String.class}, RequestOptions.SYNC);
				result = (SiteConfig) callResult;
			}
			else
			{
				// do put in queue
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	// EXECUTOR SERVICE FUNCTIONS
	public void start(JobExecutionContext context)
	{
		if (context != null)
		{
			JobExecutorAdmin.getInstance().start(context.getSiteConfig(), context.getJobExecutionStat());
		}
	}

	public void pause(String siteName)
	{
		if (StringUtils.isNotBlank(siteName))
		{
			JobExecutorAdmin.getInstance().pause(siteName);
		}
	}

	public void end(String siteName)
	{
		if (StringUtils.isNotBlank(siteName))
		{
			JobExecutorAdmin.getInstance().end(siteName);
		}
	}

	public Response pause()
	{
		JobExecutorAdmin.getInstance().pauseAll();
		return Response.ok().build();
	}
	
	@Override
	public void updateBrandKB(String content) {
		BrandService brandsService = BrandServiceImpl.getInstance();
		brandsService.updateBrandKB(content);
	}

	public JobExecutionStats getRunningStats()
	{
		JobExecutionStats stats = new JobExecutionStats();
		stats.setJobExecutionStatList(JobExecutorAdmin.getInstance().getAllStatistics());
		return stats;
	}
	
	@Override
	public boolean reduceBoost(SiteConfig siteConfig) {
		JobExecutorAdmin.getInstance().reduceBoost(siteConfig);
		return true;
	}

}
