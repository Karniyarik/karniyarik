<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("Siteni Ekle", "addsite.jsp", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" version="XHTML+RDFa 1.0" xml:lang="tr">
	<head>
		<title>Siteni Ekle | Karnıyarık - Alışverişi yardık! </title>
		<meta name="description" http-equiv="description" content="Karniyarik.com'a sitenizi eklemek için gerekli bilgileri bize gönderin, 
			sitenizdeki bilgiler Karniyarik.com'dan aranabilir olsun." />
		<meta name="keywords" http-equiv="keywords" content="Karniyarik.com, Site Ekle, Karniyarik.com site ekleme" />
		<jsp:include page="commonmeta.jsp" />
		<jsp:include page="commoninclude.jsp" />
		<script type='text/javascript' src='http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.min.js'></script>
		<script type='text/javascript' >
		$(document).ready(function() {
			$("#contactForm").validate({
				rules: {
					name: {
						required: true,
						minlength: 2
					},
					sitename: {
						required: true,
						minlength: 2
					},
					url: {
						required: true,
						minlength: 2
					},
					email: {
						required: true,
						email: true
					},
					phone: {
						required: true,
						minlength: 9
					},		
					message: {
						required: true,
						maxlength: 1024
					}			
				},
				messages: {
					name: "Lütfen site adını giriniz",
					url: "Lütfen site adresini giriniz",
					phone: "Lütfen telefon numaranızı giriniz",
					email: "Lütfen email adresini giriniz",
					sitename: "Lütfen site adını giriniz"
				}
			});
		
			$(".input").tooltip({
				// place tooltip on the right edge
				position: "center right",
				// a little tweaking of the position
				offset: [-2, -50],
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
		  <div class="sresult"><h1>Sitenizi Ekleyin</h1></div>
		  <div class="cl"></div>
		  <div class="l">
		    <div class="filter full"></div>
		    <div class="cl"></div>
		    <div class="ll"></div>
			<div class="content">
				<br/>
				<%String status = request.getParameter("status");
					if(status != null && status.equalsIgnoreCase("ok"))
					{ %><div class="notice">
						Siteniz en kısa zaman içerisinde Karnıyarık'a eklenecektir. Bu esnada Karnıyarık'ın sitenize sağlayabilecekleri hakkında 
						aşağıdaki dökümanı gözden geçirebilirsiniz:
						<br/>
						<br/>
						<a href="http://www.karniyarik.com/kurumsal/docs/flyer.pdf">
							<img class="sprite pdf-btn" src="<%=request.getContextPath()%>/images/transparent.gif" width="32" height="32" alt="PDF Dosyası"/> 
							Karnıyarık Broşür
						</a>
						<br/>
						<br/>
						<hr/>
						<a title="Alışveriş" rel="home" href="http://www.karniyarik.com">Ana sayfaya dön</a>
					</div><%} else {%>
				<div class="notice">
				<p>Karnıyarık'ta bilgileriniz şu an listelenmiyorsa aşağıdaki formu kullanarak sisteminizin kaydını yapabilirsiniz. 
				Bilgileriniz listelenmeye başlayınca formda belirttiğiniz email adresinize Karnıyarık içerisinde mağazanızı 
				yönetebileceğiniz kurumsal giriş için kullanıcı adınız ve şifreniz yollanacaktır. 
				Eğer email adresinin alan adı siteniz ile aynı değilse mail göndermeden önce sizlerle iletişime geçmemiz ve verilen 
				emailin gerçekten sitenin sahibi olduğunu onaylamamız gerekecektir. 
				Sitenizi Karnıyarık'a eklenmesi için lütfen aşağıdaki bilgileri doldurunuz.</p>
				</div>	
				<h3>Koşullar</h3>
				<h4>Ücretlendirme</h4>
				<ul>
					<li>Karniyarik.com'da siteniz ücretsiz olarak listelenir.</li> 
					<li>Karniyarik.com'da ücretli olarak sunulan özellikler <a href="http://www.karniyarik.com/kurumsal/features.gsp">Kurumsal Özellikler</a> sayfasında 
					kısaca anlatılan Özel Mağaza ve Sponsor Arama özellikleridir. Bu özellikler ile ilgili <a href="http://www.karniyarik.com/kurumsal/docs/flyer.pdf">Karnıyarık Broşür
						<img class="sprite pdf-btn" src="<%=request.getContextPath()%>/images/transparent.gif" width="32" height="32" alt="PDF Dosyası"/>
					</a> 
					dökümanını inceleyebilirsiniz.</li>					
				</ul>
				<h4>Site ekleme önceliklendirmesi</h4>
				<p>Sitenizi Karnıyarık'a eklerken aşağıdaki kriterlere göre bir önceliklendirme yapılmaktadır</p>
				<img src="<%=request.getContextPath()%>/images/add_site_priority.png" alt="Site Ekleme Önceliklendirmesi" style="margin-bottom:20px;"/>
				<ul>
					<li><span class="purple">Sponsor aramaya dahil olacak sitelere öncelik tanınmaktadır</span>
						<p>Sitenizi ekleme esnasında hemen sponsor aramaya dahil etmek isterseniz sisteme eklenmek için öncelik tanınmaktadır.</p>
					</li>
					<li>
						<span class="purple">Veri Beslemesi Sunan sitelere öncelik tanınmaktadır</span> 
						<p>Sitenizi ekleme isteğinde bulunurken geçerli bir <a href="<%=request.getContextPath()%>/datafeed/">Veri Beslemesi</a> sunarsanız siteniz bir kaç saat içinde 
						sisteme eklenecektir. Lütfen veri beslemesinin ne olduğuna dair <a href="<%=request.getContextPath()%>/datafeed/">Veri Beslemeleri</a> adresini dikkatlice okuyunuz.
						Konu hakkında her türlü sorunuz için <a href="<%=request.getContextPath()%>/contact.jsp">iletişim sayfasında</a> bulunan eposta ya da iletişim formu
						aracılığı ile bizlerle iletişime geçebilirsiniz.</p>
					</li>
					<li>Bunun dışındakiler <span class="purple">site ekleme isteği tarihine</span> göre sıralanıp eklenecektir. 
					<p>Eğer site bilgilerinizin otomatik toplanmasını istiyorsanız ve de sponsor aramaya dahil olmak istemiyorsanız, siteniz istek tarihine göre oluşturulmuş
					sıraya göre sisteme eklenecektir. İsteklerin yoğunluğundan ötürü bu sürecin zamanı konusunda Karniyarik.com olarak herhangi bir taahhütte bulunamamaktayız.</p> 
					</li>
				</ul>
				
				<h3>Lütfen okuyunuz</h3>
				<ul class="purple">
					<li>Telefon, email ve adınız gibi bilgiler sizlerle iletişim kurabilmemiz için önemlidir.</li> 
					<li>Veri beslemesi sitenizin Site Haritası ya da RSS beslemesi <b>değildir</b>.</li>
					<li>Veri beslemesi hakkında lütfen <a href="<%=request.getContextPath()%>/datafeed/">Veri Beslemeri Sayfasını</a> okuyunuz</li>
				</ul>
			
				<p>Sitenizi Karnıyarık'a eklenmesi için lütfen aşağıdaki bilgileri doldurunuz.</p>
				<form action="<%=request.getContextPath()%>/system/addsite" method="post" id="contactForm">
					<fieldset>
						<legend>Site Bilgileri</legend>
						<p>
				       	<label for="sitename">Site Adı*</label>
				       	<input class="input" type="text" name="sitename" id="sitename" value="<%=request.getParameter("sitename") == null ? "" : request.getParameter("sitename")%>" title="Sitenizin görünür adı. Ör: X alışveriş"/>
				       	<label for="url">Site Adresi*</label>
				       	<input class="input" type="text" name="url" id="url" value="<%=request.getParameter("url") == null ? "" : request.getParameter("url")%>" title="Sitenizin adresi. Ör: http://www.x.com"/>
				       	<label for="datafeed">Veri Sağlayıcı (XML) Adresi</label>
				       	<input class="input" type="text" name="datafeed" id="datafeed" value="<%=request.getParameter("datafeed") == null ? "" : request.getParameter("datafeed")%>" title="Sitenizin XML veri adresi. Ör: http://www.x.com/karniyarik.xml"/>
						<label for="name">Ad/Soyadınız*</label>
				        <input class="input" type="text" name="name" id="name" value="<%=request.getParameter("name") == null ? "" : request.getParameter("name")%>" title="Adınız, Soyadınız"/>
				        <label for="email">E-posta adresiniz*</label>
				   	    <input class="input" type="text" name="email" id="email" value="<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>" title="Email adresiniz"/>
				        <label for="phone">Telefon numaranız*</label>
				        <input class="input" type="text" name="phone" id="phone" value="<%=request.getParameter("phone") == null ? "" : request.getParameter("phone")%>" title="Telefon numaranız"/>
				        <label for="message">Site Açıklaması</label>
				   	    <textarea class="input" name="message" id="message" rows="3" cols="20" title="Eklemek istediğiniz notlar, sitenizin açıklaması vs."><%= request.getParameter("message") == null ? "" : request.getParameter("message")%></textarea>
				   	    <label for="soru">Aşağıdaki kutuya görüntülenen harfleri aynen giriniz
				   	    <% if(status != null && status.equalsIgnoreCase("captchaerr")){ %>
							<span class="error"> *Harfleri yanlış girdiniz. Tekrar deneyiniz...</span>
						<%}%>
				   	    </label>
				   	    <jsp:include page="captcha.jsp"></jsp:include>
				   	    </p>
				   	    <input type="hidden" name="return" id="return" value="addsite.jsp" />
				       	<button type="submit">Gönder</button>
					</fieldset>
				</form><%}%>
			</div>	    
		  </div>
		</div>
		<div class="cl" style="height:50px"></div>
		<div ></div>
		<jsp:include page="commonfooter.jsp"></jsp:include>
	</body>
</html>