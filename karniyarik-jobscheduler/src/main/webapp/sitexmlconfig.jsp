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
<%@page import="java.io.File"%>
<%@page import="com.karniyarik.common.util.ConfigurationURLUtil"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="org.apache.commons.io.IOUtils"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%><html>
<%
String sitename = request.getParameter("s");
File siteConfigDir = ConfigurationURLUtil.loadSiteConfDir();
String fileName = siteConfigDir.getAbsolutePath() + "/" + sitename;
if(!fileName.endsWith(".xml"))
{
	fileName = fileName + ".xml";
}
File file = new File(fileName);
String content = FileUtils.readFileToString(file);
content = StringEscapeUtils.escapeHtml(content);
%> 
<head>
<title><%=sitename%> Site XML Config</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
    <%@ include file="header.jsp" %>
    <div class="span-24 last rndb">
	    <div class="mt10"></div>
	    <h1><%=sitename%> Site XML Config</h1>
	    <hr/>
	    <div class="span-23 last cl"></div>
	    <div class="mb10"></div>
    	<div class="span-23 last cl"></div>
    	<div class="last mt10 mb10">
    	</div>
    	<div class="span-23 last cl"></div>
    	<div class="span-23 mt10">
			<pre>
<%=content%>
			</pre>
    	</div>
    	</div>
    <%@ include file="footer.jsp" %>    	
    </div>
   </div>
</body>
</html>