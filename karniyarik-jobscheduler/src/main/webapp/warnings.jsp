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
<%@page import="com.karniyarik.jobscheduler.vo.ScheduleInformation"%>
<%@page import="com.karniyarik.jobscheduler.warning.WarningMessageController"%>
<%@page import="com.karniyarik.common.exception.ExceptionVO"%>
<%@page import="com.karniyarik.jobscheduler.warning.WeeklyWarnings"%>
<%@page import="com.karniyarik.jobscheduler.warning.DailyWarnings"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%><html>
<head>
<title>Warnings</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>Warnings</h1>
    
	    <hr/>
	    <div class="cl"></div>
	    <div class="mb10"></div>
    
    	<div class="span-24 last">
    		<form action="warnings.jsp" method="get" id="form">
    		Select Week:
    		<select name="date" onchange="$('#form').submit()">
			<%
			WarningMessageController controller = new WarningMessageController();
			String selected = request.getParameter("date");
			List<String> dateList = controller.getWarningWeeks();
			for(String date: dateList){%>
    			<option value="<%=date%>" <%=date.equalsIgnoreCase(selected) ? "selected='selected'" : "" %>><%=date%></option>
    		<%} %>
    		</select>
    		</form>
		</div>
    	<div class="cl"></div>
    	<div class="mt10">
   			<%WeeklyWarnings weekly = controller.getWarnings(selected);%>
   			<table>
   				<caption>Totals</caption>
   				<thead>
   					<tr>
   						<th>Type</th>
   						<th>Count</th>
   					</tr>
   				</thead>
   				<tbody>
   					<%for(String type: weekly.getWarningTypeCount().keySet()){%>
   						<tr>
   							<td><%=type%></td>
   							<td><%=weekly.getWarningTypeCount().get(type)%></td>
   						</tr>
   					<%} %>
   				</tbody>
   			</table>
   			<%List<DailyWarnings> dailyList = weekly.getWarnings();
   			int index = 0;
   			
   			for(DailyWarnings daily: dailyList){%>
    		<table>
    			<caption><%=daily.getDate()%></caption>
				<thead>
	    			<tr>
		    			<th>Date</th>
						<th>Identifier</th>
		    			<th>Title</th>
	    			</tr>	    		
	    		</thead>
	    		<tbody>
	    			<%
	    			Map<String, List<ExceptionVO>> dailyMap = daily.getWarnings();
	    			for(String identifier: dailyMap.keySet()){
	    				List<ExceptionVO> warnings = dailyMap.get(identifier);
	    				for(ExceptionVO vo: warnings){ index++;%>
	    			<tr>
	    				<td><%=vo.getDate()%></td>
	    				<td><%=vo.getIdentifier()%></td>
	    				<td><%=vo.getTitle()%></td>
	    				<td>
	    					<a class="inline" href="#detail<%=index%>"> 
	    						<img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/>
	    					</a>
	    					<div style="display:none">
	    						<div id="detail<%=index%>" style="width:500px;height:400px;padding:10px;">
	    							<p><%=vo.getDate()%></p>
	    							<p><%=vo.getIdentifier()%></p>
	    							<p><%=vo.getTitle()%></p>
	    							<p><%=vo.getMessage()%></p>
	    							<p><%=StringUtils.isNotBlank(vo.getStackTrace()) ? StringEscapeUtils.escapeHtml(vo.getStackTrace()) : ""%></p>
	    						</div>
	    					</div>		
	    				</td>
	    			</tr> 
	    			<%}
	    			}%>
	    		</tbody>
    		</table>
    		<%}%>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>