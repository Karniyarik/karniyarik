<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="../error.jsp"%>
<%@page import="com.karniyarik.web.util.WebApplicationDataHolder"%>
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
<%@page import="java.net.URLEncoder"%>
<%@page import="com.karniyarik.web.category.OtelUtil"%>
<%@page import="com.karniyarik.web.category.UtilProvider"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.servlet.image.ImageServlet"%>
<%HttpCacheUtil.setSearchResponseCacheAttributes(response, request);%>
<%request.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.HOTEL);
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
request.setAttribute(RequestWrapper.SEARCH_RESULT, searchResult);
int rSort = 0;
int pSort = 0;
switch(searchResult.getSort()){
case 1: rSort = 1; break;
case 2: pSort = 1; break;
case 3: pSort = 2; break;
}
if(searchResult.isShowEmptySearchError()) {
	response.sendRedirect("http://www.karniyarik.com");
}
OtelUtil otelUtil = (OtelUtil) UtilProvider.getDomainUtil(CategorizerConfig.HOTEL_TYPE);
BaseCategoryUtil.setBreadCrumb(request, searchResult.getBreadcrumb());

boolean showQuerySuggestion = searchResult.getTotalHits() > 100 && searchResult.getSuggestedQueries().size() > 0;
boolean showGASFAtTop = !showQuerySuggestion && searchResult.getResults().size() > 2;

if(searchResult.isShowNotFound()) { request.setAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE, 9);}
else{request.setAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE, searchResult.getSponsoredProducts().size() > 0 ? 5 : 4);}
%>
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
<meta name="keywords" content="<%=searchResult.getPageKeywords()%>" />
<meta property="og:title" content="<%=searchResult.getPageTitle()%>"/>
<meta property="og:description" content="<%=searchResult.getPageDescription()%>"/>
<%if(StringUtils.isNotBlank(searchResult.getCanonical())){%>
<link rel="canonical" href="http://www.karniyarik.com<%=searchResult.getCanonical()%>" />
<%} %>
<%if(StringUtils.isNotBlank(searchResult.getPageImgSrc())){
	String pageImageSrc = ImageServlet.getImageUrl(request,searchResult.getPageImgSrc(), "");%>
<link rel="image_src" href="<%=pageImageSrc%>" />
<meta property="og:image" content="<%=pageImageSrc%>"/>
<%}%>
<jsp:include page="../commonmeta.jsp" />
<jsp:include page="../commoninclude.jsp">
 <jsp:param name="dataedited" value="true" />
</jsp:include>
<script src="http://www.google.com/jsapi"></script>
<script type="text/javascript" charset="utf-8">google.load('ads.search', '2');</script>
</head>
<body>
<%if(searchResult.isShowProducts() && !searchResult.isSimilar()) {%>
<script type="text/javascript" charset="utf-8">
	<%String queryForAds = StringEscapeUtils.escapeJavaScript(searchResult.getOriginalQuery());%>
	var showAtTop = <%=showGASFAtTop%>
	var pageOptions = {
	'pubId' : 'pub-5913641514503498',
	'query' : '<%=queryForAds%>'
	}
	var adblock1 = {
			'container' : 'adblock1',
			'number' : 2,
			'width' : 'auto',
			'lines' : 2,
			'fontFamily' : 'arial',
			'fontSizeTitle' : '14px',
			'fontSizeDescription' : '14px',
			'fontSizeDomainLink' : '14px',
			'colorTitleLink' : '#89266D',
			'colorText' : '#4D4D4D',
			'colorDomainLink' : '#548600',
			'colorBackground': '#F2F2F2',
			'channel' : '9791473423+1041500583'
	};
	
	var adblock2 = {
			'container' : 'adblock2',
			'number' : 4,
			'width' : 'auto',
			'lines' : 2,
			'fontFamily' : 'arial',
			'fontSizeTitle' : '14px',
			'fontSizeDescription' : '14px',
			'fontSizeDomainLink' : '14px',
			'colorTitleLink' : '#89266D',
			'colorText' : '#4D4D4D',
			'colorDomainLink' : '#548600',
			'colorBackground': '#F2F2F2',
			'channel' : '5348926787+1041500583'
	};

	var adblock3 = {
			'container' : 'adblock3',
			'number' : 4,
			'width' : 'auto',
			'lines' : 3,
			'fontFamily' : 'arial',
			'fontSizeTitle' : '12px',
			'fontSizeDescription' : '12px',
			'fontSizeDomainLink' : '12px',
			'colorTitleLink' : '#89266D',
			'colorText' : '#4D4D4D',
			'colorDomainLink' : '#548600',
			'colorBackground': '#F2F2F2',
			'channel' : '4424461175+1041500583'
	};
	new google.ads.search.Ads(pageOptions, adblock1, adblock2, adblock3);
</script>
<%} %>
<div class="ce ar tmh2 ">
	<jsp:include page="../nav.jsp"></jsp:include>
</div>
<form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/otel/search.jsp" onsubmit="setFormInSearchPageAction(this, '<%=request.getContextPath()%>'); submitForm()">
<div class="ce">
  <div class="search search2">
    <div class="search2l">
      <div class="search2r">
        <div class="stab">
          <a rel="nofollow" class="ri" href="#"><img src="<%=request.getContextPath()%>/images/bas.png" width="25" height="23" alt="Ürün" /><span>Ürün</span></a>
        </div>
        <div class="stab tho">
          <a rel="nofollow" class="ri" href="#"><img src="<%=request.getContextPath()%>/images/car.png" width="29" height="23" alt="Araç" /><span>Araç</span></a>
        </div>
        <div class="stab tho1 act">
          <a rel="nofollow" class="ri" href="#"><img src="<%=request.getContextPath()%>/images/otel.png" width="29" height="23" alt="Otel" /><span>Otel</span></a>
        </div>
        <div class="fform2">
          <input id="words" name="query" type="text" class="trans inp mhide" value="<%=searchResult.getEscapedQuery()%>" />
          <input class="trans sub" name="" type="submit" value="Ara" />
          <input id="stab" name="stab" type="hidden" value="3" />
        </div>
      </div>
    </div>
  </div>
	
  <jsp:include page="../commontop_inf.jsp"></jsp:include>  
  
  <%if(true){ //searchResult.isShowToolbar()){ %>
  <div class="sresult">
    <%if(searchResult.isShowProducts()) {%>
    <%if(!searchResult.isSimilar()){ %>
    <div class="rtime c3">
    	<%=searchResult.getTimeTaken()%> sn'de 
    	<%if(searchResult.getCategoryPropMap().get(SearchConstants.STORE) != null) {%> 
    		<%=NumberFormat.getInstance(Locale.ENGLISH).format(searchResult.getCategoryPropMap().get(SearchConstants.STORE).size())%> siteden
    	<%}%> 
    	<%=NumberFormat.getInstance(Locale.ENGLISH).format(searchResult.getTotalHits())%> otel bulundu
    </div>
    <%}%>
    <h1><%=searchResult.getPageHeader()%></h1>
    <%}//ishow products%>
  </div>
  <div class="cl"></div>
  <%}%>
    
  <div class="l">
    
    <div class="filter">
      <div class="fr c4 sopi">
        <select name="psize" class="fl" title="Her sayfada görüntülenecek otel sayısını belirle" onchange="$('#frmSearch').submit();">
          <option title="Her sayfada 10 otel göster" value="10" <%=searchResult.getPageSize() == 10 ? "selected='selected'" : ""%>>10 sonuç göster</option>
		  <option title="Her sayfada 20 otel göster" value="20" <%=searchResult.getPageSize() == 20 ? "selected='selected'" : ""%>>20 sonuç göster</option>
		  <option title="Her sayfada 30 otel göster" value="30" <%=searchResult.getPageSize() == 30 ? "selected='selected'" : ""%>>30 sonuç göster</option>          
        </select>
        <label title="Otel resimlerinin gösterilip gösterilmeyeceğini belirle">
          <input <%if(searchResult.getShowImages()==0){%>checked="checked" <%}%> type="checkbox" name="simg" id="checkbox" class="fl" style="height:15px;margin:3px 5px;vertical-align:middle" onchange="$('#frmSearch').submit();"/>
          <span class="f2">Resimleri göster<b>me</b></span>
        </label>
      </div>
      <div class="h b">Sonuçları daralt</div>
      <div class="t b">Sırala: </div>
      <input id="sort" name="sort" type="hidden" value="<%=searchResult.getSort()%>"/>
      <div class="si" title="Otelleri arama sorgusu ile ilişkisinin benzerliğine göre sırala">
      	<a rel="nofollow" href="#" onclick="sortResults(1)" rel="nofollow"><img src="<%=request.getContextPath()%>/images/ud<%=rSort%>.png" width="19" height="13" alt="Sorgu ile ilişkiye göre sırala" />Benzerlik</a>
      </div>
      <div class="si" title="Otelleri fiyata göre ucuzdan/pahalıya ya da pahalıdan/ucuza göre sırala">
      	<a rel="nofollow" href="#" onclick="sortResults(2)" rel="nofollow"><img src="<%=request.getContextPath()%>/images/ud<%=pSort%>.png" width="19" height="13" alt="Fiyata göre sırala" />Fiyat</a>
      </div>
    </div>
    
    <div class="cl"></div>

    <!-- div class="lc" style="width:874px" -->
    <div class="lc car <%=searchResult.isShowProducts()? "" : "full"%>">
      <jsp:include page="../didyoumean.jsp"></jsp:include>
      <jsp:include page="../searchnotfound.jsp"></jsp:include>
      <%if(searchResult.isShowFilters()) {%>
      <div class="prop" style="margin-bottom:10px">
        <div class="c1">
          <jsp:include page="filterprice.jsp"></jsp:include>
          <jsp:include page="filtercountry.jsp"></jsp:include>
          <jsp:include page="filtercity.jsp"></jsp:include>
          <jsp:include page="filterstar.jsp"></jsp:include>
          
          <div class="cl"></div>
        </div>
      </div>
      <%} %>
      <jsp:include page="../querysuggestion.jsp"></jsp:include>
      <div class="gafs" id="adblock1" style="max-height:144px;"></div>
      <div class="nav <%=searchResult.isSimilar() ? "simnav" : ""%>">
        <%if(!searchResult.isSimilar()) {%>
      	<jsp:include page="../pager.jsp"></jsp:include>
      	<%} %>
      	<%if(searchResult.getSponsoredProducts() != null && searchResult.getSponsoredProducts().size() > 0){%>
        <span class="f3">Sponsored links:</span>
        <%} %>
      </div>
      <%if(searchResult.getSponsoredProducts() != null && searchResult.getSponsoredProducts().size() > 0){%>
      <div class="spon">
        <%for(ProductResult sponsored: searchResult.getSponsoredProducts()){%>
        <div class="prod" style="margin:0;background:url(<%=request.getContextPath()%>/images/prbr.png) no-repeat right -1px" typeof="v:Product">
          <div class="pri">
			<%if(searchResult.isShowImages() == 1){String imgUrl = ImageServlet.getImageUrl(request, sponsored);%>
	          <img src="<%=imgUrl%>" alt="Otel resmi" property="v:photo" content="<%=imgUrl%>"/>
          	<%} %>
          </div>
          <div class="prinf">
            <div class="h">
            	<a rel="nofollow" target="_blank" href="<%=sponsored.getLink()%>" rel="nofollow" title="Otelin sitedeki sayfasına git" 
		 		onclick="productClicked('<%=sponsored.getLink()%>', '<%=sponsored.getSourceName()%>', '<%=sponsored.getProductName()%>', 7)" rel="v:url">  
		 		<%=sponsored.getProductHighlightedName()%>
		 		</a>
		 		<span style="display:none" property="v:name"><%=sponsored.getProductName()%></span>
            </div>
            <div class="d">Son güncelleme: <span title="Son ürün güncelleme tarihi"><%=sponsored.getLastFetchDate()%></span></div>
            <div class="ad">
               <span property="v:brand"><%=sponsored.getBrand()%></span>
            </div>
            <div class="sr">
	            <span class="addthis_button" 
	            	addthis:url="<%=sponsored.getShareLink()%>" addthis:title="<%=sponsored.getShareName()%>" addthis:description="<%=sponsored.getShareDesc()%>">
	      			<img width="88" height="17" style="cursor: pointer;" alt="Paylaş" src="<%=request.getContextPath()%>/images/share.png" title="Arkadaşlarınla paylaş"/>
	      		</span>
              &nbsp;
			  <a rel="nofollow" title="Benzer Ürünler" href="<%=sponsored.getShareLink()%>" class="c2">Benzer Oteller</a>
              <img src="<%=request.getContextPath()%>/images/more.png" width="14" height="13" alt="Benzer Oteller" />
            </div>            
          </div>
          <div class="prpi">
            <div class="pprice">
				<span property="v:price" title="Fiyatı"><%=sponsored.getPrice()%> <%=sponsored.getPriceCurrency()%></span> 
            </div>
            <div class="psourc">
	            <!-- span class="c1">Kaynak:</span --> 
				<%=sponsored.getSourceName()%>
            </div>
            <div >
	            <a target="_blank" href="<%=sponsored.getLink()%>" rel="nofollow" title="Otelin sitedeki sayfasına git"
	            onclick="productClicked('<%=sponsored.getLink()%>', '<%=sponsored.getSourceName()%>', '<%=sponsored.getProductName()%>', 6)">
	            	<img src="<%=request.getContextPath()%>/images/gostore.png"  width="119" height="23"  alt="Siteye git"/>
	            </a>              
            </div>
          </div>
          <div class="deta">
            <div class="dhea">Detay</div>
            <div class="drow"><span class="c1">Şehir: </span>
            <% if (!StringUtils.isEmpty(sponsored.getProperty("city"))) { %>
  			<%=sponsored.getProperty("city") %>
  			<% } %>
            </div>
          </div>
        </div>
        <%}%>
      </div>
      <%}%>
      <div class="prods">
        <div class="cl"></div>
        <%for(ProductResult product: searchResult.getResults()){%>
        <div class="prod otel" typeof="v:Product">
          <div class="pri">
			<%if(searchResult.isShowImages() == 1){String imgUrl = ImageServlet.getImageUrl(request, product);%>
	          <img src="<%=imgUrl%>" alt="Otel resmi" property="v:photo" content="<%=imgUrl%>"/>
          	<%} %>
          </div>
          <div class="prinf" style="margin-left:10px;">
            <div class="h">
            	<a target="_blank" href="<%=product.getLink()%>" rel="nofollow" title="Otelin sitedeki sayfasına git" 
		 		onclick="productClicked('<%=product.getLink()%>', '<%=product.getSourceName()%>', '<%=product.getProductName()%>', 5)" rel="v:url">
		 		<%=product.getProductHighlightedName()%>
		 		</a>
		 		<span style="display:none" property="v:name"><%=product.getProductName()%></span>
            </div>
            <div class="ad bold 12px" style="margin-bottom:5px;">
            	<span><%=product.getProperty("address")%>, <%=product.getProperty("zip")%> <%=product.getProperty("city")%>, <%=product.getProperty("country")%></span> &nbsp;
             </div>
            <div class="ad desc">
            	<span><%=LinkedLabel.getShortenedLabel(product.getProperty("description"), 230)%></span> &nbsp;
             </div>
             <div class="d lg">Son güncelleme: <span title="Son güncelleme tarihi"><%=product.getLastFetchDate()%></span></div>
            <div class="sr">
	            <span class="addthis_button" 
	            	addthis:url="<%=product.getShareLink()%>" addthis:title="<%=product.getShareName()%>" addthis:description="<%=product.getShareDesc()%>">
	      			<img width="88" height="17" style="cursor: pointer;" alt="Paylaş" src="<%=request.getContextPath()%>/images/share.png" title="Arkadaşlarınla paylaş"/>
	      		</span>
              &nbsp;
              <%
            	String longitude = product.getProperty("longitude");
            	String latitude = product.getProperty("latitude");
            	if(StringUtils.isNotBlank(longitude) && StringUtils.isNotBlank(latitude)){ 
	              	String name = URLEncoder.encode(product.getProductName(), StringUtil.DEFAULT_ENCODING);
	              	String imageURL = URLEncoder.encode(product.getImageURL(), StringUtil.DEFAULT_ENCODING);
	              	String address = URLEncoder.encode(product.getProperty("address"), StringUtil.DEFAULT_ENCODING);
	              	String zip = URLEncoder.encode(product.getProperty("zip"), StringUtil.DEFAULT_ENCODING);
	              	String city = URLEncoder.encode(product.getProperty("city"), StringUtil.DEFAULT_ENCODING);
	              	String link = URLEncoder.encode(product.getLink(), StringUtil.DEFAULT_ENCODING);
              %>
              <!-- href="<%=request.getContextPath()%>/otel/map.jsp?n=<%=name%>&l=<%=link%>&a=<%=address%>&c=<%=city%>&z=<%=zip%>&i=<%=imageURL%>&lat=<%=latitude%>&lon=<%=longitude%>"> -->
              <a rel="nofollow" class="iframe c2" title="Haritada göster" 
              href="<%=request.getContextPath()%>/otel/map.jsp?n=<%=name%>&l=<%=link%>&a=<%=address%>&c=<%=city%>&z=<%=zip%>&lat=<%=latitude%>&lon=<%=longitude%>">
                Haritada Göster
              </a>
              <img src="<%=request.getContextPath()%>/images/more.png" width="14" height="13" alt="Haritada Göster" />
              <%} %>
              &nbsp;&nbsp;              
  			  <a rel="nofollow" title="Benzer Oteller" href="<%=product.getShareLink()%>" class="c2">Benzer Oteller</a>
              <img src="<%=request.getContextPath()%>/images/more.png" width="14" height="13" alt="Benzer Oteller" /></div>
          </div>
          <div class="prpi">
            <div class="pprice">
                <%if(!product.getPrice().equalsIgnoreCase("?")){%>
				<span property="v:price" title="Fiyatı"><%=product.getPrice()%> <%=product.getPriceCurrency()%></span>        
				<span class="ppriceadd">'dan başlayan fiyatlarla</span>
				<%}else{%>
				<span class="ppriceadd">Fiyatı siteden öğreniniz</span>
				<%}%>
            </div>
            <!-- div class="psourc">
				<%=product.getSourceName()%>
            </div -->
            <div class="stars">
            	<img src="<%=request.getContextPath()%>/images/stars/stars<%=otelUtil.getStar(product)%>.png" width="81" height="15" alt="Değerlendirme"/>
            </div>
            <div>
            	<a rel="nofollow" target="_blank" href="<%=product.getLink()%>" rel="nofollow" title="Otelin sitedeki sayfasına git" 
		 		onclick="productClicked('<%=product.getLink()%>', '<%=product.getSourceName()%>', '<%=product.getProductName()%>', 5)">
	            	<img src="<%=request.getContextPath()%>/images/gosite.png" width="119" height="23"  alt="Siteye git"/>
	            </a>              
            </div>
            <div>
            	<!-- a rel="nofollow" title="Güncel Fiyat" class="iframe_otel c2" href="<%=request.getContextPath()%>/otel/pricecheck.jsp">Güncel Fiyat&gt;&gt;</a -->
            </div>
          </div>
          <div class="deta">
            <div class="dhea">Detay</div>
            <div class="drow"><span class="c1">Şehir: </span>
            <% if (!StringUtils.isEmpty(product.getProperty("city"))) { %>
  			<%=product.getProperty("city") %>
  			<% } %>
            </div>
            <div class="drow">&nbsp;</div>
            <div class="drow">&nbsp;</div>
            <div class="drow">&nbsp;</div>
            <div class="drow">&nbsp;</div>
            <div class="drow">&nbsp;</div>
          </div>
        </div>
        <%}%>
        <div class="cl"></div>
      </div>
      <div class="nav">
        <jsp:include page="../pager.jsp"></jsp:include>
      </div>

	 <div id="adblock2" class="gafs" style="max-height:240px;"></div>
    
    <%if(searchResult.isSimilar()){ %>
	 	<div class="cl"></div> 
		<div>
			<script type="text/javascript"><!--
			google_ad_client = "ca-pub-5913641514503498";
			/* otelSimilarHor */
			google_ad_slot = "3337473278";
			google_ad_width = 728;
			google_ad_height = 90;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>				
		</div>
		<div class="cl"></div>
	  <%} %>	      
      <%if(searchResult.getResults().size() > 0){%>
	  <div style="margin-top:10px;">
		  <fb:like-box profile_id="90334878760" width="876"  height="180" connections="15" stream="false" 
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
		</div>
		<%}%>		      
    </div>   
  </div>

  <div class="r">
	<div class="radsh"></div>
    <%if(searchResult.isShowFilters() || searchResult.isSimilar()) {%>
    <%if(searchResult.getResults().size() > 0) {%>
    <div class="rads b">
    	<img src="<%=request.getContextPath()%>/images/sehir-firsati/mail_block.png" onclick="javascript: prompt_email();" style="cursor:pointer;"/>
    </div>
    <div class="rads b">
		<script type="text/javascript"><!--
		google_ad_client = "ca-pub-5913641514503498";
		/* otelSideTop */
		google_ad_slot = "2763562207";
		google_ad_width = 160;
		google_ad_height = 600;
		//-->
		</script>
		<script type="text/javascript"
		src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
		</script>
    </div>
    <%}%>
    <%if(searchResult.getResults().size() > 2) {%>
    <div class="rads b">
    	<iframe scrolling="no" width="162" height="395" frameborder="0" marginheight="0" marginwidth="0" src ="<%=request.getContextPath()%>/sehir-firsati/slider/slider.jsp" style="overflow:hidden;border:none;left:0pt;top: 0pt;">
  			<p>Karnıyarık Şehir Fırsatları</p>
		</iframe>
    </div>
    <%}%>
    <%if(searchResult.getResults().size() > 5) {%>
    <div class="rads b">
	    <iframe scrolling="no" width="162" height="405" frameborder="0" marginheight="0" marginwidth="0" src ="<%=request.getContextPath()%>/firsat/widget_y.jsp" style="overflow:hidden;border:none;left:0pt;top: 0pt;">
  			<p>Karnıyarık Tek Ürün Fırsatları</p>
		</iframe>    
    </div>
    <%} %>
    <%if(searchResult.getResults().size() > 8) {%>
	<div class="rads b">
	<div id="adblock3"></div>
	</div>	   
	<%}%>
    <%}%>   
  </div>
</div>
</form>
<div class="cl" style="height:50px"></div>
<div ></div>
<jsp:include page="../commonfooter.jsp"></jsp:include>
<jsp:include page="../supporters.jsp"></jsp:include>
</body>
</html>