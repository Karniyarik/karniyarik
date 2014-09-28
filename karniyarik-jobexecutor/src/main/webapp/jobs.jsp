<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.karniyarik.common.statistics.vo.JobExecutionStat"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.karniyarik.common.KarniyarikRepository"%>
<%@page import="com.karniyarik.common.config.site.SitesConfig"%>
<%@page import="com.karniyarik.common.config.site.SiteConfig"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.karniyarik.jobexecutor.JobExecutorAdmin"%><html>
<head>
<title>Jobs</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
    	<div class="span-24 last">	
    		<script type="text/javascript">
    		$(function() {
    			$( "#refresh" ).button({
    				text: true,
    				icons: {primary: "ui-icon-arrow-refresh-1-w"}
    			});
    		});
    		</script>
	    	<span class="toolbar ui-widget-header ui-corner-all">
				<a id="refresh" href="jobs.jsp">Refresh</a>
			</span>
		</div>
    	<div class="cl"></div>
    	<div class="mt10">
	    	<table id="jobstable">
	    		<tr>
	    			<th>Site</th>
	    			<th>Datafeed</th>
	    			<th>Schedule Date</th>
	    			<th>Start</th>
	    			<th>End</th>
	    			<th>State</th>
	    			<th>Visited</th>
	    			<th>Product Count</th>
	    			<th>Duplicate</th>
	    			<th>To Visit</th>
	    			<th>Exception</th>
	    			<th>Indexed</th>
	    		</tr>
	    		<%
	    			List<JobExecutionStat> statisticsList = JobExecutorAdmin.getInstance().getAllStatistics();
	    			
	    			SitesConfig sitesConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig();
	    			JobExecutionStat statistics;
	    			String beginDate;
	    			String endDate;
	    			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 
	    			for(int i = 0; i < statisticsList.size(); i++) {
	    				
	    				statistics = statisticsList.get(i);
	    				
	    				SiteConfig siteConfig = sitesConfig.getSiteConfig(statistics.getSiteName());
	    				boolean isDataFeed = false;
	    				if(siteConfig != null)
	    				{
	    					isDataFeed = siteConfig.hasDatafeed();
	    				}
	    				
	    				if (statistics.getStartDate() > 0) {
		    				beginDate = dateFormat.format(new Date(statistics.getStartDate()));
	    				} else {
	    					beginDate = "Not started";
	    				}
	    				
	    				if (statistics.getEndDate() > 0) {
		    				endDate = dateFormat.format(new Date(statistics.getEndDate()));
	    				} else {
							endDate = "Not ended";	    					
	    				}
	    				
	    				DecimalFormat format = new DecimalFormat("#0");
	    			%>
	    			<tr class="<%=statistics.getStatusMessage().toString().toLowerCase()%>">
	    				<td>
	    				<a class="iframe" href='siteconfig.jsp?s=<%=statistics.getSiteName()%>'><img src="<%=request.getContextPath()%>/images/icons/16/zoom_in.png"/></a>
	    				<a href='siteconfig.jsp?s=<%=statistics.getSiteName()%>'><%=statistics.getSiteName()%></a>
	    				</td>
	    				<td><img src="<%=request.getContextPath()%>/images/icons/24/<%=isDataFeed ? "green_flag.png" : "red_flag.png"%>"/></td>
	    				<td></td>
	    				<td><%=beginDate%></td>
	    				<td><%=endDate%></td>
						<td><%=statistics.getStatus().toString()%></td>
	    				<td><%=statistics.getTotalVisitedLinks()%></td>
	    				<td><%=statistics.getProductCount()%></td>
	    				<td><%=statistics.getDuplicateProductCount()%></td>
	    				<td><%=statistics.getTotalLinksToVisit()%></td>
	    				<td><%=statistics.getTotalExceptionCount()%></td>
	    				<td><%=statistics.getProductsIndexed()%></td>
	    			</tr>	
	    			<%}%>
	    	</table>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>