package com.karniyarik.jobscheduler;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;

import com.karniyarik.common.KarniyarikRepository;
import com.karniyarik.common.config.site.SiteConfig;
import com.karniyarik.common.group.GroupMember;
import com.karniyarik.common.group.GroupMemberFactory;
import com.karniyarik.common.group.ISchedulerService;
import com.karniyarik.common.statistics.vo.JobExecutionContext;
import com.karniyarik.common.statistics.vo.JobExecutionStat;
import com.karniyarik.common.statistics.vo.JobExecutionStats;
import com.karniyarik.common.util.StreamUtil;
import com.karniyarik.externalrank.alexa.AlexaRankRegistry;
import com.karniyarik.externalrank.alexa.AlexaSiteInfo;

public class SchedulerGroupServer extends ReceiverAdapter implements
		MembershipListener, ISchedulerService {

	private static SchedulerGroupServer instance = null;
	
	public static String ADMIN_GROUP_NAME = "CrawlerGroup";
	
	private JChannel channel;
	private RpcDispatcher dispatcher;
	private Map<String, GroupMember> activeMemberMap = new HashMap<String, GroupMember>();
	private List<GroupMember> activeMembers = new ArrayList<GroupMember>();
	private List<GroupMember> executors = new ArrayList<GroupMember>();
	private List<GroupMember> seleniumCapable = new ArrayList<GroupMember>();
	private List<GroupMember> jobsWithClassCapable = new ArrayList<GroupMember>();
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Timer timer = new Timer();
	private GroupMember thisMember = null;
	
	private SchedulerGroupServer() {
		try {
			thisMember = GroupMemberFactory.getThisMember();
			thisMember.setType(GroupMemberFactory.SCHEDULER);

			URL url = StreamUtil.getURL("udp.xml");
			channel = new JChannel(url);
			channel.setReceiver(this);
			dispatcher = new RpcDispatcher(channel, null, null, this);
			channel.connect(ADMIN_GROUP_NAME);
			channel.getState(null, 10000);
			refreshActiveMembers();
			
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					refreshActiveMembers();
				}
			}, 10000, 20000);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public GroupMember getMemberDetails()
	{
		return thisMember;
	}

	public static SchedulerGroupServer getInstance() {
		if (instance == null) {
			instance = new SchedulerGroupServer();
		}
		return instance;
	}

	public List<GroupMember> getActiveMembers() {
		lock.readLock().lock();
		List<GroupMember> result = activeMembers;
		lock.readLock().unlock();
		return result;
	}
	
	public List<GroupMember> getSeleniumCapable() {
		lock.readLock().lock();
		List<GroupMember> result = seleniumCapable;
		lock.readLock().unlock();
		return result;
	}

	public List<GroupMember> getJobsWithCassCapable() {
		lock.readLock().lock();
		List<GroupMember> result = jobsWithClassCapable;
		lock.readLock().unlock();
		return result;
	}

	public List<GroupMember> getExecutors() {
		lock.readLock().lock();
		List<GroupMember> result = executors;
		lock.readLock().unlock();
		return result;
	}
	
	public GroupMember getMemberByName(String name)
	{
		lock.readLock().lock();
		GroupMember groupMember = activeMemberMap.get(name);
		lock.readLock().unlock();
		return groupMember;
	}

	public void shutDown() {
		timer.cancel();
		channel.close();
	}
	

	public void refreshActiveMembers() {
		lock.writeLock().lock();
		activeMembers = new ArrayList<GroupMember>();
		seleniumCapable = new ArrayList<GroupMember>();
		executors = new ArrayList<GroupMember>();
		activeMemberMap = new HashMap<String, GroupMember>();
		
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
					activeMemberMap.put(memberDetail.getUuid(), memberDetail);
					if(memberDetail.isExecutor())
					{
						executors.add(memberDetail);
					}
					if(memberDetail.isSeleniumCapable())
					{
						seleniumCapable.add(memberDetail);
					}
					if(memberDetail.isJobsWithClassCapable())
					{
						jobsWithClassCapable.add(memberDetail);
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		finally{
			lock.writeLock().unlock();
		}
	}
	
	private RpcDispatcher getDispatcher() {
		return dispatcher;
	}

	public boolean setCapability(GroupMember member, Boolean selenium, Boolean jobclass) {
		try {
			getDispatcher().callRemoteMethod(member.getAddress(), "setCapability", new Object[] { selenium, jobclass}, new Class[] { Boolean.class, Boolean.class }, RequestOptions.SYNC);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean sendStartCommand(GroupMember member, SiteConfig siteConfig,
			JobExecutionStat stat) {
		try {
			JobExecutionContext context = new JobExecutionContext();
			context.setJobExecutionStat(stat);
			context.setSiteConfig(siteConfig);
			getDispatcher().callRemoteMethod(member.getAddress(), "start", new Object[] { context }, new Class[] { JobExecutionContext.class }, RequestOptions.SYNC);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public Map<GroupMember, JobExecutionStats> getRunningStats()
	{
		Map<GroupMember, JobExecutionStats> stats = new HashMap<GroupMember, JobExecutionStats>();
		
		List<GroupMember> activeMembers2 = getActiveMembers();
		for(GroupMember member : activeMembers2)
		{
			if(member.isExecutor())
			{
				try {
					Object result = getDispatcher().callRemoteMethod(member.getAddress(), "getRunningStats", new Object[] {},new Class[] {}, RequestOptions.SYNC);
					stats.put(member, (JobExecutionStats) result);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return stats;
	}

	public boolean sendPauseCommand(String siteName) {
		try {
			Vector<Address> members = channel.getView().getMembers();
			getDispatcher().callRemoteMethods(members, "pause", new Object[] { siteName },new Class[] { String.class }, RequestOptions.SYNC);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean sendEndCommand(String siteName) {
		try {
			Vector<Address> members = channel.getView().getMembers();
			getDispatcher().callRemoteMethods(members, "end", new Object[] { siteName },new Class[] { String.class }, RequestOptions.SYNC);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean sendPauseAllCommand() {
		try {
			Vector<Address> members = channel.getView().getMembers();
			getDispatcher().callRemoteMethods(members, "pause", new Object[] {},new Class[] {}, RequestOptions.SYNC);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean sendReduceBoostCommand(SiteConfig siteConfig) {
		try {
			Vector<Address> members = channel.getView().getMembers();
			
			if(members.size() > 0 )
			{
				getDispatcher().callRemoteMethod(members.get(0), "reduceBoost", new Object[] {siteConfig},new Class[] {SiteConfig.class}, RequestOptions.SYNC);	
			}
			
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean sendNewBrandFileContent(String content) {
		try {
			Vector<Address> members = channel.getView().getMembers();
			getDispatcher().callRemoteMethods(members, "updateBrandKB", new Object[] {content},new Class[] {String.class}, RequestOptions.SYNC);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	//SCHEDULER SERVICE FUNCTIONS
	public void saveStat(JobExecutionStat stat)
	{
		if (stat != null) {
			JobSchedulerAdmin.getInstance().saveStat(stat);
		} 
	}
	
	@Override
	public AlexaSiteInfo getAlexaInfo(String sitename)
	{
		return AlexaRankRegistry.getInstance().getSiteInfo(sitename);
	}
	
	public SiteConfig getSiteConfig(String sitename)
	{
		return KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
	}
}
