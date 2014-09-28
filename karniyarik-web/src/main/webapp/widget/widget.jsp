<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="../error.jsp"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
<head>
<title>Karnıyarık - Alışverişi yardık! Widget</title>
<meta name="description" content="Karnıyarık Widget yardım sayfası" />
<meta name="keywords" content="Karnıyarık Widget" />
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
  <div class="sresult"><h1>Karnıyarık Widget</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
    <div class="lc full">
      <div class="prop">
        <div class="c1 text">
		    <ul>
			    <li><a href="#nedir">Karnıyarık Widget Nedir?</a></li>
			    <li><a href="#kullanici">Kimler Kullanabilir?</a></li>
			    <li><a href="#kullanim">Nasıl Kullanılır?</a></li>
			</ul>
        </div>
      </div>
    </div>
	<div class="content">
	<br/>
	<div>
		<img alt="Karnıyarık Widget Örnek Ekranı" src="images/widget_example.png" style="float:right; margin-left: 5px; margin-top:5px; margin-bottom:5px;"/>
		<h3 id="nedir">Karnıyarık Widget Nedir?</h3>
	    <p>
	    Karnıyarık Widget web sayfalarınıza kolaylıkla ürün arama sonuçlarınızı yerleştirebilmeniz için bir web aracıdır.
	    Bu web aracı sayesinde web sayfanıza eklediğiniz basit bir HTML+Javascipt ile anında sayfanızda bulunan içerik ile 
	    ilgili ürün arama sonuçlarını şekilde olduğu gibi gösterme şansına sahipsiniz.   
		</p>
		<p>
		Bulunan veriler Karnıyarık sisteminin son kullanıcılara hizmet sunduğu veriler ile aynıdır. Kısacası web arayüzünden yapılan sorgular sonucunda
		gösterilen değerlerin aynısına Karnıyarık Widget'i kullanarak sayfanızda görüntüleyebilirsiniz.
		</p>
		<h3 id="kullanici">Kimler Kullanabilir</h3>		
		<p>
			Karnıyarık Widget sayfalarında ürün bilgisi gösterip kullanıcılara ekstra bilgi vermek isteyen herkese açıktır. Özelliklede ürün yorum siteleri
			dünyada başka örneklerde de olduğu gibi bu sayede sayfalarında bahsettikleri ürün hakkında kullanıcılarına ürünün nerede satıldığı
			ve fiyatının ne olduğu konusunda bilgi vererek daha iyi hizmet verebilme şansına sahip.    
		</p>
		<h3 id="kullanim">Nasıl Kullanılır</h3>
	    <p>
	    	Karnıyarık Widget, bir çok web widget'ı gibi sayfanıza eklediğiniz bir scriptle mümkündür. Aşağıdaki örnekte verildiği
	    	gibi yapmanız gereken sayfanıza Karnıyarık Widget scriptini eklemek ve daha sonrasında da görüntünün çıkması istediğiniz yere
	    	Karnıyarık Widget nesnesini çalıştırmak. Karnıyarık Widget nesnesinin aldığı değerler içerisinde görüntünün
	    	değişmesini sağlacak parametreler yanında en önemli parametre sayfanızın içeriğini ifade eden ürün adını
	    	"query" parametresi ile vermek.
	    </p>
	    <pre class="samplecode">
&lt;script src="http://www.karniyarik.com/widget/widget.js" type="text/javascript"&gt;&lt;/script&gt;
&lt;script type="text/javascript"&gt;
new KRNYRK.Widget({
	  query:"nokia n97 mini",
	  maxresult:4,
	  width:'250px',
	  header: {text:'#E5E5E5', background:'#636363'},
	  list: {background: 'white', name:'#5F7A06', price:'#883171', store:'#636363'}
	  }).start();
&lt;/script&gt;
   		</pre>
	</div>	    
  </div>
</div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>