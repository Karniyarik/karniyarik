<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>
<div align="left" class="span-24 last rndb header">
	<div class="span-8" style="margin-top: 10px;">
	<img src="images/logo2.png" alt="Karniyarik Logo"/>
	</div>
	<div class="span-16 last" style="margin-top: 30px;">
		<ul class="sf-menu span-16">
			<li><a href="jobs.jsp">Jobs</a></li>
			<li><a href="<%=request.getContextPath()%>/logout.htm">Logout</a></li>
		</ul>    
	</div>
</div>
<div class="span-24 last">Logged in as: <security:authentication property="principal.username"/> </div>
