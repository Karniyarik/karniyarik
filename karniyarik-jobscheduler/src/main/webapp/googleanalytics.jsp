<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.common.ga.Analytics"%>
<%@page import="com.karniyarik.common.ga.GoogleAnalyticsDataFetcher"%>
<%@page import="com.karniyarik.common.ga.Visit"%>
<%@page import="com.karniyarik.common.ga.Event"%>
<%@page import="com.karniyarik.common.ga.Referral"%>

<script>
	$(document).ready(function() {
		$("a.inline").fancybox({
			'hideOnContentClick': true,
			'speedIn'		: 200, 
			'speedOut'		: 200,
			'overlayShow'	: false
		});
	});
</script>
<%
	String day = request.getParameter("day");
	Analytics analytics = null; 
	if(day != null && day.equals("today"))
	{
		analytics = GoogleAnalyticsDataFetcher.getInstance(true).getTodayAnalytics();	
	}
	else
	{
		analytics = GoogleAnalyticsDataFetcher.getInstance(true).getYesterdayAnalytics();
	}
%>
<div style="font-size:10px;">
   	<hr/>
   	<%=analytics.getDate()%><br/>
   	<div class="span-24">
    	<div class="span-23 last">
    	<table>
    		<caption>Visits</caption>
    		<tbody>
	    	<tr>
	    		<th class="nobg"></th>
	    		<th>Visitors</th>
	    		<th>Unique Visitors</th>
	    		<th>Page views</th>
	    		<th>Page/ Visit</th>
	    		<th>New Visits</th>
	    		<th>Bounce Rate</th>
	    		<th>Time On Site</th>
	    	</tr>
    		<%for(Visit visit: analytics.getVisits().getVisits()){ %>
	    	<tr>
	    		<td><%=visit.isMobile()?"Mobile" : "Web"%></td>
		    	<td><%=visit.getTotalVisitors()%></td>
		    	<td><%=visit.getUniqueVisitors()%></td>    	
		    	<td><%=visit.getTotalPageviews()%></td>
		    	<td><%=visit.getPagesPerVisit()%></td>
				<td><%=visit.getNewVisits()%></td>
				<td><%=visit.getBounceRate()%></td>
				<td><%=visit.getAvgTimeOnSite()%></td>
	    	</tr>
	    	<%} %>
	    	</tbody>
    	</table>
    	</div>
    	<div class="cl mt10"></div>    	
    	<div class="span-23 last">
	    	<table>
	    		<caption>Events</caption>
	    		<tbody>
		    	<tr>
		    		<th class="nobg">Name</th>
		    		<th>Total</th>
		    		<th>Unique</th>
		    		<th>Value</th>
		    		<th></th>
		    	</tr>
		    	<%
		    	int index =0;
		    	for(Event event: analytics.getEvents().getEvents()){
		    		index++;
		    	%>
		    	<tr>
			    	<td><a class="inline" href="#data<%=index%>"><%=event.getName()%></a></td>
			    	<td><%=event.getTotalEvents()%></td>
			    	<td><%=event.getUniqueEvents()%></td>
			    	<td><%=event.getTotalValue()%></td>
			    	<td>
			    		<a class="inline" href="#data<%=index%>">View</a>
			    		<div style="display:none">
			    			<div id="data<%=index%>">
							<table>
								<caption><%=event.getName()%></caption>
								<tbody>	    	
						    	<tr>
						    		<th class="nobg">Name</th>
						    		<th>Total</th>
						    		<th>Unique</th>
						    		<th>Value</th>
						    	</tr>
						    	<tr>
						    	<%for(Event subEvent: event.getTopSubEvents()){%>
							    	<td><%=subEvent.getName()%></td>
							    	<td><%=subEvent.getTotalEvents()%></td>
							    	<td><%=subEvent.getUniqueEvents()%></td>
							    	<td><%=subEvent.getTotalValue()%></td>
						    	</tr>
					    		<%}%>
					    		</tbody>
						    </table>			    		
			    			</div>
			    		</div>
			    	</td>
		    	</tr>
	    		<%}%>
	    		</tbody>
		    </table>
    	</div>
   	</div>    	
   	<div class="span-23 last">
    	<table>
    		<caption>Referrals</caption>
    		<tbody>
	    	<tr>
	    		<th class="nobg" scope="col">Source</th>
	    		<th scope="col">Visitor</th>
	    		<th scope="col">PageView</th>
	    		<th scope="col">Exits</th>
	    		<th scope="col">Avg. Time</th>
	    		<th scope="col">Time On Site</th>
	    	</tr>
	    	<%for(Referral referral : analytics.getReferrals().getReferrals()){%>
	    	<tr>
		    	<td><%=referral.getSource()%></td>
		    	<td><%=referral.getTotalVisitors()%></td>
		    	<td><%=referral.getTotalPageviews()%></td>
		    	<td><%=referral.getExits()%></td>
		    	<td><%=referral.getAvgTimeOnSite()%></td>
		    	<td><%=referral.getPagesPerVisit()%></td>
	    	</tr>
    		<%}%>
    		</tbody>
	    </table>
   	</div>
   	<div class="cl mt10"></div>    	
</div>

