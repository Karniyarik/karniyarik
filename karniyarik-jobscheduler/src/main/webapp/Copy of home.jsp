<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.karniyarik.common.group.GroupMember"%>
<%@page import="com.karniyarik.jobscheduler.SchedulerGroupServer"%><html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>Karniyarik Admin</title>
<jsp:include page="common_include.jsp"></jsp:include>
</head>
<body>
  <div class="container rndb">
    <%@ include file="header.jsp" %>
       <div>
       		<h1>Cluster Members</h1>
    		<%
    			List<GroupMember> members = SchedulerGroupServer.getInstance().getActiveMembers();
				for(GroupMember member: members) {
    		%>
    		<div class="bkmrk" style="width:110px;text-align: center;">
	    		<img src="<%=request.getContextPath()%>/images/logos/server<%=member.isSeleniumCapable() ? "_se" : ""%>.png"/>
	    		<span><%=member.getAddress()%></span>
	    		<span><%=member.getIp()%></span>
	    		<span><%=member.getType()%></span>
    		</div>
   			<%}%>
       </div>
       <div class="cl"></div>
       <div>
           <h1>Bookmarks</h1>
           <hr />
           <div class="bkmrk">
           	<a target="_blank" href="http://karniyarik.com:11080/probe" title="Prob Enterprise">
           		<img src="<%=request.getContextPath()%>/images/logos/probe_ent.png"></img>
           	</a>
           </div>

           <div class="bkmrk">
           	<a target="_blank" href="http://karniyarik.com:11080/probe" title="Prob Web">
           		<img src="<%=request.getContextPath()%>/images/logos/probe_web.png"></img>
           	</a>
           </div>

           <div class="bkmrk">
           	<a target="_blank" href="http://www.alexa.com/siteinfo/karniyarik.com" title="Alexa">
           		<img src="<%=request.getContextPath()%>/images/logos/alexa.png"></img>
           	</a>
           </div>

           <div class="bkmrk">
           	<a target="_blank" href="http://www.quantcast.com/karniyarik.com" title="Quantcast">
           		<img src="<%=request.getContextPath()%>/images/logos/quantcast.png"></img>
           	</a>
           </div>

           <div class="bkmrk">
           	<a target="_blank" href="http://www.addthis.com/" title="Addthis">
           		<img src="<%=request.getContextPath()%>/images/logos/addthis.png"></img>
           	</a>
           </div>

           <div class="bkmrk">
           	<a target="_blank" href="http://twitter.com/karniyarik" title="Twitter">
           		<img src="<%=request.getContextPath()%>/images/logos/twitter.png"></img>
           	</a>
           </div>

           <div class="bkmrk">
           	<a target="_blank" href="http://dev.karniyarik.com/trac/karniyarik" title="Trac">
           		<img src="<%=request.getContextPath()%>/images/logos/trac.png"></img>
           	</a>
           </div>
       </div>
		<div class="cl"></div>
       <div>
           <h1>Monitoring</h1>
           
           <div class="dashboard_1" style="width:120px;">
           	<div class="dash_int">
           		<script type="text/javascript" src="http://cdn.widgetserver.com/syndication/subscriber/InsertWidget.js"></script><script>if (WIDGETBOX) WIDGETBOX.renderWidget('4b1573be-d8fa-4d21-b0c8-a4a42cb740eb');</script><noscript>Get the <a href="http://www.widgetbox.com/widget/google-pagerank-badge-ladios">Google PageRank Badge</a> widget and many other <a href="http://www.widgetbox.com/">great free widgets</a> at <a href="http://www.widgetbox.com">Widgetbox</a>!</noscript>
           	</div>
	        <div class="dash_int">  
	           <script type='text/javascript' language='JavaScript' src='http://xslt.alexa.com/site_stats/js/s/a?url=www.karniyarik.com'></script>
	        </div>
           </div>
		   <div class="dashboard_1" style="width:600px;">
				<iframe marginwidth="0"  marginheight="0" width="600" height="250" src="https://www.embeddedanalytics.com/reports/displayreport?reportcode=CpCSoOqteF&chckcode=ga6zTc9lDeFMKCKixDVzHV" type="text/html" frameborder="0" scrolling="no"></iframe>		   
		   </div>
			<div class="dashboard_1" style="width:230px;">
			<div>
			<script type="text/javascript" language="JavaScript" src="http://twittercounter.com/embed/?username=karniyarik&style=bird"></script>
			</div>
			<script src="http://widgets.twimg.com/j/2/widget.js"></script>
			<script>
			new TWTR.Widget({
			  version: 2,
			  type: 'profile',
			  rpp: 3,
			  interval: 6000,
			  width: 250,
			  height: 300,
			  theme: {
			    shell: {
			      background: '#333333',
			      color: '#ffffff'
			    },
			    tweets: {
			      background: '#000000',
			      color: '#ffffff',
			      links: '#4aed05'
			    }
			  },
			  features: {
			    scrollbar: false,
			    loop: false,
			    live: false,
			    hashtags: true,
			    timestamp: true,
			    avatars: false,
			    behavior: 'all'
			  }
			}).render().setUser('karniyarik').start();
			</script>
           </div>		   
		   <div class="cl"></div>
		   <div class="dashboard_1">
	           <h2>You noodle</h2>
				<script type="text/javascript">var yn_widget_width = 300;var yn_widget_height = 150;</script><script language="javascript" type="text/javascript" src="http://younoodle.com/widget/score/startup?identifier=karniyarik_com"></script>
           </div>
           <div class="dashboard_1">
				<script type="text/javascript"
				src="http://widgets.alexa.com/traffic/javascript/graph.js"></script>
				<script type="text/javascript">/*
				<![CDATA[*/
				// USER-EDITABLE VARIABLES
				// enter up to 3 domains, separated by a space
				var sites      = ['karniyarik.com'];
				var opts = {
				width:      400,  // width in pixels (max 400)
				height:     250,  // height in pixels (max 300)
				type:       'r',  // "r" Reach, "n" Rank, "p" Page Views
				range:      '3m', // "7d", "1m", "3m", "6m", "1y", "3y", "5y", "max"
				bgcolor:    'e6f3fc' // hex value without "#" char (usually "e6f3fc")
				};
				// END USER-EDITABLE VARIABLES
				AGraphManager.add( new AGraph(sites, opts) );
				//]]></script>				
			</div>
			<div class="cl"></div>           
           	
           <div class="cl"></div>
       </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
