<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="../error.jsp"%>
<%@page import="com.karniyarik.web.json.LinkedLabel"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.web.citydeals.CityResult"%>
<%@page import="com.karniyarik.web.citydeals.CityDealConverter"%>
<%@page import="com.karniyarik.web.citydeals.CityDealResult"%>
<%@page import="com.karniyarik.web.citydeals.IPGeoLookup"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%HttpCacheUtil.setResponseCacheAttributes(response, request, 2);%>
<%CityDealConverter result = new CityDealConverter(request);
List<CityResult> cities =  result.getCities();
List<CityDealResult> results = result.getCityDeals();
CityResult currentCity = result.getSelectedCity();%>
<html>
<head>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/pack.css?02102011"/>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/layout.css?02102011"/>
		<!--[if IE 6]><link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/ie6.css" /><![endif]-->
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/pack.js?02102011"></script>
<script type="text/javascript">
$(document).ready(function(){	
	$("#sehirfirsatislider").easySlider({
		controlsBefore:	'<div id="controls2" class="slider-btn">',
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
.sehirfirsatiwidget {text-align:center;background-color: #F2F2F2;border:2px solid #b2b2b2;padding:3px;}
.sehirfirsatiwidget .h a {font-size:16px; font-weight:bold;}
.sehirfirsatiwidget .h {margin-bottom:24px;font-size:11px; padding-top:4px;}
.sehirfirsatiwidget .h span {font-size:11px;text-align: left;}
.sehirfirsatiwidget .f {padding:4px;margin-top:22px;}
#sehirfirsatislider {border:2px solid #C18AB1; width: 146px; background:white;}
#sehirfirsatislider ul{width: 146px !important;}
#sehirfirsatislider li{background:white;width: 146px;height: 200px;}
#sehirfirsatislider .ppho {text-align: center;padding:4px;}
#sehirfirsatislider .ppho img {width: 136px; height:106px; border:1px solid gray;}
#sehirfirsatislider .pri {margin-left:3px;width: 140px;}
#sehirfirsatislider .pri .k {margin-bottom:4px;font-weight:bold;font-size:16px;line-height:20px;vertical-align: middle;background:#81AB00;color:white;padding:3px;}
#sehirfirsatislider .pri .k a {color:white;}
#sehirfirsatislider .pri .p {font-size:14px;font-weight:bold;line-height:20px;vertical-align: middle;}
#sehirfirsatislider .pri .p .item1 {float:left;width:55px;}
#sehirfirsatislider .pri .p .item2 {width: 55px;text-decoration:line-through;}
div#controls, div#controls2{margin:0;position:relative;} 
#prevBtn, #nextBtn{display:block;margin:0;overflow:hidden;text-indent:-8000px;width:40px;height:20px;position:absolute;left:58px;}											
#prevBtn{top:-222px;}
</style>
</head>
<body>
<div style="width:160px;height:390px;margin-left:auto;margin-right:auto;">
	<div class="sehirfirsatiwidget srdb">
		<div class="h">
			<a style="width:150px;display:block;" href="<%=request.getContextPath()%>/sehir-firsati" title="Şehir Fırsatı">Karnıyarık Şehir Fırsatları</a>
			<span>Şehrinize özel tüm fırsatları Karnıyarık aracılığı ile kolayca takip edebilirsiniz.</span> 
		</div>
		<div id="sehirfirsatislider" class="srdb">
			<ul>				
			<%int index =0;
			for(CityDealResult deal: results){
				if(index > 8) break;
				index++;
			%>
				<li>
					<div class="head">
					</div>
					<div class="ppho">
				        <a rel="nofollow" href="<%=deal.getShareURL()%>" title="Şehir fırsatının sayfasına git">
				          	<%StringBuffer imgBuff = new StringBuffer();
				        	  imgBuff.append(request.getContextPath());
				        	  imgBuff.append("/imgrsz/");
				        	  imgBuff.append(deal.getImageName());
				        	  imgBuff.append(".png");
				        	  imgBuff.append("?w=136&h=106&v=");
				        	  imgBuff.append(deal.getImage());
				        	  String imgUrl = imgBuff.toString();%>
					    	<img src="<%=imgBuff%>" height="106" width="136" alt="Şehir Fırsatı Resmi"/>
					    </a>					
					</div>
				    <div class="pri">
				    	<div class="k">
				    	<a rel="nofollow" href="<%=deal.getShareURL()%>" title="Şehir fırsatının sayfasına git">
				    		<%=deal.getDiscountPercentage()%> İndirim 
				    	</a>
				    	</div>
				    	<div class="p">
				    		<span class="item1"><%=deal.getDiscountPrice()%> <%=deal.getPriceCurrency()%></span>
				    		<span class="item2"><%=deal.getPrice()%> <%=deal.getPriceCurrency()%></span>
				    	</div>
				    </div>
				    <div class="time">
				    	<a rel="nofollow" href="<%=deal.getShareURL()%>" title="Şehir fırsatının sayfasına git"><%=deal.getRemainingTimeStr()%></a>
				    </div>
				</li>
			<%}%>
			</ul>
		</div>		
		<div class="f">
			<a href="<%=request.getContextPath()%>/sehir-firsati/" title="Şehir Fırsatı">Hepsini Göster</a>
		</div>		
	</div>
</div>
</body>
</html>