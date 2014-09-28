<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<div align="left" class="span-24 last rndb header">
	<div class="span-8" style="margin-top: 10px;">
	<img src="images/logo2.png" alt="Karniyarik Logo"/>
	</div>
	<div class="span-16 last" style="margin-top: 30px;">
		<ul class="sf-menu span-16">
			<li><a href="<%=request.getContextPath()%>/home.jsp">Home</a></li>
			<li><a href="<%=request.getContextPath()%>/systemhealth.jsp">Monitor</a></li>
			<li><a href="#">Jobs</a>
				<ul>
					<li><a href="jobs.jsp">All Jobs</a></li>
					<li><a href="executingjobs.jsp">Executing Jobs</a></li>
	    			<li><a href="failedjobs.jsp">Failed Jobs</a></li>
				</ul>
			</li>
			<li><a href="#">Health</a>
				<ul>
					<li><a href="jobs_health.jsp">Jobs Health</a></li>
					<li><a href="brokenjobs.jsp">Broken Down</a></li>
					<li><a href="alljobsdetails.jsp">All Jobs Details</a></li>
					<li><a href="warnings.jsp">Warnings</a></li>
				</ul>
			</li>
			<li><a href="schedules.jsp">Schedules</a></li>
			<li><a href="#jsp">Config</a>
				<ul>
					<li><a href="uploadxml.jsp">Job Config Upload</a></li>
					<li><a href="deletejob.jsp">Delete Site</a></li>
					<li><a href="uploadbrand.jsp">Brand File Upload</a></li>
					<li><a href="uploadsitelinkprefix.jsp">Site Prefix Upload</a></li>
				</ul>
			</li>
			<li><a href="<%=request.getContextPath()%>/logout.htm">Logout</a></li>
		</ul>    
	</div>
</div>
<div class="span-24 last">Logged in as: <security:authentication property="principal.username"/> </div>
