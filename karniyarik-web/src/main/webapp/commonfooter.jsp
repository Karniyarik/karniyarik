<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
%><%@page import="java.util.Calendar"%>
		<div class="socials" style="margin:auto">
			<div class="btns">
				<a rel="nofollow" target="_blank" href="http://www.facebook.com/pages/Karniyarik/90334878760" title="Facebook'da Karnıyarık"><img class="sprite socials-fb-32" width="30" height="30" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Facebook'da Karnıyarık"/></a>
				<a rel="nofollow" target="_blank" href="http://twitter.com/karniyarik" title="Twitter'da Karnıyarık"><img class="sprite socials-t-32" width="30" height="30" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Twitter'da Karnıyarık"/></a>
				<a rel="nofollow" target="_blank" href="http://friendfeed.com/karniyarik" title="Friendfeed'de Karnıyarık"><img class="sprite socials-ff-32" width="30" height="30" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Friendfeed'de Karnıyarık"/></a>
				<a rel="nofollow" target="_blank" href="http://www.linkedin.com/companies/karniyarik" title="LinkedIn'de Karnıyarık"><img class="sprite socials-li-32" width="30" height="30" src="<%=request.getContextPath()%>/images/transparent.gif" alt="LinkedIn'de Karnıyarık"/></a>
				<a rel="nofollow" target="_blank" href="http://www.stumbleupon.com/url/www.karniyarik.com/" title="Stumbleupon'da Karnıyarık"><img class="sprite socials-su-32" width="30" height="30" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Stumbleupon'da Karnıyarık"/></a>
				<a rel="nofollow" target="_blank" href="http://digg.com/software/Startup_Online_Good_Search_in_Turkey" title="Digg'de Karnıyarık"><img class="sprite socials-dg-32" width="30" height="30" src="<%=request.getContextPath()%>/images/transparent.gif" alt="Digg'de Karnıyarık"/></a>
			</div>
		Takip Et: </div>
		
		<div class="copy c1"> 
		 Karnıyarık Ltd. © <%=Calendar.getInstance().get(Calendar.YEAR)%>
		 | <a rel="nofollow" class="tb" title="Karniyarik.com hakkında bilgi almak için tıklayın" href="<%=request.getContextPath()%>/aboutus.jsp">Hakkımızda</a>
		 | <a rel="nofollow" class="tb" title="Bilgi topladığımız internet sitelerini görmek için tıklayın" href="<%=request.getContextPath()%>/sites.jsp">Bilgi Toplanan Siteler</a>
		 | <a class="tb purple bold" title="Karniyarik Kurumsal" href="<%=request.getContextPath()%>/kurumsal">Mağaza Girişi (Yeni!)</a>
		 | <a rel="nofollow" class="tb" title="Karniyarik.com'a sitenizi eklemek için tıklayın" href="<%=request.getContextPath()%>/addsite.jsp">Sitenizi Ekleyin</a>
		 | <a rel="nofollow" class="tb" href="<%=request.getContextPath()%>/aboutus.jsp#legal" title="Yasal Bilgi">Yasal Bilgi</a>
		 | <a rel="nofollow" class="tb" title="Karniyarik API kullanımı ile ilgili detaylı bilgi almak için tıklayın" href="<%=request.getContextPath()%>/api.jsp">API</a>
		 | <a rel="nofollow" class="tb" title="Karniyarik Veri Beslemeleri ile ilgili detaylı bilgi almak için tıklayın" href="<%=request.getContextPath()%>/datafeed/">Veri Beslemesi</a> 
		 | <a rel="nofollow" class="tb" title="Karniyarik iPhone uygulaması" href="<%=request.getContextPath()%>/iphone/">iPhone</a>
		 | <a rel="nofollow" class="tb" title="Karniyarik iş imkanlari" href="<%=request.getContextPath()%>/isimkanlari/">İş İmkanları</a>
		 | <a rel="nofollow" class="tb" title="Karniyarik.com kullanımı ile ilgili detaylı bilgi almak için tıklayın" href="<%=request.getContextPath()%>/help.jsp">Yardım</a> 
		</div>
		<jsp:include page="ganalytics.jsp"/>