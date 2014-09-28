<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("Veri Beslemeleri", "datafeed", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" 
 xmlns:fb="http://www.facebook.com/2008/fbml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>Veri Beslemeleri | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Alışveriş, ikinci el ilan, şehir fırsatı ya da otel sitenizdeki bilgileri Karniyarik.com'a otomatik olarak göndermek için kullanabileceğiniz veri beslemesi şemaları, avantajları ve diğer bilgiler." />
<meta name="keywords" content="Karniyarik.com, alışiveriş, ürün, otel, şehir fırsatı, ikinci el araba, veri beslemesi şeması" />
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
  <div class="sresult"><h1>Veri Beslemeleri</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
    <div class="lc full">
      <div class="prop">
        <div class="c1 text">
		    <ul>
			    <li><a href="#nedir">Nedir?</a></li>
			    <li><a href="#sema">Şemalar</a></li>
			</ul>
        </div>
      </div>
    </div>
	<div class="content">
	<br/>
	<div>
		<h3 id="nedir">Nedir</h3>
		<img src="img/feed.png" style="float:right;padding:10px;"/>
		<p>Karnıyarık mağazalarınızdan ve web sistemlerinizden ağ örümcekleri ile verilerinizi toplayabilme kabiliyetine sahiptir. Fakat isterseniz
		veri beslemelerini de kullanarak Karnıyarık'da ürünlerinizin listelenmesini sağlayabilirsiniz. Veri beslemeleri sitenizde bulunan bir URL'e 
		otomatik olarak koyacağınız XML formatında bir dosya olarak ifade edilebilir. Bu formatın açıklamaları ve örnekleri aşağıdaki kısımlarda verilmiştir.</p>
		<p>Veri beslemelerinin ağ örümceklerine avantajları aşağıdaki gibidir:</p>
		<ul>
			<li>Karnıyarık Ağ Örümcekleri sitenizde gezinirken öncelikli olarak sisteminizi yormamaya çalışmaktadır. Bu nedenle sitenin
			cevap verme hızı dahil olmak üzere belli kriterlere göre sitenizden sayfa indirme frekansını otomatik olarak ayarlamaktadır.
			Bu sayede sisteminiz yorulmamaktadır ama sitenizdeki sayfa sayısına göre örümcekler ile sayfa toplama işlemi zaman alabilmektedir.
			Son kullanıcıya ise bu sistemin yansıması verilerin yenilenmesi frekansı açısından olmaktadır. Eğer verilerinizin
			daha sıklıkla güncellenmesini istiyorsanız Karnıyarık olarak veri beslemelerini kullanmanızı öneriyoruz.</li>
			<li>Ağ örümcekleri ile siteniz gezinirken Karnıyarık'da listelenen verileriniz sayfalarınızın içerisinden otomatik olarak çıkarılmaktadır.
			Bu belli sayfalarda hata payını arttırmaktadır. Bu konuda sıkıntılar yaşıyorsanız veri beslemeleri sayesinde 
			hata payını sıfıra indirebilirsiniz.</li>
			<li>Ağ örümcekleri sitenizi ziyaret ettiğinde sitenizdeki sayfa sayısına göre sitenizden veri indirmektedir. Her bir sayfanın yaklaşık olarak 
			200 KB olduğunu varsayarsanız sitenizden indirilen veri miktarı yüksek sayılara ulaşabilmektedir. Sunucu barındırma hizmetinde kısıtlar olan
			siteler için bu siteye belli bir veri indirme yükü getirebilmektedir. Öte yandan veri beslemeleri ile sitenizden indirilen veri miktarını 
			yüzlerce kat aşağıya indirebilirsiniz.</li>
		</ul>
	</div>
	<div>
		<h3 id="sema">Şemalar</h3>
		<div style="width:400px;margin:10px;float:left;">
			<h4>Ürün Veri Besleme Şeması</h4>
			<ul>
				<li>Şema (XSD) Dosyası: <a target="_blank" href="xsd/product.xsd">http://www.karniyarik.com/datafeed/xsd/product.xsd</a></li>
				<li>Örnek XML Dosyası: <a target="_blank" href="sample/urun.xml">http://www.karniyarik.com/datafeed/sample/urun.xml</a></li>
			</ul>
			
			<table class="apitable" style="width:160px;height:160px;">
			<tr>
				<th>Şema Şekli</th>
			</tr>
			<tr>
				<td><a class="iframe" href="img/product.png"><img src="img/product.png" style="width:150px;height:150px;"/></a></td>
			</tr>
			</table>
		</div>
		
		<div style="width:400px;margin:10px;float:left;">
			<h4>Araç Veri Besleme Şeması</h4>
			<ul>
				<li>Şema (XSD) Dosyası: <a target="_blank" href="xsd/car.xsd">http://www.karniyarik.com/datafeed/xsd/car.xsd</a></li>
				<li>Örnek XML Dosyası: <a target="_blank" href="sample/araba.xml">http://www.karniyarik.com/datafeed/sample/araba.xml</a></li>
			</ul>
			
			<table class="apitable" style="width:160px;height:160px;">
			<tr>
				<th>Şema Şekli</th>
			</tr>
			<tr>
				<td><a class="iframe" href="img/car.png"><img src="img/car.png" style="width:150px;height:150px;"/></a></td>
			</tr>
			</table>
		</div>
		
		<div style="width:100%;margin:10px;float:left;">
			<h4>Ürün Veri Besleme Şeması Açıklamaları</h4>
			<table class="apitable" style="width:100%;height:100%;">
			<tr>
				<th>XML Eleman Adı</th>
				<th>Açıklaması</th>
				<th>Kısıtlar</th>
			</tr>
			<tr>
				<td>isim</td>
				<td>
					Ürünün ismi <br/>
					<b>Ör:</b> &lt;isim&gt;Polietilen Tahta 25 X 40 X 2&lt;/isim&gt; 
				</td>
				<td>Bulunması zorunlu bir elemandır.</td>
			</tr>
			<tr>
				<td>fiyat</td>
				<td>
					Ürünün fiyatı. Ondalık kısmın noktayla, binlik kısımların virgülle ayrılması gerekmektedir. <br/>
					<b>Ör:</b> &lt;fiyat&gt;20.75&lt;/fiyat&gt;
				</td>
				<td>Bulunması zorunlu bir elemandır.</td>
			</tr>
			<tr>
				<td>id</td>
				<td>
					Ürünün sisteminizdeki tekil numarası<br/>
					<b>Ör:</b> &lt;id&gt;NTBTSBU500-1DK&lt;/id&gt;
				</td>

				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>marka</td>
				<td>
					Ürünün markası<br/>
					<b>Ör:</b> &lt;marka&gt;Cinoxsan&lt;/marka&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			<tr>
				<td>model</td>
				<td>
					Ürünün modeli<br/>
					<b>Ör:</b> &lt;model&gt;PT-3245&lt;/model&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			<tr>
				<td>birim</td>
				<td>
					Ürün fiyatının para birimidir.<br/>
					<b>Ör:</b> &lt;birim&gt;TL&lt;/birim&gt;
				</td>
				<td>Opsiyoneldir. Varsayılan değeri TL dir.</td>
			</tr>
			<tr>
				<td>kategori</td>
				<td>
					Ürünün kategorisi.<br/>
					<b>Ör:</b> &lt;kategori&gt;HAZIRLIK EKİPMANLARI &amp; KESME TAHTALARI&lt;/kategori&gt;<br/>
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			<tr>
				<td>urun_url</td>
				<td>
					Ürün bilgilerinin bulunduğu adrestir.<br/>
					<b>Ör:</b> &lt;urun_url&gt;http://www.test.com/index.php?do=catalog/product&amp;pid=618&lt;/urun_url&gt;<br/>
				</td>
				<td>Bulunması zorunlu bir elemandır.</td>
			</tr>
			<tr>
				<td>resim</td>
				<td>
					Ürün resminin bulunduğu adrestir.<br/>
					<b>Ör:</b> &lt;resim&gt;http://www.test.com/modules/catalog/products/pr_01_618_max.jpg&lt;/resim&gt;<br/>
				</td>
				<td>Opsiyoneldir.</td>
			</tr>		
													
			</table>
	</div>	
		
	<div style="width:100%;margin:10px;float:left;">
			<h4>Araç Veri Besleme Şeması Açıklamaları</h4>
			<table class="apitable" style="width:100%;height:100%;">
			<tr>
				<th>XML Eleman Adı</th>
				<th>Açıklaması</th>
				<th>Kısıtlar</th>
			</tr>
			<tr>
				<td>isim</td>
				<td>
					Aracın ismi <br/>
					<b>Ör:</b> &lt;isim&gt;Satılık İkinci El Oto Porsche 911 Turbo&lt;/isim&gt; 
				</td>
				<td>Bulunması zorunlu bir elemandır.</td>
			</tr>
			<tr>
				<td>fiyat</td>
				<td>
					Aracın fiyatı. Ondalık kısmın noktayla, binlik kısımların virgülle ayrılması gerekmektedir. <br/>
					<b>Ör:</b> &lt;fiyat&gt;177500.00&lt;/fiyat&gt;
				</td>
				<td>Bulunması zorunlu bir elemandır.</td>
			</tr>
			<tr>
				<td>id</td>
				<td>
					Aracın sisteminizdeki tekil numarası<br/>
					<b>Ör:</b> &lt;id&gt;PRSCHE-911TS&lt;/id&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>marka</td>
				<td>
					Aracın markası<br/>
					<b>Ör:</b> &lt;marka&gt;Porsche&lt;/marka&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>birim</td>
				<td>
					Araç fiyatının para birimidir.<br/>
					<b>Ör:</b> &lt;birim&gt;EUR&lt;/birim&gt;
				</td>
				<td>Opsiyoneldir. Varsayılan değeri TL dir.</td>
			</tr>
			
			<tr>
				<td>kategori</td>
				<td>
					Aracın kategorisi.
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			<tr>
				<td>url</td>
				<td>
					Araç bilgilerinin bulunduğu adrestir.<br/>
					<b>Ör:</b> &lt;urun_url&gt;http://www.test.com/araba/porsche-911-turbo/44149/&lt;/urun_url&gt;<br/>
				</td>
				<td>Bulunması zorunlu bir elemandır.</td>
			</tr>
			<tr>
				<td>resim</td>
				<td>
					Araç resminin bulunduğu adrestir.<br/>
					<b>Ör:</b> &lt;resim&gt;http://www.test.com/img/cars/6068689227.JPG&lt;/resim&gt;<br/>
				</td>
				<td>Opsiyoneldir.</td>
			</tr>		
			
			<tr>
				<td>kilometre</td>
				<td>
					Aracın kaç kilometrede olduğunu gösteren xml elemanıdır.<br/>
					<b>Ör:</b> &lt;kilometre&gt;25000&lt;/kilometre&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>modelyili</td>
				<td>
					Aracın model yılıdır.<br/>
					<b>Ör:</b> &lt;modelyili&gt;2008&lt;/modelyili&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>motorgucu</td>
				<td>
					Aracın motor gücü.<br/>
					<b>Ör:</b> &lt;motorgucu&gt;480&lt;/motorgucu&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>motorhacmi</td>
				<td>
					Aracın motor hacmi<br/>
					<b>Ör:</b> &lt;motorhacmi&gt;4000&lt;/motorhacmi&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>renk</td>
				<td>
					Aracın rengi.<br/>
					<b>Ör:</b> &lt;renk&gt;Siyah&lt;/renk&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
					
			<tr>
				<td>sehir</td>
				<td>
					Aracın bulunduğu şehir.<br/>
					<b>Ör:</b> &lt;sehir&gt;İstanbul&lt;/sehir&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>vites_tipi</td>
				<td>
					Aracın vites tipi.<br/>
					<b>Ör:</b> &lt;vites_tipi&gt;Otomatik&lt;/vites_tipi&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>
			
			<tr>
				<td>yakit_tipi</td>
				<td>
					Aracın yakıt tipi.<br/>
					<b>Ör:</b> &lt;yakit_tipi&gt;Benzin&lt;/yakit_tipi&gt;
				</td>
				<td>Opsiyoneldir.</td>
			</tr>										
			</table>
	</div>
		<!-- div style="width:400px;margin:10px;float:left;">
			<h4>Otel Veri Besleme Şeması</h4>
			<ul>
				<li>Şema (XSD) Dosyası: <a target="_blank" href="xsd/otel.xsd">http://www.karniyarik.com/datafeed/xsd/otel.xsd</a></li>
				<li>Örnek XML Dosyası: <a target="_blank" href="sample/otel.xml">http://www.karniyarik.com/datafeed/sample/otel.xml</a></li>
			</ul>
			
			<table class="apitable" style="width:160px;height:160px;">
			<tr>
				<th>Şema Şekli</th>
			</tr>
			<tr>
				<td><a class="iframe" href="img/otel.png"><img src="img/otel.png" style="width:150px;height:150px;"/></a></td>
			</tr>
			</table>
		</div -->
	</div>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>