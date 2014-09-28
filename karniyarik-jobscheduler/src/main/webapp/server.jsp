<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.common.group.GroupMember"%>
<%@page import="com.karniyarik.jobscheduler.SchedulerGroupServer"%>

<%
	String serverId = request.getParameter("id");
	GroupMember member = SchedulerGroupServer.getInstance().getMemberByName(serverId);
	JobRequestHandler.handleCapabilityRequest(request, response, member);
%>

<%@page import="com.karniyarik.jobscheduler.JobSchedulerAdmin"%>
<%@page import="com.karniyarik.common.group.GroupMemberFactory"%>
<%@page import="com.karniyarik.jobscheduler.request.JobRequestHandler"%><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Data Collection Server</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
  <%@ include file="header.jsp" %>
  <div class="span-24 rndb">
  	<div>
         <h1><%=member.getAddress()%></h1>
         <hr />
         <div class="span-4" style="float:left;text-align: center;">
         	<img src="<%=request.getContextPath()%>/images/server/server.png" style="vertical-align:middle;clear:both;"/>
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
         <div class="span-18 last">
         	<span class="span-18 last" style="font-weight:bold;"><%=GroupMemberFactory.getMemberTypeStr(member.getType())%></span>
         	<span class="span-4" style="font-weight:bold;">IP:</span><span class="span-14 last"><%=member.getIp()%></span>
         	<span class="span-4" style="font-weight:bold;">Selenium Capable:</span><span class="span-14 last"><%=member.isSeleniumCapable()%></span>
         	<span class="span-4" style="font-weight:bold;">Job Class Capable:</span><span class="span-14 last"><%=member.isJobsWithClassCapable()%></span>
         	<span class="span-4" style="font-weight:bold;">UUID:</span><span class="span-14 last"><%=member.getUuid()%></span>
         	<span class="span-4">&nbsp;</span><span class="span-14 last"><a href="http://<%=member.getIp()%>:10080/karniyarik-jobexecutor">Go to Server</a></span>
         </div>
         <%if(member.isExecutor()){ %>
         <div class="span-4">&nbsp;</div>
         <div class="span-18 last">
         <%
         	String seleniumchecked = "";
         	if(member.isSeleniumCapable())
         	{
         		seleniumchecked = "checked='checked'";
         	}
         	
         	String jobclasschecked = "";
         	if(member.isJobsWithClassCapable())
         	{
         		jobclasschecked = "checked='checked'";
         	}
         %>         
         <form action="<%=request.getContextPath()%>/server.jsp">
         	<label for="selenium">Selenium Capable</label><input type="checkbox" name="selenium" <%=seleniumchecked%>></input><br/>
         	<label for="jobclass">Job Class Capable</label><input type="checkbox" name="jobclass" <%=jobclasschecked%>></input><br/>
         	<input type="hidden" name="change" value="1"></input>
         	<input type="hidden" name="id" value="<%=serverId%>"></input>
         	<input type="submit" value="Submit"></input>
         </form>
         </div>
         <%}%>
    </div>
    <hr/>
    <div class="cl"></div>
    <%@ include file="footer.jsp" %>  	
  </div>
 </div>
</body>
</html>
