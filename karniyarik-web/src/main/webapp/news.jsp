<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="com.karniyarik.common.config.news.NewsList"%>
<%@page import="com.karniyarik.common.KarniyarikRepository"%>
<%@page import="com.karniyarik.common.config.news.News"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("Haberler", "news.jsp", request);
%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
<head>
<title>Karnıyarık - Alışverişi yardık! Haberler</title>
<meta name="description" content="Karniyarik.com'un siz kullanıcılarının ilgisini çekecek haberleri, yeni özellikleri." />
<meta name="keywords" content="Karniyarik.com, haberler, yeni özellikler" />
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
  <div class="sresult"><h1>Haberler</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
    <div class="lc full">
      <div class="prop">
        <div class="c1 text">
        </div>
      </div>
    </div>
	<div class="content">
	<br/>
	<%NewsList list = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getNewsList();
    for(News news:list.getNewsList()){%>
    <h3 id="<%=news.getDate()%>"><%=news.getTitle()%></h3>
    <p>
    <%=news.getFullText()%>
	</p>
 	<%}%>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="commonfooter.jsp"></jsp:include>
</body>
</html>