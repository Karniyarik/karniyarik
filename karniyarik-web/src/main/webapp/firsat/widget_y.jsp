<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="../../error.jsp"%>
<%@page import="com.karniyarik.web.bendeistiyorum.DailyOpportunityLoader"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.karniyarik.common.vo.Product"%>
<%@page import="com.karniyarik.web.util.Formatter"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="com.karniyarik.web.json.SearchResult"%>
<%@page import="com.karniyarik.web.util.SearchResultRequestWrapper"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%@page import="com.karniyarik.web.json.ProductResult"%>
<%HttpCacheUtil.setResponseCacheAttributes(response, request, 2);int size = 3;%>

<html>
<head>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/pack.css?02102011"/>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/layout.css?02102011"/>
		<!--[if IE 6]><link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/ie6.css" /><![endif]-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/pack.js?02102011"></script>
<script type="text/javascript">
$(document).ready(function(){	
	$("#urunfirsat").easySlider({
		controlsBefore:	'<div id="controls1" class="slider-btn">',
		controlsAfter:	'</div>',		
		prevId: 'prevBtn',
		nextId: 'nextBtn',
		auto: true,	
		pause: 4000,	
		speed: 800,
		vertical: true,
		continuous: true
	});			
});	
</script>
<style type="text/css">
.srdb {-moz-border-radius:5px; -moz-background-clip:border; -moz-background-inline-policy:continuous; -moz-background-origin:padding; -webkit-border-radius: 5px; border-radius: 5px;}
.urunfirsatwidget {text-align:center;background-color: #F2F2F2;border:2px solid #b2b2b2;padding:3px;}
.urunfirsatwidget .h a {font-size:16px; font-weight:bold;}
.urunfirsatwidget .h {margin-bottom:24px;font-size:11px; padding-top:4px;}
.urunfirsatwidget .h span {font-size:11px;text-align: left;}
.urunfirsatwidget .f {padding:4px;margin-top:22px;}
#urunfirsat {border:2px solid #C18AB1; width: 146px; background:white;}
#urunfirsat ul{width: 146px !important;}
#urunfirsat li{background:white;width: 146px;height: 200px;}
#urunfirsat .ppho {text-align: center;padding:4px;}
#urunfirsat .ppho img {width: 136px; height:106px; border:1px solid gray;}
#urunfirsat .pri {margin-left:3px;width: 140px;}
#urunfirsat .pri .k {margin-bottom:4px;font-weight:bold;font-size:16px;line-height:20px;vertical-align: middle;background:#81AB00;color:white;padding:3px;}
#urunfirsat .pri .p {font-size:11px;font-weight:bold;vertical-align: middle;height:55px;overflow:hidden;}
#urunfirsat .pri .p .item1 {float:left;width:140px;}
div#controls1 {margin:0;position:relative;} 
#controls1 #prevBtn, #controls1 #nextBtn{display:block;margin:0;overflow:hidden;text-indent:-8000px;width:40px;height:20px;position:absolute;left:58px;}											
#controls1 #prevBtn{top:-222px;}
</style>
</head>
<body>
<div style="width:160px;height:400px;margin-left:auto;margin-right:auto;">
	<div class="urunfirsatwidget srdb">
		<div class="h">
			<a href="<%=request.getContextPath()%>/firsat">Karnıyarık Tek Ürün Fırsatları</a><br/>
			<span>Her gün tek ürün satan sitelerdeki çok ucuz fırsat ürünlerini Karnıyarık aracılığı ile takip edebilirsiniz.</span> 
		</div>
		<div id="urunfirsat" class="srdb">
			<ul>				
			<%List<ProductResult> products = DailyOpportunityLoader.getInstance().getRandomProducts(size);
			int index =0;
			String url = request.getContextPath() + "/firsat/";
			for(ProductResult product: products){
				if(index > 8) break;
				index++;
			%>
				<li>
					<div class="head">
					</div>
					<div class="ppho">
				        <a rel="nofollow" href="<%=url%>" title="Ürün fırsatının sayfasına git">
				          	<%StringBuffer imgBuff = new StringBuffer();
				        	  imgBuff.append(request.getContextPath());
				        	  imgBuff.append("/imgrsz/");
				        	  imgBuff.append(product.getImageName());
				        	  imgBuff.append(".png");
				        	  imgBuff.append("?w=136&h=106&v=");
				        	  imgBuff.append(product.getImageURL());
				        	  String imgUrl = imgBuff.toString();%>
					    	<img src="<%=imgBuff%>" height="106" width="136" alt="Ürün Fırsatı Resmi"/>
					    </a>					
					</div>
				    <div class="pri">
				    	<div class="k"><%=product.getPrice()%> <%=product.getPriceCurrency()%></div>
				    	<div class="p">
				    		<span class="item1">
				    			<a rel="nofollow" class="purple" href="<%=url%>">
				    			<%=LinkedLabel.getShortenedLabel(product.getProductName(), 100)%>
				    			</a>
				    		</span>
				    	</div>
				    </div>
				    <div class="time">
				    </div>
				</li>
			<%}%>
			</ul>
		</div>
		<div class="f">
			<a href="<%=request.getContextPath()%>/firsat/">Hepsini Göster</a>
		</div>		
	</div>
</div>
</body>
</html>
