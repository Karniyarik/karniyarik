<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="../error.jsp"%>
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
<%@page import="com.karniyarik.web.site.FunnyStatisticsProvider"%>
<%@page import="com.karniyarik.web.site.FunnyStatistics"%>
<%HttpCacheUtil.setResponseCacheAttributes(response, request, 2);%>
<%
  WebApplicationDataHolder aDataHolder = WebApplicationDataHolder.getInstance();
  FunnyStatistics stat = FunnyStatisticsProvider.getInstance().getStat();
  BaseCategoryUtil.setStaticPageBreadCrumb("Site İstatistikleri", "sitestat", request);
%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>Site İstatistikleri | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Karniyarik.com'da bulunan sitelerin isimlerinde en çok hangi kelimeler var, hangi sitelerde en çok ürün var, hangi sitelere en çok tıklanıyor..." />
<jsp:include page="../commonmeta.jsp" />
<jsp:include page="../commoninclude.jsp" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.tagcloud.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#keywordcloud span').tagcloud({
	    size: {start: 12, end: 40, unit: "px"}, 
	    color: {start: '#548600', end: '#548600'}
	  });
	$('#extcloud span').tagcloud({
	    size: {start: 20, end: 40, unit: "px"}, 
	    color: {start: '#8F3275', end: '#8F3275'}
	  });
	
});
</script>
<style type="text/css">
.cloud span {margin-right:10px;margin-bottom:10px;}
</style>
</head>
<body>
<div class="ce ar tmh2 ">
	<jsp:include page="../nav.jsp"></jsp:include>
</div>
<div class="ce">
  <form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/urun/search.jsp" onsubmit="if($('#stab').val()=='1')this.action='<%=request.getContextPath()%>/urun/search.jsp'; submitForm()">
  <jsp:include page="../commontop.jsp"></jsp:include>
  </form>
  <div class="sresult">Site İstatistikleri</div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full">
    	<div class="fr c4 sopi">
    	<span class="addthis_button" 
	    	addthis:url="http://www.karniyarik.com/sitestat" addthis:title="Karnıyarık Site İstatistikleri" addthis:description="Karniyarik.com'da bulunan sitelerin isimlerinde en çok hangi kelimeler var, hangi sitelerde en çok ürün var, hangi sitelere en çok tıklanıyor...">
	    	<img width="88" height="17" style="cursor: pointer;" alt="Paylaş" src="<%=request.getContextPath()%>/images/share.png" title="Arkadaşlarınla paylaş"/>
	    </span>
	    </div>
    </div>
    <div class="cl"></div>
    <div class="ll"></div>
	<div class="lc full">
      <div class="prop">
        <div class="c1 text">
		    <ul class="contentindex">
		    	<li><a href="#pname">Site Adı İstatistikleri</a></li>    
				<li><a href="#pclick">Site Tıklama İstatistikleri</a></li>
			    <li><a href="#pcount">Ürün Sayısı İstatistikleri</a></li>
			</ul>
        </div>
      </div>
    </div>    
	<div class="content">
		<div class="cl"></div>
		<br/>
		<h3 id="pname" style="margin-top: 20px;">Site Adı İstatistikleri</h3>
		<div class="prepend-top">
			<div style="width:450px;margin-right:20px;float:left;">
				<h4>Site adı içerisinde geçen kelimeler:</h4>
				<div style="width:100%;" id="keywordcloud" class="cloud">
					
					<%for(String keyword: stat.getDomainKeywords().keySet()){%>
					<span rel="<%=stat.getDomainKeywords().get(keyword)%>" 
						title="Alan adı içerisinde '<%=keyword%>' geçen toplam <%=stat.getDomainKeywords().get(keyword)%> mağaza var">
						<%=keyword%>
					</span>
					<%} %>
				</div>
			</div>
			<div style="width:450px;float:right;">
				<h4>Alan adı uzantısı:</h4>
				<div style="width:100%;" id="extcloud" class="cloud">
					<%for(String keyword: stat.getExtensionKeywords().keySet()){%>
					<span rel="<%=stat.getExtensionKeywords().get(keyword)%>" 
						title="Alan adı uzantısı '<%=keyword%>' olan toplam <%=stat.getExtensionKeywords().get(keyword)%> mağaza var">
						<%=keyword%>
					</span>
					<%} %>
				</div>
			</div>
		</div>
		<div class="cl"></div>
		<h3 id="pclick" style="margin-top: 40px;">Site Tıklama İstatistikleri</h3>
		<div class="prepend-top" >
			<div style="width:320px;margin-right:10px;float:left;" class="border">
				<h4>En Çok Tıklanan Alışveriş Mağazaları</h4>
				<div style="width:100%;" class="cloud">
					<table>
						<tbody>
							<%
							int index = 0;
							for(SiteInfoBean site: stat.getTopClickedProductCountSites()){
								index++;
								if(index > 5)
								{
									break;
								}
							%>
							<tr>
								<td>
								<%if(site.getLogoURL().contains("nologo")){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} else if(StringUtils.isNotBlank(site.getLogoURL())){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} %>
								</td>
								<td>
									<a rel="no-follow" target="_blank" href="<%=site.getSiteConfig().getUrl()%>"><%=site.getSiteConfig().getDomainName()%></a>
								</td>		
							</tr>
							<%} %>
						</tbody>
					</table>
				</div>
			</div>
			<div style="width:320px;margin-right:10px;float:left;"  class="border">
				<h4>En Çok Tıklanan AraÇ Siteleri</h4>
				<div style="width:100%;" class="cloud">
					<table>
						<tbody>
							<%
							index = 0;
							for(SiteInfoBean site: stat.getTopClickedCarCountSites()){
								index++;
								if(index > 5)
								{
									break;
								}
							%>
							<tr>
								<td>
								<%if(site.getLogoURL().contains("nologo")){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} else if(StringUtils.isNotBlank(site.getLogoURL())){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} %>
								</td>
								<td>
									<a rel="no-follow" target="_blank" href="<%=site.getSiteConfig().getUrl()%>"><%=site.getSiteConfig().getDomainName()%></a>
								</td>		
							</tr>
							<%} %>
						</tbody>
					</table>
				</div>
			</div>	
			<div style="width:320px;float:left;">
				<h4>En Çok Tıklanan Şehir Fırsatı Siteleri</h4>
				<div style="width:100%;" class="cloud">
					<table>
						<tbody>
							<%
							index = 0;
							for(SiteInfoBean site: stat.getTopClickedCityDealSites()){
								index++;
								if(index > 5)
								{
									break;
								}
							%>
							<tr>
								<td>
								<%if(site.getLogoURL().contains("nologo")){%>
									<img src="<%=request.getContextPath() + "/" + site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} else if(StringUtils.isNotBlank(site.getLogoURL())){%>
									<img height="75" src="<%=request.getContextPath() + site.getLogoURL()%>140.png" alt="<%=site.getSiteConfig().getSiteName()%> logosu"/>
								<%} %>
								</td>
								<td>
									<%=site.getSiteConfig().getSiteName()%>
								</td>		
							</tr>
							<%} %>
						</tbody>
					</table>
				</div>
			</div>	
		</div>
		<div class="cl"></div>
		<h3 id="pcount" style="margin-top: 40px;">Ürün Sayısı İstatistikleri</h3>
		<div class="prepend-top" >
			<div style="width:500px;margin-right:10px;float:left;" class="border">
				<h4>En Çok Ürün Bulunan Alışveriş Mağazaları</h4>
				<div style="width:100%;" class="cloud">
					<table>
						<tbody>
							<%
							index = 0;
							for(SiteInfoBean site: stat.getTopProductCountSites()){
								index++;
								if(index > 5)
								{
									break;
								}
							%>
							<tr>
								<td>
								<%if(site.getLogoURL().contains("nologo")){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} else if(StringUtils.isNotBlank(site.getLogoURL())){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} %>
								</td>
								<td>
									<a rel="no-follow" target="_blank" href="<%=site.getSiteConfig().getUrl()%>"><%=site.getSiteConfig().getDomainName()%></a>
								</td>
								<td>
									<%=Formatter.formatInteger(site.getProductCount())%> Ürün
								</td>		
							</tr>
							<%} %>
						</tbody>
					</table>
				</div>
			</div>	
			<div style="width:480px;float:left;">
				<h4>En Çok Ürün Bulunan Araç Siteleri</h4>
				<div style="width:100%;" class="cloud">
					<table>
						<tbody>
							<%
							index = 0;
							for(SiteInfoBean site: stat.getTopCarCountSites()){
								index++;
								if(index > 5)
								{
									break;
								}
							%>
							<tr>
								<td>
								<%if(site.getLogoURL().contains("nologo")){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} else if(StringUtils.isNotBlank(site.getLogoURL())){%>
									<img src="<%=site.getLogoURL()%>" alt="<%=site.getSiteConfig().getDomainName()%> logosu"/>
								<%} %>
								</td>
								<td>
									<a rel="no-follow" target="_blank" href="<%=site.getSiteConfig().getUrl()%>"><%=site.getSiteConfig().getDomainName()%></a>
								</td>
								<td>
									<%=Formatter.formatInteger(site.getProductCount())%> Araç
								</td>												
							</tr>
							<%} %>
						</tbody>
					</table>
				</div>
			</div>	
		</div>
		<div class="cl"></div>
	</div>	    
  </div>
</div>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>