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
%><%@page import="com.karniyarik.web.citydeals.CityResult"
%><%@page import="com.karniyarik.web.citydeals.CityDealConverter"
%><%@page import="com.karniyarik.web.citydeals.CityDealResult"
%><%@page import="com.karniyarik.web.citydeals.IPGeoLookup"
%><%@page import="java.net.URLEncoder"
%><%@page import="com.karniyarik.web.util.HttpCacheUtil"
%><%@page import="com.karniyarik.web.category.BaseCategoryUtil"
%><%@page import="com.karniyarik.web.servlet.image.ImageServlet"
%><%@page import="java.util.ArrayList"
%><%
HttpCacheUtil.setNoCacheAttributes(response, request);
CityDealConverter result = new CityDealConverter(request);
List<CityResult> cities =  result.getCities();
List<CityResult> activeCities =  result.getActiveCities();
List<CityDealResult> results = result.getCityDeals();
CityResult currentCity = result.getSelectedCity();
CityDealResult sharedDeal = result.getSharedCityDeal();
BaseCategoryUtil.setBreadCrumb(request, result.getBreadcrumb());
String title = CityDealConverter.getTitle(sharedDeal, currentCity.getName(), result.getSource());
String description = CityDealConverter.getDescription(sharedDeal, results, currentCity.getName(), result.getSource());
String pageImageSrc = CityDealConverter.getPageImageSrc(results, sharedDeal);
boolean oldDealsStarted = false;
request.setAttribute(RequestWrapper.GA_TRCK_TYPE_VALUE, 6);
%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" 
	xmlns="http://www.w3.org/1999/xhtml" 
	version="XHTML+RDFa 1.0" 
	xmlns:addthis="http://www.addthis.com/help/api-spec" 
	xml:lang="tr"
	xmlns:fb="http://www.facebook.com/2008/fbml"
	xmlns:og="http://opengraphprotocol.org/schema/">
	<head>
		<title><%=title%></title>
		<meta name="description" content="<%=description%>" />
		<meta name="keywords" content="şehir fırsatları, şehir fırsatı, <%=currentCity.getName()%>" />
		<meta property="og:title" content="<%=title%>"/>
		<meta property="fb:type" content="product"/>
		<meta property="og:description" content="<%=description%>"/>
		<link rel="image_src" href="<%=pageImageSrc%>" />
		<meta property="og:image" content="<%=pageImageSrc%>"/>
		<jsp:include page="../commonmeta.jsp" />
		<jsp:include page="../commoninclude.jsp" />
		<link rel="alternate" type="application/rss+xml" title="RSS" href="<%=request.getContextPath()%>/sehir-firsati/rss/<%=currentCity.getValue()%>.rss" />
	</head>
	<body>
		<div class="ce ar tmh2 ">
			<jsp:include page="../nav.jsp"></jsp:include>
		</div>
		
		<div class="ce citydeal">
		  <form class="form2" id="frmSearch" method="get" action="<%=request.getContextPath()%>/urun/search.jsp" onsubmit="if($('#stab').val()=='1')this.action='<%=request.getContextPath()%>/urun/search.jsp'; submitForm()">
		 	 <jsp:include page="../commontop.jsp"></jsp:include>
		  </form><%
		  if(true){ //searchResult.isShowToolbar()){ 
		  %><div class="sresult">
		    <div class="rtime c3">
		    </div><%
		    if(sharedDeal == null){
		    %><h1><%=currentCity.getName()%> Şehir Fırsatı Listesi</h1><%
		    } else { 
		    %><h1>Şehir Fırsatı: <%=CityDealConverter.getTitle(sharedDeal)%></h1><%
		    } 
		%></div>
		  <div class="cl"></div><%
		  }
		  %><div class="l">
		  	<form class="formcity" id="frmCity" method="get" action="<%=request.getContextPath()%>/sehir-firsati">  
			    <div class="filter">
			      <div class="fr c4 sopi">
			        <span class="f2">Şehir Seç:</span>
			        <select name="city" class="fl" title="Şehir Değiştir" onchange="$('#frmCity').submit();">
			        <%for(CityResult city : activeCities){%>
			          <option title="<%=city.getName()%>" value="<%=city.getValue()%>" <%=city.isSelected() ? "selected='selected'" : ""%>><%=city.getName()%> (<%=city.getDealCount()%>)</option>
			          <%} %>
			        </select>
			      </div>
			      <div class="h b">&nbsp;</div>
			      <div class="t b"></div>
			      <div class="si" title="Fırsatları indirim yüzdesine göre sırala">
		      		<a href="<%=request.getContextPath()%>/sehir-firsati/?city=<%=currentCity.getValue()%>&sort=<%=CityDealConverter.SORT_DPERCENTAGE%>" rel="nofollow">
		      			<img class="sprite sort-ud-1" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="sırala" />
		      			İndirim Yüzdesi
		      		</a>
		      	  </div>
		      	  <div class="si" title="Fırsatları fiyata göre sırala">
		      		<a href="<%=request.getContextPath()%>/sehir-firsati/?city=<%=currentCity.getValue()%>&sort=<%=CityDealConverter.SORT_PRICE%>" rel="nofollow">
		      			<img class="sprite sort-ud-1" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="sırala" />
		      			Fiyat
		      		</a>
		      	  </div>
		      	  <div class="si" title="Fırsatları tarihe göre sırala">
		      		<a href="<%=request.getContextPath()%>/sehir-firsati/?city=<%=currentCity.getValue()%>&sort=<%=CityDealConverter.SORT_DATE%>" rel="nofollow">
		      			<img class="sprite sort-ud-1" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="sırala" />
		      			Tarih
		      		</a>
		      	  </div>
		      	  <div class="si" title="Fırsatları indirim miktarına göre sırala">
		      		<a href="<%=request.getContextPath()%>/sehir-firsati/?city=<%=currentCity.getValue()%>&sort=<%=CityDealConverter.SORT_DAMOUNT%>" rel="nofollow">
		      			<img class="sprite sort-ud-1" src="<%=request.getContextPath()%>/images/transparent.gif" width="19" height="13" alt="sırala" />
		      			İndirim Miktarı
		      		</a>
		      	  </div>      	  
		    	</div>
		    </form>
		    <div class="cl"></div>
			
		    <!-- div class="lc" style="width:874px" -->
		    <div class="lc car">
		    	<%if(results.size() < 1){%>
				<div class="prop">
				  <div class="c1"> <%=currentCity.getName()%> şehirinde kayıtlı bir şehir fırsatı şu an bulunmamaktadır. 
				  </div>
				</div><%} else {}
		    	if(result.getPages().size() > 0){ 
		    	%><div class="nav toppg">
					<div class="navh"><%
						List<LinkedLabel> labels = result.getPages();
						for(LinkedLabel linkedLabel: labels){%>
						<a <%=StringUtils.isNotBlank(linkedLabel.getCssClass()) ? "class='" + linkedLabel.getCssClass() + "'" : ""%> href="<%=linkedLabel.getLink()%>">
							<%=linkedLabel.getLabel()%>
						</a><%
						}
					%></div>
			  	</div><%
			  	} 
			  %><div class="prods">
		        <div class="cl"></div>
		        <%if(sharedDeal != null)
		        {%>
		        <div class="prod snglshrd sh_ct">
		          <div class="pri">
			        <a target="_blank" rel="nofollow" href="<%=sharedDeal.getProductURL()%>" title="Şehir fırsatının sayfasına git"
			        	onclick="sendClickToAdsense('<%=sharedDeal.getProductURL()%>', '<%=sharedDeal.getSource()%>', '', 4)">
		          	<%String imgUrl = ImageServlet.getImageRszUrl(request, sharedDeal.getImage(), sharedDeal.getImageName(), 180, 140);%>
				    	<img class="primg lazy" src="<%=imgUrl%>" height="140" width="180" alt="Şehir Fırsatı Resmi"/>
				    </a>
			       	<a target="_blank" rel="nofollow" href="<%=sharedDeal.getProductURL()%>" title="Şehir fırsatının sayfasına git" 
			 			onclick="sendClickToAdsense('<%=sharedDeal.getProductURL()%>', '<%=sharedDeal.getSource()%>', '', 4)">
		            	<img class="sprite go-city-site-btn go" src="<%=request.getContextPath()%>/images/transparent.gif" width="180" height="40" alt="Şehir fırsatı detayı"/>
		            </a>            
		          </div>
		          <div class="prinf">
		            <div>
						<div class="price_bar">
		                     <div class="first item"><div>Fiyat<br><span class="bigger"><%=sharedDeal.getDiscountPrice()%> <%=sharedDeal.getPriceCurrency()%></span></div></div>
		                     <div class="secnd item"><div>Eski Fiyat<br><span class="bigger"><%=sharedDeal.getPrice()%> <%=sharedDeal.getPriceCurrency()%></span></div></div>                                                                                                                                                                                                                
		                     <div class="third item"><div>Kazanç<br><span class="bigger"><%=sharedDeal.getDiscountPercentage()%></span></div></div>
		                </div>
		                <div class="share">
		      	            <span class="addthis_button" 
			            		addthis:url="<%=sharedDeal.getShareURL()%>" addthis:title="<%=sharedDeal.getShareName()%>" addthis:description="<%=sharedDeal.getShareDesc()%>"
			            		addthis:email_vars="{img:'http://www.karniyarik.com<%=imgUrl%>',price:'<%=sharedDeal.getDiscountAmount()%> TL',sourcesite:'<%=sharedDeal.getSource()%>'}">
				      			<img width="113" height="40" class="sprite share-btn-2"  style="cursor: pointer;" alt="Paylaş" src="<%=request.getContextPath()%>/images/transparent.gif" title="Arkadaşlarınla paylaş"/>
				      		</span>
		                </div>             
		            </div>
		            <div class="cl"></div>
					<div class="ad desc">
		            	<%=LinkedLabel.getShortenedLabel(sharedDeal.getDescription(), 300)%>
		            	<a rel="nofollow" target="_blank" title="Devamı" href="<%=sharedDeal.getProductURL()%>" class="c2" 
		            		onclick="sendClickToAdsense('<%=sharedDeal.getProductURL()%>', '<%=sharedDeal.getSource()%>', '', 4)">Devamı</a>
		              	<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Şehir Fırsatı Detayı"/>
		             </div>
		            <div class="sr">
					  <!-- iframe src="http://www.facebook.com/plugins/like.php?href=<%=URLEncoder.encode(sharedDeal.getProductURL(), StringUtil.DEFAULT_ENCODING)%>&amp;layout=button_count&amp;show_faces=false&amp;width=200&amp;action=recommend&amp;colorscheme=light&amp;height=21&amp;locale=tr_TR" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:200px; height:21px;" allowTransparency="true"></iframe -->
					</div>
		          </div>
		          <div class="prpi">
				    <a target="_blank" rel="nofollow" href="<%=sharedDeal.getProductURL()%>" title="Şehir fırsatının sayfasına git"
				    	onclick="sendClickToAdsense('<%=sharedDeal.getProductURL()%>', '<%=sharedDeal.getSource()%>', '', 4)">
			      		<img src="<%=request.getContextPath() + sharedDeal.getSourceImage()%>140.png" alt="<%=sharedDeal.getSource()%> Logosu"/>
					</a>
					<div class="cdwn">
						<div class="headr">Geri Sayım</div>
						<div class="time"><%=sharedDeal.getRemainingTimeStr()%></div>
						<span style="display:none"><%=sharedDeal.getRemainingTime()%></span>
					</div>
		          </div>
		        </div>
		        <div class="cl"></div>	
		        <div class="others"><h2><%=currentCity.getName()%> Şehirindeki Tüm Fırsatlar</h2></div>
		        <%} %>
		        <%for(int dealIndex = 0; dealIndex < results.size(); dealIndex++){
		        	 CityDealResult deal = results.get(dealIndex);%>
		        <div class="prod">
		          <div class="pri">
			        <a rel="nofollow" target="_blank" href="<%=deal.getProductURL()%>" title="Şehir fırsatının sayfasına git"
			        onclick="sendClickToAdsense('<%=deal.getProductURL()%>', '<%=deal.getSource()%>', '', 4)">
		          	<%String imgUrl = ImageServlet.getImageRszUrl(request, deal.getImage(), deal.getImageName(), 180, 140);%>
				    	<img class="primg lazy" src="<%=imgUrl%>" height="140" width="180" alt="Şehir Fırsatı Resmi"/>
				    </a>
				    <%if(deal.getRemainingTime() > 0) {%>
			       	<a rel="nofollow" target="_blank" href="<%=deal.getProductURL()%>" rel="nofollow" title="Şehir fırsatının sayfasına git" 
			 			onclick="sendClickToAdsense('<%=deal.getProductURL()%>', '<%=deal.getSource()%>', '', 4)">
		            	<img class="sprite go-city-site-btn go" src="<%=request.getContextPath()%>/images/transparent.gif" width="180" height="40" alt="Şehir fırsatı detayı"/>
		            </a>
		            <%} %>
		          </div>
		          <div class="prinf">
		            <div>
						<div class="price_bar">
		                     <div class="first item"><div>Fiyat<br><span class="bigger"><%=deal.getDiscountPrice()%> <%=deal.getPriceCurrency()%></span></div></div>
		                     <div class="secnd item"><div>Eski Fiyat<br><span class="bigger"><%=deal.getPrice()%> <%=deal.getPriceCurrency()%></span></div></div>                                                                                                                                                                                                                
		                     <div class="third item"><div>Kazanç<br><span class="bigger"><%=deal.getDiscountPercentage()%></span></div></div>
		                </div>
		                <div class="share">
		                
		      	            <span class="addthis_button" 
			            		addthis:url="<%=deal.getShareURL()%>" addthis:title="<%=deal.getShareName()%>" addthis:description="<%=deal.getShareDesc()%>"
			            		addthis:email_vars="{img:'http://www.karniyarik.com<%=imgUrl%>',price:'<%=deal.getDiscountPrice()%> TL',sourcesite:'<%=deal.getSource()%>'}">
				      			<img width="113" height="40" class="sprite share-btn-2"  style="cursor: pointer;" alt="Paylaş" src="<%=request.getContextPath()%>/images/transparent.gif" title="Arkadaşlarınla paylaş"/>
				      		</span>
		                </div>             
		            </div>
		            <div class="cl"></div>
					<div class="ad desc">
		            	<%=LinkedLabel.getShortenedLabel(deal.getDescription(), 300)%>
		            	<a rel="nofollow" target="_blank" title="Devamı" href="<%=deal.getProductURL()%>" class="c2" 
		            		onclick="sendClickToAdsense('<%=deal.getProductURL()%>', '<%=deal.getSource()%>', '', 4)">Şehir Fırsatı Devamı</a>
		              	<img width="14" height="13" class="sprite more-btn" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Şehir Fırsatı Detayı"/>
		             </div>
		            <div class="sr">
						<!-- iframe src="http://www.facebook.com/plugins/like.php?href=<%=URLEncoder.encode(deal.getShareURL(), StringUtil.DEFAULT_ENCODING)%>&amp;layout=button_count&amp;show_faces=false&amp;width=200&amp;action=recommend&amp;colorscheme=light&amp;height=21&amp;locale=tr_TR" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:200px; height:21px;" allowTransparency="true"></iframe -->           
					</div>
		          </div>
		          <div class="prpi">
				    <a rel="nofollow" target="_blank" href="<%=deal.getProductURL()%>" title="Şehir fırsatının sayfasına git"
				    	onclick="sendClickToAdsense('<%=deal.getProductURL()%>', '<%=deal.getSource()%>', '', 4)">
			      		<img src="<%=request.getContextPath() + deal.getSourceImage()%>140.png" alt="<%=deal.getSource()%> Logosu"/>
					</a>
					<div class="cdwn">
						<div class="headr">Geri Sayım</div>
						<div class="time"><%=deal.getRemainingTimeStr()%></div>
						<span style="display:none"><%=deal.getRemainingTime()%></span>
					</div>
		          </div>
		        </div>
		        <%
		        boolean addCities = false;
		        boolean addExpiredTitle = false;
		        
		        if(dealIndex < (results.size()-1)){
		        	deal = results.get(dealIndex+1);
		        	if(deal.getRemainingTime() <= 0 && oldDealsStarted == false){
		        		addCities = true;
		        		addExpiredTitle = true;
		        	}
		        } else if(oldDealsStarted == false){
		        	addCities = true;
		        }
		        %>
		        <%if(addCities) {%>
		       	<%} %>
		       	<%if(addExpiredTitle) {oldDealsStarted = true;%>
		       	<div class="cl"></div>	
		        <div class="others"><h2>Zamanı Geçen Fırsatlar</h2></div>
		        <div class="cl"></div>
		        <div>
		       	<%}%>
		        <%}%>
		        <%if(oldDealsStarted) {%>
		        </div>
		        <%} %>
		        <div class="cl"></div>
		      </div>
		      <%if(result.getPages().size() > 0){ %>
		      <div class="nav">
					<div class="navh">
					<%
						List<LinkedLabel> labels = result.getPages();
						for(LinkedLabel linkedLabel: labels){%>
						<a <%=StringUtils.isNotBlank(linkedLabel.getCssClass()) ? "class='" + linkedLabel.getCssClass() + "'" : ""%> href="<%=linkedLabel.getLink()%>"><%=linkedLabel.getLabel()%></a>
					<%}%>
					</div>
			  	</div>
			  	<%} %>
		      <div class="cl"></div>
		      <div class="noprint">
				<div style="margin-top:20px;" class="prop">
					<div class="cities" id="cities">
							<h2>Tüm Şehirler</h2>
						   	<%
						   	int index = 0;
						   	int size = 11;
						   	for(CityResult city: activeCities){%>
						   		<%if(index%size == 0){%><ul><%} index++;%>
							<li>
								<a href="<%=request.getContextPath() + "/" + city.getUrl()%>" title="<%=city.getName()%> Şehir Fırsatı Listesi"><%=city.getName()%></a>
								<span title="Bu şehirde toplam <%=city.getDealCount()%> fırsat bulunmaktadır.">(<%=city.getDealCount()%>)</span>
							</li>
							<%if(index%size == 0){%></ul><%}%>
						   	<%}%>
					   	</div>
				   </div> 
				</div>
		       	<div class="cl"></div>
		       	<div class="prop">
				  <div class="c1"> Şehir Fırsatı Siteleri: 
				    <div class="props sources" >
				      <div class="cl lower"></div>
				      <%for(String sources: result.getSources()) {%>
				      <h2>
				      <a href='<%=request.getContextPath() + "/" + currentCity.getUrl() + "/" + sources%>' title="<%=sources%> Şehir Fırsatı fırsatlari"><%=sources%></a>
				      </h2>
				      <%}%>
				      <div class="cl"></div>
				    </div>
				  </div>
				  <div class="cl"></div>
				</div>	
		      </div>
		    </div>
		
		  <div class="r">
		    <div class="radsh">
				<div class="radsh">
			    	<% 
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
						addthis:title="<%=title%>" 
					    addthis:email_vars="{img:'<%=pageImageSrc%>',description:'<%=StringEscapeUtils.escapeJavaScript(description)%>'}"
					    addthis:email_template="search_result">
							<a class="addthis_button_email"></a>
							<a class="addthis_button_facebook"></a>
							<a class="addthis_button_twitter"></a>
						</div>
					</div>
			    </div>    
		    <%//if(currentCity.isSelected()) {%>
		    <!-- a href="http://feeds.feedburner.com/karniyarik/sehirfirsatlari/rss/<%=currentCity.getValue()%>">
		    	<img src="<%=request.getContextPath()%>/images/sehir-firsati/rss8.png" title="RSS ile Takip Et" alt="RSS"/>
		    </a -->
		    <%//} %>
		    </div>
		    <div class="rads b">
				<a href="http://www.etohum.com/" title="eTohum 15 İnternet Girişimi - 2010" rel="nofollow">
					<img class="sprite logo-etohum-15" src="<%=request.getContextPath()%>/images/transparent.gif" width="160" height="105" alt="eTohum 15 Logo"/>
				</a>
		    	<!-- img src="<%=request.getContextPath()%>/images/sehir-firsati/mail_block.png" onclick="javascript: prompt_email();" style="cursor:pointer;"/ -->
		    <%//} %>
		    </div>
		    <%if(results.size() > 2) {%>
			<div class="rads b">
				<script type="text/javascript"><!--
				google_ad_client = "ca-pub-5913641514503498";
				/* cityDealSideTop1 */
				google_ad_slot = "0196519855";
				google_ad_width = 160;
				google_ad_height = 600;
				//-->
				</script>
				<script type="text/javascript"
				src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
				</script>
		    </div>    
			<%} %>
		    <%if(results.size() > 4 || results.size() < 3) {%>
		    <div style="margin-top:20px;">
			  <fb:like-box profile_id="90334878760" width="158"  height="400" connections="6" stream="false" 
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
		    <%} %>
		    <%if(results.size() > 7) {%>
		    <div class="rads b">
			    <iframe scrolling="no" width="162" height="405" frameborder="0" marginheight="0" marginwidth="0" src ="<%=request.getContextPath()%>/firsat/widget_y.jsp" style="overflow:hidden;border:none;left:0pt;top: 0pt;">
		  			<p>Karnıyarık Tek Ürün Fırsatları</p>
				</iframe>    
		    </div>
		    <%} %>
			<%if(results.size() > 9) {%>
			<div class="rads b">
			<script type="text/javascript"><!--
			google_ad_client = "ca-pub-5913641514503498";
			/* cityDealSideBottom1 */
			google_ad_slot = "4971260081";
			google_ad_width = 160;
			google_ad_height = 600;
			//-->
			</script>
			<script type="text/javascript"
			src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
			</script>
			</div>	   
		   	<%} %>
		  </div>
		  <div class="cl">&nbsp;</div>
		  
		<div class="cl" style="height:20px"></div>		
		<div ></div>
		<jsp:include page="../commonfooter.jsp"></jsp:include>
		<jsp:include page="../maildialog.jsp"></jsp:include>
	</body>
</html>