<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.karniyarik.common.statistics.vo.JobExecutionStat"%>
<%@page import="com.karniyarik.jobscheduler.request.JobRequestHandler"%>
<%@page import="com.karniyarik.jobscheduler.JobSchedulerAdmin"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.karniyarik.common.KarniyarikRepository"%>
<%@page import="com.karniyarik.common.config.site.SitesConfig"%>
<%@page import="com.karniyarik.common.config.site.SiteConfig"%>
<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummary"%>
<%@page import="com.karniyarik.jobscheduler.util.JobStatisticsSummaryCalculator"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.karniyarik.jobscheduler.JobAdminStateFilter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.quartz.Trigger"%>
<%@page import="com.karniyarik.jobscheduler.util.DateFormatter"%>
<%@page import="com.karniyarik.jobscheduler.request.ScheduleCalculator"%>
<%@page import="com.karniyarik.jobscheduler.vo.ScheduleInformation"%><html>
<head>
<title>Jobs</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>Scheduling Info</h1>
    
	    <hr/>
	    <div class="cl"></div>
	    <div class="mb10"></div>
    
    	<div class="span-24 last">	
    		<script type="text/javascript">
    		$(function() {
    			$( "#refresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    			$( "#filter" ).buttonset();
    			$( "#autorefresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    		});
    		</script>
			<%
    			String refreshStr = "";
    			String state = request.getParameter("state");
    			String checked = "";
	    		if(StringUtils.isNotBlank(state))
				{
	    			JobAdminStateFilter stateFilter = JobAdminStateFilter.valueOf(state);
	    			refreshStr = "?state=" + stateFilter;
					if(stateFilter == JobAdminStateFilter.EXECUTING)
					{
						checked = "checked='checked'"; 
					}
				}
    		%>      		
	    	<span class="toolbar ui-widget-header ui-corner-all">
				<a id="refresh" href="schedules.jsp<%=refreshStr%>">Refresh</a>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<span id="filter">
					<a id="all" href="schedules.jsp?state=<%=JobAdminStateFilter.ALL%>">All</a>
					<a id="executing" href="schedules.jsp?state=<%=JobAdminStateFilter.EXECUTING%>">Executing</a>
					<a id="failed" href="schedules.jsp?state=<%=JobAdminStateFilter.FAILED%>">Failed</a>
					<a id="brokendown" href="schedules.jsp?state=<%=JobAdminStateFilter.BROKEN%>">Broken Down</a>
					<a id="datafeed" href="schedules.jsp?state=<%=JobAdminStateFilter.DATAFEED%>">Datafeed</a>
				</span>
			</span>
			<span class="toolbar ui-widget-header ui-corner-all">
				<input type="checkbox" <%=checked%> id="autorefresh" name="autorefresh"/><label for="autorefresh">Auto refresh</label>
			</span>
		</div>
    	<div class="cl"></div>
    	<div class="mt10">
	    	<table id="jobstable">
	    		<tr>
	    			<th>Site</th>
	    			<th>Datefeed</th>
	    			<th>Last Start Date</th>
	    			<th>Last End Date</th>
	    			<th>First Start Date</th>
	    			<th>Previous Start Date</th>
	    			<th>Next Start Date</th>
	    			<th>Final Date</th>
	    			<th>End Date</th>
	    			<th>Last Duration</th>
	    			<th>Time to Next</th>
	    		</tr>
	    		<%
	    			List<JobAdminStateFilter> filters = new ArrayList<JobAdminStateFilter>();
	    			if(StringUtils.isNotBlank(state))
	    			{
	    				filters.add(JobAdminStateFilter.valueOf(state));
	    			}
	    			else
	    			{
	    				filters.add(JobAdminStateFilter.ALL);
	    			}
	    			
	    			List<JobExecutionStat> statisticsList = JobSchedulerAdmin.getInstance().getStatistics(filters);
	    	
	    			List<ScheduleInformation> schedules= ScheduleCalculator.getSchedules(statisticsList);
	    			
	    			for(ScheduleInformation info: schedules ) {
	    				
	    			%>
	    			<tr>
	    				<td>
	    				<a class="iframe" href='jobdetail_min.jsp?sn=<%=info.getSitename()%>'><img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/></a>
	    				<a href='jobdetail.jsp?sn=<%=info.getSitename()%>'><%=info.getSitename()%></a>
	    				</td>
	    				<td><img src="<%=request.getContextPath()%>/images/icons/24/<%=info.isDatafeed() ? "green_flag.png" : "red_flag.png"%>"/></td>
	    				<td><%=DateFormatter.format(info.getBeginDate())%></td>
	    				<td><%=DateFormatter.format(info.getEndDate())%></td>
	    				<td><%=DateFormatter.format(info.getStartDate())%></td>
						<td><%=DateFormatter.format(info.getPreviousFireTime())%></td>
						<td><%=DateFormatter.format(info.getNextStartDate())%></td>
						<td><%=DateFormatter.format(info.getFinalFireTime())%></td>
						<td><%=DateFormatter.format(info.getEndTime())%></td>
						<td><%=info.getLastExecutionDurationStr()%></td>
						<td><%=info.getDurationStr()%></td>
	    			</tr>	
	    			<%}%>
	    	</table>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>