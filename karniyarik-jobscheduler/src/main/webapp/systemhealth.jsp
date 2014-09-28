<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.common.group.GroupMember"%>
<%@page import="com.karniyarik.jobscheduler.SchedulerGroupServer"%>
<%@page import="com.karniyarik.jobscheduler.util.SystemHealthCalculator"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.karniyarik.common.group.GroupMemberFactory"%>
<%@page import="com.karniyarik.jobscheduler.JobAdminStateFilter"%>

<%@page import="com.karniyarik.common.ga.Analytics"%>
<%@page import="com.karniyarik.common.ga.Visit"%>
<%@page import="com.karniyarik.common.ga.Event"%>
<%@page import="com.karniyarik.common.ga.Referral"%><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Karniyarik Job Scheduler</title>
<jsp:include page="common_include.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$("#analyticstabs").tabs();
		$("#incometabs").tabs();
	});
</script>
</head>
<body>
  <div class="container">
  <%@ include file="header.jsp" %>
  <div class="span-24 rndb">
    <div class="cl"></div>
    <jsp:include page="clustermembers.jsp"></jsp:include>
    <hr/>
    <div class="cl"></div>
    <div class="span-13" style="max-height:350px;height:350px;">
    	<jsp:include page="overallhealth.jsp"></jsp:include>
    </div> 
    
    <div class="span-10 last mb10" style="max-height:350px;height:350px;">
    	<div id="incometabs">
	    	<h1>Income</h1>
	    	<ul>
				<li><a href="#itabs-1">Today</a></li>
				<li><a href="#itabs-2">Yesterday</a></li>
			</ul>
			<div id="itabs-1">
			    <jsp:include page="income.jsp"><jsp:param name="day" value="today"/></jsp:include>
			</div>    
			<div id="itabs-2">
			    <jsp:include page="income.jsp"></jsp:include>
			</div>    	
    	</div>
    </div>
    <div class="span-24 mt10 mb10">
    
    </div>
    <div class="cl"></div>
    <div id="analyticstabs" class="mt10">
    	<h1>Analytics</h1>
    	<ul>
			<li><a href="#tabs-1">Today</a></li>
			<li><a href="#tabs-2">Yesterday</a></li>
		</ul>
		<div id="tabs-1">
		    <jsp:include page="googleanalytics.jsp"><jsp:param name="day" value="today"/></jsp:include>
		</div>    
		<div id="tabs-2">
		    <jsp:include page="googleanalytics.jsp"></jsp:include>
		</div>
    </div>
    
    <hr/>
    
    <div class="cl"></div>
    <%@ include file="footer.jsp" %>  	
  </div>
 </div>
</body>
</html>
