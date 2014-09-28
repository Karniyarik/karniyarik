<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("Hakkımızda", "aboutus.jsp", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" 
	xmlns="http://www.w3.org/1999/xhtml" 
 	xmlns:fb="http://www.facebook.com/2008/fbml"
 	xmlns:addthis="http://www.addthis.com/help/api-spec" 
	xmlns:og="http://opengraphprotocol.org/schema/" 
 	version="XHTML+RDFa 1.0" xml:lang="tr">
	<head>
		<title>Hakkımızda | Karnıyarık - Alışverişi yardık!</title>
		<meta name="description" content="Karnıyarık ürün arama motoru hakkında tüm bilmek istedikleriniz: yapısı, hakkındaki haberler, Karniyarik.com ile ilgili dış bağlantılar." />
		<meta name="keywords" content="Karniyarik.com, Hakkımızda, Hakkında, alışveriş, ürün arama motoru, karniyarik, dikey arama motoru" />
		<link rel="image_src" href="http://www.karniyarik.com/images/logo2.png" />
		<meta property="og:image" content="http://www.karniyarik.com/images/logo2.png"/>
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
	  <div class="sresult"><h1>Hakkımızda</h1></div>
	  <div class="cl"></div>
	  <div class="l">
	    <div class="filter full"></div>
	    <div class="cl"></div>
	    <div class="ll"></div>
	    <div class="lc full">
	      <div class="prop">
	        <div class="c1 text">
			    <ul>
				    <li><a href="#nedir">Karnıyarık Nedir?</a></li>
				    <li><a href="#haber">Haberler</a></li>   
				    <li><a href="#baglanti">Dış Bağlantılar</a></li>
				    <li><a href="#legal">Yasal Bilgi</a></li>
				    <li><a href="#oduller">Ödüller</a></li>
			    </ul>
	        </div>
	      </div>
	    </div>
		<div class="content">
			<div style="width: 716px;margin-right:10px; float: left;">
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
				<p>
				Sistemin nasıl çalıştığına dair detaylı bilgiye <a title="Karniyarik.com kullanımı ile ilgili detaylı bilgi almak için tıklayın" href="<%=request.getContextPath()%>/help.jsp">Yardım</a> sayfamızdan ulaşabilirsiniz.
				</p>
				<p>
				Her türlü istek, öneri, iş ortaklığı fırsatları için <a title="Karniyarik.com takımı ile iletişime geçmek için tıklayın" href="<%=request.getContextPath()%>/contact.jsp">İletişim</a> sayfamızdan bizimle irtibata geçebilirsiniz
				</p>
				<p>
				Karnıyarık API kullanımı ve bilgilerini <a title="Karniyarik.com API yardım sayfası" href="<%=request.getContextPath()%>/api.jsp">API</a> sayfamızda bulabilirsiniz.
				</p>			
				<p>
				Karnıyarık'ın nasıl yapıldığını öğrenmek için <a title="Karnıyarık Tarifi" href="<%=request.getContextPath()%>/karniyarik-tarifi/">Karnıyarık Tarifi</a> sayfamızı ziyaret edebilirsiniz.
				</p>			
	
				<h3 id="baglanti">Dış Bağlantılar</h3>
				<ul>
				<li><img src="<%=request.getContextPath()%>/images/socials/facebook16.png" alt="Facebook"/><a href="http://www.facebook.com/pages/Karniyarik/90334878760" target="_blank">Karnıyarık Facebook sayfası.</a></li>			
				<li><img src="<%=request.getContextPath()%>/images/socials/etohum16.png" alt="eTohum"/><a href="http://eambar.etohum.com/sirketler/karniyarik-com" target="_blank">eTohum'da Karnıyarık</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/twitter16.png" alt="Twitter"/><a href="http://twitter.com/karniyarik" target="_blank">Twitter'da Karnıyarık</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/linkedin16.png" alt="LinkedIn"/><a href="http://www.linkedin.com/companies/karniyarik" target="_blank">LinkedIn'de Karnıyarık</a> [ingilizce]</li>				
				<li><img src="<%=request.getContextPath()%>/images/socials/crunchbase16.png" alt="Crunchbase"/><a href="http://www.crunchbase.com/company/karniyarik" target="_blank">Crunchbase'de Karnıyarık tanıtımı</a> [ingilizce]</li>
				<li><a href="<%=request.getContextPath()%>/images/basin/as_kasim_aralik_2010_1.png" target="_blank">AS Dergisi'nde Karnıyarık hakkında yazı (S1)</a> (Dergi için ayrıca <a href="http://www.as-medya.net/dergi-48" title="AS Medya Kasım Aralık 2010">buraya</a> tıklayınız)</li>
				<li><a href="<%=request.getContextPath()%>/images/basin/as_kasim_aralik_2010_2.png" target="_blank">AS Dergisi'nde Karnıyarık hakkında yazı (S2)</a></li>
				<li><a href="<%=request.getContextPath()%>/images/basin/platin_ekim_2010_800x1000.jpg" target="_blank">Platin Dergisi'nde Karnıyarık hakkında yazı</a></li>
				<li><a href="<%=request.getContextPath()%>/images/basin/dunya_ekim_2010.jpeg" target="_blank">Dünya yayınında Karnıyarık hakkında yazı</a></li>
				<li><a href="http://www.bilgicagi.com/Yazilar/3005-internette_karli_alisveris_icin_dikey_arama_ile_dogru_sonuc.aspx" target="_blank">Bilgiçağı.com röportajı</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/tnw16.png" alt="Thenextweb"/><a href="http://thenextweb.com/tr/2010/01/27/karniyarik-nasil-yapilir/" target="_blank">The Next Web TR'de çıkmış söyleşi</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/tnw16.png" alt="Thenextweb"/><a href="http://thenextweb.com/tr/2010/01/27/karniyarik-nasil-yapilir2/" target="_blank">The Next Web TR söyleşi - 2</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/tnw16.png" alt="Thenextweb"/><a href="http://thenextweb.com/tr/2010/05/04/karniyarigi-servis-etme-zamani/" target="_blank">The Next Web TR söyleşi - 3</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/scribd16.png" alt="Scribd"/><a href="http://www.scribd.com/doc/30806848/Karniyariktaki-teknolojiler/" target="_blank">Karnıyarık'da kullanılan teknolojiler hakkında kısa bir bilgi</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/wiki16.png" alt="Wikipedia"/><a href="http://tr.wikipedia.org/wiki/%C3%9Cr%C3%BCn_arama" target="_blank">Tükçe Wikipedia'ya hediyemiz: Ürün Arama</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/wiki16.png" alt="Wikipedia"/><a href="http://tr.wikipedia.org/wiki/Dikey_arama" target="_blank">Tükçe Wikipedia'ya hediyemiz: Dikey Arama</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/wiki16.png" alt="Wikipedia"/><a href="http://tr.wikipedia.org/wiki/Uzun_kuyruk" target="_blank">Tükçe Wikipedia'ya hediyemiz: Uzun Kuyruk</a></li>
				<li><img src="<%=request.getContextPath()%>/images/socials/eksi16.png" alt="Ekşisözlük"/><a href="http://sozluk.sourtimes.org/show.asp?t=karniyarik.com" target="_blank">Ekşisözlük'de Karnıyarık</a></li>
				<li><a href="http://www.fazlamesai.net/index.php?a=article&amp;sid=5252" target="_blank">Fazlamesai'deki yazımız</a></li>
				<li><a href="http://www.yeni.web.tr/karniyarik-com" target="_blank">yeni.web.tr'de Karnıyarık açıklaması</a></li>
				<li><a href="http://www.techno-logic.tv/2009/12/31/technologic-120-bolum/" target="_blank">TechnoLogic TV'de Karnıyarık'ın da anlatıldığı video.</a></li>
				<li><a href="http://octoberswimmer.blogspot.com/2009/04/karnyark.html" target="_blank">Bloglardan: octoberswimmer.blogspot.com</a></li>
				<li><a href="http://www.killerstartups.com/Site-Reviews/karniyarik-com-online-search-in-turkey" target="_blank">KillerStartups'ta konu olan Karnıyarık</a> [ingilizce]</li>					
				</ul>
				<h3 id="legal">Yasal Bilgi</h3>
			    <p>
			    <span class="vcard"><a class="fn org url" href="/"><span class="n">Karniyarik.com</span></a></span>, gerek 
			    mağazalar gerekse internet sitelerinden alışveriş yapan tüketicilere fiyat-ürün çeşitliliği açılarından daha 
			    bilinçli olmaları yönünde kolaylık sağlamak amacıyla servis veren bir sistemdir. Karniyarik.com üzerinden 
			    <b>herhangi bir ürün satılmaMAktadır</b>. Ürünler hakkındaki güncel bilgiler, ürünü satan mağaza üzerinde mevcuttur. 
			    İlgili mağazalarla yaşanan sorunlardan, maddi ve manevi oluşan zararlardan Karniyarik.com sorumlu değildir. 
			    Karniyarik.com kullanıcıların bilgilerini, kendilerinden izinsiz bir biçimde kullanmaz, 3. şahıslara satmaz. 
			    Karniyarik.com'da yer alan bütün marka ve isimlerin hakları ilgili firmalara aittir.
				</p>
				<h3 id="oduller">Ödüller</h3>
				<ul>
				<li>
					<a href="http://www.etohum.com/iste-2010-etohum-15-internet-girisimi" target="_blank">
					<img src="images/awards/etohum.png" alt="etohum"/>
					</a> eTohum 15
				</li>
				<li>
					<a href="http://www.innovate100.com/2010/04/istanbul-pitch-slam-semi-finalists/" target="_blank">
					<img src="images/awards/innovate100.png" alt="innovate100"/>
					</a> Innovate 100 Yarı Finalisti
				</li>
				<li>
					<a href="http://www.sanayi.gov.tr/Pages.aspx?pageID=545&lng=tr" target="_blank">
					<img src="images/awards/sanayi.png" alt="Sanayi Bakanlığı "/>
					</a> Sanayi Bakanlığı Teknogirişim Sermayesi Desteği
				</li>
				<li>
					<a href="http://www.kosgeb.gov.tr" target="_blank">
					<img src="images/awards/kosgeb1.png" alt="KOSGEB"/>
					</a> KOSGEB
				</li>
				
				</ul>
				<h3 id="logolar">Logolar</h3>
				<p>
					Sitenizde ya da uygulamanızda Karnıyarık logosunu kullanmak isterseniz 
					<a title="Karniyarik Logo" href="<%=request.getContextPath()%>/karniyarik-logo.jsp">Karniyarik Logolari</a> 
					sayfasına gözatabilirsiniz. 
				</p>
			</div>
			<div style="width: 310px;float: left;text-align: center;">
				<br/>
				<h3><a href="http://www.twitter.com/karniyarik" target="_blank">Sosyal Medya</a></h3>
				<fb:like-box profile_id="90334878760" width="300"  height="250" connections="10" stream="false" 
				header="false" css="http://www.karniyarik.com/css/facebook.css?1"></fb:like-box>
				<div id="fb-root"></div>
				<script>
			      window.fbAsyncInit = function() {
			        FB.init({appId: '115191701853270', status: true, cookie: true,
			                 xfbml: true});
			      };
			      (function() {
			        var e = document.createElement('script');
			        e.type = 'text/javascript';
			        e.src = //document.location.protocol +
			          'http://connect.facebook.net/tr_TR/all.js';
			        e.async = true;
			        document.getElementById('fb-root').appendChild(e);
			      }());
			    </script>
			    <br/>
			    <!-- h3><a href="http://www.twitter.com/karniyarik" target="_blank">Twitter</a></h3 -->
				<script src="http://widgets.twimg.com/j/2/widget.js" type="text/javascript"></script>
				<script type="text/javascript">
					new TWTR.Widget({
					  version: 2,
					  type: 'profile',
					  rpp: 5,
					  interval: 6000,
					  width: 300,
					  height: 300,
					  theme: {
					    shell: {
					      background: '#E6E6E6',
					      color: '#4D4D4D'
					    },
					    tweets: {
					      background: '#ffffff',
					      color: '#000000',
					      links: '#548600'
					    }
					  },
					  features: {
					    scrollbar: false,
					    loop: false,
					    live: false,
					    hashtags: true,
					    timestamp: true,
					    avatars: false,
					    behavior: 'all'
					  }
					}).render().setUser('karniyarik').start();
				</script> 
		  </div>
		</div>	    
	  </div>
	</div>
	<div class="cl" style="height:50px"></div>
	<div ></div>
	<jsp:include page="commonfooter.jsp"></jsp:include>
	</body>
</html>