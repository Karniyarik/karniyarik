<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page errorPage="error.jsp"%>
<%@page import="com.karniyarik.common.site.FeaturedMerchantVO"%>
<%@page import="com.karniyarik.common.site.Payment"%>
<%@page import="com.karniyarik.common.site.Shipment"%>
<%@page import="com.karniyarik.common.site.Certificate"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.karniyarik.common.site.SiteRegistry"%>
<%@page import="com.karniyarik.web.util.HttpCacheUtil"%>
<%HttpCacheUtil.setNoCacheAttributes(response, request);%>
<html xmlns:v="http://rdf.data-vocabulary.org/#" xmlns="http://www.w3.org/1999/xhtml" xml:lang="tr" lang="tr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title></title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/reset.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/layout.css?2" />
</head>
<body class="ftrd">
<%FeaturedMerchantVO vo = SiteRegistry.getInstance().getFeaturedMerchant(request.getParameter("uid")); %>
<div class="ftrdmain">
	<div class="ftrdheader">
		<div style="display:inline"><%=vo.getName()%></div>
		<div class="ftrd_close"><a href="" onclick="parent.$.fancybox.close();">Kapat</a></div>
	</div>
	<div class="ftrdwindow">
		<div class="ftrdcont">
			<div class="ftrd_col ftrd_sec">
				<img src="<%=vo.getLogo()%>"/>
			</div>

			<div class="ftrd_col ftrd_sec">
				<div class="ftrd_sec_head">Kargo ve Ödeme</div>
				<div>
					<ul class="ftrd_sec_list ship_pay">
					<%for(Payment payment: vo.getPayment()){ %>
					<li><img src="<%=payment.getImage()%>" title="<%=payment.getName()%>"/></li>
					<%} %>
					</ul>
				</div>				
				<div>
					<ul class="ftrd_sec_list ship_pay">
					<%for(Shipment shipment: vo.getShipment()){ %>
					<li><img src="<%=shipment.getImage()%>" title="<%=shipment.getName()%>"/></li>
					<%} %>
					</ul>
				</div>
			</div>
			<div style="display:block; clear:both;"></div>
			<div class="ftrd_col ftrd_sec">
				<div class="ftrd_sec_head">İletişim Bilgileri</div>
				<ul class="ftrd_sec_list ftrd_img_c">
					<%if(StringUtils.isNotBlank(vo.getContact().getPhone()) && !vo.getContact().getPhone().equals("null")){ %>
					<li><img src="<%=request.getContextPath()%>/featured/images/phone.gif" alt="Telefon" title="Telefon"/><%=vo.getContact().getPhone()%></li>
					<%}%>
					<%if(StringUtils.isNotBlank(vo.getContact().getFax()) && !vo.getContact().getFax().equals("null")){ %>
					<li><img src="<%=request.getContextPath()%>/featured/images/fax.gif" alt="Fax" title="Fax"/><%=vo.getContact().getFax()%></li>
					<%}%>
					<%if(StringUtils.isNotBlank(vo.getContact().getEmail()) && !vo.getContact().getEmail().equals("null")){ %>
					<li>Email:<a href="mailto:<%=vo.getContact().getEmail()%>"><%=vo.getContact().getEmail()%></a></li>
					<%}%>
					<%if(StringUtils.isNotBlank(vo.getContact().getUrl()) && !vo.getContact().getUrl().equals("null")){ %>
					<li><a target="_blank" href="<%=vo.getContact().getUrl()%>">Web Sayfası</a></li>
					<%}%>
					<%if(StringUtils.isNotBlank(vo.getContact().getContactus()) && !vo.getContact().getContactus().equals("null")){ %>
					<li><a target="_blank" href="<%=vo.getContact().getContactus()%>">İletişim Sayfası</a></li>
					<%}%>					
				</ul>		
			</div>
			<div class="ftrd_col ftrd_sec">
				<div class="ftrd_sec_head">Hakkında</div>
				<ul class="ftrd_sec_list">
					<%if(StringUtils.isNotBlank(vo.getContact().getCompany()) && !vo.getContact().getCompany().equals("null")){ %>
					<li>Şirket:&nbsp;<%=vo.getContact().getCompany()%></li>
					<%} %>
					<%if(StringUtils.isNotBlank(vo.getContact().getAddress()) && !vo.getContact().getAddress().equals("null")){ %>
					<li>Adres:&nbsp;<%=vo.getContact().getAddress()%></li>
					<%} %>
					<%if(StringUtils.isNotBlank(vo.getAboutus()) && !vo.getAboutus().equals("null")){ %>
					<li><a target="_blank" href="<%=vo.getAboutus()%>">Hakkımızda Sayfası</a></li>
					<%} %>
				</ul>		
			</div>			
			<div style="display:block; clear:both;"></div>
			<div class="ftrd_col ftrd_sec">
				<div class="ftrd_sec_head">Takip Edin</div>
				<ul class="ftrd_sec_list ftrd_img_c">
					<%if(StringUtils.isNotBlank(vo.getFacebook()) && !vo.getFacebook().equals("null")){ %>
					<li><img src="<%=request.getContextPath()%>/featured/images/follow/fb.gif"/><a target="_blank" href="<%=vo.getFacebook()%>">Facebook sayfası</a></li>
					<%} %>
					<%if(StringUtils.isNotBlank(vo.getTwitter()) && !vo.getTwitter().equals("null")){ %>
					<li><img src="<%=request.getContextPath()%>/featured/images/follow/tw.gif"/><a target="_blank" href="<%=vo.getTwitter()%>">Twitter</a></li>
					<%} %>
				</ul>
			</div>
			<div class="ftrd_col ftrd_sec">
				<div class="ftrd_sec_head">Hizmet Bilgileri</div>
				<div>
					<%for(String name: vo.getLinks().keySet()){ %>
					<span><a target="_blank" href="<%=vo.getLinks().get(name)%>"><%=name%></a></span>,
					<%} %>
				</div>
			</div>
			<div style="display:block; clear:both;"></div>
			<div class="ftrd_sec">
				<div class="ftrd_sec_head">Sertifikalar</div>
				<ul class="ftrd_sec_list ship_pay">
					<%for(Certificate certificate: vo.getCertificates()){ %>
					<li><img src="<%=certificate.getImage()%>" title="<%=certificate.getName()%>"/></li>
					<%} %>
				</ul>
			</div>
			<div class="ftrd_sec">
				<div class="ftrd_sec_head">Açıklama</div>
				<div>
					<%if(StringUtils.isNotBlank(vo.getContact().getNotes())){ %>
					<%=vo.getContact().getNotes()%>
					<%} %>
				</div>
			</div>
			
		</div>
	</div>
</div>
</body>
</html>