<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
<head>
<title>Karnıyarık - Alışverişi yardık! Servlets</title>
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
  <div class="sresult"><h1>Karnıyarık Servlets</h1></div>
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
				<th>URL</th>
				<th>Açıklama</th>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/system/refreshindex"><%=request.getContextPath()%>/system/refreshindex</a></td>
				<td></td>
			</tr>			
			<!-- tr>
				<td><a href="<%=request.getContextPath()%>/system/backupindex"><%=request.getContextPath()%>/system/backupindex</a></td>
				<td></td>
			</tr -->			
			<!-- tr>
				<td><a href="<%=request.getContextPath()%>/system/cleanindex"><%=request.getContextPath()%>/system/cleanindex</a></td>
				<td></td>
			</tr -->
			<tr>
				<td><%=request.getContextPath()%>/system/mergeindex?s=sitename&c=true</td> 
				<td></td>
			</tr>

			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/generatesitemap"><%=request.getContextPath()%>/rest/event/generatesitemap</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/refreshsitemapdates"><%=request.getContextPath()%>/rest/event/refreshsitemapdates</a></td>
				<td></td>
			</tr>			
			
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/createsearchlogsitemap"><%=request.getContextPath()%>/rest/event/createsearchlogsitemap</a></td>
				<td></td>
			</tr>			

			<tr>
				<td><%=request.getContextPath()%>/rest/event/createinsightsitemap?f=insightX.txt</td>
				<td></td>
			</tr>			
						
			<tr>
				<td><a href="<%=request.getContextPath()%>/system/siteconfrefresh"><%=request.getContextPath()%>/system/siteconfrefresh</a></td>
				<td></td>
			</tr>			
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/entsynch"><%=request.getContextPath()%>/rest/event/entsynch</a></td>
				<td></td>
			</tr>			
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/statsynch"><%=request.getContextPath()%>/rest/event/statsynch</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/refreshqueryindex"><%=request.getContextPath()%>/rest/event/refreshqueryindex</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/refreshsitereviews"><%=request.getContextPath()%>/rest/event/refreshqueryindex</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/refreshtwittermsg"><%=request.getContextPath()%>/rest/event/refreshtwittermsg</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/sendtwitterstatus"><%=request.getContextPath()%>/rest/event/sendtwitterstatus</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/refreshcitydeals"><%=request.getContextPath()%>/rest/event/refreshcitydeals</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/solrcommit"><%=request.getContextPath()%>/rest/event/solrcommit</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/solrcommit?c=Urun"><%=request.getContextPath()%>/rest/event/solrcommit?c=Urun</a></td>
				<td></td>
			</tr>
			<tr>
				<td><a href="<%=request.getContextPath()%>/rest/event/solrcommit?c=Araba"><%=request.getContextPath()%>/rest/event/solrcommit?c=Araba</a></td>
				<td></td>
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