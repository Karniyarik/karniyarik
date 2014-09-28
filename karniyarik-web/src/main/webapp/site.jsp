<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="com.karniyarik.common.KarniyarikRepository"%>
<%@page import="com.karniyarik.common.config.site.SitesConfig"%>
<%@page import="com.karniyarik.common.config.site.SiteConfig"%>
<%@page import="java.util.Collection"%>
<%@page import="com.karniyarik.web.util.WebApplicationDataHolder"%>
<%@page import="com.karniyarik.site.SiteInfoProvider"%>
<%@page import="com.karniyarik.site.SiteInfoConfig"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%@page import="com.karniyarik.common.site.SiteRegistry"%>
<%@page import="com.karniyarik.common.site.SiteInfo"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.common.site.FeaturedMerchantVO"%>
<%@page import="com.karniyarik.common.site.Payment"%>
<%@page import="com.karniyarik.common.site.Shipment"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.karniyarik.common.site.Certificate"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="com.karniyarik.web.util.Formatter"%>
<%HttpCacheUtil.setResponseCacheAttributes(response, request, 2);%>
<%WebApplicationDataHolder aDataHolder = WebApplicationDataHolder.getInstance(); 
  String sitename = request.getParameter("s");
  SiteInfoProvider infoProvider = SiteInfoProvider.getInstance();
  SiteInfoConfig siteInfoConfig = infoProvider.getSiteInfo(sitename);
  SiteInfo siteInfo = SiteRegistry.getInstance().getSiteInfo(sitename);
  SiteConfig siteConfig = KarniyarikRepository.getInstance().getConfig().getConfigurationBundle().getSitesConfig().getSiteConfig(sitename);
  List<LinkedLabel> crumb=BaseCategoryUtil.getStaticPageBreadCrumb("Bilgi Toplanan Siteler", "site.jsp", request);

  if(siteConfig == null)
  {
	response.setStatus(404);
	response.sendRedirect(request.getContextPath() + "/404.jsp");
	return;
  }
  
  BaseCategoryUtil.appendPageToBreadCrumb(request, crumb, siteConfig.getDomainName(), "site/" + siteConfig.getSiteName());
  FeaturedMerchantVO featured = null;
  
  if(siteInfo != null && siteInfo.isFeatured())
  {
  	featured = SiteRegistry.getInstance().getFeaturedMerchant(sitename);
  }
%>

<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title><%=siteConfig.getDomainName()%> | Karnıyarık</title>
<meta name="description" content="<%=siteConfig.getDomainName()%> sitesi hakkıda bilgiler, iletişim adresleri, çalıştığı kargo şirketleri, ödeme şekilleri, güvenlik sertifikaları ve daha fazlası." />
<meta name="keywords" content="<%=siteConfig.getDomainName()%>, <%=siteConfig.getDomainName()%> iletişim, <%=siteConfig.getDomainName()%> kargo, <%=siteConfig.getDomainName()%> ödeme şekilleri, <%=siteConfig.getDomainName()%> güvenlik sertifikaları" />
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
  <div class="sresult"><h1><%=siteConfig.getDomainName()%></h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
	<div class="content site" typeof="v:Organization">
		<br/>
	    <div style="width:150px;float:left;margin-right:10px;">
	    	<a rel="nofollow v:url" target="_blank" href="<%=siteInfo.getUrl()%>" title="<%=siteConfig.getDomainName()%>">
			<%String logo = siteInfo.getLogourl(); 
			if(StringUtils.isBlank(logo) || logo.equals("null")){
				logo = request.getContextPath() + "images/sites/100x30/" + siteConfig.getImage();
			}
			%>
			<img class="logo" src="<%=logo%>" alt="<%=siteConfig.getDomainName()%>" title="<%=siteConfig.getDomainName()%> logosu"  style="margin-bottom:20px;"/>
			</a>
			<label property="v:name" ><%=siteConfig.getDomainName()%></label>
			<a rel="nofollow" target="_blank" href="<%=siteInfo.getUrl()%>" title="<%=siteConfig.getDomainName()%>">
				<img class="sprite go-site-btn" src="<%=request.getContextPath()%>/images/transparent.gif" width="119" height="23" alt="<%=siteConfig.getDomainName()%> sitesine git"/>
			</a>
	    </div>
    
	    <div style="width:500px;float:left;margin-right:10px;" class="border">
		    <%if(StringUtils.isNotBlank(siteInfo.getDescription()) && !siteInfo.getDescription().equals("null")){ %>
		    <p><%=siteInfo.getDescription()%>
		    <%}%>
		    </p>
		    <br/>
		    <p>
			<%if(siteInfo.isFeatured() && StringUtils.isNotBlank(featured.getContact().getNotes())){ %>
			<%=featured.getContact().getNotes()%>
			<%} %>
			</p>
	    </div>

	    <div style="width:300px;float:right;margin-left:10px;">
	    	<div>
	    	<%if(siteInfo.isFeatured()){%>
	    		<img width="60" height="100" class="sprite ribbon-featured" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Özel Mağaza"/>
	    	<%}%>
	    	<%if(siteInfo.isSponsored()){%>
	    		<img width="60" height="100" class="sprite ribbon-sponsored" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Sponsor Mağaza"/>
	    	<%}%>
	    	</div>
	    	<div style="margin-bottom:10px;">
	    	 <%Integer productCount = aDataHolder.getSiteProductCount(siteConfig.getSiteName());%>
	    		<span class="lg bold">Bu siteden toplam <%=Formatter.formatInteger(productCount)%> ürün sisteme kayıtlıdır.</span>
	    	</div>
		    <div style="margin-bottom:10px;">
				<%if(siteInfoConfig != null){
					int calculatedRank = -1;
					int rank = -1;
					String rankLink = "";
					String ratingOver5 = Formatter.formatRating(siteInfoConfig.getSiteRankOver10());
					calculatedRank = siteInfoConfig.getCalculatedRank();
					rank = siteInfoConfig.getRank();
					rankLink = siteInfoConfig.getSiteReviewURL();%>
					<span class="lg bold">
					<a rel="nofollow" target="_blank" title="NeredenAldın.com'da <%=rank%> sırada." href='<%=rankLink%>'>
						<img property="v:rating" content="<%=ratingOver5%>" class="sprite stars-b-<%=calculatedRank%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="81" height="15" alt="Site değerlendirmesi"/>
					</a>
					&nbsp;&nbsp;<a rel="nofollow" target="_blank" href="<%=rankLink%>">NeredenAldın.com</a>'da <%=siteInfoConfig.getRank()%>. sırada</span>
					<%if (calculatedRank >= 0) {%>
					<span class="lg bold"></span>
					<%}%>
				<%}else{%>
					<span class="lg bold"><a rel="nofollow" href="http://www.neredenaldin.com">NeredenAldın.com</a>'da kayıtlı Değil</span> 
				<%}%>
			</div>
	    </div>
	
    <div class="cl" style="height:20px"></div>
    
    <%if(siteInfo.isFeatured()){%>
    <div style="width:1000px;" class="sec rndb">
	    <div style="width:250px;" class="border subsec">
			<h2>İletişim Bilgileri</h2>
			<ul>
				<%if(StringUtils.isNotBlank(featured.getContact().getPhone())){ %>
				<li><img src="<%=request.getContextPath()%>/featured/images/phone.gif" alt="Telefon" title="Telefon"/><span property="v:tel"><%=featured.getContact().getPhone()%></span></li>
				<%}%>
				<%if(StringUtils.isNotBlank(featured.getContact().getFax())){ %>
				<li><img src="<%=request.getContextPath()%>/featured/images/fax.gif" alt="Fax" title="Fax"/><%=featured.getContact().getFax()%></li>
				<%}%>
				<%if(StringUtils.isNotBlank(featured.getContact().getEmail())){ %>
				<li><img src="<%=request.getContextPath()%>/featured/images/email.gif" alt="E-Posta" title="E-Posta"/><a rel="nofollow" href="mailto:<%=featured.getContact().getEmail()%>"><%=featured.getContact().getEmail()%></a></li>
				<%}%>
				<%if(StringUtils.isNotBlank(siteInfo.getUrl())){ %>
				<li><a rel="nofollow" target="_blank" href="<%=featured.getContact().getUrl()%>">Web Sayfası</a></li>
				<%}%>
				<%if(StringUtils.isNotBlank(featured.getContact().getContactus())){ %>
				<li><a rel="nofollow" target="_blank" href="<%=featured.getContact().getContactus()%>">İletişim Sayfası</a></li>
				<%}%>					
			</ul>
		</div>
		
		<div style="width:270px;" class="border subsec">
			<h2>Hizmet Bilgileri</h2>
			<div>
				<%for(String name: featured.getLinks().keySet()){ %>
				<span><a rel="nofollow" target="_blank" href="<%=featured.getLinks().get(name)%>"><%=name%></a></span>,
				<%} %>
			</div>
		</div>
		
		<div style="float:left;" class="subsec">	
			<h2>Hakkında</h2>
			<ul>
				<%if(StringUtils.isNotBlank(featured.getContact().getCompany())){ %>
				<li><%=featured.getContact().getCompany()%></li>
				<%} %>
				<%if(StringUtils.isNotBlank(featured.getContact().getAddress())){ %>
				<li><span rel="v:address"><%=featured.getContact().getAddress()%></span></li>
				<%} %>
				<%if(StringUtils.isNotBlank(featured.getAboutus())){ %>
				<li><a rel="nofollow" target="_blank" href="<%=featured.getAboutus()%>">Hakkımızda Sayfası</a></li>
				<%} %>
			</ul>		
		</div>
	</div>
	
	<div class="cl" style="height:20px"></div>

	<div style="width:1000px;" class="sec rndb">
		<div style="width:250px;" class="border subsec">
			<h2>Takip Edin</h2>
			<ul>
				<%if(StringUtils.isNotBlank(featured.getFacebook())){ %>
				<li><img src="<%=request.getContextPath()%>/featured/images/follow/fb.gif"/><a rel="nofollow" target="_blank" href="<%=featured.getFacebook()%>">Facebook sayfası</a></li>
				<%} %>
				<%if(StringUtils.isNotBlank(featured.getTwitter())){ %>
				<li><img src="<%=request.getContextPath()%>/featured/images/follow/tw.gif"/><a rel="nofollow" target="_blank" href="<%=featured.getTwitter()%>">Twitter</a></li>
				<%} %>
			</ul>
		</div>

		<div style="width:270px;" class="border subsec">
			<h2>Ödeme Şekilleri ve Kargo</h2>
			<ul class="ftrd_sec_list ship_pay">
				<%for(Payment payment: featured.getPayment()){ %>
				<li><img src="<%=payment.getImage()%>" title="<%=payment.getName()%>"/></li>
				<%} %>
			</ul>
			<ul class="ftrd_sec_list ship_pay">
				<%for(Shipment shipment: featured.getShipment()){ %>
				<li><img src="<%=shipment.getImage()%>" title="<%=shipment.getName()%>"/></li>
				<%} %>
			</ul>
		</div>

		<div style="float:left;" class="subsec">
			<h2>Sertifikalar</h2>
			<ul class="ftrd_sec_list ship_pay">
				<%for(Certificate certificate: featured.getCertificates()){ %>
				<li><img src="<%=certificate.getImage()%>" title="<%=certificate.getName()%>"/></li>
				<%} %>
			</ul>
		</div>
	</div>	
  	<%} %>
	</div>
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="commonfooter.jsp"></jsp:include>
</body>
</html>