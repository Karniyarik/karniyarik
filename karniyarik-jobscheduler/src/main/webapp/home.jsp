<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.karniyarik.common.group.GroupMember"%>
<%@page import="com.karniyarik.jobscheduler.SchedulerGroupServer"%>
<%@page import="com.karniyarik.jobscheduler.util.SystemHealthCalculator"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.karniyarik.common.group.GroupMemberFactory"%>
<%@page import="com.karniyarik.jobscheduler.JobAdminStateFilter"%><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Karniyarik Job Scheduler</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container">
  <%@ include file="header.jsp" %>
  <div class="span-24 rndb">
  	<div>
         <h1>Bookmarks</h1>
         <hr />
         <div class="bkmrk">
         	<a target="_blank" href="https://www.google.com/analytics/reporting/?id=11734189" title="Google Analytics">
         		<img src="<%=request.getContextPath()%>/images/logos/analytics.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="https://www.google.com/webmasters/tools/dashboard?hl=tr&siteUrl=http://www.karniyarik.com" title="Google Webmasters">
        		<img src="<%=request.getContextPath()%>/images/logos/webmasters.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="https://monitor.distimo.com/dashboard" title="Distimo iPhone Analytics">
        		<img src="<%=request.getContextPath()%>/images/logos/distimo.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="https://pp.pingdom.com/index.php/member/reports/reports_uptime" title="Pingdom Uptime Analytics">
        		<img src="<%=request.getContextPath()%>/images/logos/pingdom.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://www.addthis.com/" title="Addthis">
        		<img src="<%=request.getContextPath()%>/images/logos/addthis.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://www.alexa.com/siteinfo/karniyarik.com" title="Alexa Karniyarik">
        		<img src="<%=request.getContextPath()%>/images/logos/alexa.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://twitter.com/karniyarik" title="Twitter">
        		<img src="<%=request.getContextPath()%>/images/logos/twitter.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://twittercounter.com/compare/karniyarik/month/" title="Twitter Stats">
        		<img src="<%=request.getContextPath()%>/images/logos/twittercounter.png"></img>
        	</a>
        </div>        
        <div class="bkmrk">
        	<a target="_blank" href="http://www.facebook.com/insights/?sk=po_90334878760" title="Facebook Stats">
        		<img src="<%=request.getContextPath()%>/images/logos/facebook.png"></img>
        	</a>
        </div>
        <div class="cl"></div>
        <div class="bkmrk">
        	<a target="_blank" href="http://us2.admin.mailchimp.com/" title="Mailchimp">
        		<img src="<%=request.getContextPath()%>/images/logos/mailchimp.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://www.bendeistiyorum.com/affiliate/index.php" title="Bendeistiyorum">
        		<img src="<%=request.getContextPath()%>/images/logos/bendeistiyorum.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://panel.gelirortaklari.com/snapshot" title="Gelir Ortakları">
        		<img src="<%=request.getContextPath()%>/images/logos/gelirortaklari.png"></img>
        	</a>
        </div>
        <div class="cl"></div>
        <div class="bkmrk">
        	<a target="_blank" href="http://www.karniyarik.com/internal" title="Karniyarik Web">
        		<img src="<%=request.getContextPath()%>/images/logos/kweb.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://www.karniyarik.com/kurumsal" title="Karniyarik Enteprise">
        		<img src="<%=request.getContextPath()%>/images/logos/kenterprise.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.8:10080" title="Karniyarik Statistics">
        		<img src="<%=request.getContextPath()%>/images/logos/kstat.png"></img>
        	</a>
        </div>
		<div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.2:10080/karniyarik-jobexecutor" title="Karniyarik Executor 1">
        		<img src="<%=request.getContextPath()%>/images/logos/kexecutor.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.3:10080/karniyarik-jobexecutor" title="Karniyarik Executor 2">
        		<img src="<%=request.getContextPath()%>/images/logos/kexecutor.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.4:10080/karniyarik-jobexecutor" title="Karniyarik Executor 3">
        		<img src="<%=request.getContextPath()%>/images/logos/kexecutor.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.5:10080/karniyarik-jobexecutor" title="Karniyarik Executor 4">
        		<img src="<%=request.getContextPath()%>/images/logos/kexecutor.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.7:10080/karniyarik-jobexecutor" title="Karniyarik Executor 5">
        		<img src="<%=request.getContextPath()%>/images/logos/kexecutor.png"></img>
        	</a>
        </div>          
        <div class="cl"></div>
        <div class="bkmrk">
        	<a target="_blank" href="http://www.karniyarik.com:11080/probe" title="Prob Karniyarik Enterprise">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_ent.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://www.karniyarik.com:8080/probe" title="Probe Karniyarik Web">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_web.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.8:10080/probe" title="Probe Karniyarik Statistics">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_stat.png"></img>
        	</a>
        </div>        
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.2:10080/probe" title="Karniyarik Executor 1 Probe">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_executor.png"></img>
        	</a>
        </div>
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.3:10080/probe" title="Karniyarik Executor 2 Probe">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_executor.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.4:10080/probe" title="Karniyarik Executor 3 Probe">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_executor.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.5:10080/probe" title="Karniyarik Executor 4 Probe">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_executor.png"></img>
        	</a>
        </div>           
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.7:10080/probe" title="Karniyarik Executor 5 Probe">
        		<img src="<%=request.getContextPath()%>/images/logos/probe_executor.png"></img>
        	</a>
        </div>
        <div class="cl"></div>
        <div class="bkmrk">
        	<a target="_blank" href="<%=request.getContextPath()%>/systemhealth.jsp" title="Health">
        		<img src="<%=request.getContextPath()%>/images/logos/health.png"></img>
        	</a>
        </div>                              
        <div class="bkmrk">
        	<a target="_blank" href="http://dev.karniyarik.com/trac/karniyarik" title="Trac">
        		<img src="<%=request.getContextPath()%>/images/logos/trac.png"></img>
        	</a>
        </div>                              
        <div class="bkmrk">
        	<a target="_blank" href="http://77.92.136.8:9080/hudson" title="Hudson - Build">
        		<img src="<%=request.getContextPath()%>/images/logos/hudson.png"></img>
        	</a>
        </div>                              
        <div class="bkmrk">
        	<a target="_blank" href="http://admin.karniyarik.com/xquery-online" title="XQuery Online">
        		<img src="<%=request.getContextPath()%>/images/logos/xqueryonline.png"></img>
        	</a>
        </div>                              
    </div>
    <hr/>
    <div class="cl"></div>
    <jsp:include page="clustermembers.jsp"></jsp:include>
    <div class="cl"></div>
    <%@ include file="footer.jsp" %>  	
  </div>
 </div>
</body>
</html>
