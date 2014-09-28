<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page isErrorPage="true"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%HttpCacheUtil.setNoCacheAttributes(response, request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
	<head>
		<title>404 - Karnıyarık - Sayfa Bulunamadı</title>
		<meta name="description" content="404 - Karnıyarık - Sayfa Bulunamadı" />
		<jsp:include page="commonmeta.jsp" />
		<jsp:include page="commoninclude.jsp" />
	</head>
	<body>
		<div class="ce ar tmh2 ">
			<jsp:include page="nav.jsp"></jsp:include>
		</div>
		<div class="ce">
		  <form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/urun/search.jsp" onsubmit="if($('#stab').val()=='1')this.action='<%=request.getContextPath()%>/urun/search.jsp'; submitForm()">
		  <jsp:include page="commontop.jsp"></jsp:include>
		  </form>
		  <div class="sresult"><h1>404 - Sayfa Bulunamadı</h1></div>
		  <div class="cl"></div>
		  <div class="l">
		    <div class="filter full"></div>
		    <div class="cl"></div>
		    <div class="ll"></div>
		    <div class="lc full">
		      <div class="prop">
		        <div class="c1 text">
		    	<p>Aradıgınız sayfayı bulamadık. Bunun nedenleri arasında:</p>
		    	<ul>
		    		<li>Yazdığınız adres yanlış olabilir</li>
		    		<li>Karnıyarık'da hiç bir zaman böyle bir sayfa olmamıştır</li>
		    		<li>Ya da bir zamanlar böyle bir sayfa vardı ama kaldırılmış olabilir</li>
		    	</ul>
		    	<div class="props" >
		      	  <div class="cl"></div>
		      	  <a title="Alışveriş" rel="home" href="http://www.karniyarik.com">Ana sayfaya git.</a>
			      <div class="cl"></div>
		    	</div>    	
				</div>
			  </div>
		    </div>
		  </div>
		</div>
		<div class="cl" style="height:50px"></div>
		<div ></div>
		<jsp:include page="commonfooter.jsp"></jsp:include>
	</body>
</html>