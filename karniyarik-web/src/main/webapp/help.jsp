<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("Yardım", "help.jsp", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
<head>
<title>Yardım | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Karniyarik.com'u nasıl kullanmalısınız? Sistemdeki özellikler nelerdir? En ucuz ürünleri bulmak, 
fiyatı olan herşeye tek sorguda ulaşmak için Karniyarik.com'u nasıl kullanmanız gerektiğini öğrenin." />
<meta name="keywords" content="nasıl kullanılır, Karniyarik.com kullanım klavuzu, yardım, alışveriş, karniyarik.com" />
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
  <div class="sresult"><h1>Size nasıl yardımcı olabiliriz?</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
    <div class="lc full">
      <div class="prop">
        <div class="c1 text">
		    <ul class="contentindex">
				<li><a href="#nedir">Karnıyarık Nedir?</a></li>    
			    <li><a href="#ana_sayfa">Ana Sayfa</a></li>
			    <li><a href="#listeleme">Ürün Listeleme Sayfası</a></li>
			    <li><a href="#fiyat_filtresi">Fiyat Filtresi</a></li>
			    <li><a href="#marka_filtresi">Marka Filtresi</a></li>
			    <li><a href="#magaza_filtresi">Mağaza Filtresi</a></li>
			    <li><a href="#siralama">Sıralama Kutusu</a></li>
			</ul>
		    <ul class="contentindex">
			    <li><a href="#urun_sayisi">Ürün Sayısı Kutusu</a></li>
			    <li><a href="#urun_fotografi">Ürün Fotoğrafı Gösterme</a></li>
			    <li><a href="#paylas">Paylaş</a></li>
			    <li><a href="#benzer_urunler">Benzer Ürünler</a></li>
			    <li><a href="#kaynak">Ürün Kaynağı</a></li>
			    <li><a href="#marka">Ürün Markası</a></li>
			    <li><a href="#son_guncelleme">Son Güncelleme Tarihi</a></li>        	
        	</ul>
		    <ul class="contentindex">
			    <li><a href="#fiyat">Ürün Fiyatı</a></li>
			    <li><a href="#bulunan_urun">Bulunan Ürün Sayısı</a></li>
			    <li><a href="#hakkimizda">Hakkımızda Sayfası</a></li>
			    <li><a href="#siteler">Bilgi Toplanan Siteler Sayfası</a></li>
			    <li><a href="#iletisim">İletişim Sayfası</a></li>
			    <li><a href="#neredenaldin">NeredenAldın.com</a></li>
			    <li><a href="#opensearch">Gezgin Arama Eklentisi</a></li>		            	
        	</ul>
        </div>
      </div>
    </div>
	<div class="content">
		<br/>
<h3 id="nedir">Karnıyarık Nedir?</h3>
    <p>
    <span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span> 
	gerek mağazalar gerekse internet sitelerinden alışveriş yapan tüketicilere fiyat-ürün çeşitliliği açılarından daha bilinçli olmaları
	yönünde kolaylık sağlamak amacıyla servis veren bir sistemdir. 
	</p>
	<p>
	<span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span>
	üzerinden herhangi bir ürün satılmamaktadır. Ürünler hakkındaki güncel bilgiler, satış yapan mağazaların internet sitelerinde mevcuttur.
	</p>
	<p>
	<span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span>
	üzerinden araba ilanı verilmemektedir. İlanlar hakkındaki güncel bilgiler, ilanların verildiği internet sitelerinde mevcuttur.
	</p>
 
	<h3 id="ana_sayfa">Ana Sayfa</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    karniyarik.com ana sayfası <span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">http://www.karniyarik.com</span></a></span>
    adresini ziyaret ettiğinizde karşılaştığınız ilk sayfadır. Arama yapabilmek için Ara düğmesinin solunda bulunan metin alanına bulmak istediğiniz ürünün
    ismini (örnek: <a href="http://www.karniyarik.com/%C3%BCr%C3%BCn/ne-kadar/nokia+cep+telefonu+sarj+aleti">nokia cep telefonu sarj aleti</a>)
    veya markasını (örnek: <a href="http://www.karniyarik.com/%C3%BCr%C3%BCn/ne-kadar/blackberry">Blackberry</a>) yazıp Ara düğmesine tıklamanız yeterlidir.
	</p>
	<b>Detaylı Yardım:</b><br />
	<p>
	karniyarik.com ana sayfası <span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">http://www.karniyarik.com</span></a></span>
    adresini ziyaret ettiğinizde karşılaştığınız ilk sayfadır. Bu sayfada bir arama kutusu, bir Ara düğmesi, Son Arananlar bölümü, En Çok Arananlar bölümü ile Hakkımızda,
    Bilgi Toplanan Siteler, İletişim ve Yardım bağlantıları bulunmaktadır.
    </p>
    <p>
    <span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">http://www.karniyarik.com</span></a></span> ana sayfası, kullanım kolaylığı sağlayabilmek için mümkün olduğunca sade tasarlanmıştır. 
	Arama yapabilmek için Ara düğmesinin solunda bulunan metin alanına bulmak istediğiniz ürünün ismini (örnek: <a href="http://www.karniyarik.com/%C3%BCr%C3%BCn/ne-kadar/nokia+cep+telefonu+sarj+aleti">nokia cep telefonu sarj aleti</a>)
    veya markasını (örnek: <a href="http://www.karniyarik.com/%C3%BCr%C3%BCn/ne-kadar/blackberry">Blackberry</a>) yazıp Ara düğmesine tıklamanız yeterlidir.
    </p>
    <p>
	Metin kutusuna yazdığınız kelimelere genel olarak “sorgu”, yaptığınız arama işlemine ise “sorgulama” adı verilmektedir.
	Eğer sorgulama işlemi sonucunda hiç ürün bulunamadıysa karnıyarık size bir dizi öneri sunar. Aradığınız ürün ya da markanın ismini yanlış yazmış olabileceğiniz gibi, karniyarik.com içerisinde henüz bu isimde bir ürün olmayabilir.
	</p>
	<p>
	Bir diğer öneri özelliği de “Şunu Mu Demek İstediniz” özelliğidir. Karniyarik, sık yapılan yazım hatalarını anlar ve size girmiş olduğunuz sorguda geçen kelimelere en çok benzeyen kelime veya kelimeleri görüntüler. Örneğin; “simens” kelimesi ile sorgulama yaptığınız zaman karnıyarık size “Şunu Mu Demek İstediniz: siemens” şeklinde bir bilgi mesajı görüntüleyecektir.
	</p>
	<p>
	Yapmış olduğunuz sorgulama işlemi sonrası karniyarik.com, 1 milyondan fazla ürün içerisinden girmiş olduğunuz sorguya uygun ürünleri listeleyecektir.
	</p>
 
	<h3 id="listeleme">Ürün Listeleme Sayfası</h3>
    <p>
    Sorgulama sonucu bulunan ürünler Ürün Listeleme Sayfası’nda listelenir. Bu sayfada;
    </p>
    <ul>
  		<li>Ana sayfaya gitmeden arama yapabileceğiniz bir arama kutusu</li>
  		<li>Sıralama, Ürün Sayısı, Ürün Fotoğraf Gösterme/Gizleme yapabileceğiniz seçim kutuları</li>
  		<li>Fiyat, Marka ve Mağaza Filtreleri</li>
  		<li>Paylaşım yapabilmenize olanak tanıyan Paylaş kutusu</li>
  		<li>Ürün Fotoğrafı, İsmi, Fiyatı, Kaynağı, Markası, Son Güncellenme Tarihi bilgilerinin yer aldığı ürün bölümü</li>
  		<li>Ve, listelenen ürüne benzer ürünleri kolaylıkla bulabileceğiniz Benzer Ürünler düğmesi bulunur.</li>
	</ul>
	<p>
	Yapmış olduğunuz sorgulama sonrası çok fazla sayıda ürün bulunmuşsa, daha kolay karar vermenize yardımcı olmak için karniyarik, Yüksek Sonuç Döndü paneli görüntüler.
	Bu panelde sorgu içerisinde girmiş olduğunuz ürün ya da markanın alt ürünleri listelenir. Örneğin; <a href="http://www.karniyarik.com/%C3%BCr%C3%BCn/ne-kadar/siemens">“siemens”</a> kelimesinden oluşan bir sorgu sonrası aşağıdaki gibi bir Yüksek Sonuç Döndü paneli görüntülenecek ve dilerseniz bu sorgu önerilerinden birini seçmenizi isteyecektir:
	</p>
	<img alt="sorgu önerisi" src="images/help/sorgu_onerisi.JPG"/><br />
	<p>
	Yüksek Sonuç Döndü panelinde önerilen bir sorguyu seçebileceğiniz gibi, sayfada bulunan Fiyat, Marka ve Mağaza filtrelerinden bir ya da birkaçını seçerek de aradığınız ürünü kolayca bulabilirsiniz.
	</p>
    
	<h3 id="fiyat_filtresi">Fiyat Filtresi</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    Fiyat filtresi panelindeki fiyat aralıklarından birinin üzerine tıklayabilir veya Fiyat Aralığı kendi istediğiniz bir aralığı girip Filtrele düğmesine tıklayabilirsiniz. Sadece seçmiş olduğunuz fiyat aralığına uygun ürünler listelenir.
	</p>
	<b>Detaylı Yardım:</b><br />
    <p>
    Sorgulama sonucunda bulunmuş ürünler üzerinde uygulayabileceğiniz filtrelerden birisi Fiyat Filtresidir. Bu filtre sayesinde sadece belirli bir fiyat aralığındaki ürünleri görme imkanına sahip olursunuz.
	</p>
	<p>
	Fiyat panelinde yapmış olduğunuz sorgulama sonrası bulunan ürünlerin fiyatları, karnıyarık tarafından belirli aralıklarda görüntülenir. Örneğin; 77 TL’den Küçük, 77 TL – 219 TL arası, gibi.  Bu fiyat aralıklarından herhangi birine tıklamanız durumunda, daha önce yapmış olduğunuz sorgulama sonrası listenmiş ürünler içerisinden <b><i>sadece</i></b> fiyatı belirlediğiniz aralıkta olan ürünler listelenir. Örneğin 77 TL – 219 TL gibi bir fiyat aralığına tıklamanız durumunda, fiyatı 77 TL ile 219 TL arasındaki ürünler listelenecektir.
	</p>
	<p>
	Fiyat filtresi uygulandıktan sonra fiyat aralıklarının görüntülendiği panel kapanacak ve Uygulanmış Filtreler bölümde uygulamış olduğunuz filtre görünecektir.
	</p>
	<p>
	Kaldır düğmesine tıklandığında Uygulanmış Filtre silinecek ve Fiyat Filtresi ilk haline dönecektir.
	</p>
	<p>
	Bir diğer fiyat filtresi uygulama yöntemi de Fiyat Aralığı alanına kendi istediğiniz bir aralığı girmektir.
	Fiyat Aralığı alanında, rakamla, Alt Fiyat Sınırı veya Üst Fiyat Sınırı veya hem Alt Fiyat hem de
	Üst Fiyat sınırı belirledikten sonra Filtre düğmesine tıklamanız yeterlidir.
	Örneğin; <b><i>sadece</i></b> fiyatı 60 TL’den büyük ürünlerle ilgileniyorsanız, Fiyat Aralığındaki ilk alana 60 yazıp
	Filtrele düğmesine tıklamanız yeterlidir. Benzer şekilde, fiyatı 200 TL’den düşük ürünleri bulmak için de,
	Fiyat Aralığındaki sağ alana 200 yazıp Filtrele düğmesine tıklamalısınız. Belirli bir fiyat aralığı ile ilgileniyorsanız,
	bu durumda da ilk alana 60 ikinci alana da 200 yazıp, Filtrele düğmesine tıklamanız yeterlidir.
	</p>
 
 
	<h3 id="marka_filtresi">Marka Filtresi</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    Marka filtresi panelindeki marka isimleri yanında seçim kutularından bir veya daha fazlasını seçip, Filtrele düğmesine tıklayabilirsiniz. Sadece seçmiş olduğunuz Markalara uygun ürünler listelenir.
	</p>
	<b>Detaylı Yardım:</b><br />
    <p>
    Sorgulama sonucunda bulunmuş ürünler üzerinde uygulayabileceğiniz filtrelerden bir diğeri Marka Filtresidir. Aynı ürün ismine sahip değişik markalar olabilmektedir; örneğin Cep Telefonu kelimeleri ile sorgulama yaptığınızda Nokia, Siemens, HTC, Motorola gibi pek çok farklı markaya ait ürün listelenecektir.
	</p>
	<p>
	Marka filtresi ile, yapmış olduğunuz sorgulamayı daha da özelleştirebilirsiniz. 
	</p>
	<p>
	Marka Filtresi panelinden görüntülenmesini istediğiniz marka veya markaların isimlerinin yanlarında bulunan seçim kutularına tıklayıp seçili hale getirdikten sonra, Filtre düğmesine tıklamanız yeterlidir.
	</p>
	<p>
	Filtrele düğmesine tıklandıktan sonra seçilmiş olan markalar, Uygulanmış Filtreler panelinde görüntülenecektir.
	</p>
	<p>
	Fiyat Filtresi bölümünde anlatıldığı gibi, eğer Fiyat Aralığı alanına girilmiş fiyat bilgileri varsa, Filtrele düğmesine tıkladığınızda hem Marka hem de Fiyat filtrelerinin birlikte uygulandığını görürsünüz. Örneğin; Cep Telefonu kelimeleri ile bir sorgulama yaptığınızı düşünelim. Fiyat Aralığı alanına 60 – 133 rakamlarını girdikten sonra Marka filtresi panelinden Nokia markasının seçim kutusuna tıklayıp, Filtrele düğmesine tıklarsanız, <b>sadece</b> fiyatı 60 TL ile 133 TL arasında olan Nokia marka cep telefonları listelenecektir.
	</p>
	<p>
	Uygulanmış Filtreler panelindeki Kaldır düğmesine tıkladığınızda da uygulamış olduğunuz filtreler silinecektir.
	</p>
 
 
	<h3 id="magaza_filtresi">Mağaza Filtresi</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    Mağaza filtresi panelindeki mağaza isimleri yanında seçim kutularından bir veya daha fazlasını seçip, Filtrele düğmesine tıklayabilirsiniz. Sadece seçmiş olduğunuz mağazalardaki ürünler listelenir.
	</p>
	<b>Detaylı Yardım:</b><br />
    <p>
    Sorgulama sonucunda bulunmuş ürünler üzerinde uygulayabileceğiniz filtrelerden bir diğeri Mağaza Filtresidir. Mağaza Filtresinin en faydalı özelliği, aynı ürünü satan pek çok mağazadan hangisinde daha uygun fiyatlı ürünlerin olduğunu görebilmenize imkan sağlamasıdır.
	</p>
	<p>
	Mağaza Filtresi panelinden görüntülenmesini istediğiniz mağaza veya mağazaların isimlerinin yanlarında bulunan seçim kutularına tıklayıp seçili hale getirdikten sonra, Filtre düğmesine tıklamanız yeterlidir.
	</p>
	<p>
	Filtrele düğmesine tıklandıktan sonra seçilmiş olan mağazalar, Uygulanmış Filtreler panelinde görüntülenecektir.
	</p>
	<p>
	Fiyat ve Marka Filtrelerinde olduğu gibi, Mağaza filtresi seçiminde de, eğer daha önceden seçilmiş Fiyat ve/veya Marka filtresi varsa, Mağaza filtreleri seçilip Filtrele düğmesine tıklandığında sadece Mağaza filtresi değil Fiyat ve/veya Marka filtreleri de uygulanacaktır.
	</p>
	<p>
	Örneğin; Cep Telefonu kelimeleri ile bir sorgulama yaptığınızı düşünelim. Sırasıyla; önce Fiyat Aralığı alanına 680 – 750 rakamlarını girdiniz, sonra Marka filtresi panelinden Blackberry markasının seçim kutusuna tıkladınız, ardında da Mağaza filtresi panelinden hepsiburada değerini seçtiniz. Filtrele düğmesine tıkladığınızda, <b><i>sadece</i></b> fiyatı 680 TL ile 750 TL arasında olan hepsiburada mağazasında satılan Blackberry marka cep telefonları listelenecektir.
	</p>
	<p>
	Görüldüğü üzere filtreleme özellikleri isterseniz basit filtreleme yapma isterseniz de karmaşık filtreleme yapma imkanı tanımaktadır.
	</p>
	<p>
	Uygulanmış Filtreler panelindeki Kaldır düğmesine tıkladığınızda da uygulamış olduğunuz filtreler silinecektir.
	</p>
 
	<h3 id="siralama">Sıralama Kutusu</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    Sorgu ile Benzerliğe Göre, Ucuzdan Pahalıya Göre veya Pahalıdan Ucuza Göre sıralama yapabilmek için Sıralama seçim kutusunu kullanabilirsiniz.
	</p>
	<b>Detaylı Yardım:</b><br />
    <p>
    Sıralama kutusu, yapmış olduğunuz sorgulama sonrası listelenen ürünlerin 3 değişik şekilde yeniden sıralanmasını sağlamak amacıyla kullanılmaktadır.
	</p>
	<ul>
		<li><b>Sorgu İle Benzerliğe Göre:</b>
		Yapmış olduğunuz her sorgulama sonucu bulunan ürünler, ilk başta Sorgu İle Benzerliğe Göre seçeneği ile sıralanır.
		</li>
		<li><b>Ucuzdan Pahalıya Göre:</b>
		Sırala kutusundan Ucuzdan Pahalıya Göre seçeneğini seçtiğinizde, sorgulama sonucunda bulunmuş olan ürünlerin fiyatları ucuzdan pahalıya doğru artan bir sırada sıralanır.
		</li>
		<li><b>Pahalıdan Ucuza Göre:</b>
		Sırala kutusundan Pahalıdan Ucuza Göre seçeneğini seçtiğinizde, sorgulama sonucunda bulunmuş olan ürünlerin fiyatları pahalıdan ucuza doğru azalan bir sırada sıralanır.
		</li>
	</ul>
  
	<h3 id="urun_sayisi">Ürün Sayısı Kutusu</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    Her sayfada 15, 30 veya 50 adet ürün listelemek için, Ürün Sayısı seçim kutusunu kullanabilirsiniz.
	</p>
	<b>Detaylı Yardım:</b><br />
    <p>
    Yapmış olduğunuz sorgulama sonucunda karnıyarık ilk olarak her sayfada 15 adet ürün gösterir. Eğer ürünlerin gösterildiği sayfada 30 veya 50 adet ürün gösterilmesini istiyorsanız, Ürün Sayısı kutusundan 30 veya 50 değerini seçmeniz yeterlidir. 
	</p>
 
 	<h3 id="urun_fotografi">Ürün Fotoğrafı Gösterme</h3>
    <b>Özet Yardım:</b><br/>
    <p>
    Ürünlerin fotoğraflarını gizlemek veya görüntülemek için Fotoğraf seçim kutusunu kullanabilirsiniz.
	</p>
	<b>Detaylı Yardım:</b><br />
    <p>
    Yapmış olduğunuz sorgulama sonucunda karnıyarık ilk olarak ürünlerin fotoğraflarını gösterir.
    </p>
    <p>
    Fotoğrafı olmayan ürünler için de ortak bir fotoğraf gösterir;<br />
    <img alt="sorgu önerisi" src="<%=request.getContextPath()%>/images/nophoto.gif"/>
    </p>
    <p>
	Ürünlerin fotoğraflarını gizlemek için Fotoğraf kutusundan Gizle değerini seçebilirsiniz. Ürünlerin fotoğrafları gizlendikten sonra, eğer fotoğrafları tekrar görüntülemek istiyorsanız, yine aynı kutudan Göster değerini seçmeniz yeterlidir.
	</p>
 
	<h3 id="paylas">Paylaş</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    Paylaş özelliği, sorgulama sonucu bulduğunuz ürün veya ürünleri diğer insanlarla paylaşmanıza olanak tanır. Paylaş bağlantısı yapmış olduğunuz sorgulamayı paylaşmanıza imkan tanırken, belirli bir ürünün sağ tarafında bulunan Paylaş düğmesi sadece o ürünü paylaşmanıza imkan tanır.
	</p>
	<p>
	Şu yöntemlerden biriyle paylaşım yapabilirsiniz; E-Posta, Sık Kullanılanlar, Facebook, Live, FriendFeed, Google, Delicious, Digg ve Twitter.
	</p>
	<b>Detaylı Yardım:</b><br />
	<p>
	Paylaş özelliği, sorgulama sonucu bulduğunuz ürün veya ürünleri diğer insanlarla paylaşmanıza olanak tanır. Paylaş bağlantısı yapmış olduğunuz sorgulamayı paylaşmanıza imkan tanırken, belirli bir ürünün sağ tarafında bulunan Paylaş düğmesi sadece o ürünü paylaşmanıza imkan tanır.
	</p>
	<p>
	Paylaş bağlantısına (<img alt="sorgu önerisi" src="images/help/share_link.PNG"/>) veya düğmesine (<img alt="sorgu önerisi" src="images/help/share_button.PNG"/>) tıkladığınızda bir pencere açılacaktır. Bu pencereyi kullanarak şu yöntemlerle paylaşımda bulunabilirsiniz:
	</p>
	<ul>
		<li><b>E-Posta:</b> Bu seçeneği seçtiğinizde bir adet E-Posta penceresi açılacaktır. Bu pencerede Kime, Kimden ve Mesaj alanları ile Gönder ve İptal düğmeleri bulunur.
			<ul>
				<li>Kime: Paylaşımda bulunmak istediğiniz kişi veya kişilerin E-Posta adreslerini Kime kutusuna yazabilirsiniz. Eğer birden fazla E-Posta adresi yazmak isterseniz, adreslerin arasına virgül koymanız gerekmektedir. Örneğin; Elif Turan ve Osman Gündüz isimli kişilerin e-posta adresleri, sırasıyla, “e.t.uran@hotmail.com” ve “o.gndz@hotmail.com” olsun. Bu durumda, Kime alanına e.t.uran@hotmail.com, o.gndz@hotmail.com yazmanız yeterlidir.</li>
				<li>Kimden: Bu alana kendi E-Posta adresinizi yazmanız gerekmektedir. Örneğin; E-Posta adresinizin a.basar@hotmail.com olduğunu varsayarsak, bu alana a.basar@hotmail.com yazmanız yeterlidir.</li>
				<li>Mesaj: Bu alana isteğe bağlıdır. Boş bırakabilir, ya da dilerseniz, E-Posta’yı gönderdiğiniz kişilerin okuması için bir mesaj yazabilirsiniz.</li>
				<li>E-Posta penceresinin örnek olarak doldurulmuş hali şu şekilde görünecektir:<img alt="sorgu önerisi" src="images/help/email.PNG"/><br/></li>				
				<li>Yapmış olduğunuz sorgulamada geçen kelimeleri veya sayfanın adresini ayrıca yazmanıza gerek yoktur; bu bilgiler E-Postaya otomatik olarak eklenmektedir.</li>
				<li>E-Postayı göndermeden E-Posta penceresini kapatmak için pencerenin sağ üst köşesindeki X düğmesine tıklayabilir ya da İptal düğmesine tıklayabilirsiniz. Her iki durumda da E-Posta penceresi kapanır ve girmiş olduğunuz bilgiler kaybolur.</li>
				<li>E-Postayı göndermek için, alanları doldurduktan sonra Gönder düğmesine tıklamanız yeterlidir. “Mesajınız gönderildi” bilgi mesajı görüntülenecektir. E-Posta penceresini kapatmak için X düğmesine tıklamanız yeterlidir.</li>
			</ul>
		</li>	
		<li><b>Sık Kullanılanlar:</b> Paylaş kutusundan Sık Kullanılanlar değerini seçmeniz halinde, kullanmakta olduğunuz internet gezgininin Sık Kullanılanlar (veya Yer İmleri) ekranı açılır. Karniyarik.com’da bulunduğunuz sayfanın adı ve adresi otomatik olarak ilgili alanlara yazılır. Eğer isterseniz, bu bilgilerde değişiklik yapabilirsiniz. Ekle (veya Tamam) düğmesine tıkladığınızda, karniyarik.com’da yapmış olduğunuz sorgulama Sık Kullanılanlar listenize eklenir.</li>
		<li><b>Facebook:</b> Paylaş kutusundan Facebook değerini seçmeniz halinde, karniyarik.com sizi Facebook sayfasına yönlendirecektir. Eğer Facebook’ta bir oturumunuz açıksa, profilinize not ekleme ekranı açılacaktır. Oturumunuz açık değilse önce oturum açmanız istenecektir.</li>
		<li><b>Live:</b> Paylaş kutusundan Live değerini seçmeniz halinde, karniyarik.com sizi Windows Live sayfasına yönlendirecektir. Eğer Windows Live’da bir oturumunuz açıksa, Windows Live üzerinde paylaşım yapma ekranı açılacaktır. Oturumunuz açık değilse önce oturum açmanız istenecektir.</li>
		<li><b>FriendFeed:</b> Paylaş kutusundan FriendFeed değerini seçmeniz halinde, karniyarik.com sizi FriendFeed sayfasına yönlendirecektir. Eğer FriendFeed’de bir oturumunuz açıksa, FriendFeed üzerinde paylaşım yapma ekranı açılacaktır. Oturumunuz açık değilse önce oturum açmanız istenecektir.</li>
		<li><b>Delicious:</b> Paylaş kutusundan Delicious değerini seçmeniz halinde, karniyarik.com sizi Delicious sayfasına yönlendirecektir. Eğer Delicious’da bir oturumunuz açıksa, Delicious üzerinde paylaşım yapma ekranı açılacaktır. Oturumunuz açık değilse önce oturum açmanız istenecektir.</li>
		<li><b>Google:</b> Paylaş kutusundan Google değerini seçmeniz halinde, karniyarik.com sizi Google sayfasına yönlendirecektir. Eğer Google’da bir oturumunuz açıksa, Google üzerinde paylaşım yapma ekranı açılacaktır. Oturumunuz açık değilse önce oturum açmanız istenecektir.</li>
		<li><b>Twitter:</b> Paylaş kutusundan Twitter değerini seçmeniz halinde, karniyarik.com sizi Twitter sayfasına yönlendirecektir. Eğer Twitter’da bir oturumunuz açıksa, Twitter üzerinde paylaşım yapma ekranı açılacaktır. Oturumunuz açık değilse önce oturum açmanız istenecektir.</li>
		<li><b>Digg:</b> Paylaş kutusundan Digg değerini seçmeniz halinde, karniyarik.com sizi Digg sayfasına yönlendirecektir. Eğer Digg’de bir oturumunuz açıksa, Digg üzerinde paylaşım yapma ekranı açılacaktır. Oturumunuz açık değilse önce oturum açmanız istenecektir.</li>
	</ul>
 
	<h3 id="benzer_urunler">Benzer Ürünler</h3>
	<b>Özet Yardım:</b><br/>
    <p>
    Tam olarak aradığınız ürünü bulamadığınızda veya bulduğunuz ürüne benzer başka ne tür ürünler olduğunu görmek istediğinizde Benzer Ürünler düğmesine tıklamanız yeterlidir.
	</p>
	<b>Detaylı Yardım:</b><br />
	<p>
	İnternet üzerinde alışveriş hizmeti veren pek çok mağaza vardır. Bu mağazalardaki ürün isimleri ve/veya bilgileri bazen birbirinden farklılıklar gösterebilmektedir. Benzer Ürünler düğmesi, farklı alışveriş sitelerinde aynı ürün için birbirinden farklı isim ve/veya bilgiler kullanılmış olsa bile, karniyarik.com kullanıcıların aradıkları ürünü zahmetsizce bulabilmelerine imkan tanımaktadır.
	</p>
	<p>
	Sorgulama sonrası ürünlerin listelendiği sayfada her bir ürünün bulunduğu satırın sağ tarafında Benzer Ürünler düğmesi görüntülenir. Tam olarak aradığınız ürünü bulamadığınızda veya bulduğunuz ürüne benzer başka ne tür ürünler olduğunu görmek istediğinizde Benzer Ürünler düğmesine tıklamanız yeterlidir.
	</p>
	<p>
	Bu düğmeye tıkladığınızda karniyarik.com, yeni bir sayfa açar ve seçili olan ürünün isim, marka gibi bilgilerini kullanarak, seçili olan ürüne benzer ürünleri listeler.
	</p>
	<p>
	Örneğin; Nokia 6710 Navigator Cep Telefonu isimli ürünün yanında bulunan Benzer Ürünler düğmesine tıklandığında, Nokia markasına ait  Nokia 6710 Navigator, Nokia 6210 Navigator,  Nokia 6710 Titanium Cep Telefonu gibi farklı ürünleri listeler.
	</p>
 
	<h3 id="kaynak">Ürün Kaynağı</h3>
	<p>
	Sorgulama sonucunda listelenen ürünlerin isim bilgisi altında Kaynak bilgisi görüntülenir. Kaynak, o ürün bilgisinin hangi alışveriş sitesinden (örneğin; Ereyon, HepsiBurada, E-Bebek, gibi) alındığını gösterir.
	</p>

	<h3 id="marka">Ürün Markası</h3>
	<p>
	Sorgulama sonucunda listelenen ürünlerin isim bilgisi altında Marka bilgisi görüntülenir. Marka, o ürünün markasının ne olduğunu gösterir.
	</p>

	<h3 id="son_guncelleme">Son Güncelleme Tarihi</h3>
	<p>
	<span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span>, sorgulamalarınızda en güncel ürün ve fiyat bilgilerine ulaşabilmeniz için, belirli aralıklarla alışveriş siteleri ziyaret edip, ürün bilgilerini toplamaktadır. 
	</p>
	<p>
	Sorgulama sonucunda listelenen ürünlerin isim bilgisi altında Son Güncelleme bilgisi görüntülenir. Son Güncelleme bilgisi, o ürünün isim ve fiyat bilgisinin ilgili alışveriş sitesinden en son hangi tarih ve saatte alındığını gösterir.
	</p>

	<h3 id="fiyat">Ürün Fiyatı</h3>
	<p>
	Sorgulama sonucunda listelenen ürünlerin isim bilgisinin sağ tarafında Fiyat bilgisi görüntülenir. Fiyat bilgisi Türk Lirası cinsinden gösterilmektedir.
	</p>

	<h3 id="bulunan_urun">Bulunan Ürün Sayısı</h3>
	<p>
	<span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span>, yapmış olduğunuz sorgulama sonucunda kaç saniyede kaç adet siteden kaç adet ürün bulduğu bilgisini sayfanın sağ üst köşesinde görüntüler. Örneğin; “0.53 sn'de 49 siteden 4930 ürün bulundu”.
	</p>
	
	<h3 id="hakkimizda">Hakkımızda Sayfası</h3>
	<p>
	Hakkımızda sayfasında <span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span> hakkındaki teknik bilgilere ve karniyarik.com Geliştirme Takımına ait bilgilere ulaşabilirsiniz. 
	</p>
	
	<h3 id="siteler">Bilgi Toplanan Siteler Sayfası</h3>
	<p>
	Bilgi Toplanan Siteler sayfasında <span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span>’un bilgi topladığı tüm çevrimiçi alışveriş siteleri görüntülenmektedir. Toplam kaç alışveriş sitesinden, kaç adet markaya ait kaç adet ürün bilgisinin kayıtlı olduğu bilgisine buradan erişebilirsiniz.
	</p>

	<h3 id="iletisim">İletişim Sayfası</h3>
	<p>
	İletişim sayfası, karniyarik.com ziyaretçilerinin Karnıyarık Yazılım Teknolojileri Ltd. Şti. ile iletişim kurabilmelerine olanak sağlayan sayfadır.
	</p>
	<p>
	İster bu sayfada verilen E-Posta adresini kullanarak isterseniz de İletişim Formunu kullanarak Görüş, Öneri, İstek, İş Ortaklığı gibi konular karniyarik.com ekibi ile iletişim kurabilirsiniz.
	</p>


 	<h3 id="neredenaldin">NeredenAldın.com</h3>
 	<b>Özet Yardım:</b><br/>
    <p>
    Alışveriş sitelerine, o alışveriş sitesini kullanmış olan kullanıcıların verdiği puanları gösteren bir hizmet sitesidir.
	</p>
	<b>Detaylı Yardım:</b><br />
	<p>
	<span class="vcard"> <a class="fn org url" href="http://www.karniyarik.com"><span class="n">karniyarik.com</span></a></span>, ziyaretçilerine alışveriş kararı verirken daha fazla yardımcı olabilmek amacıyla, <a href="http://www.neredenaldin.com">NeredenAldin.com</a> sitesiyle iş ortaklığı yapmaktadır.
	</p>
	<p>
	<a href="http://www.neredenaldin.com">NeredenAldin.com</a>, E-Ticaret sitelerini, o siteleri kullanan kullanıcıların hizmet kalitesine verdikleri puanları derleyerek, görüntüleyen bir hizmet sitesidir.
	</p>
	<p>
	Karniyarik.com, yapmakta olduğu iş ortaklığı ile, yapmış olduğunuz sorgulama sonucunda görüntülenen Mağaza filtresinde ve Bilgi Toplanan Siteler sayfasında, ilgili mağazaya ait puanı görüntüler.
	</p>


	<h3 id="opensearch">Gezgin Arama Eklentisi</h3>
	<p>
		Eğer kullanıdığınız gezgin (Firefox, Internet Explorer, Opera, Chrome vs.) gezgin içersinden aramayı destekliyorsa, Karnıyarık'ı gezginin arama kutusuna ekleyebilirsiniz. 
		Bu tip gezginlerde gezginin sol üst köşesinde bir arama kutusu bulunmaktadır. Bu arama kutusunda bulunan menü ile Karnıyarık'ı gezgin aramasına ekleyebilirsiniz.
		<br/>
		<img alt="opensearch örnek resmi" src="images/help/opensearch.gif"/>
	</p>		
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="commonfooter.jsp"></jsp:include>
</body>
</html>