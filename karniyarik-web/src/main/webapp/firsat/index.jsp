<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="../error.jsp"%>
<%@page import="com.karniyarik.web.json.SearchResultConverter"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.json.ProductResult"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.karniyarik.common.util.StringUtil"%>
<%@page import="com.karniyarik.web.util.RequestWrapper"%>
<%@page import="com.karniyarik.common.config.system.CategorizerConfig"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.ir.SearchConstants"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="com.karniyarik.site.SiteInfoProvider"%>
<%@page import="com.karniyarik.site.SiteInfoConfig"%>
<%@page import="com.karniyarik.common.statistics.vo.ProductClickLog"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%HttpCacheUtil.setResponseCacheAttributes(response, request, 2);
request.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.DAILY_OPPORTUNITY);
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
BaseCategoryUtil.setStaticPageBreadCrumb("Tek Ürün Fırsatları", "firsat", request);
request.setAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE, 7);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" 
	xmlns="http://www.w3.org/1999/xhtml" 
	version="XHTML+RDFa 1.0" 
	xmlns:addthis="http://www.addthis.com/help/api-spec" 
	xml:lang="tr"
	xmlns:fb="http://www.facebook.com/2008/fbml"
	xmlns:og="http://opengraphprotocol.org/schema/">
<head>
<title><%=searchResult.getPageTitle()%></title>
<meta name="description" content="<%=searchResult.getPageDescription()%>" />
<meta name="keywords" content="günlük fırsat ürünleri, en ucuz fiyatlar" />
<meta property="og:title" content="<%=searchResult.getPageTitle()%>"/>
<meta property="og:description" content="<%=searchResult.getPageDescription()%>"/>
<jsp:include page="../commonmeta.jsp" />
<jsp:include page="../commoninclude.jsp" />
</head>
<body>
<div class="ce ar tmh2 ">
	<jsp:include page="../nav.jsp"></jsp:include>
</div>

<div class="ce daily">
  <form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/urun/search.jsp" onsubmit="if($('#stab').val()=='1')this.action='<%=request.getContextPath()%>/urun/search.jsp'; submitForm()">
  <jsp:include page="../commontop.jsp"></jsp:include>
  </form>
  <%if(true){ //searchResult.isShowToolbar()){ %>
  <div class="sresult">
    <div class="rtime c3">
    </div>
    <h1><%=searchResult.getPageHeader()%></h1>
  </div>
  <div class="cl"></div>
  <%}%>
    
  <div class="l">
    <div class="filter">
      <div class="fr c4 sopi">
      </div>
      <div class="h b"></div>
      <div class="t b"></div>
      <div class="si"></div>
    </div>
  
    <div class="cl"></div>
    <!-- div class="lc" style="width:874px" -->
    <div class="lc car">
<!--      <div class="emailbox">-->
<!--      	<h2>Her Gün Tek Ürün Fırsatlarından Haberdar Olmak İçin:</h2>-->
<!--      	<form>-->
<!--			<table>-->
<!--         		<tbody>-->
<!--         		<tr>-->
<!--            		<th>E-posta</th>-->
<!--            		<td class="small"><input type="text" name="email" maxlength="65" id="emailsub"></td>-->
<!--            		<td class="left">-->
<!--            			<input type="submit" onfocus="this.blur();" onclick="this.blur();" value="Kayıt Ol" name="register">-->
<!--            		</td>-->
<!--         		</tr>-->
<!--      			</tbody>-->
<!--      		</table>      		-->
<!--      	</form>-->
<!--      </div>-->
    
      <div class="prods">
        <div class="cl"></div>
        
        <%for(ProductResult product: searchResult.getResults()){%>
        <div class="prod">
          <div class="pri">
	        <a rel="nofollow" target="_blank" href="<%=product.getLink()%>" title="Fırsatın sayfasına git"
	        	onclick="sendClickToAdsense('<%=product.getLink()%>', '<%=product.getSourceName()%>', '', 5)">
          	<%StringBuffer imgBuff = new StringBuffer();
        	  imgBuff.append(request.getContextPath());
        	  imgBuff.append("/imgrsz/");
        	  imgBuff.append(product.getImageName());
        	  imgBuff.append(".png");
        	  imgBuff.append("?w=180&h=140&v=");
        	  imgBuff.append(product.getImageURL());
        	  String imgUrl = imgBuff.toString();%>
		    	<img class="primg" src="<%=imgBuff%>" height="140" width="180" alt="Fırsat Ürünü resmi"/>
		    </a>
		    
	       	<a rel="nofollow" target="_blank" href="<%=product.getLink()%>" rel="nofollow" title="Fırsatın sayfasına git" 
	 			onclick="sendClickToAdsense('<%=product.getLink()%>', '<%=product.getSourceName()%>', '', 5)">
            	<img class="sprite go-city-site-btn go" src="<%=request.getContextPath()%>/images/transparent.gif" width="180" height="40" alt="Ürün Fırsatı Detayı"/>
            </a>              
          </div>
          <div class="prinf">
           <div class="price_bar">
                <div class="first item"><span class="bigger"><%=product.getPrice()%> <%=product.getPriceCurrency()%></span></div>
           </div>        
            <div class="desc">
            	<%=LinkedLabel.getShortenedLabel(product.getProductName(), 100)%>
            </div>          
            <div class="ad">
<!--				<div class="addthis_toolbox addthis_default_style">-->
<!--				    <a class="addthis_button_facebook" title="Facebook'ta paylaş"></a>-->
<!--				    <a class="addthis_button_twitter" title="Twitter'da paylaş"></a>-->
<!--				    <a class="addthis_button_email" title="Email ile paylaş"></a>-->
<!--				    <span class="addthis_separator">|</span>-->
<!--				    <a class="addthis_button_expanded" title="Diğerleri">Diğer</a>-->
<!--				</div>            -->
            </div>
             <div class="cl"></div>
<!--			<div class="cdwn">-->
<!--				<div class="headr">Geri Sayım</div>-->
<!--				<div class="time">1 Gün 5 Saat 4 Dakika</div>-->
<!--			</div>-->
			<div class="sr">
			    <a rel="nofollow" target="_blank" href="<%=product.getLink()%>" title="Fırsatın sayfasına git"
			    	onclick="sendClickToAdsense('<%=product.getLink()%>', '<%=product.getSourceName()%>', '', 5)">
		      		<img src="<%=request.getContextPath() + "/images/firsat/" + product.getSourceName()%>1.png" alt="<%=product.getSourceName()%> Logosu"/>
				</a>
			</div>
          </div>
        </div>
        <%}%>
        <div class="prod">
        </div>
        <div class="cl"></div>
      </div>
    </div>
  </div>

  <div class="r">
    <div class="radsh"></div>
    <div class="rads b">
		<script type="text/javascript"><!--
		google_ad_client = "ca-pub-5913641514503498";
		/* dailyDealTop */
		google_ad_slot = "3947840750";
		google_ad_width = 160;
		google_ad_height = 600;
		//-->
		</script>
		<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>    
    </div>
	<div class="rads b">
   	<%if(searchResult.getResults().size() > 4) {%>
		<script type="text/javascript"><!--
		google_ad_client = "pub-5913641514503498";
		/* 120x600, oluşturulma 29.04.2010 */
		google_ad_slot = "1460716633";
		google_ad_width = 120;
		google_ad_height = 600;
		//-->
		</script>
		<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>		
   	<%} %>
	</div>	   
	<div class="rads b">
   	<%if(searchResult.getResults().size() > 6) {%>
		<script type="text/javascript"><!--
		google_ad_client = "pub-5913641514503498";
		/* 120x600, oluşturulma 29.04.2010 */
		google_ad_slot = "3115700083";
		google_ad_width = 120;
		google_ad_height = 600;
		//-->
		</script>
		<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>   	
   	<%} %>
	</div>	   	
  </div>
</div>
<div class="cl" style="height:20px"></div>
<div></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
</body>
</html>