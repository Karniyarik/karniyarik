<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.common.statistics.vo.JobExecutionStat"%>
<%@page import="com.karniyarik.jobscheduler.JobSchedulerAdmin"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.karniyarik.common.group.GroupMember"%><div>

<div>
<%
String sitename = request.getParameter("sitename");
if(StringUtils.isNotBlank(sitename)){
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
	List<JobExecutionStat> history = JobSchedulerAdmin.getInstance().getStatHistory(sitename); %>
	<table>
		<thead>
			<tr>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Avg. Fetch Time</th>
				<th>Product Count</th>
				<th>Product Miss Count</th>
				<th>Duplicate Products</th>
				<th>State</th>
				<th>Message</th>
				<th>Server</th>
			</tr>
		</thead>
   		<tbody>
   		<%for(JobExecutionStat stat: history){
   			String startDate = "";
   			String endDate = "";
   			if(stat.getStartDate() != 0)
   			{
   				startDate = dateFormat.format(stat.getStartDate());
   			}
   			if(stat.getEndDate() != 0)
   			{
   				endDate = dateFormat.format(stat.getEndDate()); 
   			}
   			
   			String serverId = stat.getRunningServer();
			String serverName = stat.getRunningServer();
			GroupMember member = null;
			if (StringUtils.isNotBlank(serverId)) {
				member = JobSchedulerAdmin.getInstance().getMember(serverId);
				if(member != null)
				{
					serverName = member.getAddress().toString();
				}
			} else if (JobSchedulerAdmin.getInstance().isWaiting(stat.getSiteName())) {
				serverId = null;
				serverName = "Waiting";
			} else {
				serverId = null;
				serverName = "-";
			}
   		%>   		
   			<tr>
	    		<td><%=startDate%></td>
	    		<td><%=endDate%></td>
	    		<td><%=stat.getWindowedAvgFetchTime()%></td>
	    		<td><%=stat.getProductCount()%></td>
	    		<td><%=stat.getProductMissCount()%></td>
	    		<td><%=stat.getDuplicateProductCount()%></td>
	    		<td>
		    		<span title="<%=stat.getStatus().hasFailed()%>">
		    			<img src="<%=request.getContextPath()%>/images/icons/24/<%=stat.getStatus().hasFailed() ? "red_flag.png" : "green_flag.png"%>"/>
		    		</span>
	    		</td>
	    		<td><%=stat.getStatusMessage()%></td>
	    		<td>
		    		<span title="<%=serverId%>">
		    		<%=stat.getRunningServer()%>
		    		</span>
	    		</td>
    		</tr>
    	<%} %>
   		</tbody>
   		<tfoot>
   			<tr>
   			</tr> 
   		</tfoot>
   	</table>			
<%}%>
</div>
   	
</div>