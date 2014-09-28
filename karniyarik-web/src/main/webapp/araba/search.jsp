<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page errorPage="../error.jsp"
%><%@page import="com.karniyarik.web.json.SearchResultConverter"
%><%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"
%><%@page import="com.karniyarik.web.json.SearchResult"
%><%@page import="com.karniyarik.web.json.ProductResult"
%><%@page import="com.karniyarik.web.json.LinkedLabel"
%><%@page import="org.apache.commons.lang.StringUtils"
%><%@page import="org.apache.commons.lang.StringEscapeUtils"
%><%@page import="com.karniyarik.common.util.StringUtil"
%><%@page import="com.karniyarik.web.util.RequestWrapper"
%><%@page import="com.karniyarik.common.config.system.CategorizerConfig"
%><%@page import="java.util.List"
%><%@page import="com.karniyarik.ir.SearchConstants"
%><%@page import="java.text.NumberFormat"
%><%@page import="java.util.Locale"
%><%@page import="com.karniyarik.site.SiteInfoProvider"
%><%@page import="com.karniyarik.site.SiteInfoConfig"
%><%@page import="com.karniyarik.common.statistics.vo.ProductClickLog"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.servlet.image.ImageServlet"
%><%
HttpCacheUtil.setSearchResponseCacheAttributes(response, request);
request.setAttribute(RequestWrapper.CATEGORY, CategorizerConfig.CAR);
SearchResult searchResult = SearchResultRequestWrapper.getInstance(request).getSearchResult();
request.setAttribute(RequestWrapper.SEARCH_RESULT, searchResult);
int rSort = 0;
int pSort = 0;
int kSort = 0;
int ySort = 0;
int hSort = 0;
switch(searchResult.getSort()){
case 1: rSort = 1; break;
case 2: pSort = 1; break;
case 3: pSort = 2; break;
case 4: kSort = 1; break;
case 5: kSort = 2; break;
case 6: ySort = 1; break;
case 7: ySort = 2; break;
case 8: hSort = 1; break;
case 9: hSort = 2;break;
}

BaseCategoryUtil.setBreadCrumb(request, searchResult.getBreadcrumb());

boolean showQuerySuggestion = searchResult.getTotalHits() > 100 && searchResult.getSuggestedQueries().size() > 0;
boolean showGASFAtTop = !showQuerySuggestion && searchResult.getResults().size() > 2;

if(searchResult.isShowNotFound()) { request.setAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE, 10);}
else{request.setAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE, searchResult.getSponsoredProducts().size() > 0 ? 3 : 2);}

%><html xmlns:v="http://rdf.data-vocabulary.org/#" 
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
		<meta property="fb:type" content="product"/>
		<meta property="og:title" content="<%=searchResult.getPageTitle()%>"/>
		<meta property="og:description" content="<%=searchResult.getPageDescription()%>"/><%
		if(StringUtils.isNotBlank(searchResult.getPageImgSrc())){
			String pageImageSrc = ImageServlet.getImageUrl(request,searchResult.getPageImgSrc(), "");
		%><link rel="image_src" href="http://www.karniyarik.com<%=pageImageSrc%>" />
		<meta property="og:image" content="http://www.karniyarik.com<%=pageImageSrc%>"/><%
		}
		if(searchResult.isShowNotFound()){
		%><meta property="og:image" content="http://www.karniyarik.com/images/logo2.png"/><%
		} 
		if(StringUtils.isNotBlank(searchResult.getCanonical())){%>
		<link rel="canonical" href="http://www.karniyarik.com<%=searchResult.getCanonical()%>" /><%
		}
		%><jsp:include page="../commonmeta.jsp" />
		<jsp:include page="../commoninclude.jsp">
		 <jsp:param name="dataedited" value="true" />
		</jsp:include>
		<script src="http://www.google.com/jsapi" type="text/javascript"></script>
		<script type="text/javascript" charset="utf-8">google.load('ads.search', '2');</script>
	</head>
	<body><%if(searchResult.isShowProducts() && !searchResult.isSimilar()) {
		%><script type="text/javascript" charset="utf-8">
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
					'channel' : '0946833349+6750908220'
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
					'channel' : '1034028995+6750908220'
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
					'channel' : '2000917743+6750908220'
			};
			new google.ads.search.Ads(pageOptions, adblock1, adblock2, adblock3);
		</script><%} 
		%><div class="ce ar tmh2 ">
			<jsp:include page="../nav.jsp"></jsp:include>
		</div>
		<form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/araba/search.jsp" onsubmit="setFormInSearchPageAction(this, '<%=request.getContextPath()%>'); submitForm()">
		<div class="ce">
		  <jsp:include page="../commontop.jsp">
			<jsp:param name="queryi" value="<%=searchResult.getEscapedQuery()%>" />		  	
			<jsp:param name="stabi" value="2" />
		  </jsp:include>
		  <%if(true){ //searchResult.isShowToolbar()){ %>
		  <div class="sresult">
		    <%if(searchResult.isShowProducts()) {%>
		    <%if(!searchResult.isSimilar()){ %>
		    <div class="rtime c3">
		    	<%=searchResult.getTimeTaken()%> sn'de 
		    	<%if(searchResult.getCategoryPropMap().get(SearchConstants.STORE) != null) {%> 
		    		<%=NumberFormat.getInstance(Locale.ENGLISH).format(searchResult.getCategoryPropMap().get(SearchConstants.STORE).size())%> siteden
		    	<%}%> 
		    	<%=NumberFormat.getInstance(Locale.ENGLISH).format(searchResult.getTotalHits())%> ürün bulundu
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
		        <select name="psize" class="fl" title="Her sayfada görüntülenecek ürün sayısını belirle" onchange="$('#frmSearch').submit();">
		          <option title="Her sayfada 15 ürün göster" value="15" <%=searchResult.getPageSize() == 15 ? "selected='selected'" : ""%>>15 sonuç göster</option>
				  <option title="Her sayfada 30 ürün göster" value="30" <%=searchResult.getPageSize() == 30 ? "selected='selected'" : ""%>>30 sonuç göster</option>
				  <option title="Her sayfada 50 ürün göster" value="50" <%=searchResult.getPageSize() == 50 ? "selected='selected'" : ""%>>50 sonuç göster</option>          
		        </select>
		        <label title="Ürün resimlerinin gösterilip gösterilmeyeceğini belirle">
		          <input <%if(searchResult.getShowImages()==0){%>checked="checked" <%}%> type="checkbox" name="simg" id="checkbox" class="fl" style="height:15px;margin:3px 5px;vertical-align:middle" onchange="$('#frmSearch').submit();"/>
		          <span class="f2">Resimleri göster<b>me</b></span>
		        </label>
		      </div>
		      <div class="h b">Sonuçları daralt</div>
		      <div class="t b">Sırala: </div>
		      <input id="sort" name="sort" type="hidden" value="<%=searchResult.getSort()%>"/>
		      <div class="si" title="Araçları arama sorgusu ile ilişkisinin benzerliğine göre sırala">
		      	<a href="#" onclick="sortResults(1)" rel="nofollow">
		      		<img class="sprite sort-ud-<%=rSort%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="Sorgu ile ilişkiye göre sırala" />
		      		Benzerlik
		      		</a>
		      </div>
		      <div class="si" title="Araçları fiyata göre ucuzdan/pahalıya ya da pahalıdan/ucuza göre sırala">
		      	<a href="#" onclick="sortResults(2)" rel="nofollow">
		      		<img class="sprite sort-ud-<%=pSort%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="Fiyata göre sırala" />
		      		Fiyat
		      	</a>
		      </div>
		      <div class="si" title="Araçları kilometresine göre azalandan/artana ya da artandan/azalana göre sırala">
		      	<a href="#" onclick="sortResults(4)" rel="nofollow">
		      		<img class="sprite sort-ud-<%=kSort%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="Kilometeye göre sırala" />
		      		Km
		      	</a>
		      </div>
		      <div class="si" title="Araçları yılına göre azalandan/artana ya da artandan/azalana göre sırala">
		      	<a href="#" onclick="sortResults(6)" rel="nofollow">
		      		<img class="sprite sort-ud-<%=ySort%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="Yıla göre sırala" />
		      		Yıl
		      	</a>
		      </div>
		      <div class="si" title="Araçları motor gücüne göre azalandan/artana ya da artandan/azalana göre sırala">
		      	<a href="#" onclick="sortResults(8)" rel="nofollow">
		      		<img class="sprite sort-ud-<%=hSort%>" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="Motor gücüne göre sırala" />
		      		Hp
		      	</a>
		      </div>
		    </div>
		    
		    <div class="cl"></div>
		
		    <!-- div class="lc" style="width:874px" -->
		    <div class="lc car <%=searchResult.isShowProducts()? "" : "full"%>">
		      <jsp:include page="../didyoumean.jsp"></jsp:include>
		      <jsp:include page="../searchnotfound.jsp"></jsp:include>
		      <jsp:include page="../otherdomainresults.jsp"></jsp:include>
		      <%if(searchResult.isShowFilters()) {%>
		      <div id="cfltrb" class="cfltrbc cfltrbcd">&nbsp;</div>
		      <%} %>
		      <jsp:include page="../querysuggestion.jsp"></jsp:include>
		      <div class="gafs" id="adblock1" style="max-height:144px;"></div>
		      <div class="toppg nav <%=searchResult.isSimilar() ? "simnav" : ""%>">
		        <%if(!searchResult.isSimilar() && searchResult.getPagerResults().size() > 0) {%>
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
		          <%
		          String imgUrl = ImageServlet.getImageUrl(request, sponsored);
		          if(searchResult.isShowImages() == 1){%>
			          <img src="<%=imgUrl%>" alt="Araç resmi" property="v:photo" content="<%=imgUrl%>" class="lazy"/>
		          <%} %>
		          </div>
		          <div class="prinf">
		            <div class="h">
		            	<a rel="nofollow" target="_blank" href="<%=sponsored.getLink()%>" title="Aracın sitedeki sayfasına git" 
				 		onclick="productClicked('<%=sponsored.getLink()%>', '<%=sponsored.getSourceName()%>', '<%=sponsored.getProductName()%>', 3)">  
				 		<%=sponsored.getProductHighlightedName()%>
				 		</a>
				 		<span style="display:none" property="v:name"><%=sponsored.getProductName()%></span>
		            </div>
		            <div class="ad">
		               <span property="v:brand"><%=sponsored.getBrand()%></span>
		            </div>
					<div class="d lg lstupdt">Son güncelleme: <span title="Son ürün güncelleme tarihi"><%=sponsored.getLastFetchDate()%></span></div>            
		            <div class="sr">
			            <span class="addthis_button" 
			            	addthis:url="<%=sponsored.getShareLink()%>" addthis:title="<%=sponsored.getShareName()%>" addthis:description="<%=sponsored.getShareDesc()%>"
			            	addthis:email_vars="{img:'http://www.karniyarik.com<%=imgUrl%>',price:'<%=sponsored.getPrice()%> TL',sourcesite:'<%=sponsored.getSourceName()%>'}">
			      			<img width="88" height="17" class="sprite share-btn"  style="cursor: pointer;" alt="Paylaş" src="<%=request.getContextPath()%>/images/transparent.gif" title="Arkadaşlarınla paylaş"/>
			      		</span>&nbsp;
			      		<span>
							<img width="88" height="17" class="sprite samename-btn" style="cursor: pointer;" alt="ara" src="<%=request.getContextPath()%>/images/transparent.gif" title="Aynı isim ile ara" onclick="searchWithSameName('<%=sponsored.getSameNamedQueryLink()%>');"/>
			      		</span>&nbsp;
						<a rel="nofollow v:url" title="Benzer Araçlar" href="<%=sponsored.getShareLink()%>" class="c2">Benzer Araçlar</a>
						<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Benzer Araçlar"/>
		            </div>            
		          </div>
		          <div class="prpi">
		            <div class="pprice">
						<span property="v:price" title="Fiyatı"><%=sponsored.getPrice()%> TL</span>
			            <span class="org-pri" title="Orjinal Fiyatı">
			            <%if(StringUtils.isNotBlank(sponsored.getPriceAlternate())){ %>
			            (<%=sponsored.getPriceAlternate()%> <%=sponsored.getPriceCurrency()%>)
			            <%}%>
			            </span>
		            </div>
		            <div class="psourc">
			            <!-- span class="c1">Kaynak:</span --> 
						<%=sponsored.getSourceName()%>
		            </div>
		            <div >
			            <a target="_blank" href="<%=sponsored.getLink()%>" rel="nofollow" title="Aracın sitedeki sayfasına git"
			            onclick="productClicked('<%=sponsored.getLink()%>', '<%=sponsored.getSourceName()%>', '<%=sponsored.getProductName()%>', 3)">
			            	<img class="sprite go-site-btn" src="<%=request.getContextPath()%>/images/transparent.gif" width="119" height="23" alt="Siteye git"/>
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
		            <div class="drow"><span class="c1">Yılı:</span>
		       		<% if (!StringUtils.isEmpty(sponsored.getProperty("modelyear"))) { %>
		  			<%=sponsored.getProperty("modelyear")%>
		  			<% } %>
		            </div>
		            <div class="drow"><span class="c1">Km'si: </span>
					<% if (!StringUtils.isEmpty(sponsored.getProperty("km"))) { %>
			  		<%= sponsored.getProperty("km") %> km
			  		<% } %>
		            </div>
		            <div class="drow"><span class="c1">Gücü: </span>
					<% if (!StringUtils.isEmpty(sponsored.getProperty("enginepower"))) { %>
			  		<%= sponsored.getProperty("enginepower") %> hp
			  		<% } %>
		            </div>
		          </div>
		        </div>
		        <%}%>
		      </div>
		      <%}%>
		      <div class="prods">
		        <div class="cl"></div>
		        <%
		        int index = 0;
		        for(ProductResult product: searchResult.getResults()){
					index++; 
					if(index == 2 && searchResult.isSimilar()){
				%>
				<div class="cl"></div>	
		        <div class="others"><h2>Benzer Araçlar</h2></div>  
				<%}%>      
		        <div class="prod <%=product.isSingleShared() ? "snglshrd" : ""%>" typeof="v:Product">
		          <div class="pri">
					<%
					String imgUrl = ImageServlet.getImageUrl(request, product);
					if(searchResult.isShowImages() == 1){%>
			          <img src="<%=imgUrl%>" alt="Araç resmi" property="v:photo" content="<%=imgUrl%>" class="lazy"/>
		          	<%} %>
		          </div>
		          <div class="prinf">
		            <div class="h">
		            	<a target="_blank" href="<%=product.getLink()%>" rel="nofollow" title="Aracın sitedeki sayfasına git" 
				 		onclick="productClicked('<%=product.getLink()%>', '<%=product.getSourceName()%>', '<%=product.getProductName()%>', 2)">
				 		<%=product.getProductHighlightedName()%>
				 		</a>
				 		<span style="display:none" property="v:name"><%=product.getProductName()%></span>
		            </div>
		            <div class="ad">
		            	&nbsp;<span property="v:brand"><%=product.getBrand()%></span>
		             </div>
		             <div class="d lg lstupdt">Son güncelleme: <span title="Son güncelleme tarihi"><%=product.getLastFetchDate()%></span></div>
		            <div class="sr">
			            <span class="addthis_button" 
			            	addthis:url="<%=product.getShareLink()%>" addthis:title="<%=product.getShareName()%>" addthis:description="<%=product.getShareDesc()%>"
			            	addthis:email_vars="{img:'http://www.karniyarik.com<%=imgUrl%>',price:'<%=product.getPrice()%> TL',sourcesite:'<%=product.getSourceName()%>'}">
			      			<img width="88" height="17" class="sprite share-btn"  style="cursor: pointer;" alt="Paylaş" src="<%=request.getContextPath()%>/images/transparent.gif" title="Arkadaşlarınla paylaş"/>
			      		</span>&nbsp;
			      		<span>
							<img width="88" height="17" class="sprite samename-btn" style="cursor: pointer;" alt="ara" src="<%=request.getContextPath()%>/images/transparent.gif" title="Aynı isim ile ara" onclick="searchWithSameName('<%=product.getSameNamedQueryLink()%>');"/>
			      		</span>&nbsp;
						<a rel="nofollow v:url" title="Benzer Araçlar" href="<%=product.getShareLink()%>" class="c2">Benzer Araçlar</a>
						<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Benzer Araçlar"/>
					</div>
		          </div>
		          <div class="prpi">
		            <div class="pprice">
		  				<span property="v:price" title="Fiyatı"><%=product.getPrice()%> TL</span>
			            <span class="org-pri" title="Orjinal Fiyatı">
			            <%if(StringUtils.isNotBlank(product.getPriceAlternate())){ %>
			            (<%=product.getPriceAlternate()%> <%=product.getPriceCurrency()%>)
			            <%}%>
			            </span> 
		            </div>
		            <div class="psourc">
						<%=product.getSourceName()%>
		            </div>
		            <div class="stars">
		            </div>
		            <div>
		            	<a target="_blank" href="<%=product.getLink()%>" rel="nofollow" title="Aracın sitedeki sayfasına git" 
				 		onclick="productClicked('<%=product.getLink()%>', '<%=product.getSourceName()%>', '<%=product.getProductName()%>', 2)">
			            	<img class="sprite go-site-btn" src="<%=request.getContextPath()%>/images/transparent.gif" width="119" height="23" alt="Siteye git"/>
			            </a>              
		            </div>
		          </div>
		          <div class="deta">
		            <div class="dhea">Detay</div>
		            <div class="drow"><span class="c1">Şehir: </span>
		            <% if (!StringUtils.isEmpty(product.getProperty("city"))) { %>
		  			<%=product.getProperty("city") %>
		  			<% } %>
		            </div>
		            <div class="drow"><span class="c1">Yılı:</span>
		       		<% if (!StringUtils.isEmpty(product.getProperty("modelyear"))) { %>
		  			<%=product.getProperty("modelyear")%>
		  			<% } %>
		            </div>
		            <div class="drow"><span class="c1">Km'si: </span>
					<% if (!StringUtils.isEmpty(product.getProperty("km"))) { %>
			  		<%= product.getProperty("km") %> km
			  		<% } %>
		            </div>
		            <div class="drow"><span class="c1">Gücü: </span>
					<% if (!StringUtils.isEmpty(product.getProperty("enginepower"))) { %>
			  		<%= product.getProperty("enginepower") %> hp
			  		<% } %>
		            </div>
		          </div>
		        </div>
		        <%}%>
		        <div class="cl"></div>
		      </div>
		      <%if(searchResult.getPagerResults().size() > 0) {%>
		      <div class="nav">
		        <jsp:include page="../pager.jsp"></jsp:include>
		      </div>
		      <%} %>
			  <%if(searchResult.isShowFilters()) {%>
		      <div class="prop cfltr cfltrd" style="margin-bottom:10px">
		        <div class="c1" id="carFilterDiv">
				  <jsp:include page="filterother.jsp"></jsp:include>
		          <div class="cl"></div>
		          <jsp:include page="filterbrand.jsp"></jsp:include>
		          <jsp:include page="filtercity.jsp"></jsp:include>
		          <jsp:include page="filtergear.jsp"></jsp:include>
		          <jsp:include page="filterfuel.jsp"></jsp:include>
		          <jsp:include page="filtercolor.jsp"></jsp:include>
		          <!-- filterfuel.jsp-->
		          <div class="cl"></div>
		        </div>
		        <div style="width: 100%;text-align:right;display:inline-block;"><div class="filterDivButton" onclick="propContainerToggle('#carFilterDiv', '.filterDivButton','#cfltrb')">&nbsp;</div></div>
		      </div>
		      <%} %>
		      		
		      <div id="adblock2" class="gafs" style="max-height:240px;"></div>
			  
			  <%if(searchResult.isSimilar() && !searchResult.isShowURLNotFoundError() && !searchResult.isShowEmptySearchError()) { %>
			 	<div class="cl"></div> 
				<div>
					<script type="text/javascript"><!--
					google_ad_client = "ca-pub-5913641514503498";
					/* carSimilarHor */
					google_ad_slot = "8208964000";
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
			  <div style="margin-top:10px;float:left;width:876px;height:257px;" class="noprint">
				  <fb:like-box profile_id="90334878760" width="876"  height="255" connections="30" stream="false" 
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
		    <div class="radsh">
				<div class="radsh">
			    	<%if(searchResult != null && searchResult.getResults().size() > 0){ 
			    	String currentURL = request.getRequestURL().toString();
			    	if(StringUtils.isNotBlank(request.getQueryString()))
			    	{
			    		currentURL += "?" + request.getQueryString();
			    	}
			    	%>
			    	<div style="width:100%;text-align:right;padding-top:4px;">
			    		<font style="font-weight:bold;margin-right:4px;float:left;">Paylaş:</font>
						<div class="addthis_toolbox addthis_default_style" 
						style="float:right;width:60px;"
						addthis:url="<%=currentURL%>"
						addthis:title="<%=searchResult.getPageTitle()%>"
					    addthis:email_vars="{img:'<%=searchResult.getPageImgSrc()%>',description:'<%=StringEscapeUtils.escapeJavaScript(searchResult.getPageDescription())%>'}"
					    addthis:email_template="search_result">
							<a class="addthis_button_email"></a>
							<a class="addthis_button_facebook"></a>
							<a class="addthis_button_twitter"></a>
						</div>
					</div>
					<%}%>   
			    </div>    
		    </div>
		    <%if(searchResult.isShowFilters() || searchResult.isSimilar()) {%>
		    <%if(searchResult.getResults().size() > 0) {%>
		    <div class="rads b">
				<a target="_blank" href="http://www.etohum.com/" title="eTohum 15 İnternet Girişimi - 2010" rel="nofollow">
					<img class="sprite logo-etohum-15" src="<%=request.getContextPath()%>/images/transparent.gif" width="160" height="105" alt="eTohum 15 Logo"/>
				</a>
		     	<!-- img src="<%=request.getContextPath()%>/images/sehir-firsati/mail_block.png" onclick="javascript: prompt_email();" style="cursor:pointer;"/ -->
		    </div>
		    <div class="rads b">
				<script type="text/javascript"><!--
				google_ad_client = "ca-pub-5913641514503498";
				/* carCntSide1 */
				google_ad_slot = "1555002058";
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
		<jsp:include page="../maildialog.jsp"></jsp:include>
	</body>
</html>