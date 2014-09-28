<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("API", "api.jsp", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" 
	xmlns="http://www.w3.org/1999/xhtml" 
 	xmlns:fb="http://www.facebook.com/2008/fbml"
 	xmlns:addthis="http://www.addthis.com/help/api-spec" 
	xmlns:og="http://opengraphprotocol.org/schema/" 
 	version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>Karnıyarık API | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Karnıyarık API kullanım ve yardım sayfası. Karniyarik.com'un sizlere sağladığı Uygulama Programlama Arayüzü (API) sayesinde sistemlerinizde Karniyarik.com'un değerli bilgilerini ücretsiz kullanabilirsiniz." />
<meta name="keywords" content="Karnıyarık API, Uygulama Programlama Arayüzü, API, ücretsiz, REST" />
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
  <div class="sresult"><h1>Karnıyarık API</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
    <div class="lc full">
      <div class="prop">
        <div class="c1 text">
		    <ul>
			    <li><a href="#nedir">Karnıyarık API Nedir?</a></li>
			    <li><a href="#widget">Karnıyarık Widget</a></li>			    
			    <li><a href="#key">Kullanabilmek için ne gerekli?</a></li>    
			    <li><a href="#kullanim">Nasıl Kullanılır?</a></li>
			    <li><a href="#sorguornek">Sorgu ve URL örnekleri</a></li>
			    <li><a href="#sonucornek">Sonuç örnekleri</a></li>
			</ul>
        </div>
      </div>
    </div>
	<div class="content">
	<br/>
	<div>
		<div style="width: 686px;margin-right:10px;float: left;">
			<h3 id="nedir">Karnıyarık API Nedir?</h3>
		    <p>
		    Karnıyarık API (Uygulama Programlama Arayüzü) sizlerin Karnıyarık'da bulunan bilgilerle yeni uygulamalar geliştirebilmesini sağlayan bir arayüzdür.
		    Eğer bir bilgisayar programcısı iseniz ve Karnıyarık'ın şu anki web arayüzünden yapılan işlemleri ve içerisinde bulunan verileri kullanarak başka uygulamalar
		    geliştirmek istiyorsanız kullanabileceğiniz bir programala arayüzünü sizlere sunuyoruz. 
			</p>
			<p>
			Bulunan veriler Karnıyarık sisteminin son kullanıcılara hizmet sunduğu veriler ile aynıdır. Kısacası web arayüzünden yapılan sorgular ve sonucunda
			gösterilen değerlerin aynısına Karnıyarık API'yi kullanarak erişebilirisiniz.
			</p>
			<h3 id="key">Kullanabilmek için ne gerekli?</h3>
		    <p>
		    Karnıyarık API'yi kullanabilmek amacı ile bizlerden iletişim formu ya da iletişim mail adresi aracılığı ile bir API anahtarı isteyebilirsiniz. 
		    API anahtarı isteğinde bulunurken ad, soyad, email gibi bilgilerin yanında kullanılacak olan sistem hakkında bilgiyi bizlere sunmanızı rica ediyoruz. 
			</p>
		</div>
	    <div style="width: 310px;float: left;text-align: center;">
	    	<h3 id="kullananlar">Karnıyarık API Kullanıcıları</h3>
	    	<div class="apiusers">
	    	<a target="_blank" rel="nofollow" href="http://www.alirkenbak.com">
	    		<img src="<%=request.getContextPath()%>/images/apiusers/alirkenbak.png"  alt="Alirkenbak.com"></img>
	    	</a>
	    	</div>
	    	<div class="apiusers">
	    	<a target="_blank" rel="nofollow" href="http://itunes.apple.com/tr/app/id367280632?mt=8">
	    		<img src="<%=request.getContextPath()%>/images/apiusers/kacpara.png"  alt="Kac Para iPhone"></img>
	    	</a>
	    	</div>
	    	<div class="apiusers">
	    	<a target="_blank" rel="nofollow" href="http://popiler.org/popilerproducts.aspx">
	    		<img src="<%=request.getContextPath()%>/images/apiusers/popiler.png" alt="Popiler.org"></img>
	    	</a>
	    	</div>	    	
	    	<div class="apiusers">
	    	<a target="_blank" rel="nofollow" href="http://www.hediyemibul.com/">
	    		<img src="<%=request.getContextPath()%>/images/apiusers/hediyemibul.png" alt="Hediyemibul.com"></img>
	    	</a>
	    	</div>	    	
	    	<div class="apiusers">
	    	<a target="_blank" rel="nofollow" href="http://projects.celtic-initiative.org/cbdp/index.php?lang=tr">
	    		<img src="<%=request.getContextPath()%>/images/apiusers/cbdp.png" alt="Context-Based Digital Personality"></img>
	    	</a>
	    	</div>
	    </div>
    </div>
    <div style="display: block;clear:both;">
		<h3 id="widget">Karnıyarık Widget</h3>
	    <p>
	    Yine Karnıyarık API gibi sizlere hizmet veren <a href="widget/widget.jsp" title="Karnıyarık Widget">Karnıyarık Widget</a> için 
	    <a href="widget/widget.jsp" title="Karnıyarık Widget">Widget sayfasını</a> ziyaret ediniz. 
		</p>			 
		<h3 id="kullanim">Nasıl Kullanılır</h3>
	    <p>
	    	Karnıyarık API, REST mantığı ile çalışmakta ve isterseniz XML isterseniz JSON formatında sonuç dönmektedir. 
	    </p>
	    <h4>REST URL'i</h4>
	    <p>
	    	Kullanılacak olan REST son-nokta URL'i aşağıdaki gibidir:
		</p>
			<ul>
	    	<li>http://www.karniyarik.com/rest/{xml/json}/{product/car}</li>
	    	</ul>
	    <h4>URL Path parametleri</h4>
	    <p>
	    	Burada eğer JSON formatında sonuç almak istiyorsanız {xml/json} yazan yere "json", XML formatında sonuç almak isterseniz de aynı yere "xml"  
	    	yazmanız gerekmektedir. Aynı şekilde araba aramak için {product/car} yazan yere "car", ürün aramak için ise yine aynı yere "product" yazmanız 
	    	gerekmektedir.
	    </p>
	    <b>Örnek</b>	
	    <p>
	    	JSON formatında ürün aramak için şu URL'e istek gönderilmelidir:<br/>    	
	    </p>
		<ul>
	    	<li>http://www.karniyarik.com/rest/json/product</li>
	    </ul>    
		<h4>URL Query parametleri</h4>
		<p>
			Arama sorgusu ve arama kriterleri REST URL'ine parametre olarak eklenmektedir. Eklenecek parametreler aşağıdaki tabloda verilmiştir.
		</p>
		<table class="apitable">
			<tr>
				<th>Parametre adı</th>
				<th>Açıklaması</th>
				<th>Tek/Liste</th>
				<th>Kısıtlar</th>
			</tr>
			<tr>
				<td>q</td>
				<td>
					Arama sorgusu. <br/>
					<b>Ör:</b> q=cep+telefonu 
				</td>
				<td>Tek</td>
				<td>Bulunması zorunlu bir parametredir.</td>
			</tr>
			<tr>
				<td>s</td>
				<td>
					Sıralama şekli. Sıralama şekilleri ve değerleri aşağıdaki gibidir:
					<ul>
						<li>Sorgu ile benzerliğe göre: 1</li>
						<li>Fiyata göre: ucuzdan pahalıya: 2 </li>
						<li>Fiyata göre: pahalıdan ucuza: 3 </li>
					</ul>
					
					Araç arama esnasında aşağıdaki değerler de girilebilmektedir:
					<ul>
						<li>Km'ye göre: küçükten büyüğe: 4 </li>
						<li>Km'ye göre: büyükten küçüğe: 5 </li>
						<li>Yıl'a göre: küçükten büyüğe: 6 </li>
						<li>Yıl'a göre: büyükten küçüğe: 7 </li>
						<li>Motor Gücü'ne göre: küçükten büyüğe: 8 </li>
						<li>Motor Gücü'ne göre: büyükten küçüğe: 9 </li>						
					</ul>
					
					<b>Ör:</b> s=2
				</td>
				<td>Tek</td>
				<td>Varsayılan değer 1'dir.</td>
			</tr>
			<tr>
				<td>ps</td>
				<td>
					Sorgu sonucunda dönecek sonuç sayısı. Normal arayüzde de olduğu gibi arama sonuçları sayfa sayfa gönderilmektedir.
					Her bir sayfa için yeni istekte bulunmak gerekmektedir. Her bir sayfada belli miktarda sonuç bulunmaktadır. Bu değer
					bir sayfada bulunacak sonuç sayısını ifade eder.<br/>
					<b>Ör:</b> ps=5 
				</td>
				<td>Tek</td>
				<td>Bu değer 10'dan büyük olamaz. Varsayılan değer 10'dur.</td>
			</tr>
			<tr>
				<td>p</td>
				<td>
					Arama sonuçlarının sayfa numarası. Normal arayüzde de olduğu gibi arama sonuçları sayfa sayfa gönderilmektedir.
					Her bir sayfa için yeni istekte bulunmak gerekmektedir. Bu değer kaçıncı sayfaya ait sonuçların döneceğini belirtir.<br/>
					<b>Ör:</b> p=2 
				</td>
				<td>Tek</td>
				<td>Bu değer sorgu sonuçlarının sayfa sayısından büyük olamaz. Varsayılan değer 1'dir.</td>
			</tr>
			<tr>
				<td>src</td>
				<td>
					Alışveris sitesi isimleri. Bu filtre belirtilirse sadece belirtilen sitelerdeki sonuçlar döner.<br/>
					<b>Ör:</b> src=asitesi,bsitesi 
				</td>
				<td>Liste</td>
				<td></td>
			</tr>
			<tr>
				<td>brand</td>
				<td>
					Marka isimleri. Bu filtre belirtilirse sadece belirtilen markalardaki sonuçlar döner.<br/>
					<b>Ör:</b> brand=amarkası,bmarkası 
				</td>
				<td>Liste</td>
				<td></td>
			</tr>
			<tr>
				<td>price</td>
				<td>
					Fiyat aralığı. Sadece belirtilen fiyat aralığındaki sonuçları döner.<br/>
					<b>Ör:</b> price=10,100<br/>
					Virgülden önceki ilk değer küçük olan fiyat sonraki ise büyük olan fiyattır. eğer sadece küçük ya da büyük fiyat belirtilecekse
					yine virgül kullanılıp ilgili yere fiyat yazılması gerekmektedir. Örnek olarak aşağıdaki parametre fiyatı 100'den küçük olanları 
					getirir:
					price=,100<br/>
				</td>
				<td>Liste</td>
				<td></td>
			</tr>
			<tr>
				<td>key</td>
				<td>
					Size özel API anahtarıdır. Bu anahtarı lütfen bizimle iletişime geçip edininiz.<br/>
					<b>Ör:</b> key=ABCDEFGHIJKLMN<br/>
				</td>
				<td>Tek</td>
				<td>Parametre olarak bulunması zorunludur.</td>
			</tr>
			<tr>
				<td>evol</td>
				<td>
					Motor hacmi (cc). Verilen iki değer arasında motor hacmine sahip sonuçları döner.<br/>
					<b>Ör:</b> evol=500,5000<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>		
			<tr>
				<td>epow</td>
				<td>
					Motor Gücü (hp). Verilen iki değer arasında motor gücüne sahip sonuçları döner.<br/>
					<b>Ör:</b> epow=100,200<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>		
			<tr>
				<td>year</td>
				<td>
					Model yılı. Verilen iki değer arasında model yılına sahip sonuçları döner.<br/>
					<b>Ör:</b> year=1998,2004<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>		
			<tr>
				<td>km</td>
				<td>
					Kilometresi. Verilen iki değer arasında kilometreye sahip sonuçları döner.<br/>
					<b>Ör:</b> km=30000,100000<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>		
			<tr>
				<td>city</td>
				<td>
					Şehir. Verilen şehirlerde bulunan sonuçları döner.<br/>
					<b>Ör:</b> city=Ankara,İstanbul,İzmir<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>
			<tr>
				<td>fuel</td>
				<td>
					Yakıt tipi. Verilen yakıt tiplerindeki sonuçları döner.<br/>
					<b>Ör:</b> fuel=Benzin,LPG<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>
			<tr>
				<td>gear</td>
				<td>
					Vitesi tipi. Verilen vites tiplerindeki sonuçları döner.<br/>
					<b>Ör:</b> gear=Manuel,Triptronik<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>
			<tr>
				<td>color</td>
				<td>
					Renk. Verilen renkteki sonuçları döner.<br/>
					<b>Ör:</b> color=Kırmızı,Gri,Gri Metalik<br/>
				</td>
				<td>Liste</td>
				<td>Sadece araba arama için geçerlidir.</td>
			</tr>										
		</table>	
		
		<h3 id="sorguornek">Sorgu ve URL örnekleri</h3>
		<h4>Ürün sorgusu örneği</h4>	
	   	Aşağıdaki gibi bir sorgu için çağrılması gereken REST servisi ve parametreleri yine aşağıda belirtilmiştir.
	   	<ul>  
	   	<li>"cep telefonu" sorgusu sonucunda</li>
	   	<li>Hepsiburada ya da Teknosa'da bulunan</li>
	   	<li>100 ile 400 fiyat aralığında</li>
	   	<li>Nokia ya da Samsung markalı sonuçlardan</li>
	   	<li>her bir sayfada 5 tane olacak şekilde</li>
	   	<li>sorgu ile benzerliğe göre sıralayarak</li>
	   	<li>JSON formatında</li>
	   	</ul>
	   	getir:<br/>    	
		<ul>
	    	<li>http://www.karniyarik.com/rest/json/product?p=2&amp;q=cep+telefonu&amp;s=1&amp;ps=5&amp;src=hepsiburada,teknosa&amp;brand=Nokia,Samsung&amp;price=100,400&amp;key=ABCDEFGH</li>
	    </ul>    
	    <h4>Araba sorgusu örneği</h4>
	   	Aşağıdaki gibi bir sorgu için çağrılması gereken REST servisi ve parametreleri yine aşağıda belirtilmiştir.
	   	<ul>
	    	<li>"mercedes clk" sorgusu sonucunda</li> 
	    	<li>30,000 ile 50,000 fiyat aralığında</li>
	    	<li>fiyata göre: azalandan artana göre sıralayarak</li>
	    	<li>1998 ile 2004 model yılları arasında</li>
	    	<li>50,000 km ile 200,000 km arasında</li>
	    	<li>500cc ile 5000cc motor gücü arasında</li>
			<li>Ankara veya İstanbul şehirlerinde bulunan</li>
			<li>Benzinli ya da LPG'li</li>
			<li>Triptronik vitesli</li>
			<li>Gri yada Metalik Gri rengindeki sonuçları</li>
	    	<li>XML formatında</li>
	   	</ul>
	    	getir:
		<ul>	
	    	<li>http://www.karniyarik.com/rest/xml/car?q=mercedes+clk&amp;s=1&amp;ps=10&amp;price=30000,45000&amp;year=1998,2004&amp;km=50000,200000&amp;evol=500,5000&amp;epow=100,200&amp;city=Ankara,%C4%B0stanbul&amp;fuel=Benzin,LPG&amp;gear=Triptronik&amp;color=Gri,Gri+Metalik&amp;key=ABCDEFGH</li>
	    </ul>
    </div>
	<h3 id="sonucornek">Sonuç örnekleri</h3>
    <h4>JSON Sonuç Örneği</h4>
<pre class="samplecode">
{
   "totalhits":"96",
   "page":"2",
   "pagesize":"5",
   "query":"cep telefonu",
   "link":"http://www.karniyarik.com/urun/search.jsp?query=cep+telefonu&amp;price1=100&amp;price2=400&amp;price1=100&amp;price2=400&amp;
   sort=1&amp;source=hepsiburada,teknosa&amp;brand=Nokia,Samsung&amp;page=2&amp;psize=5",
   "products":{
      "product":[
         {
            "name":"Nokia 3120 Classic",
            "url":"http://www.siteadi.com",
            "price":"308.99",
            "currency":"TL",
            "brand":"Nokia",
            "site":"hepsiburada",
            "imageurl":"http://sitaadi.com/image.jpg"
         }
      ]
   },
   "filters":{
      "filter":[
         {
            "name":"price",
            "values":{
               "value":[
                  {
                     "name":"157'den küçük",
                     "count":"0"
                  },
                  {
                     "name":"162 - 259",
                     "count":"0"
                  },
                  {
                     "name":"269 - 316",
                     "count":"0"
                  },
                  {
                     "name":"319'dan büyük",
                     "count":"0"
                  }
               ]
            }
         },
         {
            "name":"source",
            "values":{
               "value":[
                  {
                     "name":"hepsiburada",
                     "count":"51"
                  },
                  {
                     "name":"teknosa",
                     "count":"45"
                  }
               ]
            }
         },
         {
            "name":"brand",
            "values":{
               "value":[
                  {
                     "name":"Nokia",
                     "count":"46"
                  },
                  {
                     "name":"Samsung",
                     "count":"50"
                  }
               ]
            }
         }
      ]
   }
}    	
    	</pre>    	
    <br/>
    <h4>XML Sonuç Örneği</h4>
    	<pre class="samplecode">
&lt;?xml version="1.0" encoding="UTF-8" standalone="yes"?&gt;
&lt;ns2:results xmlns:ns2="http://www.karniyarik.com/api"&gt;
  &lt;totalhits&gt;4&lt;/totalhits&gt;
  &lt;page&gt;1&lt;/page&gt;
  &lt;pagesize&gt;10&lt;/pagesize&gt;
  &lt;query&gt;mercedes clk&lt;/query&gt;
  &lt;link&gt;http://www.karniyarik.com/araba/search.jsp?query=mercedes+clk&amp;price1=30000&amp;price2=45000&amp;
  enginepower1=100&amp;enginepower2=200&amp;enginevolume1=500&amp;enginevolume2=5000&amp;km1=50000&amp;km2=200000&amp;
  year1=1998&amp;year2=2004&amp;sort=1&amp;page=1&amp;psize=10&amp;city=Ankara,İstanbul&amp;fuel=Benzin,LPG&amp;gear=Triptronik&amp;
  color=Gri,Gri Metalik&lt;/link&gt;
  &lt;products&gt;
    &lt;product&gt;
      &lt;name&gt;Mercedes CLK 200 Avantgarde Kompressor&lt;/name&gt;
      &lt;url&gt;http://www.siteadi.com/2211720/Mercedes+CLK+200+Avantgarde+Kompressor&lt;/url&gt;
      &lt;price&gt;37,000.00&lt;/price&gt;
      &lt;currency&gt;TL&lt;/currency&gt;
      &lt;brand&gt;Mercedes&lt;/brand&gt;
      &lt;site&gt;araba&lt;/site&gt;
      &lt;imageurl&gt;http://siteadi.com/2009/08/14/4564f54632e726f9ec44cb35571b51a0.jpg&lt;/imageurl&gt;
    &lt;/product&gt;
    &lt;product&gt;
      &lt;name&gt;Mercedes CLK 200 Kompressor Elegance&lt;/name&gt;
      &lt;url&gt;http://www.siteadi.com/2573577/Mercedes+CLK+200+Kompressor+Elegance&lt;/url&gt;
      &lt;price&gt;38,000.00&lt;/price&gt;
      &lt;currency&gt;TL&lt;/currency&gt;
      &lt;brand&gt;Mercedes&lt;/brand&gt;
      &lt;site&gt;araba&lt;/site&gt;
      &lt;imageurl&gt;http://siteadi.com/2010/01/22/2e7afa8885c15395d771596e5c9626de.jpg&lt;/imageurl&gt;
    &lt;/product&gt;
    &lt;product&gt;
      &lt;name&gt;MERCEDES CLK
200 Kompressor&lt;/name&gt;
      &lt;url&gt;http://www.hurriyetoto.com/oto/otomobil/detay.php?ilan_id=3572560&lt;/url&gt;
      &lt;price&gt;38,750.00&lt;/price&gt;
      &lt;currency&gt;TL&lt;/currency&gt;
      &lt;brand&gt;Mercedes&lt;/brand&gt;
      &lt;site&gt;hurriyetoto&lt;/site&gt;
      &lt;imageurl&gt;http://his.hurriyetoto.com/_thumbnail/_th_3/200947/869403001258540232408836_.jpg&lt;/imageurl&gt;
    &lt;/product&gt;
    &lt;product&gt;
      &lt;name&gt;Mercedes CLK 200 E2 Pack&lt;/name&gt;
      &lt;url&gt;http://www.siteadi.com/2571153/Mercedes+CLK+200+E2+Pack&lt;/url&gt;
      &lt;price&gt;37,250.00&lt;/price&gt;
      &lt;currency&gt;TL&lt;/currency&gt;
      &lt;brand&gt;Mercedes&lt;/brand&gt;
      &lt;site&gt;araba&lt;/site&gt;
      &lt;imageurl&gt;http://siteadi.com/2010/01/21/54a64d1b1eb3a46f80c56703edd49e31.jpg&lt;/imageurl&gt;
    &lt;/product&gt;
  &lt;/products&gt;
  &lt;filters&gt;
    &lt;filter&gt;
      &lt;name&gt;gear&lt;/name&gt;
      &lt;values&gt;
        &lt;value&gt;
          &lt;name&gt;Triptronik&lt;/name&gt;
          &lt;count&gt;4&lt;/count&gt;
        &lt;/value&gt;
      &lt;/values&gt;
    &lt;/filter&gt;
    &lt;filter&gt;
      &lt;name&gt;color&lt;/name&gt;
      &lt;values&gt;
        &lt;value&gt;
          &lt;name&gt;Gri&lt;/name&gt;
          &lt;count&gt;1&lt;/count&gt;
        &lt;/value&gt;
        &lt;value&gt;
          &lt;name&gt;Gri Metalik&lt;/name&gt;
          &lt;count&gt;3&lt;/count&gt;
        &lt;/value&gt;
      &lt;/values&gt;
    &lt;/filter&gt;
    &lt;filter&gt;
      &lt;name&gt;brand&lt;/name&gt;
      &lt;values&gt;
        &lt;value&gt;
          &lt;name&gt;Mercedes&lt;/name&gt;
          &lt;count&gt;4&lt;/count&gt;
        &lt;/value&gt;
      &lt;/values&gt;
    &lt;/filter&gt;
    &lt;filter&gt;
      &lt;name&gt;fuel&lt;/name&gt;
      &lt;values&gt;
        &lt;value&gt;
          &lt;name&gt;Benzin&lt;/name&gt;
          &lt;count&gt;3&lt;/count&gt;
        &lt;/value&gt;
        &lt;value&gt;
          &lt;name&gt;LPG&lt;/name&gt;
          &lt;count&gt;1&lt;/count&gt;
        &lt;/value&gt;
      &lt;/values&gt;
    &lt;/filter&gt;
    &lt;filter&gt;
      &lt;name&gt;city&lt;/name&gt;
      &lt;values&gt;
        &lt;value&gt;
          &lt;name&gt;Ankara&lt;/name&gt;
          &lt;count&gt;3&lt;/count&gt;
        &lt;/value&gt;
        &lt;value&gt;
          &lt;name&gt;&#304;stanbul&lt;/name&gt;
          &lt;count&gt;1&lt;/count&gt;
        &lt;/value&gt;
      &lt;/values&gt;
    &lt;/filter&gt;
  &lt;/filters&gt;
&lt;/ns2:results&gt;   	
    	</pre>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="commonfooter.jsp"></jsp:include>
</body>
</html>