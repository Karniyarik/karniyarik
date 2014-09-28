<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
<head>
<title>Karnıyarık - Alışverişi yardık! Internal</title>
<meta name="description" content="Karnıyarık Servlets yardım sayfası" />
<meta name="keywords" content="Karnıyarık Servlets" />
<jsp:include page="../commonmeta.jsp" />
<jsp:include page="../commoninclude.jsp" />
</head>
<body>
<div class="ce ar tmh2 ">
	<jsp:include page="../nav.jsp"></jsp:include>
</div>
<div class="ce">
  <form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/urun/search.jsp" onsubmit="if($('#stab').val()=='1')this.action='<%=request.getContextPath()%>/urun/search.jsp'; submitForm()">
  <jsp:include page="../commontop.jsp"></jsp:include>
  </form>
  <div class="sresult"><h1>Karnıyarık Web Internal Actions</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
	<div class="content">
	<br/>
		<div style="display: block;clear:both;">
		<table class="apitable">
			<tr>
				<td><a href="<%=request.getContextPath()%>/internal/servlets.jsp">Event Servlets</a></td>
			</tr>			
		</table>
	    </div>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>