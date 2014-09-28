<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("Karnıyarık Tarifi", "tarif/", request);
%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" 
 xmlns:fb="http://www.facebook.com/2008/fbml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>Karnıyarık Tarifi</title>
<meta name="description" content="Karnıyarık Tarifi" />
<meta name="keywords" content="Karnıyarık, Tarif" />
<meta property="og:title" content="Karnıyarık Tarifi"/>
<meta property="og:description" content="Karnıyarık Tarifi"/>
<link rel="image_src" href="http://www.karniyarik.com/tarif/img/267x200/karniyarik_tarifi_7.png" />
<meta property="og:image" content="http://www.karniyarik.com/tarif/img/267x200/karniyarik_tarifi_7.png"/>
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
  <div class="sresult"><h1>Karnıyarık Tarifi</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
	<div class="content">
		<br/>
		<div style="width: 860px;margin-right:10px;float: left;">
			<div style="width: 420px;margin-right:20px;float: left;">
				<h3 style="margin-left:5px;">Açıklama</h3>
				<img src="img/267x200/karniyarik_tarifi_7.png" alt="Karnıyarık Tabağı" style="float:left;" class="thumb"/>
				<p>
					Karnıyarık Türk mutfağının belli başlı patlıcan yemeklerinden biridir. Hazırlanırken ana malzeme olan patlıcanın dışında soğan, biber, domates ve 
					kıyma kullanılır. Türk mutfağındaki sebzeler arasında patlıcan çok özel bir önem taşır. Patlıcan sebze olarak dünyanın birçok ülkesinde yendiği 
					halde çeşitlilik açısından Türk mutfağını dünyanın en çok patlıcan yemeğine sahip mutfak olarak saymak bir abartma sayılamaz.		
		 		</p>
	 		</div>
	 		<div style="width: 420px;float:left;">
		 		<h3 style="margin-left:5px;">Malzemeler</h3>
		 		<img src="img/267x200/karniyarik_tarifi_1.png" alt="Karnıyarık için Gerekli malzemeler" style="float:left;" class="thumb"/>
				<ul>
					<li>5 Adet patlıcan</li>
					<li>5 Adet sivri biber</li>
					<li>4 Adet domates</li>
					<li>3 <a href="<%=request.getContextPath()%>/ürün/ne-kadar/Çorba+kaşığı" target="_blank">Çorba kaşığı</a> tuz</li>
					<li>1 <a href="<%=request.getContextPath()%>/ürün/ne-kadar/Su+bardağı" target="_blank">Su bardağı</a> 
					<a href="<%=request.getContextPath()%>/ürün/ne-kadar/sıvı+yağ" target="_blank">sıvı yağ</a></li>
					<li>2 Orta boy soğan</li>
					<li>250 Gr kıyma</li>
					<li>Yarım deste maydonoz</li>
					<li>1 <a href="<%=request.getContextPath()%>/ürün/ne-kadar/Çorba+kaşığı" target="_blank">Çorba kaşığı</a> 
					<a href="<%=request.getContextPath()%>/ürün/ne-kadar/domates+salçası" target="_blank">domates salçası</a></li>
				</ul> 		
	 		</div>
	 		<div class="cl"></div>
	 		<h3>Hazırlanışı</h3>
	 		<div style="width: 430px;margin-right:20px;float: left;">
		 		<p>
		 			Önce patlıcanlar alacalı soyulur ve acısının cıkması için tuzlu suda yarım saat bekletilmeye alınır.
		 		</p>
		 		<p>
		 			Patlıcanlar beklerken iç malzemesi  hazırlanır. Bunun için bir <a href="<%=request.getContextPath()%>/ürün/ne-kadar/tava" target="_blank">tavaya</a> 
		 			<a href="<%=request.getContextPath()%>/ürün/ne-kadar/sıvı+yağ" target="_blank">sıvı yağ</a> konulur ve ısıtılır. 
		 			2 orta boy soğan ince ince küp şeklinde doğranır ve kızartılır.
		 		</p>
		 		<p>
		 			Soğan yumuşayıp sararınca üzerine 250-300 gr kıyma ilave edilir.
		 		</p>
		 		<p>
		 			Kıymanın rengi döndukten sonra ince ince doğranmış yeşil biber, küp küp doğranmış domates, istenilen baharatlar(
		 			<a href="<%=request.getContextPath()%>/ürün/ne-kadar/pul+biber" target="_blank">pul biber</a>, 
		 			<a href="<%=request.getContextPath()%>/ürün/ne-kadar/karabiber" target="_blank">karabiber</a>, 
		 			<a href="<%=request.getContextPath()%>/ürün/ne-kadar/kimyon" target="_blank">kimyon</a>) 
		 			kıyılmış maydonoz ilave edip kısık ateşte pişirilir.
		 		</p>
		 		<p>
		 		Daha sonra tuzlu suda bekleyen patlıcanlar peçeteyle kurulanarak kızgın yağda nar gibi olacak şekilde kızartılır.
		 		</p>
		 		<p>
		 		Hazırladığımız kıyma patlıcanların ortasına güzelce doldurulur. Patlıcanların üstüne halka şeklinde kesilmiş domates ve biber koyulur.
		 		</p>
		 		<p>
		 		Son olarak 1 kaşık <a href="<%=request.getContextPath()%>/ürün/ne-kadar/salça" target="_blank">salça</a> 1 bardak sıcak suda karıştırılır ve tencereye koyulur. 
		 		Kısık ateşte 15-20  dakika pişirilir.
		 		</p>
		 		<p>
		 			Ve artık Karnıyarık'ınız servise hazır
		 		</p>
		 		<p>
		 			(Verdiği tarif için Ayşenur Hanım'a teşekkür ediyoruz)
		 		</p>
	 		</div>
	 		<div style="width: 400px;float:left;">
				<a href="img/800x600/karniyarik_tarifi_2.png" rel="gallery1" title="Alacalı soyulmuş patlıcanlar" class="iframe" style="float:left;">
					<img alt="Alacalı soyulmuş patlıcanlar" src="img/267x200/karniyarik_tarifi_2.png" class="thumb"/>
				</a> 		
				<a href="img/800x600/karniyarik_tarifi_3.png" rel="gallery1" title="Alacalı soyulmuş patlıcanlar" class="iframe"  style="float:left;">
					<img alt="Alacalı soyulmuş patlıcanlar" src="img/267x200/karniyarik_tarifi_3.png" class="thumb"/>
				</a> 		
				<a href="img/800x600/karniyarik_tarifi_4.png" rel="gallery1" title="Alacalı soyulmuş patlıcanlar" class="iframe"  style="float:left;">
					<img alt="Alacalı soyulmuş patlıcanlar" src="img/267x200/karniyarik_tarifi_4.png" class="thumb"/>
				</a>
				<a href="img/800x600/karniyarik_tarifi_5.png" rel="gallery1" title="Alacalı soyulmuş patlıcanlar" class="iframe"  style="float:left;">
					<img alt="Alacalı soyulmuş patlıcanlar" src="img/267x200/karniyarik_tarifi_5.png" class="thumb"/>
				</a>
				<a href="img/800x600/karniyarik_tarifi_6.png" rel="gallery1" title="Alacalı soyulmuş patlıcanlar" class="iframe"  style="float:left;">
					<img alt="Alacalı soyulmuş patlıcanlar" src="img/267x200/karniyarik_tarifi_6.png" class="thumb"/>
				</a>
				<a href="img/800x600/karniyarik_tarifi_7.png" rel="gallery1" title="Alacalı soyulmuş patlıcanlar" class="iframe"  style="float:left;">
					<img alt="Alacalı soyulmuş patlıcanlar" src="img/267x200/karniyarik_tarifi_7.png" class="thumb"/>
				</a>
	 		</div>
		</div>
		<div style="width: 160px;text-align: center;float:left;margin-top:2em;">
			<script type="text/javascript"><!--
			google_ad_client = "ca-pub-5913641514503498";
			/* tarifCntSide */
			google_ad_slot = "7650223104";
			google_ad_width = 160;
			google_ad_height = 600;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
		</div>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>