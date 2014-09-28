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
<%@page import="java.util.ArrayList"%><html>
<head>
<title>Site Link Prefix File</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1>Site Link Prefix File</h1>
	    <hr/>
	    <div class="span-23 last cl"></div>
	    <div class="mb10"></div>
    	<div class="span-23 last cl"></div>
    	<div class="mt10">
    	<div class="span-23 last cl"></div>
    	<div class="span-23 last mt10 mb10">
    	<%
    	String result = request.getParameter("result");
    	String files = request.getParameter("files"); 
    	if(StringUtils.isNotBlank(result) && result.equals("success")){
    		String[] filesArr = files.split(",");
    	%>
			<span class="success span-23 last">
				The following files are uploaded: 
				<%for(String file: filesArr) {%>
				<a href="<%=request.getContextPath()%>/sitexmlconfig.jsp?s=<%=file%>"><%=file%></a><br/>
				<%}%>
			</span>
    	<%} else {%>
    		<span class="error">An error occured, cannot distribute the file to web</span>
    	<%}%> 
    	</div>
    	<div class="span-23 last cl"></div>
    	<div class="span-23 mt10">
    	<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/rest/uploadsiteprefix">
			<input type="file" name="xmlconfig"/>
			<input type="submit" value="Submit"/>    	
    	</form>
    	</div>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>