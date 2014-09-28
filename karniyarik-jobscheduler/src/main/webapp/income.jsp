<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.karniyarik.common.ga.GoogleAnalyticsDataFetcher"%>
<%@page import="com.karniyarik.common.ga.Analytics"%>
<%@page import="com.karniyarik.common.ga.Event"%>
<%@page import="java.text.DecimalFormat"%>

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
<div>
   	<%
   		Event sponsoredEvent = null; 
   		for(Event event: analytics.getEvents().getEvents())
   		{
   			if(event.isClick() && event.isSponsored())
   			{
   				sponsoredEvent = event;
   				break;
   			}
   		}
   		DecimalFormat format = new DecimalFormat("###,###.##");
   		Double sponsoredIncome = 0d; 
   		if(sponsoredEvent != null)
   		{
   			sponsoredIncome = sponsoredEvent.getTotalValue();
   			sponsoredIncome = sponsoredIncome * 1.18 / 100;
   		}
   		
   		double total = sponsoredIncome;
   	%>
   	<table>
   		<tbody>
   			<tr>
	    		<th>Sponsored</th>
	    		<td><%=format.format(sponsoredIncome)%> TL</td>
    		</tr>
    		<tr>
	    		<th>Adsense</th>
	    		<td></td>
    		</tr>
    		<tr>
	    		<th>Gelir Ort.</th>
	    		<td></td>
    		</tr>
    		<tr>
	    		<th>Bendeistiyorum</th>
	    		<td></td>
    		</tr>
   		</tbody>
   		<tfoot>
   			<tr>
   				<td>Total:</td>
   				<td><%=format.format(total)%> TL</td>
   			</tr> 
   		</tfoot>
   	</table>
</div>