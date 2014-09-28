<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%String dataEdited = request.getParameter("dataedited");if(StringUtils.isBlank(dataEdited)){dataEdited = "false";}%>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/pack.css?02102011"/>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/layout.css?02102011"/>
		<!--[if IE 6]><link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/ie6.css" /><![endif]-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/pack.js?02102011"></script>
		<script type="text/javascript">var dataEdited = <%=dataEdited%>;</script>
		<script type="text/javascript">var rootPath='<%=request.getContextPath()%>';</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscripts.js?02232011"></script>