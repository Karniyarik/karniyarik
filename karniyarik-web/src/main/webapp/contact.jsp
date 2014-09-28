<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("İletişim", "contact.jsp", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>İletişim | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" http-equiv="description" content="Karniyarik.com'a sormak istediklerinizi, dileklerinizi, 
şikayetlerinizi, yardım isteklerinizi, ve hata bildirimlerinizi bu sayfadaki form aracılığı ile bizlere iletebilirsiniz." />
<meta name="keywords" http-equiv="keywords" content="iletişim, Dilek, Şikayet, İstek, Soru" />
<jsp:include page="commonmeta.jsp" />
<jsp:include page="commoninclude.jsp" />
<script type='text/javascript' src='http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.min.js'></script>
<script type='text/javascript' >
$(document).ready(function() {
	$("#contactForm").validate({
		rules: {
			name: {
				required: true,
				minlength: 4
			},
			email: {
				required: true,
				email: true
			},
			message: {
				required: true,
				minlength: 10
			}			
		},
		messages: {
			name: "Lütfen adınızı giriniz",
			email: "Lütfen email adresinizi giriniz",
			message: "Lütfen mesajınızı giriniz"
		}
	});

	$(".input").tooltip({
		// place tooltip on the right edge
		position: "center right",
		// a little tweaking of the position
		offset: [-2, 5],
		// use the built-in fadeIn/fadeOut effect
		effect: "fade",
		// custom opacity setting
		opacity: 0.7
	});
});
</script>
</head>
<body>
<div class="ce ar tmh2 ">
	<jsp:include page="nav.jsp"></jsp:include>
</div>

<div class="ce">
  <form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/urun/search.jsp" onsubmit="if($('#stab').val()=='1')this.action='<%=request.getContextPath()%>/urun/search.jsp'; submitForm()">
  <jsp:include page="commontop.jsp"></jsp:include>
  </form>
  <div class="sresult"><h1>İletişim</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
	<div class="content">
	<br/>
    <p>İstek, öneri, iş ortaklığı gibi konular için <a href="#contactForm">İletişim Formu</a>'muzu kullanabileceğiniz gibi, bizlere <img alt="info mail" src="images/mail/info.png" class="mailimg"/> e-posta adresinden de ulaşabilirsiniz</p>
    <p><b>Mağazalara özel:</b> Web sitenizdeki ürünlerin <a href="http://www.karniyarik.com">Karnıyarık</a>'ta yer alması için lütfen <a href="<%=request.getContextPath()%>/addsite.jsp">Site Ekleme Formunu</a> kullanınız</p> 
	<h3>İletişim Bilgileri</h3>
	<p>
	<span typeof="v:Organization">
		<span property="v:name">Karnıyarık Yazılım Teknolojileri Ltd. Şti.</span><br/>
			<!-- <a href="http://company.karniyarik.com"><span property="v:url">http://company.karniyarik.com</span></a><br/> -->
			<span><img alt="info mail" src="images/mail/info.png" class="mailimg"/></span><br/>			
			<span  property="v:address">
				<span property="v:street-address">KOSGEB Teknoloji Geliştirme Merkezi No:302</span>
				<span property="v:street-address">ODTÜ, Teknokent</span>
				<span property="v:region">Ankara</span>
				<span property="v:postal-code">06561</span>
				<span property="v:country-name">Türkiye</span>
			</span><br/>
			<!-- Telefon: <span property="v:tel">+90 312 2101300-(302)</span><br/ -->
			Fax: <span property="v:tel">+90 312 2101309</span><br/>			
	</span>
	</p>
	<%
		String status = request.getParameter("status");
		if(status != null && status.equalsIgnoreCase("ok"))
		{ %>
		<div class="notice">
			Mesajınız başarı ile iletilmiştir.
		</div>
	<% 
		} else {
	%>
	<h3>Lütfen okuyunuz</h3>
	<ul class="purple">
		<li>Karnıyarık'da herhangi bir şekilde ürün satılmamaktadır.</li> 
		<li>Stok durumu sormak için lütfen arama sonuçlarındaki "Mağazaya git" tuşuna tıklayınız ve ürünün asıl satıldığı mağazadan sorunuz.</li>
		<li>Site sahibiseniz sitenizi Karnıyarık'a eklemek için lütfen <a href="<%=request.getContextPath()%>/addsite.jsp">Site Ekleme Formunu</a> kullanınız.</li>
	</ul>
	<form action="<%=request.getContextPath()%>/system/contactus" method="post" id="contactForm">
		<fieldset>
			<legend>İletişim formu</legend>
			<p>
			<label for="amac">Sizin de söylecekleriniz var</label>
			<select class="input" name="amac" id="amac" title="Mesajınızın ana konusunu seçiniz">
				<option value="istek" <%=request.getParameter("amac") == null || ((String)request.getParameter("amac")).equals("istek") ? "selected=\"selected\"" : ""%>>Şu da olsa ne güzel olurdu...</option>
				<option value="oneri" <%=StringUtils.equals(request.getParameter("amac"), "oneri") ? "selected=\"selected\"" : ""%>>Bir önerim var!</option>
				<option value="is_ortakligi" <%=StringUtils.equals(request.getParameter("amac"), "is_ortakligi") ? "selected=\"selected\"" : ""%>>Nasıl beraber iş yapabiliriz?</option>
				<option value="site_eklenmesi" <%=StringUtils.equals(request.getParameter("amac"), "site_eklenmesi") ? "selected=\"selected\"" : ""%>>Şu site güzel onu da ekleyin!</option>
				<option value="api_key" <%=StringUtils.equals(request.getParameter("amac"), "api_key") ? "selected=\"selected\"" : ""%>>Karnıyarık API için anahtar istiyorum</option>
			</select>

	       	<label for="name">İsminiz*</label>
	       	<input class="input" type="text" title="Adınızı ve soyadınızı giriniz" name="name" id="name" value="<%=request.getParameter("name") == null ? "" : request.getParameter("name")%>"/>
	        <label for="email">E-posta adresiniz*</label>
	   	    <input class="input" type="text" title="Eposta adresinizi giriniz" name="email" id="email" value="<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>" />
	        <label for="message">Mesajınız*</label>
	   	    <textarea class="input" name="message" title="Bize iletmek istediğiniz mesajınızı giriniz" id="message" rows="5" cols="20"><%= request.getParameter("message") == null ? "" : request.getParameter("message")%></textarea>
	   	    <label for="soru">Aşağıdaki kutuya görüntülenen harfleri aynen giriniz
	   	    <% if(status != null && status.equalsIgnoreCase("captchaerr"))
				{ %>
				<span class="error"> *Harfleri yanlış girdiniz. Tekrar deneyiniz...</span>
			<% 
				}
	   	 	%>
	   	    </label>
	   	    <jsp:include page="captcha.jsp"></jsp:include>
	   	    </p>
	   	    <input type="hidden" name="return" id="return" value="contact.jsp" />
	       	<button type="submit">Gönder</button>
		</fieldset>
	</form>
	<%} %>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="commonfooter.jsp"></jsp:include>
</body>
</html>