<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.common.group.GroupMember"%>
<%@page import="com.karniyarik.jobscheduler.SchedulerGroupServer"%>
<%@page import="com.karniyarik.common.group.GroupMemberFactory"%>
<div>
<h1>Cluster Members</h1>
<%
List<GroupMember> members = SchedulerGroupServer.getInstance().getActiveMembers();
for(GroupMember member: members) {
%>
<div class="bkmrk" style="width:250px;height:110px; overflow:hidden; text-align: left;">
	<div style="float:left;width:82px;margin-right:4px;">
		<a href="<%=request.getContextPath()%>/server.jsp?id=<%=member.getUuid()%>">
		<img src="<%=request.getContextPath()%>/images/server/server.png" style="vertical-align:middle;clear:both;"/>
		</a>
		<div style="text-align: center;">
			<%if(member.isSeleniumCapable()){ %>
			<img src="<%=request.getContextPath()%>/images/server/selenium.jpg" alt="Selenium" title="Selenium"/>
			<%} %>
			<%if(member.isJobsWithClassCapable()){ %>
			<img src="<%=request.getContextPath()%>/images/server/class.jpg" alt="Jobs With Class" title="Jobs With Class"/>
			<%} %>
			<%if(member.isScheduler()){ %>
			<img src="<%=request.getContextPath()%>/images/server/scheduler.jpg" alt="Scheduler" title="Scheduler"/>
			<%} %>
			<%if(member.isExecutor()){ %>
			<img src="<%=request.getContextPath()%>/images/server/executor.jpg" alt="Executor" title="Executor"/>
			<%} %>
		</div>
	</div>
	<div>
		<span style="font-weight: bold;">
			<%=GroupMemberFactory.getMemberTypeStr(member.getType())%>
			<a href="<%=request.getContextPath()%>/server.jsp?id=<%=member.getUuid()%>">
			<img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/>
		</a>
		</span>
		<span><%=member.getAddress()%></span>
		<span><%=member.getIp()%></span>
		<span><%=member.getUuid()%></span>
	</div>
</div>
<%}%>
 </div>
    