<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"><%@
page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="com.karniyarik.web.json.SearchStatisticsManager"
%><%@page import="java.util.Calendar"
%><%@page import="com.karniyarik.web.statusmsg.TwitterMessages"
%><%@page import="com.karniyarik.web.statusmsg.TwitterMessage"
%><%@page import="com.karniyarik.common.config.news.NewsList"
%><%@page import="com.karniyarik.common.KarniyarikRepository"
%><%@page import="com.karniyarik.common.config.news.News"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.util.QueryStringHelper"
%><%@page import="com.karniyarik.common.config.system.SearchConfig"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%
HttpCacheUtil.setIndexResponseCacheAttributes(response, request);int categoryType =  RequestWrapper.getInstance(request).getCategoryType();
String desc = "Fiyatı olan her şey Karniyarik.com'da";
String keywords = "alışveriş, ürün, araba, ikinci el, en ucuz, ne kadar, nerede satılıyor, alışveriş siteleri, fiyat, karniyarik, alışveriş sitesi yorumları, dikey arama motoru, ürün arama, alışveriş arama";
String title = "Karnıyarık - Alışverişi Yardık!";
request.setAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE, 8);
%>
<html 
	xmlns="http://www.w3.org/1999/xhtml" 
	xml:lang="tr" 
	lang="tr"
	xmlns:addthis="http://www.addthis.com/help/api-spec" 
	xmlns:fb="http://www.facebook.com/2008/fbml"
	xmlns:og="http://opengraphprotocol.org/schema/">
	<head>
		<title><%=title%></title>
		<meta name="description" content="<%=desc%>" />
		<meta name="keywords" content="<%=keywords%>" />
		<meta property="fb:type" content="website"/>
		<meta property="og:title" content="<%=title%>"/>
		<meta property="og:url" content="http://www.karniyarik.com"/>
		<meta property="og:description" content="<%=desc%>"/>
		<meta property="og:image" content="http://www.karniyarik.com/images/logo/karniyarik86x86.png"/>
		<link rel="image_src" href="http://www.karniyarik.com/images/logo/karniyarik86x86.png" />
		<link rel="canonical" href="http://www.karniyarik.com" />
		<jsp:include page="commonmeta.jsp" />
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/pack.css?02102011" />
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/layout.css?02102011" />
		<!--[if IE 6]><link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/ie6.css" /><![endif]-->
		<script type='text/javascript' >var rootPath='<%=request.getContextPath()%>';</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/pack.js?02102011"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscripts.js?02232011"></script>
	</head>
<body class="home"> <!-- onload="$('#words').focus();" -->
<div class="c ar tmh ">
<jsp:include page="nav.jsp"></jsp:include>
</div>
<div class="cp">
  <a href="/" class="logo" title="Karniyarik.com: Ürün arama motoru, fiyatı olan herşeyi bul(acak)">
  	<img alt="" src="<%=request.getContextPath()%>/images/transparent.gif" height="95" width="418" class="sprite logo-img"/>
  </a>
</div>
<div class="line">
  <div class="cp">
    <div class="search">
      <div class="searchl">
        <div class="searchr">
          <div class="stab act">
            <a class="ri" title="Ürün ara"><span class="sprite p-tab"></span><span class="txt">Ürün</span></a>
          </div>
          <div class="stab tho">
            <a class="ri" title="Araç ara"><span class="sprite c-tab"></span><span class="txt">Araç</span></a>
          </div>
          <!-- div class="stab tho1">
            <a class="ri" title="Emlak ara"><img src="<%=request.getContextPath()%>/images/estate.png" width="32" height="27" alt="Emlak" /><span>Emlak</span></a>
          </div -->          
          <!-- div class="stab tho2">
            <a class="ri" title="Otel ara"><img src="<%=request.getContextPath()%>/images/otel.png" width="32" height="27" alt="Otel" /><span>Otel</span></a>
          </div -->          
          <form action="urun/search.jsp" method="get" class="form" onsubmit="setFormAction(this)" id="frmSearch">
          	<fieldset>
            <input id="words" name="query" type="text" class="trans inp mhide" value="Ürün ara..." />
            <input class="trans sub" name="" type="submit" value="Ara!" />
            <input id="stab" name="stab" type="hidden" value="1" />
            </fieldset>
          </form>
        </div>
      </div>
    </div>
  </div>
  <div class="c">
    <div class="who c1 b"><h1>Karniyarik.com Nedir?</h1></div>
    <div class="whod"><strong class="b">Karniyarik.com </strong>gerek mağazalar gerekse internet sitelerinden alışveriş yapan tüketicilere fiyat-ürün çeşitliliği açılarından daha bilinçli olmaları yönünde kolaylık sağlamak amacıyla servis veren bir sistemdir. <span class="c2 b"><a href="aboutus.jsp#nedir">Devamı...</a></span></div>
  </div>
</div>
<div class="cl"></div>
<div class="c">
<div class="tabs">
      <div class="tabh">
        <a class="tab act"><span>Son arananlar</span></a>
        <a class="tab" ><span>En çok arananlar</span></a>
      </div>
      <div class="tabc">
      	<div class="1 val act">
		<%int index = 0;
    	List<LinkedLabel> lastSearches = SearchStatisticsManager.getInstance().getLastSearches(CategorizerConfig.PRODUCT_TYPE);
    	LinkedLabel searchStr = null;
    	QueryStringHelper helper = new QueryStringHelper(request);
    	helper.setPageSize(SearchConfig.DEFAULT_PAGE_SIZE);
    	helper.setSort(1);
    	helper.setShowImages(1);
    	helper.setContextPath(request.getContextPath());
    	
    	helper.setCategoryFilterValue("urun");
		for(int i = 0; i < lastSearches.size(); i++){
			searchStr = lastSearches.get(i);
			if(StringUtils.isNotBlank(searchStr.getOriginalValue())){
				helper.setSearchQuery(searchStr.getOriginalValue());
		%>
    	<a title="<%=searchStr.getLabel()%> fiyatları" href="<%=helper.getRequestQuery()%>" rel="nofollow"><%=searchStr.getShortenedLabel()%></a>
    	<%}}%>
      	</div>
      	<div class="2 val">
		<%index = 0;
    	lastSearches = SearchStatisticsManager.getInstance().getLastSearches(CategorizerConfig.CAR_TYPE);
    	searchStr = null;
    	helper.setCategoryFilterValue("araba");
		for(int i = 0; i < lastSearches.size(); i++){
			searchStr = lastSearches.get(i);
			if(StringUtils.isNotBlank(searchStr.getOriginalValue())){
				helper.setSearchQuery(searchStr.getOriginalValue());
		%>
		<a title="<%=searchStr.getLabel()%> fiyatları" href="<%=helper.getRequestQuery()%>" rel="nofollow"><%=searchStr.getShortenedLabel()%></a>
    	<%}}%>
      	</div>
      	<div class="3 val">
      	</div>
      </div>
      <div class="tabc">
        <div class="1 val act">
	    <%List<LinkedLabel> topSearches = SearchStatisticsManager.getInstance().getTopSearches(CategorizerConfig.PRODUCT_TYPE);
	      helper.setCategoryFilterValue("urun");
		  for(int i = 0; i < topSearches.size(); i++){
			searchStr = topSearches.get(i);
			if(StringUtils.isNotBlank(searchStr.getOriginalValue())){
				helper.setSearchQuery(searchStr.getOriginalValue());
		%>
			<a title="<%=searchStr.getLabel()%> fiyatları" href="<%=helper.getRequestQuery()%>"><%=searchStr.getShortenedLabel()%></a>
	    <%}}%>
	    </div>
	    <div class="2 val">
		<%topSearches = SearchStatisticsManager.getInstance().getTopSearches(CategorizerConfig.CAR_TYPE);
			helper.setCategoryFilterValue("araba");
		  for(int i = 0; i < topSearches.size(); i++){
			searchStr = topSearches.get(i);
			if(StringUtils.isNotBlank(searchStr.getOriginalValue())){
				helper.setSearchQuery(searchStr.getOriginalValue());
		%>
			<a title="<%=searchStr.getLabel()%> fiyatları" href="<%=helper.getRequestQuery()%>"><%=searchStr.getShortenedLabel()%></a>
	    <%}}%>	    
	    </div>
	    <div class="3 val">
	    </div>
      </div>      
    </div>
</div>
<div class="c">
  <div class="box news" style="width: 725px; height: 20px; margin-right: 0pt;text-align: center;">
  	<a rel="nofollow" href="<%=request.getContextPath()%>/addsite.jsp">
  		<img alt="transparent" src="<%=request.getContextPath()%>/images/transparent.gif" height="23" width="119" class="sprite addsite"/>
  	</a>
  	<b>Mağazalara özel:</b> Sitenizi Karnıyarık'a eklemek için <a rel="nofollow" href="<%=request.getContextPath()%>/addsite.jsp">Site Ekleme Formunu</a> kullanabilirsiniz.
  </div>
  <div class="cl"></div>
	<div class="copy c1"> 
	 <a rel="nofollow" class="tb" href="" title="Karnıyarık Bilişim Teknolojileri Ltd. Şti. sayfasına git">Karnıyarık Ltd.</a> © <%=Calendar.getInstance().get(Calendar.YEAR)%>
	 | <a rel="nofollow" class="tb" title="Karniyarik.com hakkında bilgi almak için tıklayın" href="<%=request.getContextPath()%>/aboutus.jsp">Hakkımızda</a>
	 | <a rel="nofollow" class="tb" title="Bilgi topladığımız internet sitelerini görmek için tıklayın" href="<%=request.getContextPath()%>/sites.jsp">Bilgi Toplanan Siteler</a>
	 | <a class="tb purple bold" title="Karniyarik Kurumsal" href="<%=request.getContextPath()%>/kurumsal">Mağaza Girişi (Yeni!)</a> 
	 | <a rel="nofollow" class="tb" title="Karniyarik.com'a sitenizi eklemek için tıklayın" href="<%=request.getContextPath()%>/addsite.jsp">Sitenizi Ekleyin</a>
	 | <a rel="nofollow" class="tb" href="<%=request.getContextPath()%>/aboutus.jsp#legal">Yasal Bilgi</a>
	 | <a rel="nofollow" class="tb" title="Karniyarik API kullanımı ile ilgili detaylı bilgi almak için tıklayın" href="<%=request.getContextPath()%>/api.jsp">API</a> 
	 | <a rel="nofollow" class="tb" title="Karniyarik Veri Beslemeleri ile ilgili detaylı bilgi almak için tıklayın" href="<%=request.getContextPath()%>/datafeed/">Veri Beslemesi</a> 
	 | <a rel="nofollow" class="tb" title="Karniyarik iPhone uygulaması" href="<%=request.getContextPath()%>/iphone/">iPhone</a> 
	 | <a rel="nofollow" class="tb" title="Karniyarik iş imkanlari" href="<%=request.getContextPath()%>/isimkanlari/">İş İmkanları</a>
	 | <a rel="nofollow" class="tb" title="Karniyarik.com kullanımı ile ilgili detaylı bilgi almak için tıklayın" href="<%=request.getContextPath()%>/help.jsp">Yardım</a>
	</div>
</div>
<!--div class="c">
  <  div class="adst">Ads</div>
  <div class="ads">
    <div class="p">Place for advertisement</div>
  </div>
</div-->

<jsp:include page="ganalytics.jsp"/>
</body>
</html>