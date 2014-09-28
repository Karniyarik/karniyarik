<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("iPhone", "iphone", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" 
 xmlns:fb="http://www.facebook.com/2008/fbml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>iPhone Uygulaması | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Ürün arama cepte! Karniyarik.com iPhone uygulaması hakkında bilmek istedikleriniz, ekran görüntüleri ve dış bağlantılar." />
<meta name="keywords" content="Karniyarik.com iPhone, mobil ürün arama" />
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
  <div class="sresult"><h1>Karnıyarık iPhone/iPod Touch</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
	<div class="content">
		<br/>
		<img alt="Karnıyarık iPhone/iPod Touch uygulaması" src="img/logo.png" style="float:left;margin-right:10px;margin-bottom:10px;"/>
		<p>Karnıyarık artık iPhone ve iPod'da, 
			<a target="blank" href="http://www.karniyarik.com/ürün/ne-kadar/iPhone+32GB">iPhone</a> ve 
			<a target="blank" href="http://www.karniyarik.com/ürün/ne-kadar/iPod+Touch+16GB">iPod</a> Karnıyarık'ta ! </p>
		<p>Karnıyarık iPhone/iPod Touch uygulaması üzerinden ihtiyaç duyduğunuz anda 
		bir ürünün nerelerde ve ne kadara satıldığını bulabilirsiniz. Aynı zamanda 
		ikinci el araba sitelerinden istediğiniz özellikteki arabaları bulma imkanına 
		da sahipsiniz. Arama sonuçları içersinden istediklerinizi favorilerinize 
		ekleyebilirsiniz. Önceki aramalarınızdan yada favorilerinizden bir ürüne 
		hızlıca ulaşabilirsiniz.
		</p>
		<p>Karnıyarık iPhone/iPod touch uygulamasına aşağıdaki bağlantıdan ulaşabilirsiniz:</p>
		<ul><li><a href="http://itunes.apple.com/tr/app/id382242506">http://itunes.apple.com/tr/app/id382242506</a></li></ul>
		<a href="img/splash.png" rel="gallery1" title="Giriş Ekranı" class="iframe"><img alt="Giriş Ekranı" src="img/splash.png" class="thumb"/></a>
		<a href="img/urun_arama.png" rel="gallery1" title="Ürün Arama Giriş Ekranı" class="iframe"><img alt="Ürün Arama Giriş Ekranı" src="img/urun_arama.png" class="thumb"/></a>
		<a href="img/arac_arama.png" rel="gallery1" title="Araç Arama Giriş Ekranı" class="iframe"><img alt="Araç Arama Giriş Ekranı" src="img/arac_arama.png" class="thumb"/></a>
		<a href="img/son_aramalar.png" rel="gallery1" title="Son Aramalar" class="iframe"><img alt="Son Aramalar" src="img/son_aramalar.png" class="thumb"/></a>
		<a href="img/favoriler.png" rel="gallery1" title="Favoriler" class="iframe"><img alt="Favoriler" src="img/favoriler.png" class="thumb"/></a>
		<a href="img/filtre1.png" rel="gallery1" title="Filtre Ekranı(1)" class="iframe"><img alt="Filtre Ekranı(1)" src="img/filtre1.png" class="thumb"/></a>
		<a href="img/filtre2.png" rel="gallery1" title="Filtre Ekranı(2)" class="iframe"><img alt="Filtre Ekranı(2)" src="img/filtre2.png" class="thumb"/></a>
		<a href="img/filtre3.png" rel="gallery1" title="Filtre Ekranı(3)" class="iframe"><img alt="Filtre Ekranı(3)" src="img/filtre3.png" class="thumb"/></a>
		<a href="img/info.png" rel="gallery1" title="Bilgi Ekranı" class="iframe"><img alt="Bilgi Ekranı" src="img/info.png" class="thumb"/></a>
			
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>