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
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.common.site.SiteRegistry"%>
<%@page import="com.karniyarik.common.site.SiteInfo"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%@page import="com.karniyarik.web.util.Formatter"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.karniyarik.web.site.Sites"%>
<%@page import="com.karniyarik.web.site.SiteInfoBean"%>
<%HttpCacheUtil.setResponseCacheAttributes(response, request, 2);%>
<%WebApplicationDataHolder aDataHolder = WebApplicationDataHolder.getInstance();
  Sites sites = new Sites(request);
  BaseCategoryUtil.setStaticPageBreadCrumb("Bilgi Toplanan Siteler", "sites.jsp", request);
%>

<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>Bilgi Toplanan Siteler | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Karniyarik.com'da hangi alışveriş siteleri bulunuyor, bu alışveriş sitelerindeki ürün miktarları, yorumlar ve benzeri bilgiler." />
<meta name="description" content="Karniyarik.com, bilgi toplanan siteler." />
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
  <div class="sresult">Bilgi Toplanan Siteler</div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
	<div class="lc full">
      <div class="prop">
        <div class="c1 text">
		    <ul class="contentindex">
				<li><a href="#sponsor">Sponsor Siteler</a></li>    
				<li><a href="#ozel">Özel Mağazalar</a></li>
			    <li><a href="#urun">Çevrimiçi Alışveriş Siteleri</a></li>
			    <li><a href="#araba">2.el Araba Siteleri</a></li>
			    <li><a href="#sehirfirsati">Şehir Fırsatı Siteleri</a></li>
			</ul>
        </div>
      </div>
    </div>    
	<div class="content">
	<br/>
    <p>Şu anda toplam <b><%=Formatter.formatInteger(aDataHolder.getSiteCount())%></b> alışveriş sitesiden 
    <b><%=Formatter.formatInteger(aDataHolder.getProductCount())%></b> ürün kayıtlıdır. 
    Toplam marka sayısı <b>7649</b>'dur.</p>
    <p>Sitelerin ürün sayısı, tıklanma miktarları, adları içinde geçen kelime yoğunlukları vb. bilgileri için <a href="<%=request.getContextPath()%>/sitestat">Site İstatistikleri</a> sayfasını ziyaret edebilirsiniz.</p>
	<h3 id="sponsor">Sponsor Siteler</h3>
	<div class="prepend-top" >
	<%	for(SiteInfoBean bean: sites.getSponsoredProductSites()){ %> 
		<div class="siteinfo sps" typeof="v:Organization">
			<a href="<%=bean.getSiteDetailURL()%>"><img src="<%=bean.getLogoURL()%>" alt="<%=bean.getEnterpriseInfo().getDisplayName()%> logosu"/></a><br/>
			<label property="v:name"><%=bean.getSiteConfig().getDomainName()%></label><br/>
			<label><%=Formatter.formatInteger(bean.getProductCount())%> ürün</label><br/>			
			<label>
				<%if (bean.getCalculatedRank() >= 0) {%>
				<a rel="nofollow" target="_blank" title="NeredenAldın.com'da <%=bean.getRank()%> sırada." href='<%=bean.getRankLink()%>'>
					<img property="v:rating" content="<%=bean.getRatingOver5()%>" class="sprite stars-b-<%=bean.getCalculatedRank()%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="81" height="15" alt="Site değerlendirmesi"/>
				</a>
				<%} else { %>				
				<img property="v:rating" content="<%=bean.getRatingOver5()%>" class="sprite stars-b-<%=bean.getCalculatedRank()%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="81" height="15" alt="Site değerlendirmesi"/>
				<%}%>
			</label><br/>
			<span>
			<a rel="v:url" href="<%=bean.getSiteDetailURL()%>">Detayli Bilgi</a>
			<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Site hakkında bilgi"/>			
			</span>
		</div>
	   <%}%>
	 </div>
	<div class="cl"></div>
	<h3 id="ozel">Özel Mağazalar</h3>
	<div class="prepend-top" >
	<%	for(SiteInfoBean bean: sites.getFeaturedSites()){ %> 
		<div class="siteinfo sps" typeof="v:Organization">
			<a href="<%=bean.getSiteDetailURL()%>"><img src="<%=bean.getLogoURL()%>" alt="<%=bean.getEnterpriseInfo().getDisplayName()%> logosu"/></a><br/>
			<label property="v:name"><%=bean.getSiteConfig().getDomainName()%></label><br/>
			<label><%=bean.getProductCount()%> ürün</label><br/>			
			<label>
				<%if (bean.getCalculatedRank() >= 0) {%>
				<a rel="nofollow" target="_blank" title="NeredenAldın.com'da <%=bean.getRank()%> sırada." href='<%=bean.getRankLink()%>'>
					<img property="v:rating" content="<%=bean.getRatingOver5()%>" class="sprite stars-b-<%=bean.getCalculatedRank()%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="81" height="15" alt="Site değerlendirmesi"/>
				</a>
				<%} else { %>
				<img property="v:rating" content="<%=bean.getRatingOver5()%>" class="sprite stars-b-<%=bean.getCalculatedRank()%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="81" height="15" alt="Site değerlendirmesi"/>
				<%}%>
			</label><br/>
			<span>
			<a rel="v:url" href="<%=bean.getSiteDetailURL()%>">Detayli Bilgi</a>
			<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Site hakkında bilgi"/>			
			</span>
		</div>
	   <%}%>
	 </div>
	<div class="cl"></div>
	<h3 id="sehirfirsati">Şehir Fırsatı Siteleri</h3>
   	<div class="prepend-top">
	<%	for(SiteInfoBean bean: sites.getCityDealSites()){ %>
		<div class="siteinfo border">
			<img width="150" src="<%=request.getContextPath() + bean.getLogoURL()%>140.png" alt="<%=bean.getSiteConfig().getSiteName()%> logosu"/><br/>
			<label property="v:name"><%=bean.getSiteConfig().getSiteName()%></label><br/>
			<label><%=Formatter.formatInteger(bean.getProductCount())%> fırsat</label><br/>
		</div>
	   <%}%>
	</div>		
	<div class="cl"></div>
	<h3  id="urun">Çevrimiçi Alışveriş Siteleri</h3>
	<div class="prepend-top">
	<%	for(SiteInfoBean bean: sites.getProductSites()){ %>
		<div class="siteinfo border" typeof="v:Organization">
			<img src="<%=bean.getLogoURL()%>" alt="<%=bean.getEnterpriseInfo().getDisplayName()%> logosu"/><br/>
			<label property="v:name"><%=bean.getSiteConfig().getDomainName()%></label><br/>
			<label><%=Formatter.formatInteger(bean.getProductCount())%> ürün</label><br/>			
			<label>
				<%if (bean.getCalculatedRank() >= 0) {%>
				<a rel="nofollow" target="_blank" title="NeredenAldın.com'da <%=bean.getRank()%> sırada." href='<%=bean.getRankLink()%>'>
					<img property="v:rating" content="<%=bean.getRatingOver5()%>" class="sprite stars-b-<%=bean.getCalculatedRank()%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="81" height="15" alt="Site değerlendirmesi"/>
				</a>
				<%} else { %>
				<img property="v:rating" content="<%=bean.getRatingOver5()%>" class="sprite stars-b-<%=bean.getCalculatedRank()%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="81" height="15" alt="Site değerlendirmesi"/>
				<%}%>
			</label><br/>
			<span>
				<a rel="nofollow v:url" target="_blank" href="<%=bean.getSiteConfig().getUrl()%>">Siteye Git</a>
				<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Siteye Git"/>			
			</span>
		</div>
	   <%}%>
	   </div>
	   <div class="cl"></div>
	   <h3 id="araba">2.el Araba Siteleri</h3>
	   	<div class="prepend-top">
		<%	for(SiteInfoBean bean: sites.getCarSites()){ %>
			<div class="siteinfo border">
				<img src="<%=bean.getLogoURL()%>" alt="<%=bean.getEnterpriseInfo().getDisplayName()%> logosu"/><br/>
				<label property="v:name"><%=bean.getSiteConfig().getDomainName()%></label><br/>
				<label><%=Formatter.formatInteger(bean.getProductCount())%> ürün</label><br/>
				<span>
					<a rel="nofollow v:url" target="_blank" href="<%=bean.getSiteConfig().getUrl()%>">Siteye Git</a>
					<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Siteye Git"/>								
				</span>
			</div>
		   <%}%>
		</div>
		<div class="cl"></div>	    
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="commonfooter.jsp"></jsp:include>
</body>
</html>