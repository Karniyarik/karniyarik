<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.category.BaseCategoryUtil"%>
<%HttpCacheUtil.setStaticResponseCacheAttributes(response, request);
BaseCategoryUtil.setStaticPageBreadCrumb("Hakkımızda > Logo", "karniyarik-logo.jsp", request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" 
	xmlns="http://www.w3.org/1999/xhtml" 
 	xmlns:fb="http://www.facebook.com/2008/fbml"
 	xmlns:addthis="http://www.addthis.com/help/api-spec" 
	xmlns:og="http://opengraphprotocol.org/schema/" 
 	version="XHTML+RDFa 1.0" xml:lang="tr">
<head>
<title>Logolar | Karnıyarık - Alışverişi yardık!</title>
<meta name="description" content="Karnıyarık ürün arama motoru logoları ve görselleri" />
<meta name="keywords" content="Karniyarik.com, Logo, Görsel" />
<link rel="image_src" href="http://www.karniyarik.com/images/logo2.png" />
<meta property="og:image" content="http://www.karniyarik.com/images/logo2.png"/>
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
  <div class="sresult"><h1>Logolar</h1></div>
  <div class="cl"></div>
  <div class="l">
    <div class="filter full"></div>
    <div class="cl"></div>
    <div class="ll"></div>
    <div class="lc full">
      <div class="prop">
        <div class="c1 text">
        </div>
      </div>
    </div>
	<div class="content">
		<br/>
    	<p>Sitenizde ya da uygulamanızda kullanmak üzere aşağıdaki görsellerden faydalanabilirsiniz.</p>
    	<div style="width:450px;margin-right:30px;float:left;">
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/logo/karniyarik86x86.png" alt="Karniyarik Logo 86x86"/>
	    		<p>86x86</p>
    		</div>
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/logo/karniyarik88x31.png" alt="Karniyarik Logo 88x31"/>
	    		<p>88x31</p>
    		</div>
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/logo/karniyarik93x20.png" alt="Karniyarik Logo 93x20"/>
	    		<p>93x20</p>
    		</div>
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/logo/karniyarik303x69.png" alt="Karniyarik Logo 303x69"/>
	    		<p>303x69</p>
    		</div>
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/logo/karniyarik418x95.png" alt="Karniyarik Logo 418x95"/>
	    		<p>418x95</p>
    		</div>
    	</div>
    	<div style="width:450px;float:left;">
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/powered/pb_164x60.png" alt="Karniyarik Logo 164x60"/>
	    		<p>164x60</p>
			</div>
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/powered/pb_200x26.png" alt="Karniyarik Logo 200x26"/>
	    		<p>200x26</p>
			</div>
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/powered/pb_218x80.png" alt="Karniyarik Logo 218x80"/>
	    		<p>218x80</p>
			</div>
    		<div style="margin-bottom:30px;">
	    		<img src="<%=request.getContextPath()%>/images/powered/pb_300x40.png" alt="Karniyarik Logo 300x40"/>
	    		<p>300x40</p>
			</div>
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