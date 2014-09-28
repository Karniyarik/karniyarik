<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("İş İmkanları", "isimkanlari", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" 
 xmlns:fb="http://www.facebook.com/2008/fbml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>İş İmkanları | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Karniyarik.com iş imkanları, iş olanakları ve açık pozisyonlar." />
<meta name="keywords" content="Karniyarik.com, iş imkanları, iş olanakları, açık pozisyonlar, web geliştirici, java programcı, yazılım geliştirme, idari işler, pazarlama" />
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
  <div class="sresult"><h1>İş İmkanları</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
    <div class="lc full">
      <div class="prop">
        <div class="c1 text">
		    <ul>
			    <li><a href="#genel">Genel Özellikler</a></li>
			    <li><a href="#acik">Açık Pozisyonlar</a></li>   
		    </ul>
        </div>
      </div>
    </div>
    
	<div class="content">
		<br/>
		<h3 id="genel">Genel Özellikler</h3>
		<h4>Kimleri Aramızda Görmek İstiyoruz?</h4>
		<p>
			Seçtiğimiz dikey arama alanlarında en iyisini yapmaya odaklanmış bir takım olarak hangi alanda uzmanlaşmış olursa olsun 
			arkadaş canlısı, meraklı, dürüst ve gerçekçi takım arkadaşları aramaktayız. Deneyimli ya da yeni mezun, teknik ya da 
			diğer alanlarda üniversite mezunu arkadaşlarla tanışmak için can atıyoruz.
		</p>
		<p>
			Lütfen çalışma şeklimizi ve kullandığımız teknolojileri kontrol edip özgeçmişinizi <img alt="kariyer eposta" src="<%=request.getContextPath()%>/images/mail/kariyer.png" class="mailimg"/> e-posta adresine gönderiniz. 
		</p>
		<h4>Ne Yapıyoruz?</h4>
		<p>Geçmişi ve bugünü ile İnternet'i yakından tanıyoruz ve gelişmesini hayranlıkla izliyoruz. İlk olarak bu hızda değişen İnternet'i bugün 
		bir ucundan yakalamak ve günümüz İnternet'ini Türkiye'nin daha iyi anlamasını sağlamak istiyoruz. Yine bugün İnternet'in geleceğine dair 
		adımlar atmak ve bu geleceğe adımızı yazmak istiyoruz. Bu amaç doğrultusunda sürekli olarak	öğreniyoruz. Yanlışlarımızdan korkmuyoruz ama 
		onlardan ders almaya çalışıyoruz. Olabildiğince karmaşık sistemimizi son kullanıcılarımıza yine olabildiğince basit sunmaya çalışıyoruz. Bütün 
		bu amaçlar için çok sıkı çalışıyoruz.      
		</p> 
		<h4>Nasıl Çalışıyoruz?</h4>
		<p>Teknik geliştirme sürecimiz aşağıdaki maddeler üzerine kuruludur:</p>
		<ul>
			<li>Deneysel</li>
			<li>Kullanıcı odaklı</li>
			<li>Hızlı iterasyonlar ile ilerme</li>
			<li>Yanlışlar ve öğrenme</li>
		</ul>
		<h4>Hangi Teknolojilerle Çalışıyoruz?</h4>
		<ul>
			<li>Java, JEE, Grails, Phyton, Scala</li>
			<li>Linux</li>
			<li>İlişkisel/Anahtar-Değer/Çizge Veritabanı Yönetim Sistemleri</li>
			<li>Jetty, Tomcat, Apache</li>
			<li>Paralel sistemler ve büyük veri setleri</li>
			<li>Yazı İşleme</li>
			<li>Doğal dil işleme</li>
			<li>Öğrenen yazılımlar ve puslu mantık</li>
			<li>İstatistik</li>
			<li>Yüksek Erişilebilirlik</li>
		</ul>
		<h3 id="acik">Açık Pozisyonlar</h3>
		<h4>İdari İşler Asistanı</h4>
		<p>Bir başlangıç şirketinin idari işlerini yürütebilecek nitelikte dinamik ve sorumluluk sahibi arkadaşlar arıyoruz. İdari işler asistanının görevi 
		şirketin yasal evraklarını takip etmek, idari dökümantasyonları hazırlamak, müşteri ilişkilerini yönetmek, müşteri istek ve önerilerini 
		yazılımcılarımıza iletmek temelli olacaktır.</p> 
		<p>Genel nitelikler:</p>
		<ul>
			<li>Yoğun mail ve telefon trafiğini yönetebilme</li>
			<li>İş takibi ve sonuçlandırmasını yapabilme</li>
			<li>Organizasyon becerileri kuvvetli</li>
			<li>Üniversite mezunu</li>
			<li>İnternete ilgili</li>
		</ul>

		<h4>Yazılım Mühendisi</h4>
		<p>İnternete ve şirketimizin kullandığı teknolojilere ilgili yazılım mühendisi arkadaşlar arıyoruz. Karnıyarık'taki var olan yazılımcılarımız 
		yeni teknolojileri öğrenmeye ve uygulamaya meraklı, "önce çalıştır sonra hızlandır" mottosuna sahiptirler. Gerçek dünya problemlerine hızlı 
		çözümler uygulayan, ve büyük düşünmekten çekinmeyen insanlardır.</p>
		<p>Gerekli genel nitelikler:</p>
		<ul>
			<li>Web programlama deneyimine ya da isteğine sahip</li>
			<li>Hızlı prototipleme ve gerçekleştirmeye yatkınlık</li>
			<li>Karmaşık sorunları çözebilme</li>
			<li>Üniversitelerin bilgisayar mühendisliği bölümlerinde okuyan ya da mezun</li>
		</ul>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>